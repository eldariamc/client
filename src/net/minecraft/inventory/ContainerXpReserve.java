package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityXpReserve;

/**
 * Created by David on 12/08/2016.
 */
public class ContainerXpReserve extends Container {

	private TileEntityXpReserve xpReserve;
	private int lastExperience;

	public ContainerXpReserve(TileEntityXpReserve xpReserve) {
		this.xpReserve = xpReserve;
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.xpReserve.experience);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (ICrafting crafting : this.crafters) {
			if (this.lastExperience != this.xpReserve.experience)
				crafting.sendProgressBarUpdate(this, 0, this.xpReserve.experience);
		}

		this.lastExperience = this.xpReserve.experience;
	}

	@Override
	public void updateProgressBar(int variable, int value) {
		switch (variable) {
			case 0:
				this.xpReserve.experience = value;
				break;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.worldObj.getTileEntity(this.xpReserve.posX, this.xpReserve.posY, this.xpReserve.posZ) == this.xpReserve
				&& player.getDistanceSq((double) this.xpReserve.posX + 0.5D, (double) this.xpReserve.posY + 0.5D, (double) this.xpReserve.posZ + 0.5D) <= 64.0D;
	}

}
