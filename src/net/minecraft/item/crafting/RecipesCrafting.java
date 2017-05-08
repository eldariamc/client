package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesCrafting
{
    private static final String __OBFID = "CL_00000095";

    /**
     * Adds the crafting recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager cm)
    {
        cm.addRecipe(new ItemStack(Blocks.chest), "###", "# #", "###", '#', Blocks.planks);
        cm.addRecipe(new ItemStack(Blocks.trapped_chest), "#-", '#', Blocks.chest, '-', Blocks.tripwire_hook);
        cm.addRecipe(new ItemStack(Blocks.ender_chest), "###", "#E#", "###", '#', Blocks.obsidian, 'E', Items.ender_eye);
        cm.addRecipe(new ItemStack(Blocks.furnace), "###", "# #", "###", '#', Blocks.cobblestone);
        cm.addRecipe(new ItemStack(Blocks.crafting_table), "##", "##", '#', Blocks.planks);
        cm.addRecipe(new ItemStack(Blocks.sandstone), "##", "##", '#', new ItemStack(Blocks.sand, 1, 0));
        cm.addRecipe(new ItemStack(Blocks.sandstone, 4, 2), "##", "##", '#', Blocks.sandstone);
        cm.addRecipe(new ItemStack(Blocks.sandstone, 1, 1), "#", "#", '#', new ItemStack(Blocks.stone_slab, 1, 1));
        cm.addRecipe(new ItemStack(Blocks.quartz_block, 1, 1), "#", "#", '#', new ItemStack(Blocks.stone_slab, 1, 7));
        cm.addRecipe(new ItemStack(Blocks.quartz_block, 2, 2), "#", "#", '#', new ItemStack(Blocks.quartz_block, 1, 0));
        cm.addRecipe(new ItemStack(Blocks.stonebrick, 4), "##", "##", '#', Blocks.stone);
        cm.addRecipe(new ItemStack(Blocks.iron_bars, 16), "###", "###", '#', Items.iron_ingot);
        cm.addRecipe(new ItemStack(Blocks.glass_pane, 16), "###", "###", '#', Blocks.glass);
        cm.addRecipe(new ItemStack(Blocks.redstone_lamp, 1), " R ", "RGR", " R ", 'R', Items.redstone, 'G', Blocks.glowstone);
        cm.addRecipe(new ItemStack(Blocks.beacon, 1), "GGG", "GSG", "OOO", 'G', Blocks.glass, 'S', Items.nether_star, 'O', Blocks.obsidian);
        cm.addRecipe(new ItemStack(Blocks.nether_brick, 1), "NN", "NN", 'N', Items.netherbrick);

        // --- Crafts Keyrisium ---

        cm.addRecipe(new ItemStack(Blocks.obsidian_furnace), "###", "#F#", "###", '#', Blocks.obsidian, 'F', Blocks.furnace);
        cm.addRecipe(new ItemStack(Blocks.reinforced_obsidian, 2), "##", "##", '#', Blocks.cobblestone);
        cm.addRecipe(new ItemStack(Items.compacted_slime_ball), "##", "##", '#', Items.slime_ball);
        cm.addRecipe(new ItemStack(Blocks.iron_ladder, 3), "# #", "###", "# #", '#', Items.iron_ingot);
        cm.addRecipe(new ItemStack(Blocks.slime), "###", "#C#", "###", '#', Items.slime_ball, 'C', Items.compacted_slime_ball);
        cm.addRecipe(new ItemStack(Blocks.obsand), "##", "G#", '#', Blocks.obsidian, 'G', Blocks.gravel);
        cm.addRecipe(new ItemStack(Blocks.water_pipe), "P ", "G ", "BG", 'P', Items.paper, 'G', Blocks.glass_pane, 'B', Items.brewing_stand);
        cm.addRecipe(new ItemStack(Blocks.zinc_chest), "###", "#C#", "###", '#', Items.zinc, 'C', Blocks.chest);
        cm.addRecipe(new ItemStack(Blocks.cronyxe_chest), "###", "#C#", "###", '#', Items.cronyxe, 'C', Blocks.chest);
        cm.addRecipe(new ItemStack(Blocks.kobalt_chest), "###", "#C#", "###", '#', Items.kobalt, 'C', Blocks.chest);
        cm.addRecipe(new ItemStack(Items.chest_orb), "CG ", "GKG", " G-", 'C', Items.cronyxe, 'G', Blocks.glass, 'K', Items.kobalt, '-', Items.stick);

        // ------------------------
    }
}
