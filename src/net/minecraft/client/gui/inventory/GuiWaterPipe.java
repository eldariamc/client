package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWaterPipe;
import net.minecraft.tileentity.TileEntityWaterPipe;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by David on 28/06/2016.
 */
public class GuiWaterPipe extends GuiContainer {

	private static final ResourceLocation waterPipeGUI = new ResourceLocation("textures/gui/container/water_pipe.png");
	private TileEntityWaterPipe waterPipe;

	public GuiWaterPipe(InventoryPlayer inventoryPlayer, TileEntityWaterPipe tileEntityWaterPipe) {
		super(new ContainerWaterPipe(inventoryPlayer, tileEntityWaterPipe));
		this.waterPipe = tileEntityWaterPipe;
	}

	protected void func_146979_b(int p_146979_1_, int p_146979_2_)
	{
		String var3 = this.waterPipe.isInventoryNameLocalized() ? this.waterPipe.getInventoryName() : I18n.format(this.waterPipe.getInventoryName());
		this.fontRendererObj.drawString(var3, this.defaultX / 2 - this.fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.defaultY - 96 + 2, 4210752);
	}

	@Override
	protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiWaterPipe.waterPipeGUI);
		int x = (this.width - this.defaultX) / 2;
		int y = (this.height - this.defaultY) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, this.defaultX, this.defaultY);
	}
}
