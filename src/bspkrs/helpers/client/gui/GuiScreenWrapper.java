package bspkrs.helpers.client.gui;

import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public class GuiScreenWrapper extends GuiScreen {
   public List buttonList() {
      return this.buttonList;
   }

   public int height() {
      return this.height;
   }

   public int width() {
      return this.width;
   }
}
