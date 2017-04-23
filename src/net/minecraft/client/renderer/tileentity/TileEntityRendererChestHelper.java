package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

public class TileEntityRendererChestHelper
{
    public static TileEntityRendererChestHelper instance = new TileEntityRendererChestHelper();
    private TileEntityChest normal = new TileEntityChest(0, Blocks.chest);
    private TileEntityChest trapped = new TileEntityChest(1, Blocks.trapped_chest);
    private TileEntityChest mystery = new TileEntityChest(0, Blocks.mystery_box);
    private TileEntityChest magic = new TileEntityChest(0, Blocks.magic_chest);
    private TileEntityChest zinc = new TileEntityChest(0, Blocks.zinc_chest);
    private TileEntityChest cronyxe = new TileEntityChest(0, Blocks.cronyxe_chest);
    private TileEntityChest kobalt = new TileEntityChest(0, Blocks.kobalt_chest);
    private TileEntityEnderChest ender = new TileEntityEnderChest();
    private static final String __OBFID = "CL_00000946";

    public void func_147715_a(Block block, int p_147715_2_, float p_147715_3_)
    {
        if (block == Blocks.ender_chest)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.ender, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block == Blocks.trapped_chest)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.trapped, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block == Blocks.mystery_box)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.mystery, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block == Blocks.magic_chest)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.magic, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block == Blocks.zinc_chest)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.zinc, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block == Blocks.cronyxe_chest)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.cronyxe, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block == Blocks.kobalt_chest)
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.kobalt, 0.0, 0.0, 0.0, 0.0F);
        }
        else
        {
            TileEntityRendererDispatcher.instance.func_147549_a(this.normal, 0.0D, 0.0D, 0.0D, 0.0F);
        }
    }
}
