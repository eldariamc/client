package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOre extends Block
{

    public BlockOre()
    {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        if (this == Blocks.coal_ore)
            return Items.coal;
        if (this == Blocks.diamond_ore)
            return Items.diamond;
        if (this == Blocks.lapis_ore)
            return Items.dye;
        if (this == Blocks.emerald_ore)
            return Items.emerald;
        if (this == Blocks.quartz_ore)
            return Items.quartz;
        if (this == Blocks.lignite_ore)
            return Items.lignite;
        if (this == Blocks.eldarium_ore)
            return Items.eldarium_nugget;

        return super.getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand)
    {
        if (this == Blocks.lapis_ore)
            return 4 + rand.nextInt(5);
        /*if (this == Blocks.cronyxe_ore)
            return 1 + rand.nextInt(2);
        if (this == Blocks.kobalt_ore)
            return 1 + rand.nextInt(3);*/
        if (this == Blocks.xp_ore)
            return 0;

        return super.quantityDropped(rand);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int bonus, Random rand)
    {
        if (bonus > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, rand, bonus))
        {
            int i = rand.nextInt(bonus + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return (this == Blocks.eldarium_ore ? 1 : this.quantityDropped(rand)) * (i + 1); // Eldaria - eldarium ore only drop with fortune
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);

        if (this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_) != Item.getItemFromBlock(this))
        {
            int var8 = 0;

            if (this == Blocks.coal_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 0, 2);
            }
            else if (this == Blocks.diamond_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
            }
            else if (this == Blocks.emerald_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
            }
            else if (this == Blocks.lapis_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 2, 5);
            }
            else if (this == Blocks.quartz_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 2, 5);
            }
            else if (this == Blocks.lignite_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 0, 2);
            }
            else if (this == Blocks.cronyxe_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
            }
            else if (this == Blocks.kobalt_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
            }
            else if (this == Blocks.eldarium_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 3, 7);
            }
            else if (this == Blocks.xp_ore)
            {
                var8 = MathHelper.getRandomIntegerInRange(p_149690_1_.rand, 7, 12);
            }

            this.dropXpOnBlockBreak(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int p_149692_1_)
    {
        return this == Blocks.lapis_ore ? 4 : 0;
    }
}
