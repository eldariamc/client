package bspkrs.fml.util;

import fr.dabsunter.mcp.BusListener;
import fr.dabsunter.mcp.McpHandler;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;

public abstract class InputEventListener extends BusListener {
   private static HashMap<KeyBinding, InputEventListener> instances = new HashMap();
   protected KeyBinding keyBinding;
   protected boolean isKeyDown;
   protected boolean allowRepeats;

   public InputEventListener(KeyBinding keyBinding, boolean allowRepeats) {
      this.keyBinding = keyBinding;
      this.allowRepeats = allowRepeats;
      this.isKeyDown = false;
      instances.put(keyBinding, this);
      //ClientRegistry.registerKeyBinding(keyBinding);
      McpHandler.registerBusListener(this);
   }

   public KeyBinding getKeyBinding() {
      return this.keyBinding;
   }

   public void onKeyInput() {
      this.onInputEvent();
   }

   public void onMouseInput() {
      this.onInputEvent();
   }

   private void onInputEvent() {
      int keyCode = this.keyBinding.getKeyCode();
      boolean state = keyCode < 0?Mouse.isButtonDown(keyCode + 100):Keyboard.isKeyDown(keyCode);
      if(state != this.isKeyDown || state && this.allowRepeats) {
         if(state) {
            this.keyDown(this.keyBinding, state == this.isKeyDown);
         } else {
            this.keyUp(this.keyBinding);
         }

         this.isKeyDown = state;
      }

   }

   public abstract void keyDown(KeyBinding var1, boolean var2);

   public abstract void keyUp(KeyBinding var1);

   public static boolean isRegistered(KeyBinding kb) {
      return instances.containsKey(kb);
   }

   public static void unRegister(KeyBinding kb) {
      if(isRegistered(kb)) {
         //FMLCommonHandler.instance().bus().unregister(instances.get(kb));
         McpHandler.unregisterBusListener(instances.get(kb));
         instances.remove(kb);
      }

   }
}
