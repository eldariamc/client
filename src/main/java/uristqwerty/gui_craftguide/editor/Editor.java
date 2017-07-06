package uristqwerty.gui_craftguide.editor;

import uristqwerty.gui_craftguide.components.GuiElement;

import java.util.HashMap;
import java.util.Map;

public class Editor
{
	public static Map<String, Class<? extends GuiElement>> components = new HashMap<String, Class<? extends GuiElement>>();
	
	public static void registerComponent(Class<? extends GuiElement> component)
	{
		GuiElementMeta meta = component.getAnnotation(GuiElementMeta.class);
		
		if(meta != null)
		{
			components.put(meta.name(), component);
		}
	}
}
