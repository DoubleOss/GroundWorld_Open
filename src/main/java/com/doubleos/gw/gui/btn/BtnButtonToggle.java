package com.doubleos.gw.gui.btn;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BtnButtonToggle extends GuiButton
{
    protected ResourceLocation resourceLocation;
    protected boolean stateTriggered;
    protected int xTexStart;
    protected int yTexStart;
    protected int xDiffTex;
    protected int yDiffTex;

    String m_btnName = "";
    public BtnButtonToggle(int buttonId, int xIn, int yIn, int widthIn, int heightIn, boolean buttonText, String btnName)
    {
        super(buttonId, xIn, yIn, widthIn, heightIn, "");
        this.stateTriggered = buttonText;
        m_btnName = btnName;
    }

    public void initTextureValues(int xTexStartIn, int yTexStartIn, int xDiffTexIn, int yDiffTexIn, ResourceLocation resourceLocationIn)
    {
        this.xTexStart = xTexStartIn;
        this.yTexStart = yTexStartIn;
        this.xDiffTex = xDiffTexIn;
        this.yDiffTex = yDiffTexIn;
        this.resourceLocation = resourceLocationIn;
    }

    public void setStateTriggered(boolean p_191753_1_)
    {
        this.stateTriggered = p_191753_1_;
    }

    public boolean isStateTriggered()
    {
        return this.stateTriggered;
    }

    public void setPosition(int p_191752_1_, int p_191752_2_)
    {
        this.x = p_191752_1_;
        this.y = p_191752_2_;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {



            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if(hovered)
                mc.getTextureManager().bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory_guide\\"+m_btnName+"_active.png"));
            else
                mc.getTextureManager().bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory_guide\\"+m_btnName+".png"));

            GlStateManager.disableDepth();
            int i = this.xTexStart;
            int j = this.yTexStart;

            if (this.stateTriggered)
            {
                i += this.xDiffTex;
            }

            if (this.hovered)
            {
                j += this.yDiffTex;
            }

            float textureWidth = 26f/3f;

            Render.drawTexture(this.x, this.y, textureWidth, textureWidth, 0,0, 1, 1 , 1, 1);
            //this.drawTexturedModalRect(this.x, this.y, i, j, this.width, this.height);
            GlStateManager.enableDepth();
        }
    }
}
