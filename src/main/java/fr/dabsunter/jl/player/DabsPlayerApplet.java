package fr.dabsunter.jl.player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.PlayerApplet;

import java.net.URL;

/**
 * Created by David on 19/06/2017.
 *
 * A simple applet that plays an MPEG audio file.
 * The URL (relative to the document base)
 * is passed as the "audioURL" parameter.
 *
 * @author	Dabsunter
 */
public class DabsPlayerApplet extends PlayerApplet {

	private boolean needTest = true;
	private DabsAudioDevice audio = null;

	@Override
	protected AudioDevice getAudioDevice() throws JavaLayerException {
		DabsAudioDevice dev = new DabsAudioDevice();
		if (needTest) {
			dev.test();
			needTest = false;
		}
		audio = dev;
		return dev;
	}

	public DabsAudioDevice getLastAudioDevice() {
		return audio;
	}

	@Override
	protected URL getAudioURL()	{
		String urlString = getAudioFileName();
		URL url = null;
		if (urlString != null) {
			try	{
				url = new URL(urlString);
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
		return url;
	}
}
