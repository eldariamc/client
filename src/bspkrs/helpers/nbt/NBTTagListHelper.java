package bspkrs.helpers.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTTagListHelper {
   public static NBTTagCompound getCompoundTagAt(NBTTagList ntl, int i) {
      return ntl.getCompoundTagAt(i);
   }

   public static String getStringTagAt(NBTTagList ntl, int i) {
      return ntl.getStringTagAt(i);
   }
}
