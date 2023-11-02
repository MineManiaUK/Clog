package com.reddust9.clog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClogConfig {
    private static final Logger logger = LoggerFactory.getLogger(Clog.class);

    private boolean enableLogger;
    private boolean useJsonSink;

    private ClogConfig() {
        this.enableLogger = true;
        this.useJsonSink = true;
    }

    public static ClogConfig load(Path dataDir) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path configPath = dataDir.resolve("clog-config.json");

        if(!configPath.toFile().exists()) {
            logger.debug("Creating default config file");
            String defaultConfig = gson.toJson(new ClogConfig());
            try {
                Files.writeString(configPath, defaultConfig);
            } catch (IOException e) {
                logger.error("Could not write default config!", e);
            }
        }

        String configJson = null;
        try {
            configJson = Files.readString(configPath);
        } catch (IOException e) {
            logger.error("Could not load config file!", e);
        }
        return gson.fromJson(configJson, ClogConfig.class);
    }

    public boolean isLoggerEnabled() {
        return enableLogger;
    }

    public boolean isJsonSinkEnabled() {
        return useJsonSink;
    }
}
