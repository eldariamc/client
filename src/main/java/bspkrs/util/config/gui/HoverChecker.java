package bspkrs.util.config.gui;

import bspkrs.util.ReflectionHelper;
import net.minecraft.client.gui.GuiButton;

/** @deprecated */
@Deprecated
public class HoverChecker {
   private int top;
   private int bottom;
   private int left;
   private int right;
   private int threshold;
   private GuiButton button;
   private long hoverStart;

   public HoverChecker(int top, int bottom, int left, int right, int threshold) {
      this.top = top;
      this.bottom = bottom;
      this.left = left;
      this.right = right;
      this.threshold = threshold;
      this.hoverStart = -1L;
   }

   public HoverChecker(GuiButton button, int threshold) {
      this.button = button;
      this.threshold = threshold;
   }

   public void updateBounds(int top, int bottom, int left, int right) {
      this.top = top;
      this.bottom = bottom;
      this.left = left;
      this.right = right;
   }

   public boolean checkHover(int mouseX, int mouseY) {
      return this.checkHover(mouseX, mouseY, true);
   }

   public boolean checkHover(int mouseX, int mouseY, boolean canHover) {
      if(this.button != null) {
         this.top = this.button.yPosition;
         this.bottom = this.button.yPosition + ReflectionHelper.getIntValue(GuiButton.class, "field_146121_g", "height", this.button, 18);
         this.left = this.button.xPosition;
         this.right = this.button.xPosition + this.button.func_146117_b();
         canHover = canHover && this.button.visible;
      }

      if(canHover && this.hoverStart == -1L && mouseY >= this.top && mouseY <= this.bottom && mouseX >= this.left && mouseX <= this.right) {
         this.hoverStart = System.currentTimeMillis();
      } else if(!canHover || mouseY < this.top || mouseY > this.bottom || mouseX < this.left || mouseX > this.right) {
         this.resetHoverTimer();
      }

      return canHover && this.hoverStart != -1L && System.currentTimeMillis() - this.hoverStart >= (long)this.threshold;
   }

   public void resetHoverTimer() {
      this.hoverStart = -1L;
   }
}
