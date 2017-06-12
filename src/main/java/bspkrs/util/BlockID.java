package bspkrs.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockID {
   public final String id;
   public final int metadata;

   public BlockID(String id, int metadata) {
      this.id = id;
      this.metadata = metadata;
   }

   public BlockID(String block) {
      this((String)block, -1);
   }

   public BlockID(Block block, int metadata) {
      this(Block.blockRegistry.getNameForObject(block), metadata);
   }

   public BlockID(Block block) {
      this(Block.blockRegistry.getNameForObject(block), -1);
   }

   /** @deprecated */
   @Deprecated
   public BlockID(String format, String delimiter) {
      int comma = format.indexOf(",");
      if(comma != -1) {
         this.id = format.substring(0, comma).trim();
      } else {
         this.id = format.trim();
      }

      this.metadata = CommonUtils.parseInt(format.substring(comma + 1, format.length()).trim(), -1);
   }

   public BlockID(World world, int x, int y, int z) {
      this(world, x, y, z, world.getBlockMetadata(x, y, z));
   }

   public BlockID(World world, int x, int y, int z, int metadata) {
      this(world.getBlock(x, y, z), metadata);
   }

   public boolean isValid() {
      return this.getBlock() != null;
   }

   public Block getBlock() {
      return (Block)Block.blockRegistry.getObject(this.id);
   }

   public static BlockID parse(String format) {
      int metadataModulus = 0;
      format = format.trim();
      int comma = format.indexOf(",");
      int tilde = format.indexOf("~");
      if(tilde == -1) {
         tilde = format.indexOf("%");
      }

      if(comma == -1 && tilde != -1) {
         throw new RuntimeException(String.format("ModulusBlockID format error: a \"~\" or \"%1$s\" was found, but no \",\" in format \"%2$s\". Expected format is \"<blockidstring>, <integer metadata> %1$s <integer modulus>\". EG: \"minecraft:log, 0 %1$s 4\".", new Object[]{"%", format}));
      } else if(tilde != -1 && comma > tilde) {
         throw new RuntimeException(String.format("ModulusBlockID format error: a \"~\" or \"%1$s\" was found before a \",\" in format \"%2$s\". Expected format is \"<blockidstring>, <integer metadata> %1$s <integer modulus>\". EG: \"minecraft:log, 0 %1$s 4\".", new Object[]{"%", format}));
      } else {
         if(tilde == -1) {
            tilde = format.length();
         }

         String id;
         if(comma != -1) {
            id = format.substring(0, comma).trim();
         } else {
            id = format.trim();
         }

         int metadata = CommonUtils.parseInt(format.substring(comma + 1, tilde).trim(), -1);
         if(tilde != format.length()) {
            metadataModulus = CommonUtils.parseInt(format.substring(tilde + 1, format.length()).trim(), 0);
         }

         return (BlockID)(metadata != -1 && metadataModulus > 0?new ModulusBlockID(id, metadata, metadataModulus):new BlockID(id, metadata));
      }
   }

   public BlockID clone() {
      return new BlockID(this.id, this.metadata);
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
         return this.metadata % o.metadataModulus == o.metadata % o.metadataModulus;
      } else {
         BlockID o = (BlockID)obj;
         return o.metadata != -1 && this.metadata != -1?this.metadata == o.metadata:true;
      }
   }

   public int hashCode() {
      return this.id.hashCode() * 37;
   }

   public String toString() {
      return this.metadata == -1?this.id + "":this.id + ", " + this.metadata;
   }
}
