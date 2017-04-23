package net.minecraft.util;

/**
 * Created by David on 08/08/2016.
 */
public class XpUtil {
	public static final int XP_PER_BOTTLE = 8;
	public static final int RATIO = 20;
	public static final int LIQUID_PER_XP_BOTTLE = 160;

	public static int liquidToExperience(int liquid) {
		return liquid / 20;
	}

	public static int experienceToLiquid(int xp) {
		return xp * 20;
	}

	public static int getLiquidForLevel(int level) {
		return experienceToLiquid(getExperienceForLevel(level));
	}

	public static int getExperienceForLevel(int level) {
		return level == 0?0:(level > 0 && level < 16?level * 17:(level > 15 && level < 31?(int)(1.5D * Math.pow((double)level, 2.0D) - 29.5D * (double)level + 360.0D):(int)(3.5D * Math.pow((double)level, 2.0D) - 151.5D * (double)level + 2220.0D)));
	}

	public static int getXpBarCapacity(int level) {
		return level >= 30?62 + (level - 30) * 7:(level >= 15?17 + (level - 15) * 3:17);
	}

	public static int getLevelForExperience(int experience) {
		int i;
		for(i = 0; getExperienceForLevel(i) <= experience; ++i) {
			;
		}

		return i - 1;
	}
}