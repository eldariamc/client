package bspkrs.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class ModulusBlockID extends BlockID {
   public final int metadataModulus;

   public ModulusBlockID(String id, int metadata, int metadataModulus) {
      super(id, Math.max(metadata, 0) % Math.max(metadataModulus, 1));
      this.metadataModulus = Math.max(metadataModulus, 1);
   }

   public ModulusBlockID(Block block, int metadata, int metadataModulus) {
      super(block, Math.max(metadata, 0) % Math.max(metadataModulus, 1));
      this.metadataModulus = Math.max(metadataModulus, 1);
   }

   public ModulusBlockID(World world, int x, int y, int z, int metadataModulus) {
      this(world, x, y, z, world.getBlockMetadata(x, y, z), metadataModulus);
   }

   public ModulusBlockID(World world, int x, int y, int z, int metadata, int metadataModulus) {
      this(world.getBlock(x, y, z), Math.max(metadata, 0), metadataModulus);
   }

   public BlockID clone() {
      return new ModulusBlockID(this.id, this.metadata, this.metadataModulus);
   }

   public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      } else if(!(obj instanceof BlockID)) {
         return false;
      } else if(((BlockID)obj).id != null && !((BlockID)obj).id.equals(this.id)) {
         return false;
      } else if(((BlockID)obj).id == null && this.id != null) {
         return false;
      } else if(obj instanceof ModulusBlockID) {
         ModulusBlockID o = (ModulusBlockID)obj;
         return this.metadata % this.metadataModulus == o.metadata % o.metadataModulus;
      } else {
         BlockID o = (BlockID)obj;
         return o.metadata == -1?true:this.metadata % this.metadataModulus == o.metadata % this.metadataModulus;
      }
   }

   public String toString() {
      return this.id + ", " + this.metadata + " % " + this.metadataModulus;
   }
}
