package bspkrs.helpers.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldHelper {
   public static boolean isAirBlock(World world, int x, int y, int z) {
      return world.isAirBlock(x, y, z);
   }

   public static boolean isBlockNormalCube(World world, int x, int y, int z, boolean defaultVal) {
      return world.isBlockNormalCubeDefault(x, y, z, defaultVal);
   }

   public static boolean isBlockOpaqueCube(World world, int x, int y, int z) {
      return getBlock(world, x, y, z).isOpaqueCube();
   }

   public static Block getBlock(World world, int x, int y, int z) {
      return world.getBlock(x, y, z);
   }

   public static TileEntity getBlockTileEntity(World world, int x, int y, int z) {
      return world.getTileEntity(x, y, z);
   }

   public static void removeBlockTileEntity(World world, int x, int y, int z) {
      world.removeTileEntity(x, y, z);
   }

   public static boolean setBlock(World world, int x, int y, int z, Block block, int metadata, int notifyFlag) {
      return world.setBlock(x, y, z, block, metadata, notifyFlag);
   }

   public static boolean setBlockToAir(World world, int x, int y, int z) {
      return world.setBlockToAir(x, y, z);
   }
}
