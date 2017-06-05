package net.minecraft.client.gui;

import java.io.InputStream;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import net.kencochrane.raven.Raven;
import net.kencochrane.raven.RavenFactory;
import net.kencochrane.raven.dsn.Dsn;
import net.kencochrane.raven.event.Event;
import net.kencochrane.raven.event.EventBuilder;
import net.kencochrane.raven.event.interfaces.ExceptionInterface;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;


 

 
public class GuiRadioSlider extends GuiButton
{
    private float value;
    public boolean activated;
    private final float minimum;
    private final float maximum;
    private String name;
    RadioThread t = new RadioThread();
 
    public static String radio = "http://icecast.skyrock.net/s/natio_mp3_128k";
 
    public GuiRadioSlider(int par1, int par2, int par3, float parMin, float parMax, String string, int par4)
    {
        super(par1, par2, par3, par4, 20, string + " - " + String.valueOf(((int)Math.round((JavaSoundAudioDevice.radioVolume + 40) / 8))) + "%");
        this.value = JavaSoundAudioDevice.radioVolume / 46 + 29;
        this.minimum = parMin;
        this.maximum = parMax;
        this.name = string;
    }
 
    public int getHoverState(boolean p_146114_1_)
    {
        return 0;
    }
 
    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int x, int z)
    {
        if (this.field_146125_m)
        {
            if (this.activated)
            {
                this.value = (float)(x - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
 
                if (this.value < 0.0F)
                {
                    this.value = 0.0F;
                }
 
                if (this.value > 1.0F)
                {
                    this.value = 1.0F;
                }
 
                float var4 = this.value * 46 - 40;
                //System.out.println(var4);
 
                if (Minecraft.radioPlayer == null && var4 > -40.0F) {
                    //System.out.println("Gotcha");
 
                }
 
                if (var4 == -40.0F && Minecraft.radioPlayer != null) {
                    Minecraft.radioPlayer.close();
                    Minecraft.radioPlayer = null;
                }
 
                //System.out.println("Old: " + Minecraft.radioPlayer + " New: " + var4);
                if (JavaSoundAudioDevice.radioVolume != var4) JavaSoundAudioDevice.radioVolume = var4;
                this.displayString = this.name + " - " + String.valueOf((int) Math.floor(this.value * 100)) + "%";
            }
 
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.value * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.field_146128_h + (int)(this.value * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
        }
    }
 
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int x, int z)
    {
        if (super.mousePressed(mc, x, z))
        {
            this.value = (float)(x - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
 
            if (this.value < 0.0F)
            {
                this.value = 0.0F;
            }
 
            if (this.value > 1.0F)
            {
                this.value = 1.0F;
            }
 
 
            float var4 = this.value * 46 - 40;
            JavaSoundAudioDevice.radioVolume = var4;
            this.displayString = this.name + " - " + String.valueOf((int) Math.floor(this.value * 100)) + "%";
            this.activated = true;
 
            if (Minecraft.radioPlayer == null && var4 > -40.0F) {
                System.out.println("Gotcha");
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Gotcha");
                            InputStream is = null;
                            while (is == null) {
                                try {
                                    is = new URL(GuiRadioSlider.radio).openStream();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (Minecraft.radioPlayer == null) {
                                Minecraft.radioPlayer = new Player(is);
                                Minecraft.radioPlayer.play();
                            }
 
                            Minecraft.radioPlayer = null;
                        } catch (JavaLayerException e) {
                            String rawDsn = "https://c8bc49c9dd72489981b20b3eafea745e:2a8798f582db438c84e0c0962a3add44@app.getsentry.com/44106?raven.async=false";
                            Raven raven = RavenFactory.ravenInstance(new Dsn(
                                    rawDsn));
 
                            // enregistrer un message simple //nan j'ai des erreurs bah là
                            EventBuilder eventBuilder = new EventBuilder()
                                    .setMessage("Erreur -> exception player")
                                    .setLevel(Event.Level.ERROR)
                                    .setLogger("MinecraftClient")
                                    .addSentryInterface(
                                            new ExceptionInterface(e));
 
                            raven.runBuilderHelpers(eventBuilder); // Optional
                            raven.sendEvent(eventBuilder.build());
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
 
            return true;
        }
        else
        {
            return false;
        }
    }
 
    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int p_146118_1_, int p_146118_2_)
    {
        this.activated = false;
    }
}