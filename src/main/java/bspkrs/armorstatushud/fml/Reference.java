package bspkrs.armorstatushud.fml;

import bspkrs.util.config.Configuration;

import java.util.logging.Logger;

public class Reference {
   public static final String MODID = "ArmorStatusHUD";
   public static final String NAME = "ArmorStatusHUD";
   public static final String PROXY_COMMON = "bspkrs.armorstatushud.mcp.CommonProxy";
   public static final String PROXY_CLIENT = "bspkrs.armorstatushud.mcp.ClientProxy";
   public static final String GUI_FACTORY = "bspkrs.armorstatushud.mcp.gui.ModGuiFactoryHandler";
   public static final Logger LOGGER = Logger.getLogger("ArmorStatusHUD");
   public static Configuration config = null;
}
