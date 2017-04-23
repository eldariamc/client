package net.minecraft.entity.projectile;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Created by David on 17/12/2016.
 */
public class EntityC4 extends EntityThrowable
{
	public int fuse = 100;

	public EntityC4(World world) {
		super(world);
	}

	public EntityC4(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}

	public EntityC4(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.isInWater()) {
			if (!this.worldObj.isClient) {
				float vel = 0.7F;
				double x = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
				double y = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
				double z = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
				EntityItem var13 = new EntityItem(worldObj, posX + x, posY + y, posZ + z, new ItemStack(Items.c4));
				var13.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(var13);
			}
			this.setDead();
			return;
		}

		if (this.fuse-- <= 0)
		{
			this.setDead();

			if (!this.worldObj.isClient)
			{
				this.explode();
			}
		}
	}

	private void explode() {
		float power = 3.0F;
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, power, true);
	}

	@Override
	protected void onImpact(MovingObjectPosition movingObj) {
		if (movingObj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			this.motionX *= -0.10000000149011612D;
			this.motionY *= -0.10000000149011612D;
			this.motionZ *= -0.10000000149011612D;
		} else {
			this.xTile = movingObj.blockX;
			this.yTile = movingObj.blockY;
			this.zTile = movingObj.blockZ;
			this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
			this.motionX = (double)((float)(movingObj.hitVec.xCoord - this.posX));
			this.motionY = (double)((float)(movingObj.hitVec.yCoord - this.posY));
			this.motionZ = (double)((float)(movingObj.hitVec.zCoord - this.posZ));
			//double var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
			//this.posX -= this.motionX / var20 * 0.05000000074505806D;
			//this.posY -= this.motionY / var20 * 0.05000000074505806D;
			//this.posZ -= this.motionZ / var20 * 0.05000000074505806D;
			//this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.throwableShake = 7;

			if (this.inTile.getMaterial() != Material.air) {
				this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
			}
		}
	}

	@Override
	protected float getScope() {
		return 0.75F;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		super.readEntityFromNBT(tagCompound);

		fuse = tagCompound.getShort("Fuse");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);

		tagCompound.setShort("Fuse", (short)fuse);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if (!this.worldObj.isClient) {
			float vel = 0.7F;
			double x = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
			double y = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
			double z = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
			EntityItem var13 = new EntityItem(worldObj, posX + x, posY + y, posZ + z, new ItemStack(Items.c4));
			var13.delayBeforeCanPickup = 10;
			worldObj.spawnEntityInWorld(var13);
		}
		this.setDead();
		return true;
	}
}
