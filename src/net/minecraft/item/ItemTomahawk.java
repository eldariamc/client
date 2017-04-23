package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTomahawk;
import net.minecraft.world.World;

/**
 * Created by David on 13/01/2017.
 */
public class ItemTomahawk extends Item {

	public ItemTomahawk()
	{
		this.maxStackSize = 16;
		//this.setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--itemStack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isClient)
		{
			world.spawnEntityInWorld(new EntityTomahawk(world, player));
		}

		return itemStack;
	}
}
