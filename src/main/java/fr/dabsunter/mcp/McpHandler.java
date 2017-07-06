package fr.dabsunter.mcp;

import bspkrs.armorstatushud.fml.ArmorStatusHUDMod;
import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import bspkrs.statuseffecthud.fml.StatusEffectHUDMod;
import fr.dabsunter.eldaria.Eldaria;
import fr.dabsunter.eldaria.macrocmd.MacroCommands;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import uristqwerty.CraftGuide.CraftGuide_MCP;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

/**
 * Created by David on 20/12/2016.
 */
public class McpHandler {

	private static final Initiable[] INIT_HANDLERS = new Initiable[]{
			bspkrsCoreMod.instance,
			ArmorStatusHUDMod.instance,
			StatusEffectHUDMod.instance,
			new MacroCommands(),
			Eldaria.ANTI_CHEAT,
			new CraftGuide_MCP()
	};
	private static final HashSet<Tickable> RENDER_TICK_HANDLERS = new HashSet<>();
	private static HashSet<Tickable> renderToAdd;
	private static HashSet<Tickable> renderToRemove;
	private static final HashSet<Tickable> CLIENT_TICK_HANDLERS = new HashSet<>();
	private static HashSet<Tickable> clientToAdd;
	private static HashSet<Tickable> clientToRemove;
	private static final HashSet<BusListener> BUS_HANDLERS = new HashSet<>();
	private static HashSet<BusListener> busToAdd;
	private static HashSet<BusListener> busToRemove;

	public static void firePreInit() {
		for (Initiable i : INIT_HANDLERS)
			i.preInit();
	}

	public static void fireInit() {
		for (Initiable i : INIT_HANDLERS)
			i.init();
	}

	public static void firePostInit() {
		for (Initiable i : INIT_HANDLERS)
			i.postInit();
	}

	public static void registerRenderTick(final Tickable tickable) {
		if (renderToAdd == null)
			renderToAdd = new HashSet<>();
		renderToAdd.add(tickable);
	}

	public static void unregisterRenderTick(final Tickable tickable) {
		if (renderToRemove == null)
			renderToRemove = new HashSet<>();
		renderToRemove.add(tickable);
	}

	public static void fireRenderTick(final Tickable.Phase phase) {
		if (renderToRemove != null) {
			RENDER_TICK_HANDLERS.removeAll(renderToRemove);
			renderToRemove = null;
		}
		if (renderToAdd != null) {
			RENDER_TICK_HANDLERS.addAll(renderToAdd);
			renderToAdd = null;
		}
		for (Tickable t : RENDER_TICK_HANDLERS)
			t.onTick(phase);
	}

	public static void registerClientTick(final Tickable tickable) {
		if (clientToAdd == null)
			clientToAdd = new HashSet<>();
		clientToAdd.add(tickable);
	}

	public static void unregisterClientTick(final Tickable tickable) {
		if (clientToRemove == null)
			clientToRemove = new HashSet<>();
		clientToRemove.add(tickable);
	}

	public static void fireClientTick(final Tickable.Phase phase) {
		if (clientToRemove != null) {
			CLIENT_TICK_HANDLERS.removeAll(clientToRemove);
			clientToRemove = null;
		}
		if (clientToAdd != null) {
			CLIENT_TICK_HANDLERS.addAll(clientToAdd);
			clientToAdd = null;
		}
		for (Tickable t : CLIENT_TICK_HANDLERS)
			t.onTick(phase);
	}

	public static void registerBusListener(final BusListener listener) {
		if (busToAdd == null)
			busToAdd = new HashSet<>();
		busToAdd.add(listener);
	}

	public static void unregisterBusListener(final BusListener listener) {
		if (busToRemove == null)
			busToRemove = new HashSet<>();
		busToRemove.add(listener);
	}

	public static void fireGuiOpen(final GuiScreen gui) {
		manageBusListener();
		for (BusListener l : BUS_HANDLERS)
			l.onGuiOpen(gui);
	}

	public static void fireMouseInput() {
		manageBusListener();
		for (BusListener l : BUS_HANDLERS)
			l.onMouseInput();
	}

	public static void fireKeyInput() {
		manageBusListener();
		for (BusListener l : BUS_HANDLERS)
			l.onKeyInput();
	}

	public static boolean fireLoadResource(ResourceLocation location, InputStream stream) throws IOException {
		manageBusListener();
		for (BusListener l : BUS_HANDLERS)
			if (!l.onLoadResource(location, stream))
				return false;
		return true;
	}

	private static void manageBusListener() {
		if (busToRemove != null) {
			BUS_HANDLERS.removeAll(busToRemove);
			busToRemove = null;
		}
		if (busToAdd != null) {
			BUS_HANDLERS.addAll(busToAdd);
			busToAdd = null;
		}
	}

	public static void browseOnDesktop(String uri)
	{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(new URI(uri));
			} catch (URISyntaxException | IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.err.println("Browse on Desktop is not supported !");
			System.err.println("But here is the link : " + uri);
		}
	}

}
