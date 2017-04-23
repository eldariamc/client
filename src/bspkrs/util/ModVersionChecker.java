package bspkrs.util;

import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import com.google.common.collect.Ordering;
import net.minecraft.util.StatCollector;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.prefs.Preferences;

public class ModVersionChecker {
   private static final Map versionCheckerMap = new HashMap();
   private URL versionURL;
   private final String modID;
   private String newVersion;
   private final String currentVersion;
   private String updateURL;
   private String[] loadMsg;
   private String[] inGameMsg;
   private File trackerFile;
   private File trackerDir;
   private static Preferences versionCheckTracker;
   private String lastNewVersionFound;
   private final String CHECK_ERROR;
   private final boolean errorDetected;
   private int runsSinceLastMessage;

   public ModVersionChecker(String modID, String curVer, String versionURL, String updateURL, String[] loadMsg, String[] inGameMsg) {
      this(modID, curVer, versionURL, updateURL, loadMsg, inGameMsg, bspkrsCoreMod.instance.updateTimeoutMilliseconds);
   }

   public ModVersionChecker(String modID, String curVer, String versionURL, String updateURL, String[] loadMsg, String[] inGameMsg, int timeoutMS) {
      this.CHECK_ERROR = "check_error";
      this.modID = modID;
      this.currentVersion = curVer;
      this.updateURL = updateURL;
      this.loadMsg = loadMsg;
      this.inGameMsg = inGameMsg;

      try {
         if(versionURL.startsWith("http://dl.dropboxusercontent.com")) {
            versionURL = versionURL.replaceFirst("http", "https");
         }

         this.versionURL = new URL(versionURL);
         BSLog.info("Initializing ModVersionChecker for mod %s", new Object[]{modID});
      } catch (Throwable var9) {
         BSLog.warning("Error initializing ModVersionChecker for mod %s: %s", new Object[]{modID, var9.getMessage()});
      }

      String[] versionLines = CommonUtils.loadTextFromURL(this.versionURL, BSLog.INSTANCE.getLogger(), new String[]{"check_error"}, timeoutMS);
      if(versionLines.length != 0 && !versionLines[0].trim().equals("<html>")) {
         this.newVersion = versionLines[0].trim();
      } else {
         this.newVersion = "check_error";
      }

      this.errorDetected = this.newVersion.equals("check_error");
      if(this.trackerDir == null) {
         this.trackerDir = new File(CommonUtils.getConfigDir() + "/bspkrsCore/");
         if(this.trackerDir.exists()) {
            this.trackerFile = new File(this.trackerDir, "ModVersionCheckerTracking.txt");
            this.trackerFile.delete();
            this.trackerDir.delete();
         }

         this.trackerDir = new File(CommonUtils.getConfigDir());
         this.trackerFile = new File(this.trackerDir, "bspkrs_ModVersionCheckerTracking.txt");
         if(this.trackerFile.exists()) {
            this.trackerFile.delete();
         }
      }

      if(versionCheckTracker == null) {
         versionCheckTracker = Preferences.userNodeForPackage(this.getClass()).node("modversiontracker" + Const.MCVERSION);
      }

      if(!("@" + "MOD_VERSION@").equals(this.currentVersion) && !"${mod_version}".equals(this.currentVersion)) {
         this.lastNewVersionFound = versionCheckTracker.get(modID, this.currentVersion);
         if(this.lastNewVersionFound.equals("<html>")) {
            this.lastNewVersionFound = this.currentVersion;
         }

         this.runsSinceLastMessage = versionCheckTracker.node("runs_since_last_message").getInt(modID, 0);
         if(this.errorDetected) {
            this.newVersion = this.lastNewVersionFound;
         }

         if(!this.errorDetected && !isCurrentVersion(this.lastNewVersionFound, this.newVersion)) {
            this.runsSinceLastMessage = 0;
            this.lastNewVersionFound = this.newVersion;
         } else {
            this.runsSinceLastMessage %= 10;
         }

         versionCheckTracker.node("runs_since_last_message").putInt(modID, this.runsSinceLastMessage + 1);
         versionCheckTracker.put(modID, this.lastNewVersionFound);
      }

      if(versionLines.length > 1 && versionLines[1].trim().length() != 0) {
         this.updateURL = versionLines[1];
      }

      this.setLoadMessage(loadMsg);
      this.setInGameMessage(inGameMsg);
      versionCheckerMap.put(modID.toLowerCase(Locale.US), this);
   }

   public ModVersionChecker(String modName, String oldVer, String versionURL, String updateURL) {
      this(modName, oldVer, versionURL, updateURL, new String[]{"{modID} {oldVer} is out of date! Visit {updateURL} to download the latest release ({newVer})."}, new String[]{"§c{modID} {newVer} §ris out! Download the latest from §a{updateURL}§r"});
   }

   public void checkVersionWithLogging() {
      if(!isCurrentVersion(this.currentVersion, this.newVersion)) {
         for(String msg : this.loadMsg) {
            BSLog.info(msg, new Object[0]);
         }
      }

   }

   public void setLoadMessage(String[] loadMsg) {
      this.loadMsg = loadMsg;

      for(int i = 0; i < this.loadMsg.length; ++i) {
         this.loadMsg[i] = this.replaceAllTags(this.loadMsg[i]);
      }

   }

   public void setInGameMessage(String[] inGameMsg) {
      this.inGameMsg = inGameMsg;

      for(int i = 0; i < this.inGameMsg.length; ++i) {
         this.inGameMsg[i] = this.replaceAllTags(this.inGameMsg[i]);
      }

   }

   public String[] getLoadMessage() {
      return this.loadMsg;
   }

   public String[] getInGameMessage() {
      return this.inGameMsg;
   }

   public URL getVersionURL() {
      return this.versionURL;
   }

   public String getNewVersion() {
      return this.newVersion;
   }

   public String getCurrentVersion() {
      return this.currentVersion;
   }

   public String getUpdateURL() {
      return this.updateURL;
   }

   public static Map getVersionCheckerMap() {
      return versionCheckerMap;
   }

   public boolean isCurrentVersion() {
      return this.errorDetected || isCurrentVersion(this.runsSinceLastMessage == 0?this.currentVersion:this.lastNewVersionFound, this.newVersion);
   }

   public static boolean isCurrentVersion(String oldVer, String newVer) {
      return Ordering.natural().compare(oldVer, newVer) >= 0;
   }

   private String replaceAllTags(String s) {
      return s.replace("{oldVer}", this.currentVersion).replace("{newVer}", this.newVersion).replace("{modID}", this.modID).replace("{updateURL}", this.updateURL);
   }

   public static String[] checkVersionForMod(String modID) {
      String[] var10000 = new String[]{""};
      String[] r;
      if(versionCheckerMap.containsKey(modID.toLowerCase(Locale.US))) {
         ModVersionChecker versionChecker = (ModVersionChecker)versionCheckerMap.get(modID.toLowerCase(Locale.US));
         if(!versionChecker.errorDetected) {
            if(!isCurrentVersion(versionChecker.currentVersion, versionChecker.newVersion)) {
               r = versionChecker.getInGameMessage();
            } else {
               r = new String[]{StatCollector.translateToLocalFormatted("bspkrs.modversionchecker.uptodate", new Object[]{versionChecker.modID})};
            }
         } else {
            r = new String[]{StatCollector.translateToLocalFormatted("bspkrs.modversionchecker.error", new Object[]{versionChecker.modID})};
         }
      } else {
         r = new String[]{StatCollector.translateToLocal("bspkrs.modversionchecker.invalidmodid")};
      }

      return r;
   }
}
