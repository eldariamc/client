package bspkrs.util.config.gui;

import bspkrs.client.util.HUDUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

/** @deprecated */
@Deprecated
public class GuiUnicodeGlyphButton extends GuiButtonExt {
   public String glyph;
   public float glyphScale;

   public GuiUnicodeGlyphButton(int id, int xPos, int yPos, int width, int height, String displayString, String glyph, float glyphScale) {
      super(id, xPos, yPos, width, height, displayString);
      this.glyph = glyph;
      this.glyphScale = glyphScale;
   }

   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if(this.visible) {
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         int k = this.getHoverState(this.hovered);
         HUDUtils.drawContinuousTexturedBox(buttonTextures, this.xPosition, this.yPosition, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
         this.mouseDragged(mc, mouseX, mouseY);
         int color = 14737632;
         if(this.packedFGColour != 0) {
            color = this.packedFGColour;
         } else if(!this.enabled) {
            color = 10526880;
         } else if(this.hovered) {
            color = 16777120;
         }

         String buttonText = this.displayString;
         int glyphWidth = (int)((float)mc.fontRenderer.getStringWidth(this.glyph) * this.glyphScale);
         int strWidth = mc.fontRenderer.getStringWidth(buttonText);
         int elipsisWidth = mc.fontRenderer.getStringWidth("...");
         int totalWidth = strWidth + glyphWidth;
         if(totalWidth > this.width - 6 && totalWidth > elipsisWidth) {
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, this.width - 6 - elipsisWidth).trim() + "...";
         }

         strWidth = mc.fontRenderer.getStringWidth(buttonText);
         int var10000 = glyphWidth + strWidth;
         GL11.glPushMatrix();
         GL11.glScalef(this.glyphScale, this.glyphScale, 1.0F);
         this.drawCenteredString(mc.fontRenderer, this.glyph, (int)((float)(this.xPosition + this.width / 2 - strWidth / 2) / this.glyphScale - (float)glyphWidth / (2.0F * this.glyphScale) + 2.0F), (int)(((float)this.yPosition + (float)(this.height - 8) / this.glyphScale / 2.0F - 1.0F) / this.glyphScale), color);
         GL11.glPopMatrix();
         this.drawCenteredString(mc.fontRenderer, buttonText, (int)((float)(this.xPosition + this.width / 2) + (float)glyphWidth / this.glyphScale), this.yPosition + (this.height - 8) / 2, color);
      }

   }
}
