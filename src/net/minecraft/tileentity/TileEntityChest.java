package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] content;
    public boolean field_145984_a;
    public TileEntityChest field_145992_i;
    public TileEntityChest field_145990_j;
    public TileEntityChest field_145991_k;
    public TileEntityChest field_145988_l;
    public float field_145989_m;
    public float field_145986_n;
    public int field_145987_o;
    private int field_145983_q;
    private int field_145982_r;
    private String field_145981_s;
    private static final String __OBFID = "CL_00000346";

    public TileEntityChest()
    {
        this.field_145982_r = -1;
    }

    public TileEntityChest(int p_i2350_1_, int size)
    {
        this.field_145982_r = p_i2350_1_;
        content = new ItemStack[size];
    }

    public TileEntityChest(int p_i2350_1_, Block fakeType) { // Keyrisium - Trick used in TieEntityRendererChestHelper
        this(p_i2350_1_, 27);
        this.blockType = fakeType;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return content.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.content[p_70301_1_];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.content[p_70298_1_] != null)
        {
            ItemStack var3;

            if (this.content[p_70298_1_].stackSize <= p_70298_2_)
            {
                var3 = this.content[p_70298_1_];
                this.content[p_70298_1_] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = this.content[p_70298_1_].splitStack(p_70298_2_);

                if (this.content[p_70298_1_].stackSize == 0)
                {
                    this.content[p_70298_1_] = null;
                }

                this.onInventoryChanged();
                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.content[p_70304_1_] != null)
        {
            ItemStack var2 = this.content[p_70304_1_];
            this.content[p_70304_1_] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.content[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return this.isInventoryNameLocalized() ? this.field_145981_s : "container.chest";
    }

    /**
     * Returns if the inventory name is localized
     */
    public boolean isInventoryNameLocalized()
    {
        return this.field_145981_s != null && this.field_145981_s.length() > 0;
    }

    public void func_145976_a(String p_145976_1_)
    {
        this.field_145981_s = p_145976_1_;
    }

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList var2 = p_145839_1_.getTagList("Items", 10);

        if (p_145839_1_.hasKey("Size"))
            this.content = new ItemStack[p_145839_1_.getByte("Size")];
        else
            this.content = new ItemStack[27];

        if (p_145839_1_.func_150297_b("CustomName", 8))
        {
            this.field_145981_s = p_145839_1_.getString("CustomName");
        }

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.content.length)
            {
                this.content[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

    }

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        NBTTagList var2 = new NBTTagList();

        p_145841_1_.setByte("Size", (byte) content.length);

        for (int var3 = 0; var3 < this.content.length; ++var3)
        {
            if (this.content[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.content[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        p_145841_1_.setTag("Items", var2);

        if (this.isInventoryNameLocalized())
        {
            p_145841_1_.setString("CustomName", this.field_145981_s);
        }

    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.worldObj.getTileEntity(this.posX, this.posY, this.posZ) != this ? false : p_70300_1_.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }

    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        this.field_145984_a = false;
    }

    private void func_145978_a(TileEntityChest p_145978_1_, int p_145978_2_)
    {
        if (p_145978_1_.isInvalid())
        {
            this.field_145984_a = false;
        }
        else if (this.field_145984_a)
        {
            switch (p_145978_2_)
            {
                case 0:
                    if (this.field_145988_l != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }

                    break;

                case 1:
                    if (this.field_145991_k != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }

                    break;

                case 2:
                    if (this.field_145992_i != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }

                    break;

                case 3:
                    if (this.field_145990_j != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }
            }
        }
    }

    public void func_145979_i()
    {
        if (!this.field_145984_a)
        {
            this.field_145984_a = true;
            this.field_145992_i = null;
            this.field_145990_j = null;
            this.field_145991_k = null;
            this.field_145988_l = null;

            if (this.func_145977_a(this.posX - 1, this.posY, this.posZ))
            {
                this.field_145991_k = (TileEntityChest)this.worldObj.getTileEntity(this.posX - 1, this.posY, this.posZ);
            }

            if (this.func_145977_a(this.posX + 1, this.posY, this.posZ))
            {
                this.field_145990_j = (TileEntityChest)this.worldObj.getTileEntity(this.posX + 1, this.posY, this.posZ);
            }

            if (this.func_145977_a(this.posX, this.posY, this.posZ - 1))
            {
                this.field_145992_i = (TileEntityChest)this.worldObj.getTileEntity(this.posX, this.posY, this.posZ - 1);
            }

            if (this.func_145977_a(this.posX, this.posY, this.posZ + 1))
            {
                this.field_145988_l = (TileEntityChest)this.worldObj.getTileEntity(this.posX, this.posY, this.posZ + 1);
            }

            if (this.field_145992_i != null)
            {
                this.field_145992_i.func_145978_a(this, 0);
            }

            if (this.field_145988_l != null)
            {
                this.field_145988_l.func_145978_a(this, 2);
            }

            if (this.field_145990_j != null)
            {
                this.field_145990_j.func_145978_a(this, 1);
            }

            if (this.field_145991_k != null)
            {
                this.field_145991_k.func_145978_a(this, 3);
            }
        }
    }

    private boolean func_145977_a(int p_145977_1_, int p_145977_2_, int p_145977_3_)
    {
        if (this.worldObj == null)
        {
            return false;
        }
        else
        {
            Block var4 = this.worldObj.getBlock(p_145977_1_, p_145977_2_, p_145977_3_);
            return canBeLarge() && var4 instanceof BlockChest && ((BlockChest)var4).field_149956_a == this.func_145980_j(); // Keyrisium - add canBeLarge condition
        }
    }

    public void updateEntity()
    {
        /*System.out.println("i=" + field_145992_i);
        System.out.println("j=" + field_145990_j);
        System.out.println("k=" + field_145991_k);
        System.out.println("l=" + field_145988_l);*/

        super.updateEntity();
        this.func_145979_i();
        ++this.field_145983_q;
        float var1;

        if (!this.worldObj.isClient && this.field_145987_o != 0 && (this.field_145983_q + this.posX + this.posY + this.posZ) % 200 == 0)
        {
            this.field_145987_o = 0;
            var1 = 5.0F;
            List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)((float)this.posX - var1), (double)((float)this.posY - var1), (double)((float)this.posZ - var1), (double)((float)(this.posX + 1) + var1), (double)((float)(this.posY + 1) + var1), (double)((float)(this.posZ + 1) + var1)));
            Iterator var3 = var2.iterator();

            while (var3.hasNext())
            {
                EntityPlayer var4 = (EntityPlayer)var3.next();

                if (var4.openContainer instanceof ContainerChest)
                {
                    IInventory var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory();

                    if (var5 == this || var5 instanceof InventoryLargeChest && ((InventoryLargeChest)var5).isPartOfLargeChest(this))
                    {
                        ++this.field_145987_o;
                    }
                }
            }
        }

        this.field_145986_n = this.field_145989_m;
        var1 = 0.1F;
        double var11;

        if (this.field_145987_o > 0 && this.field_145989_m == 0.0F && this.field_145992_i == null && this.field_145991_k == null)
        {
            double var8 = (double)this.posX + 0.5D;
            var11 = (double)this.posZ + 0.5D;

            if (this.field_145988_l != null)
            {
                var11 += 0.5D;
            }

            if (this.field_145990_j != null)
            {
                var8 += 0.5D;
            }

            this.worldObj.playSoundEffect(var8, (double)this.posY + 0.5D, var11, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.field_145987_o == 0 && this.field_145989_m > 0.0F || this.field_145987_o > 0 && this.field_145989_m < 1.0F)
        {
            float var9 = this.field_145989_m;

            if (this.field_145987_o > 0)
            {
                this.field_145989_m += var1;
            }
            else
            {
                this.field_145989_m -= var1;
            }

            if (this.field_145989_m > 1.0F)
            {
                this.field_145989_m = 1.0F;
            }

            float var10 = 0.5F;

            if (this.field_145989_m < var10 && var9 >= var10 && this.field_145992_i == null && this.field_145991_k == null)
            {
                var11 = (double)this.posX + 0.5D;
                double var6 = (double)this.posZ + 0.5D;

                if (this.field_145988_l != null)
                {
                    var6 += 0.5D;
                }

                if (this.field_145990_j != null)
                {
                    var11 += 0.5D;
                }

                this.worldObj.playSoundEffect(var11, (double)this.posY + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.field_145989_m < 0.0F)
            {
                this.field_145989_m = 0.0F;
            }
        }
    }

    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
    {
        if (p_145842_1_ == 1)
        {
            this.field_145987_o = p_145842_2_;
            return true;
        }
        else
        {
            return super.receiveClientEvent(p_145842_1_, p_145842_2_);
        }
    }

    public void openInventory()
    {
        if (this.field_145987_o < 0)
        {
            this.field_145987_o = 0;
        }

        ++this.field_145987_o;
        this.worldObj.func_147452_c(this.posX, this.posY, this.posZ, this.getBlockType(), 1, this.field_145987_o);
        this.worldObj.notifyBlocksOfNeighborChange(this.posX, this.posY, this.posZ, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.posX, this.posY - 1, this.posZ, this.getBlockType());
    }

    public void closeInventory()
    {
        if (this.getBlockType() instanceof BlockChest)
        {
            --this.field_145987_o;
            this.worldObj.func_147452_c(this.posX, this.posY, this.posZ, this.getBlockType(), 1, this.field_145987_o);
            this.worldObj.notifyBlocksOfNeighborChange(this.posX, this.posY, this.posZ, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.posX, this.posY - 1, this.posZ, this.getBlockType());
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.func_145979_i();
    }

    public int func_145980_j()
    {
        if (this.field_145982_r == -1)
        {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest))
            {
                return 0;
            }

            BlockChest chest = (BlockChest)this.getBlockType();
            this.field_145982_r = chest.field_149956_a;
        }

        return this.field_145982_r;
    }

    public boolean canBeLarge() {
        Block block = getBlockType();
        return !(block instanceof BlockChest) || ((BlockChest) block).canBeLarge;
    }
}
