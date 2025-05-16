package com.github.donmor.lifeleech.forge;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import com.github.donmor.lifeleech.LifeLeechMod;
import com.github.donmor.lifeleech.LifeLeechMod.Conf;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(LifeLeechMod.MOD_ID)
public final class LifeLeechModForge {
	private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, LifeLeechMod.MOD_ID);
	@SuppressWarnings("unused")
	public static final RegistryObject<Enchantment> LIFELEECH_ENCHANTMENT = ENCHANTMENTS.register("lifeleech", () -> LifeLeechMod.LIFELEECH);

	public LifeLeechModForge() {
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
				}).getRight();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config);
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
		ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		LifeLeechMod.init();
	}
}
