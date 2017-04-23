package bspkrs.util.config;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @deprecated */
@Deprecated
public class Configuration {
   public static final String CATEGORY_GENERAL = "general";
   public static final String ALLOWED_CHARS = "._-";
   public static final String DEFAULT_ENCODING = "UTF-8";
   public static final String CATEGORY_SPLITTER = ".";
   public static final String NEW_LINE = System.getProperty("line.separator");
   public static final String COMMENT_SEPARATOR = "##########################################################################################################";
   private static final String CONFIG_VERSION_MARKER = "~CONFIG_VERSION";
   private static final Pattern CONFIG_START = Pattern.compile("START: \"([^\\\"]+)\"");
   private static final Pattern CONFIG_END = Pattern.compile("END: \"([^\\\"]+)\"");
   public static final CharMatcher allowedProperties = CharMatcher.JAVA_LETTER_OR_DIGIT.or(CharMatcher.anyOf("._-"));
   private static Configuration PARENT = null;
   File file;
   private Map<String, ConfigCategory> categories;
   private final Map<String, Configuration> children;
   private boolean caseSensitiveCustomCategories;
   public String defaultEncoding;
   private String fileName;
   public boolean isChild;
   private boolean changed;
   private String definedConfigVersion;
   private String loadedConfigVersion;

   public Configuration() {
      this.categories = new TreeMap<>();
      this.children = new TreeMap<>();
      this.defaultEncoding = "UTF-8";
      this.fileName = null;
      this.isChild = false;
      this.changed = false;
      this.definedConfigVersion = null;
      this.loadedConfigVersion = null;
   }

   public Configuration(File file) {
      this(file, (String)null);
   }

   public Configuration(File file, String configVersion) {
      this.categories = new TreeMap();
      this.children = new TreeMap();
      this.defaultEncoding = "UTF-8";
      this.fileName = null;
      this.isChild = false;
      this.changed = false;
      this.definedConfigVersion = null;
      this.loadedConfigVersion = null;
      this.file = file;
      this.definedConfigVersion = configVersion;
      String basePath = new File(".").getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");
      String path = file.getAbsolutePath().replace(File.separatorChar, '/').replace("/./", "/").replace(basePath, "");
      if(PARENT != null) {
         PARENT.setChild(path, this);
         this.isChild = true;
      } else {
         this.fileName = path;
         this.load();
      }

   }

   public Configuration(File file, String configVersion, boolean caseSensitiveCustomCategories) {
      this(file, configVersion);
      this.caseSensitiveCustomCategories = caseSensitiveCustomCategories;
   }

   public Configuration(File file, boolean caseSensitiveCustomCategories) {
      this(file, (String)null, caseSensitiveCustomCategories);
   }

   public String toString() {
      return this.file.getAbsolutePath();
   }

   public String getDefinedConfigVersion() {
      return this.definedConfigVersion;
   }

   public String getLoadedConfigVersion() {
      return this.loadedConfigVersion;
   }

   public Property get(String category, String key, boolean defaultValue) {
      return this.get(category, key, defaultValue, (String)null);
   }

