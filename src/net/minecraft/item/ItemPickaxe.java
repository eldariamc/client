package net.minecraft.item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ItemPickaxe extends ItemTool
{
    public static final Set<Block> PROPER_BLOCKS = Sets.newHashSet(Blocks.cobblestone, Blocks.double_stone_slab,
            Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore,
            Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore,
            Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block,
            Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail,
            Blocks.activator_rail, Blocks.zinc_ore, Blocks.cronyxe_ore, Blocks.kobalt_ore, Blocks.eldarium_ore,
            Blocks.gemme_ore);

    protected ItemPickaxe(Item.ToolMaterial p_i45347_1_)
    {
        super(2.0F, p_i45347_1_, PROPER_BLOCKS);
    }

    public boolean func_150897_b(Block block)
    {
        return canPickaxe(block);
    }

    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return p_150893_2_.getMaterial() != Material.iron && p_150893_2_.getMaterial() != Material.anvil && p_150893_2_.getMaterial() != Material.rock ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
    }
}
