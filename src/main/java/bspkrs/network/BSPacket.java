package bspkrs.network;

import net.minecraft.network.PacketBuffer;

public interface BSPacket {
   void readBytes(PacketBuffer var1);

   void writeBytes(PacketBuffer var1);
}
