package bspkrs.fml.util;

import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class DelayedGuiDisplayTicker implements Tickable {
   private int delayTicks;
   private Minecraft mcClient;
   private GuiScreen screen;

   public DelayedGuiDisplayTicker(int delayTicks, GuiScreen screen) {
      this.delayTicks = delayTicks;
      this.mcClient = Minecraft.getMinecraft();
      this.screen = screen;
      McpHandler.registerClientTick(this);
   }

   public void onTick(Phase phase) {
      if(phase != Phase.START) {
         if(--this.delayTicks <= 0) {
            this.mcClient.displayGuiScreen(this.screen);
            McpHandler.unregisterClientTick(this);
         }

      }
   }
}
