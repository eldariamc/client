package bspkrs.util.config.gui;

import bspkrs.client.util.HUDUtils;
import bspkrs.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @deprecated */
@Deprecated
public class GuiEditListEntries extends GuiListExtended {
   private final GuiEditList parentGuiEditList;
   private final Minecraft mc;
   private final IConfigProperty prop;
   private final List<IGuiEditListEntry> listEntries;
   private boolean isDefault;
   private boolean isChanged;
   private boolean canAddMoreEntries;
   private final int controlWidth;
   private final String[] beforeValues;

   public GuiEditListEntries(GuiEditList parent, Minecraft mc, IConfigProperty prop, String[] beforeValues, String[] currentValues) {
      super(mc, parent.width, parent.height, parent.titleLine2 != null?(parent.titleLine3 != null?43:33):23, parent.height - 32, 20);
      this.parentGuiEditList = parent;
      this.mc = mc;
      this.prop = prop;
      this.beforeValues = beforeValues;
      this.func_148130_a(false);
      this.isChanged = !Arrays.deepEquals(beforeValues, currentValues);
      this.isDefault = Arrays.deepEquals(currentValues, prop.getDefaults());
      this.canAddMoreEntries = !prop.isListLengthFixed() && (prop.getMaxListLength() == -1 || currentValues.length < prop.getMaxListLength());
      this.listEntries = new ArrayList();
      this.controlWidth = parent.width / 2 - (prop.isListLengthFixed()?0:48);
      if(prop.isList() && prop.getType().equals(ConfigGuiType.BOOLEAN)) {
         for(String value : currentValues) {
            this.listEntries.add(new GuiEditListEntries.EditListBooleanEntry(Boolean.valueOf(value).booleanValue()));
         }
      } else if(prop.isList() && prop.getType().equals(ConfigGuiType.INTEGER)) {
         for(String value : currentValues) {
            this.listEntries.add(new GuiEditListEntries.EditListIntegerEntry(Integer.parseInt(value)));
         }
      } else if(prop.isList() && prop.getType().equals(ConfigGuiType.DOUBLE)) {
         for(String value : currentValues) {
            this.listEntries.add(new GuiEditListEntries.EditListDoubleEntry(Double.parseDouble(value)));
         }
      } else if(prop.isList()) {
         for(String value : currentValues) {
            this.listEntries.add(new GuiEditListEntries.EditListStringEntry(value));
         }
      }

      if(!prop.isListLengthFixed()) {
         this.listEntries.add(new GuiEditListEntries.EditListBaseEntry());
      }

   }

   protected int getScrollBarX() {
      return this.width - this.width / 4;
   }

   public int getListWidth() {
      return this.parentGuiEditList.width;
   }

