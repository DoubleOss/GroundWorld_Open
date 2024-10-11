package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiRulePage extends GuiScreen
{

    String name;
    int currnet = 0;
    int m_min = 0;
    int m_max = 6;

    Minecraft minecraft = Minecraft.getMinecraft();
    public GuiRulePage(String name, int min, int max)
    {
        this.name = name;
        m_min = min;
        m_max = max;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {

        if(mouseButton == 0)
        {

            if(currnet >= m_max)
            {
                minecraft.player.closeScreen();;
            }
            else
            {
                currnet++;
            }
        }
        else
        {

            if(currnet <= m_min)
            {

            }
            else
            {
                currnet--;
            }
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        ScaledResolution scaled = new ScaledResolution(mc);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/guis/rule/"+name+"_"+currnet+".png"));
        drawTexture(0, 0, width, height, 0, 0, 1, 1, 1 ,1);

    }

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
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
