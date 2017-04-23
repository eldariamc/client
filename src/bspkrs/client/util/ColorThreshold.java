package bspkrs.client.util;

import java.util.List;

public class ColorThreshold implements Comparable<ColorThreshold> {
   public int threshold;
   public String colorCode;

   public ColorThreshold(int t, String c) {
      this.threshold = t;
      this.colorCode = c;
   }

   public String toString() {
      return this.threshold + ", " + this.colorCode;
   }

   public int compareTo(ColorThreshold o) {
      return this.threshold > o.threshold?1:(this.threshold < o.threshold?-1:0);
   }

   public static String getColorCode(List<ColorThreshold> colorList, int value) {
      for(ColorThreshold ct : colorList) {
         if(value <= ct.threshold) {
            return ct.colorCode;
         }
      }

      return "f";
   }
}
