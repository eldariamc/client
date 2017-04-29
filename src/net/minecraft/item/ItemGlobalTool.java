package net.minecraft.item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;

/**
 * Created by David on 29/04/2017.
 */
public class ItemGlobalTool extends ItemTool {
	public ItemGlobalTool(ToolMaterial material) {
		super(3.0F, material, getProperBlocks());
	}

	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
	{
		return p_150893_2_.getMaterial() != Material.wood && p_150893_2_.getMaterial() != Material.plants
				&& p_150893_2_.getMaterial() != Material.vine && p_150893_2_.getMaterial() != Material.iron
				&& p_150893_2_.getMaterial() != Material.anvil && p_150893_2_.getMaterial() != Material.rock
				? super.func_150893_a(p_150893_1_, p_150893_2_)
				: this.efficiencyOnProperMaterial;
	}

	public boolean func_150897_b(Block block)
	{
		return block == Blocks.snow_layer || block == Blocks.snow || canPickaxe(block);
	}

	private static Set<Block> getProperBlocks() {
		Set<Block> properBlocks = Sets.newHashSet();
		properBlocks.addAll(ItemAxe.PROPER_BLOCKS);
		properBlocks.addAll(ItemPickaxe.PROPER_BLOCKS);
		properBlocks.addAll(ItemSpade.PROPER_BLOCKS);
		return properBlocks;
	}
}
