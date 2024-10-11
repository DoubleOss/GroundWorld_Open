package com.doubleos.gw.gui.btn;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.util.Reference;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BtnRecipe extends GuiButton
{
    String m_btnName = "";

    public BtnRecipe(int buttonId, int x, int y, String buttonText, String btnName)
    {
        super(buttonId, x, y, buttonText);

        Variable variable = Variable.Instance();

        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

        float scaleWidth = (float) scaled.getScaledWidth_double();

        float scaleHeight = (float) scaled.getScaledHeight_double();

        float currutionWidth = 73f / 3f;
        float currutionHeight = 73f / 3f;

        float btn_Width = 96 / 3;
        float btn_Height = 30 /3;



        this.width = (int) currutionWidth;
        this.height = (int) currutionHeight;
        m_btnName = btnName;


    }

    public void setPosition(int p_191746_1_, int p_191746_2_)
    {
        this.x = p_191746_1_;
        this.y = p_191746_2_;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            boolean isActive = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);

            float btnImageWidth = 76f/3f;
            float btnImageHeight = 76f/3f;

            if(!isActive)
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory_guide.png"));
                Render.drawTexture(x, y, btnImageWidth, btnImageHeight, 0, 0, 1, 1, 1 ,1);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory_guide_active.png"));
                Render.drawTexture(x, y, btnImageWidth, btnImageHeight, 0, 0, 1, 1, 5 ,1);
            }

        }
    }
}