   public GuiEditListEntries.IGuiEditListEntry getListEntry(int index) {
      return (GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(index);
   }

   protected int getSize() {
      return this.listEntries.size();
   }

   private void addNewEntryAtIndex(int index) {
      if(this.prop.isList() && this.prop.getType().equals(ConfigGuiType.BOOLEAN)) {
         this.listEntries.add(index, new GuiEditListEntries.EditListBooleanEntry(true));
      } else if(this.prop.isList() && this.prop.getType().equals(ConfigGuiType.INTEGER)) {
         this.listEntries.add(index, new GuiEditListEntries.EditListIntegerEntry(0));
      } else if(this.prop.isList() && this.prop.getType().equals(ConfigGuiType.DOUBLE)) {
         this.listEntries.add(index, new GuiEditListEntries.EditListDoubleEntry(0.0D));
      } else if(this.prop.isList()) {
         this.listEntries.add(index, new GuiEditListEntries.EditListStringEntry(""));
      }

      this.canAddMoreEntries = !this.prop.isListLengthFixed() && (this.prop.getMaxListLength() == -1 || this.listEntries.size() - 1 < this.prop.getMaxListLength());
      this.keyTyped('\u0000', 207);
   }

   private void removeEntryAtIndex(int index) {
      this.listEntries.remove(index);
      this.canAddMoreEntries = !this.prop.isListLengthFixed() && (this.prop.getMaxListLength() == -1 || this.listEntries.size() - 1 < this.prop.getMaxListLength());
      this.keyTyped('\u0000', 207);
   }

   protected boolean isChanged() {
      return this.isChanged;
   }

   protected boolean isDefault() {
      return this.isDefault;
   }

   private void recalculateState() {
      this.isDefault = true;
      this.isChanged = false;
      int listLength = this.prop.isListLengthFixed()?this.listEntries.size():this.listEntries.size() - 1;
      if(listLength != this.prop.getDefaults().length) {
         this.isDefault = false;
      }

      if(listLength != this.beforeValues.length) {
         this.isChanged = true;
      }

      if(this.isDefault) {
         for(int i = 0; i < listLength; ++i) {
            if(!this.prop.getDefaults()[i].equals(((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue())) {
               this.isDefault = false;
            }
         }
      }

      if(!this.isChanged) {
         for(int i = 0; i < listLength; ++i) {
            if(!this.beforeValues[i].equals(((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue())) {
               this.isChanged = true;
            }
         }
      }

   }

   protected void keyTyped(char eventChar, int eventKey) {
      for(GuiEditListEntries.IGuiEditListEntry entry : this.listEntries) {
         entry.keyTyped(eventChar, eventKey);
      }

      this.recalculateState();
   }

   protected void updateScreen() {
      for(GuiEditListEntries.IGuiEditListEntry entry : this.listEntries) {
         entry.updateCursorCounter();
      }

   }

   protected void mouseClicked(int x, int y, int mouseEvent) {
      for(GuiEditListEntries.IGuiEditListEntry entry : this.listEntries) {
         entry.mouseClicked(x, y, mouseEvent);
      }

   }

   protected boolean isListSavable() {
      for(GuiEditListEntries.IGuiEditListEntry entry : this.listEntries) {
         if(!entry.isValueSavable()) {
            return false;
         }
      }

      return true;
   }

   protected void saveListChanges() {
      int listLength = this.prop.isListLengthFixed()?this.listEntries.size():this.listEntries.size() - 1;
      if(this.parentGuiEditList.slotIndex != -1 && this.parentGuiEditList.parentScreen != null && this.parentGuiEditList.parentScreen instanceof GuiConfig && ((GuiConfig)this.parentGuiEditList.parentScreen).propertyList.func_148180_b(this.parentGuiEditList.slotIndex) instanceof GuiPropertyList.EditListPropEntry) {
         GuiPropertyList.EditListPropEntry entry = (GuiPropertyList.EditListPropEntry)((GuiConfig)this.parentGuiEditList.parentScreen).propertyList.func_148180_b(this.parentGuiEditList.slotIndex);
         String[] as = new String[listLength];

         for(int i = 0; i < listLength; ++i) {
            as[i] = ((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue();
         }

         entry.setListFromChildScreen(as);
      } else if(this.prop.isList() && this.prop.getType().equals(ConfigGuiType.BOOLEAN)) {
         boolean[] abol = new boolean[listLength];

         for(int i = 0; i < listLength; ++i) {
            abol[i] = Boolean.parseBoolean(((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue());
         }

         this.prop.set(abol);
      } else if(this.prop.isList() && this.prop.getType().equals(ConfigGuiType.INTEGER)) {
         int[] ai = new int[listLength];

         for(int i = 0; i < listLength; ++i) {
            ai[i] = Integer.parseInt(((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue());
         }

         this.prop.set(ai);
      } else if(this.prop.isList() && this.prop.getType().equals(ConfigGuiType.DOUBLE)) {
         double[] ad = new double[listLength];

         for(int i = 0; i < listLength; ++i) {
            ad[i] = Double.parseDouble(((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue());
         }

         this.prop.set(ad);
      } else if(this.prop.isList()) {
         String[] as = new String[listLength];

         for(int i = 0; i < listLength; ++i) {
            as[i] = ((GuiEditListEntries.IGuiEditListEntry)this.listEntries.get(i)).getValue();
         }

         this.prop.set(as);
      }

   }

   protected void drawScreenPost(int mouseX, int mouseY, float f) {
      for(GuiEditListEntries.IGuiEditListEntry entry : this.listEntries) {
         entry.drawToolTip(mouseX, mouseY);
      }

   }

   @Override
   public IGuiListEntry func_148180_b(int p_148180_1_) {
      return this.listEntries.get(p_148180_1_);
   }

   public class EditListBaseEntry implements GuiEditListEntries.IGuiEditListEntry {
      protected final GuiButtonExt btnAddNewEntryAbove = new GuiButtonExt(0, 0, 0, 18, 18, "+");
      private final HoverChecker addNewEntryAboveHoverChecker;
      protected final GuiButtonExt btnRemoveEntry;
      private final HoverChecker removeEntryHoverChecker;
      private final List addNewToolTip;
      private final List removeToolTip;
      protected boolean isValidValue = true;
      protected boolean isValidated = false;

      public EditListBaseEntry() {
         this.btnAddNewEntryAbove.packedFGColour = HUDUtils.getColorCode('2', true);
         this.btnAddNewEntryAbove.enabled = GuiEditListEntries.this.parentGuiEditList.enabled;
         this.btnRemoveEntry = new GuiButtonExt(0, 0, 0, 18, 18, "x");
         this.btnRemoveEntry.packedFGColour = HUDUtils.getColorCode('c', true);
         this.btnRemoveEntry.enabled = GuiEditListEntries.this.parentGuiEditList.enabled;
         this.addNewEntryAboveHoverChecker = new HoverChecker(this.btnAddNewEntryAbove, 800);
         this.removeEntryHoverChecker = new HoverChecker(this.btnRemoveEntry, 800);
         this.addNewToolTip = new ArrayList();
         this.removeToolTip = new ArrayList();
         this.addNewToolTip.add(I18n.format("bspkrs.configgui.tooltip.addNewEntryAbove", new Object[0]));
         this.removeToolTip.add(I18n.format("bspkrs.configgui.tooltip.removeEntry", new Object[0]));
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         if(this.getValue() != null && this.isValidated) {
            GuiEditListEntries.this.mc.fontRenderer.drawString(this.isValidValue?EnumChatFormatting.GREEN + "✔":EnumChatFormatting.RED + "✕", listWidth / 4 - GuiEditListEntries.this.mc.fontRenderer.getStringWidth("✔") - 2, y + slotHeight / 2 - GuiEditListEntries.this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
         }

         int half = listWidth / 2;
         if(GuiEditListEntries.this.canAddMoreEntries) {
            this.btnAddNewEntryAbove.visible = true;
            this.btnAddNewEntryAbove.xPosition = half + (half / 2 - 44);
            this.btnAddNewEntryAbove.yPosition = y;
            this.btnAddNewEntryAbove.drawButton(GuiEditListEntries.this.mc, mouseX, mouseY);
         } else {
            this.btnAddNewEntryAbove.visible = false;
         }

         if(!GuiEditListEntries.this.prop.isListLengthFixed() && slotIndex != GuiEditListEntries.this.listEntries.size() - 1) {
            this.btnRemoveEntry.visible = true;
            this.btnRemoveEntry.xPosition = half + (half / 2 - 22);
            this.btnRemoveEntry.yPosition = y;
            this.btnRemoveEntry.drawButton(GuiEditListEntries.this.mc, mouseX, mouseY);
         } else {
            this.btnRemoveEntry.visible = false;
         }

      }

      public void drawToolTip(int mouseX, int mouseY) {
         boolean canHover = mouseY < GuiEditListEntries.this.bottom && mouseY > GuiEditListEntries.this.top;
         if(this.btnAddNewEntryAbove.visible && this.addNewEntryAboveHoverChecker.checkHover(mouseX, mouseY, canHover)) {
            GuiEditListEntries.this.parentGuiEditList.drawToolTip(this.addNewToolTip, mouseX, mouseY);
         }

         if(this.btnRemoveEntry.visible && this.removeEntryHoverChecker.checkHover(mouseX, mouseY, canHover)) {
            GuiEditListEntries.this.parentGuiEditList.drawToolTip(this.removeToolTip, mouseX, mouseY);
         }

      }

      public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         if(this.btnAddNewEntryAbove.mousePressed(GuiEditListEntries.this.mc, x, y)) {
            this.btnAddNewEntryAbove.playPressSound(GuiEditListEntries.this.mc.getSoundHandler());
            GuiEditListEntries.this.addNewEntryAtIndex(index);
            GuiEditListEntries.this.recalculateState();
            return true;
         } else if(this.btnRemoveEntry.mousePressed(GuiEditListEntries.this.mc, x, y)) {
            this.btnRemoveEntry.playPressSound(GuiEditListEntries.this.mc.getSoundHandler());
            GuiEditListEntries.this.removeEntryAtIndex(index);
            GuiEditListEntries.this.recalculateState();
            return true;
         } else {
            return false;
         }
      }

      public void func_148277_b(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         this.btnAddNewEntryAbove.mouseReleased(x, y);
         this.btnRemoveEntry.mouseReleased(x, y);
      }

      public void keyTyped(char eventChar, int eventKey) {
      }

      public void updateCursorCounter() {
      }

      public void mouseClicked(int x, int y, int mouseEvent) {
      }

      public boolean isValueSavable() {
         return this.isValidValue;
      }

      public String getValue() {
         return null;
      }
   }

   public class EditListBooleanEntry extends GuiEditListEntries.EditListBaseEntry {
      protected final GuiButtonExt btnValue;
      private boolean value;

      public EditListBooleanEntry(boolean value) {
         super();
         this.value = value;
         this.btnValue = new GuiButtonExt(0, 0, 0, GuiEditListEntries.this.controlWidth, 18, I18n.format(String.valueOf(value), new Object[0]));
         this.btnValue.enabled = GuiEditListEntries.this.parentGuiEditList.enabled;
         this.isValidated = false;
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         super.func_148279_a(slotIndex, x, y, listWidth, slotHeight, tessellator, mouseX, mouseY, isSelected);
         this.btnValue.xPosition = listWidth / 4;
         this.btnValue.yPosition = y;
         String trans = I18n.format(String.valueOf(this.value), new Object[0]);
         if(!trans.equals(String.valueOf(this.value))) {
            this.btnValue.displayString = trans;
         } else {
            this.btnValue.displayString = String.valueOf(this.value);
         }

         this.btnValue.packedFGColour = this.value?HUDUtils.getColorCode('2', true):HUDUtils.getColorCode('4', true);
         this.btnValue.drawButton(GuiEditListEntries.this.mc, mouseX, mouseY);
      }

      public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         if(this.btnValue.mousePressed(GuiEditListEntries.this.mc, x, y)) {
            this.btnValue.playPressSound(GuiEditListEntries.this.mc.getSoundHandler());
            this.value = !this.value;
            GuiEditListEntries.this.recalculateState();
            return true;
         } else {
            return super.func_148278_a(index, x, y, mouseEvent, relativeX, relativeY);
         }
      }

      public void func_148277_b(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         this.btnValue.mouseReleased(x, y);
         super.func_148277_b(index, x, y, mouseEvent, relativeX, relativeY);
      }

      public String getValue() {
         return String.valueOf(this.value);
      }
   }

   public class EditListDoubleEntry extends GuiEditListEntries.EditListStringEntry {
      public EditListDoubleEntry(double value) {
         super(String.valueOf(value));
         this.isValidated = true;
      }

      public void keyTyped(char eventChar, int eventKey) {
         if(GuiEditListEntries.this.parentGuiEditList.enabled || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
            String validChars = "0123456789";
            String before = this.textFieldValue.getText();
            if(validChars.contains(String.valueOf(eventChar)) || !before.startsWith("-") && this.textFieldValue.func_146198_h() == 0 && eventChar == 45 || !before.contains(".") && eventChar == 46 || eventKey == 14 || eventKey == 211 || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
               this.textFieldValue.textboxKeyTyped(GuiEditListEntries.this.parentGuiEditList.enabled?eventChar:'\u0000', eventKey);
            }

            if(!this.textFieldValue.getText().trim().isEmpty() && !this.textFieldValue.getText().trim().equals("-")) {
               try {
                  double value = Double.parseDouble(this.textFieldValue.getText().trim());
                  if(value >= GuiEditListEntries.this.prop.getMinDoubleValue() && value <= GuiEditListEntries.this.prop.getMaxDoubleValue()) {
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

      public String getValue() {
         return this.textFieldValue.getText().trim();
      }
   }

   public class EditListIntegerEntry extends GuiEditListEntries.EditListStringEntry {
      public EditListIntegerEntry(int value) {
         super(String.valueOf(value));
         this.isValidated = true;
      }

      public void keyTyped(char eventChar, int eventKey) {
         if(GuiEditListEntries.this.parentGuiEditList.enabled || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
            String validChars = "0123456789";
            String before = this.textFieldValue.getText();
            if(validChars.contains(String.valueOf(eventChar)) || !before.startsWith("-") && this.textFieldValue.func_146198_h() == 0 && eventChar == 45 || eventKey == 14 || eventKey == 211 || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
               this.textFieldValue.textboxKeyTyped(GuiEditListEntries.this.parentGuiEditList.enabled?eventChar:'\u0000', eventKey);
            }

            if(!this.textFieldValue.getText().trim().isEmpty() && !this.textFieldValue.getText().trim().equals("-")) {
               try {
                  long value = Long.parseLong(this.textFieldValue.getText().trim());
                  if(value >= (long)GuiEditListEntries.this.prop.getMinIntValue() && value <= (long)GuiEditListEntries.this.prop.getMaxIntValue()) {
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

      public String getValue() {
         return this.textFieldValue.getText().trim();
      }
   }

   public class EditListStringEntry extends GuiEditListEntries.EditListBaseEntry {
      protected final GuiTextField textFieldValue;

      public EditListStringEntry(String value) {
         super();
         this.textFieldValue = new GuiTextField(GuiEditListEntries.this.mc.fontRenderer, GuiEditListEntries.this.width / 4 + 1, 0, GuiEditListEntries.this.controlWidth - 3, 16);
         this.textFieldValue.func_146203_f(10000);
         this.textFieldValue.setText(value);
         this.isValidated = GuiEditListEntries.this.prop.getValidStringPattern() != null;
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         super.func_148279_a(slotIndex, x, y, listWidth, slotHeight, tessellator, mouseX, mouseY, isSelected);
         if(!GuiEditListEntries.this.prop.isListLengthFixed() && slotIndex == GuiEditListEntries.this.listEntries.size() - 1) {
            this.textFieldValue.func_146189_e(false);
         } else {
            this.textFieldValue.func_146189_e(true);

            try {
               if(ReflectionHelper.getIntValue(GuiTextField.class, "field_146210_g", "yPosition", this.textFieldValue, -1) != y + 1) {
                  ReflectionHelper.setIntValue(GuiTextField.class, "field_146210_g", "yPosition", this.textFieldValue, y + 1);
               }
            } catch (Throwable var11) {
               var11.printStackTrace();
            }

            this.textFieldValue.drawTextBox();
         }

      }

      public void keyTyped(char eventChar, int eventKey) {
         if(GuiEditListEntries.this.parentGuiEditList.enabled || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
            this.textFieldValue.textboxKeyTyped(GuiEditListEntries.this.parentGuiEditList.enabled?eventChar:'\u0000', eventKey);
            if(GuiEditListEntries.this.prop.getValidStringPattern() != null) {
               if(GuiEditListEntries.this.prop.getValidStringPattern().matcher(this.textFieldValue.getText().trim()).matches()) {
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

      public String getValue() {
         return this.textFieldValue.getText().trim();
      }
   }

   public interface IGuiEditListEntry extends IGuiListEntry {
      void keyTyped(char var1, int var2);

      void updateCursorCounter();

      void mouseClicked(int var1, int var2, int var3);

      void drawToolTip(int var1, int var2);

      boolean isValueSavable();

      String getValue();
   }
}
