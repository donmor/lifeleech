package com.github.donmor.lifeleech.mixin;

import com.github.donmor.lifeleech.LivingEntityXIF;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityXIF {
	@Shadow
	protected float lastHurt;

	@Override
	public float getLastHurt() {
		return lastHurt;
	}
}
