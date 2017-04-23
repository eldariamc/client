package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;
import java.util.Random;

public class BlockStone extends Block
{
    public static final String[] TYPES = new String[]{"stone", "stone_granite", "stone_granite_smooth", "stone_diorite", "stone_diorite_smooth", "stone_andesite", "stone_andesite_smooth"};
    public static final String[] NAMES = new String[]{"stone", "granite", "graniteSmooth", "diorite", "dioriteSmooth", "andesite", "andesiteSmooth"};
    private IIcon[] icons;
    private static final String __OBFID = "CL_00000317";

    public BlockStone()
    {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public Item getItemDropped(int meta, Random random, int fortune)
    {
        return meta == 0 ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(Blocks.stone);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.icons.length)
        {
            meta = 0;
        }

        return this.icons[meta];
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
    {
        for (int i = 0; i < TYPES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.icons = new IIcon[TYPES.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon(TYPES[i]);
        }
    }

    @Override
    public MapColor getMapColor(int meta) {
        switch (meta) {
            case 0:
            case 5:
            case 6:
                return MapColor.field_151665_m;
            case 1:
            case 2:
                return MapColor.field_151664_l;
            case 3:
            case 4:
                return MapColor.field_151677_p;
        }
        return super.getMapColor(meta);
    }
}
