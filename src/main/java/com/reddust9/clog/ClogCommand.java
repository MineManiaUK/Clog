package com.reddust9.clog;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class ClogCommand {
    public static BrigadierCommand create(Clog plugin) {
        LiteralCommandNode<CommandSource> literalNode = LiteralArgumentBuilder
                .<CommandSource>literal("clog")
                .requires(s -> s.hasPermission("clog.command"))
                .executes(ctx -> {
                    CommandSource src = ctx.getSource();
                    src.sendMessage(Component
                            .text("[CLOG] ")
                            .color(TextColor.fromHexString("#1cc715"))
                            .append(Component.text("This server is running Clog version " + BuildConstants.VERSION)));
                    return Command.SINGLE_SUCCESS;
                })
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("disable", StringArgumentType.word())
                        .executes(ctx -> {
                            String statusText;
                            boolean alreadyEnabled = plugin.getConfig().isLoggerEnabled();
                            if(!alreadyEnabled) {
                                statusText = "The plugin is already disabled!";
                            } else {
                                plugin.getConfig().setLoggerEnabled(false);
                                statusText = "Disabled the plugin!";
                            }

                            CommandSource src = ctx.getSource();
                            src.sendMessage(Component
                                    .text("[CLOG] ")
                                    .color(TextColor.fromHexString("#1cc715"))
                                    .append(Component.text(statusText)));

                            return Command.SINGLE_SUCCESS;
                        }))
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("enable", StringArgumentType.word())
                        .executes(ctx -> {
                            String statusText;
                            boolean alreadyEnabled = plugin.getConfig().isLoggerEnabled();
                            if(alreadyEnabled) {
                                statusText = "The plugin is already enabled!";
                            } else {
                                plugin.getConfig().setLoggerEnabled(true);
                                statusText = "Enabled the plugin!";
                            }

                            CommandSource src = ctx.getSource();
                            src.sendMessage(Component
                                    .text("[CLOG] ")
                                    .color(TextColor.fromHexString("#1cc715"))
                                    .append(Component.text(statusText)));

                            return Command.SINGLE_SUCCESS;
                        }))
                .build();

        return new BrigadierCommand(literalNode);
    }
}
