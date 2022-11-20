package com.github.alexnijjar.ad_astra.commands;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.LanderEntity;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;


public class DecelerateRocketCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("rocket")
                .then(CommandManager.literal("land").executes(DecelerateRocketCommand::run)));
    }

    public static boolean scmd = false;
    static  int countdown = 0;
    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        AdAstra.LOGGER.info("Command Run");
        scmd = true;

        return 1;
    }

}