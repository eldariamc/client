package bspkrs.helpers.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHelper {
   public static String getUniqueID(Item item) {
      return Item.itemRegistry.getNameForObject(item);
   }

   public static Item getItem(String uniqueID) {
      return (Item)Item.itemRegistry.getObject(uniqueID);
   }

   public static boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase elb) {
      return itemStack.getItem().onBlockDestroyed(itemStack, world, block, x, y, z, elb);
   }
}
