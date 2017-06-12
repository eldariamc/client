package bspkrs.statuseffecthud;

import bspkrs.fml.util.DelayedGuiDisplayTicker;
import bspkrs.statuseffecthud.fml.gui.GuiSEHConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandStatusEffect extends CommandBase {
   public String getCommandName() {
      return "statuseffect";
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.statuseffect.usage";
   }

   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
      return true;
   }

   public int getRequiredPermissionLevel() {
      return 1;
   }

   public void processCommand(ICommandSender var1, String[] var2) {
      try {
         new DelayedGuiDisplayTicker(10, new GuiSEHConfig(null));
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   public int compareTo(Object object) {
      return object instanceof CommandBase?this.getCommandName().compareTo(((CommandBase)object).getCommandName()):0;
   }
}
