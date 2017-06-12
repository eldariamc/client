package bspkrs.armorstatushud.fml.gui;

import bspkrs.armorstatushud.fml.Reference;
import bspkrs.util.config.gui.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class GuiASHConfig extends GuiConfig {
   public GuiASHConfig(GuiScreen parent) throws NoSuchMethodException, SecurityException {
      super(parent, Reference.config.getCategory("general").getPropertyOrder(), false,  "ArmorStatusHUD",true, "ArmorStatusHUD Config");
      /*super(parent, new IConfigProperty[]{
              ConfigElement.ENABLED.getConfigProperty(),
              ConfigElement.ALIGN_MODE.getConfigProperty(),
              ConfigElement.LIST_MODE.getConfigProperty(),
              ConfigElement.ENABLE_ITEM_NAME.getConfigProperty(),GuiConfig.getAbridgedConfigPath(Reference.config.toString())
              ConfigElement.SHOW_DAMAGE_OVERLAY.getConfigProperty(),
              ConfigElement.SHOW_ITEM_COUNT.getConfigProperty(),
              ConfigElement.DAMAGE_COLOR_LIST.getConfigProperty(),
              ConfigElement.DAMAGE_DISPLAY_TYPE.getConfigProperty(),
              ConfigElement.DAMAGE_THRESHOLD_TYPE.getConfigProperty(),
              ConfigElement.SHOW_ITEM_DAMAGE.getConfigProperty(),
              ConfigElement.SHOW_ARMOR_DAMAGE.getConfigProperty(),
              ConfigElement.SHOW_MAX_DAMAGE.getConfigProperty(),
              ConfigElement.SHOW_EQUIPPED_ITEM.getConfigProperty(),
              ConfigElement.X_OFFSET.getConfigProperty(),
              ConfigElement.Y_OFFSET.getConfigProperty(),
              ConfigElement.Y_OFFSET_BOTTOM_CENTER.getConfigProperty(),
              ConfigElement.APPLY_X_OFFSET_TO_CENTER.getConfigProperty(),
              ConfigElement.APPLY_Y_OFFSET_TO_MIDDLE.getConfigProperty(),
              ConfigElement.SHOW_IN_CHAT.getConfigProperty()
      }, false, "ArmorStatusHUD", false, "ArmorStatusHUD");*/
   }
}
