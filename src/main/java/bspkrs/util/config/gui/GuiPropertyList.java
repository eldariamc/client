package bspkrs.util.config.gui;

import bspkrs.bspkrscore.fml.Reference;
import bspkrs.client.util.HUDUtils;
import bspkrs.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

/** @deprecated */
@Deprecated
public class GuiPropertyList extends GuiListExtended {
   public final GuiConfig parentGuiConfig;
   public final Minecraft mc;
   public List<IGuiConfigListEntry> listEntries;
   public int maxLabelTextWidth = 0;
   public int maxEntryRightBound = 0;
   public int labelX;
   public int controlX;
   public int controlWidth;
   public int resetX;
   public int scrollBarX;

   public GuiPropertyList(GuiConfig parent, Minecraft mc) {
      super(mc, parent.width, parent.height, parent.titleLine2 != null?33:23, parent.height - 32, 20);
      this.parentGuiConfig = parent;
      this.func_148130_a(false);
      this.mc = mc;
      this.listEntries = new ArrayList();
      int i = 0;
      String s = null;

      for(IConfigProperty prop : parent.properties) {
         if(prop != null && prop.isProperty()) {
            int l = mc.fontRenderer.getStringWidth(I18n.format(prop.getLanguageKey(), new Object[0]));
            if(l > this.maxLabelTextWidth) {
               this.maxLabelTextWidth = l;
            }
         }
      }

      int viewWidth = this.maxLabelTextWidth + 8 + this.width / 2;
      this.labelX = this.width / 2 - viewWidth / 2;
      this.controlX = this.labelX + this.maxLabelTextWidth + 8;
      this.resetX = this.width / 2 + viewWidth / 2 - 45;
      this.controlWidth = this.resetX - this.controlX - 5;
      this.scrollBarX = this.width;

      for(IConfigProperty prop : parent.properties) {
         if(prop != null) {
            if(prop.hasCustomIGuiConfigListEntry()) {
               try {
                  this.listEntries.add((IGuiConfigListEntry) prop.getCustomIGuiConfigListEntryClass().getConstructor(new Class[]{GuiConfig.class, GuiPropertyList.class, IConfigProperty.class}).newInstance(new Object[]{this.parentGuiConfig, this, prop}));
               } catch (Throwable var11) {
                  Reference.LOGGER.severe(String.format("There was a critical error instantiating the custom IGuiConfigListEntry for property %s.", prop.getName()));
                  var11.printStackTrace();
               }
            } else if(!prop.isProperty()) {
               if(prop.getType().equals(ConfigGuiType.CONFIG_CATEGORY)) {
                  this.listEntries.add(new GuiPropertyList.GuiConfigCategoryListEntry(this.parentGuiConfig, this, prop));
               }
            } else if(prop.isList()) {
               this.listEntries.add(new GuiPropertyList.EditListPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.BOOLEAN)) {
               this.listEntries.add(new GuiPropertyList.BooleanPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.INTEGER)) {
               this.listEntries.add(new GuiPropertyList.IntegerPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.DOUBLE)) {
               this.listEntries.add(new GuiPropertyList.DoublePropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.COLOR)) {
               if(prop.getValidValues() != null && prop.getValidValues().length > 0) {
                  this.listEntries.add(new GuiPropertyList.ColorPropEntry(this.parentGuiConfig, this, prop));
               } else {
                  this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
               }
            } else if(prop.getType().equals(ConfigGuiType.BLOCK_LIST)) {
               this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.ITEMSTACK_LIST)) {
               this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.ENTITY_LIST)) {
               this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.BIOME_LIST)) {
               this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
            } else if(prop.getType().equals(ConfigGuiType.DIMENSION_LIST)) {
               this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
            } else if(!prop.getType().equals(ConfigGuiType.MOD_ID)) {
               if(prop.getType().equals(ConfigGuiType.STRING)) {
                  if(prop.getValidValues() != null && prop.getValidValues().length > 0) {
                     this.listEntries.add(new GuiPropertyList.SelectStringPropEntry(this.parentGuiConfig, this, prop));
                  } else {
                     this.listEntries.add(new GuiPropertyList.StringPropEntry(this.parentGuiConfig, this, prop));
                  }
               }
            } else {
               Map<String, String> values = new TreeMap();

               /*for(ModContainer mod : Loader.instance().getActiveModList()) {
                  values.put(mod.getModId(), mod.getName());
               }*/
               values.put("keyrisium", "Keyrisium");

               values.put("minecraft", "Minecraft");
               this.listEntries.add(new GuiPropertyList.SelectValuePropEntry(this.parentGuiConfig, this, prop, values));
            }
         }
      }

   }

   protected void initGui() {
      this.width = this.parentGuiConfig.width;

      try {
         ReflectionHelper.setIntValue(GuiSlot.class, "field_148158_l", "height", this, this.parentGuiConfig.height);
      } catch (Throwable var4) {
         ;
      }

      this.maxLabelTextWidth = 0;

      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         if(entry.getLabelWidth() > this.maxLabelTextWidth) {
            this.maxLabelTextWidth = entry.getLabelWidth();
         }
      }

      this.top = this.parentGuiConfig.titleLine2 != null?33:23;
      this.bottom = this.parentGuiConfig.height - 32;
      this.left = 0;
      this.right = this.width;
      int viewWidth = this.maxLabelTextWidth + 8 + this.width / 2;
      this.labelX = this.width / 2 - viewWidth / 2;
      this.controlX = this.labelX + this.maxLabelTextWidth + 8;
      this.resetX = this.width / 2 + viewWidth / 2 - 45;
      this.maxEntryRightBound = 0;

      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         if(entry.getEntryRightBound() > this.maxEntryRightBound) {
            this.maxEntryRightBound = entry.getEntryRightBound();
         }
      }

      this.scrollBarX = this.maxEntryRightBound + 5;
      this.controlWidth = this.maxEntryRightBound - this.controlX - 45;
   }

   public int getSize() {
      return this.listEntries.size();
   }

   public GuiPropertyList.IGuiConfigListEntry func_148180_b(int index) {
      return (GuiPropertyList.IGuiConfigListEntry)this.listEntries.get(index);
   }

   public int getScrollBarX() {
      return this.scrollBarX;
   }

   public int getListWidth() {
      return this.parentGuiConfig.width;
   }

   public void keyTyped(char eventChar, int eventKey) {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         entry.keyTyped(eventChar, eventKey);
      }

   }

   public void updateScreen() {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         entry.updateCursorCounter();
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseEvent) {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         entry.mouseClicked(mouseX, mouseY, mouseEvent);
      }

   }

   public void saveProperties() {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         entry.saveProperty();
      }

   }

   public boolean areAllPropsDefault(boolean includeSubCategoryProps) {
      Iterator i$ = this.listEntries.iterator();

      while(true) {
         if(!i$.hasNext()) {
            return true;
         }

         GuiPropertyList.IGuiConfigListEntry entry = (GuiPropertyList.IGuiConfigListEntry)i$.next();
         if((includeSubCategoryProps || !(entry instanceof GuiPropertyList.GuiConfigCategoryListEntry)) && !entry.isDefault()) {
            break;
         }
      }

      return false;
   }

   public void setAllPropsDefault(boolean includeSubCategoryProps) {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         if(includeSubCategoryProps || !(entry instanceof GuiPropertyList.GuiConfigCategoryListEntry)) {
            entry.setToDefault();
         }
      }

   }

   public boolean areAnyPropsChanged(boolean includeSubCategoryProps) {
      Iterator i$ = this.listEntries.iterator();

      while(true) {
         if(!i$.hasNext()) {
            return false;
         }

         GuiPropertyList.IGuiConfigListEntry entry = (GuiPropertyList.IGuiConfigListEntry)i$.next();
         if((includeSubCategoryProps || !(entry instanceof GuiPropertyList.GuiConfigCategoryListEntry)) && entry.isChanged()) {
            break;
         }
      }

      return true;
   }

   public boolean areAnyPropsEnabled(boolean includeSubCategoryProps) {
      Iterator i$ = this.listEntries.iterator();

      while(true) {
         if(!i$.hasNext()) {
            return false;
         }

         GuiPropertyList.IGuiConfigListEntry entry = (GuiPropertyList.IGuiConfigListEntry)i$.next();
         if((includeSubCategoryProps || !(entry instanceof GuiPropertyList.GuiConfigCategoryListEntry)) && entry.enabled()) {
            break;
         }
      }

      return true;
   }

   public void undoAllChanges(boolean includeSubCategoryProps) {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         if(includeSubCategoryProps || !(entry instanceof GuiPropertyList.GuiConfigCategoryListEntry)) {
            entry.undoChanges();
         }
      }

   }

   public void drawScreenPost(int mouseX, int mouseY, float partialTicks) {
      for(GuiPropertyList.IGuiConfigListEntry entry : this.listEntries) {
         entry.drawToolTip(mouseX, mouseY);
      }

   }

   public static class BooleanPropEntry extends GuiPropertyList.ButtonPropEntry {
      protected final boolean beforeValue;
      protected boolean currentValue;

      private BooleanPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeValue = prop.getBoolean();
         this.currentValue = this.beforeValue;
         this.btnValue.enabled = this.enabled();
         this.updateValueButtonText();
      }

      public void updateValueButtonText() {
         this.btnValue.displayString = I18n.format(String.valueOf(this.currentValue), new Object[0]);
         this.btnValue.packedFGColour = this.currentValue?HUDUtils.getColorCode('2', true):HUDUtils.getColorCode('4', true);
      }

      public void valueButtonPressed(int slotIndex) {
         if(this.enabled()) {
            this.currentValue = !this.currentValue;
         }

      }

      public boolean isDefault() {
         return this.currentValue == Boolean.valueOf(this.prop.getDefault()).booleanValue();
      }

      public void setToDefault() {
         if(this.enabled()) {
            this.currentValue = Boolean.valueOf(this.prop.getDefault()).booleanValue();
            this.updateValueButtonText();
         }

      }

      public boolean isChanged() {
         return this.currentValue != this.beforeValue;
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.currentValue = this.beforeValue;
            this.updateValueButtonText();
         }

      }

      public void saveProperty() {
         if(this.enabled() && this.isChanged()) {
            this.prop.set(this.currentValue);
         }

      }
   }

   public abstract static class ButtonPropEntry extends GuiPropertyList.GuiConfigListEntryBase {
      protected final GuiButtonExt btnValue;

      public ButtonPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.btnValue = new GuiButtonExt(0, this.parentPropertyList.controlX, 0, this.parentPropertyList.controlWidth, 18, I18n.format(prop.getString(), new Object[0]));
      }

      public abstract void updateValueButtonText();

      public abstract void valueButtonPressed(int var1);

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         super.func_148279_a(slotIndex, x, y, listWidth, slotHeight, tessellator, mouseX, mouseY, isSelected);

         try {
            ReflectionHelper.setIntValue(GuiButton.class, "field_146120_f", "width", this.btnValue, this.parentPropertyList.controlWidth);
         } catch (Throwable var11) {
            var11.printStackTrace();
         }

         this.btnValue.xPosition = this.parentGuiConfig.propertyList.controlX;
         this.btnValue.yPosition = y;
         this.btnValue.drawButton(this.mc, mouseX, mouseY);
      }

      public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         if(this.btnValue.mousePressed(this.mc, x, y)) {
            this.btnValue.playPressSound(this.mc.getSoundHandler());
            this.valueButtonPressed(index);
            this.updateValueButtonText();
            return true;
         } else {
            return super.func_148278_a(index, x, y, mouseEvent, relativeX, relativeY);
         }
      }

      public void func_148277_b(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         super.func_148277_b(index, x, y, mouseEvent, relativeX, relativeY);
         this.btnValue.mouseReleased(x, y);
      }

      public void keyTyped(char eventChar, int eventKey) {
      }

      public void updateCursorCounter() {
      }

      public void mouseClicked(int x, int y, int mouseEvent) {
      }
   }

   public static class ColorPropEntry extends GuiPropertyList.SelectStringPropEntry {
      ColorPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.btnValue.enabled = this.enabled();
         this.updateValueButtonText();
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         this.btnValue.packedFGColour = HUDUtils.getColorCode(this.prop.getValidValues()[this.currentIndex].charAt(0), true);
         super.func_148279_a(slotIndex, x, y, listWidth, slotHeight, tessellator, mouseX, mouseY, isSelected);
      }

      public void updateValueButtonText() {
         this.btnValue.displayString = I18n.format(this.prop.getValidValues()[this.currentIndex], new Object[0]) + " - " + I18n.format("bspkrs.configgui.sampletext", new Object[0]);
      }
   }

   public static class DoublePropEntry extends GuiPropertyList.StringPropEntry {
      private final double beforeValue;

      private DoublePropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeValue = prop.getDouble();
      }

      public void keyTyped(char eventChar, int eventKey) {
         if(this.enabled() || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
            String validChars = "0123456789";
            String before = this.textFieldValue.getText();
            if(validChars.contains(String.valueOf(eventChar)) || !before.startsWith("-") && this.textFieldValue.func_146198_h() == 0 && eventChar == 45 || !before.contains(".") && eventChar == 46 || eventKey == 14 || eventKey == 211 || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
               this.textFieldValue.textboxKeyTyped(this.enabled()?eventChar:'\u0000', eventKey);
            }

            if(!this.textFieldValue.getText().trim().isEmpty() && !this.textFieldValue.getText().trim().equals("-")) {
               try {
                  double value = Double.parseDouble(this.textFieldValue.getText().trim());
                  if(value >= this.prop.getMinDoubleValue() && value <= this.prop.getMaxDoubleValue()) {
                     this.isValidValue = true;
                  } else {
                     this.isValidValue = false;
                  }
               } catch (Throwable var7) {
                  this.isValidValue = false;
               }
            } else {
               this.isValidValue = false;
            }
         }

      }

      public boolean isChanged() {
         try {
            return this.beforeValue != Double.parseDouble(this.textFieldValue.getText().trim());
         } catch (Throwable var2) {
            return true;
         }
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.textFieldValue.setText(String.valueOf(this.beforeValue));
         }

      }

      public void saveProperty() {
         if(this.enabled()) {
            if(this.isChanged() && this.isValidValue) {
               try {
                  double value = Double.parseDouble(this.textFieldValue.getText().trim());
                  this.prop.set(value);
               } catch (Throwable var4) {
                  this.prop.setToDefault();
               }
            } else if(this.isChanged() && !this.isValidValue) {
               try {
                  double value = Double.parseDouble(this.textFieldValue.getText().trim());
                  if(value < this.prop.getMinDoubleValue()) {
                     this.prop.set(this.prop.getMinDoubleValue());
                  } else {
                     this.prop.set(this.prop.getMaxDoubleValue());
                  }
               } catch (Throwable var3) {
                  this.prop.setToDefault();
               }
            }
         }

      }
   }

   public static class EditListPropEntry extends GuiPropertyList.ButtonPropEntry {
      private final String[] beforeValues;
      private String[] currentValues;

      public EditListPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeValues = prop.getStringList();
         this.currentValues = prop.getStringList();
         this.updateValueButtonText();
      }

      public void updateValueButtonText() {
         this.btnValue.displayString = "";

         for(String s : this.currentValues) {
            this.btnValue.displayString = this.btnValue.displayString + ", [" + s + "]";
         }

         this.btnValue.displayString = this.btnValue.displayString.replaceFirst(", ", "");
      }

      public void valueButtonPressed(int slotIndex) {
         this.mc.displayGuiScreen(new GuiEditList(this.parentGuiConfig, this.prop, slotIndex, this.currentValues, this.enabled()));
      }

      public void setListFromChildScreen(String[] newList) {
         if(this.enabled() && !Arrays.deepEquals(this.currentValues, newList)) {
            this.currentValues = newList;
            this.updateValueButtonText();
         }

      }

      public boolean isDefault() {
         return Arrays.deepEquals(this.prop.getDefaults(), this.currentValues);
      }

      public void setToDefault() {
         if(this.enabled()) {
            this.currentValues = this.prop.getDefaults();
            this.updateValueButtonText();
         }

      }

      public boolean isChanged() {
         return !Arrays.deepEquals(this.beforeValues, this.currentValues);
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.currentValues = this.beforeValues;
            this.updateValueButtonText();
         }

      }

      public void saveProperty() {
         if(this.enabled() && this.isChanged()) {
            this.prop.set(this.currentValues);
         }

      }
   }

   public static class GuiConfigCategoryListEntry extends GuiPropertyList.GuiConfigListEntryBase {
      protected GuiConfig subGuiConfig;
      protected final GuiButtonExt btnSelectCategory;

      public GuiConfigCategoryListEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.subGuiConfig = new GuiConfig(this.parentGuiConfig, this.prop.getConfigPropertiesList(false), this.prop.isHotLoadable(), this.parentGuiConfig.modID, this.parentGuiConfig.allowNonHotLoadConfigChanges, this.parentGuiConfig.title, (this.parentGuiConfig.titleLine2 == null?"":this.parentGuiConfig.titleLine2) + " > " + this.propName);
         this.btnSelectCategory = new GuiButtonExt(0, 0, 0, 300, 18, I18n.format(this.propName, new Object[0]));
         this.tooltipHoverChecker = new HoverChecker(this.btnSelectCategory, 800);
         this.drawLabel = false;
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         this.btnSelectCategory.xPosition = listWidth / 2 - 150;
         this.btnSelectCategory.yPosition = y;
         this.btnSelectCategory.drawButton(this.mc, mouseX, mouseY);
         super.func_148279_a(slotIndex, x, y, listWidth, slotHeight, tessellator, mouseX, mouseY, isSelected);
      }

      public void drawToolTip(int mouseX, int mouseY) {
         boolean canHover = mouseY < this.parentGuiConfig.propertyList.bottom && mouseY > this.parentGuiConfig.propertyList.top;
         if(this.tooltipHoverChecker.checkHover(mouseX, mouseY, canHover)) {
            this.parentGuiConfig.drawToolTip(this.toolTip, mouseX, mouseY);
         }

         super.drawToolTip(mouseX, mouseY);
      }

      public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         if(this.btnSelectCategory.mousePressed(this.mc, x, y)) {
            this.btnSelectCategory.playPressSound(this.mc.getSoundHandler());
            Minecraft.getMinecraft().displayGuiScreen(this.subGuiConfig);
            return true;
         } else {
            return super.func_148278_a(index, x, y, mouseEvent, relativeX, relativeY);
         }
      }

      public void func_148277_b(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         this.btnSelectCategory.mouseReleased(x, y);
      }

      public boolean isDefault() {
         return this.subGuiConfig.propertyList != null?this.subGuiConfig.propertyList.areAllPropsDefault(true):true;
      }

      public void setToDefault() {
         this.subGuiConfig.propertyList.setAllPropsDefault(true);
      }

      public void keyTyped(char eventChar, int eventKey) {
      }

      public void updateCursorCounter() {
      }

      public void mouseClicked(int x, int y, int mouseEvent) {
      }

      public void saveProperty() {
         if(this.subGuiConfig.propertyList != null) {
            this.subGuiConfig.propertyList.saveProperties();
         }

      }

      public boolean isChanged() {
         return this.subGuiConfig.propertyList != null?this.subGuiConfig.propertyList.areAnyPropsChanged(true):false;
      }

      public void undoChanges() {
         if(this.subGuiConfig.propertyList != null) {
            this.subGuiConfig.propertyList.undoAllChanges(true);
         }

      }

      public boolean enabled() {
         return this.parentGuiConfig.allowNonHotLoadConfigChanges || this.parentGuiConfig.areAllPropsHotLoadable || this.prop.isHotLoadable();
      }

      public int getLabelWidth() {
         return 0;
      }

      public int getEntryRightBound() {
         return this.parentPropertyList.width / 2 + 155 + 22 + 18;
      }
   }

   public abstract static class GuiConfigListEntryBase implements GuiPropertyList.IGuiConfigListEntry {
      protected final GuiConfig parentGuiConfig;
      protected final GuiPropertyList parentPropertyList;
      protected final IConfigProperty prop;
      protected final Minecraft mc;
      protected final String propName;
      protected final GuiButtonExt btnUndoChanges;
      protected final GuiButtonExt btnDefault;
      protected List toolTip;
      protected List undoToolTip;
      protected List defaultToolTip;
      protected boolean isValidValue = true;
      protected HoverChecker tooltipHoverChecker;
      protected HoverChecker undoHoverChecker;
      protected HoverChecker defaultHoverChecker;
      protected boolean drawLabel;

      public GuiConfigListEntryBase(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         this.parentGuiConfig = parentGuiConfig;
         this.parentPropertyList = parentPropertyList;
         this.prop = prop;
         this.mc = Minecraft.getMinecraft();
         String trans = I18n.format(prop.getLanguageKey(), new Object[0]);
         if(!trans.equals(prop.getLanguageKey())) {
            this.propName = trans;
         } else {
            this.propName = prop.getName();
         }

         this.btnUndoChanges = new GuiButtonExt(0, 0, 0, 18, 18, "↩");
         this.btnDefault = new GuiButtonExt(0, 0, 0, 18, 18, "☄");
         this.undoHoverChecker = new HoverChecker(this.btnUndoChanges, 800);
         this.defaultHoverChecker = new HoverChecker(this.btnDefault, 800);
         this.undoToolTip = Arrays.asList(new String[]{I18n.format("bspkrs.configgui.tooltip.undoChanges", new Object[0])});
         this.defaultToolTip = Arrays.asList(new String[]{I18n.format("bspkrs.configgui.tooltip.resetToDefault", new Object[0])});
         this.drawLabel = true;
         String comment;
         if(prop.getType().equals(ConfigGuiType.INTEGER)) {
            comment = I18n.format(prop.getLanguageKey() + ".tooltip", new Object[]{"\n" + EnumChatFormatting.AQUA, prop.getDefault(), Integer.valueOf(prop.getMinIntValue()), Integer.valueOf(prop.getMaxIntValue())});
         } else {
            comment = I18n.format(prop.getLanguageKey() + ".tooltip", new Object[]{"\n" + EnumChatFormatting.AQUA, prop.getDefault(), Double.valueOf(prop.getMinDoubleValue()), Double.valueOf(prop.getMaxDoubleValue())});
         }

         if(!comment.equals(prop.getLanguageKey() + ".tooltip")) {
            this.toolTip = this.mc.fontRenderer.listFormattedStringToWidth(EnumChatFormatting.GREEN + this.propName + "\n" + EnumChatFormatting.YELLOW + comment, 300);
         } else if(prop.getComment() != null && !prop.getComment().trim().isEmpty()) {
            this.toolTip = this.mc.fontRenderer.listFormattedStringToWidth(EnumChatFormatting.GREEN + this.propName + "\n" + EnumChatFormatting.YELLOW + prop.getComment(), 300);
         } else {
            this.toolTip = this.mc.fontRenderer.listFormattedStringToWidth(EnumChatFormatting.GREEN + this.propName + "\n" + EnumChatFormatting.RED + "No tooltip defined.", 300);
         }

      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         boolean isChanged = this.isChanged();
         if(this.drawLabel) {
            String label = (!this.isValidValue?EnumChatFormatting.RED.toString():(isChanged?EnumChatFormatting.WHITE.toString():EnumChatFormatting.GRAY.toString())) + (isChanged?EnumChatFormatting.ITALIC.toString():"") + this.propName;
            this.mc.fontRenderer.drawString(label, this.parentGuiConfig.propertyList.labelX, y + slotHeight / 2 - this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
         }

         this.btnUndoChanges.xPosition = this.parentPropertyList.scrollBarX - 44;
         this.btnUndoChanges.yPosition = y;
         this.btnUndoChanges.enabled = this.enabled() && isChanged;
         this.btnUndoChanges.displayString = "↩";
         this.btnUndoChanges.drawButton(this.mc, mouseX, mouseY);
         this.btnDefault.xPosition = this.parentPropertyList.scrollBarX - 22;
         this.btnDefault.yPosition = y;
         this.btnDefault.enabled = this.enabled() && !this.isDefault();
         this.btnDefault.displayString = "☄";
         this.btnDefault.drawButton(this.mc, mouseX, mouseY);
         if(this.tooltipHoverChecker == null) {
            this.tooltipHoverChecker = new HoverChecker(y, y + slotHeight, x, this.parentGuiConfig.propertyList.controlX - 8, 800);
         } else {
            this.tooltipHoverChecker.updateBounds(y, y + slotHeight, x, this.parentGuiConfig.propertyList.controlX - 8);
         }

      }

      public void drawToolTip(int mouseX, int mouseY) {
         boolean canHover = mouseY < this.parentGuiConfig.propertyList.bottom && mouseY > this.parentGuiConfig.propertyList.top;
         if(this.toolTip != null && this.tooltipHoverChecker != null && this.tooltipHoverChecker.checkHover(mouseX, mouseY, canHover)) {
            this.parentGuiConfig.drawToolTip(this.toolTip, mouseX, mouseY);
         }

         if(this.undoHoverChecker.checkHover(mouseX, mouseY, canHover)) {
            this.parentGuiConfig.drawToolTip(this.undoToolTip, mouseX, mouseY);
         }

         if(this.defaultHoverChecker.checkHover(mouseX, mouseY, canHover)) {
            this.parentGuiConfig.drawToolTip(this.defaultToolTip, mouseX, mouseY);
         }

      }

      public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         if(this.btnDefault.mousePressed(this.mc, x, y)) {
            this.btnDefault.playPressSound(this.mc.getSoundHandler());
            this.setToDefault();
            return true;
         } else if(this.btnUndoChanges.mousePressed(this.mc, x, y)) {
            this.btnUndoChanges.playPressSound(this.mc.getSoundHandler());
            this.undoChanges();
            return true;
         } else {
            return false;
         }
      }

      public void func_148277_b(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         this.btnDefault.mouseReleased(x, y);
      }

      public abstract boolean isDefault();

      public abstract void setToDefault();

      public abstract void keyTyped(char var1, int var2);

      public abstract void updateCursorCounter();

      public abstract void mouseClicked(int var1, int var2, int var3);

      public abstract boolean isChanged();

      public abstract void undoChanges();

      public abstract void saveProperty();

      public boolean enabled() {
         return this.parentGuiConfig.allowNonHotLoadConfigChanges || this.parentGuiConfig.areAllPropsHotLoadable || this.prop.isHotLoadable();
      }

      public int getLabelWidth() {
         return this.mc.fontRenderer.getStringWidth(this.propName);
      }

      public int getEntryRightBound() {
         return this.parentPropertyList.resetX + 40;
      }
   }

   public interface IGuiConfigListEntry extends IGuiListEntry {
      boolean enabled();

      void keyTyped(char var1, int var2);

      void updateCursorCounter();

      void mouseClicked(int var1, int var2, int var3);

      boolean isDefault();

      void setToDefault();

      void undoChanges();

      boolean isChanged();

      void saveProperty();

      void drawToolTip(int var1, int var2);

      int getLabelWidth();

      int getEntryRightBound();
   }

   public static class IntegerPropEntry extends GuiPropertyList.StringPropEntry {
      private final int beforeValue;

      private IntegerPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeValue = prop.getInt();
      }

      public void keyTyped(char eventChar, int eventKey) {
         if(this.enabled() || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
            String validChars = "0123456789";
            String before = this.textFieldValue.getText();
            if(validChars.contains(String.valueOf(eventChar)) || !before.startsWith("-") && this.textFieldValue.func_146198_h() == 0 && eventChar == 45 || eventKey == 14 || eventKey == 211 || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
               this.textFieldValue.textboxKeyTyped(this.enabled()?eventChar:'\u0000', eventKey);
            }

            if(!this.textFieldValue.getText().trim().isEmpty() && !this.textFieldValue.getText().trim().equals("-")) {
               try {
                  long value = Long.parseLong(this.textFieldValue.getText().trim());
                  if(value >= (long)this.prop.getMinIntValue() && value <= (long)this.prop.getMaxIntValue()) {
                     this.isValidValue = true;
                  } else {
                     this.isValidValue = false;
                  }
               } catch (Throwable var7) {
                  this.isValidValue = false;
               }
            } else {
               this.isValidValue = false;
            }
         }

      }

      public boolean isChanged() {
         try {
            return this.beforeValue != Integer.parseInt(this.textFieldValue.getText().trim());
         } catch (Throwable var2) {
            return true;
         }
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.textFieldValue.setText(String.valueOf(this.beforeValue));
         }

      }

      public void saveProperty() {
         if(this.enabled()) {
            if(this.isChanged() && this.isValidValue) {
               try {
                  int value = Integer.parseInt(this.textFieldValue.getText().trim());
                  this.prop.set(value);
               } catch (Throwable var3) {
                  this.prop.setToDefault();
               }
            } else if(this.isChanged() && !this.isValidValue) {
               try {
                  int value = Integer.parseInt(this.textFieldValue.getText().trim());
                  if(value < this.prop.getMinIntValue()) {
                     this.prop.set(this.prop.getMinIntValue());
                  } else {
                     this.prop.set(this.prop.getMaxIntValue());
                  }
               } catch (Throwable var2) {
                  this.prop.setToDefault();
               }
            }
         }

      }
   }

   public static class SelectStringPropEntry extends GuiPropertyList.ButtonPropEntry {
      protected final int beforeIndex;
      protected final int defaultIndex;
      protected int currentIndex;

      private SelectStringPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeIndex = this.getIndex(prop.getString());
         this.defaultIndex = this.getIndex(prop.getDefault());
         this.currentIndex = this.beforeIndex;
         this.btnValue.enabled = this.enabled();
         this.updateValueButtonText();
      }

      private int getIndex(String s) {
         for(int i = 0; i < this.prop.getValidValues().length; ++i) {
            if(this.prop.getValidValues()[i].equalsIgnoreCase(s)) {
               return i;
            }
         }

         return 0;
      }

      public void updateValueButtonText() {
         this.btnValue.displayString = I18n.format(this.prop.getValidValues()[this.currentIndex], new Object[0]);
      }

      public void valueButtonPressed(int slotIndex) {
         if(this.enabled()) {
            if(++this.currentIndex >= this.prop.getValidValues().length) {
               this.currentIndex = 0;
            }

            this.updateValueButtonText();
         }

      }

      public boolean isDefault() {
         return this.currentIndex == this.defaultIndex;
      }

      public void setToDefault() {
         if(this.enabled()) {
            this.currentIndex = this.defaultIndex;
            this.updateValueButtonText();
         }

      }

      public boolean isChanged() {
         return this.currentIndex != this.beforeIndex;
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.currentIndex = this.beforeIndex;
            this.updateValueButtonText();
         }

      }

      public void saveProperty() {
         if(this.enabled() && this.isChanged()) {
            this.prop.set(this.prop.getValidValues()[this.currentIndex]);
         }

      }
   }

   public static class SelectValuePropEntry extends GuiPropertyList.ButtonPropEntry {
      private final String beforeValue;
      private String currentValue;
      private Map selectableValues;

      public SelectValuePropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop, Map selectableValues) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeValue = prop.getString();
         this.currentValue = prop.getString();
         this.selectableValues = selectableValues;
         this.updateValueButtonText();
      }

      public void updateValueButtonText() {
         this.btnValue.displayString = this.currentValue;
      }

      public void valueButtonPressed(int slotIndex) {
         this.mc.displayGuiScreen(new GuiSelectString(this.parentGuiConfig, this.prop, slotIndex, this.selectableValues, this.currentValue, this.enabled()));
      }

      public void setValueFromChildScreen(String newValue) {
         if(this.enabled() && !this.currentValue.equals(newValue)) {
            this.currentValue = newValue;
            this.updateValueButtonText();
         }

      }

      public boolean isDefault() {
         return this.prop.getDefault() != null?this.prop.getDefault().equals(this.currentValue):true;
      }

      public void setToDefault() {
         if(this.enabled()) {
            this.currentValue = this.prop.getDefault();
            this.updateValueButtonText();
         }

      }

      public boolean isChanged() {
         return !this.beforeValue.equals(this.currentValue);
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.currentValue = this.beforeValue;
            this.updateValueButtonText();
         }

      }

      public void saveProperty() {
         if(this.enabled() && this.isChanged()) {
            this.prop.set(this.currentValue);
         }

      }
   }

   public static class StringPropEntry extends GuiPropertyList.GuiConfigListEntryBase {
      protected final GuiTextField textFieldValue;
      private final String beforeValue;

      private StringPropEntry(GuiConfig parentGuiConfig, GuiPropertyList parentPropertyList, IConfigProperty prop) {
         super(parentGuiConfig, parentPropertyList, prop);
         this.beforeValue = prop.getString();
         this.textFieldValue = new GuiTextField(this.mc.fontRenderer, this.parentPropertyList.controlX + 1, 0, this.parentPropertyList.controlWidth - 3, 16);
         this.textFieldValue.func_146203_f(10000);
         this.textFieldValue.setText(prop.getString());
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         super.func_148279_a(slotIndex, x, y, listWidth, slotHeight, tessellator, mouseX, mouseY, isSelected);

         /*try {
            if(ReflectionHelper.getIntValue(GuiTextField.class, "field_146209_f", "xPosition", this.textFieldValue, -1) != this.parentPropertyList.controlX + 2) {
               ReflectionHelper.setIntValue(GuiTextField.class, "field_146209_f", "xPosition", this.textFieldValue, this.parentPropertyList.controlX + 2);
            }*/
            if (this.textFieldValue.xPosition != this.parentPropertyList.controlX + 2)
               this.textFieldValue.xPosition = this.parentPropertyList.controlX + 2;

            /*if(ReflectionHelper.getIntValue(GuiTextField.class, "field_146210_g", "yPosition", this.textFieldValue, -1) != y + 1) {
               ReflectionHelper.setIntValue(GuiTextField.class, "field_146210_g", "yPosition", this.textFieldValue, y + 1);
            }*/
            if (this.textFieldValue.yPosition != y + 1)
               this.textFieldValue.yPosition = y + 1;

            /*if(ReflectionHelper.getIntValue(GuiTextField.class, "field_146218_h", "width", this.textFieldValue, -1) != this.parentPropertyList.controlWidth - 4) {
               ReflectionHelper.setIntValue(GuiTextField.class, "field_146218_h", "width", this.textFieldValue, this.parentPropertyList.controlWidth - 4);
            }*/
            if (this.textFieldValue.field_146218_h != this.parentPropertyList.controlWidth - 4)
               this.textFieldValue.field_146218_h = this.parentPropertyList.controlWidth - 4;
         /*} catch (Throwable var11) {
            var11.printStackTrace();
         }*/

         this.textFieldValue.drawTextBox();
      }

      public void keyTyped(char eventChar, int eventKey) {
         if(this.enabled() || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
            this.textFieldValue.textboxKeyTyped(this.enabled()?eventChar:'\u0000', eventKey);
            if(this.prop.getValidStringPattern() != null) {
               if(this.prop.getValidStringPattern().matcher(this.textFieldValue.getText().trim()).matches()) {
                  this.isValidValue = true;
               } else {
                  this.isValidValue = false;
               }
            }
         }

      }

      public void updateCursorCounter() {
         this.textFieldValue.updateCursorCounter();
      }

      public void mouseClicked(int x, int y, int mouseEvent) {
         this.textFieldValue.mouseClicked(x, y, mouseEvent);
      }

      public boolean isDefault() {
         return this.prop.getDefault() != null?this.prop.getDefault().equals(this.textFieldValue.getText()):true;
      }

      public void setToDefault() {
         if(this.enabled()) {
            this.textFieldValue.setText(this.prop.getDefault());
            this.keyTyped('\u0000', 199);
         }

      }

      public boolean isChanged() {
         return !this.beforeValue.equals(this.textFieldValue.getText());
      }

      public void undoChanges() {
         if(this.enabled()) {
            this.textFieldValue.setText(this.beforeValue);
         }

      }

      public void saveProperty() {
         if(this.enabled()) {
            if(this.isChanged() && this.isValidValue) {
               this.prop.set(this.textFieldValue.getText());
            } else if(this.isChanged() && !this.isValidValue) {
               this.prop.setToDefault();
            }
         }

      }

      public boolean enabled() {
         return this.parentGuiConfig.allowNonHotLoadConfigChanges || this.parentGuiConfig.areAllPropsHotLoadable || this.prop.isHotLoadable();
      }
   }
}
