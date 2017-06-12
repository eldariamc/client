package bspkrs.bspkrscore.fml;

import bspkrs.util.CommonUtils;
import bspkrs.util.Const;
import bspkrs.util.ModVersionChecker;
import bspkrs.util.config.Configuration;
import fr.dabsunter.mcp.Initiable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*@Mod(
   modid = "bspkrsCore",
   name = "bspkrsCore",
   version = "6.16",
   useMetadata = true,
   guiFactory = "bspkrs.bspkrscore.fml.gui.ModGuiFactoryHandler"
)*/
public class bspkrsCoreMod implements Initiable {
   private final boolean allowUpdateCheckDefault = true;
   public boolean allowUpdateCheck = true;
   private final boolean allowDebugOutputDefault = false;
   public boolean allowDebugOutput = false;
   private final int updateTimeoutMillisecondsDefault = 3000;
   public int updateTimeoutMilliseconds = 3000;
   private final boolean generateUniqueNamesFileDefault = true;
   public boolean generateUniqueNamesFile = false;
   public boolean showMainMenuMobs = true;
   public static bspkrsCoreMod instance = new bspkrsCoreMod();
   public static CommonProxy proxy = new ClientProxy();
   protected ModVersionChecker versionChecker;
   protected final String versionURL = Const.VERSION_URL + "/Minecraft/" + Const.MCVERSION + "/bspkrsCore.version";
   protected final String mcfTopic = "http://www.minecraftforum.net/topic/1114612-";
   protected BSCClientTicker ticker;
   private boolean isCommandRegistered;

   public void preInit() {
      //metadata = event.getModMetadata();
      File file = new File(CommonUtils.getConfigDir(), "bspkrsCore.cfg");
      if(!CommonUtils.isObfuscatedEnv()) {
         ;
      }

      Reference.config = new Configuration(file);
      this.syncConfig();
   }

   public void syncConfig() {
      String ctgyGen = "general";
      Reference.config.load();
      Reference.config.setCategoryComment(ctgyGen, "ATTENTION: Editing this file manually is no longer necessary. \nOn the Mods list screen select the entry for bspkrsCore, then click the Config button to modify these settings.");
      List<String> orderedKeys = new ArrayList(ConfigElement.values().length);
      this.allowUpdateCheck = Reference.config.getBoolean(ConfigElement.ALLOW_UPDATE_CHECK.key(), ctgyGen, true, ConfigElement.ALLOW_UPDATE_CHECK.desc(), ConfigElement.ALLOW_UPDATE_CHECK.languageKey());
      orderedKeys.add(ConfigElement.ALLOW_UPDATE_CHECK.key());
      this.updateTimeoutMilliseconds = Reference.config.getInt(ConfigElement.UPDATE_TIMEOUT_MILLISECONDS.key(), ctgyGen, 3000, 100, 30000, ConfigElement.UPDATE_TIMEOUT_MILLISECONDS.desc(), ConfigElement.UPDATE_TIMEOUT_MILLISECONDS.languageKey());
      orderedKeys.add(ConfigElement.UPDATE_TIMEOUT_MILLISECONDS.key());
      this.allowDebugOutput = Reference.config.getBoolean(ConfigElement.ALLOW_DEBUG_OUTPUT.key(), ctgyGen, this.allowDebugOutput, ConfigElement.ALLOW_DEBUG_OUTPUT.desc(), ConfigElement.ALLOW_DEBUG_OUTPUT.languageKey());
      orderedKeys.add(ConfigElement.ALLOW_DEBUG_OUTPUT.key());
      this.generateUniqueNamesFile = Reference.config.getBoolean(ConfigElement.GENERATE_UNIQUE_NAMES_FILE.key(), ctgyGen, false, ConfigElement.GENERATE_UNIQUE_NAMES_FILE.desc(), ConfigElement.GENERATE_UNIQUE_NAMES_FILE.languageKey());
      orderedKeys.add(ConfigElement.GENERATE_UNIQUE_NAMES_FILE.key());
      this.showMainMenuMobs = Reference.config.getBoolean(ConfigElement.SHOW_MAIN_MENU_MOBS.key(), ctgyGen, true, ConfigElement.SHOW_MAIN_MENU_MOBS.desc(), ConfigElement.SHOW_MAIN_MENU_MOBS.languageKey());
      orderedKeys.add(ConfigElement.SHOW_MAIN_MENU_MOBS.key());
      Reference.config.setCategoryPropertyOrder(ctgyGen, orderedKeys);
      if(Reference.config.hasCategory(ctgyGen + ".example_properties")) {
         Reference.config.removeCategory(Reference.config.getCategory(ctgyGen + ".example_properties"));
      }

      Reference.config.save();
   }

   public void init() {
      /*if(this.allowUpdateCheck) {
         this.versionChecker = new ModVersionChecker("bspkrsCore", metadata.version, this.versionURL, "http://www.minecraftforum.net/topic/1114612-");
         this.versionChecker.checkVersionWithLogging();
      }*/

      //FMLCommonHandler.instance().bus().register(new NetworkHandler());

      /*try {
         ClientCommandHandler.instance.registerCommand(new CommandBS());
         this.isCommandRegistered = true;
      } catch (Throwable var3) {
         this.isCommandRegistered = false;
      }*/

      //FMLCommonHandler.instance().bus().register(instance);
   }

   public void postInit() {
      /*if(this.generateUniqueNamesFile) {
         UniqueNameListGenerator.instance().run();
      }*/

      proxy.registerMainMenuTickHandler();
   }

   /*@EventHandler
   public void serverStarting(FMLServerStartingEvent event) {
      if(!this.isCommandRegistered) {
         event.registerServerCommand(new CommandBS());
      }

   }*/

   public static void onConfigChanged() {
      //if(event.modID.equals("bspkrsCore")) {
      Reference.config.save();
      instance.syncConfig();
      //}

   }
}
