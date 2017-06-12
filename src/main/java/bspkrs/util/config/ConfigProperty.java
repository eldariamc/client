package bspkrs.util.config;

import bspkrs.util.config.gui.ConfigGuiType;
import bspkrs.util.config.gui.IConfigProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/** @deprecated */
@Deprecated
public class ConfigProperty implements IConfigProperty {
   private Property prop;
   private Property.Type type;
   private final boolean isProperty;
   private ConfigCategory ctgy;

   public ConfigProperty(ConfigCategory ctgy) {
      this.ctgy = ctgy;
      this.isProperty = false;
   }

   public ConfigProperty(Property prop) {
      this(prop, prop.getType());
   }

   public ConfigProperty(Property prop, Property.Type type) {
      this.prop = prop;
      this.type = type;
      this.isProperty = true;
   }

   public List getConfigPropertiesList(boolean listCategoriesFirst) {
      if(this.isProperty) {
         return null;
      } else {
         List<IConfigProperty> props = new ArrayList();
         Iterator<ConfigCategory> ccI = this.ctgy.getChildren().iterator();
         Iterator<Property> pI = this.ctgy.getOrderedValuesSet().iterator();
         if(listCategoriesFirst) {
            while(ccI.hasNext()) {
               props.add(new ConfigProperty((ConfigCategory)ccI.next()));
            }
         }

         while(pI.hasNext()) {
            props.add(new ConfigProperty((Property)pI.next()));
         }

         if(!listCategoriesFirst) {
            while(ccI.hasNext()) {
               props.add(new ConfigProperty((ConfigCategory)ccI.next()));
            }
         }

         return props;
      }
   }

   public String getName() {
      return this.isProperty?this.prop.getName():this.ctgy.getName();
   }

   public boolean isProperty() {
      return this.isProperty;
   }

   public boolean hasCustomIGuiConfigListEntry() {
      return this.isProperty?this.prop.getCustomIGuiConfigListEntryClass() != null:this.ctgy.getCustomIGuiConfigListEntryClass() != null;
   }

   public Class getCustomIGuiConfigListEntryClass() {
      return this.isProperty?this.prop.getCustomIGuiConfigListEntryClass():this.ctgy.getCustomIGuiConfigListEntryClass();
   }

   public String getQualifiedName() {
      return this.isProperty?this.prop.getName():this.ctgy.getQualifiedName();
   }

   public ConfigGuiType getType() {
      return this.isProperty?(this.type == Property.Type.BOOLEAN?ConfigGuiType.BOOLEAN:(this.type == Property.Type.DOUBLE?ConfigGuiType.DOUBLE:(this.type == Property.Type.INTEGER?ConfigGuiType.INTEGER:(this.type == Property.Type.COLOR?ConfigGuiType.COLOR:(this.type == Property.Type.BLOCK_LIST?ConfigGuiType.BLOCK_LIST:(this.type == Property.Type.ITEMSTACK_LIST?ConfigGuiType.ITEMSTACK_LIST:(this.type == Property.Type.ENTITY_LIST?ConfigGuiType.ENTITY_LIST:(this.type == Property.Type.BIOME_LIST?ConfigGuiType.BIOME_LIST:(this.type == Property.Type.DIMENSION_LIST?ConfigGuiType.DIMENSION_LIST:(this.type == Property.Type.MOD_ID?ConfigGuiType.MOD_ID:ConfigGuiType.STRING)))))))))):ConfigGuiType.CONFIG_CATEGORY;
   }

   public boolean isList() {
      return this.isProperty?this.prop.isList():false;
   }

   public boolean isListLengthFixed() {
      return this.isProperty?this.prop.isListLengthFixed():false;
   }

   public int getMaxListLength() {
      return this.isProperty?this.prop.getMaxListLength():-1;
   }

   public String getComment() {
      return this.isProperty?this.prop.comment:this.ctgy.getComment();
   }

   public boolean isDefault() {
      return this.isProperty?this.prop.isDefault():true;
   }

   public void setToDefault() {
      if(this.isProperty) {
         this.prop.setToDefault();
      }

   }

   public boolean isHotLoadable() {
      return this.isProperty?this.prop.isHotLoadable():this.ctgy.isHotLoadable();
   }

   public boolean getBoolean() {
      return this.isProperty?this.prop.getBoolean():false;
   }

   public int getInt() {
      return this.isProperty?this.prop.getInt():0;
   }

   public String getString() {
      return this.isProperty?this.prop.getString():this.ctgy.getQualifiedName();
   }

   public double getDouble() {
      return this.isProperty?this.prop.getDouble():0.0D;
   }

   public boolean[] getBooleanList() {
      return this.isProperty?this.prop.getBooleanList():new boolean[0];
   }

   public int[] getIntList() {
      return this.isProperty?this.prop.getIntList():new int[0];
   }

   public String[] getStringList() {
      return this.isProperty?this.prop.getStringList():new String[0];
   }

   public double[] getDoubleList() {
      return this.isProperty?this.prop.getDoubleList():new double[0];
   }

   public void set(boolean bol) {
      if(this.isProperty) {
         this.prop.setValue(bol);
      }

   }

   public void set(int i) {
      if(this.isProperty) {
         this.prop.setValue(i);
      }

   }

   public void set(String s) {
      if(this.isProperty) {
         this.prop.setValue(s);
      }

   }

   public void set(double d) {
      if(this.isProperty) {
         this.prop.setValue(d);
      }

   }

   public void set(boolean[] bol) {
      if(this.isProperty) {
         this.prop.setValues(bol);
      }

   }

   public void set(int[] i) {
      if(this.isProperty) {
         this.prop.setValues(i);
      }

   }

   public void set(String[] s) {
      if(this.isProperty) {
         this.prop.setValues(s);
      }

   }

   public void set(double[] d) {
      if(this.isProperty) {
         this.prop.setValues(d);
      }

   }

   public String[] getValidValues() {
      return this.isProperty?this.prop.getValidValues():new String[0];
   }

   public String getLanguageKey() {
      return this.isProperty?this.prop.getLanguageKey():this.ctgy.getLanguagekey();
   }

   public String getDefault() {
      return this.isProperty?this.prop.getDefault():null;
   }

   public String[] getDefaults() {
      return this.isProperty?this.prop.getDefaults():null;
   }

   public int getMinIntValue() {
      return this.isProperty?this.prop.getMinIntValue():0;
   }

   public int getMaxIntValue() {
      return this.isProperty?this.prop.getMaxIntValue():0;
   }

   public double getMinDoubleValue() {
      return this.isProperty?this.prop.getMinDoubleValue():0.0D;
   }

   public double getMaxDoubleValue() {
      return this.isProperty?this.prop.getMaxDoubleValue():0.0D;
   }

   public Pattern getValidStringPattern() {
      return this.isProperty?this.prop.getValidStringPattern():null;
   }
}
