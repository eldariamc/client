package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class GuiLabel extends Gui
{
    protected int width;
    protected int height;
    public int posX;
    public int posY;
    private List<String> text;
    private boolean centered = true;
    public boolean visible = true;
    private boolean hasBox = false;
    private int textColor = 0xFFFFFF;
    private int backgroundColor = 0x000000;
    private int topLeftBorderColor = 0x808080;
    private int bottomRightBorderColor = 0x808080;
    private FontRenderer fontRenderer;
    private int shift;

    public GuiLabel(int posX, int posY, int width, int height, FontRenderer fontRenderer, String... text) {
        this(posX, posY, width, height, fontRenderer, Arrays.asList(text));
    }

    public GuiLabel(int posX, int posY, int width, int height, FontRenderer fontRenderer, List<String> text) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.fontRenderer = fontRenderer;
        this.text = text;
    }

    public List<String> lines() {
        return text;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setHasBox(boolean hasBox) {
        this.hasBox = hasBox;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBottomRightBorderColor(int bottomRightBorderColor) {
        this.bottomRightBorderColor = bottomRightBorderColor;
    }

    public void setTopLeftBorderColor(int topLeftBorderColor) {
        this.topLeftBorderColor = topLeftBorderColor;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public void drawLabel(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawBox(mc, mouseX, mouseY);
            int y = this.posY + this.height / 2 + this.shift / 2;
            y -= this.text.size() * 10 / 2;

            for (int i = 0; i < this.text.size(); ++i)
            {
                if (this.centered)
                {
                    this.drawCenteredString(this.fontRenderer, this.text.get(i), this.posX + this.width / 2, y + i * 10, this.textColor);
                }
                else
                {
                    this.drawString(this.fontRenderer, this.text.get(i), this.posX, y + i * 10, this.textColor);
                }
            }
        }
    }

    protected void drawBox(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.hasBox)
        {
            int w = this.width + this.shift * 2;
            int h = this.height + this.shift * 2;
            int x = this.posX - this.shift;
            int y = this.posY - this.shift;
            drawRect(x, y, x + w, y + h, this.backgroundColor);
            this.drawHorizontalLine(x, x + w, y, this.topLeftBorderColor);
            this.drawHorizontalLine(x, x + w, y + h, this.bottomRightBorderColor);
            this.drawVerticalLine(x, y, y + h, this.topLeftBorderColor);
            this.drawVerticalLine(x + w, y, y + h, this.bottomRightBorderColor);
        }
    }
}
