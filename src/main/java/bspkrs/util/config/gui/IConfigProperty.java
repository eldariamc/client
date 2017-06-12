package bspkrs.util.config.gui;

import java.util.List;
import java.util.regex.Pattern;

/** @deprecated */
@Deprecated
public interface IConfigProperty {
   boolean isProperty();

   boolean hasCustomIGuiConfigListEntry();

   Class getCustomIGuiConfigListEntryClass();

   String getName();

   String getQualifiedName();

   String getLanguageKey();

   String getComment();

   List getConfigPropertiesList(boolean var1);

   ConfigGuiType getType();

   boolean isList();

   boolean isListLengthFixed();

   int getMaxListLength();

   boolean isDefault();

   String getDefault();

   String[] getDefaults();

   void setToDefault();

   boolean isHotLoadable();

   boolean getBoolean();

   int getInt();

   String getString();

   double getDouble();

   boolean[] getBooleanList();

   int[] getIntList();

   String[] getStringList();

   double[] getDoubleList();

   void set(boolean var1);

   void set(int var1);

   void set(String var1);

   void set(double var1);

   void set(boolean[] var1);

   void set(int[] var1);

   void set(String[] var1);

   void set(double[] var1);

   String[] getValidValues();

   int getMinIntValue();

   int getMaxIntValue();

   double getMinDoubleValue();

   double getMaxDoubleValue();

   Pattern getValidStringPattern();
}
