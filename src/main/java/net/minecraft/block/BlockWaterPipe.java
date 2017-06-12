package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityWaterPipe;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by David on 27/06/2016.
 */
public class BlockWaterPipe extends BlockContainer {

	private Random field_149961_a = new Random();

	protected BlockWaterPipe() {
		super(Material.iron);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int playerRotation = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		byte rotation = 0;

		if (playerRotation == 1) {
			rotation = 3;
		} else if (playerRotation == 2) {
			rotation = 2;
		} else if (playerRotation == 3) {
			rotation = 1;
		}

		TileEntityWaterPipe pipeEntity = (TileEntityWaterPipe) world.getTileEntity(x, y, z);

		if (pipeEntity != null) {
			pipeEntity.setRotation(rotation);
		}

	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int u1, float u1f, float u2f, float u3f) {
		if (!world.isClient) {
			TileEntityWaterPipe pipeEntity = (TileEntityWaterPipe) world.getTileEntity(x, y, z);

			if (pipeEntity != null && !player.isEating()) {
				if ((player.isSneaking() && pipeEntity.getSmokeTime() > 0) || pipeEntity.activate(player))
					player.displayGUIWaterPipe(pipeEntity);
			}
		}
		return true;
	}

	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	{
		TileEntity var7 = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

		if (var7 instanceof TileEntityWaterPipe)
		{
			TileEntityWaterPipe var8 = (TileEntityWaterPipe) var7;

			for (int var9 = 0; var9 < var8.getSizeInventory(); ++var9)
			{
				ItemStack var10 = var8.getStackInSlot(var9);

				if (var10 != null)
				{
					float var11 = this.field_149961_a.nextFloat() * 0.8F + 0.1F;
					float var12 = this.field_149961_a.nextFloat() * 0.8F + 0.1F;
					float var13 = this.field_149961_a.nextFloat() * 0.8F + 0.1F;

					while (var10.stackSize > 0)
					{
						int var14 = this.field_149961_a.nextInt(21) + 10;

						if (var14 > var10.stackSize)
						{
							var14 = var10.stackSize;
						}

						var10.stackSize -= var14;
						EntityItem var15 = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + var11), (double)((float)p_149749_3_ + var12), (double)((float)p_149749_4_ + var13), new ItemStack(var10.getItem(), var14, var10.getItemDamage()));
						float var16 = 0.05F;
						var15.motionX = (double)((float)this.field_149961_a.nextGaussian() * var16);
						var15.motionY = (double)((float)this.field_149961_a.nextGaussian() * var16 + 0.2F);
						var15.motionZ = (double)((float)this.field_149961_a.nextGaussian() * var16);
						p_149749_1_.spawnEntityInWorld(var15);
					}
				}
			}
		}

		super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityWaterPipe();
	}

	public boolean canPlaceBlockAt(World world, int j, int k, int l) {
		return world.isAirBlock(j, k + 1, l) && world.blockExists(j, k - 1, l);
	}

	public void onPostBlockPlaced(World world, int j, int k, int l, int u1) {
		super.onPostBlockPlaced(world, j, k, l, u1);
		this.checkFlowerChange(world, j, k, l);
	}

	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntityWaterPipe tileEntity = (TileEntityWaterPipe) world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity.getSmokeTime() > 0) {
			double dx = (double) ((float) x + 0.4F + random.nextFloat() * 0.2F);
			double dy = (double) ((float) y + 0.7F + random.nextFloat() * 0.3F);
			double dz = (double) ((float) z + 0.4F + random.nextFloat() * 0.2F);
			int color = Potion.potionTypes[tileEntity.getPotionId()].getLiquidColor();
			double red = (double)(color >> 16 & 255) / 255.0D;
			double green = (double)(color >> 8 & 255) / 255.0D;
			double blue = (double)(color & 255) / 255.0D;
			world.spawnParticle("mobSpell", dx, dy, dz, red, green, blue);
		}
	}

	protected final void checkFlowerChange(World world, int x, int y, int z) {
		if (!this.canPlaceBlockAt(world, x, y, z)) {
			this.breakBlock(world, x, y, z, this, 0);
			world.setBlockToAir(x, y, z);
		}

	}

	public boolean c(World world, int j, int k, int l) {
		return world.blockExists(j, k - 1, l);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	/**
	 * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
	 */
	public String getItemIconName()
	{
		return "water_pipe";
	}



	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	/*public void registerBlockIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[8];

		for (int i = 0; i < this.icons.length; ++i) {
			this.icons[i] = iconRegister.a("waterpipe:WaterPipe_" + i);
		}

	}*/
}
