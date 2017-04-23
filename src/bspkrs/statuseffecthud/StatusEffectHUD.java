package bspkrs.statuseffecthud;

import bspkrs.client.util.HUDUtils;
import bspkrs.statuseffecthud.fml.Reference;
import bspkrs.util.CommonUtils;
import bspkrs.util.config.Configuration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.*;

public class StatusEffectHUD {
   protected static float zLevel = -150.0F;
   private static ScaledResolution scaledResolution;
   private static final boolean enabledDefault = true;
   public static boolean enabled = true;
   private static final String alignModeDefault = "middleright";
   public static String alignMode = "middleright";
   private static final boolean disableInventoryEffectListDefault = true;
   public static boolean disableInventoryEffectList = true;
   private static final boolean enableBackgroundDefault = false;
   public static boolean enableBackground = false;
   private static final boolean enableEffectNameDefault = true;
   public static boolean enableEffectName = true;
   private static final boolean enableIconBlinkDefault = true;
   public static boolean enableIconBlink = true;
   private static final int durationBlinkSecondsDefault = 10;
   public static int durationBlinkSeconds = 10;
   private static final String effectNameColorDefault = "f";
   public static String effectNameColor = "f";
   private static final String durationColorDefault = "f";
   public static String durationColor = "f";
   private static final int xOffsetDefault = 2;
   public static int xOffset = 2;
   private static final int yOffsetDefault = 2;
   public static int yOffset = 2;
   private static final int yOffsetBottomCenterDefault = 41;
   public static int yOffsetBottomCenter = 41;
   private static final boolean applyXOffsetToCenterDefault = false;
   public static boolean applyXOffsetToCenter = false;
   private static final boolean applyYOffsetToMiddleDefault = false;
   public static boolean applyYOffsetToMiddle = false;
   private static final boolean showInChatDefault = true;
   public static boolean showInChat = true;
   private static Map<PotionEffect, Integer> potionMaxDurationMap = new HashMap();

   public static void initConfig(File file) {
      if(!CommonUtils.isObfuscatedEnv()) {
         ;
      }

      Reference.config = new Configuration(file);
      syncConfig();
   }

