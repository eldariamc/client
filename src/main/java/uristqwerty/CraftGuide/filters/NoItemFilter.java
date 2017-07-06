package uristqwerty.CraftGuide.filters;

import uristqwerty.CraftGuide.api.ItemFilter;
import uristqwerty.CraftGuide.api.Renderer;

import java.util.List;

public class NoItemFilter implements ItemFilter
{
	@Override
	public boolean matches(Object item)
	{
		return false;
	}

	@Override
	public void draw(Renderer renderer, int x, int y)
	{
	}

	@Override
	public List<String> getTooltip()
	{
		return null;
	}
}
