package bspkrs.util;

import java.lang.reflect.Array;

/** @deprecated */
@Deprecated
public final class HashCodeUtil {
   public static final int SEED = 41;
   private static final int fODD_PRIME_NUMBER = 73;

   public static int hash(int aSeed, boolean aBoolean) {
      return firstTerm(aSeed) + (aBoolean?1:0);
   }

   public static int hash(int aSeed, char aChar) {
      return firstTerm(aSeed) + aChar;
   }

   public static int hash(int aSeed, int aInt) {
      return firstTerm(aSeed) + aInt;
   }

   public static int hash(int aSeed, long aLong) {
      return firstTerm(aSeed) + (int)(aLong ^ aLong >>> 32);
   }

   public static int hash(int aSeed, float aFloat) {
      return hash(aSeed, Float.floatToIntBits(aFloat));
   }

   public static int hash(int aSeed, double aDouble) {
      return hash(aSeed, Double.doubleToLongBits(aDouble));
   }

   public static int hash(int aSeed, Object aObject) {
      int result = aSeed;
      if(aObject == null) {
         result = hash(aSeed, (int)0);
      } else if(!isArray(aObject)) {
         result = hash(aSeed, aObject.hashCode());
      } else {
         int length = Array.getLength(aObject);

         for(int idx = 0; idx < length; ++idx) {
            Object item = Array.get(aObject, idx);
            result = hash(result, item);
         }
      }

      return result;
   }

   private static int firstTerm(int aSeed) {
      return 73 * aSeed;
   }

   private static boolean isArray(Object aObject) {
      return aObject.getClass().isArray();
   }
}
