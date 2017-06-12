package bspkrs.util;

import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class CommonUtils {
   public static boolean isStringAllLettersOrDigits(CharSequence s) {
      for(int i = 0; i < s.length(); ++i) {
         if(!Character.isLetterOrDigit(s.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public static boolean isStringAllLettersOrDigitsOr(CharSequence s, String chrs) {
      for(int i = 0; i < s.length(); ++i) {
         if(!Character.isLetterOrDigit(s.charAt(i)) && !chrs.contains(s.subSequence(i, i + 1))) {
            return false;
         }
      }

      return true;
   }

   public static boolean isItemInList(int id, int md, String list) {
      if(list.trim().length() != 0) {
         String[] items = list.split(";");

         for(String item : items) {
            String[] values = item.split(",");
            if(parseInt(values[0]) == id) {
               if(values.length == 1) {
                  return true;
               }

               if(values.length == 2 && parseInt(values[1]) == md) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isIDInList(String id, int md, String list) {
      String[] itemArray = list.split(";");

      for(int i = 0; i < itemArray.length; ++i) {
         String[] values = itemArray[i].split(",");
         if(values.length > 1 && values[0].trim().equals(id) && parseInt(values[1], -1) == md) {
            return true;
         }

         if(values[0].trim().equals(id)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isIDInList(int id, int md, String list) {
      String[] itemArray = list.split(";");

      for(int i = 0; i < itemArray.length; ++i) {
         String[] values = itemArray[i].split(",");
         int tempID = parseInt(values[0], Integer.MAX_VALUE);
         if(tempID != Integer.MAX_VALUE) {
            if(values.length > 1 && tempID == id && parseInt(values[1], -1) == md) {
               return true;
            }

            if(tempID == id) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isIDInList(int id, String list) {
      return isIDInList(id, -1, list);
   }

   public static int[][][] stringToGroups(String string) {
      List<int[][]> groupList = new ArrayList();
      String[] groups = string.split(";");

      for(String group : groups) {
         groupList.add(stringToGroup(group));
      }

      int[][][] res = new int[groupList.size()][][];

      for(int i = 0; i < ((List)groupList).size(); ++i) {
         res[i] = (int[][])groupList.get(i);
      }

      return res;
   }

   public static int[][] stringToGroup(String string) {
      List<int[]> blockList = new ArrayList();
      String[] blocks = string.split(">");

      for(String block : blocks) {
         blockList.add(stringToBlock(block));
      }

      int[][] res = new int[blockList.size()][];

      for(int i = 0; i < ((List)blockList).size(); ++i) {
         res[i] = (int[])blockList.get(i);
      }

      return res;
   }

   public static int[] stringToBlock(String string) {
      int[] values = new int[]{0, -1};
      String[] src = string.split(",");
      if(src.length < 1) {
         return values;
      } else {
         values[0] = parseInt(src[0]);
         if(src.length < 2) {
            return values;
         } else {
            values[1] = parseInt(src[1]);
            return values;
         }
      }
   }

   public static boolean isBlockInGroups(int[] block, int[][][] groups) {
      for(int[][] group : groups) {
         if(indexOfBlock(block, group) > -1) {
            return true;
         }
      }

      return false;
   }

   public static int indexOfBlock(int[] block, int[][] group) {
      for(int i = 0; i < group.length; ++i) {
         if(block[0] == group[i][0] && (group[i][1] == -1 || block[1] == group[i][1])) {
            return i;
         }
      }

      return -1;
   }

   public static int[][] getRelatedBlocks(int[] block, int[][][] groups) {
      List<int[]> blockList = new ArrayList();

      for(int[][] group : groups) {
         if(indexOfBlock(block, group) > -1) {
            for(int i = 0; i < groups.length; ++i) {
               if(!blockList.contains(group[i])) {
                  blockList.add(i, group[i]);
               }
            }
         }
      }

      int[][] secondary = new int[blockList.size()][];

      for(int i = 0; i < ((List)blockList).size(); ++i) {
         secondary[i] = (int[])blockList.get(i);
      }

      return secondary;
   }

   public static int smallerBlockIndex(int[] block, int[][][] groups) {
      int min = Integer.MAX_VALUE;

      for(int[][] group : groups) {
         int i = indexOfBlock(block, group);
         if(i > -1 && i < min) {
            min = i;
         }
      }

      if(min == Integer.MAX_VALUE) {
         min = -1;
      }

      return min;
   }

   public static boolean isMetadataNull(int id, int[][][] groups) {
      for(int[][] group : groups) {
         for(int[] block : group) {
            if(block[0] == id && block[1] == -1) {
               return true;
            }
         }
      }

      return false;
   }

   public static int sqr(int value) {
      return value * value;
   }

   public static float sqr(float value) {
      return value * value;
   }

   public static int parseInt(String string) {
      return parseInt(string, 0);
   }

   public static int parseInt(String string, int defaultValue) {
      try {
         return Integer.parseInt(string.trim());
      } catch (NumberFormatException var3) {
         return defaultValue;
      }
   }

   public void playAtPitch(int i, World world, EntityPlayer entityplayer) {
      float f = (float)Math.pow(2.0D, (double)(i - 12) / 12.0D);
      world.playSoundAtEntity(entityplayer, "note.pling", 0.5F, f);
   }

   public static int getHighestGroundBlock(World world, int x, int y, int z) {
      while(y > 0 && (world.isAirBlock(x, y, z) || !world.isBlockNormalCubeDefault(x, y, z, true) || world.getBlock(x, y, z).canBlockStay(world, x, y, z))) {
         --y;
      }

      return y;
   }

   public static int getFirstNonAirBlockFromTop(World world, int x, int z) {
      int y;
      for(y = world.getActualHeight(); world.isAirBlock(x, y - 1, z) && y > 0; --y) {
         ;
      }

      return y;
   }

   public static int getSphericalDistance(Coord startPos, Coord endPos) {
      return (int)Math.round(Math.sqrt((double)(sqr(endPos.x - startPos.x) + sqr(endPos.z - startPos.z) + sqr(endPos.y - startPos.y))));
   }

   public static int getCubicDistance(Coord startPos, Coord endPos) {
      return Math.abs(endPos.x - startPos.x) + Math.abs(endPos.y - startPos.y) + Math.abs(endPos.z - startPos.z);
   }

   public static int getHorSquaredDistance(Coord startPos, Coord endPos) {
      return Math.abs(endPos.x - startPos.x) + Math.abs(endPos.z - startPos.z);
   }

   public static int getVerDistance(Coord startPos, Coord endPos) {
      return Math.abs(endPos.y - startPos.y);
   }

   public static double getDistanceRatioToCenter(int point1, int point2, int pos) {
      double radius = (double)Math.abs(point2 - point1) / 2.0D;
      double dar = Math.abs((double)Math.abs(pos - point1) - radius);
      return radius != 0.0D?dar / radius:0.0D;
   }

   public static void setHugeMushroom(World world, Random random, int x, int y, int z, Block block) {
      byte w = 3;
      byte cw = 4;
      byte h = 4;
      fillWithBlocks(world, x - w + 1, y, z - w, x + w - 1, y + h - 1, z - w, block, 10);
      fillWithBlocks(world, x - w + 1, y, z + w, x + w - 1, y + h - 1, z + w, block, 10);
      fillWithBlocks(world, x - w, y, z - w + 1, x - w, y + h - 1, z + w - 1, block, 10);
      fillWithBlocks(world, x + w, y, z - w + 1, x + w, y + h - 1, z + w - 1, block, 10);
      fillWithBlocksRounded(world, x - cw, y + h, z - cw, x + cw, y + h, z + cw, block, 14);
   }

   public static void fillWithBlocks(World world, int x1, int y1, int z1, int x2, int y2, int z2, Block block, int metadata) {
      for(int x = x1; x <= x2; ++x) {
         for(int y = y1; y <= y2; ++y) {
            for(int z = z1; z <= z2; ++z) {
               world.setBlock(x, y, z, block, metadata, 3);
            }
         }
      }

   }

   public static void fillWithBlocksRounded(World world, int x1, int y1, int z1, int x2, int y2, int z2, Block block, int metadata) {
      for(int x = x1; x <= x2; ++x) {
         for(int y = y1; y <= y2; ++y) {
            for(int z = z1; z <= z2; ++z) {
               double xd = getDistanceRatioToCenter(x1, x2, x);
               double yd = getDistanceRatioToCenter(y1, y2, y);
               double zd = getDistanceRatioToCenter(z1, z2, z);
               if(xd * xd + yd * yd + zd * zd <= 1.5D) {
                  world.setBlock(x, y, z, block, metadata, 3);
               }
            }
         }
      }

   }

   public static boolean isGamePaused(Minecraft mc) {
      return mc.currentScreen != null && (mc.currentScreen.doesGuiPauseGame() || mc.currentScreen instanceof GuiMainMenu);
   }

   public static String getMCTimeString(long worldTime, long fmt) {
      long HH = (long)((int)((worldTime / 1000L + 6L) % 24L));
      int MM = (int)((double)(worldTime % 1000L) / 1000.0D * 60.0D);
      boolean am = HH < 12L;
      HH = HH % fmt;
      String hour = "";
      if(fmt == 24L) {
         hour = (HH < 10L?"0":"") + String.valueOf(HH);
      } else {
         hour = String.valueOf(HH == 0L?12L:HH);
      }

      String min = (MM < 10?"0":"") + String.valueOf(MM);
      return hour + ":" + min + (fmt == 12L?(am?"AM":"PM"):"");
   }

   public static String ticksToTimeString(long ticks) {
      long secs = ticks / 20L;
      long mins = secs / 60L;
      long hrs = mins / 60L;
      long days = hrs / 24L;
      String time = "";
      if(days > 0L) {
         time = days + ":";
      }

      if(hrs > 0L) {
         time = time + (hrs % 24L < 10L && days > 0L?"0":"") + hrs % 24L + ":";
      }

      if(mins > 0L) {
         time = time + (mins % 60L < 10L && hrs > 0L?"0":"") + mins % 60L + ":";
      } else {
         time = time + "0:";
      }

      time = time + (secs % 60L < 10L?"0":"") + secs % 60L;
      return time;
   }

   public static String stringArrayToString(String[] sa) {
      return stringArrayToString(sa, "#");
   }

   public static String stringArrayToString(String[] sa, String separator) {
      String ret = "";

      for(String s : sa) {
         ret = ret + separator + " " + s;
      }

      return ret.replaceFirst(separator + " ", "");
   }

   public static String[] loadTextFromURL(URL url, Logger logger) {
      return loadTextFromURL(url, logger, (String[])(new String[]{""}), 0);
   }

   public static String[] loadTextFromURL(URL url, Logger logger, int timeoutMS) {
      return loadTextFromURL(url, logger, new String[]{""}, timeoutMS);
   }

   public static String[] loadTextFromURL(URL url, Logger logger, String defaultValue) {
      return loadTextFromURL(url, logger, (String[])(new String[]{defaultValue}), 0);
   }

   public static String[] loadTextFromURL(URL url, Logger logger, String defaultValue, int timeoutMS) {
      return loadTextFromURL(url, logger, new String[]{defaultValue}, timeoutMS);
   }

   public static String[] loadTextFromURL(URL url, Logger logger, String[] defaultValue) {
      return loadTextFromURL(url, logger, (String[])defaultValue, 0);
   }

   public static String[] loadTextFromURL(URL url, Logger logger, String[] defaultValue, int timeoutMS) {
      List<String> arraylist = new ArrayList();
      Scanner scanner = null;

      try {
         URLConnection uc = url.openConnection();
         uc.setReadTimeout(timeoutMS);
         uc.setConnectTimeout(timeoutMS);
         uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
         scanner = new Scanner(uc.getInputStream(), "UTF-8");
      } catch (Throwable var7) {
         logger.log(Level.WARN, String.format("Error retrieving remote string value at URL %s! Defaulting to %s", new Object[]{url.toString(), stringArrayToString(defaultValue)}));
         if(bspkrsCoreMod.instance.allowDebugOutput) {
            var7.printStackTrace();
         }

         if(scanner != null) {
            scanner.close();
         }

         return defaultValue;
      }

      while(scanner.hasNextLine()) {
         arraylist.add(scanner.nextLine());
      }

      scanner.close();
      return (String[])arraylist.toArray(new String[arraylist.size()]);
   }

   public static String getLogFileName() {
      try {
         Minecraft.getMinecraft();
         return "ForgeModLoader-client-0.log";
      } catch (Throwable var1) {
         return "ForgeModLoader-server-0.log";
      }
   }

   public static String getMinecraftDir() {
      try {
         return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
      } catch (NoClassDefFoundError var1) {
         return MinecraftServer.getServer().getFile("").getAbsolutePath();
      }
   }

   public static String getConfigDir() {
      File configDir = new File(getMinecraftDir(), "config");
      if (!configDir.exists())
         configDir.mkdir();
      return configDir.getAbsolutePath();
   }

   public static boolean isObfuscatedEnv() {
      try {
         Blocks.class.getField("air");
         return false;
      } catch (Throwable var1) {
         return true;
      }
   }

   public static MovingObjectPosition getPlayerLookingSpot(EntityPlayer player, boolean restrict) {
      float scale = 1.0F;
      float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * scale;
      float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * scale;
      double x = player.prevPosX + (player.posX - player.prevPosX) * (double)scale;
      double y = player.prevPosY + (player.posY - player.prevPosY) * (double)scale + 1.62D - (double)player.yOffset;
      double z = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)scale;
      Vec3 vector1 = Vec3.createVectorHelper(x, y, z);
      float cosYaw = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
      float sinYaw = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
      float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
      float sinPitch = MathHelper.sin(-pitch * 0.017453292F);
      float pitchAdjustedSinYaw = sinYaw * cosPitch;
      float pitchAdjustedCosYaw = cosYaw * cosPitch;
      double distance = 500.0D;
      if(player instanceof EntityPlayerMP && restrict) {
         //distance = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
      }

      Vec3 vector2 = vector1.addVector((double)pitchAdjustedSinYaw * distance, (double)sinPitch * distance, (double)pitchAdjustedCosYaw * distance);
      return player.worldObj.rayTraceBlocks(vector1, vector2);
   }

   public static void spawnExplosionParticleAtEntity(Entity entity) {
      for(int i = 0; i < 20; ++i) {
         double d0 = entity.worldObj.rand.nextGaussian() * 0.02D;
         double d1 = entity.worldObj.rand.nextGaussian() * 0.02D;
         double d2 = entity.worldObj.rand.nextGaussian() * 0.02D;
         double d3 = 10.0D;
         entity.worldObj.spawnParticle("explode", entity.posX + (double)(entity.worldObj.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width - d0 * d3, entity.posY + (double)(entity.worldObj.rand.nextFloat() * entity.height) - d1 * d3, entity.posZ + (double)(entity.worldObj.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width - d2 * d3, d0, d1, d2);
      }

   }
}
