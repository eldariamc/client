package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSeaLantern extends Block
{
	public BlockSeaLantern(Material materialIn)
	{
		super(materialIn);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(int meta)
	{
		return MapColor.field_151677_p;
	}

	protected boolean canSilkHarvest()
	{
		return true;
	}
}
