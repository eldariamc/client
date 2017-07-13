package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesWeapons
{
    private String[][] recipePatterns = new String[][] {{"X", "X", "#"}};
    private Object[][] recipeItems;

    public RecipesWeapons()
    {
        this.recipeItems = new Object[][] {
            {Blocks.planks     , Blocks.cobblestone, Items.iron_ingot, Items.diamond      , Items.gold_ingot  , Items.zinc, Items.cronyxe, Items.kobalt, Items.eldarium},
            {Items.wooden_sword, Items.stone_sword , Items.iron_sword, Items.diamond_sword, Items.golden_sword, Items.zinc_sword, Items.cronyxe_sword, Items.kobalt_sword, Items.eldarium_sword}
        };
    }

    /**
     * Adds the weapon recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager cm)
    {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2)
        {
            Object var3 = this.recipeItems[0][var2];

            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4)
            {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                cm.addRecipe(new ItemStack(var5), this.recipePatterns[var4], '#', Items.stick, 'X', var3);
            }
        }

        cm.addRecipe(new ItemStack(Items.bow, 1), " #X", "# X", " #X", 'X', Items.string, '#', Items.stick);
        cm.addRecipe(new ItemStack(Items.arrow, 4), "X", "#", "Y", 'Y', Items.feather, 'X', Items.flint, '#', Items.stick);

        // --- Arcs Eldaria ---

        cm.addRecipe(new ItemStack(Items.zinc_bow), " #X", "# X", " #X", 'X', Items.string, '#', Items.zinc);
        cm.addRecipe(new ItemStack(Items.cronyxe_bow), " #X", "# X", " #X", 'X', Items.string, '#', Items.cronyxe);
        cm.addRecipe(new ItemStack(Items.kobalt_bow), " #X", "# X", " #X", 'X', Items.string, '#', Items.kobalt);
        cm.addRecipe(new ItemStack(Items.eldarium_bow), " #X", "# X", " #X", 'X', Items.string, '#', Items.eldarium);

        // --------------------
    }
}
