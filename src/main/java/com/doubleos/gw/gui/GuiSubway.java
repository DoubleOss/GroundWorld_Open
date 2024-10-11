package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiSubway extends GuiScreen
{


    public class BtnGuiSubway extends GuiButton
    {


        String check = "";
        public BtnGuiSubway(int buttonId, int x, int y, String buttonText, String check)
        {
            super(buttonId, x, y, buttonText);
            this.check = check;
            this.width = 174/3;
            this.height = 94/3;

        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";

            float back_Width = 174f/3f;
            float back_Height = 94f/3f;
            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\subway\\"+check+".png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\subway\\"+check+".png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 5, 1);
            }

        }

        @Override
        public void playPressSound(SoundHandler soundHandlerIn) {
            soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(GwSoundHandler.OIL_KIOSK, 1.0F));
        }
    }

    String startPos = "";

    String endPos = "";


    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 0)
        {
            //지하철 엔티티 load
            mc.player.sendChatMessage("/지하철소환 " + startPos + " " + endPos);
            mc.player.closeScreen();
        }
        else
        {
            mc.player.closeScreen();
        }
    }

    public GuiSubway(String startPos, String endPos)
    {
        super();

        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ScaledResolution scaled = new ScaledResolution(mc);

        float scaleWidth = scaled.getScaledWidth();
        float scaleHeight = scaled.getScaledHeight();

        float backWidth = 1045 / 3f;
        float backHeight = 611 / 3f;

        float confirmWidth = 240f/ 3f;
        float confirmHeight = 107f/ 3f;


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\subway\\background.png"));
        Render.drawTexture(scaleWidth/2f - backWidth/2f, scaleHeight/2f - backHeight/2f, backWidth, backHeight, 0, 0, 1, 1, 1, 1);


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\subway\\"+startPos+".png"));
        Render.drawTexture(scaleWidth/2f - 101, scaleHeight/2f - 28f, confirmWidth, confirmHeight, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\subway\\"+endPos+".png"));
        Render.drawTexture(scaleWidth/2f + 21f, scaleHeight/2f - 28f, confirmWidth, confirmHeight, 0, 0, 1, 1, 1, 1);


        for(GuiButton button : buttonList)
            button.drawButton(mc, mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui()
    {
        ScaledResolution scaled = new ScaledResolution(mc);

        int scaleWidth = (int)scaled.getScaledWidth();
        int scaleHeight = (int)scaled.getScaledHeight();

        buttonList.clear();

        int id = 0;

        buttonList.add(new BtnGuiSubway(id++, scaleWidth/2 - 66, scaleHeight/2 + 33, "", "confirm"));
        buttonList.add(new BtnGuiSubway(id++, scaleWidth/2 + 8, scaleHeight/2 + 33, "", "no"));


    }
}
