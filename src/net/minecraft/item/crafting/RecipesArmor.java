package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesArmor
{
    private String[][] recipePatterns = new String[][] {{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] recipeItems;

    public RecipesArmor()
    {
        this.recipeItems = new Object[][] {
                {Items.leather           , Blocks.fire               , Items.iron_ingot     , Items.diamond           , Items.gold_ingot       , Items.zinc           , Items.cronyxe           , Items.kobalt           , Items.eldarium},
                {Items.leather_helmet    , Items.chainmail_helmet    , Items.iron_helmet    , Items.diamond_helmet    , Items.golden_helmet    , Items.zinc_helmet    , Items.cronyxe_helmet    , Items.kobalt_helmet    , Items.eldarium_helmet},
                {Items.leather_chestplate, Items.chainmail_chestplate, Items.iron_chestplate, Items.diamond_chestplate, Items.golden_chestplate, Items.zinc_chestplate, Items.cronyxe_chestplate, Items.kobalt_chestplate, Items.eldarium_chestplate},
                {Items.leather_leggings  , Items.chainmail_leggings  , Items.iron_leggings  , Items.diamond_leggings  , Items.golden_leggings  , Items.zinc_leggings  , Items.cronyxe_leggings  , Items.kobalt_leggings  , Items.eldarium_leggings},
                {Items.leather_boots     , Items.chainmail_boots     , Items.iron_boots     , Items.diamond_boots     , Items.golden_boots     , Items.zinc_boots     , Items.cronyxe_boots     , Items.kobalt_boots     , Items.eldarium_boots}
        };
    }

    /**
     * Adds the armor recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager cm)
    {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2)
        {
            Object var3 = this.recipeItems[0][var2];

            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4)
            {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                cm.addRecipe(new ItemStack(var5), new Object[] {this.recipePatterns[var4], 'X', var3});
            }
        }

        cm.addShapelessRecipe(new ItemStack(Items.exploration_boots), Items.cronyxe_boots, Items.feather);
    }
}
