package net.minecraft.client.gui;

import fr.dabsunter.eldaria.macrocmd.MacroCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GuiKeyBindingList extends GuiListExtended
{
    private final GuiControls guiControls;
    private final Minecraft mc;
    private final GuiListExtended.IGuiListEntry[] entries;
    private final ArrayList<MacroEntry> macroEntries;
    private int field_148188_n = 0;
    private static final String __OBFID = "CL_00000732";

    public GuiKeyBindingList(GuiControls guiControls, Minecraft mc)
    {
        super(mc, guiControls.width, guiControls.height, 63, guiControls.height - 32, 20);
        this.guiControls = guiControls;
        this.mc = mc;
        KeyBinding[] keyBindings = (KeyBinding[])ArrayUtils.clone(mc.gameSettings.keyBindings);
        this.entries = new GuiListExtended.IGuiListEntry[keyBindings.length + KeyBinding.func_151467_c().size()];
        Arrays.sort(keyBindings);
        int index = 0;
        String var5 = null;
        KeyBinding[] var6 = keyBindings;
        int var7 = keyBindings.length;

        for (int var8 = 0; var8 < var7; ++var8)
        {
            KeyBinding var9 = var6[var8];
            String var10 = var9.getKeyCategory();

            if (!var10.equals(var5))
            {
                var5 = var10;
                this.entries[index++] = new GuiKeyBindingList.CategoryEntry(var10, false);
            }

            int var11 = mc.fontRenderer.getStringWidth(I18n.format(var9.getKeyDescription(), new Object[0]));

            if (var11 > this.field_148188_n)
            {
                this.field_148188_n = var11;
            }

            this.entries[index++] = new GuiKeyBindingList.KeyEntry(var9, null);
        }

        this.entries[index] = new CategoryEntry("Macros", true);

        this.macroEntries = new ArrayList<>();

        for (Map.Entry<KeyBinding, String> kb : MacroCommands.MACROS.entrySet())
            macroEntries.add(new MacroEntry(kb.getKey(), kb.getValue()));
    }

    protected int getSize()
    {
        return this.entries.length + macroEntries.size();
    }

    public GuiListExtended.IGuiListEntry func_148180_b(int p_148180_1_)
    {
        return p_148180_1_ < this.entries.length ? this.entries[p_148180_1_] : macroEntries.get(p_148180_1_ - this.entries.length);
    }

    protected int func_148137_d()
    {
        return super.func_148137_d() + 15;
    }

    public int func_148139_c()
    {
        return super.func_148139_c() + 32;
    }

    @Override
    public boolean func_148179_a(int p_148179_1_, int p_148179_2_, int p_148179_3_) {
        for (MacroEntry entry : macroEntries)
            entry.cmdTextField.mouseClicked(p_148179_1_, p_148179_2_, p_148179_3_);

        return super.func_148179_a(p_148179_1_, p_148179_2_, p_148179_3_);
    }

    public void saveMacroCommands() { // Keyrisium - save commands
        for (MacroEntry entry : macroEntries)
            MacroCommands.MACROS.put(entry.keyBinding, entry.cmdTextField.getText());
        MacroCommands.save();
    }

    public void keyTyped(char c, int i) {
        for (MacroEntry entry : macroEntries)
            entry.cmdTextField.textboxKeyTyped(c, i);
    }

    public class CategoryEntry implements GuiListExtended.IGuiListEntry
    {
        private final String field_148285_b;
        private final int field_148286_c;
        private final GuiButton addButton;
        private static final String __OBFID = "CL_00000734";

        public CategoryEntry(String p_i45028_2_, boolean isMacro)
        {
            this.field_148285_b = I18n.format(p_i45028_2_);
            this.field_148286_c = GuiKeyBindingList.this.mc.fontRenderer.getStringWidth(this.field_148285_b);
            this.addButton = new GuiButton(0, 0, 0, 50, 18, "Add");
            this.addButton.visible = isMacro;
        }

        public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
        {
            GuiKeyBindingList.this.mc.fontRenderer.drawString(this.field_148285_b, GuiKeyBindingList.this.mc.currentScreen.width / 2 - this.field_148286_c / 2, p_148279_3_ + p_148279_5_ - GuiKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT - 1, 16777215);

            this.addButton.xPosition = p_148279_2_ + 190;
            this.addButton.yPosition = p_148279_3_;
            this.addButton.drawButton(GuiKeyBindingList.this.mc, p_148279_7_, p_148279_8_);
        }

        public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
        {
            if (this.addButton.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
                KeyBinding keyBinding = new KeyBinding("", 0, "Macros");
                macroEntries.add(new MacroEntry(keyBinding, ""));
                MacroCommands.put(keyBinding, "");
                return true;
            } else {
                return false;
            }
        }

        public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
            this.addButton.mouseReleased(p_148277_2_, p_148277_3_);
        }
    }

    public class KeyEntry implements GuiListExtended.IGuiListEntry
    {
        private final KeyBinding field_148282_b;
        private final String field_148283_c;
        private final GuiButton field_148280_d;
        private final GuiButton field_148281_e;
        private static final String __OBFID = "CL_00000735";

        private KeyEntry(KeyBinding p_i45029_2_)
        {
            this.field_148282_b = p_i45029_2_;
            this.field_148283_c = I18n.format(p_i45029_2_.getKeyDescription());
            this.field_148280_d = new GuiButton(0, 0, 0, 75, 18, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
            this.field_148281_e = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
        }

        public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
        {
            boolean var10 = GuiKeyBindingList.this.guiControls.field_146491_f == this.field_148282_b;
            GuiKeyBindingList.this.mc.fontRenderer.drawString(this.field_148283_c, p_148279_2_ + 90 - GuiKeyBindingList.this.field_148188_n, p_148279_3_ + p_148279_5_ / 2 - GuiKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
            this.field_148281_e.xPosition = p_148279_2_ + 190;
            this.field_148281_e.yPosition = p_148279_3_;
            this.field_148281_e.enabled = this.field_148282_b.getKeyCode() != this.field_148282_b.getKeyCodeDefault();
            this.field_148281_e.drawButton(GuiKeyBindingList.this.mc, p_148279_7_, p_148279_8_);
            this.field_148280_d.xPosition = p_148279_2_ + 105;
            this.field_148280_d.yPosition = p_148279_3_;
            this.field_148280_d.displayString = GameSettings.getKeyDisplayString(this.field_148282_b.getKeyCode());
            boolean var11 = false;

            if (this.field_148282_b.getKeyCode() != 0)
            {
                KeyBinding[] var12 = GuiKeyBindingList.this.mc.gameSettings.keyBindings;
                int var13 = var12.length;

                for (int var14 = 0; var14 < var13; ++var14)
                {
                    KeyBinding var15 = var12[var14];

                    if (var15 != this.field_148282_b && var15.getKeyCode() == this.field_148282_b.getKeyCode())
                    {
                        var11 = true;
                        break;
                    }
                }
            }

            if (var10)
            {
                this.field_148280_d.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.field_148280_d.displayString + EnumChatFormatting.WHITE + " <";
            }
            else if (var11)
            {
                this.field_148280_d.displayString = EnumChatFormatting.RED + this.field_148280_d.displayString;
            }

            this.field_148280_d.drawButton(GuiKeyBindingList.this.mc, p_148279_7_, p_148279_8_);
        }

        public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
        {
            if (this.field_148280_d.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_))
            {
                GuiKeyBindingList.this.guiControls.field_146491_f = this.field_148282_b;
                return true;
            }
            else if (this.field_148281_e.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_))
            {
                GuiKeyBindingList.this.mc.gameSettings.setKeyCodeSave(this.field_148282_b, this.field_148282_b.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                return true;
            }
            else
            {
                return false;
            }
        }

        public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_)
        {
            this.field_148280_d.mouseReleased(p_148277_2_, p_148277_3_);
            this.field_148281_e.mouseReleased(p_148277_2_, p_148277_3_);
        }

        KeyEntry(KeyBinding p_i45030_2_, Object p_i45030_3_)
        {
            this(p_i45030_2_);
        }
    }

    public class MacroEntry implements GuiListExtended.IGuiListEntry {
        private final KeyBinding keyBinding;
        private final GuiTextField cmdTextField;
        private final GuiButton keyButton;
        private final GuiButton removeButton;

        MacroEntry(KeyBinding keyBinding, String command)
        {
            this.keyBinding = keyBinding;
            this.cmdTextField = new GuiTextField(GuiKeyBindingList.this.mc.fontRenderer, 0, 0, 135, 18);
            this.cmdTextField.setText(command);
            this.keyButton = new GuiButton(0, 0, 0, 75, 18, I18n.format(keyBinding.getKeyDescription()));
            this.removeButton = new GuiButton(0, 0, 0, 50, 18, I18n.format("Remove"));
        }

        public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
        {
            boolean var10 = GuiKeyBindingList.this.guiControls.field_146491_f == this.keyBinding;
            this.cmdTextField.xPosition = p_148279_2_ + 90 - GuiKeyBindingList.this.field_148188_n;
            this.cmdTextField.yPosition = p_148279_3_ /*+ p_148279_5_ / 2 - GuiKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT / 2*/;
            this.cmdTextField.drawTextBox();
            //GuiKeyBindingList.this.mc.fontRenderer.drawString(this.field_148283_c, , , 16777215);
            this.removeButton.xPosition = p_148279_2_ + 190;
            this.removeButton.yPosition = p_148279_3_;
            this.removeButton.drawButton(GuiKeyBindingList.this.mc, p_148279_7_, p_148279_8_);
            this.keyButton.xPosition = p_148279_2_ + 105;
            this.keyButton.yPosition = p_148279_3_;
            this.keyButton.displayString = GameSettings.getKeyDisplayString(this.keyBinding.getKeyCode());
            boolean var11 = false;

            if (this.keyBinding.getKeyCode() != 0)
            {
                KeyBinding[] var12 = GuiKeyBindingList.this.mc.gameSettings.keyBindings;
                int var13 = var12.length;

                for (int var14 = 0; var14 < var13; ++var14)
                {
                    KeyBinding var15 = var12[var14];

                    if (var15 != this.keyBinding && var15.getKeyCode() == this.keyBinding.getKeyCode())
                    {
                        var11 = true;
                        break;
                    }
                }
            }

            if (var10)
            {
                this.keyButton.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.keyButton.displayString + EnumChatFormatting.WHITE + " <";
            }
            else if (var11)
            {
                this.keyButton.displayString = EnumChatFormatting.RED + this.keyButton.displayString;
            }

            this.keyButton.drawButton(GuiKeyBindingList.this.mc, p_148279_7_, p_148279_8_);
        }

        public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
        {
            if (this.keyButton.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_))
            {
                GuiKeyBindingList.this.guiControls.field_146491_f = this.keyBinding;
                return true;
            }
            else if (this.removeButton.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_))
            {
                macroEntries.remove(this);
                MacroCommands.remove(keyBinding);
                MacroCommands.save();
                KeyBinding.resetKeyBindingArrayAndHash();
                return true;
            }
            else
            {
                return false;
            }
        }

        public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_)
        {
            this.keyButton.mouseReleased(p_148277_2_, p_148277_3_);
            this.removeButton.mouseReleased(p_148277_2_, p_148277_3_);
        }
    }
}
