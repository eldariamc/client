package bspkrs.armorstatushud;

import bspkrs.client.util.ColorThreshold;
import bspkrs.client.util.HUDUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class HUDElement {
   public final ItemStack itemStack;
   public final int iconW;
   public final int iconH;
   public final int padW;
   private int elementW;
   private int elementH;
   private String itemName = "";
   private int itemNameW;
   private String itemDamage = "";
   private int itemDamageW;
   private final boolean isArmor;
   private Minecraft mc = Minecraft.getMinecraft();

   public HUDElement(ItemStack itemStack, int iconW, int iconH, int padW, boolean isArmor) {
      this.itemStack = itemStack;
      this.iconW = iconW;
      this.iconH = iconH;
      this.padW = padW;
      this.isArmor = isArmor;
      this.initSize();
   }

   public int width() {
      return this.elementW;
   }

   public int height() {
      return this.elementH;
   }

   private void initSize() {
      this.elementH = ArmorStatusHUD.enableItemName?Math.max(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2, this.iconH):Math.max(this.mc.fontRenderer.FONT_HEIGHT, this.iconH);
      if(this.itemStack != null) {
         int damage = 1;
         int maxDamage = 1;
         if((this.isArmor && ArmorStatusHUD.showArmorDamage || !this.isArmor && ArmorStatusHUD.showItemDamage) && this.itemStack.isItemStackDamageable()) {
            maxDamage = this.itemStack.getMaxDamage() + 1;
            damage = maxDamage - this.itemStack.getItemDamageForDisplay();
            if(ArmorStatusHUD.damageDisplayType.equalsIgnoreCase("value")) {
               this.itemDamage = "§" + ColorThreshold.getColorCode(ArmorStatusHUD.colorList, ArmorStatusHUD.damageThresholdType.equalsIgnoreCase("percent")?damage * 100 / maxDamage:damage) + damage + (ArmorStatusHUD.showMaxDamage?"/" + maxDamage:"");
            } else if(ArmorStatusHUD.damageDisplayType.equalsIgnoreCase("percent")) {
               this.itemDamage = "§" + ColorThreshold.getColorCode(ArmorStatusHUD.colorList, ArmorStatusHUD.damageThresholdType.equalsIgnoreCase("percent")?damage * 100 / maxDamage:damage) + damage * 100 / maxDamage + "%";
            }
         }

         this.itemDamageW = this.mc.fontRenderer.getStringWidth(HUDUtils.stripCtrl(this.itemDamage));
         this.elementW = this.padW + this.iconW + this.padW + this.itemDamageW;
         if(ArmorStatusHUD.enableItemName) {
            this.itemName = this.itemStack.getDisplayName();
            this.elementW = this.padW + this.iconW + this.padW + Math.max(this.mc.fontRenderer.getStringWidth(HUDUtils.stripCtrl(this.itemName)), this.itemDamageW);
         }

         this.itemNameW = this.mc.fontRenderer.getStringWidth(HUDUtils.stripCtrl(this.itemName));
      }

   }

   public void renderToHud(int x, int y) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableRescaleNormal();
      RenderHelper.enableStandardItemLighting();
      RenderHelper.enableGUIStandardItemLighting();
      ArmorStatusHUD.itemRenderer.zLevel = 200.0F;
      if(ArmorStatusHUD.alignMode.toLowerCase().contains("right")) {
         ArmorStatusHUD.itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.itemStack, x - (this.iconW + this.padW), y);
         HUDUtils.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.itemStack, x - (this.iconW + this.padW), y, ArmorStatusHUD.showDamageOverlay, ArmorStatusHUD.showItemCount);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         this.mc.fontRenderer.drawStringWithShadow(this.itemName + "§r", x - (this.padW + this.iconW + this.padW) - this.itemNameW, y, 16777215);
         this.mc.fontRenderer.drawStringWithShadow(this.itemDamage + "§r", x - (this.padW + this.iconW + this.padW) - this.itemDamageW, y + (ArmorStatusHUD.enableItemName?this.elementH / 2:this.elementH / 4), 16777215);
      } else {
         ArmorStatusHUD.itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.itemStack, x, y);
         HUDUtils.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.itemStack, x, y, ArmorStatusHUD.showDamageOverlay, ArmorStatusHUD.showItemCount);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         this.mc.fontRenderer.drawStringWithShadow(this.itemName + "§r", x + this.iconW + this.padW, y, 16777215);
         this.mc.fontRenderer.drawStringWithShadow(this.itemDamage + "§r", x + this.iconW + this.padW, y + (ArmorStatusHUD.enableItemName?this.elementH / 2:this.elementH / 4), 16777215);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
