package bspkrs.statuseffecthud.fml;

import bspkrs.util.Const;
import bspkrs.util.ModVersionChecker;
import fr.dabsunter.mcp.Initiable;

/*@Mod(
   modid = "StatusEffectHUD",
   name = "StatusEffectHUD",
   version = "1.27",
   dependencies = "required-after:bspkrsCore@[6.15,)",
   useMetadata = true,
   guiFactory = "bspkrs.statuseffecthud.mcp.gui.ModGuiFactoryHandler"
)*/
public class StatusEffectHUDMod implements Initiable {
   protected ModVersionChecker versionChecker;
   protected final String versionURL = Const.VERSION_URL + "/Minecraft/" + Const.MCVERSION + "/statusEffectHUD.version";
   protected final String mcfTopic = "http://www.minecraftforum.net/topic/1114612-";
   public static StatusEffectHUDMod instance = new StatusEffectHUDMod();
   public static CommonProxy proxy = new ClientProxy();

   public void preInit() {
      proxy.preInit();
   }

   public void init() {
      proxy.init();
   }

   @Override
   public void postInit() {

   }
}
