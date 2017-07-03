package fr.dabsunter.eldaria;

import bspkrs.util.config.ConfigProperty;

/**
 * Created by David on 03/07/2017.
 */
public class HudManager {

	private static final boolean[] ARMOR_ENABLED = {true, true, true};
	private static final String[] ARMOR_ALIGN_MODE = {"topright", "topleft", "topcenter"};
	private static final String[] ARMOR_LIST_MODE = {"vertical", "vertical", "horizontal"};
	private static final boolean[] ARMOR_ENABLE_ITEM_NAME = {false, false, false};
	private static final boolean[] ARMOR_SHOW_DAMAGE_OVERLAY = {false, false, false};
	private static final boolean[] ARMOR_SHOW_ITEM_COUNT = {true, true, true};
	private static final String[] ARMOR_DAMAGE_COLOR_LIST = {"100,f; 80,7; 60,e; 40,6; 25,c; 10,4",
			"100,f; 80,7; 60,e; 40,6; 25,c; 10,4", "100,f; 80,7; 60,e; 40,6; 25,c; 10,4"};
	private static final String[] ARMOR_DAMAGE_DISPLAY_TYPE = {"value", "value", "value"};
	private static final String[] ARMOR_DAMAGE_THRESHOLD_TYPE = {"value", "value", "value"};
	private static final boolean[] ARMOR_SHOW_ITEM_DAMAGE = {true, true, true};
	private static final boolean[] ARMOR_SHOW_ARMOR_DAMAGE = {true, true, true};
	private static final boolean[] ARMOR_SHOW_MAX_DAMAGE = {false, false, false};
	private static final boolean[] ARMOR_SHOW_EQUIPPED_ITEM = {true, true, true};
	private static final int[] ARMOR_X_OFFSET = {0, 0, 0};
	private static final int[] ARMOR_Y_OFFSET = {0, 0, 0};
	private static final int[] ARMOR_Y_OFFSET_BOTTOM_CENTER = {0, 0, 0};
	private static final boolean[] ARMOR_APPLY_X_OFFSET_TO_CENTER = {true, true, true};
	private static final boolean[] ARMOR_APPLY_Y_OFFSET_TO_MIDDLE = {true, true, true};
	private static final boolean[] ARMOR_SHOW_IN_CHAT = {true, true, true};

	private static final boolean[] EFFECT_ENABLED = {true, true, true};
	private static final String[] EFFECT_ALIGN_MODE = {"topleft", "middleleft", "middleleft"};
	private static final boolean[] EFFECT_DISABLE_INV_EFFECT_LIST = {false, false, false};
	private static final boolean[] EFFECT_ENABLE_BACKGROUND = {false, false, false};
	private static final boolean[] EFFECT_ENABLE_EFFECT_NAME = {true, true, true};
	private static final int[] EFFECT_DURATION_BLINK_SECONDS = {0, 0, 0};
	private static final boolean[] EFFECT_ENABLE_ICON_BLINK = {false, false, false};
	private static final String[] EFFECT_EFFECT_NAME_COLOR = {"f", "f", "f"};
	private static final String[] EFFECT_DURATION_COLOR = {"f", "f", "f"};
	private static final int[] EFFECT_X_OFFSET = {3, 3, 3};
	private static final int[] EFFECT_Y_OFFSET = {2, 0, 0};
	private static final int[] EFFECT_Y_OFFSET_BOTTOM_CENTER = {0, 0, 0};
	private static final boolean[] EFFECT_APPLY_X_OFFSET_TO_CENTER = {true, true, true};
	private static final boolean[] EFFECT_APPLY_Y_OFFSET_TO_MIDDLE = {true, true, true};
	private static final boolean[] EFFECT_SHOW_IN_CHAT = {true, true, true};

