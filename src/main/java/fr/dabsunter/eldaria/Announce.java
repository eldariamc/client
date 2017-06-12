package fr.dabsunter.eldaria;

import fr.dabsunter.mcp.network.packets.AnnouncePacket;

/**
 * Created by David on 09/04/2017.
 */
public class Announce {

	public final String message;
	public int duration;

	public Announce(AnnouncePacket packet) {
		this.message = packet.getMessage();
		this.duration = packet.getDuration();
	}
}
