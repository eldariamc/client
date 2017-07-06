package uristqwerty.CraftGuide.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import uristqwerty.CraftGuide.CraftGuideLog;
import uristqwerty.CraftGuide.api.*;

import java.util.ArrayList;
import java.util.List;

public class GrassSeedDrops extends CraftGuideAPIObject implements RecipeProvider
{

	static final List<SeedEntry> seedList = new ArrayList<SeedEntry>();

	static {
		seedList.add(new SeedEntry(new ItemStack(Items.wheat_seeds), 10));
	}

	@Override
	public void generateRecipes(RecipeGenerator generator)
	{
		try
		{
			/* Why? Why is WeightedRandom.Item completely public, but ForgeHooks.SeedEntry not?
			 * Forge, of all packages, should be designed with the idea that mods may need
			 * access to odd places.
			 */
			//Field seedList = ForgeHooks.class.getDeclaredField("seedList");
			//seedList.setAccessible(true);
			List<? extends WeightedRandom.Item> entries = seedList;

			ItemStack grass = new ItemStack(Blocks.tallgrass, 1, 1);

			double total = 0;
			for(WeightedRandom.Item entry: entries)
			{
				total += entry.itemWeight;
			}

			total *= 8;

			Slot[] slots = new Slot[] {
					new ExtraSlot(18, 21, 16, 16, grass).clickable().showName().setSlotType(SlotType.INPUT_SLOT),
					new ChanceSlot(44, 21, 16, 16, true).setFormatString(" (%1$.6f%% chance)").setRatio(100000000).setSlotType(SlotType.OUTPUT_SLOT).drawOwnBackground(),
			};

			RecipeTemplate template = generator.createRecipeTemplate(slots, grass);

			for(WeightedRandom.Item entry: entries)
			{
				Object[] recipeContents = new Object[]{
						grass,
						new Object[]{
								((SeedEntry)entry).seed,
								(int)(entry.itemWeight/total * 100000000),
						},
				};

				generator.addRecipe(template, recipeContents);
			}
		}
		catch(SecurityException e)
		{
			CraftGuideLog.log(e);
		}
		catch(IllegalArgumentException e)
		{
			CraftGuideLog.log(e);
		}
	}

	static class SeedEntry extends WeightedRandom.Item
	{
		public final ItemStack seed;
		public SeedEntry(ItemStack seed, int weight)
		{
			super(weight);
			this.seed = seed;
		}
	}
}
