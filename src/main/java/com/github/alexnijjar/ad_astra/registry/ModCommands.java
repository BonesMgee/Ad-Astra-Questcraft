package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.commands.DecelerateRocketCommand;
import com.github.alexnijjar.ad_astra.commands.LaunchRocketCommand;
import com.github.alexnijjar.ad_astra.commands.PlanetGuiCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModCommands {

	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			PlanetGuiCommand.register(dispatcher);
		});
		CommandRegistrationCallback.EVENT.register(LaunchRocketCommand::register);
		CommandRegistrationCallback.EVENT.register(DecelerateRocketCommand::register);
	}
}