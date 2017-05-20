package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by David on 27/06/2016.
 */
public class TileEntityWaterPipe extends TileEntity implements ISidedInventory {

	private static final int[] topSlot = new int[]{0};
	private static final int[] sideSlot = new int[]{1};
	private ItemStack[] inventory = new ItemStack[2];
	private String name;
	public int smokeTime = 0;
	public int potionId;
	public byte rotation;

	public TileEntityWaterPipe() {

	}

	/**
	 * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
	 * block.
	 *
	 * @param side
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 1 ? topSlot : sideSlot;
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 *
	 * @param slot
	 * @param item
	 * @param side
	 */
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 *
	 * @param p_102008_1_
	 * @param p_102008_2_
	 * @param p_102008_3_
	 */
	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	/**
	 * Returns the stack in slot i
	 *
	 * @param i
	 */
	@Override
	public ItemStack getStackInSlot(int i) {
		return i >= 0 && i < inventory.length ? inventory[i] : null;
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 *
	 * @param p_70298_1_
	 * @param p_70298_2_
	 */
	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
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
	 *
	 * @param p_70304_1_
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if (p_70304_1_ >= 0 && p_70304_1_ < this.inventory.length)
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
	 *
	 * @param p_70299_1_
	 * @param p_70299_2_
	 */
	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		if (p_70299_1_ >= 0 && p_70299_1_ < this.inventory.length)
		{
			this.inventory[p_70299_1_] = p_70299_2_;
		}
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName() {
		return this.isInventoryNameLocalized() ? this.name : "container.smoking";
	}

	public void setInventoryName(String name) {
		this.name = name;
	}

	/**
	 * Returns if the inventory name is localized
	 */
	@Override
	public boolean isInventoryNameLocalized() {
		return this.name != null && this.name.length() > 0;
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 *
	 * @param p_70300_1_
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return this.worldObj.getTileEntity(this.posX, this.posY, this.posZ) == this && p_70300_1_.getDistanceSq((double) this.posX + 0.5D, (double) this.posY + 0.5D, (double) this.posZ + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 *
	 * @param slot
	 * @param item
	 */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return slot == 0 ? item.getItem() == Items.coal : item.getItem().isPotionIngredient(item);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(posX, posY, posZ, 6, tagCompound);
	}

	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Inventory", 10);

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < this.inventory.length) {
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		this.rotation = tagCompound.getByte("Rotation");
		this.smokeTime = tagCompound.getShort("SmokeTime");
		this.potionId = tagCompound.getByte("PotionId");

		if (tagCompound.func_150297_b("CustomName", 8))
		{
			this.name = tagCompound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			ItemStack stack = this.inventory[i];

			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);

		tagCompound.setByte("Rotation", this.rotation);
		tagCompound.setShort("SmokeTime", (short) this.smokeTime);
		tagCompound.setByte("PotionId", (byte) this.potionId);

		if (this.isInventoryNameLocalized())
		{
			tagCompound.setString("CustomName", this.name);
		}
	}



	public int getSmokeTime() {
		return smokeTime;
	}

	public int getPotionId() {
		return potionId;
	}

	public void setRotation(byte rotation) {
		this.rotation = rotation;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isClient && smokeTime == 0 && inventory[0] != null && inventory[0].getItem() == Items.coal
				&& inventory[1] != null) {
			Item item = inventory[1].getItem();
			if (item == Items.sugar)
				potionId = 1;
			else if (item == Items.blaze_powder)
				potionId = 5;
			else if (item == Items.speckled_melon)
				potionId = 6;
			else if (item == Items.slime_ball)
				potionId = 8;
			else if (item == Items.ghast_tear)
				potionId = 10;
			else if (item == Items.magma_cream)
				potionId = 12;
			else if (item == Items.fish && ItemFishFood.FishType.func_150978_a(inventory[1]) == ItemFishFood.FishType.PUFFERFISH)
				potionId = 13;
			else if (item == Items.golden_carrot)
				potionId = 16;
			else if (item == Items.fermented_spider_eye)
				potionId = 18;
			else if (item == Items.spider_eye)
				potionId = 19;
			else
				return;
			decrStackSize(0, 1);
			decrStackSize(1, 1);
			smokeTime = 5;
			this.worldObj.markBlockForUpdate(posX, posY, posZ);
		}
	}

	public boolean activate(EntityPlayer player) {
		if (smokeTime > 0) {
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 0));
			if (Potion.potionTypes[potionId].isInstant()) {
				Potion.potionTypes[potionId].affectEntity(null, player, 1, 1.0);
			} else {
				player.addPotionEffect(new PotionEffect(potionId, 9600, 1));
			}
			this.worldObj.playSoundAtEntity(player, "random.drink", 5.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			smokeTime--;
			this.worldObj.markBlockForUpdate(posX, posY, posZ);
			return false;
		}
		return true;
	}
}
