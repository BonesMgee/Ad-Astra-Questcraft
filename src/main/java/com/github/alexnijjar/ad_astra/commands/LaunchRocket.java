package com.github.alexnijjar.ad_astra.commands;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;

import static com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings.*;
import static com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings.sentRightPacket;

public class LaunchRocket {
    public static int launch() {
        AdAstra.LOGGER.info("Command Run");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            clickingJump = client.options.jumpKey.isPressed();
            ClientModKeybindings.clickingSprint = client.options.sprintKey.isPressed();
            ClientModKeybindings.clickingForward = client.options.forwardKey.isPressed();
            ClientModKeybindings.clickingBack = client.options.backKey.isPressed();
            ClientModKeybindings.clickingLeft = client.options.leftKey.isPressed();
            ClientModKeybindings.clickingRight = client.options.rightKey.isPressed();

            if (client.world != null) {
                if (client.player != null) {
                        if (client.player.getVehicle() instanceof RocketEntity rocket) {
                            if (!rocket.isFlying()) {
                                if (LaunchRocketCommand.lcmd == true)
                                    if (rocket.getFluidAmount() >= RocketEntity.getRequiredAmountForLaunch(rocket.getFluidVariant())) {
                                    PacketByteBuf buf = PacketByteBufs.create();
                                    buf.writeInt(rocket.getId());
                                    ClientPlayNetworking.send(ModC2SPackets.LAUNCH_ROCKET, buf);
                                } else if (sentJumpPacket) {
                                    client.player.sendMessage(new TranslatableText("message.ad_astra.no_fuel"), false);

                                }
                            }
                        }
                }

                if (clickingJump && sentJumpPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.JUMP));
                    sentJumpPacket = false;
                }

                if (clickingSprint && sentSprintPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.SPRINT));
                    sentSprintPacket = false;
                }

                if (clickingForward && sentForwardPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.FORWARD));
                    sentForwardPacket = false;
                }

                if (clickingBack && sentBackPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.BACK));
                    sentBackPacket = false;
                }

                if (clickingLeft && sentLeftPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.LEFT));
                    sentLeftPacket = false;
                }

                if (clickingRight && sentRightPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.RIGHT));
                    sentRightPacket = false;
                }

                if (!clickingJump && !sentJumpPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.JUMP));
                    sentJumpPacket = true;
                }

                if (!clickingSprint && !sentSprintPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.SPRINT));
                    sentSprintPacket = true;
                }

                if (!clickingForward && !sentForwardPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.FORWARD));
                    sentForwardPacket = true;
                }

                if (!clickingBack && !sentBackPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.BACK));
                    sentBackPacket = true;
                }

                if (!clickingLeft && !sentLeftPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.LEFT));
                    sentLeftPacket = true;
                }

                if (!clickingRight && !sentRightPacket) {
                    ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.RIGHT));
                    sentRightPacket = true;
                }
            }
        });
        return 1;
    }
}

