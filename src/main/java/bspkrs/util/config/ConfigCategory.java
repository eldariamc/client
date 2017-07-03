package bspkrs.util.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/** @deprecated */
@Deprecated
public class ConfigCategory implements Map<String, Property> {
   private String name;
   private String comment;
   private String languagekey;
   private final ArrayList<ConfigCategory> children;
   private final Map<String, Property> properties;
   public final ConfigCategory parent;
   private boolean changed;
   private boolean isHotLoadable;
   private Class customEntryClass;
   private List<String> propertyOrder;

   public ConfigCategory(String name) {
      this(name, (ConfigCategory)null);
   }

   public ConfigCategory(String name, ConfigCategory parent) {
      this.comment = "";
      this.children = new ArrayList();
      this.properties = new TreeMap<>();
      this.changed = false;
      this.isHotLoadable = false;
      this.customEntryClass = null;
      this.propertyOrder = null;
      this.name = name;
      this.parent = parent;
      if(parent != null) {
         parent.children.add(this);
      }

   }

   public boolean equals(Object obj) {
      if(!(obj instanceof ConfigCategory)) {
         return false;
      } else {
         ConfigCategory cat = (ConfigCategory)obj;
         return this.name.equals(cat.name) && this.children.equals(cat.children);
      }
   }

   public String getName() {
      return this.name;
   }

   public String getQualifiedName() {
      return getQualifiedName(this.name, this.parent);
   }

   public static String getQualifiedName(String name, ConfigCategory parent) {
      return parent == null?name:parent.getQualifiedName() + "." + name;
   }

   public ConfigCategory getFirstParent() {
      return this.parent == null?this:this.parent.getFirstParent();
   }

   public boolean isChild() {
      return this.parent != null;
   }

   public Map<String, Property> getValues() {
      return ImmutableMap.copyOf(this.properties);
   }

   public Property[] getOrderedValuesArray() {
      if(this.propertyOrder != null) {
         List<Property> list = new ArrayList();

         for(String s : this.propertyOrder) {
            if(this.properties.containsKey(s)) {
               list.add(this.properties.get(s));
            }
         }

         return (Property[])list.toArray(new Property[list.size()]);
      } else {
         return (Property[])this.properties.values().toArray(new Property[this.properties.size()]);
      }
   }

   public Set getOrderedValuesSet() {
      if(this.propertyOrder != null) {
         LinkedHashSet<Property> set = new LinkedHashSet();

         for(String key : this.propertyOrder) {
            if(this.properties.containsKey(key)) {
               set.add(this.properties.get(key));
            }
         }

         return ImmutableSet.copyOf(set);
      } else {
         return ImmutableSet.copyOf(this.properties.values());
      }
   }

   public ConfigCategory setCustomIGuiConfigListEntryClass(Class clazz) {
      this.customEntryClass = clazz;
      return this;
   }

   public Class getCustomIGuiConfigListEntryClass() {
      return this.customEntryClass;
   }

   public ConfigCategory setLanguageKey(String languagekey) {
      this.languagekey = languagekey;
      return this;
   }

   public String getLanguagekey() {
      return this.languagekey != null?this.languagekey:this.getQualifiedName();
   }

   public ConfigCategory setComment(String comment) {
      this.comment = comment;
      return this;
   }

   public String getComment() {
      return this.comment;
   }

   public ConfigCategory setIsHotLoadable(boolean isHotLoadable) {
      this.isHotLoadable = isHotLoadable;
      return this;
   }

   public boolean isHotLoadable() {
      return this.isHotLoadable;
   }

   public ConfigCategory setPropertyOrder(List propertyOrder) {
      this.propertyOrder = propertyOrder;

      for(String s : this.properties.keySet()) {
         if(!propertyOrder.contains(s)) {
            propertyOrder.add(s);
         }
      }

      return this;
   }

   public List<ConfigProperty> getPropertyOrder() {
      ArrayList<ConfigProperty> list = new ArrayList<>();
      Iterable<String> keys = this.propertyOrder != null?ImmutableList.copyOf(this.propertyOrder):ImmutableList.copyOf(this.properties.keySet());
      for (String key : keys)
         list.add(new ConfigProperty(properties.get(key)));
      return list;
   }

   public boolean containsKey(String key) {
      return this.properties.containsKey(key);
   }

   public Property get(String key) {
      return (Property)this.properties.get(key);
   }

   private void write(BufferedWriter out, String... data) throws IOException {
      this.write(out, true, data);
   }

   private void write(BufferedWriter out, boolean new_line, String... data) throws IOException {
      for(int x = 0; x < data.length; ++x) {
         out.write(data[x]);
      }

      if(new_line) {
         out.write(Configuration.NEW_LINE);
      }

   }

