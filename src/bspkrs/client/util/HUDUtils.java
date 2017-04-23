package bspkrs.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class HUDUtils {
   private static int[] colorCodes = new int[]{0, 170, 'ꨀ', 'ꪪ', 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215, 0, 42, 10752, 10794, 2752512, 2752554, 2763264, 2763306, 1381653, 1381695, 1392405, 1392447, 4134165, 4134207, 4144917, 4144959};

   public static int getColorCode(char c, boolean isLighter) {
      return colorCodes[isLighter?"0123456789abcdef".indexOf(c):"0123456789abcdef".indexOf(c) + 16];
   }

   public static void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int borderSize, float zLevel) {
      drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, borderSize, borderSize, borderSize, borderSize, zLevel);
   }

   public static void drawContinuousTexturedBox(ResourceLocation res, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int borderSize, float zLevel) {
      drawContinuousTexturedBox(res, x, y, u, v, width, height, textureWidth, textureHeight, borderSize, borderSize, borderSize, borderSize, zLevel);
   }

   public static void drawContinuousTexturedBox(ResourceLocation res, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(res);
      drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel);
   }

   public static void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glBlendFunc(770, 771);
      int fillerWidth = textureWidth - leftBorder - rightBorder;
      int fillerHeight = textureHeight - topBorder - bottomBorder;
      int canvasWidth = width - leftBorder - rightBorder;
      int canvasHeight = height - topBorder - bottomBorder;
      int xPasses = canvasWidth / fillerWidth;
      int remainderWidth = canvasWidth % fillerWidth;
      int yPasses = canvasHeight / fillerHeight;
      int remainderHeight = canvasHeight % fillerHeight;
      drawTexturedModalRect(x, y, u, v, leftBorder, topBorder, zLevel);
      drawTexturedModalRect(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
      drawTexturedModalRect(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
      drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);

      for(int i = 0; i < xPasses + (remainderWidth > 0?1:0); ++i) {
         drawTexturedModalRect(x + leftBorder + i * fillerWidth, y, u + leftBorder, v, i == xPasses?remainderWidth:fillerWidth, topBorder, zLevel);
         drawTexturedModalRect(x + leftBorder + i * fillerWidth, y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, i == xPasses?remainderWidth:fillerWidth, bottomBorder, zLevel);

         for(int j = 0; j < yPasses + (remainderHeight > 0?1:0); ++j) {
            drawTexturedModalRect(x + leftBorder + i * fillerWidth, y + topBorder + j * fillerHeight, u + leftBorder, v + topBorder, i == xPasses?remainderWidth:fillerWidth, j == yPasses?remainderHeight:fillerHeight, zLevel);
         }
      }

      for(int j = 0; j < yPasses + (remainderHeight > 0?1:0); ++j) {
         drawTexturedModalRect(x, y + topBorder + j * fillerHeight, u, v + topBorder, leftBorder, j == yPasses?remainderHeight:fillerHeight, zLevel);
         drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + j * fillerHeight, u + leftBorder + fillerWidth, v + topBorder, rightBorder, j == yPasses?remainderHeight:fillerHeight, zLevel);
      }

   }

   public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float zLevel) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)zLevel, (double)((float)(u + 0) * var7), (double)((float)(v + height) * var8));
      tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)zLevel, (double)((float)(u + width) * var7), (double)((float)(v + height) * var8));
      tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)zLevel, (double)((float)(u + width) * var7), (double)((float)(v + 0) * var8));
      tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * var7), (double)((float)(v + 0) * var8));
      tessellator.draw();
   }

   public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int x, int y) {
      renderItemOverlayIntoGUI(fontRenderer, itemStack, x, y, true, true);
   }

   public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int x, int y, boolean showDamageBar, boolean showCount) {
      if(itemStack != null && (showDamageBar || showCount)) {
         if(itemStack.isItemDamaged() && showDamageBar) {
            int var11 = (int)Math.round(13.0D - (double)itemStack.getItemDamageForDisplay() * 13.0D / (double)itemStack.getMaxDamage());
            int var7 = (int)Math.round(255.0D - (double)itemStack.getItemDamageForDisplay() * 255.0D / (double)itemStack.getMaxDamage());
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            Tessellator var8 = Tessellator.instance;
            int var9 = 255 - var7 << 16 | var7 << 8;
            int var10 = (255 - var7) / 4 << 16 | 16128;
            renderQuad(var8, x + 2, y + 13, 13, 2, 0);
            renderQuad(var8, x + 2, y + 13, 12, 1, var10);
            renderQuad(var8, x + 2, y + 13, var11, 1, var9);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }

         if(showCount) {
            int count = 0;
            if(itemStack.getMaxStackSize() > 1) {
               count = countInInventory(Minecraft.getMinecraft().thePlayer, itemStack.getItem(), itemStack.getItemDamageForDisplay());
            } else if(itemStack.getItem().equals(Items.bow)) {
               count = countInInventory(Minecraft.getMinecraft().thePlayer, Items.arrow);
            }

            if(count > 1) {
               String var6 = "" + count;
               GL11.glDisable(2896);
               GL11.glDisable(2929);
               fontRenderer.drawStringWithShadow(var6, x + 19 - 2 - fontRenderer.getStringWidth(var6), y + 6 + 3, 16777215);
               GL11.glEnable(2896);
               GL11.glEnable(2929);
            }
         }
      }

   }

   public static void renderQuad(Tessellator tessellator, int x, int y, int width, int height, int color) {
      tessellator.startDrawingQuads();
      tessellator.setColorOpaque_I(color);
      tessellator.addVertex((double)(x + 0), (double)(y + 0), 0.0D);
      tessellator.addVertex((double)(x + 0), (double)(y + height), 0.0D);
      tessellator.addVertex((double)(x + width), (double)(y + height), 0.0D);
      tessellator.addVertex((double)(x + width), (double)(y + 0), 0.0D);
      tessellator.draw();
   }

   public static int countInInventory(EntityPlayer player, Item item) {
      return countInInventory(player, item, -1);
   }

   public static int countInInventory(EntityPlayer player, Item item, int md) {
      int count = 0;

      for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
         if(player.inventory.mainInventory[i] != null && item.equals(player.inventory.mainInventory[i].getItem()) && (md == -1 || player.inventory.mainInventory[i].getItemDamage() == md)) {
            count += player.inventory.mainInventory[i].stackSize;
         }
      }

      return count;
   }

   public static String stripCtrl(String s) {
      return s.replaceAll("(?i)§[0-9a-fklmnor]", "");
   }
}