   public Property get(String category, String key, boolean defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, false);
   }

   public Property get(String category, String key, boolean defaultValue, String comment, boolean isHotLoadable) {
      Property prop = this.get(category, key, Boolean.toString(defaultValue), comment, Property.Type.BOOLEAN);
      prop.setDefaultValue(Boolean.toString(defaultValue));
      prop.setIsHotLoadable(isHotLoadable);
      if(!prop.isBooleanValue()) {
         prop.setValue(defaultValue);
      }

      return prop;
   }

   public Property get(String category, String key, boolean[] defaultValues) {
      return this.get(category, key, defaultValues, (String)null);
   }

   public Property get(String category, String key, boolean[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, false, -1, false);
   }

   public Property get(String category, String key, boolean[] defaultValues, String comment, boolean isHotLoadable) {
      return this.get(category, key, defaultValues, comment, false, -1, isHotLoadable);
   }

   public Property get(String category, String key, boolean[] defaultValues, String comment, boolean isListLengthFixed, int maxListLength, boolean isHotLoadable) {
      String[] values = new String[defaultValues.length];

      for(int i = 0; i < defaultValues.length; ++i) {
         values[i] = Boolean.toString(defaultValues[i]);
      }

      Property prop = this.get(category, key, values, comment, Property.Type.BOOLEAN);
      prop.setDefaultValues(values);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      prop.setIsHotLoadable(isHotLoadable);
      if(!prop.isBooleanList()) {
         prop.setValues(values);
      }

      return prop;
   }

   public Property get(String category, String key, int defaultValue) {
      return this.get(category, key, defaultValue, (String)null, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
   }

   public Property get(String category, String key, int defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
   }

   public Property get(String category, String key, int defaultValue, String comment, int minValue, int maxValue, boolean isHotLoadable) {
      Property prop = this.get(category, key, Integer.toString(defaultValue), comment, Property.Type.INTEGER);
      prop.setDefaultValue(Integer.toString(defaultValue));
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      prop.setIsHotLoadable(isHotLoadable);
      if(!prop.isIntValue()) {
         prop.setValue(defaultValue);
      }

      return prop;
   }

   public Property get(String category, String key, int[] defaultValues) {
      return this.get(category, key, defaultValues, (String)null);
   }

   public Property get(String category, String key, int[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, Integer.MIN_VALUE, Integer.MAX_VALUE, false, -1, false);
   }

   public Property get(String category, String key, int[] defaultValues, String comment, int minValue, int maxValue, boolean isHotLoadable) {
      return this.get(category, key, defaultValues, comment, minValue, maxValue, false, -1, isHotLoadable);
   }

   public Property get(String category, String key, int[] defaultValues, String comment, int minValue, int maxValue, boolean isListLengthFixed, int maxListLength, boolean isHotLoadable) {
      String[] values = new String[defaultValues.length];

      for(int i = 0; i < defaultValues.length; ++i) {
         values[i] = Integer.toString(defaultValues[i]);
      }

      Property prop = this.get(category, key, values, comment, Property.Type.INTEGER);
      prop.setDefaultValues(values);
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      prop.setIsHotLoadable(isHotLoadable);
      if(!prop.isIntList()) {
         prop.setValues(values);
      }

      return prop;
   }

   public Property get(String category, String key, double defaultValue) {
      return this.get(category, key, defaultValue, (String)null);
   }

   public Property get(String category, String key, double defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, -1.7976931348623157E308D, Double.MAX_VALUE, false);
   }

   public Property get(String category, String key, double defaultValue, String comment, double minValue, double maxValue, boolean isHotLoadable) {
      Property prop = this.get(category, key, Double.toString(defaultValue), comment, Property.Type.DOUBLE);
      prop.setDefaultValue(Double.toString(defaultValue));
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      prop.setIsHotLoadable(isHotLoadable);
      if(!prop.isDoubleValue()) {
         prop.setValue(defaultValue);
      }

      return prop;
   }

   public Property get(String category, String key, double[] defaultValues) {
      return this.get(category, key, (double[])defaultValues, (String)null);
   }

   public Property get(String category, String key, double[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, -1.7976931348623157E308D, Double.MAX_VALUE, false, -1, false);
   }

   public Property get(String category, String key, double[] defaultValues, String comment, double minValue, double maxValue, boolean isHotLoadable) {
      return this.get(category, key, defaultValues, comment, minValue, maxValue, false, -1, isHotLoadable);
   }

   public Property get(String category, String key, double[] defaultValues, String comment, double minValue, double maxValue, boolean isListLengthFixed, int maxListLength, boolean isHotLoadable) {
      String[] values = new String[defaultValues.length];

      for(int i = 0; i < defaultValues.length; ++i) {
         values[i] = Double.toString(defaultValues[i]);
      }

      Property prop = this.get(category, key, values, comment, Property.Type.DOUBLE);
      prop.setDefaultValues(values);
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      prop.setIsHotLoadable(isHotLoadable);
      if(!prop.isDoubleList()) {
         prop.setValues(values);
      }

      return prop;
   }

   public Property get(String category, String key, String defaultValue) {
      return this.get(category, key, defaultValue, (String)null);
   }

   public Property get(String category, String key, String defaultValue, String comment) {
      return this.get(category, key, defaultValue, comment, Property.Type.STRING);
   }

   public Property get(String category, String key, String defaultValue, String comment, Pattern validationPattern) {
      Property prop = this.get(category, key, defaultValue, comment, Property.Type.STRING);
      prop.setValidStringPattern(validationPattern);
      return prop;
   }

   public Property get(String category, String key, String defaultValue, String comment, String[] validValues) {
      Property prop = this.get(category, key, defaultValue, comment, Property.Type.STRING);
      prop.setValidValues(validValues);
      return prop;
   }

   public Property get(String category, String key, String defaultValue, String comment, Pattern validationPattern, boolean isHotLoadable) {
      Property prop = this.get(category, key, defaultValue, comment, Property.Type.STRING);
      prop.setValidStringPattern(validationPattern);
      prop.setIsHotLoadable(isHotLoadable);
      return prop;
   }

   public Property get(String category, String key, String defaultValue, String comment, String[] validValues, boolean isHotLoadable) {
      Property prop = this.get(category, key, defaultValue, comment, Property.Type.STRING);
      prop.setValidValues(validValues);
      prop.setIsHotLoadable(isHotLoadable);
      return prop;
   }

   public Property get(String category, String key, String[] defaultValues) {
      return this.get(category, key, defaultValues, (String)null, false, -1, (Pattern)null, false);
   }

   public Property get(String category, String key, String[] defaultValues, String comment) {
      return this.get(category, key, defaultValues, comment, false, -1, (Pattern)null, false);
   }

   public Property get(String category, String key, String[] defaultValues, String comment, Pattern validationPattern) {
      return this.get(category, key, defaultValues, comment, false, -1, validationPattern, false);
   }

   public Property get(String category, String key, String[] defaultValues, String comment, Pattern validationPattern, boolean isHotLoadable) {
      return this.get(category, key, defaultValues, comment, false, -1, validationPattern, isHotLoadable);
   }

   public Property get(String category, String key, String[] defaultValues, String comment, boolean isListLengthFixed, int maxListLength, Pattern validationPattern, boolean isHotLoadable) {
      Property prop = this.get(category, key, defaultValues, comment, Property.Type.STRING);
      prop.setIsListLengthFixed(isListLengthFixed);
      prop.setMaxListLength(maxListLength);
      prop.setValidStringPattern(validationPattern);
      prop.setIsHotLoadable(isHotLoadable);
      return prop;
   }

   public Property get(String category, String key, String defaultValue, String comment, Property.Type type) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      ConfigCategory cat = this.getCategory(category);
      if(cat.containsKey(key)) {
         Property prop = cat.get(key);
         if(prop.getType() == null) {
            prop = new Property(prop.getName(), prop.getString(), type);
            cat.put(key, prop);
         }

         prop.setDefaultValue(defaultValue);
         prop.comment = comment;
         return prop;
      } else if(defaultValue != null) {
         Property prop = new Property(key, defaultValue, type);
         prop.setValue(defaultValue);
         cat.put(key, prop);
         prop.setDefaultValue(defaultValue);
         prop.comment = comment;
         return prop;
      } else {
         return null;
      }
   }

   public Property get(String category, String key, String[] defaultValues, String comment, Property.Type type) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      ConfigCategory cat = this.getCategory(category);
      if(cat.containsKey(key)) {
         Property prop = cat.get(key);
         if(prop.getType() == null) {
            prop = new Property(prop.getName(), prop.getString(), type);
            cat.put(key, prop);
         }

         prop.setDefaultValues(defaultValues);
         prop.comment = comment;
         return prop;
      } else if(defaultValues != null) {
         Property prop = new Property(key, defaultValues, type);
         prop.setDefaultValues(defaultValues);
         prop.comment = comment;
         cat.put(key, prop);
         return prop;
      } else {
         return null;
      }
   }

   public boolean hasCategory(String category) {
      return this.categories.get(category) != null;
   }

   public boolean hasKey(String category, String key) {
      ConfigCategory cat = (ConfigCategory)this.categories.get(category);
      return cat != null && cat.containsKey(key);
   }

   public void load() {
      if(PARENT == null || PARENT == this) {
         BufferedReader buffer = null;
         Configuration.UnicodeInputStreamReader input = null;

         label18: {
            try {
               if(this.file.getParentFile() != null) {
                  this.file.getParentFile().mkdirs();
               }

               if(this.file.exists() || this.file.createNewFile()) {
                  if(!this.file.canRead()) {
                     break label18;
                  }

                  input = new Configuration.UnicodeInputStreamReader(new FileInputStream(this.file), this.defaultEncoding);
                  this.defaultEncoding = input.getEncoding();
                  buffer = new BufferedReader(input);
                  ConfigCategory currentCat = null;
                  Property.Type type = null;
                  ArrayList<String> tmpList = null;
                  int lineNum = 0;
                  String name = null;
                  this.loadedConfigVersion = null;

                  while(true) {
                     ++lineNum;
                     String line = buffer.readLine();
                     if(line == null) {
                        if(lineNum == 1) {
                           this.loadedConfigVersion = this.definedConfigVersion;
                        }
                        break label18;
                     }

                     Matcher start = CONFIG_START.matcher(line);
                     Matcher end = CONFIG_END.matcher(line);
                     if(!start.matches()) {
                        if(!end.matches()) {
                           int nameStart = -1;
                           int nameEnd = -1;
                           boolean skip = false;
                           boolean quoted = false;
                           boolean isFirstNonWhitespaceCharOnLine = true;

                           for(int i = 0; i < line.length() && !skip; ++i) {
                              if(!Character.isLetterOrDigit(line.charAt(i)) && "._-".indexOf(line.charAt(i)) == -1 && (!quoted || line.charAt(i) == 34)) {
                                 if(!Character.isWhitespace(line.charAt(i))) {
                                    switch(line.charAt(i)) {
                                    case '\"':
                                       if(tmpList == null) {
                                          if(quoted) {
                                             quoted = false;
                                          }

                                          if(!quoted && nameStart == -1) {
                                             quoted = true;
                                          }
                                       }
                                       break;
                                    case '#':
                                       if(tmpList == null) {
                                          skip = true;
                                          continue;
                                       }
                                       break;
                                    case ':':
                                       if(tmpList == null) {
                                          type = Property.Type.tryParse(line.substring(nameStart, nameEnd + 1).charAt(0));
                                          nameEnd = -1;
                                          nameStart = -1;
                                       }
                                       break;
                                    case '<':
                                       if(tmpList != null && i + 1 == line.length() || tmpList == null && i + 1 != line.length()) {
                                          throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", new Object[]{this.fileName, Integer.valueOf(lineNum)}));
                                       }

                                       if(i + 1 == line.length()) {
                                          name = line.substring(nameStart, nameEnd + 1);
                                          if(currentCat == null) {
                                             throw new RuntimeException(String.format("\'%s\' has no scope in \'%s:%d\'", new Object[]{name, this.fileName, Integer.valueOf(lineNum)}));
                                          }

                                          tmpList = new ArrayList();
                                          skip = true;
                                       }
                                       break;
                                    case '=':
                                       if(tmpList == null) {
                                          name = line.substring(nameStart, nameEnd + 1);
                                          if(currentCat == null) {
                                             throw new RuntimeException(String.format("\'%s\' has no scope in \'%s:%d\'", new Object[]{name, this.fileName, Integer.valueOf(lineNum)}));
                                          }

                                          Property prop = new Property(name, line.substring(i + 1), type, true);
                                          i = line.length();
                                          currentCat.put(name, prop);
                                       }
                                       break;
                                    case '>':
                                       if(tmpList == null) {
                                          throw new RuntimeException(String.format("Malformed list property \"%s:%d\"", new Object[]{this.fileName, Integer.valueOf(lineNum)}));
                                       }

                                       if(isFirstNonWhitespaceCharOnLine) {
                                          currentCat.put(name, new Property(name, (String[])tmpList.toArray(new String[tmpList.size()]), type));
                                          name = null;
                                          tmpList = null;
                                          type = null;
                                       }
                                       break;
                                    case '{':
                                       if(tmpList == null) {
                                          name = line.substring(nameStart, nameEnd + 1);
                                          String qualifiedName = ConfigCategory.getQualifiedName(name, currentCat);
                                          ConfigCategory cat = (ConfigCategory)this.categories.get(qualifiedName);
                                          if(cat == null) {
                                             currentCat = new ConfigCategory(name, currentCat);
                                             this.categories.put(qualifiedName, currentCat);
                                          } else {
                                             currentCat = cat;
                                          }

                                          name = null;
                                       }
                                       break;
                                    case '}':
                                       if(tmpList == null) {
                                          if(currentCat == null) {
                                             throw new RuntimeException(String.format("Config file corrupt, attepted to close to many categories \'%s:%d\'", new Object[]{this.fileName, Integer.valueOf(lineNum)}));
                                          }

                                          currentCat = currentCat.parent;
                                       }
                                       break;
                                    case '~':
                                       if(tmpList == null && line.startsWith("~CONFIG_VERSION")) {
                                          int colon = line.indexOf(58);
                                          if(colon != -1) {
                                             this.loadedConfigVersion = line.substring(colon + 1).trim();
                                          }

                                          skip = true;
                                       }
                                       break;
                                    default:
                                       if(tmpList == null) {
                                          throw new RuntimeException(String.format("Unknown character \'%s\' in \'%s:%d\'", new Object[]{Character.valueOf(line.charAt(i)), this.fileName, Integer.valueOf(lineNum)}));
                                       }
                                    }

                                    isFirstNonWhitespaceCharOnLine = false;
                                 }
                              } else {
                                 if(nameStart == -1) {
                                    nameStart = i;
                                 }

                                 nameEnd = i;
                                 isFirstNonWhitespaceCharOnLine = false;
                              }
                           }

                           if(quoted) {
                              throw new RuntimeException(String.format("Unmatched quote in \'%s:%d\'", new Object[]{this.fileName, Integer.valueOf(lineNum)}));
                           }

                           if(tmpList != null && !skip) {
                              tmpList.add(line.trim());
                           }
                        } else {
                           this.fileName = end.group(1);
                           Configuration child = new Configuration();
                           child.categories = this.categories;
                           this.children.put(this.fileName, child);
                        }
                     } else {
                        this.fileName = start.group(1);
                        this.categories = new TreeMap();
                     }
                  }
               }
            } catch (IOException var35) {
               var35.printStackTrace();
               break label18;
            } finally {
               if(buffer != null) {
                  try {
                     buffer.close();
                  } catch (IOException var34) {
                     ;
                  }
               }

               if(input != null) {
                  try {
                     input.close();
                  } catch (IOException var33) {
                     ;
                  }
               }

            }

            return;
         }

         this.resetChangedState();
      }
   }

   public void save() {
      if(PARENT != null && PARENT != this) {
         PARENT.save();
      } else {
         try {
            if(this.file.getParentFile() != null) {
               this.file.getParentFile().mkdirs();
            }

            if(!this.file.exists() && !this.file.createNewFile()) {
               return;
            }

            if(this.file.canWrite()) {
               FileOutputStream fos = new FileOutputStream(this.file);
               BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, this.defaultEncoding));
               buffer.write("# Configuration file" + NEW_LINE + NEW_LINE);
               if(this.definedConfigVersion != null) {
                  buffer.write("~CONFIG_VERSION: " + this.definedConfigVersion + NEW_LINE + NEW_LINE);
               }

               if(this.children.isEmpty()) {
                  this.save(buffer);
               } else {
                  for(Entry<String, Configuration> entry : this.children.entrySet()) {
                     buffer.write("START: \"" + (String)entry.getKey() + "\"" + NEW_LINE);
                     ((Configuration)entry.getValue()).save(buffer);
                     buffer.write("END: \"" + (String)entry.getKey() + "\"" + NEW_LINE + NEW_LINE);
                  }
               }

               buffer.close();
               fos.close();
            }
         } catch (IOException var5) {
            var5.printStackTrace();
         }

      }
   }

   private void save(BufferedWriter out) throws IOException {
      for(ConfigCategory cat : this.categories.values()) {
         if(!cat.isChild()) {
            cat.write(out, 0);
            out.newLine();
         }
      }

   }

   public ConfigCategory getCategory(String category) {
      ConfigCategory ret = (ConfigCategory)this.categories.get(category);
      if(ret == null) {
         if(category.contains(".")) {
            String[] hierarchy = category.split("\\.");
            ConfigCategory parent = (ConfigCategory)this.categories.get(hierarchy[0]);
            if(parent == null) {
               parent = new ConfigCategory(hierarchy[0]);
               this.categories.put(parent.getQualifiedName(), parent);
               this.changed = true;
            }

            for(int i = 1; i < hierarchy.length; ++i) {
               String name = ConfigCategory.getQualifiedName(hierarchy[i], parent);
               ConfigCategory child = (ConfigCategory)this.categories.get(name);
               if(child == null) {
                  child = new ConfigCategory(hierarchy[i], parent);
                  this.categories.put(name, child);
                  this.changed = true;
               }

               ret = child;
               parent = child;
            }
         } else {
            ret = new ConfigCategory(category);
            this.categories.put(category, ret);
            this.changed = true;
         }
      }

      return ret;
   }

   public void removeCategory(ConfigCategory category) {
      for(ConfigCategory child : category.getChildren()) {
         this.removeCategory(child);
      }

      if(this.categories.containsKey(category.getQualifiedName())) {
         this.categories.remove(category.getQualifiedName());
         if(category.parent != null) {
            category.parent.removeChild(category);
         }

         this.changed = true;
      }

   }

   public Configuration setCategoryComment(String category, String comment) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setComment(comment);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public void addCustomCategoryComment(String category, String comment) {
      this.setCategoryComment(category, comment);
   }

   public Configuration setCategoryLanguageKey(String category, String langKey) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setLanguageKey(langKey);
      return this;
   }

   /** @deprecated */
   @Deprecated
   public void addCustomCategoryLanguageKey(String category, String langKey) {
      this.setCategoryLanguageKey(category, langKey);
   }

   public Configuration setCategoryCustomIGuiConfigListEntryClass(String category, Class clazz) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setCustomIGuiConfigListEntryClass(clazz);
      return this;
   }

   public Configuration setCategoryIsHotLoadable(String category, boolean isHotLoadable) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setIsHotLoadable(isHotLoadable);
      return this;
   }

   public Configuration setCategoryPropertyOrder(String category, List propOrder) {
      if(!this.caseSensitiveCustomCategories) {
         category = category.toLowerCase(Locale.ENGLISH);
      }

      this.getCategory(category).setPropertyOrder(propOrder);
      return this;
   }

   private void setChild(String name, Configuration child) {
      if(!this.children.containsKey(name)) {
         this.children.put(name, child);
         this.changed = true;
      } else {
         Configuration old = (Configuration)this.children.get(name);
         child.categories = old.categories;
         child.fileName = old.fileName;
         old.changed = true;
      }

   }

   public static void enableGlobalConfig() {
      PARENT = new Configuration(new File("global.cfg"));
      PARENT.load();
   }

   public boolean hasChanged() {
      if(this.changed) {
         return true;
      } else {
         for(ConfigCategory cat : this.categories.values()) {
            if(cat.hasChanged()) {
               return true;
            }
         }

         for(Configuration child : this.children.values()) {
            if(child.hasChanged()) {
               return true;
            }
         }

         return false;
      }
   }

   private void resetChangedState() {
      this.changed = false;

      for(ConfigCategory cat : this.categories.values()) {
         cat.resetChangedState();
      }

      for(Configuration child : this.children.values()) {
         child.resetChangedState();
      }

   }

   public Set getCategoryNames() {
      return ImmutableSet.copyOf(this.categories.keySet());
   }

   public boolean renameProperty(String category, String oldPropName, String newPropName) {
      if(this.hasCategory(category) && this.getCategory(category).containsKey(oldPropName) && !oldPropName.equalsIgnoreCase(newPropName)) {
         this.get(category, newPropName, this.getCategory(category).get(oldPropName).getString(), "");
         this.getCategory(category).remove(oldPropName);
         return true;
      } else {
         return false;
      }
   }

   public boolean moveProperty(String oldCategory, String propName, String newCategory) {
      if(!oldCategory.equals(newCategory) && this.hasCategory(oldCategory) && this.getCategory(oldCategory).containsKey(propName)) {
         this.getCategory(newCategory).put(propName, this.getCategory(oldCategory).remove(propName));
         return true;
      } else {
         return false;
      }
   }

   public void copyCategoryProps(Configuration fromConfig, String[] ctgys) {
      if(ctgys == null) {
         ctgys = (String[])this.getCategoryNames().toArray(new String[this.getCategoryNames().size()]);
      }

      for(String ctgy : ctgys) {
         if(fromConfig.hasCategory(ctgy) && this.hasCategory(ctgy)) {
            ConfigCategory thiscc = this.getCategory(ctgy);
            ConfigCategory fromcc = fromConfig.getCategory(ctgy);

            for(Entry<String, Property> entry : thiscc.getValues().entrySet()) {
               if(fromcc.containsKey(entry.getKey())) {
                  thiscc.put(entry.getKey(), fromcc.get(entry.getKey()));
               }
            }
         }
      }

   }

   public String getString(String name, String category, String defaultValue, String comment) {
      return this.getString(name, category, defaultValue, comment, (String)name, (Pattern)null);
   }

   public String getString(String name, String category, String defaultValue, String comment, String langKey) {
      return this.getString(name, category, defaultValue, comment, (String)langKey, (Pattern)null);
   }

   public String getString(String name, String category, String defaultValue, String comment, Pattern pattern) {
      return this.getString(name, category, defaultValue, comment, name, pattern);
   }

   public String getString(String name, String category, String defaultValue, String comment, String langKey, Pattern pattern) {
      Property prop = this.get(category, name, defaultValue);
      prop.setPropLanguageKey(langKey);
      prop.setValidStringPattern(pattern);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getString();
   }

   public String getString(String name, String category, String defaultValue, String comment, String[] validValues) {
      return this.getString(name, category, defaultValue, comment, validValues, name);
   }

   public String getString(String name, String category, String defaultValue, String comment, String[] validValues, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setValidValues(validValues);
      prop.setPropLanguageKey(langKey);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getString();
   }

   public String[] getStringList(String name, String category, String[] defaultValues, String comment) {
      return this.getStringList(name, category, defaultValues, comment, (String[])null, name);
   }

   public String[] getStringList(String name, String category, String[] defaultValue, String comment, String[] validValues) {
      return this.getStringList(name, category, defaultValue, comment, validValues, name);
   }

   public String[] getStringList(String name, String category, String[] defaultValue, String comment, String[] validValues, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setPropLanguageKey(langKey);
      prop.setValidValues(validValues);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getStringList();
   }

   public boolean getBoolean(String name, String category, boolean defaultValue, String comment) {
      return this.getBoolean(name, category, defaultValue, comment, name);
   }

   public boolean getBoolean(String name, String category, boolean defaultValue, String comment, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setPropLanguageKey(langKey);
      prop.comment = comment + " [default: " + defaultValue + "]";
      return prop.getBoolean(defaultValue);
   }

   public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
      return this.getInt(name, category, defaultValue, minValue, maxValue, comment, name);
   }

   public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment, String langKey) {
      Property prop = this.get(category, name, defaultValue);
      prop.setPropLanguageKey(langKey);
      prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]";
      prop.setMinValue(minValue);
      prop.setMaxValue(maxValue);
      return prop.getInt(defaultValue) < minValue?minValue:(prop.getInt(defaultValue) > maxValue?maxValue:prop.getInt(defaultValue));
   }

   public float getFloat(String name, String category, float defaultValue, float minValue, float maxValue, String comment) {
      return this.getFloat(name, category, defaultValue, minValue, maxValue, comment, name);
   }

   public float getFloat(String name, String category, float defaultValue, float minValue, float maxValue, String comment, String langKey) {
      Property prop = this.get(category, name, Float.toString(defaultValue), name);
      prop.setPropLanguageKey(langKey);
      prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]";
      prop.setMinValue((double)minValue);
      prop.setMaxValue((double)maxValue);

      try {
         return Float.parseFloat(prop.getString()) < minValue?minValue:(Float.parseFloat(prop.getString()) > maxValue?maxValue:Float.parseFloat(prop.getString()));
      } catch (Exception var10) {
         var10.printStackTrace();
         return defaultValue;
      }
   }

   public File getConfigFile() {
      return this.file;
   }

   public static class UnicodeInputStreamReader extends Reader {
      private final InputStreamReader input;

      public UnicodeInputStreamReader(InputStream source, String encoding) throws IOException {
         String enc = encoding;
         byte[] data = new byte[4];
         PushbackInputStream pbStream = new PushbackInputStream(source, data.length);
         int read = pbStream.read(data, 0, data.length);
         int size = 0;
         int bom16 = (data[0] & 255) << 8 | data[1] & 255;
         int bom24 = bom16 << 8 | data[2] & 255;
         int bom32 = bom24 << 8 | data[3] & 255;
         if(bom24 == 15711167) {
            enc = "UTF-8";
            size = 3;
         } else if(bom16 == '\ufeff') {
            enc = "UTF-16BE";
            size = 2;
         } else if(bom16 == '\ufffe') {
            enc = "UTF-16LE";
            size = 2;
         } else if(bom32 == '\ufeff') {
            enc = "UTF-32BE";
            size = 4;
         } else if(bom32 == -131072) {
            enc = "UTF-32LE";
            size = 4;
         }

         if(size < read) {
            pbStream.unread(data, size, read - size);
         }

         this.input = new InputStreamReader(pbStream, enc);
      }

      public String getEncoding() {
         return this.input.getEncoding();
      }

      public int read(char[] cbuf, int off, int len) throws IOException {
         return this.input.read(cbuf, off, len);
      }

      public void close() throws IOException {
         this.input.close();
      }
   }
}
