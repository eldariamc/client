package bspkrs.bspkrscore.fml;

import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class BSCClientTicker implements Tickable {
   public static boolean allowUpdateCheck;
   private Minecraft mcClient;
   public static boolean isRegistered = false;

   public BSCClientTicker() {
      if(!isRegistered) {
         this.mcClient = Minecraft.getMinecraft();
         //FMLCommonHandler.instance().bus().register(this);
         McpHandler.registerClientTick(this);
         isRegistered = true;
      }

   }

   public void onTick(Phase phase) {
      if(!phase.equals(Phase.START)) {
         boolean keepTicking = this.mcClient == null || this.mcClient.thePlayer == null || this.mcClient.theWorld == null;
         if(!keepTicking && isRegistered) {
            if(bspkrsCoreMod.instance.allowUpdateCheck && bspkrsCoreMod.instance.versionChecker != null && !bspkrsCoreMod.instance.versionChecker.isCurrentVersion()) {
               for(String msg : bspkrsCoreMod.instance.versionChecker.getInGameMessage()) {
                  this.mcClient.thePlayer.addChatMessage(new ChatComponentText(msg));
               }
            }

            allowUpdateCheck = false;
            if(bspkrsCoreMod.instance.allowDebugOutput && !keepTicking && this.mcClient.theWorld.isClient) {
               ;
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

   static {
      allowUpdateCheck = false/*bspkrsCoreMod.instance.allowUpdateCheck*/;
   }
}
