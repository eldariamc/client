package bspkrs.util.config.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;

import java.util.*;
import java.util.Map.Entry;

/** @deprecated */
@Deprecated
public class GuiSelectStringEntries extends GuiListExtended {
   private GuiSelectString parentGuiSelectString;
   private Minecraft mc;
   private IConfigProperty prop;
   private List<ListEntry> listEntries;
   private boolean isDefault;
   private boolean isChanged;
   private final Map selectableValues;
   private final String beforeValue;
   public String currentValue;
   private int selectedIndex;
   private int maxEntryWidth = 0;

   public GuiSelectStringEntries(GuiSelectString parent, Minecraft mc, IConfigProperty prop, Map selectableValues, String beforeValue, String currentValue) {
      super(mc, parent.width, parent.height, parent.titleLine2 != null?(parent.titleLine3 != null?43:33):23, parent.height - 32, 11);
      this.parentGuiSelectString = parent;
      this.mc = mc;
      this.prop = prop;
      this.selectableValues = selectableValues;
      this.beforeValue = beforeValue;
      this.currentValue = currentValue;
      this.func_148130_a(true);
      this.isChanged = beforeValue.equals(currentValue);
      this.isDefault = currentValue.equals(prop.getDefault());
      this.listEntries = new ArrayList();
      int index = 0;
      List<Entry<String, String>> sortedList = new ArrayList(selectableValues.entrySet());
      Collections.sort(sortedList, new GuiSelectStringEntries.EntryComparator());

      for(Entry<String, String> entry : sortedList) {
         this.listEntries.add(new GuiSelectStringEntries.ListEntry(entry));
         if(mc.fontRenderer.getStringWidth((String)entry.getValue()) > this.maxEntryWidth) {
            this.maxEntryWidth = mc.fontRenderer.getStringWidth((String)entry.getValue());
         }

         if(this.currentValue.equals(entry.getKey())) {
            this.selectedIndex = index;
         }

         ++index;
      }

   }

   protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
      this.selectedIndex = index;
      this.currentValue = ((GuiSelectStringEntries.IGuiSelectStringListEntry)this.listEntries.get(index)).getValue();
   }

   protected boolean isSelected(int index) {
      return index == this.selectedIndex;
   }

   @Override
   public IGuiListEntry func_148180_b(int p_148180_1_) {
      return this.listEntries.get(p_148180_1_);
   }

   protected int getScrollBarX() {
      return this.width / 2 + this.maxEntryWidth / 2 + 5;
   }

   public int getListWidth() {
      return this.maxEntryWidth + 5;
   }

   public GuiSelectStringEntries.IGuiSelectStringListEntry getListEntry(int index) {
      return (GuiSelectStringEntries.IGuiSelectStringListEntry)this.listEntries.get(index);
   }

   protected int getSize() {
      return this.listEntries.size();
   }

   protected boolean isChanged() {
      return !this.beforeValue.equals(this.currentValue);
   }

   protected boolean isDefault() {
      return this.currentValue.equals(this.prop.getDefault());
   }

   protected void saveChanges() {
      if(this.parentGuiSelectString.slotIndex != -1 && this.parentGuiSelectString.parentScreen != null && this.parentGuiSelectString.parentScreen instanceof GuiConfig && ((GuiConfig)this.parentGuiSelectString.parentScreen).propertyList.func_148180_b(this.parentGuiSelectString.slotIndex) instanceof GuiPropertyList.SelectValuePropEntry) {
         GuiPropertyList.SelectValuePropEntry entry = (GuiPropertyList.SelectValuePropEntry)((GuiConfig)this.parentGuiSelectString.parentScreen).propertyList.func_148180_b(this.parentGuiSelectString.slotIndex);
         entry.setValueFromChildScreen(this.currentValue);
      } else {
         this.prop.set(this.currentValue);
      }

   }

   public static class EntryComparator implements Comparator<Entry> {
      public int compare(Entry o1, Entry o2) {
         int compare = ((String)o1.getValue()).toLowerCase().compareTo(((String)o2.getValue()).toLowerCase());
         if(compare == 0) {
            compare = ((String)o1.getKey()).toLowerCase().compareTo(((String)o2.getKey()).toLowerCase());
         }

         return compare;
      }
   }

   public interface IGuiSelectStringListEntry extends IGuiListEntry {
      String getValue();
   }

   public class ListEntry implements GuiSelectStringEntries.IGuiSelectStringListEntry {
      protected final Entry value;

      public ListEntry(Entry value) {
         this.value = value;
      }

      public void func_148279_a(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected) {
         GuiSelectStringEntries.this.mc.fontRenderer.drawString((String)this.value.getValue(), x + 1, y, slotIndex == GuiSelectStringEntries.this.selectedIndex?16777215:14737632);
      }

      public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
         return false;
      }

      public void func_148277_b(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
      }

      public String getValue() {
         return (String)this.value.getKey();
      }
   }
}
