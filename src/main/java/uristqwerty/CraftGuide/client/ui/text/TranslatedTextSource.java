package uristqwerty.CraftGuide.client.ui.text;

import net.minecraft.client.resources.I18n;
import uristqwerty.CraftGuide.CraftGuideLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TranslatedTextSource extends TextSource
{
	private static List<WeakReference<TranslatedTextSource>> instances = new ArrayList<WeakReference<TranslatedTextSource>>();

	private String rawText;
	private String translatedText;

	public TranslatedTextSource(String text)
	{
		rawText = text;
		translatedText = translate(rawText);

		instances.add(new WeakReference(this));
	}

	@Override
	public String getText()
	{
		return translatedText;
	}

	public void setText(String text)
	{
		translatedText = translate(text);
		sendTextChanged();
	}

	private static String translate(String text)
	{
		//return StatCollector.translateToLocal(text);
		return I18n.format(text);
	}

	public static void reloadAll()
	{
		try
		{
			for(WeakReference<TranslatedTextSource> reference: instances)
			{
				if(reference != null)
				{
					TranslatedTextSource text = reference.get();

					if(text != null)
					{
						text.setText(text.rawText);
					}
				}
			}
		}
		catch(Exception e)
		{
			CraftGuideLog.log(e, "", true);
		}
	}
}
