package fr.dabsunter.eldaria;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

/**
 * Created by David on 25/06/2017.
 */
public class FloatingLog {

	private final String log;
	private int timeout = 60;

	public FloatingLog(String log) {
		this.log = log;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean update() {
		return --timeout < 0;
	}

	public void draw(FontRenderer renderer, int x, int y) {
		int alpha = timeout * 255 / 20;

		if (alpha > 255)
			alpha = 255;

		if (alpha > 8) {
			int width = renderer.getStringWidth(log);
			x -= width;

			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			Gui.drawRect(x, y - 1, x + width + 4, y + 8, alpha / 2 << 24);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			renderer.drawStringWithShadow(log, x, y, 0xFFFFFF + (alpha << 24));
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
}
