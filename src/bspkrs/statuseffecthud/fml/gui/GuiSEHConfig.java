package bspkrs.statuseffecthud.fml.gui;

import bspkrs.statuseffecthud.fml.Reference;
import bspkrs.util.config.gui.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class GuiSEHConfig extends GuiConfig {
   public GuiSEHConfig(GuiScreen parent) {
      super(parent, Reference.config.getCategory("general").getPropertyOrder(), false,  "StatusEffectHUD",true, "StatusEffectHUD Config");
   }
}
