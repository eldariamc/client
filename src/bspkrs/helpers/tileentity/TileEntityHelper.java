package bspkrs.helpers.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHelper {
   public static void readFromNBT(TileEntity te, NBTTagCompound ntc) {
      te.readFromNBT(ntc);
   }

   public static void writeToNBT(TileEntity te, NBTTagCompound ntc) {
      te.writeToNBT(ntc);
   }
}
