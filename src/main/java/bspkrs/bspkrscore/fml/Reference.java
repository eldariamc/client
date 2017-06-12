package bspkrs.bspkrscore.fml;

import bspkrs.util.Const;
import bspkrs.util.config.Configuration;

import java.util.logging.Logger;

public class Reference {
   public static final String MODID = "bspkrsCore";
   public static final String NAME = "bspkrsCore";
   public static final String MC_VERSION;
   public static final String MINECRAFT = "minecraft";
   public static final String PROXY_COMMON = "bspkrs.bspkrscore.fml.CommonProxy";
   public static final String PROXY_CLIENT = "bspkrs.bspkrscore.fml.ClientProxy";
   public static final String GUI_FACTORY = "bspkrs.bspkrscore.fml.gui.ModGuiFactoryHandler";
   public static final Logger LOGGER = Logger.getLogger("bspkrsCore");
   public static Configuration config;

   static {
      /*Properties prop = new Properties();

      try {
         InputStream stream = Reference.class.getClassLoader().getResourceAsStream("version.properties");
         prop.load(stream);
         stream.close();
      } catch (Exception var2) {
         Throwables.propagate(var2);
      }*/

      MC_VERSION = Const.MCVERSION/*prop.getProperty("version.minecraft")*/;
      config = null;
   }
}
