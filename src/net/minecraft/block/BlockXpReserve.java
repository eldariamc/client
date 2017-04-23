package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityXpReserve;
import net.minecraft.util.XpUtil;
import net.minecraft.world.World;

/**
 * Created by David on 11/08/2016.
 */
public class BlockXpReserve extends BlockContainer {

	protected BlockXpReserve() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (p_149727_1_.isClient)
		{
			return true;
		}
		else
		{
			TileEntityXpReserve var10 = (TileEntityXpReserve)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);

			if (var10 != null)
			{
				//p_149727_5_.displayGUIXpReserve(var10);
			}

			return true;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		TileEntityXpReserve tile = (TileEntityXpReserve)world.getTileEntity(x, y, z);
		int xp = XpUtil.getExperienceForLevel(tile.experience);
		while (xp > 0)
		{
			int split = EntityXPOrb.getXPSplit(xp);
			xp -= split;
			world.spawnEntityInWorld(new EntityXPOrb(world, x, y, z, split));
		}
	}
}
