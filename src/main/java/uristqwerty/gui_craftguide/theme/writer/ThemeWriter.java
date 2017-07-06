package uristqwerty.gui_craftguide.theme.writer;

import uristqwerty.gui_craftguide.theme.Theme;

import java.io.OutputStream;

public interface ThemeWriter
{
	/**
	 * Writes a Theme to the specified OutputStream.
	 * <br><br>
	 * returns true on success, or false if there was any
	 * sort of failure.
	 * @param theme
	 * @param output
	 * @return
	 */
	public boolean write(Theme theme, OutputStream output);
}
