package fr.dabsunter.mcp.network.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.dabsunter.mcp.network.CustomPacket;
import net.minecraft.client.Minecraft;

import java.util.UUID;

/**
 * Created by David on 04/04/2017.
 */
public class AuthPacket extends CustomPacket {
	private String username;
	private UUID uuid;
	private String accessToken;

	public AuthPacket(String username, UUID uuid, String accessToken) {
		super(0);
		this.username = username;
		this.uuid = uuid;
		this.accessToken = accessToken;
	}

	@Override
	public void read(ByteArrayDataInput in) {
		this.username = in.readUTF();
		this.uuid = new UUID(
				in.readLong(),
				in.readLong()
		);
		this.accessToken = in.readUTF();
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeUTF(username);
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		out.writeUTF(accessToken);
	}

	@Override
	public void process(Minecraft mc) {}
}
