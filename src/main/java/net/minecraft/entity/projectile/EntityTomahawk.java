package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Created by David on 13/01/2017.
 */
public class EntityTomahawk extends EntityThrowable {
	public EntityTomahawk(World world) {
		super(world);
	}

	public EntityTomahawk(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	public EntityTomahawk(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void onUpdate() {
		this.rotationPitch += 36.0F;
		super.onUpdate();
	}

	@Override
	protected void onImpact(MovingObjectPosition movingObj) {
		if (movingObj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
				&& movingObj.entityHit instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) movingObj.entityHit;
			living.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 4.0F);
			living.addPotionEffect(new PotionEffect(2, 60, 0, true));
		} else {
			// TODO: play soud "ding"
		}

		if (!this.worldObj.isClient) {
			float vel = 0.7F;
			double x = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
			double y = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
			double z = (double)(rand.nextFloat() * vel) + (double)(1.0F - vel) * 0.5D;
			EntityItem var13 = new EntityItem(worldObj, posX + x, posY + y, posZ + z, new ItemStack(Items.tomahawk));
			var13.delayBeforeCanPickup = 10;
			worldObj.spawnEntityInWorld(var13);
		}
		this.setDead();
	}
}
