package net.minecraft.client.gui;

import bspkrs.armorstatushud.fml.gui.GuiASHConfig;
import bspkrs.statuseffecthud.fml.gui.GuiSEHConfig;
import fr.dabsunter.eldaria.EldariaSettings;
import fr.dabsunter.mcp.gui.OptionsHelper;
import net.minecraft.client.resources.I18n;

public class GuiEldariaOptions extends GuiScreen implements GuiYesNoCallback {

    private final GuiScreen parent;
    protected String guiTitle = "Eldaria Options";

    public GuiEldariaOptions(GuiScreen parent)
    {
        this.parent = parent;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui() {
        this.guiTitle = I18n.format("options.eldaria.title");

        OptionsHelper helper = new OptionsHelper();

        this.buttonList.add(new GuiButton(helper.nextId(), helper.getButtonX(width), helper.getButtonY(height), 150, 20,
                EldariaSettings.Options.HUD_PRESETS.getLang() + ": " + EldariaSettings.getByteValue(EldariaSettings.Options.HUD_PRESETS)));
        helper.nextId();
        helper.nextId();
        this.labelList.add(new GuiLabel(helper.getButtonX(width), helper.getButtonY(height), 150, 20, mc.fontRenderer,
                I18n.format("options.eldaria.hudAdvanced")));
        helper.nextId();
        this.buttonList.add(new GuiButton(helper.nextId(), helper.getButtonX(width), helper.getButtonY(height), 75, 20,
                I18n.format("options.eldaria.hud.armor")));
        this.buttonList.add(new GuiButton(114, helper.getButtonX(width) + 75, helper.getButtonY(height), 75, 20,
                I18n.format("options.eldaria.hud.effect")));

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 100: {
                    byte hudSet = EldariaSettings.getByteValue(EldariaSettings.Options.HUD_PRESETS);
                    if (++hudSet > 3)
                        hudSet = 1;

                    EldariaSettings.setByteValue(EldariaSettings.Options.HUD_PRESETS, hudSet);

                    button.displayString = EldariaSettings.Options.HUD_PRESETS.getLang() + ": " + hudSet;
                    break;
                }
                case 104:
                    try {
                        this.mc.displayGuiScreen(new GuiASHConfig(this));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    break;
                case 114:
                    this.mc.displayGuiScreen(new GuiSEHConfig(this));
                    break;

                case 200:
                    EldariaSettings.save();
                    mc.displayGuiScreen(parent);
                    break;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.guiTitle, this.width / 2, 15, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
