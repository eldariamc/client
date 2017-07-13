package net.minecraft.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.world.World;

/**
 * Created by David on 13/07/2017.
 */
public class ItemBowCustom extends ItemBow {
	private double arrowDamage = 2.0;
	private int arrowKnockback = 0;

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int itemInUseCount)
	{
		boolean bypassRealArrow = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;

		if (bypassRealArrow || player.inventory.hasItem(Items.arrow))
		{
			int ticksUsed = this.getMaxItemUseDuration(itemStack) - itemInUseCount;
			float strenght = (float)ticksUsed / 20.0F;
			strenght = (strenght * strenght + strenght * 2.0F) / 3.0F;

			if ((double)strenght < 0.1D)
			{
				return;
			}

			if (strenght > 1.0F)
			{
				strenght = 1.0F;
			}

			EntityArrow arrow = new EntityArrow(world, player, strenght * 2.0F);
			// Eldaria - apply custom damage
			arrow.setDamage(arrowDamage);

			if (strenght == 1.0F)
			{
				arrow.setIsCritical(true);
			}

			int lvlPower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);

			if (lvlPower > 0)
			{
				arrow.setDamage(arrow.getDamage() + (double)lvlPower * 0.5D + 0.5D);
			}

			int lvlPunch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);

			if (lvlPunch > 0)
			{
				arrow.setKnockbackStrength(arrow.getKnockbackStrength() + lvlPunch);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0)
			{
				arrow.setFire(100);
			}

			itemStack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + strenght * 0.5F);

			if (bypassRealArrow)
			{
				arrow.canBePickedUp = 2;
			}
			else
			{
				player.inventory.consumeInventoryItem(Items.arrow);
			}

			if (!world.isClient)
			{
				world.spawnEntityInWorld(arrow);
			}
		}
	}

	public double getArrowDamage() {
		return arrowDamage;
	}

	public ItemBowCustom setArrowDamage(double arrowDamage) {
		this.arrowDamage = arrowDamage;
		return this;
	}

	public int getArrowKnockback() {
		return arrowKnockback;
	}

	public ItemBowCustom setArrowKnockback(int arrowKnockback) {
		this.arrowKnockback = arrowKnockback;
		return this;
	}
}
