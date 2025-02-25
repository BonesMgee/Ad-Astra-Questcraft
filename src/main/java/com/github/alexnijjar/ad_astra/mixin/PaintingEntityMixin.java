package com.github.alexnijjar.ad_astra.mixin;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.google.common.collect.Lists;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Mixin(PaintingEntity.class)
public class PaintingEntityMixin {

	@ModifyVariable(method = "Lnet/minecraft/entity/decoration/painting/PaintingEntity;<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)V", at = @At("STORE"), ordinal = 0)
	private Iterator<PaintingMotive> adastra_modifyIterator(Iterator<PaintingMotive> iterator) {
		List<PaintingMotive> motives = Lists.newArrayList(iterator);
		List<PaintingMotive> motivesToRemove = new LinkedList<>();

		// Remove the paintings from this mod for the minecraft painting item
		for (PaintingMotive motive : motives) {
			if (Registry.PAINTING_MOTIVE.getId(motive).getNamespace().equals(AdAstra.MOD_ID)) {
				motivesToRemove.add(motive);
			}
		}
		motives.removeAll(motivesToRemove);

		return motives.listIterator();
	}
}