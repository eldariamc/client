package uristqwerty.CraftGuide.client;

import uristqwerty.CraftGuide.UtilImplementationCommon;
import uristqwerty.CraftGuide.api.NamedTexture;
import uristqwerty.CraftGuide.client.ui.NamedTextureObject;
import uristqwerty.gui_craftguide.texture.DynamicTexture;

import java.util.HashMap;
import java.util.Map;

public class UtilImplementationClient extends UtilImplementationCommon
{
	private NamedTextureObject textFilter = new NamedTextureObject(DynamicTexture.instance("text-filter"));
	private NamedTextureObject itemStackAny = new NamedTextureObject(DynamicTexture.instance("stack-any"));
	private NamedTextureObject itemStackOreDict = new NamedTextureObject(DynamicTexture.instance("stack-oredict"));
	private NamedTextureObject itemStackOreDictSingle = new NamedTextureObject(DynamicTexture.instance("stack-oredict-single"));
	private NamedTextureObject itemStackBackground = new NamedTextureObject(DynamicTexture.instance("stack-background"));
	private NamedTextureObject error = new NamedTextureObject(DynamicTexture.instance("item_error"));

	private Map<String, NamedTextureObject> textureLookup = new HashMap<String, NamedTextureObject>();

	@Override
	public NamedTexture getTexture(String identifier)
	{
		if("ItemStack-Any".equalsIgnoreCase(identifier))
		{
			return itemStackAny;
		}
		else if("ItemStack-OreDict".equalsIgnoreCase(identifier))
		{
			return itemStackOreDict;
		}
		else if("ItemStack-OreDict-Single".equalsIgnoreCase(identifier))
		{
			return itemStackOreDictSingle;
		}
		else if("ItemStack-Background".equalsIgnoreCase(identifier))
		{
			return itemStackBackground;
		}
		else if("TextFilter".equalsIgnoreCase(identifier))
		{
			return textFilter;
		}
		else if("Error".equalsIgnoreCase(identifier))
		{
			return error;
		}

		if(!textureLookup.containsKey(identifier))
		{
			textureLookup.put(identifier, new NamedTextureObject(DynamicTexture.instance(identifier)));
		}

		return textureLookup.get(identifier);
	}
}
