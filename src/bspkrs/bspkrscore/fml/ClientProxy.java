package bspkrs.bspkrscore.fml;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class ClientProxy extends CommonProxy {
   private BSMainMenuRenderTicker mainMenuTicker;

   protected void registerGameTickHandler() {
      if(BSCClientTicker.allowUpdateCheck) {
         new BSCClientTicker();
      }

   }

   protected void registerMainMenuTickHandler() {
      this.mainMenuTicker = new BSMainMenuRenderTicker();
      //MinecraftForge.EVENT_BUS.register(this);
      //McpHandler.registerBusListener(this);
   }

   public void onGuiOpen(GuiScreen gui) {
      if(bspkrsCoreMod.instance.showMainMenuMobs) {
         if(gui instanceof GuiMainMenu && !this.mainMenuTicker.isRegistered()) {
            this.mainMenuTicker.register();
         } else if(this.mainMenuTicker.isRegistered()) {
            this.mainMenuTicker.unRegister();
         }
      }

   }
}
