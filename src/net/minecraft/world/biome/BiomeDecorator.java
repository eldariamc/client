package net.minecraft.world.biome;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public class BiomeDecorator
{
    /** The world the BiomeDecorator is currently decorating */
    protected World currentWorld;

    /** The Biome Decorator's random number generator. */
    protected Random randomGenerator;

    /** The X-coordinate of the chunk currently being decorated */
    protected int chunk_X;

    /** The Z-coordinate of the chunk currently being decorated */
    protected int chunk_Z;

    /** The clay generator. */
    protected WorldGenerator clayGen = new WorldGenClay(4);

    /** The sand generator. */
    protected WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);

    /** The gravel generator. */
    protected WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);

    /** The dirt generator. */
    protected WorldGenerator dirtGen = new WorldGenMinable(Blocks.dirt, 32);
    protected WorldGenerator gravelGen = new WorldGenMinable(Blocks.gravel, 32);
    protected WorldGenerator coalGen = new WorldGenMinable(Blocks.coal_ore, 16);
    protected WorldGenerator ironGen = new WorldGenMinable(Blocks.iron_ore, 8);

    /** Field that holds gold WorldGenMinable */
    protected WorldGenerator goldGen = new WorldGenMinable(Blocks.gold_ore, 8);

    /** Field that holds redstone WorldGenMinable */
    protected WorldGenerator redstoneGen = new WorldGenMinable(Blocks.redstone_ore, 7);

    /** Field that holds diamond WorldGenMinable */
    protected WorldGenerator diamondGen = new WorldGenMinable(Blocks.diamond_ore, 7);

    /** Field that holds Lapis WorldGenMinable */
    protected WorldGenerator lapisGen = new WorldGenMinable(Blocks.lapis_ore, 6);

    // --- Generators minerais Keyrisium ---

    protected WorldGenerator vanadiumGen = new WorldGenMinable(Blocks.zinc_ore, 6);
    protected WorldGenerator obsidianGen = new WorldGenMinable(Blocks.cronyxe_ore, 5);
    protected WorldGenerator pyriteGen = new WorldGenMinable(Blocks.kobalt_ore, 3);
	protected WorldGenerator keyrisiumGen = new WorldGenMinable(Blocks.eldarium_ore, 3);
    protected WorldGenerator gemmeGen = new WorldGenMinable(Blocks.gemme_ore, 1, Blocks.diamond_ore);

    // -------------------------------------

    protected WorldGenFlowers field_150514_p = new WorldGenFlowers(Blocks.yellow_flower);

    /** Field that holds mushroomBrown WorldGenFlowers */
    protected WorldGenerator mushroomBrownGen = new WorldGenFlowers(Blocks.brown_mushroom);

    /** Field that holds mushroomRed WorldGenFlowers */
    protected WorldGenerator mushroomRedGen = new WorldGenFlowers(Blocks.red_mushroom);

    /** Field that holds big mushroom generator */
    protected WorldGenerator bigMushroomGen = new WorldGenBigMushroom();

    /** Field that holds WorldGenReed */
    protected WorldGenerator reedGen = new WorldGenReed();

    /** Field that holds WorldGenCactus */
    protected WorldGenerator cactusGen = new WorldGenCactus();

    /** The water lily generation! */
    protected WorldGenerator waterlilyGen = new WorldGenWaterlily();

    /** Amount of waterlilys per chunk. */
    protected int waterlilyPerChunk;

    /**
     * The number of trees to attempt to generate per chunk. Up to 10 in forests, none in deserts.
     */
    protected int treesPerChunk;

    /**
     * The number of yellow flower patches to generate per chunk. The game generates much less than this number, since
     * it attempts to generate them at a random altitude.
     */
    protected int flowersPerChunk = 2;

    /** The amount of tall grass to generate per chunk. */
    protected int grassPerChunk = 1;

    /**
     * The number of dead bushes to generate per chunk. Used in deserts and swamps.
     */
    protected int deadBushPerChunk;

    /**
     * The number of extra mushroom patches per chunk. It generates 1/4 this number in brown mushroom patches, and 1/8
     * this number in red mushroom patches. These mushrooms go beyond the default base number of mushrooms.
     */
    protected int mushroomsPerChunk;

    /**
     * The number of reeds to generate per chunk. Reeds won't generate if the randomly selected placement is unsuitable.
     */
    protected int reedsPerChunk;

    /**
     * The number of cactus plants to generate per chunk. Cacti only work on sand.
     */
    protected int cactiPerChunk;

    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater.
     */
    protected int sandPerChunk = 1;

    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater. There
     * appear to be two separate fields for this.
     */
    protected int sandPerChunk2 = 3;

    /**
     * The number of clay patches to generate per chunk. Only generates when part of it is underwater.
     */
    protected int clayPerChunk = 1;

    /** Amount of big mushrooms per chunk */
    protected int bigMushroomsPerChunk;

    /** True if decorator should generate surface lava & water */
    public boolean generateLakes = true;
    private static final String __OBFID = "CL_00000164";

    public void func_150512_a(World p_150512_1_, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_)
    {
        if (this.currentWorld != null)
        {
            throw new RuntimeException("Already decorating!!");
        }
        else
        {
            this.currentWorld = p_150512_1_;
            this.randomGenerator = p_150512_2_;
            this.chunk_X = p_150512_4_;
            this.chunk_Z = p_150512_5_;
            this.func_150513_a(p_150512_3_);
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }

    protected void func_150513_a(BiomeGenBase p_150513_1_)
    {
        this.generateOres();
        int var2;
        int var3;
        int var4;

        for (var2 = 0; var2 < this.sandPerChunk2; ++var2)
        {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.sandGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
        }

        for (var2 = 0; var2 < this.clayPerChunk; ++var2)
        {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.clayGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
        }

        for (var2 = 0; var2 < this.sandPerChunk; ++var2)
        {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getTopSolidOrLiquidBlock(var3, var4), var4);
        }

        var2 = this.treesPerChunk;

        if (this.randomGenerator.nextInt(10) == 0)
        {
            ++var2;
        }

        int var5;
        int var6;

        for (var3 = 0; var3 < var2; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.currentWorld.getHeightValue(var4, var5);
            WorldGenAbstractTree var7 = p_150513_1_.func_150567_a(this.randomGenerator);
            var7.setScale(1.0D, 1.0D, 1.0D);

            if (var7.generate(this.currentWorld, this.randomGenerator, var4, var6, var5))
            {
                var7.func_150524_b(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
        }

        for (var3 = 0; var3 < this.bigMushroomsPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, var4, this.currentWorld.getHeightValue(var4, var5), var5);
        }

        for (var3 = 0; var3 < this.flowersPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) + 32);
            String var9 = p_150513_1_.func_150572_a(this.randomGenerator, var4, var6, var5);
            BlockFlower var8 = BlockFlower.func_149857_e(var9);

            if (var8.getMaterial() != Material.air)
            {
                this.field_150514_p.func_150550_a(var8, BlockFlower.func_149856_f(var9));
                this.field_150514_p.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
        }

        for (var3 = 0; var3 < this.grassPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            WorldGenerator var10 = p_150513_1_.getRandomWorldGenForGrass(this.randomGenerator);
            var10.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
        }

        for (var3 = 0; var3 < this.deadBushPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            (new WorldGenDeadBush(Blocks.deadbush)).generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
        }

        for (var3 = 0; var3 < this.waterlilyPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;

            for (var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2); var6 > 0 && this.currentWorld.isAirBlock(var4, var6 - 1, var5); --var6)
            {
                ;
            }

            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
        }

        for (var3 = 0; var3 < this.mushroomsPerChunk; ++var3)
        {
            if (this.randomGenerator.nextInt(4) == 0)
            {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                var6 = this.currentWorld.getHeightValue(var4, var5);
                this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }

            if (this.randomGenerator.nextInt(8) == 0)
            {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
                this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
            }
        }

        if (this.randomGenerator.nextInt(4) == 0)
        {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
        }

        if (this.randomGenerator.nextInt(8) == 0)
        {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
        }

        for (var3 = 0; var3 < this.reedsPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
        }

        for (var3 = 0; var3 < 10; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
        }

        if (this.randomGenerator.nextInt(32) == 0)
        {
            var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var3, var4) * 2);
            (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, var3, var5, var4);
        }

        for (var3 = 0; var3 < this.cactiPerChunk; ++var3)
        {
            var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            var5 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            var6 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(var4, var5) * 2);
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, var4, var6, var5);
        }

        if (this.generateLakes)
        {
            for (var3 = 0; var3 < 50; ++var3)
            {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8);
                var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, var4, var5, var6);
            }

            for (var3 = 0; var3 < 20; ++var3)
            {
                var4 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var5 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
                var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, var4, var5, var6);
            }
        }
    }

    /**
     * Standard ore generation helper. Generates most ores.
     */
    protected void genStandardOre1(int filons, WorldGenerator generator, int layerMin, int layerMax)
    {
        for (int i = 0; i < filons; ++i)
        {
            int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
            int var7 = this.randomGenerator.nextInt(layerMax - layerMin) + layerMin;
            int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
            generator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
        }
    }

    /**
     * Standard ore generation helper. Generates Lapis Lazuli.
     */
    protected void genStandardOre2(int filons, WorldGenerator generator, int p_76793_3_, int p_76793_4_)
    {
        for (int var5 = 0; var5 < filons; ++var5)
        {
            int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
            int var7 = this.randomGenerator.nextInt(p_76793_4_) + this.randomGenerator.nextInt(p_76793_4_) + (p_76793_3_ - p_76793_4_);
            int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
            generator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
        }
    }

    /**
     * Generates ores in the current chunk
     */
    protected void generateOres()
    {
        this.genStandardOre1(20, this.dirtGen, 0, 256);
        this.genStandardOre1(10, this.gravelGen, 0, 256);
        this.genStandardOre1(20, this.coalGen, 0, 128);
        this.genStandardOre1(20, this.ironGen, 0, 64);
        this.genStandardOre1(2, this.goldGen, 0, 32);
        this.genStandardOre1(8, this.redstoneGen, 0, 16);
        this.genStandardOre1(1, this.diamondGen, 0, 16);
        this.genStandardOre2(1, this.lapisGen, 16, 16);

        // --- Minerais Keyrisium ---

        this.genStandardOre1(4, this.vanadiumGen, 2, 20);
        this.genStandardOre1(3, this.obsidianGen, 2, 17);
        this.genStandardOre1(3, this.pyriteGen, 2, 16);
		//this.genStandardOre1(1, this.keyrisiumGen, 2, 16);
        this.genStandardOre1(425, this.gemmeGen, 0, 16);

        // --------------------------
    }
}
