package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesTools
{
    private String[][] recipePatterns = new String[][] {{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
    private Object[][] recipeItems;
    private static final String __OBFID = "CL_00000096";

    public RecipesTools()
    {
        this.recipeItems = new Object[][] {
            {Blocks.planks       , Blocks.cobblestone , Items.iron_ingot  , Items.diamond        , Items.gold_ingot    , Items.zinc, Items.cronyxe, Items.kobalt, Items.eldarium},
            {Items.wooden_pickaxe, Items.stone_pickaxe, Items.iron_pickaxe, Items.diamond_pickaxe, Items.golden_pickaxe, Items.zinc_pickaxe, Items.cronyxe_pickaxe, Items.kobalt_pickaxe, Items.eldarium_pickaxe},
            {Items.wooden_shovel , Items.stone_shovel , Items.iron_shovel , Items.diamond_shovel , Items.golden_shovel , Items.zinc_shovel, Items.cronyxe_shovel, Items.kobalt_shovel, Items.eldarium_shovel},
            {Items.wooden_axe    , Items.stone_axe    , Items.iron_axe    , Items.diamond_axe    , Items.golden_axe    , Items.zinc_axe, Items.cronyxe_axe, Items.kobalt_axe, Items.eldarium_axe},
            {Items.wooden_hoe    , Items.stone_hoe    , Items.iron_hoe    , Items.diamond_hoe    , Items.golden_hoe    , Items.zinc_hoe, Items.cronyxe_hoe, Items.kobalt_hoe, Items.eldarium_hoe}
        };
    }

    /**
     * Adds the tool recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager cm)
    {
        for (int i = 0; i < this.recipeItems[0].length; ++i)
        {
            Object material = this.recipeItems[0][i];

            for (int j = 0; j < this.recipeItems.length - 1; ++j)
            {
                Item result = (Item)this.recipeItems[j + 1][i];
                cm.addRecipe(new ItemStack(result), this.recipePatterns[j], '#', Items.stick, 'X', material);
            }
        }

        cm.addRecipe(new ItemStack(Items.shears), " #", "# ", '#', Items.iron_ingot);
    }
}
