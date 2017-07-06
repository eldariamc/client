package uristqwerty.gui_craftguide.theme.reader;

import uristqwerty.gui_craftguide.theme.Theme;

import java.lang.reflect.Field;

public class IntegerElement extends PrimitiveValueTemplate
{
	Long value = 0L;

	@Override
	public void characters(Theme theme, char[] chars, int start, int length)
	{
		try
		{
			value = Long.parseLong(String.valueOf(chars, start, length));
		}
		catch(NumberFormatException e)
		{
		}
	}

	@Override
	public Class<?> valueType()
	{
		return Long.class;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public boolean setField(Field field, Object object) throws IllegalArgumentException, IllegalAccessException
	{
		Class<?> type = field.getType();
		if(type == Long.class || type == long.class)
		{
			field.set(object, value);
		}
		else if(type == Integer.class || type == int.class)
		{
			field.set(object, value.intValue());
		}
		else if(type == Short.class || type == short.class)
		{
			field.set(object, value.shortValue());
		}
		else if(type == Byte.class || type == byte.class)
		{
			field.set(object, value.byteValue());
		}
		else
		{
			return false;
		}

		return true;
	}
}
