package net.minecraft.item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ItemPickaxe extends ItemTool
{
    private static final Set field_150915_c = Sets.newHashSet(Blocks.cobblestone, Blocks.double_stone_slab,
            Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore,
            Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore,
            Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block,
            Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail,
            Blocks.activator_rail, Blocks.zinc_ore, Blocks.cronyxe_ore, Blocks.kobalt_ore, Blocks.eldarium_ore,
            Blocks.gemme_ore);
    private static final String __OBFID = "CL_00000053";

    protected ItemPickaxe(Item.ToolMaterial p_i45347_1_)
    {
        super(2.0F, p_i45347_1_, field_150915_c);
    }

    public boolean func_150897_b(Block block)
    {
        if (block == Blocks.obsidian || block == Blocks.obsidian_furnace || block == Blocks.lit_obsidian_furnace)
            return this.toolMaterial.getHarvestLevel() >= 3;
        if (block == Blocks.diamond_block || block == Blocks.diamond_ore)
            return this.toolMaterial.getHarvestLevel() >= 2;
        if (block == Blocks.emerald_ore || block == Blocks.emerald_block)
            return this.toolMaterial.getHarvestLevel() >= 2;
        if (block == Blocks.gold_block || block == Blocks.gold_ore)
            return this.toolMaterial.getHarvestLevel() >= 2;
        if (block == Blocks.iron_block || block == Blocks.iron_ore)
            return this.toolMaterial.getHarvestLevel() >= 1;
        if (block == Blocks.lapis_block || block == Blocks.lapis_ore)
            return this.toolMaterial.getHarvestLevel() >= 1;
        if (block == Blocks.redstone_ore || block == Blocks.lit_redstone_ore)
            return this.toolMaterial.getHarvestLevel() >= 2;
        if (block == Blocks.zinc_ore || block == Blocks.cronyxe_ore || block == Blocks.kobalt_ore)
            return this.toolMaterial.getHarvestLevel() >= 3;
        if (block == Blocks.eldarium_ore || block == Blocks.gemme_ore)
            return this.toolMaterial.getHarvestLevel() >= 3;
        if (block.getMaterial() == Material.rock || block.getMaterial() == Material.brick)
            return true;
        if (block.getMaterial() == Material.iron)
            return true;
        if (block == Blocks.anvil)
            return this.toolMaterial.getHarvestLevel() >= 1;

        return false;
    }

    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return p_150893_2_.getMaterial() != Material.iron && p_150893_2_.getMaterial() != Material.anvil && p_150893_2_.getMaterial() != Material.rock ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
    }
}
