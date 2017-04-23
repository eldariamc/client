package fr.dabsunter.eldaria.macrocmd;

import fr.dabsunter.mcp.Initiable;
import net.minecraft.client.settings.KeyBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by David on 12/02/2017.
 */
public class MacroCommands implements Initiable {

	public static final HashMap<KeyBinding, String> MACROS = new HashMap<>();

	public static void load() {
		try {
			File file = new File("config");
			if (!file.exists())
				file.mkdir();

			file = new File(file, "macro.cfg");
			if (!file.exists())
				file.createNewFile();

			Scanner scanner = new Scanner(file);

			MACROS.clear();

			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(":", 2);
				put(new KeyBinding("", Integer.parseInt(line[0]), "Macros"), line[1]);
			}
			scanner.close();
		} catch (IOException | NumberFormatException e) {
			System.err.println("Impossible de charger les macros");
			e.printStackTrace();
		}

	}

	public static void save() {
		try {
			File file = new File("config");
			if (!file.exists())
				file.mkdir();

			file = new File(file, "macro.cfg");
			if (!file.exists())
				file.createNewFile();

			PrintStream out = new PrintStream(new FileOutputStream(file));
			for (Map.Entry<KeyBinding, String> entry : MACROS.entrySet()) {
				out.print(entry.getKey().getKeyCode());
				out.print(':');
				out.println(entry.getValue());
				out.flush();
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Impossible de sauvegarder les macros");
			e.printStackTrace();
		}
	}

	public static void put(KeyBinding keyBinding, String command) {
		MACROS.put(keyBinding, command);
		new MacroListener(keyBinding);
	}

	public static void remove(KeyBinding keyBinding) {
		MACROS.remove(keyBinding);
		MacroListener.unRegister(keyBinding);
	}

	@Override
	public void preInit() {

	}

	@Override
	public void init() {
		/* Moved into KeyBinding.java
		new KeyBinding("", 0, "Macros"); // Register "Macros" key category (bybass nullpointer)
		*/
		load();
	}

	@Override
	public void postInit() {

	}
}
