package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityWaterPipe;

/**
 * Created by David on 28/06/2016.
 */
public class ContainerWaterPipe extends Container {

	private TileEntityWaterPipe waterPipe;

	public ContainerWaterPipe(InventoryPlayer inventoryPlayer, TileEntityWaterPipe waterPipe) {
		this.waterPipe = waterPipe;
		this.addSlotToContainer(new Slot(waterPipe, 0, 70, 9){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack != null && stack.getItem() == Items.lit_coal;
			}
		});
		this.addSlotToContainer(new ContainerBrewingStand.Ingredient(waterPipe, 1, 66, 31));

		int var3;
		for (var3 = 0; var3 < 3; ++var3)
		{
			for (int var4 = 0; var4 < 9; ++var4)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, var3, 8 + var3 * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return this.waterPipe.isUseableByPlayer(p_75145_1_);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		ItemStack var3 = null;
		Slot var4 = this.getSlot(p_82846_2_);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (p_82846_2_ < this.waterPipe.getSizeInventory())
			{
				if (!this.mergeItemStack(var5, this.waterPipe.getSizeInventory(), this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, this.waterPipe.getSizeInventory(), false))
			{
				return null;
			}

			if (var5.stackSize == 0)
			{
				var4.putStack(null);
			}
			else
			{
				var4.onSlotChanged();
			}
		}

		return var3;
	}
}
