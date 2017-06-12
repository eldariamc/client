package bspkrs.armorstatushud.fml;

import bspkrs.armorstatushud.ArmorStatusHUD;
import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;

public class ASHRenderTicker implements Tickable{
   private Minecraft mcClient = Minecraft.getMinecraft();
   private static boolean isRegistered = false;

   public ASHRenderTicker() {
      isRegistered = true;
   }

   public void onTick(Phase phase) {
      if(!phase.equals(Phase.START)) {
         if(!ArmorStatusHUD.onTickInGame(this.mcClient)) {
            //FMLCommonHandler.instance().bus().unregister(this);
            McpHandler.unregisterRenderTick(this);
            isRegistered = false;
         }

      }
   }

   public static boolean isRegistered() {
      return isRegistered;
   }
}
