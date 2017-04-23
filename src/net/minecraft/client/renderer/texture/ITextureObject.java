package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResourceManager;

import java.io.IOException;

public interface ITextureObject
{
    void loadTexture(IResourceManager p_110551_1_) throws IOException;

    int getGlTextureId();
}