   public static void syncConfig() {
      String ctgyGen = "general";
      Reference.config.load();
      Reference.config.setCategoryComment(ctgyGen, "ATTENTION: Editing this file manually is no longer necessary. \nType the command \'/statuseffect config\' without the quotes in-game to modify these settings.");
      List<String> orderedKeys = new ArrayList(ConfigElement.values().length);
      enabled = Reference.config.getBoolean(ConfigElement.ENABLED.key(), ctgyGen, true, ConfigElement.ENABLED.desc(), ConfigElement.ENABLED.languageKey());
      orderedKeys.add(ConfigElement.ENABLED.key());
      alignMode = Reference.config.getString(ConfigElement.ALIGN_MODE.key(), ctgyGen, "middleright", ConfigElement.ALIGN_MODE.desc(), ConfigElement.ALIGN_MODE.validStrings(), ConfigElement.ALIGN_MODE.languageKey());
      orderedKeys.add(ConfigElement.ALIGN_MODE.key());
      disableInventoryEffectList = Reference.config.getBoolean(ConfigElement.DISABLE_INV_EFFECT_LIST.key(), ctgyGen, true, ConfigElement.DISABLE_INV_EFFECT_LIST.desc(), ConfigElement.DISABLE_INV_EFFECT_LIST.languageKey());
      orderedKeys.add(ConfigElement.DISABLE_INV_EFFECT_LIST.key());
      showInChat = Reference.config.getBoolean(ConfigElement.SHOW_IN_CHAT.key(), ctgyGen, true, ConfigElement.SHOW_IN_CHAT.desc(), ConfigElement.SHOW_IN_CHAT.languageKey());
      orderedKeys.add(ConfigElement.SHOW_IN_CHAT.key());
      enableBackground = Reference.config.getBoolean(ConfigElement.ENABLE_BACKGROUND.key(), ctgyGen, false, ConfigElement.ENABLE_BACKGROUND.desc(), ConfigElement.ENABLE_BACKGROUND.languageKey());
      orderedKeys.add(ConfigElement.ENABLE_BACKGROUND.key());
      enableEffectName = Reference.config.getBoolean(ConfigElement.ENABLE_EFFECT_NAME.key(), ctgyGen, true, ConfigElement.ENABLE_EFFECT_NAME.desc(), ConfigElement.ENABLE_EFFECT_NAME.languageKey());
      orderedKeys.add(ConfigElement.ENABLE_EFFECT_NAME.key());
      effectNameColor = Reference.config.getString(ConfigElement.EFFECT_NAME_COLOR.key(), ctgyGen, "f", ConfigElement.EFFECT_NAME_COLOR.desc(), ConfigElement.EFFECT_NAME_COLOR.validStrings(), ConfigElement.EFFECT_NAME_COLOR.languageKey());
      orderedKeys.add(ConfigElement.EFFECT_NAME_COLOR.key());
      durationColor = Reference.config.getString(ConfigElement.DURATION_COLOR.key(), ctgyGen, "f", ConfigElement.DURATION_COLOR.desc(), ConfigElement.DURATION_COLOR.validStrings(), ConfigElement.DURATION_COLOR.languageKey());
      orderedKeys.add(ConfigElement.DURATION_COLOR.key());
      enableIconBlink = Reference.config.getBoolean(ConfigElement.ENABLE_ICON_BLINK.key(), ctgyGen, true, ConfigElement.ENABLE_ICON_BLINK.desc(), ConfigElement.ENABLE_ICON_BLINK.languageKey());
      orderedKeys.add(ConfigElement.ENABLE_ICON_BLINK.key());
      durationBlinkSeconds = Reference.config.getInt(ConfigElement.DURATION_BLINK_SECONDS.key(), ctgyGen, 10, -1, 60, ConfigElement.DURATION_BLINK_SECONDS.desc(), ConfigElement.DURATION_BLINK_SECONDS.languageKey());
      orderedKeys.add(ConfigElement.DURATION_BLINK_SECONDS.key());
      xOffset = Reference.config.getInt(ConfigElement.X_OFFSET.key(), ctgyGen, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, ConfigElement.X_OFFSET.desc(), ConfigElement.X_OFFSET.languageKey());
      orderedKeys.add(ConfigElement.X_OFFSET.key());
      applyXOffsetToCenter = Reference.config.getBoolean(ConfigElement.APPLY_X_OFFSET_TO_CENTER.key(), ctgyGen, false, ConfigElement.APPLY_X_OFFSET_TO_CENTER.desc(), ConfigElement.APPLY_X_OFFSET_TO_CENTER.languageKey());
      orderedKeys.add(ConfigElement.APPLY_X_OFFSET_TO_CENTER.key());
      yOffset = Reference.config.getInt(ConfigElement.Y_OFFSET.key(), ctgyGen, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, ConfigElement.Y_OFFSET.desc(), ConfigElement.Y_OFFSET.languageKey());
      orderedKeys.add(ConfigElement.Y_OFFSET.key());
      applyYOffsetToMiddle = Reference.config.getBoolean(ConfigElement.APPLY_Y_OFFSET_TO_MIDDLE.key(), ctgyGen, false, ConfigElement.APPLY_Y_OFFSET_TO_MIDDLE.desc(), ConfigElement.APPLY_Y_OFFSET_TO_MIDDLE.languageKey());
      orderedKeys.add(ConfigElement.APPLY_Y_OFFSET_TO_MIDDLE.key());
      yOffsetBottomCenter = Reference.config.getInt(ConfigElement.Y_OFFSET_BOTTOM_CENTER.key(), ctgyGen, 41, Integer.MIN_VALUE, Integer.MAX_VALUE, ConfigElement.Y_OFFSET_BOTTOM_CENTER.desc(), ConfigElement.Y_OFFSET_BOTTOM_CENTER.languageKey());
      orderedKeys.add(ConfigElement.Y_OFFSET_BOTTOM_CENTER.key());
      Reference.config.setCategoryPropertyOrder(ctgyGen, orderedKeys);
      Reference.config.save();
   }

   public static boolean onTickInGame(Minecraft mc) {
      if(enabled && (mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat && showInChat) && !mc.gameSettings.showDebugInfo) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         scaledResolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
         displayStatusEffects(mc);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      return true;
   }

   private static int getX(int width) {
      return !alignMode.equalsIgnoreCase("topcenter") && !alignMode.equalsIgnoreCase("middlecenter") && !alignMode.equalsIgnoreCase("bottomcenter")?(!alignMode.equalsIgnoreCase("topright") && !alignMode.equalsIgnoreCase("middleright") && !alignMode.equalsIgnoreCase("bottomright")?xOffset:scaledResolution.getScaledWidth() - width - xOffset):scaledResolution.getScaledWidth() / 2 - width / 2 + (applyXOffsetToCenter?xOffset:0);
   }

   private static int getY(int rowCount, int height) {
      return !alignMode.equalsIgnoreCase("middleleft") && !alignMode.equalsIgnoreCase("middlecenter") && !alignMode.equalsIgnoreCase("middleright")?(!alignMode.equalsIgnoreCase("bottomleft") && !alignMode.equalsIgnoreCase("bottomright")?(alignMode.equalsIgnoreCase("bottomcenter")?scaledResolution.getScaledHeight() - rowCount * height - yOffsetBottomCenter:yOffset):scaledResolution.getScaledHeight() - rowCount * height - yOffset):scaledResolution.getScaledHeight() / 2 - rowCount * height / 2 + (applyYOffsetToMiddle?yOffset:0);
   }

