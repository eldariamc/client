package bspkrs.armorstatushud.fml;

import bspkrs.util.Const;
import bspkrs.util.ModVersionChecker;
import fr.dabsunter.mcp.Initiable;

/*@Mod(
   modid = "ArmorStatusHUD",
   name = "ArmorStatusHUD",
   version = "1.28",
   dependencies = "required-after:bspkrsCore@[6.15,)",
   useMetadata = true,
   guiFactory = "bspkrs.armorstatushud.mcp.gui.ModGuiFactoryHandler"
)*/
public class ArmorStatusHUDMod implements Initiable {
   protected ModVersionChecker versionChecker;
   protected String versionURL = Const.VERSION_URL + "/Minecraft/" + Const.MCVERSION + "/armorStatusHUD.version";
   protected String mcfTopic = "http://www.minecraftforum.net/topic/1114612-";
   public static ArmorStatusHUDMod instance = new ArmorStatusHUDMod();
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
