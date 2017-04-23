package bspkrs.bspkrscore.fml.gui;

import net.minecraft.client.Minecraft;

import java.util.Set;

public class ModGuiFactoryHandler {
   public void initialize(Minecraft minecraftInstance) {
   }

   public Class mainConfigGuiClass() {
      return GuiBSConfig.class;
   }

   public Set runtimeGuiCategories() {
      return null;
   }

   public Object getHandlerFor(Object element) {
      return null;
   }
}
