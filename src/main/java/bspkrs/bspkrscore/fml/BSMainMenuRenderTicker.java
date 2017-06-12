package bspkrs.bspkrscore.fml;

import bspkrs.client.util.EntityUtils;
import bspkrs.util.BSLog;
import bspkrs.util.FakeWorld;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import fr.dabsunter.mcp.McpHandler;
import fr.dabsunter.mcp.Tickable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class BSMainMenuRenderTicker implements Tickable {
   private static Minecraft mcClient;
   private static boolean isRegistered = false;
   private World world;
   private EntityLivingBase randMob;
   private GuiScreen savedScreen;
   private static List entityBlacklist = new ArrayList();
   private static List fallbackPlayerNames = new ArrayList();
   private static ItemStack[] playerItems = new ItemStack[]{new ItemStack(Items.iron_sword), new ItemStack(Items.diamond_sword), new ItemStack(Items.golden_sword), new ItemStack(Items.diamond_pickaxe), new ItemStack(Items.iron_pickaxe), new ItemStack(Items.iron_axe)};
   private static ItemStack[] zombieItems = new ItemStack[]{new ItemStack(Items.iron_sword), new ItemStack(Items.diamond_sword), new ItemStack(Items.golden_sword), new ItemStack(Items.iron_axe)};
   private static ItemStack[] skelItems = new ItemStack[]{new ItemStack(Items.bow), new ItemStack(Items.golden_sword), new ItemStack(Items.bow), new ItemStack(Items.bow), new ItemStack(Items.bow), new ItemStack(Items.bow)};
   private static Random random = new Random();
   private static Set entities = new TreeSet(EntityList.stringToClassMapping.keySet());
   private static Object[] entStrings = entities.toArray(new Object[0]);
   private static int id = -1;
   private static boolean erroredOut = false;

   public BSMainMenuRenderTicker() {
      mcClient = Minecraft.getMinecraft();
   }

   public void onTick(Phase phase) {
      /* Keyrisium - osef ya pas d'autres mods
      if(Loader.isModLoaded("WorldStateCheckpoints")) {
         bspkrsCoreMod.instance.showMainMenuMobs = false;
         BSLog.severe("Main menu mob rendering is known to cause crashes with WorldStateCheckpoints has been disabled for the remainder of this session.", new Object[0]);
         this.unRegister();
      }*/

      if(bspkrsCoreMod.instance.showMainMenuMobs && !erroredOut && mcClient.currentScreen instanceof GuiMainMenu) {
         try {
            if(mcClient.thePlayer == null || mcClient.thePlayer.worldObj == null || this.randMob == null) {
               this.init();
            }

            if(this.world != null && mcClient.thePlayer != null && this.randMob != null) {
               ScaledResolution sr = new ScaledResolution(mcClient, mcClient.displayWidth, mcClient.displayHeight);
               int mouseX = Mouse.getX() * sr.getScaledWidth() / mcClient.displayWidth;
               int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / mcClient.displayHeight - 1;
               int distanceToSide = (mcClient.currentScreen.width / 2 - 98) / 2;
               float targetHeight = (float)(sr.getScaledHeight_double() / 5.0D) / 1.8F;
               float scale = EntityUtils.getEntityScale(this.randMob, targetHeight, 1.8F);
               EntityUtils.drawEntityOnScreen(sr.getScaledWidth() - 75, (int)((float)(sr.getScaledHeight() / 2) + this.randMob.height * scale), scale, (float)(distanceToSide - mouseX), (float)(sr.getScaledHeight() / 2) + this.randMob.height * scale - this.randMob.height * scale * (this.randMob.getEyeHeight() / this.randMob.height) - (float)mouseY, this.randMob);
               //EntityUtils.drawEntityOnScreen(sr.getScaledWidth() - distanceToSide, (int)((float)(sr.getScaledHeight() / 2) + mcClient.thePlayer.height * targetHeight), targetHeight, (float)(sr.getScaledWidth() - distanceToSide - mouseX), (float)(sr.getScaledHeight() / 2) + mcClient.thePlayer.height * targetHeight - mcClient.thePlayer.height * targetHeight * (mcClient.thePlayer.getEyeHeight() / mcClient.thePlayer.height) - (float)mouseY, mcClient.thePlayer);
            }
         } catch (Throwable var8) {
            BSLog.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.", new Object[0]);
            var8.printStackTrace();
            erroredOut = true;
            mcClient.thePlayer = null;
            this.randMob = null;
            this.world = null;
         }
      }

   }

   private void init() {
      try {
         boolean createNewWorld = this.world == null;
         if(createNewWorld) {
            this.world = new FakeWorld();
         }

         if(createNewWorld || mcClient.thePlayer == null) {
            mcClient.thePlayer = new EntityClientPlayerMP(mcClient, this.world, mcClient.getSession(), (NetHandlerPlayClient)null, (StatFileWriter)null);
            mcClient.thePlayer.dimension = 0;
            mcClient.thePlayer.movementInput = new MovementInputFromOptions(mcClient.gameSettings);
            mcClient.thePlayer.height = 1.82F;
            setRandomMobItem(mcClient.thePlayer);
         }

         if(createNewWorld || this.randMob == null) {
            /*if(bspkrsCoreMod.instance.allowDebugOutput) {
               this.randMob = getNextEntity(this.world);
            } else {
               this.randMob = EntityUtils.getRandomLivingEntity(this.world, entityBlacklist, 4, fallbackPlayerNames);
            }*/
            this.randMob = new EntityOtherPlayerMP(world, new GameProfile(mcClient.getSession().getUniqueID(), mcClient.getSession().getUsername()));

            setRandomMobItem(this.randMob);
         }

         RenderManager.instance.func_147938_a(this.world, mcClient.getTextureManager(), mcClient.fontRenderer, mcClient.thePlayer, mcClient.thePlayer, mcClient.gameSettings, 0.0F);
         this.savedScreen = mcClient.currentScreen;
      } catch (Throwable var2) {
         BSLog.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.", new Object[0]);
         var2.printStackTrace();
         erroredOut = true;
         mcClient.thePlayer = null;
         this.randMob = null;
         this.world = null;
      }

   }

   private static EntityLivingBase getNextEntity(World world) {
      int tries = 0;

      Class clazz;
      while(true) {
         if(++id >= entStrings.length) {
            id = 0;
         }

         clazz = (Class)EntityList.stringToClassMapping.get(entStrings[id]);
         if(EntityLivingBase.class.isAssignableFrom(clazz)) {
            break;
         }

         ++tries;
         if(tries > 5) {
            break;
         }
      }

      if(!EntityLivingBase.class.isAssignableFrom(clazz)) {
         SimpleEntry<UUID, String> entry = (SimpleEntry)fallbackPlayerNames.get(random.nextInt(fallbackPlayerNames.size()));
         return new EntityOtherPlayerMP(world, mcClient.func_152347_ac().fillProfileProperties(new GameProfile(entry.getKey(), entry.getValue()), false));
      } else {
         if(bspkrsCoreMod.instance.allowDebugOutput) {
            BSLog.info(entStrings[id].toString());
         }

         return (EntityLivingBase)EntityList.createEntityByName((String)entStrings[id], world);
      }
   }

   public static void setRandomMobItem(EntityLivingBase ent) {
      try {
         if(ent instanceof AbstractClientPlayer) {
            ent.setCurrentItemOrArmor(0, playerItems[random.nextInt(playerItems.length)]);
         } else if(ent instanceof EntityZombie) {
            ent.setCurrentItemOrArmor(0, zombieItems[random.nextInt(zombieItems.length)]);
         } else if(ent instanceof EntitySkeleton) {
            if(random.nextBoolean()) {
               ((EntitySkeleton)ent).setSkeletonType(1);
               ent.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            } else {
               ent.setCurrentItemOrArmor(0, skelItems[random.nextInt(skelItems.length)]);
            }
         } else if(ent instanceof EntityPigZombie) {
            ent.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
         } else if(ent instanceof EntityEnderman) {
            ((EntityEnderman)ent).func_146081_a(Blocks.grass);
         }
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }

   public void register() {
      if(!isRegistered) {
         BSLog.info("Enabling Main Menu Mob render ticker");
         //FMLCommonHandler.instance().bus().register(this);
         McpHandler.registerRenderTick(this);
         isRegistered = true;
      }

   }

   public void unRegister() {
      if(isRegistered) {
         BSLog.info("Disabling Main Menu Mob render ticker");
         //FMLCommonHandler.instance().bus().unregister(this);
         McpHandler.unregisterRenderTick(this);
         isRegistered = false;
         this.randMob = null;
         this.world = null;
         mcClient.thePlayer = null;
      }

   }

   public boolean isRegistered() {
      return isRegistered;
   }

   static {
      entityBlacklist.add("Mob");
      entityBlacklist.add("Monster");
      entityBlacklist.add("EnderDragon");
      entityBlacklist.add("Squid");
      entityBlacklist.add("Ghast");
      entityBlacklist.add("Bat");
      entityBlacklist.add("CaveSpider");
      entityBlacklist.add("Giant");
      entityBlacklist.add("MillBlaze");
      entityBlacklist.add("MillGhast");
      entityBlacklist.add("MillWitherSkeleton");
      entityBlacklist.add("ml_GenericAsimmFemale");
      entityBlacklist.add("ml_GenericSimmFemale");
      entityBlacklist.add("ml_GenericVillager");
      entityBlacklist.add("BiomesOPlenty.Phantom");
      entityBlacklist.add("Forestry.butterflyGE");
      entityBlacklist.add("TConstruct.Crystal");
      entityBlacklist.add("Thaumcraft.Firebat");
      entityBlacklist.add("Thaumcraft.TaintSpore");
      entityBlacklist.add("Thaumcraft.TaintSwarm");
      entityBlacklist.add("Thaumcraft.Taintacle");
      entityBlacklist.add("Thaumcraft.TaintacleTiny");
      entityBlacklist.add("Thaumcraft.Wisp");
      entityBlacklist.add("TwilightForest.Boggard");
      entityBlacklist.add("TwilightForest.Firefly");
      entityBlacklist.add("TwilightForest.Helmet Crab");
      entityBlacklist.add("TwilightForest.Hydra");
      entityBlacklist.add("TwilightForest.HydraHead");
      entityBlacklist.add("TwilightForest.Lower Goblin Knight");
      entityBlacklist.add("TwilightForest.Mist Wolf");
      entityBlacklist.add("TwilightForest.Mosquito Swarm");
      entityBlacklist.add("TwilightForest.Upper Goblin Knight");
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("92d459067a50474285b6b079db9dc189"), "bspkrs"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("2efa46fa29484d98b822fa182d254870"), "lorddusk"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("b9a89002b3924545ab4d5b1ff60c88a6"), "Arkember"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("48a16fc8bc1f4e7284e97ec73b7d8ea1"), "TTFTCUTS"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("95fe0728e1bd4989a9803d8976aedda9"), "WayofFlowingTime"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("e6b5c088068044df9e1b9bf11792291b"), "Grumm"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("af1d579e8787433099b2e7b3777c3e7a"), "Sacheverell"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("4c13e7d20e854bdebb121a43be506302"), "Quetzz"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("0192723fb3dc495a959f52c53fa63bff"), "Pahimar"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("f46302d20b7c4cc6aee9cd714ae6b9d1"), "ZeldoKavira"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("0eff7eb1d1b74612a9c9791b7ad6277a"), "sfPlayer1"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("c7d5d58a51a84d2698f55550a37bd8d1"), "jadedcat"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("72ddaa057bbe4ae298922c8d90ea0ad8"), "RWTema"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("754e416456cc4139bb7911cfaafdebcc"), "Scottwears"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("7ef08b4e5f3d40a793d426a19fe0efe2"), "neptunepink"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("90201c8957124f99a1c95d751565560a"), "Aureylian"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("bbb87dbe690f4205bdc572ffb8ebc29d"), "direwolf20"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("d0719201af1d4aab93d0f514ab59ed8e"), "Krystal_Raven"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("069a79f444e94726a5befca90e38aaf5"), "Notch"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("61699b2ed3274a019f1e0ea8c3f06bc6"), "Dinnerbone"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("e358f2774a4c42f69ad83addf4869ea6"), "Adubbz"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("892017ede9a04f259a37b8811614707d"), "AlgorithmX2"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("6d074736b1e94378a99bbd8777821c9c"), "Cloudhunter"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("3af4f9617eb64cb0b26443fd593cf42a"), "Lunatrius"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("75831c039a0a496ba7776a78ef8833a6"), "_Sunstrike"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("b72d87cefa984a5ab5a05db51a018d09"), "sdkillen"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("af1483804ba54a3da47d710f710f9265"), "Minalien"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("72ddaa057bbe4ae298922c8d90ea0ad8"), "RWTema"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("6bdd4acd5637448898583de07cc820d5"), "futureamnet"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("8e32d7a9c8124fa78daf465ff9ffa262"), "AbrarSyed"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("6ac7c57d8c154ffeae5d5e04bd606786"), "TDWP_FTW"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("d3cf097a438f4523b770ec11e13ecc32"), "LexManos"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("98beecaf555e40649401b531fb927641"), "Vaht"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("e3ade06278f344ca90fe4fc5781ffc80"), "EddieRuckus"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("83898b2861184900913741ffc46b6e10"), "progwml6"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("0b7509f0245841609ce12772b9a45ac2"), "ohaiiChun"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("aa29ede708174c65b19277a32e772b9c"), "fuj1n"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("d4d119aad410488a87340053577d4a1a"), "Mikeemoo"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("898ed21379b249f98602d1bb03d19ba2"), "boq42"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("9671e3e159184ad0bd473f7f27f57074"), "Toby"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("f3c8d69b077645128434d1b2165909eb"), "dan200"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("cf3e2c7ed70348e0808ef139bf26ff9d"), "ecutruin"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("b1890bac9f4044fa870ba71ce4df05c2"), "nikstick22"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("a9b6837ea916496790c191aef968c96b"), "Mr_okushama"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("0ea8eca3dbf647cc9d1ac64551ca975c"), "sk89q"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("957397819f754c6c9a93621c72f5bf9c"), "ShadwDrgn"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("c501d5507e3c463e8a95256f86d9a47d"), "chicken_bones"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("0f95811ab3b64dbaba034adfec7cf5ab"), "azanor"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("4f3a8d1e33c144e7bce8e683027c7dac"), "Soaryn"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("03020c4cf2f640b1a37c4e2b159b15b7"), "pillbox"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("b476c98e175048bfad5ea21becd0aaeb"), "mDiyo"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("839a930d5b874583b8da0760f9fa0254"), "IceBladeRage"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("4b9a5c51e9324e1ead30637101fb6fae"), "Thunderdark"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("7e6d65ed6fd840a786f8df09791572fc"), "Myrathi"));
      fallbackPlayerNames.add(new SimpleEntry(UUIDTypeAdapter.fromString("b97e12cedbb14c0cafc8132b708a9b88"), "XCompWiz"));
      entities.removeAll(entityBlacklist);
   }
}
