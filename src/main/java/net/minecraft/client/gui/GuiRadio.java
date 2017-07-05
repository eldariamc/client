package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
 
public class GuiRadio extends GuiScreen {

	 private final GuiScreen parent;

	public GuiRadio(GuiScreen parent) {
		 this.parent = parent;
	}
 
	public void initGui() {
		this.buttonList.clear();
		byte var1 = -16;

		this.buttonList.add(new GuiButton(0, this.width / 2 - 149, this.height / 4 - 10 + var1, 98, 20, "§cOFF"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 49, this.height / 4 - 10 + var1, 98, 20, "NRJ"));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 51, this.height / 4 - 10 + var1, 98, 20, "SkyRock"));
		var1 += 22;
		this.buttonList.add(new GuiButton(3, this.width / 2 - 149, this.height / 4 - 10 + var1, 98, 20, "Funradio"));
		this.buttonList.add(new GuiButton(4, this.width / 2 - 49, this.height / 4 - 10 + var1, 98, 20, "Q-Dance"));
	}
 
	protected void actionPerformed(GuiButton p_146284_1_)
	{
		switch (p_146284_1_.id) {
			case 0:
			 	Minecraft.radioPlayer.stop();
			 	this.mc.displayGuiScreen(parent);
			 	break;
			case 1:
				Minecraft.radioPlayer.setFileName("http://cdn.nrjaudio.fm/audio1/fr/30001/mp3_128.mp3?origine=fluxradios");
				Minecraft.radioPlayer.start();
				this.mc.displayGuiScreen(parent);
				break;
			case 2:
 				Minecraft.radioPlayer.setFileName("http://icecast.skyrock.net/s/natio_mp3_128k");
				Minecraft.radioPlayer.start();
				this.mc.displayGuiScreen(parent);
				break;

			case 3:
				Minecraft.radioPlayer.setFileName("http://streaming.radio.funradio.fr/fun-1-48-192");
				Minecraft.radioPlayer.start();
				this.mc.displayGuiScreen(parent);
				break;
			case 4:
				Minecraft.radioPlayer.setFileName("http://stream01.platform02.true.nl:8000/qdance-hard");
				Minecraft.radioPlayer.start();
				this.mc.displayGuiScreen(parent);
				break;
		}
	}
 
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2 + 75), (float) (height / 4 + 25), 0.0F);
		GL11.glRotatef(-4.0F, 0.0F, 0.0F, 1.0F);
		float var8 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
		var8 = var8 * 90.0F / (float)(this.fontRendererObj.getStringWidth("Wesh Alors ?") + 32);
		GL11.glScalef(var8, var8, var8);
		this.drawString(this.fontRendererObj, "Wesh Alors ?", 0, -8, 66116160);
		GL11.glPopMatrix();
		this.drawCenteredString(this.fontRendererObj, I18n.format("options.eldaria.choseRadio.title"), this.width / 2, 10, 34004);
	}
}
