package fr.dabsunter.mcp;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by David on 20/12/2016.
 */
public abstract class BusListener {

	public void onGuiOpen(GuiScreen gui) {}

	public void onMouseInput() {}

	public void onKeyInput() {}

	public boolean onLoadResource(ResourceLocation location, InputStream stream) throws IOException {
		return true;
	}
}
