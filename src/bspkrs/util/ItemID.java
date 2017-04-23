package bspkrs.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemID {
   public final String id;
   public final int damage;

   public ItemID(String id, int damage) {
      this.id = id;
      this.damage = damage;
   }

   public ItemID(String id) {
      this((String)id, -1);
   }

   public ItemID(ItemStack itemStack, int damage) {
      this(itemStack.getItem(), damage);
   }

   public ItemID(ItemStack itemStack) {
      this((Item)itemStack.getItem(), -1);
   }

   public ItemID(Item item, int damage) {
      this(Item.itemRegistry.getNameForObject(item), damage);
   }

   public ItemID(Item item) {
      this(Item.itemRegistry.getNameForObject(item), -1);
   }

   public ItemID(String format, String delimiter) {
      String[] parts = format.split(delimiter);
      if(parts.length > 1) {
         this.id = parts[0].trim();
         this.damage = CommonUtils.parseInt(parts[1], -1);
      } else {
         this.id = parts[0].trim();
         this.damage = -1;
      }

   }

   public ItemID clone() {
      return new ItemID(this.id, this.damage);
   }

   public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      } else if(!(obj instanceof ItemID)) {
         return false;
      } else {
         ItemID o = (ItemID)obj;
         return o.damage != -1 && this.damage != -1?(this.id != null?this.id.equals(o.id) && this.damage == o.damage:o.id == null && this.damage == o.damage):(this.id != null?this.id.equals(o.id):o.id == null);
      }
   }

   public int hashCode() {
      return this.id.hashCode() * 31;
   }

   public String toString() {
      return this.damage == -1?this.id + "":this.id + ", " + this.damage;
   }
}
