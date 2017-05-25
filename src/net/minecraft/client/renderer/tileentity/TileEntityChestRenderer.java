package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Calendar;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation trapped_double = new ResourceLocation("textures/entity/chest/trapped_double.png");
    private static final ResourceLocation christmas_double = new ResourceLocation("textures/entity/chest/christmas_double.png");
    private static final ResourceLocation normal_double = new ResourceLocation("textures/entity/chest/normal_double.png");
    private static final ResourceLocation trapped = new ResourceLocation("textures/entity/chest/trapped.png");
    private static final ResourceLocation christmas = new ResourceLocation("textures/entity/chest/christmas.png");
    private static final ResourceLocation normal = new ResourceLocation("textures/entity/chest/normal.png");
    private static final ResourceLocation mystery = new ResourceLocation("textures/entity/chest/mystery.png");
    private static final ResourceLocation magic = new ResourceLocation("textures/entity/chest/magic.png");
    private static final ResourceLocation mystery_double = new ResourceLocation("textures/entity/chest/mystery_double.png");
    private static final ResourceLocation magic_double = new ResourceLocation("textures/entity/chest/magic_double.png");
    private static final ResourceLocation zinc = new ResourceLocation("textures/entity/chest/zinc.png");
    private static final ResourceLocation cronyxe = new ResourceLocation("textures/entity/chest/cronyxe.png");
    private static final ResourceLocation kobalt = new ResourceLocation("textures/entity/chest/kobalt.png");
    private ModelChest field_147510_h = new ModelChest();
    private ModelChest field_147511_i = new ModelLargeChest();
    private boolean isChristmas;

    public TileEntityChestRenderer()
    {
        Calendar var1 = Calendar.getInstance();

        if ((var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) ||
                (var1.get(2) + 1 == 4 && var1.get(5) == 5))
        {
            this.isChristmas = true;
        }
    }

    public void renderTileEntityAt(TileEntityChest chest, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        int var9;

        if (!chest.hasWorldObj())
        {
            var9 = 0;
        }
        else
        {
            Block var10 = chest.getBlockType();
            var9 = chest.getBlockMetadata();

            if (var10 instanceof BlockChest && var9 == 0)
            {
                ((BlockChest)var10).func_149954_e(chest.getWorldObj(), chest.posX, chest.posY, chest.posZ);
                var9 = chest.getBlockMetadata();
            }

            chest.func_145979_i();
        }

        if (chest.field_145992_i == null && chest.field_145991_k == null)
        {
            ModelChest var14;

            if (chest.field_145990_j == null && chest.field_145988_l == null)
            {
                var14 = this.field_147510_h;

                if (chest.func_145980_j() == 1)
                {
                    this.bindTexture(trapped);
                }
                else
                {
                    Block block = chest.getBlockType();
                    if (block == Blocks.chest) {
                        if (this.isChristmas)
                            this.bindTexture(christmas);
                        else
                            this.bindTexture(normal);
                    } else if (block == Blocks.mystery_box) {
                        this.bindTexture(mystery);
                    } else if (block == Blocks.magic_chest) {
                        this.bindTexture(magic);
                    } else if (block == Blocks.zinc_chest) {
                        this.bindTexture(zinc);
                    } else if (block == Blocks.cronyxe_chest) {
                        this.bindTexture(cronyxe);
                    } else if (block == Blocks.kobalt_chest) {
                        this.bindTexture(kobalt);
                    }
                }
            }
            else
            {
                var14 = this.field_147511_i;

                if (chest.func_145980_j() == 1)
                {
                    this.bindTexture(trapped_double);
                }
                else
                {
                    Block block = chest.getBlockType();
                    if (block == Blocks.chest) {
                        if (this.isChristmas)
                            this.bindTexture(christmas_double);
                        else
                            this.bindTexture(normal_double);
                    } else if (block == Blocks.mystery_box) {
                        this.bindTexture(mystery_double);
                    } else if (block == Blocks.magic_chest) {
                        this.bindTexture(magic_double);
                    }
                }
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)p_147500_2_, (float)p_147500_4_ + 1.0F, (float)p_147500_6_ + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short var11 = 0;

            if (var9 == 2)
            {
                var11 = 180;
            }

            if (var9 == 3)
            {
                var11 = 0;
            }

            if (var9 == 4)
            {
                var11 = 90;
            }

            if (var9 == 5)
            {
                var11 = -90;
            }

            if (var9 == 2 && chest.field_145990_j != null)
            {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (var9 == 5 && chest.field_145988_l != null)
            {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)var11, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float var12 = chest.field_145986_n + (chest.field_145989_m - chest.field_145986_n) * p_147500_8_;
            float var13;

            if (chest.field_145992_i != null)
            {
                var13 = chest.field_145992_i.field_145986_n + (chest.field_145992_i.field_145989_m - chest.field_145992_i.field_145986_n) * p_147500_8_;

                if (var13 > var12)
                {
                    var12 = var13;
                }
            }

            if (chest.field_145991_k != null)
            {
                var13 = chest.field_145991_k.field_145986_n + (chest.field_145991_k.field_145989_m - chest.field_145991_k.field_145986_n) * p_147500_8_;

                if (var13 > var12)
                {
                    var12 = var13;
                }
            }

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;
            var14.chestLid.rotateAngleX = -(var12 * (float)Math.PI / 2.0F);
            var14.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        this.renderTileEntityAt((TileEntityChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}