	public static void applyPresets(byte presets) {
		presets--; // User logic -> Computer logic <3

		for (ConfigProperty prop : bspkrs.armorstatushud.fml.Reference.config.getCategory("general").getPropertyOrder()) {
			switch (prop.getName()) {
				case "enabled":
					prop.set(ARMOR_ENABLED[presets]);
					break;
				case "alignMode":
					prop.set(ARMOR_ALIGN_MODE[presets]);
					break;
				case "listMode":
					prop.set(ARMOR_LIST_MODE[presets]);
					break;
				case "enableItemName":
					prop.set(ARMOR_ENABLE_ITEM_NAME[presets]);
					break;
				case "showDamageOverlay":
					prop.set(ARMOR_SHOW_ARMOR_DAMAGE[presets]);
					break;
				case "showItemCount":
					prop.set(ARMOR_SHOW_ITEM_COUNT[presets]);
					break;
				case "damageColorList":
					prop.set(ARMOR_DAMAGE_COLOR_LIST[presets]);
					break;
				case "damageDisplayType":
					prop.set(ARMOR_DAMAGE_DISPLAY_TYPE[presets]);
					break;
				case "damageThresholdType":
					prop.set(ARMOR_DAMAGE_THRESHOLD_TYPE[presets]);
					break;
				case "showItemDamage":
					prop.set(ARMOR_SHOW_ITEM_DAMAGE[presets]);
					break;
				case "showArmorDamage":
					prop.set(ARMOR_SHOW_ARMOR_DAMAGE[presets]);
					break;
				case "showMaxDamage":
					prop.set(ARMOR_SHOW_MAX_DAMAGE[presets]);
					break;
				case "showEquippedItem":
					prop.set(ARMOR_SHOW_EQUIPPED_ITEM[presets]);
					break;
				case "xOffset":
					prop.set(ARMOR_X_OFFSET[presets]);
					break;
				case "yOffset":
					prop.set(ARMOR_Y_OFFSET[presets]);
					break;
				case "yOffsetBottomCenter":
					prop.set(ARMOR_Y_OFFSET_BOTTOM_CENTER[presets]);
					break;
				case "applyXOffsetToCenter":
					prop.set(ARMOR_APPLY_X_OFFSET_TO_CENTER[presets]);
					break;
				case "applyYOffsetToMiddle":
					prop.set(ARMOR_APPLY_Y_OFFSET_TO_MIDDLE[presets]);
					break;
				case "showInChat":
					prop.set(ARMOR_SHOW_IN_CHAT[presets]);
			}
		}

		for (ConfigProperty prop : bspkrs.statuseffecthud.fml.Reference.config.getCategory("general").getPropertyOrder()) {
			switch (prop.getName()) {
				case "enabled":
					prop.set(EFFECT_ENABLED[presets]);
					break;
				case "alignMode":
					prop.set(EFFECT_ALIGN_MODE[presets]);
					break;
				case "disableInventoryEffectList":
					prop.set(EFFECT_DISABLE_INV_EFFECT_LIST[presets]);
					break;
				case "enableBackground":
					prop.set(EFFECT_ENABLE_BACKGROUND[presets]);
					break;
				case "enableEffectName":
					prop.set(EFFECT_ENABLE_EFFECT_NAME[presets]);
					break;
				case "durationBlinkSeconds":
					prop.set(EFFECT_DURATION_BLINK_SECONDS[presets]);
					break;
				case "enableIconBlink":
					prop.set(EFFECT_ENABLE_ICON_BLINK[presets]);
					break;
				case "effectNameColor":
					prop.set(EFFECT_EFFECT_NAME_COLOR[presets]);
					break;
				case "durationColor":
					prop.set(EFFECT_DURATION_COLOR[presets]);
					break;
				case "xOffset":
					prop.set(EFFECT_X_OFFSET[presets]);
					break;
				case "yOffset":
					prop.set(EFFECT_Y_OFFSET[presets]);
					break;
				case "yOffsetBottomCenter":
					prop.set(EFFECT_Y_OFFSET_BOTTOM_CENTER[presets]);
					break;
				case "applyXOffsetToCenter":
					prop.set(EFFECT_APPLY_X_OFFSET_TO_CENTER[presets]);
					break;
				case "applyYOffsetToMiddle":
					prop.set(EFFECT_APPLY_Y_OFFSET_TO_MIDDLE[presets]);
					break;
				case "showInChat":
					prop.set(EFFECT_SHOW_IN_CHAT[presets]);
			}
		}

		bspkrs.armorstatushud.fml.ClientProxy.onConfigChanged();
		bspkrs.statuseffecthud.fml.ClientProxy.onConfigChanged();
	}

}