   private static boolean shouldRender(PotionEffect pe, int ticksLeft, int thresholdSeconds) {
      return ((Integer)potionMaxDurationMap.get(pe)).intValue() > 400 && ticksLeft / 20 <= thresholdSeconds?ticksLeft % 20 < 10:true;
   }

   private static void displayStatusEffects(Minecraft mc) {
      Collection<PotionEffect> activeEffects = mc.thePlayer.getActivePotionEffects();
      if(!activeEffects.isEmpty()) {
         int yOffset = enableBackground?33:(enableEffectName?20:18);
         if(activeEffects.size() > 5 && enableBackground) {
            yOffset = 132 / (activeEffects.size() - 1);
         }

         int yBase = getY(activeEffects.size(), yOffset);

         for(PotionEffect potionEffect : activeEffects) {
            if(!potionMaxDurationMap.containsKey(potionEffect) || ((Integer)potionMaxDurationMap.get(potionEffect)).intValue() < potionEffect.getDuration()) {
               potionMaxDurationMap.put(potionEffect, new Integer(potionEffect.getDuration()));
            }

            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
            int xBase = getX(enableBackground?120:22 + mc.fontRenderer.getStringWidth("0:00"));
            String potionName = "";
            if(enableEffectName) {
               potionName = StatCollector.translateToLocal(potion.getName());
               if(potionEffect.getAmplifier() == 1) {
                  potionName = potionName + " II";
               } else if(potionEffect.getAmplifier() == 2) {
                  potionName = potionName + " III";
               } else if(potionEffect.getAmplifier() == 3) {
                  potionName = potionName + " IV";
               }

               xBase = getX(enableBackground?120:22 + mc.fontRenderer.getStringWidth(potionName));
            }

            String effectDuration = Potion.getDurationString(potionEffect);
            if(enableBackground) {
               HUDUtils.drawTexturedModalRect(xBase, yBase, 0, 166, 140, 32, zLevel);
            }

            if(!alignMode.toLowerCase().contains("right")) {
               if(potion.hasStatusIcon()) {
                  int potionStatusIcon = potion.getStatusIconIndex();
                  HUDUtils.drawTexturedModalRect(xBase + (enableBackground?6:0), yBase + (enableBackground?7:0), 0 + potionStatusIcon % 8 * 18, 198 + potionStatusIcon / 8 * 18, 18, 18, zLevel);
               }

               mc.fontRenderer.drawStringWithShadow("§" + effectNameColor + potionName + "§r", xBase + (enableBackground?10:4) + 18, yBase + (enableBackground?6:0), 16777215);
               if(shouldRender(potionEffect, potionEffect.getDuration(), durationBlinkSeconds)) {
                  mc.fontRenderer.drawStringWithShadow("§" + durationColor + effectDuration + "§r", xBase + (enableBackground?10:4) + 18, yBase + (enableBackground?6:0) + (enableEffectName?10:5), 16777215);
               }
            } else {
               xBase = getX(0);
               if(potion.hasStatusIcon()) {
                  int potionStatusIcon = potion.getStatusIconIndex();
                  if(!enableIconBlink || enableIconBlink && shouldRender(potionEffect, potionEffect.getDuration(), durationBlinkSeconds)) {
                     HUDUtils.drawTexturedModalRect(xBase + (enableBackground?-24:-18), yBase + (enableBackground?7:0), 0 + potionStatusIcon % 8 * 18, 198 + potionStatusIcon / 8 * 18, 18, 18, zLevel);
                  }
               }

               int stringWidth = mc.fontRenderer.getStringWidth(potionName);
               mc.fontRenderer.drawStringWithShadow("§" + effectNameColor + potionName + "§r", xBase + (enableBackground?-10:-4) - 18 - stringWidth, yBase + (enableBackground?6:0), 16777215);
               stringWidth = mc.fontRenderer.getStringWidth(effectDuration);
               if(shouldRender(potionEffect, potionEffect.getDuration(), durationBlinkSeconds)) {
                  mc.fontRenderer.drawStringWithShadow("§" + durationColor + effectDuration + "§r", xBase + (enableBackground?-10:-4) - 18 - stringWidth, yBase + (enableBackground?6:0) + (enableEffectName?10:5), 16777215);
               }
            }

            yBase += yOffset;
         }

         List<PotionEffect> toRemove = new LinkedList<>();

         for(PotionEffect pe : potionMaxDurationMap.keySet()) {
            if(!activeEffects.contains(pe)) {
               toRemove.add(pe);
            }
         }

         for(PotionEffect pe : toRemove) {
            potionMaxDurationMap.remove(pe);
         }
      }

   }
}
