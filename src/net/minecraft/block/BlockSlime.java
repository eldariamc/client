package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by David on 11/07/2016.
 */
public class BlockSlime extends BlockBreakable
{
	public BlockSlime()
	{
		super("slime", Material.clay, false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.slipperiness = 0.8F;
	}

	/*
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}
	*/

	/**
	 * Block's chance to react to a living entity falling on it.
	 */
	public void onFallenUpon(World worldIn, int x, int y, int z, Entity entityIn, float fallDistance)
	{
		if (entityIn.isSneaking())
		{
			super.onFallenUpon(worldIn, x, y, z, entityIn, fallDistance);
		}
		else
		{
			entityIn.fall(0.0F);
		}
	}

	/**
	 * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
	 * on its own
	 */
	public void onEntityWalking(World worldIn, int x, int y, int z, Entity entityIn)
	{
		if (entityIn.isSneaking())
		{
			super.onEntityWalking(worldIn, x, y, z, entityIn);
		}
		else if (entityIn.motionY < 0.0D)
		{
			entityIn.motionY = -entityIn.motionY;
		}
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block)
	 */
	public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z, Entity entityIn)
	{
		if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
		{
			double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
			entityIn.motionX *= d0;
			entityIn.motionZ *= d0;
		}

		super.onEntityCollidedWithBlock(worldIn, x, y, z, entityIn);
	}
}
