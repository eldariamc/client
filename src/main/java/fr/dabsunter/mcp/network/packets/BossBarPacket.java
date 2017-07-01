package fr.dabsunter.mcp.network.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.dabsunter.mcp.network.CustomPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.boss.BossStatus;

/**
 * Created by David on 01/07/2017.
 */
public class BossBarPacket extends CustomPacket {

	private String message;
	private int duration;
	private float progress;

	public BossBarPacket() {
		super(3);
	}

	public BossBarPacket(String message, int duration, float progress) {
		this();

		this.message = message;
		this.duration = duration;
		this.progress = progress;
	}

	@Override
	public void read(ByteArrayDataInput in) {
		message = in.readUTF();
		duration = in.readUnsignedShort();
		progress = in.readFloat();
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeUTF(message);
		out.writeShort(duration);
		out.writeFloat(progress);
	}

	@Override
	public void process(Minecraft mc) {
		BossStatus.setBossStatus(message, duration, progress);
	}
}
