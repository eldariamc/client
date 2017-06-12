package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by David on 11/08/2016.
 */
public class TileEntityXpReserve extends TileEntity {

	public int experience;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		experience = compound.getInteger("XpLevel");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("XpLevel", experience);
	}
}
