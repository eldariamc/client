package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by David on 05/04/2017.
 */
public class BlockBarrier extends Block {

		protected BlockBarrier()
		{
			super(Material.barrier);
			this.setBlockUnbreakable();
			this.setResistance(6000001.0F);
			this.disableStats();
			this.setLightOpacity(0);
			//this.translucent = true;
		}

		/**
		 * The type of render function that is called for this block
		 */
		public int getRenderType()
		{
			return -1;
		}

		/**
		 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
		 * cleared to be reused)
		 */
		public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
		{
			return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
		}

		public boolean isOpaqueCube()
		{
			return false;
		}

		/**
		 * Drops the block items with a specified chance of dropping the specified items
		 */
		public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {}
}
