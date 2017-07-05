package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovementInputFromOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by David on 18/12/2016.
 */
public class GuiSprint extends GuiScreen {
	public static KeyBinding keyBindSprint = new KeyBinding("Sprint (hold)", 29, "key.categories.movement");
	public static KeyBinding keyBindSprintToggle = new KeyBinding("Sprint (toggle)", 34, "key.categories.movement");
	public static KeyBinding keyBindSneakToggle = new KeyBinding("Sneak (toggle)", 21, "key.categories.movement");
	public static KeyBinding keyBindSprintMenu = new KeyBinding("Sprint menu", 24, "key.categories.movement");
	public static int flyingBoost = 3;
	public static boolean allowDoubleTap = false;
	public static boolean allowAllDirs = false;
	public static boolean disableModFunctionality = false;
	public static boolean showedToggleSneakWarning = false;
	public static boolean held = false;
	public static boolean hasChanged = false;
	public static int stoptime = 0;
	private static boolean isPlayerClassEdited = false;
	public static boolean svFlyingBoost = false;
	public static boolean svRunInAllDirs = false;
	private static boolean shouldRestoreSneakToggle = false;
	public static String connectedServer = "";
	private static byte behaviorCheckTimer = 10;
	private GuiScreen parentScreen;
	private int buttonId = -1;
	private GuiButton btnDoubleTap;
	private GuiButton btnFlyBoost;
	private GuiButton btnAllDirs;
	private GuiButton btnDisableMod; // Eldaria - mod is always active
	protected KeyBinding[] sprintBindings;

	private static int nbtInt(NBTTagCompound tag, String key, int def) {
		return tag.hasKey(key) ? tag.getInteger(key) : def;
	}

	private static boolean nbtBool(NBTTagCompound tag, String key, boolean def) {
		return tag.hasKey(key) ? tag.getBoolean(key) : def;
	}

	public static void loadSprint(Minecraft mc) {
		File file = new File(mc.mcDataDir, "config/sprint.nbt");
		if (file.exists()) {
			try {
				NBTTagCompound e = CompressedStreamTools.readCompressed(new FileInputStream(file)).getCompoundTag("Data");
				if (e == null) {
					return;
				}

				keyBindSprintMenu.setKeyCode(nbtInt(e, "keyMenu", 24));
				keyBindSprint.setKeyCode(nbtInt(e, "keySprint", 29));
				keyBindSprintToggle.setKeyCode(nbtInt(e, "keySprintToggle", 34));
				keyBindSneakToggle.setKeyCode(nbtInt(e, "keySneakToggle", 21));
				flyingBoost = nbtInt(e, "flyBoost", 3);
				allowDoubleTap = nbtBool(e, "doubleTap", false);
				allowAllDirs = nbtBool(e, "allDirs", false);
				disableModFunctionality = nbtBool(e, "disableMod", false); // Eldaria - mod is always actice
				showedToggleSneakWarning = nbtBool(e, "showedWarn", false);
			} catch (Exception var3) {
				var3.printStackTrace();
				System.out.println("Error loading Better Sprinting settings!");
			}

			updateSettingBehavior(mc);
		}
	}

	public static void saveSprint(Minecraft mc) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("keyMenu", keyBindSprintMenu.getKeyCode());
		tag.setInteger("keySprint", keyBindSprint.getKeyCode());
		tag.setInteger("keySprintToggle", keyBindSprintToggle.getKeyCode());
		tag.setInteger("keySneakToggle", keyBindSneakToggle.getKeyCode());
		tag.setInteger("flyBoost", flyingBoost);
		tag.setBoolean("doubleTap", allowDoubleTap);
		tag.setBoolean("allDirs", allowAllDirs);
		tag.setBoolean("disableMod", disableModFunctionality); // Eldaria - mod is always active
		tag.setBoolean("showedWarn", showedToggleSneakWarning);
		NBTTagCompound fintag = new NBTTagCompound();
		fintag.setTag("Data", tag);

