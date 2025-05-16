package com.github.donmor.lifeleech;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LifeLeechEnchantment extends Enchantment {
	private static final TagKey<EntityType<?>> ENTITY_TYPE_TAG_KEY = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("lifeleech", "blacklisted"));

	protected LifeLeechEnchantment() {
		super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int getMinCost(int i) {
		return 10 * i;
	}

	@Override
	public void doPostAttack(LivingEntity attacker, Entity target, int i) {
		if (!target.getType().is(ENTITY_TYPE_TAG_KEY))
			if (target instanceof LivingEntity e) switch (LifeLeechMod.options.Base()) {
				case DEALT_DAMAGE -> attacker.heal(Math.max(LifeLeechMod.options.Multiplier() * i * ((LivingEntityXIF) e).getLastHurt(), 1));
				case PLAYER_MAX_HP -> attacker.heal(Math.max(LifeLeechMod.options.Multiplier() * i * attacker.getMaxHealth(), 1));
				case TARGET_MAX_HP -> attacker.heal(Math.max(LifeLeechMod.options.Multiplier() * i * e.getMaxHealth(), 1));
			}
		super.doPostAttack(attacker, target, i);
	}
}
