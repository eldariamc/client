package bspkrs.client.util;

import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import bspkrs.util.BSLog;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class EntityUtils {
   public static float getModelSize(EntityLivingBase ent) {
      Render render = RenderManager.instance.getEntityRenderObject(ent);
      if(render instanceof RendererLivingEntity) {
         RendererLivingEntity entRender = (RendererLivingEntity)render;
      }

      return 1.8F;
   }

   public static void drawEntityOnScreenAtRotation(int posX, int posY, float scale, float xAngle, float yAngle, EntityLivingBase ent) {
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glPushMatrix();
      GL11.glEnable(2903);
      GL11.glTranslatef((float)posX, (float)posY, 50.0F);
      GL11.glScalef(-scale, scale, scale);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = ent.renderYawOffset;
      float f3 = ent.rotationYaw;
      float f4 = ent.rotationPitch;
      float f5 = ent.rotationYawHead;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(xAngle, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(yAngle, 0.0F, 1.0F, 0.0F);
      ent.renderYawOffset = (float)Math.atan(0.05000000074505806D) * 20.0F;
      ent.rotationYaw = (float)Math.atan(0.05000000074505806D) * 40.0F;
      ent.rotationPitch = -((float)Math.atan(0.05000000074505806D)) * 20.0F;
      ent.rotationYawHead = ent.renderYawOffset;
      GL11.glTranslatef(0.0F, ent.yOffset, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float viewY = RenderManager.instance.playerViewY;

      try {
         RenderManager.instance.playerViewY = 180.0F;
         RenderManager.instance.func_147940_a(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      } finally {
         GL11.glTranslatef(0.0F, -0.22F, 0.0F);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 204.0F, 204.0F);
         RenderManager.instance.playerViewY = viewY;
         ent.renderYawOffset = f2;
         ent.rotationYaw = f3;
         ent.rotationPitch = f4;
         ent.rotationYawHead = f5;
         GL11.glPopMatrix();
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable('耺');
         OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glDisable(3553);
         OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      }

   }

   public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, EntityLivingBase ent) {
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glPushMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(2903);
      GL11.glTranslatef((float)posX, (float)posY, 50.0F);
      GL11.glScalef(-scale, scale, scale);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = ent.renderYawOffset;
      float f3 = ent.rotationYaw;
      float f4 = ent.rotationPitch;
      float f5 = ent.prevRotationYawHead;
      float f6 = ent.rotationYawHead;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
      ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
      ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
      ent.rotationYawHead = ent.rotationYaw;
      ent.prevRotationYawHead = ent.rotationYaw;
      GL11.glTranslatef(0.0F, ent.yOffset, 0.0F);

      try {
         RenderManager.instance.playerViewY = 180.0F;
         RenderManager.instance.func_147940_a(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      } finally {
         ent.renderYawOffset = f2;
         ent.rotationYaw = f3;
         ent.rotationPitch = f4;
         ent.prevRotationYawHead = f5;
         ent.rotationYawHead = f6;
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable('耺');
         OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glDisable(3553);
         OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glTranslatef(0.0F, 0.0F, 20.0F);
         GL11.glPopMatrix();
      }

   }

   public static float getEntityScale(EntityLivingBase ent, float baseScale, float targetHeight) {
      return targetHeight / Math.max(Math.max(ent.width, ent.height), ent.ySize) * baseScale;
   }

   public static EntityLivingBase getRandomLivingEntity(World world) {
      return getRandomLivingEntity(world, (List)null, 5, (List)null);
   }

   public static EntityLivingBase getRandomLivingEntity(World world, List blacklist, int numberOfAttempts, List fallbackPlayerNames) {
      Random random = new Random();
      Set entities = new TreeSet(EntityList.stringToClassMapping.keySet());
      if(blacklist != null) {
         entities.removeAll(blacklist);
      }

      Object[] entStrings = entities.toArray(new Object[0]);
      int tries = 0;

      int id;
      Class clazz;
      while(true) {
         id = random.nextInt(entStrings.length);
         clazz = (Class)EntityList.stringToClassMapping.get(entStrings[id]);
         if(EntityLivingBase.class.isAssignableFrom(clazz)) {
            break;
         }

         ++tries;
         if(tries > numberOfAttempts) {
            break;
         }
      }

      if(!EntityLivingBase.class.isAssignableFrom(clazz)) {
         if(fallbackPlayerNames != null) {
            SimpleEntry<UUID, String> entry = (SimpleEntry)fallbackPlayerNames.get(random.nextInt(fallbackPlayerNames.size()));
            return new EntityOtherPlayerMP(world, Minecraft.getMinecraft().func_152347_ac().fillProfileProperties(new GameProfile((UUID)entry.getKey(), (String)entry.getValue()), true));
         } else {
            return (EntityLivingBase)EntityList.createEntityByName("Chicken", world);
         }
      } else {
         if(bspkrsCoreMod.instance.allowDebugOutput) {
            BSLog.info(entStrings[id].toString(), new Object[0]);
         }

         return (EntityLivingBase)EntityList.createEntityByName((String)entStrings[id], world);
      }
   }
}
