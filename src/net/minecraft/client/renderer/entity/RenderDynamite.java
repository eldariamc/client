package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelDynamite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityDynamite;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDynamite extends Render
{
    private static final ResourceLocation dynamiteTextures = new ResourceLocation("textures/entity/dynamite.png");
    protected ModelBase model;

    public RenderDynamite() {
        this.shadowSize = 0.0F;
        this.model = new ModelDynamite();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityDynamite entity, double x, double y, double z, float yaw, float pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 1.75F, (float)z);
        GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        float var10 = - pitch;
        float var11 = - pitch;

        if (var11 < 0.0F)
        {
            var11 = 0.0F;
        }

        if (var10 > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0F, 1.0F, 0.0F, 0.0F);
        }

        float var12 = 0.75F;
        GL11.glScalef(var12, var12, var12);
        GL11.glScalef(1.0F / var12, 1.0F / var12, 1.0F / var12);
        this.bindEntityTexture(entity);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDynamite entity)
    {
        return dynamiteTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityDynamite) entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch)
    {
        this.doRender((EntityDynamite) entity, x, y, z, yaw, pitch);
    }
}
