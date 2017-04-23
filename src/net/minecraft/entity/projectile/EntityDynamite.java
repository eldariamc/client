package net.minecraft.entity.projectile;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDynamite extends EntityThrowable
{
    public int fuse = 60;

    public EntityDynamite(World world) {
        super(world);
    }

    public EntityDynamite(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
    }

    public EntityDynamite(World world, double x, double y, double z)
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
                EntityItem var13 = new EntityItem(worldObj, posX + x, posY + y, posZ + z, new ItemStack(Items.dynamite));
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
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode() {
        float power = 2.5F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, power, true);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObj) {
        if (movingObj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            this.motionX *= -0.10000000149011612D;
            this.motionY *= -0.10000000149011612D;
            this.motionZ *= -0.10000000149011612D;
        } else {
            switch (movingObj.sideHit) {
                case 0:
                	this.motionX *= 0.05;
                	this.motionZ *= 0.05;
                case 1:
                    this.motionY *= -0.25;
                    break;
                case 2:
                case 3:
                    this.motionZ *= -0.5;
                    break;
                case 4:
                case 5:
                    this.motionX *= -0.5;
                    break;
            }
            if (movingObj.sideHit == 0 && motionX*motionX + motionY*motionY + motionZ*motionZ < 2.25) {
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

                if (this.inTile.getMaterial() != Material.air)
                {
                    this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                }
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
}