		try {
			CompressedStreamTools.writeCompressed(fintag, new FileOutputStream(new File(mc.mcDataDir, "config/sprint.nbt")));
		} catch (Exception var4) {
			var4.printStackTrace();
			System.out.println("Error saving Better Sprinting settings!");
		}

	}

	public static void doInitialSetup(Minecraft mc) {
		try {
			EntityPlayerSP.class.getDeclaredField("BetterSprintingClassCheck");
		} catch (Exception var5) {
			isPlayerClassEdited = true;
		}

		GameSettings settings = mc.gameSettings;
		KeyBinding[] newBinds = new KeyBinding[settings.keyBindings.length - 1];
		int a = 0;

		for (int index = 0; a < settings.keyBindings.length; ++a) {
			if (settings.keyBindings[a] != settings.keyBindSprint) {
				newBinds[index++] = settings.keyBindings[a];
			}
		}

		settings.keyBindings = newBinds;
		settings.keyBindSprint.setKeyCode(0);
		KeyBinding.resetKeyBindingArrayAndHash();
	}

	public static void updateSettingBehavior(Minecraft mc) {
		MovementInputFromOptions serverIP;
		if (mc.currentScreen != null && mc.thePlayer != null && mc.thePlayer.isSneaking()) {
			serverIP = (MovementInputFromOptions) mc.thePlayer.movementInput;
			if (serverIP.sneakToggle && !(mc.currentScreen instanceof GuiGameOver)) {
				if (!showedToggleSneakWarning) {
					mc.thePlayer.addChatMessage(new ChatComponentText("First-time warning: You can open inventories and menus while sneaking, however you will not be sneaking for the time it is open. Once you close the menu, sneaking will be restored."));
					mc.displayGuiScreen(null);
					mc.setIngameFocus();
					showedToggleSneakWarning = true;
					saveSprint(mc);
				} else {
					shouldRestoreSneakToggle = true;
					serverIP.sneakToggle = false;
				}
			}
		}

		if (shouldRestoreSneakToggle && mc.currentScreen == null) {
			serverIP = (MovementInputFromOptions) mc.thePlayer.movementInput;
			serverIP.sneakToggle = true;
			shouldRestoreSneakToggle = false;
		}

		if (behaviorCheckTimer > 0) {
			--behaviorCheckTimer;
		} else {
			behaviorCheckTimer = 10;
			if (mc.thePlayer == null) {
				connectedServer = "";
				svRunInAllDirs = false;
				svFlyingBoost = false;
			} else if (!mc.isIntegratedServerRunning() && mc.func_147104_D() != null && !disableModFunctionality) {
				String var2 = mc.func_147104_D().serverIP;
				if (!connectedServer.equals(var2)) {
					svRunInAllDirs = false;
					svFlyingBoost = false;
					connectedServer = new String(var2);
					if (!connectedServer.startsWith("127.0.0.1") && !connectedServer.equals("localhost")) {
						/* Osef du payload "g 1 mod mdrr"
						mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("BSprint", new byte[]{4}));*/
					} else {
						svRunInAllDirs = true;
						svFlyingBoost = true;
					}
				}
			}

		}
	}

	public GuiSprint(GuiScreen parentScreen) {
		this.sprintBindings = new KeyBinding[]{keyBindSprint, keyBindSprintToggle, keyBindSneakToggle, keyBindSprintMenu};
		this.parentScreen = parentScreen;
	}

	public static boolean canRunInAllDirs(Minecraft mc) {
		return disableModFunctionality ?false:mc.thePlayer == null && mc.theWorld == null || mc.isSingleplayer() || svRunInAllDirs;
	}

	public static boolean canBoostFlying(Minecraft mc) {
		return disableModFunctionality ?false:mc.thePlayer == null && mc.theWorld == null || mc.isSingleplayer() || mc.thePlayer.capabilities.isCreativeMode || svFlyingBoost;
	}

	public void initGui() {
		this.buttonList.clear();
		int left = this.getLeftColumnX();
		int ypos = 0;

		for (int a = 0; a < this.sprintBindings.length; ++a) {
			ypos = this.height / 6 + 24 * (a >> 1);
			GuiOptionButton btn = new GuiOptionButton(a, left + a % 2 * 160, ypos, 70, 20, this.getKeyCodeString(a));
			this.buttonList.add(btn);
			if ((a == 1 || a == 2) && disableModFunctionality) {
				btn.enabled = false;
			}
		}

		ypos += 48;
		this.btnDoubleTap = new GuiButton(199, left, ypos, 70, 20, "");
		this.buttonList.add(this.btnDoubleTap);
		if (disableModFunctionality) {
			this.btnDoubleTap.enabled = false;
		}

		this.btnAllDirs = new GuiButton(198, left + 160, ypos, 70, 20, "");
		this.buttonList.add(this.btnAllDirs);
		if (!canRunInAllDirs(this.mc)) {
			this.btnAllDirs.enabled = false;
		}

		ypos += 24;
		this.btnFlyBoost = new GuiButton(197, left, ypos, 70, 20, "");
		this.buttonList.add(this.btnFlyBoost);
		if (!canBoostFlying(this.mc)) {
			this.btnFlyBoost.enabled = false;
		}

		// Eldaria - mod is always active
		this.btnDisableMod = new GuiButton(196, left + 160, ypos, 70, 20, "");
		this.buttonList.add(this.btnDisableMod);
		if (this.mc.thePlayer != null || this.mc.theWorld != null) {
			this.btnDisableMod.enabled = false;
		}

		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, this.parentScreen == null ? 98 : 200, 20, I18n.format("gui.done")));
		if (this.parentScreen == null) {
			this.buttonList.add(new GuiButton(190, this.width / 2 + 2, this.height / 6 + 168, 98, 20, I18n.format("options.controls")));
		}

		this.updateButtons();
	}

	private void updateButtons() {
		this.btnDoubleTap.displayString = disableModFunctionality ? "Unavailable" : (allowDoubleTap ? "Enabled" : "Disabled");
		this.btnFlyBoost.displayString = canBoostFlying(this.mc) ? (flyingBoost == 0 ? "Disabled" : flyingBoost + 1 + "x") : "Unavailable";
		this.btnAllDirs.displayString = canRunInAllDirs(this.mc) ? (allowAllDirs ? "Enabled" : "Disabled") : "Unavailable";
		this.btnDisableMod.displayString = disableModFunctionality ? "Yes" : "No"; // Eldaria - mod is always active
	}


	protected void actionPerformed(GuiButton btn) {
		for (int var2 = 0; var2 < this.sprintBindings.length; ++var2) {
			this.buttonList.get(var2).displayString = this.getKeyCodeString(var2);
		}

		switch (btn.id) {
			case 190:
				this.mc.displayGuiScreen(new GuiControls(this, this.mc.gameSettings));
				break;
			case 191:
			case 192:
			case 193:
			case 194:
			case 195:
			default:
				this.buttonId = btn.id;
				StringBuilder var10001 = (new StringBuilder()).append("> ");
				btn.displayString = var10001.append(GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindings[btn.id].getKeyCode())).append(" <").toString();
				break;
			case 196:
				if (this.mc.thePlayer == null && this.mc.theWorld == null) {
					disableModFunctionality = !disableModFunctionality;
					this.initGui();
				}
				break;
			case 197:
				if (canBoostFlying(this.mc) && ++flyingBoost == 8) {
					flyingBoost = 0;
				}
				break;
			case 198:
				if (canRunInAllDirs(this.mc)) {
					allowAllDirs = !allowAllDirs;
				}
				break;
			case 199:
				if (!disableModFunctionality) {
					allowDoubleTap = !allowDoubleTap;
				}
				break;
			case 200:
				if (this.parentScreen == null) {
					this.mc.displayGuiScreen(null);
					this.mc.setIngameFocus();
				} else {
					this.mc.displayGuiScreen(this.parentScreen);
				}
		}

		saveSprint(this.mc);
		this.updateButtons();
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		if (!this.handleInput(-100 + par3)) {
			super.mouseClicked(par1, par2, par3);
		}

	}

	protected void keyTyped(char par1, int par2) {
		if (!this.handleInput(par2)) {
			super.keyTyped(par1, par2);
		}
	}

	private boolean handleInput(int par1) {
		if (this.buttonId >= 0 && this.buttonId < 180) {
			this.sprintBindings[this.buttonId].setKeyCode(par1);
			this.buttonList.get(this.buttonId).displayString = this.getKeyCodeString(this.buttonId);
			this.buttonId = -1;
			KeyBinding.resetKeyBindingArrayAndHash();
			return true;
		} else {
			return false;
		}
	}

	public void drawScreen(int mouseX, int mouseY, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Better Sprinting", this.width / 2, 20, 16777215);
		int left = this.getLeftColumnX();

		for (int a = 0; a < this.sprintBindings.length; ++a) {
			boolean i = false;

			for (int btn = 0; btn < this.sprintBindings.length; ++btn) {
				if (btn != a && this.sprintBindings[a].getKeyCode() == this.sprintBindings[btn].getKeyCode()) {
					i = true;
					break;
				}
			}

			for (int info = 0; info < this.mc.gameSettings.keyBindings.length; ++info) {
				if (this.sprintBindings[a].getKeyCode() == this.mc.gameSettings.keyBindings[info].getKeyCode()) {
					i = true;
					break;
				}
			}

			if (this.buttonId == a) {
				this.buttonList.get(a).displayString = "§f> §e??? §f<";
			} else if (i) {
				this.buttonList.get(a).displayString = "§c" + this.getKeyCodeString(a);
			} else {
				this.buttonList.get(a).displayString = this.getKeyCodeString(a);
			}

			this.drawString(this.fontRendererObj, this.sprintBindings[a].getKeyDescription(), left + a % 2 * 160 + 70 + 6, this.height / 6 + 24 * (a >> 1) + 7, -1);
		}

		this.drawButtonTitle("Double tapping", this.btnDoubleTap);
		this.drawButtonTitle("Run in all directions", this.btnAllDirs);
		this.drawButtonTitle("Flying boost", this.btnFlyBoost);
		//this.drawButtonTitle("Disable mod functionality", this.btnDisableMod); // Keyrisium - mod is always active

		for (int var10 = 0; var10 < this.buttonList.size(); ++var10) {
			GuiButton var11 = (GuiButton) this.buttonList.get(var10);
			if (mouseX >= var11.xPosition && mouseX <= var11.xPosition + var11.width && mouseY >= var11.yPosition && mouseY <= var11.yPosition + var11.height) {
				String var12 = "";
				switch (var10) {
					case 0:
						var12 = "Hold to sprint.";
						break;
					case 1:
						var12 = "Press once to start or stop sprinting.";
						break;
					case 2:
						var12 = "Press once to start or stop sneaking. When you open an inventory/menu,#sneaking will stop and get restored when you close the menu.";
						break;
					case 3:
						var12 = "Key to open this menu ingame.";
						break;
					case 4:
						var12 = "Enable or disable sprinting by double-tapping the forward key.";
						break;
					case 5:
						var12 = "Sprint in all directions.#You cannot use this in multiplayer.";
						break;
					case 6:
						var12 = "Press whilst flying in creative mode to fly faster.";
						break;
					case 7:
						var12 = "Disables all non-vanilla functionality of Better Sprinting.#This option can be used if a server doesn\'t allow the mod.";
				}

				String[] spl = var12.split("#");
				this.drawCenteredString(this.fontRendererObj, spl[0], this.width / 2, this.height / 6 + 138, -1);
				if (spl.length == 2) {
					this.drawCenteredString(this.fontRendererObj, spl[1], this.width / 2, this.height / 6 + 148, -1);
				}
				break;
			}
		}

		if (isPlayerClassEdited) {
			this.drawCenteredString(this.fontRendererObj, "§cDetected conflicted class, vital mod functions may not work!", this.width / 2, 30, -1);
		}

		super.drawScreen(mouseX, mouseY, par3);
	}

	private int getLeftColumnX() {
		return this.width / 2 - 155;
	}

	private String getKeyCodeString(int i) {
		return GameSettings.getKeyDisplayString(this.sprintBindings[i].getKeyCode());
	}

	private void drawButtonTitle(String title, GuiButton btn) {
		this.drawString(this.fontRendererObj, title, btn.xPosition + 70 + 6, btn.yPosition + 7, -1);
	}
}
