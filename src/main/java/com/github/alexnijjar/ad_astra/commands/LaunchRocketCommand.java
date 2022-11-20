package com.github.alexnijjar.ad_astra.commands;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;
import com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings;

import java.util.concurrent.TimeUnit;

import static com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings.*;


public class LaunchRocketCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("rocket")
                .then(CommandManager.literal("launch").executes(LaunchRocketCommand::run)));
    }

    static boolean lcmd = false;
    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        AdAstra.LOGGER.info("Command Run");
        lcmd = true;
        LaunchRocket.launch();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lcmd = false;
        return 1;
    }

}