package bspkrs.util.config.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/** @deprecated */
@Deprecated
public class GuiConfig extends GuiScreen {
   public final GuiScreen parentScreen;
   public String title;
   public String titleLine2;
   public final List<IConfigProperty> properties;
   public GuiPropertyList propertyList;
   private GuiButtonExt btnDefaultAll;
   private GuiButtonExt btnUndoAll;
   private GuiCheckBox chkApplyGlobally;
   /** @deprecated */
   @Deprecated
   protected Method saveAction;
   /** @deprecated */
   @Deprecated
   protected Object configObject;
   /** @deprecated */
   @Deprecated
   protected Method afterSaveAction;
   /** @deprecated */
   @Deprecated
   protected Object afterSaveObject;
   public final String modID;
   public final boolean allowNonHotLoadConfigChanges;
   public final boolean areAllPropsHotLoadable;
   private boolean needsRefresh;
   private HoverChecker checkBoxHoverChecker;

   /** @deprecated */
   @Deprecated
   public GuiConfig(GuiScreen parentScreen, IConfigProperty[] properties, boolean areAllPropsHotLoadable, String modID, boolean allowNonHotLoadConfigChanges, String title) {
      this(parentScreen, properties, areAllPropsHotLoadable, modID, allowNonHotLoadConfigChanges, title, (String)null);
   }

