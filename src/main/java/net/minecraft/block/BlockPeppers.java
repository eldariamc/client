package net.minecraft.block;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by David on 08/05/2017.
 */
public class BlockPeppers extends BlockCrops {

	protected Item getSeeds()
	{
		return Items.pepper;
	}

	protected Item getProduct()
	{
		return Items.pepper;
	}
}
