package bspkrs.statuseffecthud.fml;

public class CommonProxy {
   public void preInit() {
   }

   public void init() {
      System.err.println("************************************************************************************");
      System.err.println("* StatusEffectHUD is a CLIENT-ONLY mod. Installing it on your server is pointless. *");
      System.err.println("************************************************************************************");
   }
}
