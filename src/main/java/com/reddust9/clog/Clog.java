package com.reddust9.clog;

import com.google.inject.Inject;
import com.reddust9.clog.sinks.ClogJsonSink;
import com.reddust9.clog.sinks.ClogMongoSink;
import com.reddust9.clog.sinks.ClogSink;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;

@Plugin(
        id = "clog",
        name = "Clog",
        version = BuildConstants.VERSION,
        description = "Customizable chat logger for Velocity",
        authors = {"reddust9"}
)
public class Clog {
    @Inject private Logger logger;
    @Inject private ProxyServer server;
    @DataDirectory @Inject private Path dataDir;

    private ClogConfig config;
    private ArrayList<ClogSink> sinks;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        if(dataDir.toFile().mkdir()) {
            logger.debug("Created data folder");
        }

        config = ClogConfig.load(dataDir);
        server.getEventManager().register(this, new ClogChatListener(this));

        sinks = new ArrayList<>();
        if(config.isJsonSinkEnabled()) {
            sinks.add(new ClogJsonSink(dataDir));
        }
        if(config.isMongoSinkEnabled()) {
            sinks.add(new ClogMongoSink(config.getMongoSinkURI(), config.getMongoSinkDbName()));
        }

        logger.debug("Clog initialised!");
    }

    public void addEntry(ClogEntry entry) {
        if(!config.isLoggerEnabled()) {
            return;
        }

        sinks.forEach(s -> s.log(entry));
    }
}
