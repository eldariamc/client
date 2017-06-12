package bspkrs.statuseffecthud.fml;

import bspkrs.statuseffecthud.StatusEffectHUD;
import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.InventoryEffectRenderer;

public class SEHRenderTicker implements Tickable {
   private Minecraft mc = Minecraft.getMinecraft();
   private static boolean isRegistered = false;

   public SEHRenderTicker() {
      isRegistered = true;
   }

   public void onTick(Phase phase) {
      if(!phase.equals(Phase.START)) {
         if(!StatusEffectHUD.onTickInGame(this.mc)) {
            //FMLCommonHandler.instance().bus().unregister(this);
            McpHandler.unregisterRenderTick(this);
            isRegistered = false;
         }

      } else {
         if(StatusEffectHUD.disableInventoryEffectList && this.mc.currentScreen != null && this.mc.currentScreen instanceof InventoryEffectRenderer) {
            try {
               InventoryEffectRenderer ier = (InventoryEffectRenderer)this.mc.currentScreen;
               /*if(ReflectionHelper.getBooleanValue(InventoryEffectRenderer.class, "field_147045_u", "hasActivePotionEffects", ier, false)) {
                  ReflectionHelper.setBooleanValue(InventoryEffectRenderer.class, "field_147045_u", "hasActivePotionEffects", ier, false);
                  ReflectionHelper.setIntValue(GuiContainer.class, "field_147003_i", "guiLeft", ier, (ier.width - ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176)) / 2);
               }*/
               if (ier.field_147045_u) {
                  ier.field_147045_u = false;
                  ier.field_147003_i = (ier.width - ier.defaultX) / 2;
               }

               for(Object o : ier.buttonList/*ReflectionHelper.getListObject(GuiScreen.class, "field_146292_n", "buttonList", ier)*/) {
                  if(o instanceof GuiButton && ((GuiButton)o).id == 101) {
                     ((GuiButton)o).xPosition = ier.field_147003_i;
                             //ReflectionHelper.getIntValue(GuiContainer.class, "field_147003_i", "guiLeft", ier, (ier.width - ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176)) / 2);
                  } else if(o instanceof GuiButton && ((GuiButton)o).id == 102) {
                     ((GuiButton)o).xPosition = ier.field_147003_i + ier.defaultX - 20;
                             //ReflectionHelper.getIntValue(GuiContainer.class, "field_147003_i", "guiLeft", ier, (ier.width - ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176)) / 2) + ReflectionHelper.getIntValue(GuiContainer.class, "field_146999_f", "xSize", ier, 176) - 20;
                  }
               }
            } catch (Throwable var6) {
               ;
            }
         }

      }
   }

   public static boolean isRegistered() {
      return isRegistered;
   }
}
