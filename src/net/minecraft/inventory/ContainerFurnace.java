package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFurnace extends Container
{
    private TileEntityFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    private int lastFuelSpeed;
    private static final String __OBFID = "CL_00001748";

    public ContainerFurnace(InventoryPlayer p_i1812_1_, TileEntityFurnace p_i1812_2_)
    {
        this.furnace = p_i1812_2_;
        this.addSlotToContainer(new Slot(p_i1812_2_, 0, 56, 17));
        this.addSlotToContainer(new Slot(p_i1812_2_, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(p_i1812_1_.player, p_i1812_2_, 2, 116, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(p_i1812_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(p_i1812_1_, i, 8 + i * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.furnace.cookTime);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.furnace.burnTime);
        p_75132_1_.sendProgressBarUpdate(this, 2, this.furnace.itemBurnTime);
        p_75132_1_.sendProgressBarUpdate(this, 3, this.furnace.fuelSpeed);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastCookTime != this.furnace.cookTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.furnace.cookTime);
            }

            if (this.lastBurnTime != this.furnace.burnTime)
            {
                var2.sendProgressBarUpdate(this, 1, this.furnace.burnTime);
            }

            if (this.lastItemBurnTime != this.furnace.itemBurnTime)
            {
                var2.sendProgressBarUpdate(this, 2, this.furnace.itemBurnTime);
            }

            if (this.lastFuelSpeed != this.furnace.fuelSpeed)
            {
                var2.sendProgressBarUpdate(this, 3, this.furnace.fuelSpeed);
            }
        }

        this.lastCookTime = this.furnace.cookTime;
        this.lastBurnTime = this.furnace.burnTime;
        this.lastItemBurnTime = this.furnace.itemBurnTime;
        this.lastFuelSpeed = this.furnace.fuelSpeed;
    }

    public void updateProgressBar(int variable, int value)
    {
        if (variable == 0)
        {
            this.furnace.cookTime = value;
        }

        if (variable == 1)
        {
            this.furnace.burnTime = value;
        }

        if (variable == 2)
        {
            this.furnace.itemBurnTime = value;
        }

        if (variable == 3)
        {
            this.furnace.fuelSpeed = value;
        }
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return this.furnace.isUseableByPlayer(p_75145_1_);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(p_82846_2_);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (p_82846_2_ == 2)
            {
                if (!this.mergeItemStack(var5, 3, 39, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (p_82846_2_ != 1 && p_82846_2_ != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null)
                {
                    if (!this.mergeItemStack(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (furnace.isFuel(var5))
                {
                    if (!this.mergeItemStack(var5, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 3 && p_82846_2_ < 30)
                {
                    if (!this.mergeItemStack(var5, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.mergeItemStack(var5, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 39, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(p_82846_1_, var5);
        }

        return var3;
    }
}
