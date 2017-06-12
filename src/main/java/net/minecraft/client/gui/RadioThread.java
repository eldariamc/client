package net.minecraft.client.gui;

import java.io.InputStream;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.minecraft.client.Minecraft;
 

 
public class RadioThread extends Thread {
  public RadioThread(){
    super();
  }
 
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
            e.printStackTrace();
        }
    }
}