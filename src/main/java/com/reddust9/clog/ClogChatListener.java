package com.reddust9.clog;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;

import java.util.Optional;

public class ClogChatListener {
    private final Clog plugin;

    public ClogChatListener(Clog plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        Optional<ServerConnection> playerServer = event.getPlayer().getCurrentServer();
        plugin.addEntry(new ClogEntry(
                System.currentTimeMillis(),
                event.getPlayer().getUsername(),
                playerServer.isPresent() ? playerServer.get().getServerInfo().getName() : "Unknown",
                ClogSourceType.CHAT,
                event.getMessage()
        ));
    }

    @Subscribe
    public void onCommandExecute(CommandExecuteEvent event) {
        if(!(event.getCommandSource() instanceof Player)) {
            // we don't care
            return;
        }
        Player player = (Player) event.getCommandSource();

        Optional<ServerConnection> playerServer = player.getCurrentServer();
        plugin.addEntry(new ClogEntry(
                System.currentTimeMillis(),
                player.getUsername(),
                playerServer.isPresent() ? playerServer.get().getServerInfo().getName() : "Unknown",
                ClogSourceType.COMMAND,
                event.getCommand()
        ));
    }
}
