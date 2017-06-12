package net.minecraft.item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ItemSpade extends ItemTool
{
    public static final Set<Block> PROPER_BLOCKS = Sets.newHashSet(Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel,
            Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium);

    public ItemSpade(Item.ToolMaterial p_i45353_1_)
    {
        super(1.0F, p_i45353_1_, PROPER_BLOCKS);
    }

    public boolean func_150897_b(Block p_150897_1_)
    {
        return p_150897_1_ == Blocks.snow_layer || p_150897_1_ == Blocks.snow;
    }
}
