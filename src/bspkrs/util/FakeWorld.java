package bspkrs.util;

import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class FakeWorld extends World {
   public FakeWorld() {
      super(new FakeWorld.FakeSaveHandler(), "", new FakeWorld.FakeWorldProvider(), new WorldSettings(new WorldInfo(new NBTTagCompound())), (Profiler)null);
      this.difficultySetting = EnumDifficulty.HARD;
   }

   public BiomeGenBase getBiomeGenForCoords(int par1, int par2) {
      return BiomeGenBase.plains;
   }

   public BiomeGenBase getBiomeGenForCoordsBody(int par1, int par2) {
      return BiomeGenBase.plains;
   }

   public WorldChunkManager getWorldChunkManager() {
      return super.getWorldChunkManager();
   }

   protected void finishSetup() {

   }

   protected void initialize(WorldSettings par1WorldSettings) {
      super.initialize(par1WorldSettings);
   }

   public void setSpawnLocation() {
      super.setSpawnLocation();
   }

   public Block getTopBlock(int x, int z) {
      return Blocks.grass;
   }

   public boolean isAirBlock(int x, int y, int z) {
      return y > 63;
   }

   public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4) {
      return false;
   }

   public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6) {
      return false;
   }

   public Chunk getChunkFromBlockCoords(int x, int z) {
      return super.getChunkFromBlockCoords(x, z);
   }

   public boolean setBlock(int x, int y, int z, Block block, int meta, int notify) {
      return true;
   }

   public int getBlockMetadata(int x, int y, int z) {
      return 0;
   }

   public boolean setBlockMetadataWithNotify(int x, int y, int z, int par4, int par5) {
      return true;
   }

   public boolean setBlockToAir(int x, int y, int z) {
      return true;
   }

   public boolean breakBlock(int x, int y, int z, boolean p_147480_4_) {
      return this.isAirBlock(x, y, z);
   }

   public boolean setBlock(int x, int y, int z, Block p_147449_4_) {
      return true;
   }

   public void markBlockForUpdate(int p_147471_1_, int p_147471_2_, int p_147471_3_) {
   }

   public void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_) {
   }

   public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4) {
   }

   public void markBlockRangeForRenderUpdate(int p_147458_1_, int p_147458_2_, int p_147458_3_, int p_147458_4_, int p_147458_5_, int p_147458_6_) {
   }

   public void notifyBlocksOfNeighborChange(int p_147459_1_, int p_147459_2_, int p_147459_3_, Block p_147459_4_) {
   }

   public void notifyBlocksOfNeighborChange(int p_147441_1_, int p_147441_2_, int p_147441_3_, Block p_147441_4_, int p_147441_5_) {
   }

   public void notifyBlockOfNeighborChange(int p_147460_1_, int p_147460_2_, int p_147460_3_, Block p_147460_4_) {
   }

   public boolean isBlockTickScheduledThisTick(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_) {
      return false;
   }

   public boolean canBlockSeeTheSky(int x, int y, int z) {
      return y > 62;
   }

   public int getFullBlockLightValue(int x, int y, int z) {
      return 14;
   }

   public int getBlockLightValue(int x, int y, int z) {
      return 14;
   }

   public int getBlockLightValue_do(int x, int y, int z, boolean par4) {
      return 14;
   }

   public int getHeightValue(int x, int z) {
      return 63;
   }

   public int getChunkHeightMapMinimum(int x, int z) {
      return 63;
   }

   public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
      return 14;
   }

   public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
      return 14;
   }

   public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5) {
   }

   public void markBlockForRenderUpdate(int p_147479_1_, int p_147479_2_, int p_147479_3_) {
   }

   public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4) {
      return 14;
   }

   public float getLightBrightness(int par1, int par2, int par3) {
      return super.getLightBrightness(par1, par2, par3);
   }

   public boolean isDaytime() {
      return true;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3) {
      return null;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3) {
      return null;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_) {
      return null;
   }

   public void playSoundAtEntity(Entity par1Entity, String par2Str, float par3, float par4) {
   }

   public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, float par3, float par4) {
   }

   public void playSoundEffect(double par1, double par3, double par5, String par7Str, float par8, float par9) {
   }

   public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {
   }

   public void playRecord(String par1Str, int par2, int par3, int par4) {
   }

   public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
   }

   public boolean addWeatherEffect(Entity par1Entity) {
      return false;
   }

   public boolean spawnEntityInWorld(Entity par1Entity) {
      return false;
   }

   public void onEntityAdded(Entity par1Entity) {
   }

   public void onEntityRemoved(Entity par1Entity) {
   }

   public void removeEntity(Entity par1Entity) {
   }

   public void removePlayerEntityDangerously(Entity par1Entity) {
   }

   public void addWorldAccess(IWorldAccess par1iWorldAccess) {
      super.addWorldAccess(par1iWorldAccess);
   }

   public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
      return super.getCollidingBoundingBoxes(par1Entity, par2AxisAlignedBB);
   }

   public List func_147461_a(AxisAlignedBB p_147461_1_) {
      return super.func_147461_a(p_147461_1_);
   }

   public int calculateSkylightSubtracted(float par1) {
      return 6;
   }

   public void removeWorldAccess(IWorldAccess par1iWorldAccess) {
   }

   public float getSunBrightness(float par1) {
      return super.getSunBrightness(par1);
   }

   public Vec3 getSkyColor(Entity par1Entity, float par2) {
      return super.getSkyColor(par1Entity, par2);
   }

   public Vec3 getSkyColorBody(Entity par1Entity, float par2) {
      return null;
   }

   public float getCelestialAngle(float par1) {
      return super.getCelestialAngle(par1);
   }

   public int getMoonPhase() {
      return super.getMoonPhase();
   }

   public float getCurrentMoonPhaseFactor() {
      return super.getCurrentMoonPhaseFactor();
   }

   public float getCelestialAngleRadians(float par1) {
      return super.getCelestialAngleRadians(par1);
   }

   public Vec3 getCloudColour(float par1) {
      return super.getCloudColour(par1);
   }

   public Vec3 drawCloudsBody(float par1) {
      return null;
   }

   public Vec3 getFogColor(float par1) {
      return super.getFogColor(par1);
   }

   public int getPrecipitationHeight(int par1, int par2) {
      return super.getPrecipitationHeight(par1, par2);
   }

   public int getTopSolidOrLiquidBlock(int par1, int par2) {
      return 63;
   }

   public float getStarBrightness(float par1) {
      return super.getStarBrightness(par1);
   }

   public float getStarBrightnessBody(float par1) {
      return 0f;
   }

   public void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_) {
   }

   public void scheduleBlockUpdateWithPriority(int p_147454_1_, int p_147454_2_, int p_147454_3_, Block p_147454_4_, int p_147454_5_, int p_147454_6_) {
   }

   public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_) {
   }

   public void updateEntities() {
   }

   public void func_147448_a(Collection p_147448_1_) {
   }

   public void updateEntity(Entity par1Entity) {
   }

   public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2) {
   }

   public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB) {
      return true;
   }

   public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity) {
      return true;
   }

   public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB) {
      return false;
   }

   public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB) {
      return false;
   }

   public boolean func_147470_e(AxisAlignedBB p_147470_1_) {
      return false;
   }

   public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity) {
      return false;
   }

   public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
      return false;
   }

   public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material) {
      return false;
   }

   public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9) {
      return super.createExplosion(par1Entity, par2, par4, par6, par8, par9);
   }

   public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10) {
      return super.newExplosion(par1Entity, par2, par4, par6, par8, par9, par10);
   }

   public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB) {
      return super.getBlockDensity(par1Vec3, par2AxisAlignedBB);
   }

   public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5) {
      return true;
   }

   public String getDebugLoadedEntities() {
      return "";
   }

   public String getProviderName() {
      return "";
   }

   public TileEntity getTileEntity(int x, int y, int z) {
      return null;
   }

   public void setTileEntity(int x, int y, int z, TileEntity p_147455_4_) {
   }

   public void removeTileEntity(int x, int y, int z) {
   }

   public void markTileEntityForRemoval(TileEntity p_147457_1_) {
   }

   public boolean isBlockFullCube(int p_147469_1_, int p_147469_2_, int p_147469_3_) {
      return false;
   }

   public boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_) {
      return true;
   }

   public void calculateInitialSkylight() {
      super.calculateInitialSkylight();
   }

   public void setAllowedSpawnTypes(boolean par1, boolean par2) {
      super.setAllowedSpawnTypes(par1, par2);
   }

   public void tick() {
   }

   public void calculateInitialWeatherBody() {

   }

   protected void updateWeather() {
   }

   public void updateWeatherBody() {
   }

   protected void setActivePlayerChunksAndCheckLight() {
      super.setActivePlayerChunksAndCheckLight();
   }

   @Override
   protected int func_152379_p() {
      return 0;
   }

   protected void func_147467_a(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_) {
   }

   protected void func_147456_g() {
   }

   public boolean isBlockFreezable(int par1, int par2, int par3) {
      return false;
   }

   public boolean isBlockFreezableNaturally(int par1, int par2, int par3) {
      return false;
   }

   public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4) {
      return false;
   }

   public boolean canBlockFreezeBody(int par1, int par2, int par3, boolean par4) {
      return false;
   }

   public boolean canSnowAt(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_) {
      return false;
   }

   public boolean canSnowAtBody(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_) {
      return false;
   }

   public boolean updateAllLightTypes(int p_147451_1_, int p_147451_2_, int p_147451_3_) {
      return false;
   }

   public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_) {
      return false;
   }

   public boolean tickUpdates(boolean par1) {
      return false;
   }

   public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
      return null;
   }

   public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
      return super.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB);
   }

   public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3iEntitySelector) {
      return super.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, par3iEntitySelector);
   }

   public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB) {
      return super.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
   }

   public List selectEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3iEntitySelector) {
      return super.selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, par3iEntitySelector);
   }

   public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity) {
      return null;
   }

   public List getLoadedEntityList() {
      return super.getLoadedEntityList();
   }

   public void markTileEntityChunkModified(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_) {
   }

   public int countEntities(Class par1Class) {
      return 0;
   }

   public void addLoadedEntities(List par1List) {
   }

   public void unloadEntities(List par1List) {
   }

   public boolean canPlaceEntityOnSide(Block p_147472_1_, int p_147472_2_, int p_147472_3_, int p_147472_4_, boolean p_147472_5_, int p_147472_6_, Entity p_147472_7_, ItemStack p_147472_8_) {
      return true;
   }

   public PathEntity getPathEntityToEntity(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7) {
      return super.getPathEntityToEntity(par1Entity, par2Entity, par3, par4, par5, par6, par7);
   }

   public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9) {
      return super.getEntityPathToXYZ(par1Entity, par2, par3, par4, par5, par6, par7, par8, par9);
   }

   public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4) {
      return 0;
   }

   public int getBlockPowerInput(int par1, int par2, int par3) {
      return 0;
   }

   public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4) {
      return false;
   }

   public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4) {
      return 0;
   }

   public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3) {
      return false;
   }

   public int getStrongestIndirectPower(int par1, int par2, int par3) {
      return 0;
   }

   public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2) {
      return super.getClosestPlayerToEntity(par1Entity, par2);
   }

   public EntityPlayer getClosestPlayer(double par1, double par3, double par5, double par7) {
      return super.getClosestPlayer(par1, par3, par5, par7);
   }

   public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity, double par2) {
      return super.getClosestVulnerablePlayerToEntity(par1Entity, par2);
   }

   public EntityPlayer getClosestVulnerablePlayer(double par1, double par3, double par5, double par7) {
      return super.getClosestVulnerablePlayer(par1, par3, par5, par7);
   }

   public EntityPlayer getPlayerEntityByName(String par1Str) {
      return super.getPlayerEntityByName(par1Str);
   }

   public void sendQuittingDisconnectingPacket() {
      super.sendQuittingDisconnectingPacket();
   }

   public void checkSessionLock() throws MinecraftException {
   }

   public void func_82738_a(long par1) {
   }

   public long getSeed() {
      return 1L;
   }

   public long getTotalWorldTime() {
      return 1L;
   }

   public long getWorldTime() {
      return 1L;
   }

   public void setWorldTime(long par1) {
   }

   public ChunkCoordinates getSpawnPoint() {
      return new ChunkCoordinates(0, 64, 0);
   }

   public void setSpawnLocation(int par1, int par2, int par3) {
      super.setSpawnLocation(par1, par2, par3);
   }

   public void joinEntityInSurroundings(Entity par1Entity) {
   }

   public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
      return false;
   }

   public boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
      return false;
   }

   public void setEntityState(Entity par1Entity, byte par2) {
   }

   public IChunkProvider getChunkProvider() {
      return super.getChunkProvider();
   }

   public void addBlockEvent(int p_147452_1_, int p_147452_2_, int p_147452_3_, Block p_147452_4_, int p_147452_5_, int p_147452_6_) {
   }

   public ISaveHandler getSaveHandler() {
      return super.getSaveHandler();
   }

   public WorldInfo getWorldInfo() {
      return super.getWorldInfo();
   }

   public GameRules getGameRules() {
      return super.getGameRules();
   }

   public void updateAllPlayersSleepingFlag() {
   }

   public float getWeightedThunderStrength(float par1) {
      return 0.0F;
   }

   public void setThunderStrength(float p_147442_1_) {
   }

   public float getRainStrength(float par1) {
      return 0.0F;
   }

   public void setRainStrength(float par1) {
   }

   public boolean isThundering() {
      return false;
   }

   public boolean isRaining() {
      return false;
   }

   public boolean isRainingAt(int par1, int par2, int par3) {
      return false;
   }

   public boolean isBlockHighHumidity(int par1, int par2, int par3) {
      return false;
   }

   public void setItemData(String par1Str, WorldSavedData par2WorldSavedData) {
   }

   public WorldSavedData loadItemData(Class par1Class, String par2Str) {
      return super.loadItemData(par1Class, par2Str);
   }

   public int getUniqueDataId(String par1Str) {
      return super.getUniqueDataId(par1Str);
   }

   public void playBroadcastSound(int par1, int par2, int par3, int par4, int par5) {
   }

   public void playAuxSFX(int par1, int par2, int par3, int par4, int par5) {
   }

   public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6) {
   }

   public int getHeight() {
      return 256;
   }

   public int getActualHeight() {
      return 256;
   }

   public Random setRandomSeed(int par1, int par2, int par3) {
      return super.setRandomSeed(par1, par2, par3);
   }

   public ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_) {
      return super.findClosestStructure(p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
   }

   public boolean extendedLevelsInChunkCache() {
      return false;
   }

   public double getHorizon() {
      return super.getHorizon();
   }

   public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport) {
      return super.addWorldInfoToCrashReport(par1CrashReport);
   }

   public void destroyBlockInWorldPartially(int p_147443_1_, int p_147443_2_, int p_147443_3_, int p_147443_4_, int p_147443_5_) {
   }

   public Calendar getCurrentDate() {
      return super.getCurrentDate();
   }

   public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13nbtTagCompound) {
   }

   public Scoreboard getScoreboard() {
      return super.getScoreboard();
   }

   public void updateNeighborsAboutBlockChange(int p_147453_1_, int p_147453_2_, int p_147453_3_, Block p_147453_4_) {
   }

   public float getTensionFactorForBlock(double p_147462_1_, double p_147462_3_, double p_147462_5_) {
      return 0f;
   }

   public float func_147473_B(int p_147473_1_, int p_147473_2_, int p_147473_3_) {
      return super.func_147473_B(p_147473_1_, p_147473_2_, p_147473_3_);
   }

   public void func_147450_X() {
   }

   public void addTileEntity(TileEntity entity) {
   }

   public boolean isSideSolid(int x, int y, int z) {
      return y <= 63;
   }

   public boolean isSideSolid(int x, int y, int z, boolean _default) {
      return y <= 63;
   }

   public ImmutableSetMultimap getPersistentChunks() {
      return null;
   }

   public int getBlockLightOpacity(int x, int y, int z) {
      return 0;
   }

   public int countEntities(EnumCreatureType type, boolean forSpawnCount) {
      return 0;
   }

   public boolean blockExists(int par1, int par2, int par3) {
      return false;
   }

   protected boolean chunkExists(int par1, int par2) {
      return false;
   }

   protected IChunkProvider createChunkProvider() {
      return new FakeWorld.FakeChunkProvider();
   }

   public Entity getEntityByID(int i) {
      return EntityList.createEntityByID(i, this);
   }

   public Chunk getChunkFromChunkCoords(int par1, int par2) {
      return null;
   }

   public Block getBlock(int x, int y, int z) {
      return (Block)(y > 63?Blocks.air:Blocks.grass);
   }

   protected int getRenderDistanceChunks() {
      return 4;
   }

   public static class FakeChunkProvider implements IChunkProvider {
      public boolean chunkExists(int var1, int var2) {
         return false;
      }

      public Chunk provideChunk(int var1, int var2) {
         return null;
      }

      public Chunk loadChunk(int var1, int var2) {
         return null;
      }

      public void populate(IChunkProvider var1, int var2, int var3) {
      }

      public boolean saveChunks(boolean var1, IProgressUpdate var2) {
         return false;
      }

      public boolean unloadQueuedChunks() {
         return false;
      }

      public boolean canSave() {
         return false;
      }

      public String makeString() {
         return null;
      }

      public List getPossibleCreatures(EnumCreatureType var1, int var2, int var3, int var4) {
         return null;
      }

      public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
         return null;
      }

      public int getLoadedChunkCount() {
         return 0;
      }

      public void recreateStructures(int var1, int var2) {
      }

      public void saveExtraData() {
      }
   }

   protected static class FakeSaveHandler implements ISaveHandler {
      public WorldInfo loadWorldInfo() {
         return null;
      }

      public void checkSessionLock() {
      }

      public IChunkLoader getChunkLoader(WorldProvider var1) {
         return null;
      }

      public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {
      }

      public void saveWorldInfo(WorldInfo var1) {
      }

      @Override
      public IPlayerFileData getSaveHandler() {
         return null;
      }

      public void flush() {
      }

      public File getWorldDirectory() {
         return null;
      }

      public File getMapFileFromName(String var1) {
         return null;
      }

      public String getWorldDirectoryName() {
         return null;
      }
   }

   protected static class FakeWorldProvider extends WorldProvider {
      protected void generateLightBrightnessTable() {
      }

      protected void registerWorldChunkManager() {
         super.registerWorldChunkManager();
      }

      public IChunkProvider createChunkGenerator() {
         return super.createChunkGenerator();
      }

      public float calculateCelestialAngle(long par1, float par3) {
         return super.calculateCelestialAngle(par1, par3);
      }

      public int getMoonPhase(long par1) {
         return super.getMoonPhase(par1);
      }

      public boolean isSurfaceWorld() {
         return true;
      }

      public float[] calcSunriseSunsetColors(float par1, float par2) {
         return super.calcSunriseSunsetColors(par1, par2);
      }

      public Vec3 getFogColor(float par1, float par2) {
         return super.getFogColor(par1, par2);
      }

      public boolean canRespawnHere() {
         return true;
      }

      public float getCloudHeight() {
         return super.getCloudHeight();
      }

      public boolean isSkyColored() {
         return true;
      }

      public ChunkCoordinates getEntrancePortalLocation() {
         return super.getEntrancePortalLocation();
      }

      public int getAverageGroundLevel() {
         return 63;
      }

      public boolean getWorldHasVoidParticles() {
         return false;
      }

      public double getVoidFogYFactor() {
         return super.getVoidFogYFactor();
      }

      public boolean doesXZShowFog(int par1, int par2) {
         return false;
      }

      public void setDimension(int dim) {
      }

      public String getSaveFolder() {
         return null;
      }

      public String getWelcomeMessage() {
         return "";
      }

      public String getDepartMessage() {
         return "";
      }

      public double getMovementFactor() {
         return 0.0;
      }

      public ChunkCoordinates getRandomizedSpawnPoint() {
         return new ChunkCoordinates(0, 64, 0);
      }

      public boolean shouldMapSpin(String entity, double x, double y, double z) {
         return false;
      }

      public int getRespawnDimension(EntityPlayerMP player) {
         return 0;
      }

      public BiomeGenBase getBiomeGenForCoords(int x, int z) {
         return BiomeGenBase.plains;
      }

      public boolean isDaytime() {
         return true;
      }

      public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
         return null;
      }

      public Vec3 drawClouds(float partialTicks) {
         return null;
      }

      public float getStarBrightness(float par1) {
         return 0f;
      }

      public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
      }

      public void calculateInitialWeather() {
      }

      public void updateWeather() {
      }

      public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
         return false;
      }

      public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
         return false;
      }

      public void setWorldTime(long time) {
      }

      public long getSeed() {
         return 1L;
      }

      public long getWorldTime() {
         return 1L;
      }

      public void setSpawnPoint(int x, int y, int z) {

      }

      public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
         return false;
      }

      public boolean isBlockHighHumidity(int x, int y, int z) {
         return false;
      }

      public int getHeight() {
         return 256;
      }

      public int getActualHeight() {
         return 256;
      }

      public double getHorizon() {
         return 0.0;
      }

      public void resetRainAndThunder() {
      }

      public boolean canDoLightning(Chunk chunk) {
         return false;
      }

      public boolean canDoRainSnowIce(Chunk chunk) {
         return false;
      }

      public String getDimensionName() {
         return "";
      }

      public ChunkCoordinates getSpawnPoint() {
         return new ChunkCoordinates(0, 64, 0);
      }

      public boolean canCoordinateBeSpawn(int par1, int par2) {
         return true;
      }
   }
}
