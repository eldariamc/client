package net.minecraft.client.model;

import net.minecraft.entity.Entity;

/**
 * Created by David on 14/12/2016.
 */
public class ModelDynamite extends ModelBase
{
	private ModelRenderer block;
	private ModelRenderer block1;

	public ModelDynamite()
	{
		this( 0.0f );
	}

	public ModelDynamite( float par1 )
	{
		block = new ModelRenderer( this, 0, 0 );
		block.setTextureSize( 16, 16 );
		block.addBox( -1F, -5F, -1F, 2, 10, 2);
		block.setRotationPoint( 0F, 24F, 0F );
		block1 = new ModelRenderer( this, 6, 0 );
		block1.setTextureSize( 16, 16 );
		block1.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block1.setRotationPoint( 0F, 18.5F, 0F );
	}

	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		block.rotateAngleX = 0F;
		block.rotateAngleY = 0F;
		block.rotateAngleZ = 0F;
		block.renderWithRotation(par7);

		block1.rotateAngleX = 0F;
		block1.rotateAngleY = 0F;
		block1.rotateAngleZ = 0F;
		block1.renderWithRotation(par7);

	}

}
