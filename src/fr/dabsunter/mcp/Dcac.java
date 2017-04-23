package fr.dabsunter.mcp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by David on 16/04/2017.
 * DabsClientAntiCheat
 */
public class Dcac extends BusListener implements Initiable, Tickable {
	private static final Logger logger = LogManager.getLogger("DCAC");

	private static final List<String> XRAY_TEXTURES = Arrays.asList(
			"minecraft:textures/blocks/stone.png",
			"minecraft:textures/blocks/dirt*.png",
			"minecraft:textures/blocks/gravel.png",
			"minecraft:textures/blocks/*sand.png",
			"minecraft:textures/blocks/obsidian.png",
			"minecraft:textures/blocks/grass_side.png",
			"minecraft:textures/blocks/grass_top.png",
			"minecraft:textures/blocks/netherrack.png",
			"minecraft:textures/blocks/*clay*.png",
			"minecraft:textures/blocks/*snow*.png"

	);
	private static final List<String> CHEAT_PROCESS = Arrays.asList(
			"zenkey.exe*",
			"wonderkeys.exe*",
			"autohotkey.exe*",
			"auto*clic*.exe*",
			"auto*clique*.exe*",
			"auto*klic*.exe*",
			"cheat*engine*.exe*",
			"klick.exe*",
			"click.exe*",
			"clic.exe*",
			"clicker.exe*",
			"cliqueur.exe*",
			"super*rapid*fire*.exe*",
			"speed.exe*",
			"speedhack.exe*",
			"speeder.exe*",
			"speedcheat.exe*",
			"cheatspeed.exe*"
	);

	private Minecraft mc;

	private long startTick = 0;
	private short countdown = 0;

	private volatile boolean cheating = false;

	@Override
	public void preInit() {
		this.mc = Minecraft.getMinecraft();
	}

	@Override
	public void init() {
		McpHandler.registerClientTick(this);
		McpHandler.registerBusListener(this);
	}

	@Override
	public void postInit() {
		new Thread("Dcac") {
			@Override
			public void run() {
				while (true) {
					if (Util.getOSType() == Util.EnumOS.WINDOWS) {
						try {
							Process tasklist = new ProcessBuilder("tasklist", "-fo", "csv", "-nh")
									.redirectErrorStream(true)
									.start();
							BufferedReader in = new BufferedReader(new InputStreamReader(tasklist.getInputStream()));
							String line;
							while ((line = in.readLine()) != null) {
								String name = line.split("\"")[1];
								if (contains(CHEAT_PROCESS, name.toLowerCase()))
									cheating = true;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					try {
						sleep(5000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void onTick(Phase phase) {
		if (phase == Phase.START) {
			long currentTick = System.currentTimeMillis();
			if (countdown == 0) {
				startTick = currentTick;
			} else if (countdown >= 200) {
				countdown = -1;
				currentTick = currentTick - startTick;
				if (currentTick < 9500) {
					logger.warn("Your game has reached " + (200000/currentTick) + " tps !");
					handleCheat();
				}
			}
			countdown++;

		} else if (cheating){
			handleCheat();
			cheating = false;
		}
	}

	@Override
	public boolean onLoadResource(ResourceLocation location, InputStream stream) throws IOException {
		if (contains(XRAY_TEXTURES, location.toString())) {
			BufferedImage texture = ImageIO.read(stream);
			int w = texture.getWidth();
			int h = texture.getHeight();

			for (int x = 0; x < w; ++x)
			{
				for (int y = 0; y < h; ++y)
				{
					int rgb = texture.getRGB(x, y);
					int alpha = (rgb >> 24) & 0xff;

					if (alpha < 255)
					{
						logger.warn("Found translucent pixel in " + location + " (" + x + ";" + y + ") alpha=" + alpha);
						logger.warn("Disable it !");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Flemme de faire une desc mais je suis fière de cette méthode
	 * C'est le support des étoiles, c'est à moitié regex... fin wala
	 *
	 * @param list  List of string which could match
	 * @param input String to match
	 * @return
	 */
	private static boolean contains(List<String> list, String input) {
		for (String entry : list) {
			boolean inStar = false;
			int n = 0;
			int lastN = 0;
			for (int i = 0; i <= input.length(); i++) {
				if (entry.length() == n)
					if (inStar || input.length() == i)
						return true;
					else
						break;
				else if (input.length() == i)
					if (entry.charAt(n) == '*')
						return true;
					else
						break;

				if (entry.charAt(n) == '*') {
					lastN = ++n;
					inStar = true;
					i--;
				} else if (entry.charAt(n) == input.charAt(i)) {
					n++;
					inStar = false;
				} else if (!inStar) {
					if (lastN != 0)
						n = lastN;
					else
						break;
				}
			}
		}
		return false;
	}

	private void handleCheat() {
		if (this.mc.theWorld != null) {
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld(null);
		}
		this.mc.displayGuiScreen(new GuiErrorScreen("Le cheat, c'est mal",
				"Nous avons remarqué que votre jeu fonctionnait de manière anormale.",
				"Veuillez désactiver toute modifications vous apportant un avantage en jeu."));
	}
}
