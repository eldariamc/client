package net.minecraft.client.gui;

import fr.dabsunter.mcp.McpHandler;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;

public class GuiIngameMenu extends GuiScreen
{
    private int field_146445_a;
    private int field_146444_f;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146445_a = 0;
        this.buttonList.clear();
        byte var1 = -16;
        /*boolean var2 = true;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + var1, I18n.format("menu.returnToMenu", new Object[0])));

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }

        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + var1, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + var1, 98, 20, I18n.format("menu.options", new Object[0])));
        GuiButton var3;
        this.buttonList.add(var3 = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + var1, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + var1, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + var1, 98, 20, I18n.format("gui.stats", new Object[0])));
        var3.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();*/


        this.buttonList.add(new GuiButton(4, this.width - 150, this.height / 2 - 77 + var1, 98, 20, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(0, this.width - 150, this.height / 2 - 55 + var1, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(10, this.width - 150, this.height / 2 - 33 + var1, 98, 20, I18n.format("Site")));
        this.buttonList.add(new GuiButton(11, this.width - 150, this.height / 2 - 11 + var1, 98, 20, I18n.format("Forum")));
        this.buttonList.add(new GuiButton(12, this.width - 150, this.height / 2 + 11 + var1, 98, 20, I18n.format("Boutique")));
        this.buttonList.add(new GuiButton(13, this.width - 150, this.height / 2 + 33 + var1, 98, 20, I18n.format("Teamspeak")));
        this.buttonList.add(new GuiButton(14, this.width - 150, this.height / 2 + 55 + var1, 98, 20, I18n.format("Wiki")));
        this.buttonList.add(new GuiRadioSlider(16, this.width / 5 - 102, 45, 0.0F, 100.0F, "§9Radio", 180));

        this.buttonList.add(new GuiButton(1, this.width - 150, this.height / 2 + 77 + var1, 98, 20, I18n.format("menu.disconnect")));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                p_146284_1_.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);
                this.mc.displayGuiScreen(new GuiMainMenu());

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
                break;

            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;

            case 10:
                McpHandler.browseOnDesktop("https://eldaria.fr");
                break;

            case 11:
                McpHandler.browseOnDesktop("https://forum.eldaria.fr");
                break;

            case 12:
                McpHandler.browseOnDesktop("https://eldaria.fr/shop");
                break;

            case 13:
                McpHandler.browseOnDesktop("ts3server://ts.eldaria.fr");
                break;

            case 14:
                McpHandler.browseOnDesktop("https://wiki.eldaria.fr");
                break;

        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game"), this.width - 101, this.height / 2 - 110, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
