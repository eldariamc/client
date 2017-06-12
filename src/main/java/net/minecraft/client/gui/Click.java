package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Timer;
import net.minecraft.client.Minecraft;

public class Click extends Thread {
   protected Minecraft mc;
   public static int clics = 0;
   public static int secondesAntiAuto = 5;
   public static int maxclicks = secondesAntiAuto * 20;
   public static long time = 0L;
   private static List<Long> clicks = new ArrayList();

   public static void addClick() {
      clicks.add(Long.valueOf(System.currentTimeMillis()));
   }

   public static int getClicks() {
      Iterator iterator = clicks.iterator();

      while(iterator.hasNext()) {
         if(((Long)iterator.next()).longValue() < System.currentTimeMillis() - 1000L) {
            iterator.remove();
         }
      }

      return clicks.size();
   }

   public Click() {
      (new Timer(secondesAntiAuto * 1000, new Clicks())).start();
   }
}
