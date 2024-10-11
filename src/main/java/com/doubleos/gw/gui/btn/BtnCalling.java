package com.doubleos.gw.gui.btn;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.util.Sound;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class BtnCalling extends GuiButton {


    public String m_BtnName = "";
    public float m_Alpha = 0f;
    public BtnCalling(int buttonId, int x, int y, String buttonText, String btnName)
    {
        super(buttonId, x, y, buttonText);

        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

        float scaleWidth = (float) scaled.getScaledWidth_double();

        float scaleHeight = (float) scaled.getScaledHeight_double();

        float currutionWidth = 82f / 3;
        float currutionHeight = 82f / 3;

        if(btnName.equals("뒤로가기"))
        {
            currutionWidth = 11f/3f;
            currutionHeight = 23f/3f;
        }

        float btn_Width = 82f / 3;
        float btn_Height = 82f /3;



        this.width = (int) currutionWidth;
        this.height = (int) currutionHeight;
        m_BtnName = btnName;
    }


    public BtnCalling(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {

        ScaledResolution scaled = new ScaledResolution(mc);

        float scaleWidth = (float) scaled.getScaledWidth_double();

        float scaleHeight = (float) scaled.getScaledHeight_double();

        float currutionWidth = 82f / 3;
        float currutionHeight = 82f / 3;

        if(m_BtnName.equals("뒤로가기"))
        {
            currutionWidth = 11f/3f;
            currutionHeight = 23f/3f;
        }


        Variable variable = Variable.Instance();



        String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_on" : "";
        if(!m_BtnName.equals("뒤로가기"))
        {
            if(active.equals("_on"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\btn\\"+m_BtnName+"_hover.png"));
                drawTexture(this.x, this.y, currutionWidth, currutionHeight, 0, 0, 1, 1, 3, 1f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\btn\\"+m_BtnName+"_normal.png"));
                drawTexture(this.x, this.y, currutionWidth, currutionHeight, 0, 0, 1, 1, 3, 1f);
            }

        }
        else
        {
            if(active.equals("_on"))
            {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.scale(1.2f, 1.2f, 1.2f);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\btn\\"+m_BtnName+".png"));
                    drawTexture(this.x/1.2f, this.y/1.2f, currutionWidth, currutionHeight, 0, 0, 1, 1, 3, 1f);

                }
                GlStateManager.popMatrix();

            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\btn\\"+m_BtnName+".png"));
                drawTexture(this.x, this.y, currutionWidth, currutionHeight, 0, 0, 1, 1, 3, 1f);
            }
        }


        //super.drawButton(mc, mouseX, mouseY, partialTicks);
    }
    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
        ISound sound = Sound.getSound(GwSoundHandler.PHONETOUCH, SoundCategory.RECORDS, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS));
        soundHandlerIn.playSound(sound);
    }

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha) {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(u, v).endVertex();
            bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
            bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
            bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
}
