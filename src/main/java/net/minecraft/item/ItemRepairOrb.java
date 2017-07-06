package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by David on 10/12/2016.
 */
public class ItemRepairOrb extends Item {

	private boolean isFullRepair;

	public ItemRepairOrb(boolean isFullRepair) {
		this.setMaxStackSize(1);
		this.isFullRepair = isFullRepair;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		/*if (!player.capabilities.isCreativeMode)
			itemStack.stackSize--;

		world.playSoundAtEntity(player, "block.enchantment_table.use", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isClient) {
			repair(player.inventory.armorInventory);
			if (isFullRepair)
				repair(player.inventory.mainInventory);

		}*/

		return itemStack;
	}

	@Override
	public boolean hasEffect(ItemStack itemStack) {
		return isFullRepair;
	}

	private static void repair(ItemStack[] inventory) {
		for (ItemStack itemStack : inventory)
			if (itemStack != null && itemStack.getMaxDamage() > 0)
				itemStack.setItemDamage(0);
	}
}
