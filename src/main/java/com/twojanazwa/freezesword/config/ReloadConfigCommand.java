package com.twojanazwa.freezesword.config;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReloadConfigCommand {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("freezesword")
                        .requires(source ->
                                source.getPlayer() != null &&
                                        source.getServer()
                                                .getPlayerManager()
                                                .isOperator(source.getPlayer().getPlayerConfigEntry())
                        )
                        .then(CommandManager.literal("reload")
                                .executes(ReloadConfigCommand::reload)
                        )
        ));
    }

    private static int reload(CommandContext<ServerCommandSource> context) {
        try {
            ModConfig.load();
            context.getSource().sendFeedback(
                    () -> Text.literal("§aFreeze Sword config reloaded successfully."),
                    true
            );
        } catch (Exception e) {
            context.getSource().sendFeedback(
                    () -> Text.literal("§cFailed to reload config: " + e.getMessage()),
                    true
            );
        }
        return 1;
    }
}