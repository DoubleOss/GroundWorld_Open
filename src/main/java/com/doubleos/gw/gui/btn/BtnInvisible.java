package com.doubleos.gw.gui.btn;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class BtnInvisible extends GuiButtonExt
{

    public BtnInvisible(int id, int xPos, int yPos, String displayString) {
        super(id, xPos, yPos, displayString);
    }

    public BtnInvisible(int id, int xPos, int yPos, int width, int height, String displayString) {
        super(id, xPos, yPos, width, height, displayString);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial)
    {

    }
}
