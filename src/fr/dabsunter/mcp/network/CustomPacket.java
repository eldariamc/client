package fr.dabsunter.mcp.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

/**
 * Created by David on 04/04/2017.
 */
public abstract class CustomPacket {
	final byte id;

	protected CustomPacket(int id) {
		this.id = (byte) (id > 127 ? 127 - id : id);
	}

	public abstract void read(ByteArrayDataInput in);

	public abstract void write(ByteArrayDataOutput out);

	public abstract void process();

}
