package bspkrs.bspkrscore.fml.gui;

import bspkrs.bspkrscore.fml.Reference;
import bspkrs.util.config.gui.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class GuiBSConfig extends GuiConfig {
   public GuiBSConfig(GuiScreen parent) {
      super(parent, Reference.config.getCategory("general").getPropertyOrder(),  false,"bspkrsCore", false, GuiConfig.getAbridgedConfigPath(Reference.config.toString()));
   }
}
