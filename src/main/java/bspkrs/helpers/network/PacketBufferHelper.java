package bspkrs.helpers.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketBufferHelper {
   public static void writeNBTTagCompound(PacketBuffer data, NBTTagCompound ntc) throws IOException {
      data.writeNBTTagCompoundToBuffer(ntc);
   }

   public static NBTTagCompound readNBTTagCompound(PacketBuffer data) throws IOException {
      return data.readNBTTagCompoundFromBuffer();
   }
}
