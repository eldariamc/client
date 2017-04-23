package bspkrs.helpers.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockHelper {
   public static Material getBlockMaterial(Block block) {
      return block.getMaterial();
   }

   public static String getUniqueID(Block block) {
      return Block.blockRegistry.getNameForObject(block);
   }

   public static Block getBlock(String uniqueID) {
      return (Block)Block.blockRegistry.getObject(uniqueID);
   }

   public static int damageDropped(Block block, int metadata) {
      return block.damageDropped(metadata);
   }

   public static void dropBlockAsItem(Block block, World world, int x, int y, int z, int metadata, int fortuneLevel) {
      block.dropBlockAsItem(world, x, y, z, metadata, fortuneLevel);
   }
}
