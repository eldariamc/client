package bspkrs.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class Coord {
   public int x;
   public int y;
   public int z;

   public Coord(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Coord clone() {
      return new Coord(this.x, this.y, this.z);
   }

   public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      } else if(!(obj instanceof Coord)) {
         return false;
      } else {
         Coord o = (Coord)obj;
         return this.x == o.x && this.y == o.y && this.z == o.z;
      }
   }

   public int hashCode() {
      return this.x + this.z << 8 + this.y << 16;
   }

   public Coord add(Coord pos) {
      return new Coord(this.x + pos.x, this.y + pos.y, this.z + pos.z);
   }

   public Coord add(int[] ai) {
      return new Coord(this.x + ai[0], this.y + ai[1], this.z + ai[2]);
   }

   public Coord substract(Coord pos) {
      return new Coord(this.x - pos.x, this.y - pos.y, this.z - pos.z);
   }

   public Coord substract(int[] ai) {
      return new Coord(this.x - ai[0], this.y - ai[1], this.z - ai[2]);
   }

   /*public Coord getAdjacentCoord(ForgeDirection fd) {
      return this.getOffsetCoord(fd, 1);
   }

   public Coord getOffsetCoord(ForgeDirection fd, int distance) {
      return new Coord(this.x + fd.offsetX * distance, this.y + fd.offsetY * distance, this.z + fd.offsetZ * distance);
   }*/

   /*public Coord[] getDirectlyAdjacentCoords() {
      return this.getDirectlyAdjacentCoords(true);
   }

   public Coord[] getDirectlyAdjacentCoords(boolean includeBelow) {
      Coord[] adjacents;
      if(includeBelow) {
         adjacents = new Coord[6];
      } else {
         adjacents = new Coord[5];
      }

      adjacents[0] = this.getAdjacentCoord(ForgeDirection.UP);
      adjacents[1] = this.getAdjacentCoord(ForgeDirection.NORTH);
      adjacents[2] = this.getAdjacentCoord(ForgeDirection.EAST);
      adjacents[3] = this.getAdjacentCoord(ForgeDirection.SOUTH);
      adjacents[4] = this.getAdjacentCoord(ForgeDirection.WEST);
      if(includeBelow) {
         adjacents[5] = this.getAdjacentCoord(ForgeDirection.DOWN);
      }

      return adjacents;
   }

   public Coord[] getAdjacentCoords() {
      return this.getAdjacentCoords(true, true);
   }

   public Coord[] getAdjacentCoords(boolean includeBelow, boolean includeDiagonal) {
      if(!includeDiagonal) {
         return this.getDirectlyAdjacentCoords(includeBelow);
      } else {
         Coord[] adjacents = new Coord[includeBelow?26:17];
         int index = 0;

         for(int xl = -1; xl < 1; ++xl) {
            for(int zl = -1; zl < 1; ++zl) {
               for(int yl = includeBelow?-1:0; yl < 1; ++yl) {
                  if(xl != 0 || zl != 0 || yl != 0) {
                     adjacents[index++] = new Coord(this.x + xl, this.y + yl, this.z + zl);
                  }
               }
            }
         }

         return adjacents;
      }
   }*/

   public int get3DDistance(Coord pos) {
      return (int)Math.round(Math.sqrt(Math.pow((double)(pos.x - this.x), 2.0D) + Math.pow((double)(pos.z - this.z), 2.0D) + Math.pow((double)(pos.y - this.y), 2.0D)));
   }

   public int getCubicDistance(Coord pos) {
      return Math.abs(pos.x - this.x) + Math.abs(pos.y - this.y) + Math.abs(pos.z - this.z);
   }

   public int getSquaredDistance(Coord pos) {
      return Math.abs(pos.x - this.x) + Math.abs(pos.z - this.z);
   }

   public int getVerDistance(Coord pos) {
      return Math.abs(pos.y - this.y);
   }

   public boolean isAbove(Coord pos) {
      return pos != null?this.y > pos.y:false;
   }

   public boolean isBelow(Coord pos) {
      return pos != null?this.y < pos.y:false;
   }

   public boolean isNorthOf(Coord pos) {
      return pos != null?this.z < pos.z:false;
   }

   public boolean isSouthOf(Coord pos) {
      return pos != null?this.z > pos.z:false;
   }

   public boolean isEastOf(Coord pos) {
      return pos != null?this.x > pos.x:false;
   }

   public boolean isWestOf(Coord pos) {
      return pos != null?this.x < pos.x:false;
   }

   public boolean isXAligned(Coord pos) {
      return pos != null?this.x == pos.x:false;
   }

   public boolean isYAligned(Coord pos) {
      return pos != null?this.y == pos.y:false;
   }

   public boolean isZAligned(Coord pos) {
      return pos != null?this.z == pos.z:false;
   }

   public boolean isAirBlock(World world) {
      return world.isAirBlock(this.x, this.y, this.z);
   }

   public boolean chunkExists(World world) {
      return world.checkChunksExist(this.x, this.y, this.z, this.x, this.y, this.z);
   }

   public boolean isBlockNormalCube(World world) {
      return world.isBlockNormalCubeDefault(this.x, this.y, this.z, false);
   }

   public boolean isBlockOpaqueCube(World world) {
      return this.getBlock(world).isOpaqueCube();
   }

   /*public boolean isWood(World world) {
      return world.getBlock(this.x, this.y, this.z).isWood(world, this.x, this.y, this.z);
   }

   public boolean isLeaves(World world) {
      return world.getBlock(this.x, this.y, this.z).isLeaves(world, this.x, this.y, this.z);
   }*/

   public Block getBlock(World world) {
      return world.getBlock(this.x, this.y, this.z);
   }

   public int getBlockMetadata(World world) {
      return world.getBlockMetadata(this.x, this.y, this.z);
   }

   public BiomeGenBase getBiomeGenBase(World world) {
      return world.getBiomeGenForCoords(this.x, this.z);
   }

   public static boolean moveBlock(World world, Coord src, Coord tgt, boolean allowBlockReplacement) {
      return moveBlock(world, src, tgt, allowBlockReplacement, 3);
   }

   public static boolean moveBlock(World world, Coord src, Coord tgt, boolean allowBlockReplacement, int notifyFlag) {
      if(!world.isClient && !src.isAirBlock(world) && (tgt.isAirBlock(world) || allowBlockReplacement)) {
         Block blockID = src.getBlock(world);
         int metadata = src.getBlockMetadata(world);
         world.setBlock(tgt.x, tgt.y, tgt.z, blockID, metadata, notifyFlag);
         TileEntity te = world.getTileEntity(src.x, src.y, src.z);
         if(te != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            te.writeToNBT(nbt);
            nbt.setInteger("x", tgt.x);
            nbt.setInteger("y", tgt.y);
            nbt.setInteger("z", tgt.z);
            te = world.getTileEntity(tgt.x, tgt.y, tgt.z);
            if(te != null) {
               te.readFromNBT(nbt);
            }

            world.removeTileEntity(src.x, src.y, src.z);
         }

         world.setBlockToAir(src.x, src.y, src.z);
         return true;
      } else {
         return false;
      }
   }

   public boolean moveBlockToHereFrom(World world, Coord src, boolean allowBlockReplacement) {
      return moveBlock(world, src, this, allowBlockReplacement);
   }

   public boolean moveBlockFromHereTo(World world, Coord tgt, boolean allowBlockReplacement) {
      return moveBlock(world, this, tgt, allowBlockReplacement);
   }

   public String toString() {
      return this.x + "," + this.y + "," + this.z;
   }
}
