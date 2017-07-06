package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;

public class TileEntityFurnace extends TileEntity implements ISidedInventory
{
    private static final int[] field_145962_k = new int[] {0};
    private static final int[] field_145959_l = new int[] {2, 1};
    private static final int[] field_145960_m = new int[] {1};
    private ItemStack[] inventory = new ItemStack[3];
    public int burnTime;
    public int itemBurnTime;
    public int cookTime;
    private String inventoryName;

    public int fuelSpeed = 200;
    public double furnaceSpeed = 1.0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.inventory.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.inventory[p_70301_1_];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.inventory[p_70298_1_] != null)
        {
            ItemStack var3;

            if (this.inventory[p_70298_1_].stackSize <= p_70298_2_)
            {
                var3 = this.inventory[p_70298_1_];
                this.inventory[p_70298_1_] = null;
                return var3;
            }
            else
            {
                var3 = this.inventory[p_70298_1_].splitStack(p_70298_2_);

                if (this.inventory[p_70298_1_].stackSize == 0)
                {
                    this.inventory[p_70298_1_] = null;
                }

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
        if (this.inventory[p_70304_1_] != null)
        {
            ItemStack var2 = this.inventory[p_70304_1_];
            this.inventory[p_70304_1_] = null;
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
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        this.inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return this.isInventoryNameLocalized() ? this.inventoryName : "container.furnace";
    }

    /**
     * Returns if the inventory name is localized
     */
    public boolean isInventoryNameLocalized()
    {
        return this.inventoryName != null && this.inventoryName.length() > 0;
    }

    public void func_145951_a(String p_145951_1_)
    {
        this.inventoryName = p_145951_1_;
    }

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.inventory.length)
            {
                this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.burnTime = p_145839_1_.getShort("BurnTime");
        this.cookTime = p_145839_1_.getShort("CookTime");
        this.fuelSpeed = p_145839_1_.getShort("FuelSpeed");
        this.furnaceSpeed = p_145839_1_.getDouble("SpeedModifier");
        this.itemBurnTime = fuelTime(this.inventory[1]);

        if (p_145839_1_.func_150297_b("CustomName", 8))
        {
            this.inventoryName = p_145839_1_.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BurnTime", (short)this.burnTime);
        p_145841_1_.setShort("CookTime", (short)this.cookTime);
        p_145841_1_.setShort("FuelSpeed", (short)this.fuelSpeed);
        p_145841_1_.setDouble("SpeedModifier", this.furnaceSpeed);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inventory.length; ++var3)
        {
            if (this.inventory[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.inventory[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        p_145841_1_.setTag("Items", var2);

        if (this.isInventoryNameLocalized())
        {
            p_145841_1_.setString("CustomName", this.inventoryName);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public int func_145953_d(int p_145953_1_)
    {
        return MathHelper.floor_double(this.cookTime * p_145953_1_ / (fuelSpeed/furnaceSpeed));
    }

    public int func_145955_e(int p_145955_1_)
    {
        if (this.itemBurnTime == 0)
        {
            this.itemBurnTime = MathHelper.floor_double(fuelSpeed/furnaceSpeed);
        }
        return this.burnTime * p_145955_1_ / this.itemBurnTime;
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    public void updateEntity()
    {
        boolean var1 = this.burnTime > 0;
        boolean var2 = false;

        if (this.burnTime > 0)
        {
            --this.burnTime;
        }

        if (!this.worldObj.isClient)
        {
            if (this.burnTime != 0 || this.inventory[1] != null && this.inventory[0] != null)
            {
                if (this.burnTime == 0 && this.stopSmelting())
                {
                    this.itemBurnTime = this.burnTime = fuelTime(this.inventory[1]);

                    if (this.burnTime > 0)
                    {
                        var2 = true;

                        if (this.inventory[1] != null)
                        {
                            --this.inventory[1].stackSize;

                            if (this.inventory[1].stackSize == 0)
                            {
                                Item var3 = this.inventory[1].getItem().getContainerItem();
                                this.inventory[1] = var3 != null ? new ItemStack(var3) : null;
                            }
                        }
                    }
                }

                if (this.isBurning() && this.stopSmelting())
                {
                    ++this.cookTime;

                    if (this.cookTime >= fuelSpeed/furnaceSpeed)
                    {
                        this.cookTime = 0;
                        this.updateSlots();
                        var2 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }

            if (var1 != this.burnTime > 0)
            {
                var2 = true;
                BlockFurnace blockFurnace = (BlockFurnace)worldObj.getBlock(this.posX, this.posY, this.posZ);
                blockFurnace.func_149931_a(this.burnTime > 0, this.worldObj, this.posX, this.posY, this.posZ);
            }
        }

        if (var2)
        {
            this.onInventoryChanged();
        }
    }

    private boolean stopSmelting()
    {
        if (this.inventory[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
            return var1 == null ? false : (this.inventory[2] == null ? true : (!this.inventory[2].isItemEqual(var1) ? false : (this.inventory[2].stackSize < this.getInventoryStackLimit() && this.inventory[2].stackSize < this.inventory[2].getMaxStackSize() ? true : this.inventory[2].stackSize < var1.getMaxStackSize())));
        }
    }

    public void updateSlots()
    {
        if (this.stopSmelting())
        {
            ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);

            if (this.inventory[2] == null)
            {
                this.inventory[2] = var1.copy();
            }
            else if (this.inventory[2].getItem() == var1.getItem())
            {
                ++this.inventory[2].stackSize;
            }

            --this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0)
            {
                this.inventory[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack itemStack) {
        if (itemStack == null)
        {
            return 0;
        }
        else
        {
            Item item = itemStack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block var2 = Block.getBlockFromItem(item);

                if (var2 == Blocks.wooden_slab)
                {
                    return 150;
                }

                if (var2.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (var2 == Blocks.coal_block)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD"))
                return 200;
            if (item instanceof ItemSword && ((ItemSword)item).func_150932_j().equals("WOOD"))
                return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD"))
                return 200;
            if (item == Items.stick)
                return 100;
            if (item == Items.coal)
                return 1600;
            if (item == Items.lava_bucket)
                return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling))
                return 100;
            if (item == Items.blaze_rod)
                return 2400;
            if (item == Items.lignite)
                return 1600;
            return 0;
        }
    }

    public int fuelTime(ItemStack itemStack)
    {
        if (itemStack != null) {
            if (itemStack.getItem() == Items.lignite)
                fuelSpeed = 50;
            else
                fuelSpeed = 200;
        }
        return getItemBurnTime(itemStack);
    }

    public boolean isFuel(ItemStack p_145954_0_)
    {
        return fuelTime(p_145954_0_) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.worldObj.getTileEntity(this.posX, this.posY, this.posZ) == this && p_70300_1_.getDistanceSq((double) this.posX + 0.5D, (double) this.posY + 0.5D, (double) this.posZ + 0.5D) <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return p_94041_1_ == 2 ? false : (p_94041_1_ == 1 ? isFuel(p_94041_2_) : true);
    }

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? field_145959_l : (p_94128_1_ == 1 ? field_145962_k : field_145960_m);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
    {
        return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
    {
        return p_102008_3_ != 0 || p_102008_1_ != 1 || p_102008_2_.getItem() == Items.bucket;
    }
}
