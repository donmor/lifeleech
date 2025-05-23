package com.github.donmor.lifeleech.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;

import com.github.donmor.lifeleech.LifeLeechMod;
import com.github.donmor.lifeleech.LifeLeechMod.Conf;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;

public final class LifeLeechModFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Run our common setup.
		class IModConfig {
		}
		ForgeConfigSpec config = new ForgeConfigSpec.Builder()
				.configure(builder -> {
					// General
					builder.translation(Conf.General.l10n).push(Conf.General.class.getSimpleName());
					builder.comment(Conf.General.Base.desc).translation(Conf.General.Base.l10n)
							.defineEnum(Conf.General.Base.class.getSimpleName(), Conf.General.Base.def,
									Conf.General.Base.Value.DEALT_DAMAGE,
									Conf.General.Base.Value.PLAYER_MAX_HP,
									Conf.General.Base.Value.TARGET_MAX_HP);
					builder.comment(Conf.General.Multiplier.desc).translation(Conf.General.Multiplier.l10n)
							.define(Conf.General.Multiplier.class.getSimpleName(), Conf.General.Multiplier.def);
					builder.pop();
					return new IModConfig();
				}).getValue();
		ForgeConfigRegistry.INSTANCE.register(LifeLeechMod.MOD_ID, ModConfig.Type.COMMON, config);
		LifeLeechMod.options = new LifeLeechMod.ConfigIF() {
			@Override
			public Conf.General.Base.Value Base() {
				return config.getValues().get("General.Base") instanceof ConfigValue<?> vw
						&& vw.get() instanceof Conf.General.Base.Value value ? value : Conf.General.Base.Value.DEALT_DAMAGE;
			}

			@Override
			public int Multiplier() {
				return config.getValues().get("General.Multiplier") instanceof ConfigValue<?> vw
						&& vw.get() instanceof Integer value ? value : 20;
			}
		};
		Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(LifeLeechMod.MOD_ID, "lifeleech"), LifeLeechMod.LIFELEECH);
		LifeLeechMod.init();
	}
}
