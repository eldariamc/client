package bspkrs.statuseffecthud.fml;

import bspkrs.statuseffecthud.StatusEffectHUD;
import bspkrs.util.CommonUtils;
import fr.dabsunter.mcp.McpHandler;

import java.io.File;

public class ClientProxy extends CommonProxy {
   public void preInit() {
      StatusEffectHUD.initConfig(new File(CommonUtils.getConfigDir(), "statuseffecthud.cfg"));
   }

   public void init() {
      //FMLCommonHandler.instance().bus().register(new SEHGameTicker());
      McpHandler.registerClientTick(new SEHGameTicker());
      //FMLCommonHandler.instance().bus().register(new SEHRenderTicker());
      McpHandler.registerRenderTick(new SEHRenderTicker());
      //ClientCommandHandler.instance.registerCommand(new CommandStatusEffect());
      //FMLCommonHandler.instance().bus().register(this);
      /*if(bspkrsCoreMod.instance.allowUpdateCheck) {
         StatusEffectHUDMod var10000 = StatusEffectHUDMod.instance;
         String var10004 = StatusEffectHUDMod.metadata.version;
         String var10005 = StatusEffectHUDMod.instance.versionURL;
         StatusEffectHUDMod.instance.getClass();
         var10000.versionChecker = new ModVersionChecker("StatusEffectHUD", var10004, var10005, "http://www.minecraftforum.net/topic/1114612-");
         StatusEffectHUDMod.instance.versionChecker.checkVersionWithLogging();
      }*/

   }

   public static void onConfigChanged() {
         Reference.config.save();
         StatusEffectHUD.syncConfig();

   }
}
