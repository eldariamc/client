package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFurnace extends BlockContainer
{
    private final Random field_149933_a = new Random();
    private final boolean field_149932_b;
    private final Block drop;
    private static boolean field_149934_M;
    private IIcon field_149935_N;
    private IIcon field_149936_O;
    private double furnaceSpeed = 1.0;
    private static final String __OBFID = "CL_00000248";

    protected BlockFurnace()
    {
        super(Material.rock);
        this.field_149932_b = false;
        this.drop = this;
    }

    protected BlockFurnace(Block drop)
    {
        super(Material.rock);
        this.field_149932_b = true;
        this.drop = drop;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(drop);
    }

    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149930_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    private void func_149930_e(World world, int x, int y, int z)
    {
        if (!world.isClient)
        {
            Block relative1 = world.getBlock(x, y, z - 1);
            Block relative2 = world.getBlock(x, y, z + 1);
            Block relative3 = world.getBlock(x - 1, y, z);
            Block relative4 = world.getBlock(x + 1, y, z);
            byte data = 3;

            if (relative1.isOpaque() && !relative2.isOpaque())
            {
                data = 3;
            }

            if (relative2.isOpaque() && !relative1.isOpaque())
            {
                data = 2;
            }

            if (relative3.isOpaque() && !relative4.isOpaque())
            {
                data = 5;
            }

            if (relative4.isOpaque() && !relative3.isOpaque())
            {
                data = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, data, 2);
        }
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.field_149935_N : (side == 0 ? this.field_149935_N : (side != meta ? this.blockIcon : this.field_149936_O));
    }

    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.textureName + "_side");
        this.field_149936_O = p_149651_1_.registerIcon(this.field_149932_b ? this.textureName +  "_front_on" : this.textureName + "_front_off");
        this.field_149935_N = p_149651_1_.registerIcon(this.textureName + "_top");
    }

    /**
     * A randomly called display update to be able to addSmelting particles or other items for display
     */
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        if (this.field_149932_b)
        {
            int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            float var7 = (float)p_149734_2_ + 0.5F;
            float var8 = (float)p_149734_3_ + 0.0F + p_149734_5_.nextFloat() * 6.0F / 16.0F;
            float var9 = (float)p_149734_4_ + 0.5F;
            float var10 = 0.52F;
            float var11 = p_149734_5_.nextFloat() * 0.6F - 0.3F;

            if (var6 == 4)
            {
                p_149734_1_.spawnParticle("smoke", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 5)
            {
                p_149734_1_.spawnParticle("smoke", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 2)
            {
                p_149734_1_.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 3)
            {
                p_149734_1_.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (p_149727_1_.isClient)
        {
            return true;
        }
        else
        {
            TileEntityFurnace var10 = (TileEntityFurnace)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);

            if (var10 != null)
            {
                p_149727_5_.displayGUIFurnace(var10);
            }

            return true;
        }
    }

    public void func_149931_a(boolean p_149931_0_, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
    {
        int var5 = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
        TileEntity var6 = p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_);
        field_149934_M = true;

        if (p_149931_0_)
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Block.getBlockFromName("lit_" + textureName));
        }
        else
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Block.getBlockFromName(textureName));
        }

        field_149934_M = false;
        p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, var5, 2);

        if (var6 != null)
        {
            var6.validate();
            p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, var6);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        TileEntityFurnace tef = new TileEntityFurnace();
        tef.furnaceSpeed = this.furnaceSpeed;
        return tef;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int var7 = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var7 == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }

        if (var7 == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }

        if (var7 == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }

        if (var7 == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
        }

        if (p_149689_6_.hasDisplayName())
        {
            ((TileEntityFurnace)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145951_a(p_149689_6_.getDisplayName());
        }
    }

    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!field_149934_M)
        {
            TileEntityFurnace var7 = (TileEntityFurnace)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (var7 != null)
            {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
                {
                    ItemStack var9 = var7.getStackInSlot(var8);

                    if (var9 != null)
                    {
                        float var10 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float var11 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float var12 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (var9.stackSize > 0)
                        {
                            int var13 = this.field_149933_a.nextInt(21) + 10;

                            if (var13 > var9.stackSize)
                            {
                                var13 = var9.stackSize;
                            }

                            var9.stackSize -= var13;
                            EntityItem var14 = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + var10), (double)((float)p_149749_3_ + var11), (double)((float)p_149749_4_ + var12), new ItemStack(var9.getItem(), var13, var9.getItemDamage()));

                            if (var9.hasTagCompound())
                            {
                                var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }

                            float var15 = 0.05F;
                            var14.motionX = (double)((float)this.field_149933_a.nextGaussian() * var15);
                            var14.motionY = (double)((float)this.field_149933_a.nextGaussian() * var15 + 0.2F);
                            var14.motionZ = (double)((float)this.field_149933_a.nextGaussian() * var15);
                            p_149749_1_.spawnEntityInWorld(var14);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
    {
        return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(drop);
    }

    public double getFurnaceSpeed() {
        return furnaceSpeed;
    }

    public BlockFurnace setFurnaceSpeed(double furnaceSpeed) {
        this.furnaceSpeed = furnaceSpeed;
        return this;
    }
}
