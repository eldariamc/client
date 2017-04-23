package bspkrs.armorstatushud.fml;

import bspkrs.armorstatushud.ArmorStatusHUD;
import bspkrs.util.CommonUtils;
import fr.dabsunter.mcp.McpHandler;

import java.io.File;

public class ClientProxy extends CommonProxy {
   public void preInit() {
      ArmorStatusHUD.initConfig(new File(CommonUtils.getConfigDir(),"armorstatushud.cfg"));
   }

   public void init() {
      McpHandler.registerClientTick(new ASHGameTicker());
      McpHandler.registerRenderTick(new ASHRenderTicker());

      /* Keyrisium - already registered
      try {
         ClientCommandHandler.instance.registerCommand(new CommandArmorStatus());
      } catch (Throwable var3) {
         ;
      }*/

      /* Keyrisium - dont check for update
      if(bspkrsCoreMod.instance.allowUpdateCheck) {
         ArmorStatusHUDMod.instance.versionChecker = new ModVersionChecker("ArmorStatusHUD", ArmorStatusHUDMod.metadata.version, ArmorStatusHUDMod.instance.versionURL, ArmorStatusHUDMod.instance.mcfTopic);
         ArmorStatusHUDMod.instance.versionChecker.checkVersionWithLogging();
      }*/

   }


   public static void onConfigChanged() {
      Reference.config.save();
      ArmorStatusHUD.syncConfig();
   }
}
