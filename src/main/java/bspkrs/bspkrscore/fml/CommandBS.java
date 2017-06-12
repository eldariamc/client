package bspkrs.bspkrscore.fml;

import bspkrs.util.ModVersionChecker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandBS extends CommandBase {
   private static List version = new ArrayList();

   public String getCommandName() {
      return "bs";
   }

   public String getCommandUsage(ICommandSender sender) {
      return "commands.bs.usage";
   }

   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
      return true;
   }

   public int getRequiredPermissionLevel() {
      return 1;
   }

   public void processCommand(ICommandSender sender, String[] args) {
      if(!bspkrsCoreMod.instance.allowUpdateCheck) {
         throw new WrongUsageException("commands.bs.disabled", new Object[0]);
      } else if(args.length != 2) {
         throw new WrongUsageException("commands.bs.usage", new Object[0]);
      } else if(!args[0].equalsIgnoreCase("version")) {
         throw new WrongUsageException("commands.bs.usage", new Object[0]);
      } else {
         String[] message = ModVersionChecker.checkVersionForMod(args[1]);

         for(String s : message) {
            sender.addChatMessage(new ChatComponentText(s));
         }

      }
   }

   public List addTabCompletionOptions(ICommandSender sender, String[] args) {
      return args.length == 2?getListOfStringsMatchingLastWord(args, (String[])ModVersionChecker.getVersionCheckerMap().keySet().toArray(new String[0])):(args.length == 1?version:null);
   }

   public int compareTo(Object object) {
      return object instanceof CommandBase?this.getCommandName().compareTo(((CommandBase)object).getCommandName()):0;
   }

   static {
      version.add("version");
   }
}
