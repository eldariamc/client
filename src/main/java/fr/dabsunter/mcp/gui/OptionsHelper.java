package fr.dabsunter.mcp.gui;

/**
 * Created by David on 02/07/2017.
 */
public class OptionsHelper {

	private int i = -1;

	public int nextId() {
		return 100 + ++i;
	}

	public int getButtonX(int width) {
		return width / 2 - 155 + i % 2 * 160;
	}

	public int getButtonY(int height) {
		return height / 6 - 12 + 24 * (i >> 1);
	}
}
