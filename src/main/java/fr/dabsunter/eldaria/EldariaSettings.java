package fr.dabsunter.eldaria;

import net.minecraft.client.resources.I18n;

import java.io.*;

/**
 * Created by David on 02/07/2017.
 */
public class EldariaSettings {

	private static File optionsFile = null;

	private static byte hudPreset = 1;

	public static boolean getBooleanValue(Options option) {
		switch (option) {

			default:
				throw new IllegalStateException(name(option) + " is not a Boolean option");
		}
	}

	public static void setBooleanValue(Options option, boolean value) {
		switch (option) {

			default:
				throw new IllegalStateException(name(option) + " is not a Boolean option");
		}
	}

	public static byte getByteValue(Options option) {
		switch (option) {
			case HUD_PRESETS:
				return hudPreset;
			default:
				throw new IllegalArgumentException(name(option) + " is not a Byte option");
		}
	}

	public static void setByteValue(Options option, byte value) {
		switch (option) {
			case HUD_PRESETS:
				HudManager.applyPresets(value);
				hudPreset = value;
				break;
			default:
				throw new IllegalArgumentException(name(option) + " is not a Byte option");
		}
	}

	public static short getShortValue(Options option) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Short option");
		}
	}

	public static void setShortValue(Options option, short value) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Short option");
		}
	}

	public static int getIntegerValue(Options option) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Integer option");
		}
	}

	public static void setIntegerValue(Options option, int value) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Integer option");
		}
	}

	public static long getLongValue(Options option) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Long option");
		}
	}

	public static void setLongValue(Options option, long value) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Long option");
		}
	}

	public static float getFloatValue(Options option) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Float option");
		}
	}

	public static void setFloatValue(Options option, float value) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Float option");
		}
	}

	public static double getDoubleValue(Options option) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Double option");
		}
	}

	public static void setDoubleValue(Options option, double value) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a Double option");
		}
	}

	public static String getStringValue(Options option) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a String option");
		}
	}

	public static void setStringValue(Options option, String value) {
		switch (option) {

			default:
				throw new IllegalArgumentException(name(option) + " is not a String option");
		}
	}

	public static void load(File root) {
		optionsFile = new File(root, "optionsel.txt");

		if (optionsFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(optionsFile));
				String line;
				String[] args;
				while ((line = reader.readLine()) != null) {
					args = line.split(":", 2);
					for (Options option : Options.values()) {
						if (option.toString().equals(args[0])) {
							try {
								switch (option.getType()) {
									case BOOLEAN:
										setBooleanValue(option, Boolean.parseBoolean(args[1]));
										break;
									case BYTE:
										setByteValue(option, Byte.parseByte(args[1]));
										break;
									case SHORT:
										setShortValue(option, Short.parseShort(args[1]));
										break;
									case INTEGER:
										setIntegerValue(option, Integer.parseInt(args[1]));
										break;
									case LONG:
										setLongValue(option, Long.parseLong(args[1]));
										break;
									case FLOAT:
										setFloatValue(option, Float.parseFloat(args[1]));
										break;
									case DOUBLE:
										setDoubleValue(option, Double.parseDouble(args[1]));
										break;
									case STRING:
										setStringValue(option, args[1]);
								}
							} catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
								System.err.println(args[0] + " a été ignoré -> " + ex);
							}
							break;
						}

					}
				}
				reader.close();
			} catch (IOException e) {
				System.err.println("Impossible de charger les réglages Eldaria !");
				e.printStackTrace();
			}
		}
	}

	public static void save() {
		if (optionsFile == null)
			throw new IllegalStateException("Les réglages Eldaria n'ont jamais été chargés");

		try {
			PrintWriter writer = new PrintWriter(new FileWriter(optionsFile));

			for (Options option : Options.values()) {
				writer.print(option);
				writer.print(':');
				switch (option.getType()) {
					case BOOLEAN:
						writer.println(getBooleanValue(option));
						break;
					case BYTE:
						writer.println(getByteValue(option));
						break;
					case SHORT:
						writer.println(getShortValue(option));
						break;
					case INTEGER:
						writer.println(getIntegerValue(option));
						break;
					case LONG:
						writer.println(getLongValue(option));
						break;
					case FLOAT:
						writer.println(getFloatValue(option));
						break;
					case DOUBLE:
						writer.println(getDoubleValue(option));
						break;
					case STRING:
						writer.println(getStringValue(option));
				}
			}

			writer.close();
		} catch (IOException e) {
			System.err.println("Impossible de sauvegarder les réglages Elaria !");
			e.printStackTrace();
		}
	}

	private static String name(Options option) {
		return option == null ? "NULL" : option.name();
	}

	public enum Options {
		HUD_PRESETS("hudPreset", "options.eldaria.hudPresets", OptionType.BYTE);

		private final String key;
		private final String lang;
		private final OptionType type;

		Options(String key, String lang, OptionType type) {
			this.key = key;
			this.lang = lang;
			this.type = type;
		}

		public String getLang(Object... placeholders) {
			return I18n.format(lang, placeholders);
		}

		public OptionType getType() {
			return type;
		}

		@Override
		public String toString() {
			return key;
		}
	}

	public enum OptionType {
		BOOLEAN, BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, STRING
	}
}
