package bspkrs.statuseffecthud.fml;

import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class SEHGameTicker implements Tickable {
   private Minecraft mc;
   private static boolean isRegistered = false;

   public SEHGameTicker() {
      isRegistered = true;
      this.mc = Minecraft.getMinecraft();
   }

   public void onTick(Phase phase) {
      if(!phase.equals(Phase.START)) {
         boolean keepTicking = this.mc == null || this.mc.thePlayer == null || this.mc.theWorld == null;
         if(!keepTicking && isRegistered) {
            if(bspkrsCoreMod.instance.allowUpdateCheck && StatusEffectHUDMod.instance.versionChecker != null && !StatusEffectHUDMod.instance.versionChecker.isCurrentVersion()) {
               for(String msg : StatusEffectHUDMod.instance.versionChecker.getInGameMessage()) {
                  this.mc.thePlayer.addChatMessage(new ChatComponentText(msg));
               }
            }

            //FMLCommonHandler.instance().bus().unregister(this);
            McpHandler.unregisterClientTick(this);
            isRegistered = false;
         }

      }
   }

   public static boolean isRegistered() {
      return isRegistered;
   }
}
