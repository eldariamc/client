package bspkrs.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum BSLog {
   INSTANCE;

   private Logger logger;

   public Logger getLogger() {
      if(this.logger == null) {
         this.init();
      }

      return this.logger;
   }

   private void init() {
      if(this.logger == null) {
         this.logger = LogManager.getLogger("bspkrsCore");
      }
   }

   public static void info(String format, Object... args) {
      INSTANCE.log(Level.INFO, format, args);
   }

   public static void log(Level level, Throwable exception, String format, Object... args) {
      if(args != null && args.length > 0) {
         INSTANCE.getLogger().log(level, String.format(format, args), exception);
      } else {
         INSTANCE.getLogger().log(level, format, exception);
      }

   }

   public static void severe(String format, Object... args) {
      INSTANCE.log(Level.ERROR, format, args);
   }

   public static void warning(String format, Object... args) {
      INSTANCE.log(Level.WARN, format, args);
   }

   private void log(Level level, String format, Object... data) {
      if(data != null && data.length > 0) {
         this.getLogger().log(level, String.format(format, data));
      } else {
         this.getLogger().log(level, format);
      }

   }
}
