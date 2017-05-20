package fr.dabsunter.mcp.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.dabsunter.mcp.network.packets.ActionBarPacket;
import fr.dabsunter.mcp.network.packets.AnnouncePacket;
import fr.dabsunter.mcp.network.packets.AuthPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

/**
 * Created by David on 04/04/2017.
 */
public class CustomPacketHandler {
	private static final String CHANNEL = "EldariaClient";
	private static final Class<? extends CustomPacket>[] PACKETS = new Class[]{
			AuthPacket.class,
			AnnouncePacket.class,
			ActionBarPacket.class
	};

	public static void handle(S3FPacketCustomPayload payload) {
		try {
			if (payload.func_149169_c().equals(CHANNEL)) {
				System.out.println("Received packet !");
				ByteArrayDataInput in = ByteStreams.newDataInput(payload.func_149168_d());
				CustomPacket packet = PACKETS[in.readUnsignedByte()].newInstance();
				packet.read(in);
				packet.process(Minecraft.getMinecraft());
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void dispatch(CustomPacket packet) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeByte(packet.id);
		packet.write(out);
		Minecraft.getMinecraft().getNetHandler().getNetworkManager().scheduleOutboundPacket(
				new C17PacketCustomPayload(CHANNEL, out.toByteArray())
		);
	}
}
