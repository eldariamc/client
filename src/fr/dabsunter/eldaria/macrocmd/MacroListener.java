package fr.dabsunter.eldaria.macrocmd;

import bspkrs.fml.util.InputEventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;

/**
 * Created by David on 13/02/2017.
 */
public class MacroListener extends InputEventListener {
	public MacroListener(KeyBinding keyBinding) {
		super(keyBinding, false);
	}

	@Override
	public void keyDown(KeyBinding var1, boolean var2) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		if (player != null && Minecraft.getMinecraft().inGameHasFocus) {
			String command = MacroCommands.MACROS.get(keyBinding);
			if (command.startsWith("chat:"))
				command = command.substring(5);
			else if (!command.startsWith("/"))
				command = "/".concat(command);

			if (command.startsWith("/"))
				player.addChatMessage(new ChatComponentText("\u00a77[\u00a76\u00a7lMacro\u00a77] Send command: " + command));
			player.sendChatMessage(command);
		}
	}

	@Override
	public void keyUp(KeyBinding var1) {}
}
