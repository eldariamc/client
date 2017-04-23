package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SkinManager implements IResourceManager
{
    public static final ResourceLocation field_152793_a = new ResourceLocation("textures/entity/steve.png");
    private static final ExecutorService field_152794_b = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
    private final TextureManager field_152795_c;
    private final File field_152796_d;
    private final MinecraftSessionService sessionService;
    private final LoadingCache field_152798_f;
    private static final String __OBFID = "CL_00001830";

    public SkinManager(TextureManager p_i1044_1_, File p_i1044_2_, MinecraftSessionService p_i1044_3_)
    {
        this.field_152795_c = p_i1044_1_;
        this.field_152796_d = p_i1044_2_;
        this.sessionService = p_i1044_3_;
        this.field_152798_f = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader()
        {
            private static final String __OBFID = "CL_00001829";
            public Map func_152786_a(GameProfile gameProfile)
            {
                //return Minecraft.getMinecraft().func_152347_ac().getTextures(gameProfile, false);
                final HashMap map = Maps.newHashMap();

                map.put(Type.SKIN, new MinecraftProfileTexture("http://eldaria.fr/skins/" + gameProfile.getName() + ".png", null));

                try {
                    URL url = new URL("http://eldaria.fr/skins/" + gameProfile.getName() + "_cape.png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
                    connection.connect();
                    if (connection.getResponseCode()/100 == 2)
                        map.put(Type.CAPE, new MinecraftProfileTexture(url.toString(), null));
                } catch (IOException ex) {
                    System.err.println("Error while checking " + gameProfile.getName() + " cape");
                    ex.printStackTrace();
                }

                System.out.println("load properties:");
                System.out.println(map);
                return map;
            }
            public Object load(Object p_load_1_)
            {
                return this.func_152786_a((GameProfile)p_load_1_);
            }
        });
    }

    public ResourceLocation applyTexture(MinecraftProfileTexture p_152792_1_, Type p_152792_2_)
    {
        return this.applyTexture(p_152792_1_, p_152792_2_, (SkinManager.SkinAvailableCallback)null);
    }

    public ResourceLocation applyTexture(MinecraftProfileTexture texture, final Type type, final SkinManager.SkinAvailableCallback callback)
    {
        final ResourceLocation var4 = new ResourceLocation("skins/" + texture.getHash());
        ITextureObject var5 = this.field_152795_c.getTexture(var4);

        if (var5 != null)
        {
            if (callback != null)
            {
                callback.skinAvailable(type, var4);
            }
        }
        else
        {
            File var6 = new File(this.field_152796_d, texture.getHash().substring(0, 2));
            File var7 = new File(var6, texture.getHash());
            final ImageBufferDownload var8 = type == Type.SKIN ? new ImageBufferDownload() : null;
            ThreadDownloadImageData var9 = new ThreadDownloadImageData(var7, texture.getUrl(), field_152793_a, new IImageBuffer()
            {
                private static final String __OBFID = "CL_00001828";
                public BufferedImage parseUserSkin(BufferedImage p_78432_1_)
                {
                    if (var8 != null)
                    {
                        p_78432_1_ = var8.parseUserSkin(p_78432_1_);
                    }

                    return p_78432_1_;
                }
                public void func_152634_a()
                {
                    if (var8 != null)
                    {
                        var8.func_152634_a();
                    }

                    if (callback != null)
                    {
                        System.out.println("skinAvailable");
                        callback.skinAvailable(type, var4);
                    }
                }
            });
            this.field_152795_c.loadTexture(var4, var9);
        }

        return var4;
    }

    public void fetchTexture(final GameProfile gameProfile, final SkinManager.SkinAvailableCallback callback, final boolean p_152790_3_)
    {
        field_152794_b.submit(new Runnable()
        {
            public void run()
            {
                final HashMap var1 = Maps.newHashMap();

                /*try
                {
                    var1.putAll(SkinManager.this.sessionService.getTextures(gameProfile, p_152790_3_));
                }
                catch (InsecureTextureException var3)
                {
                    ;
                }

                if (var1.isEmpty() && gameProfile.getId().equals(Minecraft.getMinecraft().getSession().func_148256_e().getId()))
                {
                    var1.putAll(SkinManager.this.sessionService.getTextures(SkinManager.this.sessionService.fillProfileProperties(gameProfile, false), false));
                }*/

                var1.put(Type.SKIN, new MinecraftProfileTexture("http://eldaria.fr/skins/" + gameProfile.getName() + ".png", null));

                try {
                    URL url = new URL("http://eldaria.fr/skins/" + gameProfile.getName() + "_cape.png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
                    connection.connect();
                    if (connection.getResponseCode()/100 == 2)
                        var1.put(Type.CAPE, new MinecraftProfileTexture(url.toString(), null));
                } catch (IOException ex) {
                    System.err.println("Error while checking " + gameProfile.getName() + " cape");
                    ex.printStackTrace();
                }

                Minecraft.getMinecraft().func_152344_a(new Runnable()
                {
                    private static final String __OBFID = "CL_00001826";
                    public void run()
                    {
                        if (var1.containsKey(Type.SKIN))
                        {
                            SkinManager.this.applyTexture((MinecraftProfileTexture)var1.get(Type.SKIN), Type.SKIN, callback);
                        }

                        if (var1.containsKey(Type.CAPE))
                        {
                            SkinManager.this.applyTexture((MinecraftProfileTexture)var1.get(Type.CAPE), Type.CAPE, callback);
                        }
                    }
                });
            }
        });
    }

    public Map getProperties(GameProfile p_152788_1_)
    {
        return (Map)this.field_152798_f.getUnchecked(p_152788_1_);
    }

    @Override
    public Set getResourceDomains() {
        return null;
    }

    @Override
    public IResource getResource(ResourceLocation p_110536_1_) throws IOException {
        return null;
    }

    @Override
    public List getAllResources(ResourceLocation p_135056_1_) throws IOException {
        return null;
    }

    public interface SkinAvailableCallback
    {
        void skinAvailable(Type p_152121_1_, ResourceLocation p_152121_2_);
    }
}
