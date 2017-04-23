package net.minecraft.client.model;

import net.minecraft.entity.Entity;

/**
 * Created by David on 17/12/2016.
 */
public class ModelC4 extends ModelBase
{
	ModelRenderer block;
	ModelRenderer block1;
	ModelRenderer block11;
	ModelRenderer block12;
	ModelRenderer block13;
	ModelRenderer block14;
	ModelRenderer block15;
	ModelRenderer block16;
	ModelRenderer block17;
	ModelRenderer block2;
	ModelRenderer block21;
	ModelRenderer block22;
	ModelRenderer block23;
	ModelRenderer block18;
	ModelRenderer block19;
	ModelRenderer block110;

	public ModelC4()
	{
		this( 0.0f );
	}

	public ModelC4( float par1 )
	{
		block = new ModelRenderer( this, 0, 0 );
		block.setTextureSize( 64, 32 );
		block.addBox( -4.5F, -1.5F, -6F, 9, 3, 12);
		block.setRotationPoint( 0F, 22.5F, 0F );
		block1 = new ModelRenderer( this, 0, 15 );
		block1.setTextureSize( 64, 32 );
		block1.addBox( -2F, -0.5F, -2.5F, 4, 1, 5);
		block1.setRotationPoint( 0F, 21.2F, -1F );
		block11 = new ModelRenderer( this, 18, 16 );
		block11.setTextureSize( 64, 32 );
		block11.addBox( -2.5F, -0.5F, -0.5F, 5, 1, 1);
		block11.setRotationPoint( 0F, 21.2F, 3F );
		block12 = new ModelRenderer( this, 15, 15 );
		block12.setTextureSize( 64, 32 );
		block12.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block12.setRotationPoint( 0F, 21F, -2.5F );
		block13 = new ModelRenderer( this, 15, 15 );
		block13.setTextureSize( 64, 32 );
		block13.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block13.setRotationPoint( 1.25F, 21F, -2.5F );
		block14 = new ModelRenderer( this, 15, 15 );
		block14.setTextureSize( 64, 32 );
		block14.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block14.setRotationPoint( -1.25F, 21F, -2.5F );
		block15 = new ModelRenderer( this, 15, 15 );
		block15.setTextureSize( 64, 32 );
		block15.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block15.setRotationPoint( 0F, 21F, -1F );
		block16 = new ModelRenderer( this, 15, 15 );
		block16.setTextureSize( 64, 32 );
		block16.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block16.setRotationPoint( 1.25F, 21F, -1F );
		block17 = new ModelRenderer( this, 15, 15 );
		block17.setTextureSize( 64, 32 );
		block17.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block17.setRotationPoint( -1.25F, 21F, -1F );
		block2 = new ModelRenderer( this, 0, 0 );
		block2.setTextureSize( 64, 32 );
		block2.addBox( -0.5F, -0.5F, -1F, 1, 1, 2);
		block2.setRotationPoint( 0F, 21.6F, 2F );
		block21 = new ModelRenderer( this, 0, 6 );
		block21.setTextureSize( 64, 32 );
		block21.addBox( -0.5F, -0.5F, -1F, 1, 1, 2);
		block21.setRotationPoint( -0.2F, 21.6F, 2F );
		block22 = new ModelRenderer( this, 0, 3 );
		block22.setTextureSize( 64, 32 );
		block22.addBox( -0.5F, -0.5F, -1F, 1, 1, 2);
		block22.setRotationPoint( -0.4F, 21.6F, 2F );
		block23 = new ModelRenderer( this, 6, 0 );
		block23.setTextureSize( 64, 32 );
		block23.addBox( -0.5F, -0.5F, -1F, 1, 1, 2);
		block23.setRotationPoint( 1F, 21.6F, 2F );
		block18 = new ModelRenderer( this, 15, 15 );
		block18.setTextureSize( 64, 32 );
		block18.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block18.setRotationPoint( 0F, 21F, 0.5F );
		block19 = new ModelRenderer( this, 15, 15 );
		block19.setTextureSize( 64, 32 );
		block19.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block19.setRotationPoint( 1.25F, 21F, 0.5F );
		block110 = new ModelRenderer( this, 15, 15 );
		block110.setTextureSize( 64, 32 );
		block110.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		block110.setRotationPoint( -1.25F, 21F, 0.5F );
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

		block11.rotateAngleX = 0F;
		block11.rotateAngleY = 0F;
		block11.rotateAngleZ = 0F;
		block11.renderWithRotation(par7);

		block12.rotateAngleX = 0F;
		block12.rotateAngleY = 0F;
		block12.rotateAngleZ = 0F;
		block12.renderWithRotation(par7);

		block13.rotateAngleX = 0F;
		block13.rotateAngleY = 0F;
		block13.rotateAngleZ = 0F;
		block13.renderWithRotation(par7);

		block14.rotateAngleX = 0F;
		block14.rotateAngleY = 0F;
		block14.rotateAngleZ = 0F;
		block14.renderWithRotation(par7);

		block15.rotateAngleX = 0F;
		block15.rotateAngleY = 0F;
		block15.rotateAngleZ = 0F;
		block15.renderWithRotation(par7);

		block16.rotateAngleX = 0F;
		block16.rotateAngleY = 0F;
		block16.rotateAngleZ = 0F;
		block16.renderWithRotation(par7);

		block17.rotateAngleX = 0F;
		block17.rotateAngleY = 0F;
		block17.rotateAngleZ = 0F;
		block17.renderWithRotation(par7);

		block2.rotateAngleX = -1.503477E-08F;
		block2.rotateAngleY = -0.2617994F;
		block2.rotateAngleZ = -0.7853983F;
		block2.renderWithRotation(par7);

		block21.rotateAngleX = -1.503477E-08F;
		block21.rotateAngleY = -0.2617994F;
		block21.rotateAngleZ = -0.7853983F;
		block21.renderWithRotation(par7);

		block22.rotateAngleX = -1.503477E-08F;
		block22.rotateAngleY = -0.2617994F;
		block22.rotateAngleZ = -0.7853983F;
		block22.renderWithRotation(par7);

		block23.rotateAngleX = -4.254397E-09F;
		block23.rotateAngleY = 0.08726645F;
		block23.rotateAngleZ = -0.7853983F;
		block23.renderWithRotation(par7);

		block18.rotateAngleX = 0F;
		block18.rotateAngleY = 0F;
		block18.rotateAngleZ = 0F;
		block18.renderWithRotation(par7);

		block19.rotateAngleX = 0F;
		block19.rotateAngleY = 0F;
		block19.rotateAngleZ = 0F;
		block19.renderWithRotation(par7);

		block110.rotateAngleX = 0F;
		block110.rotateAngleY = 0F;
		block110.rotateAngleZ = 0F;
		block110.renderWithRotation(par7);

	}

}
