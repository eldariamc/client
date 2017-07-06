package uristqwerty.CraftGuide.filters;

import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.CommonUtilities;
import uristqwerty.CraftGuide.CraftGuide;
import uristqwerty.CraftGuide.api.*;

import java.util.ArrayList;
import java.util.List;

public class SingleItemFilter implements CombinableItemFilter
{
	public ItemStack comparison;
	private NamedTexture overlayAny = Util.instance.getTexture("ItemStack-Any");

	public SingleItemFilter(ItemStack stack)
	{
		comparison = stack;
	}

	@Override
	public boolean matches(Object stack)
	{
		if(stack instanceof ItemStack)
		{
			return CommonUtilities.checkItemStackMatch((ItemStack)stack, comparison);
		}
		else if(stack instanceof List)
		{
			for(Object item: (List<?>)stack)
			{
				if(matches(item))
				{
					return true;
				}
			}

			return false;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void draw(Renderer renderer, int x, int y)
	{
		renderer.renderItemStack(x, y, comparison);

		if(CommonUtilities.getItemDamage(comparison) == CraftGuide.DAMAGE_WILDCARD)
		{
			renderer.renderRect(x - 1, y - 1, 18, 18, overlayAny);
		}
	}

	@Override
	public List<String> getTooltip()
	{
		return Util.instance.getItemStackText(comparison);
	}

	@Override
	public ItemFilter addItemFilter(ItemFilter other)
	{
		if(other instanceof CombinableItemFilter)
		{
			List<ItemStack> otherItems = ((CombinableItemFilter)other).getRepresentativeItems();

			if(otherItems != null)
			{
				return Util.instance.getCommonFilter(Util.instance.addItemLists(getRepresentativeItems(), otherItems));
			}
		}
		else if(other instanceof StringItemFilter /*|| other instanceof LiquidFilter*/ || other instanceof PseudoFluidFilter)
		{
			return new MultiFilter(this).addItemFilter(other);
		}

		return null;
	}

	@Override
	public ItemFilter subtractItemFilter(ItemFilter other)
	{
		if(other instanceof CombinableItemFilter)
		{
			List<ItemStack> otherItems = ((CombinableItemFilter)other).getRepresentativeItems();

			if(otherItems != null)
			{
				return Util.instance.getCommonFilter(Util.instance.subtractItemLists(getRepresentativeItems(), otherItems));
			}
		}

		return null;
	}

	@Override
	public List<ItemStack> getRepresentativeItems()
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>(1);
		list.add(comparison);
		return list;
	}
}
