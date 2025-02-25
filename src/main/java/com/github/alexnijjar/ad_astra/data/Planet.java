package com.github.alexnijjar.ad_astra.data;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public record Planet(String translation, Identifier galaxy, Identifier solarSystem, RegistryKey<World> world, RegistryKey<World> orbitWorld, RegistryKey<World> parentWorld, int rocketTier, float gravity, boolean hasAtmosphere, int daysInYear,
		float temperature, long solarPower, long orbitSolarPower, boolean hasOxygen, ButtonColour buttonColour) {
}