   public void write(BufferedWriter out, int indent) throws IOException {
      String pad0 = this.getIndent(indent);
      String pad1 = this.getIndent(indent + 1);
      String pad2 = this.getIndent(indent + 2);
      if(this.comment != null && !this.comment.isEmpty()) {
         this.write(out, new String[]{pad0, "##########################################################################################################"});
         this.write(out, new String[]{pad0, "# ", this.name});
         this.write(out, new String[]{pad0, "#--------------------------------------------------------------------------------------------------------#"});
         Splitter splitter = Splitter.onPattern("\r?\n");

         for(String line : splitter.split(this.comment)) {
            this.write(out, new String[]{pad0, "# ", line});
         }

         this.write(out, new String[]{pad0, "##########################################################################################################", Configuration.NEW_LINE});
      }

      if(!Configuration.allowedProperties.matchesAllOf(this.name)) {
         this.name = '\"' + this.name + '\"';
      }

      this.write(out, new String[]{pad0, this.name, " {"});
      Property[] props = this.getOrderedValuesArray();

      for(int x = 0; x < props.length; ++x) {
         Property prop = props[x];
         if(prop.comment != null && !prop.comment.isEmpty()) {
            if(x != 0) {
               out.newLine();
            }

            Splitter splitter = Splitter.onPattern("\r?\n");

            for(String commentLine : splitter.split(prop.comment)) {
               this.write(out, new String[]{pad1, "# ", commentLine});
            }
         }

         String propName = prop.getName();
         if(!Configuration.allowedProperties.matchesAllOf(propName)) {
            propName = '\"' + propName + '\"';
         }

         if(!prop.isList()) {
            if(prop.getType() == null) {
               this.write(out, new String[]{pad1, propName, "=", prop.getString()});
            } else {
               char type = prop.getType().getID();
               this.write(out, new String[]{pad1, String.valueOf(type), ":", propName, "=", prop.getString()});
            }
         } else {
            char type = prop.getType().getID();
            this.write(out, new String[]{pad1, String.valueOf(type), ":", propName, " <"});

            for(String line : prop.getStringList()) {
               this.write(out, new String[]{pad2, line});
            }

            this.write(out, new String[]{pad1, " >"});
         }
      }

      if(this.children.size() > 0) {
         out.newLine();
      }

      for(ConfigCategory child : this.children) {
         child.write(out, indent + 1);
      }

      this.write(out, new String[]{pad0, "}", Configuration.NEW_LINE});
   }

   private String getIndent(int indent) {
      StringBuilder buf = new StringBuilder("");

      for(int x = 0; x < indent; ++x) {
         buf.append("    ");
      }

      return buf.toString();
   }

   public boolean hasChanged() {
      if(this.changed) {
         return true;
      } else {
         for(Property prop : this.properties.values()) {
            if(prop.hasChanged()) {
               return true;
            }
         }

         return false;
      }
   }

   void resetChangedState() {
      this.changed = false;

      for(Property prop : this.properties.values()) {
         prop.resetChangedState();
      }

   }

   public int size() {
      return this.properties.size();
   }

   public boolean isEmpty() {
      return this.properties.isEmpty();
   }

   public boolean containsKey(Object key) {
      return this.properties.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.properties.containsValue(value);
   }

   public Property get(Object key) {
      return (Property)this.properties.get(key);
   }

   public Property put(String key, Property value) {
      this.changed = true;
      if(this.propertyOrder != null && !this.propertyOrder.contains(key)) {
         this.propertyOrder.add(key);
      }

      return (Property)this.properties.put(key, value);
   }

   public Property remove(Object key) {
      this.changed = true;
      return (Property)this.properties.remove(key);
   }

   public void putAll(Map<? extends String, ? extends Property> m) {
      this.changed = true;
      if(this.propertyOrder != null) {
         for(String key : m.keySet()) {
            if(!this.propertyOrder.contains(key)) {
               this.propertyOrder.add(key);
            }
         }
      }

      this.properties.putAll(m);
   }

   public void clear() {
      this.changed = true;
      this.properties.clear();
   }

   public Set keySet() {
      return this.properties.keySet();
   }

   public Collection values() {
      return this.properties.values();
   }

   public Set entrySet() {
      return ImmutableSet.copyOf(this.properties.entrySet());
   }

   public Set<ConfigCategory> getChildren() {
      return ImmutableSet.copyOf(this.children);
   }

   public ConfigCategory removeChild(ConfigCategory child) {
      if(this.children.contains(child)) {
         this.children.remove(child);
         this.changed = true;
      }

      return this;
   }
}
