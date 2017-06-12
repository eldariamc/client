package bspkrs.util;

import bspkrs.util.config.Configuration;
import bspkrs.util.config.Property;

import java.io.File;

/** @deprecated */
@Deprecated
public class BSConfiguration extends Configuration {
   private final File fileRef;

   public BSConfiguration(File file) {
      super(file);
      this.fileRef = file;
   }

   public File getConfigFile() {
      return this.fileRef;
   }

   public boolean renameProperty(String category, String oldPropName, String newPropName) {
      if(this.hasCategory(category) && this.getCategory(category).containsKey(oldPropName) && !oldPropName.equalsIgnoreCase(newPropName)) {
         this.get(category, newPropName, this.getCategory(category).get(oldPropName).getString());
         this.getCategory(category).remove(oldPropName);
         return true;
      } else {
         return false;
      }
   }

   public boolean moveProperty(String oldCategory, String propName, String newCategory) {
      if(this.hasCategory(oldCategory) && this.getCategory(oldCategory).containsKey(propName)) {
         this.getCategory(newCategory).put(propName, this.getCategory(oldCategory).get(propName));
         return true;
      } else {
         return false;
      }
   }

   public String getString(String name, String category, String defaultValue, String comment) {
      return this.getString(name, category, defaultValue, comment, new String[0]);
   }

   public String getString(String name, String category, String defaultValue, String comment, String[] validValues) {
      Property prop = this.get(category, name, defaultValue);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getString();
   }

   public String[] getStringList(String name, String category, String[] defaultValues, String comment) {
      return this.getStringList(name, category, defaultValues, comment, new String[0]);
   }

   public String[] getStringList(String name, String category, String[] defaultValue, String comment, String[] validValues) {
      Property prop = this.get(category, name, defaultValue);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getStringList();
   }

   public boolean getBoolean(String name, String category, boolean defaultValue, String comment) {
      Property prop = this.get(category, name, defaultValue);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getBoolean(defaultValue);
   }

   public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
      Property prop = this.get(category, name, defaultValue);
      prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]";
      return prop.getInt(defaultValue) < minValue?minValue:(prop.getInt(defaultValue) > maxValue?maxValue:prop.getInt(defaultValue));
   }

   public float getFloat(String name, String category, float defaultValue, float minValue, float maxValue, String comment) {
      Property prop = this.get(category, name, Float.toString(defaultValue));
      prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]";

      try {
         return Float.parseFloat(prop.getString()) < minValue?minValue:(Float.parseFloat(prop.getString()) > maxValue?maxValue:Float.parseFloat(prop.getString()));
      } catch (Exception var9) {
         var9.printStackTrace();
         return defaultValue;
      }
   }
}
