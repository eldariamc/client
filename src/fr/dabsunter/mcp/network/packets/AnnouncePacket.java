package fr.dabsunter.mcp.network.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.dabsunter.eldaria.Announce;
import fr.dabsunter.mcp.network.CustomPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

/**
 * Created by David on 09/04/2017.
 */
public class AnnouncePacket extends CustomPacket {
	private String message;
	private int duration;

	public AnnouncePacket() {
		super(1);
	}

	public AnnouncePacket(String message, int duration) {
		this();

		this.message = message;
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public int getDuration() {
		return duration;
	}

	@Override
	public void read(ByteArrayDataInput in) {
		this.message = in.readUTF();
		this.duration = in.readUnsignedShort();
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeUTF(message);
		out.writeShort(duration);
	}

	@Override
	public void process(Minecraft mc) {
		GuiIngame ing = mc.ingameGUI;
		if (ing != null)
			ing.currentAnnounce = new Announce(this);
	}
}
