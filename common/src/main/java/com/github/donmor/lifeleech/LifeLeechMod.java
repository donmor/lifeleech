package com.github.donmor.lifeleech;

import net.minecraft.world.item.enchantment.Enchantment;

public final class LifeLeechMod {
	public static final String MOD_ID = "lifeleech";

	public abstract static class Conf {
		public abstract static class General {
			public static final String l10n = "config.lifeleech.general";

			public abstract static class Base {
				public static final String l10n = "config.lifeleech.general.base";
				public static final String desc = "Base value to calculate leeched HP from";
				public static final Value def = Value.DEALT_DAMAGE;

				public enum Value {
					DEALT_DAMAGE,
					PLAYER_MAX_HP,
					TARGET_MAX_HP
				}
			}

			public abstract static class Multiplier {
				public static final String l10n = "config.lifeleech.general.multiplier";
				public static final String desc = "Multiplier to calculate leeched HP (=Base(%)*level*Multiplier)";
				public static final int def = 20;
			}
		}
	}

	public interface ConfigIF {
		Conf.General.Base.Value Base();

		int Multiplier();
	}

	public static ConfigIF options;

	public static Enchantment LIFELEECH = new LifeLeechEnchantment();

	public static void init() {
		// Write common init code here.
	}
}
