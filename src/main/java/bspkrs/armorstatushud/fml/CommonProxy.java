package bspkrs.armorstatushud.fml;

import fr.dabsunter.mcp.Initiable;


public class CommonProxy implements Initiable {
   public void preInit() {
   }

   public void init() {
      Reference.LOGGER.severe("***********************************************************************************");
      Reference.LOGGER.severe("* ArmorStatusHUD is a CLIENT-ONLY mod. Installing it on your server is pointless. *");
      Reference.LOGGER.severe("***********************************************************************************");
   }

   @Override
   public void postInit() {
   }
}
