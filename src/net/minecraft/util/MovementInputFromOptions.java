package net.minecraft.util;

import net.minecraft.client.gui.GuiSprint;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";
    public boolean sprint = false;
    public boolean sprintToggle = false;
    public boolean sneakToggle = false;
    public boolean hasToggledSprint = false;
    public boolean hasToggledSneak = false;

    public MovementInputFromOptions(GameSettings p_i1237_1_)
    {
        this.gameSettings = p_i1237_1_;
    }

    public void updatePlayerMoveState()
    {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.getIsKeyPressed())
        {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.getIsKeyPressed())
        {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.getIsKeyPressed())
        {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.getIsKeyPressed())
        {
            --this.moveStrafe;
        }

        this.sprint = GuiSprint.keyBindSprint.getIsKeyPressed();
        if(!this.sprint) {
            if(!GuiSprint.disableModFunctionality && GuiSprint.keyBindSprintToggle.getIsKeyPressed()) {
                if(!this.hasToggledSprint) {
                    this.sprintToggle = !this.sprintToggle;
                    this.hasToggledSprint = true;
                }
            } else {
                this.hasToggledSprint = false;
            }

            this.sprint = this.sprintToggle;
        } else {
            this.sprintToggle = false;
        }

        this.jump = this.gameSettings.keyBindJump.getIsKeyPressed();
        this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();

        if(!this.sneak) {
            if(!GuiSprint.disableModFunctionality && GuiSprint.keyBindSneakToggle.getIsKeyPressed()) {
                if(!this.hasToggledSneak) {
                    this.sneakToggle = !this.sneakToggle;
                    this.hasToggledSneak = true;
                }
            } else {
                this.hasToggledSneak = false;
            }

            this.sneak = this.sneakToggle;
        }

        if (this.sneak)
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
}
