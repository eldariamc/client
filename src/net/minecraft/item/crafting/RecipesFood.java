package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesFood
{
    private static final String __OBFID = "CL_00000084";

    /**
     * Adds the food recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager cm)
    {
        cm.addShapelessRecipe(new ItemStack(Items.mushroom_stew), new Object[] {Blocks.brown_mushroom, Blocks.red_mushroom, Items.bowl});
        //cm.addRecipe(new ItemStack(Items.cookie, 8), new Object[] {"#X#", 'X', new ItemStack(Items.dye, 1, 3), '#', Items.wheat});
        cm.addRecipe(new ItemStack(Blocks.melon_block), new Object[] {"MMM", "MMM", "MMM", 'M', Items.melon});
        cm.addRecipe(new ItemStack(Items.melon_seeds), new Object[] {"M", 'M', Items.melon});
        cm.addRecipe(new ItemStack(Items.pumpkin_seeds, 4), new Object[] {"M", 'M', Blocks.pumpkin});
        cm.addShapelessRecipe(new ItemStack(Items.pumpkin_pie), new Object[] {Blocks.pumpkin, Items.sugar, Items.egg});
        cm.addShapelessRecipe(new ItemStack(Items.fermented_spider_eye), new Object[] {Items.spider_eye, Blocks.brown_mushroom, Items.sugar});
        cm.addShapelessRecipe(new ItemStack(Items.blaze_powder, 2), new Object[] {Items.blaze_rod});
        cm.addShapelessRecipe(new ItemStack(Items.magma_cream), new Object[] {Items.blaze_powder, Items.slime_ball});

        // --- Craft Eldaria ---

        cm.addRecipe(new ItemStack(Items.cookie, 6), "SSS", "BBB", 'S', Items.sugar, 'B', Items.bread);

        // ---------------------
    }
}
