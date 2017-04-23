package bspkrs.helpers.entity.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public class EntityPlayerHelper {
   public static void addChatMessage(EntityPlayer player, IChatComponent message) {
      player.addChatMessage(message);
   }

   public static GameProfile getGameProfile(EntityPlayer player) {
      return player.getGameProfile();
   }
}