   public GuiConfig(GuiScreen parentScreen, List properties, boolean areAllPropsHotLoadable, String modID, boolean allowNonHotLoadConfigChanges, String title) {
      this(parentScreen, properties, areAllPropsHotLoadable, modID, allowNonHotLoadConfigChanges, title, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public GuiConfig(GuiScreen parentScreen, IConfigProperty[] properties, boolean areAllPropsHotLoadable, String modID, boolean allowNonHotLoadConfigChanges, String title, String titleLine2) {
      this(parentScreen, Arrays.asList(properties), areAllPropsHotLoadable, modID, allowNonHotLoadConfigChanges, title, titleLine2);
   }

   public GuiConfig(GuiScreen parentScreen, List<IConfigProperty> properties, boolean areAllPropsHotLoadable, String modID, boolean allowNonHotLoadConfigChanges, String title, String titleLine2) {
      this.title = "Config GUI";
      this.needsRefresh = true;
      this.mc = Minecraft.getMinecraft();
      this.parentScreen = parentScreen;
      this.properties = properties;
      this.propertyList = new GuiPropertyList(this, this.mc);
      this.areAllPropsHotLoadable = areAllPropsHotLoadable;
      this.modID = modID;
      this.allowNonHotLoadConfigChanges = allowNonHotLoadConfigChanges;
      if(title != null) {
         this.title = title;
      }

      this.titleLine2 = titleLine2;
      if(this.titleLine2 != null && this.titleLine2.startsWith(" > ")) {
         this.titleLine2 = this.titleLine2.replaceFirst(" > ", "");
      }

   }

   /** @deprecated */
   @Deprecated
   public GuiConfig(GuiScreen parentScreen, IConfigProperty[] properties, Method saveAction, Object configObject, Method afterSaveAction, Object afterSaveObject) {
      this(parentScreen, properties, saveAction, configObject, afterSaveAction, afterSaveObject, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public GuiConfig(GuiScreen parentScreen, IConfigProperty[] properties, Method saveAction, Object configObject, Method afterSaveAction, Object afterSaveObject, String titleLine2) {
      this.title = "Config GUI";
      this.needsRefresh = true;
      this.mc = Minecraft.getMinecraft();
      this.parentScreen = parentScreen;
      this.properties = Arrays.asList(properties);
      this.saveAction = saveAction;
      this.configObject = configObject;
      this.afterSaveAction = afterSaveAction;
      this.afterSaveObject = afterSaveObject;
      this.propertyList = new GuiPropertyList(this, this.mc);
      this.titleLine2 = titleLine2;
      this.areAllPropsHotLoadable = false;
      this.modID = null;
      this.allowNonHotLoadConfigChanges = true;
      if(this.mc.mcDataDir.getAbsolutePath().endsWith(".")) {
         this.title = configObject.toString().replace("\\", "/").replace(this.mc.mcDataDir.getAbsolutePath().replace("\\", "/").substring(0, this.mc.mcDataDir.getAbsolutePath().length() - 1), "/.minecraft/");
      } else {
         this.title = configObject.toString().replace("\\", "/").replace(this.mc.mcDataDir.getAbsolutePath().replace("\\", "/"), "/.minecraft");
      }

   }

   public static String getAbridgedConfigPath(String path) {
      Minecraft mc = Minecraft.getMinecraft();
      return mc.mcDataDir.getAbsolutePath().endsWith(".")?path.replace("\\", "/").replace(mc.mcDataDir.getAbsolutePath().replace("\\", "/").substring(0, mc.mcDataDir.getAbsolutePath().length() - 1), "/.minecraft/"):path.replace("\\", "/").replace(mc.mcDataDir.getAbsolutePath().replace("\\", "/"), "/.minecraft");
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      if(this.propertyList == null || this.needsRefresh) {
         this.propertyList = new GuiPropertyList(this, this.mc);
         this.needsRefresh = false;
      }

      int doneWidth = Math.max(this.mc.fontRenderer.getStringWidth(I18n.format("gui.done", new Object[0])) + 20, 100);
      int undoWidth = this.mc.fontRenderer.getStringWidth("↩ " + I18n.format("bspkrs.configgui.tooltip.undoChanges", new Object[0])) + 20;
      int resetWidth = this.mc.fontRenderer.getStringWidth("☄ " + I18n.format("bspkrs.configgui.tooltip.resetToDefault", new Object[0])) + 20;
      int checkWidth = this.mc.fontRenderer.getStringWidth(I18n.format("bspkrs.configgui.applyGlobally", new Object[0])) + 13;
      int buttonWidthHalf = (doneWidth + 5 + undoWidth + 5 + resetWidth + 5 + checkWidth) / 2;
      this.buttonList.add(new GuiButtonExt(2000, this.width / 2 - buttonWidthHalf, this.height - 29, doneWidth, 20, I18n.format("gui.done", new Object[0])));
      this.buttonList.add(this.btnDefaultAll = new GuiButtonExt(2001, this.width / 2 - buttonWidthHalf + doneWidth + 5 + undoWidth + 5, this.height - 29, resetWidth, 20, "☄ " + I18n.format("bspkrs.configgui.tooltip.resetToDefault", new Object[0])));
      this.buttonList.add(this.btnUndoAll = new GuiButtonExt(2002, this.width / 2 - buttonWidthHalf + doneWidth + 5, this.height - 29, undoWidth, 20, "↩ " + I18n.format("bspkrs.configgui.tooltip.undoChanges", new Object[0])));
      this.buttonList.add(this.chkApplyGlobally = new GuiCheckBox(2003, this.width / 2 - buttonWidthHalf + doneWidth + 5 + undoWidth + 5 + resetWidth + 5, this.height - 24, I18n.format("bspkrs.configgui.applyGlobally", new Object[0]), false));
      this.checkBoxHoverChecker = new HoverChecker(this.chkApplyGlobally, 800);
      this.propertyList.initGui();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void actionPerformed(GuiButton button) {
      if(button.id == 2000) {
         try {
            if(this.parentScreen == null || !(this.parentScreen instanceof GuiConfig) && this.propertyList.areAnyPropsChanged(true)) {
               this.propertyList.saveProperties();
               if(this.modID == null) {
                  if(this.saveAction != null) {
                     this.saveAction.invoke(this.configObject, new Object[0]);
                  }

                  if(this.afterSaveAction != null) {
                     this.afterSaveAction.invoke(this.afterSaveObject, new Object[0]);
                  }
               } else {

                  // Keyrisium - bypass Forge handler
                  switch (this.modID) {
                     case "ArmorStatusHUD":
                        bspkrs.armorstatushud.fml.ClientProxy.onConfigChanged();
                        break;
                     case "bspkrsCore":
                        bspkrs.bspkrscore.fml.bspkrsCoreMod.onConfigChanged();
                        break;
                     case "StatusEffectHUD":
                        bspkrs.statuseffecthud.fml.ClientProxy.onConfigChanged();
                        break;
                  }

                  /*ConfigChangedEvent event = new ConfigChangedEvent.OnConfigChangedEvent(this.modID, this.allowNonHotLoadConfigChanges);
                  FMLCommonHandler.instance().bus().post(event);
                  if(!event.getResult().equals(Result.DENY)) {
                     FMLCommonHandler.instance().bus().post(new ConfigChangedEvent.PostConfigChangedEvent(this.modID, this.allowNonHotLoadConfigChanges));
                  }*/
               }
            }
         } catch (Throwable var3) {
            var3.printStackTrace();
         }

         this.mc.displayGuiScreen(this.parentScreen);
      } else if(button.id == 2001) {
         this.propertyList.setAllPropsDefault(this.chkApplyGlobally.isChecked());
      } else if(button.id == 2002) {
         this.propertyList.undoAllChanges(this.chkApplyGlobally.isChecked());
      }

   }

   protected void mouseClicked(int x, int y, int mouseEvent) {
      if(mouseEvent != 0 || !this.propertyList.func_148179_a(x, y, mouseEvent)) {
         this.propertyList.mouseClicked(x, y, mouseEvent);
         super.mouseClicked(x, y, mouseEvent);
      }

   }

   protected void mouseMovedOrUp(int x, int y, int mouseEvent) {
      if(mouseEvent != 0 || !this.propertyList.func_148181_b(x, y, mouseEvent)) {
         super.mouseMovedOrUp(x, y, mouseEvent);
      }

   }

   protected void keyTyped(char eventChar, int eventKey) {
      if(eventKey == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else {
         this.propertyList.keyTyped(eventChar, eventKey);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.propertyList.updateScreen();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.propertyList.func_148128_a(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
      String title2 = this.titleLine2;
      int strWidth = this.mc.fontRenderer.getStringWidth(title2);
      int elipsisWidth = this.mc.fontRenderer.getStringWidth("...");
      if(strWidth > this.width - 6 && strWidth > elipsisWidth) {
         title2 = this.mc.fontRenderer.trimStringToWidth(title2, this.width - 6 - elipsisWidth).trim() + "...";
      }

      if(title2 != null) {
         this.drawCenteredString(this.fontRendererObj, title2, this.width / 2, 18, 16777215);
      }

      this.btnUndoAll.enabled = this.propertyList.areAnyPropsEnabled(this.chkApplyGlobally.isChecked()) && this.propertyList.areAnyPropsChanged(this.chkApplyGlobally.isChecked());
      this.btnDefaultAll.enabled = this.propertyList.areAnyPropsEnabled(this.chkApplyGlobally.isChecked()) && !this.propertyList.areAllPropsDefault(this.chkApplyGlobally.isChecked());
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.propertyList.drawScreenPost(mouseX, mouseY, partialTicks);
      if(this.checkBoxHoverChecker.checkHover(mouseX, mouseY)) {
         this.drawToolTip(Arrays.asList(new String[]{I18n.format("bspkrs.configgui.applyGlobally.tooltip", new Object[0])}), mouseX, mouseY);
      }

   }

   public void drawToolTip(List stringList, int x, int y) {
      this.func_146283_a(stringList, x, y/*, this.fontRendererObj*/);
   }
}
