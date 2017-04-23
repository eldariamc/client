package bspkrs.armorstatushud.fml;

import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ASHGameTicker implements Tickable {
   private Minecraft mcClient = Minecraft.getMinecraft();
   private static boolean isRegistered = false;

   public ASHGameTicker() {
      isRegistered = true;
   }

   public void onTick(Phase phase) {
      if(!phase.equals(Phase.START)) {
         boolean keepTicking = this.mcClient == null || this.mcClient.thePlayer == null || this.mcClient.theWorld == null;
         if(!keepTicking && isRegistered) {
            if(bspkrsCoreMod.instance.allowUpdateCheck && ArmorStatusHUDMod.instance.versionChecker != null && !ArmorStatusHUDMod.instance.versionChecker.isCurrentVersion()) {
               for(String msg : ArmorStatusHUDMod.instance.versionChecker.getInGameMessage()) {
                  this.mcClient.thePlayer.addChatMessage(new ChatComponentText(msg));
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
