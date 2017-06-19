package fr.dabsunter.jl.player;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDeviceBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;

import javax.sound.sampled.*;

/**
 * Created by David on 18/06/2017.
 *
 * The <code>JavaSoundAudioDevice</code> implements an audio
 * device by using the JavaSound API.
 *
 * @author Dabsunter
 */
public class DabsAudioDevice extends AudioDeviceBase {
	private SourceDataLine source = null;

	private AudioFormat fmt = null;

	private byte[] byteBuf = new byte[4096];

	protected void setAudioFormat(AudioFormat fmt)	{
		this.fmt = fmt;
	}

	protected AudioFormat getAudioFormat() {
		if (fmt == null) {
			Decoder decoder = getDecoder();
			fmt = new AudioFormat(decoder.getOutputFrequency(),
					16,
					decoder.getOutputChannels(),
					true,
					false);
		}
		return fmt;
	}

	protected DataLine.Info getSourceLineInfo()	{
		AudioFormat fmt = getAudioFormat();
		//return new DataLine.Info(SourceDataLine.class, fmt, 4000);
		return new DataLine.Info(SourceDataLine.class, fmt);
	}

	public void open(AudioFormat fmt) throws JavaLayerException	{
		if (!isOpen()) {
			setAudioFormat(fmt);
			openImpl();
			setOpen(true);
		}
	}

	protected void openImpl() throws JavaLayerException	{

	}


	// createSource fix.
	protected void createSource() throws JavaLayerException	{
		Throwable t = null;
		try {
			Line line = AudioSystem.getLine(getSourceLineInfo());
			if (line instanceof SourceDataLine) {
				source = (SourceDataLine)line;
				//source.open(fmt, millisecondsToBytes(fmt, 2000));
				source.open(fmt);

				// Set initial volume
				setLineGain(Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RADIO));

				source.start();

			}
		} catch (RuntimeException | LinkageError | LineUnavailableException ex)	{
			t = ex;
		}
		if (source == null)
			throw new JavaLayerException("cannot obtain source audio line", t);
	}

	public int millisecondsToBytes(AudioFormat fmt, int time) {
		return (int)(time*(fmt.getSampleRate()*fmt.getChannels()*fmt.getSampleSizeInBits())/8000.0);
	}

	protected void closeImpl() {
		if (source != null) {
			source.close();
		}
	}

	protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
		if (source == null)
			createSource();

		byte[] b = toByteArray(samples, offs, len);
		source.write(b, 0, len*2);
	}

	protected byte[] getByteArray(int length) {
		if (byteBuf.length < length) {
			byteBuf = new byte[length+1024];
		}
		return byteBuf;
	}

	protected byte[] toByteArray(short[] samples, int offs, int len) {
		byte[] b = getByteArray(len*2);
		int idx = 0;
		short s;
		while (len-- > 0) {
			s = samples[offs++];
			b[idx++] = (byte)s;
			b[idx++] = (byte)(s >>> 8);
		}
		return b;
	}

	protected void flushImpl() {
		if (source != null) {
			source.drain();
		}
	}

	public int getPosition() {
		int pos = 0;
		if (source != null) {
			pos = (int)(source.getMicrosecondPosition()/1000);
		}
		return pos;
	}

	/**
	 * Runs a short test by playing a short silent sound.
	 */
	public void test() throws JavaLayerException {
		try	{
			open(new AudioFormat(22050, 16, 1, true, false));
			short[] data = new short[22050/10];
			write(data, 0, data.length);
			flush();
			close();
		} catch (RuntimeException ex) {
			throw new JavaLayerException("Device test failed: "+ex);
		}
	}

	/**
	 * Retrieve the Master Gain truncated in a range from 0.0 to 1.0
	 *
	 * @return the Master Gain
	 * @throws JavaLayerException
	 */
	public float getLineGain() throws JavaLayerException {
		if (source == null)
			throw new JavaLayerException("No LineSource set!");
		if (!source.isControlSupported(FloatControl.Type.MASTER_GAIN))
			throw new JavaLayerException("Master Gain fload control is not suported !");
		FloatControl c = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
		return ((c.getValue() - c.getMinimum()) / (c.getMaximum() - c.getMinimum())) * 2.0F - 1.0F;
	}

	/**
	 * Set the Master Gain in a range from 0.0 to 1.0
	 *
	 * @param gain the Master Gain to set
	 * @return weither th operation failed or not
	 * @throws IllegalArgumentException
	 */
	public boolean setLineGain(float gain) throws IllegalArgumentException {
		if (source != null && source.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl c = (FloatControl)source.getControl(FloatControl.Type.MASTER_GAIN);
			c.setValue((gain + 1.0F) / 2.0F * (c.getMaximum() - c.getMinimum()) + c.getMinimum());
			return true;
		}
		return false;
	}
}
