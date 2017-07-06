package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Set;

public class ItemTool extends Item
{
    private Set properBlocks;
    protected float efficiencyOnProperMaterial = 4.0F;

    /** Damage versus entities. */
    private float damageVsEntity;

    /** The material this tool is made from. */
    protected Item.ToolMaterial toolMaterial;

    protected ItemTool(float p_i45333_1_, Item.ToolMaterial p_i45333_2_, Set p_i45333_3_)
    {
        this.toolMaterial = p_i45333_2_;
        this.properBlocks = p_i45333_3_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45333_2_.getMaxUses());
        this.efficiencyOnProperMaterial = p_i45333_2_.getEfficiencyOnProperMaterial();
        this.damageVsEntity = p_i45333_1_ + p_i45333_2_.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return this.properBlocks.contains(p_150893_2_) ? this.efficiencyOnProperMaterial : 1.0F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        p_77644_1_.damageItem(2, p_77644_3_);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
        {
            p_150894_1_.damageItem(1, p_150894_7_);
        }

        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    public Item.ToolMaterial func_150913_i()
    {
        return this.toolMaterial;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
        return this.toolMaterial.func_150995_f() == p_82789_2_.getItem() ? true : super.getIsRepairable(p_82789_1_, p_82789_2_);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        Multimap var1 = super.getItemAttributeModifiers();
        var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return var1;
    }

    protected boolean canPickaxe(Block block) {
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
        if (block == Blocks.eldarium_ore)
            return this.toolMaterial.getHarvestLevel() >= 3;
        if (block.getMaterial() == Material.rock || block.getMaterial() == Material.brick)
            return true;
        if (block.getMaterial() == Material.iron)
            return true;
        if (block == Blocks.anvil)
            return this.toolMaterial.getHarvestLevel() >= 1;

        return false;
    }
}
