package uristqwerty.CraftGuide;

import fr.dabsunter.mcp.Initiable;
import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import uristqwerty.CraftGuide.client.fml.CraftGuideClient_MCP;

import java.io.File;

public class CraftGuide_MCP implements CraftGuideLoaderSide, Initiable
{
	//private static Logger logger;

	private Minecraft mc;

	public static CraftGuideSide side = new CraftGuideClient_MCP();

	private CraftGuide craftguide;

	public static class KeyCheckTick implements Tickable
	{
		public void onTick(Phase phase)
		{
			if (phase == Phase.START)
				side.checkKeybind();
		}
	}

	public void preInit()
	{
		this.mc = Minecraft.getMinecraft();

		//logger = event.getModLog();
		CraftGuide.loaderSide = this;
		CraftGuide.side = side;
		craftguide = new CraftGuide();

		//FMLCommonHandler.instance().bus().register(new KeyCheckTick());
		McpHandler.registerClientTick(new KeyCheckTick());
	}

	public void init()
	{
		craftguide.preInit("craftguide:craftguide_item", true);
	}

	@Override
	public void postInit() {
		craftguide.init();

	}


	@Override
	public boolean isModLoaded(String name)
	{
		return false;
	}

	@Override
	public File getConfigDir()
	{
		return new File(mc.mcDataDir, "config");
	}

	@Override
	public File getLogDir()
	{
		File mcDir = mc.mcDataDir;

		if(mcDir == null)
			return CraftGuide.configDirectory();

		File dir = new File(mcDir, "logs");

		if(!dir.exists() && !dir.mkdirs())
		{
			return CraftGuide.configDirectory();
		}

		return dir;
	}

	/*@Override
	public void addRecipe(ItemStack output, Object[] recipe)
	{
		GameRegistry.addRecipe(output, recipe);
	}*/

	@Override
	public void logConsole(String text)
	{
		System.out.println("[CraftGuide] " + text);
	}

	@Override
	public void logConsole(String text, Throwable e)
	{
		System.err.println("[CraftGuide] " + text);
		e.printStackTrace();
	}

	@Override
	public void initClientNetworkChannels()
	{
		// TODO Auto-generated method stub

	}
}
