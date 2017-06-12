package net.minecraft.client.renderer.tileentity;

/**
 * Created by David on 15/04/2017.
 */
import net.minecraft.client.model.ModelWaterPipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityWaterPipe;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

//@SideOnly(Side.CLIENT)
public class TileEntityWaterPipeRenderer extends TileEntitySpecialRenderer {
	private ModelWaterPipe modelWaterPipe = new ModelWaterPipe();
	/*private ModelTobacco modelTobacco = new ModelTobacco();
	private ModelCoal modelCoal = new ModelCoal();*/
	private static final ResourceLocation waterPipeGreen = new ResourceLocation("textures/models/waterpipe/green.png");
	private static final ResourceLocation waterPipeRed = new ResourceLocation("textures/models/waterpipe/red.png");
	private static final ResourceLocation waterPipeBlue = new ResourceLocation("textures/models/waterpipe/blue.png");
	private static final ResourceLocation waterPipeBlack = new ResourceLocation("textures/models/waterpipe/black.png");
	private static final ResourceLocation waterPipeLime = new ResourceLocation("textures/models/waterpipe/lime.png");
	private static final ResourceLocation waterPipeOrange = new ResourceLocation("textures/models/waterpipe/orange.png");
	private static final ResourceLocation waterPipeCyan = new ResourceLocation("textures/models/waterpipe/cyan.png");
	private static final ResourceLocation waterPipePink = new ResourceLocation("textures/models/waterpipe/pink.png");
	//private static final ResourceLocation coal = new ResourceLocation("reaper_waterpipe:textures/models/Coal.png");
	//private static final ResourceLocation charcoal = new ResourceLocation("reaper_waterpipe:textures/models/Charcoal.png");
	//private static final ResourceLocation coalBurning = new ResourceLocation("reaper_waterpipe:textures/models/CoalBurning.png");
	//private static final ResourceLocation tobaccoBurnt = new ResourceLocation("reaper_waterpipe:textures/models/TobaccoBurnt.png");

	public TileEntityWaterPipeRenderer() {
	}

	public void renderAModelAt(TileEntityWaterPipe entityPipe, double d, double d1, double d2, float f) {
		int i = entityPipe.getBlockMetadata();
		int j = 0/*entityPipe.getRotation() * 90*/;
		switch(i) {
			case 0:
				this.bindTexture(waterPipeBlack);
				break;
			case 1:
				this.bindTexture(waterPipeRed);
				break;
			case 2:
				this.bindTexture(waterPipeBlue);
				break;
			case 3:
				this.bindTexture(waterPipeGreen);
				break;
			case 4:
				this.bindTexture(waterPipeLime);
				break;
			case 5:
				this.bindTexture(waterPipeOrange);
				break;
			case 6:
				this.bindTexture(waterPipeCyan);
				break;
			case 7:
				this.bindTexture(waterPipePink);
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
		GL11.glRotatef((float)j, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		this.modelWaterPipe.renderModel(0.0625F);
		/*if(WaterPipe.instance.getAdvancedRender() == 1) {
			if(entityPipe.getSmokeTime() > 0) {
				this.bindTexture(coalBurning);
				this.modelCoal.renderModel(0.0625F);
			} else if(entityPipe.func_70301_a(0) != null) {
				if(entityPipe.func_70301_a(0).func_77960_j() == 0) {
					this.bindTexture(coal);
				} else {
					this.bindTexture(charcoal);
				}

				this.modelCoal.renderModel(0.0625F);
			}

			if(entityPipe.getSmokeTime() > 0) {
				this.bindTexture(tobaccoBurnt);
				this.modelTobacco.renderModel(0.0625F);
			} else if(entityPipe.func_70301_a(1) != null) {
				Tobacco loadedTobacco = WaterPipe.instance.getRecipesWaterPipe().getTobacco(entityPipe.func_70301_a(1).func_77973_b(), entityPipe.func_70301_a(1).func_77960_j());
				if(loadedTobacco != null) {
					this.bindTexture(loadedTobacco.getTexture());
					this.modelTobacco.renderModel(0.0625F);
				}
			}
		}*/

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		this.renderAModelAt((TileEntityWaterPipe)tileentity, d, d1, d2, f);
	}
}
