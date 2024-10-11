package com.doubleos.gw.gui.btn;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.util.Render;
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
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.awt.image.BufferedImage;

public class BtnContent extends GuiButton
{

    public String m_BtnName = "";
    Minecraft minecraft = Minecraft.getMinecraft();

    Variable variable = Variable.Instance();

    int m_btn_Width = 600 / 2;
    int m_btn_Height = 51 / 2;

    float m_btn_ImageWidth = 600 / 2f;
    float m_btn_ImageHeight = 51 / 2f;

    public boolean m_callActive = false;


    int textureId = 0;

    public BtnContent(int buttonId, int x, int y, String buttonText)
    {
        super(buttonId, x, y, buttonText);
    }

    public BtnContent(int buttonId, int x, int y, String buttonText, String memberName)
    {
        super(buttonId, x, y, buttonText);

        m_BtnName = memberName;

        m_btn_ImageWidth = 68/3f;
        m_btn_ImageHeight = 68/3f;

        int m_btn_Width = (int) 178;
        int m_btn_Height = (int) 26;

        if(memberName.equals("뒤로가기"))
        {
            m_btn_Width = (int) ((int) 66f/3f);
            m_btn_Height = (int) ((int) 18f/3f);
        }
        else if(memberName.equals("슬라이더"))
        {
            float back_Width = 12/3f;
            float back_Height = 192f/3f;

            m_btn_Width = (int) back_Width;
            m_btn_Height = (int) back_Height;
        }

        this.width = m_btn_Width;
        this.height = m_btn_Height;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
        ISound sound = Sound.getSound(GwSoundHandler.PHONETOUCH, SoundCategory.RECORDS, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS));
        soundHandlerIn.playSound(sound);
    }
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {



        String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";

        if(m_BtnName.equals("뒤로가기"))
        {
            if(active.equals("_active"))
            {
                float back_Width = 66f/3f;
                float back_Height = 16f/3f;
                GlStateManager.pushMatrix();
                {
                    GlStateManager.scale(1.05f, 1.05f, 1.05f);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\btn\\뒤로가기.png"));
                    Render.drawTexture(this.x/1.05f-0.25f, this.y/1.05f-0.25f, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
                }
                GlStateManager.popMatrix();

            }
            else
            {
                float back_Width = 66f/3f;
                float back_Height = 16f/3f;
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\btn\\뒤로가기.png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
            }



        }
        else if (m_BtnName.equals("슬라이더"))
        {
            float back_Width = 6/3f;
            float back_Height = 192f/3f;

            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\scroll_bar.png"));
                Render.drawTexture(this.x+1, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1, 0.5f, 0.5f, 0.5f);
            }
            else
            {

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\scroll_bar.png"));
                Render.drawTexture(this.x+1, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
            }

        }
        else
        {
            String callingPlayer = variable.getMemberIdToKoreaNickName(m_BtnName);
            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\face\\"+callingPlayer+".png"));
            Render.drawTexture(this.x -m_btn_ImageWidth + 45, this.y -0, m_btn_ImageWidth, m_btn_ImageHeight, 0, 0, 1, 1, 3, 1);


            float callingPlayerStr_Width  = 42f / 3f;
            float callingPlayerStr_Height  = 24f / 3f;

            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\str\\"+callingPlayer+".png"));
            Render.drawTexture(this.x + 58, this.y + 8, callingPlayerStr_Width, callingPlayerStr_Height, 0, 0, 1, 1, 3, 1);


            float contentLine_Width  = 538f / 3f;
            float contentLine_Height  = 2f / 3f;

            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\str\\line.png"));
            Render.drawTexture(this.x + 10f, this.y + 27, contentLine_Width, contentLine_Height, 0, 0, 1, 1, 3, 1);

            float call_Width = 28f/3f;
            float call_Height = 28f/3f;


            m_callActive = (mouseX >= this.x - call_Height/2 + 160 && mouseY >= this.y + call_Height/2 + 3
                    && mouseX < this.x - call_Height/2 + 160 + call_Width && mouseY < this.y + call_Height/2 + 3 + call_Height);


            if(m_callActive)
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\btn\\통화아이콘_hover.png"));
                Render.drawTexture(this.x - call_Height/2 + 160, this.y + call_Height/2 + 3, call_Width, call_Height, 0, 0, 1, 1, 4, 1);

            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\btn\\통화아이콘_normal.png"));
                Render.drawTexture(this.x - call_Height/2 + 160, this.y + call_Height/2 + 3, call_Width, call_Height, 0, 0, 1, 1, 4, 1);

            }



        }

        super.drawButton(mc, mouseX, mouseY, partialTicks);

    }

    public float easeOutCubic(float x)
    {
        return (float)(1 - Math.pow(1 - x, 3));
    }

    public void drawStringScaleResizeByRightWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (width - x -stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x +stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();
        this.minecraft.renderEngine.bindTexture(this.minecraft.player.getLocationSkin());

        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 1f, 1f);
        GlStateManager.popMatrix();
    }

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float r, float g, float b)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(r, g, b, alpha);
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

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.enableTexture2D();
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


    public void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (width - x - stringSize/2)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }

    private void drawYLinearTexture(float x, float y, float xSize, float ySize, float endV, float alpha, float z)
    {

        endV = 1 - endV * 0.01f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

            bufferbuilder.pos(x, y + (ySize*endV), z).tex(0.0d,endV).endVertex();
            bufferbuilder.pos(x, y+ySize, z).tex(0.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize, y+ySize, z).tex(1.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize, y+ (ySize*endV), z).tex(1.0d,endV).endVertex();

            /* 거꾸로 리니어

            bufferbuilder.pos(x, y + ySize * endV, z).tex(0.0d,endV).endVertex();
            bufferbuilder.pos(x+ xSize, y + ySize * endV, z).tex(1.0d,endV).endVertex();
            bufferbuilder.pos(x+ xSize, y, z).tex(1.0d, 0.0d).endVertex();
            bufferbuilder.pos(x, y, z).tex(0.0d,0.0d).endVertex();


             */



            tessellator.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private void drawXLinearTexture(float x, float y, float xSize, float ySize, float endU, float alpha, float z)
    {
        endU = endU * 0.01f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(x, y, z).tex(0.0d,0.0d).endVertex();
            bufferbuilder.pos(x, y+ySize, z).tex(0.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize * endU, y+ySize, z).tex(endU,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize * endU, y, z).tex(endU,0.0d).endVertex();

            tessellator.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }


    protected void renderHotbarItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (!stack.isEmpty())
        {
            float f = stack.getAnimationsToGo() - partialTicks;
            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                {
                    float f1 = 1.0F + f / 5.0F;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.translate((x + 8), (y + 12), 0.0F);
                    GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                    GlStateManager.translate(-(x + 8), -(y + 12), 0.0F);

                }

            }
            mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) player, stack, x, y);
            if (f > 0.0F)
                GlStateManager.popMatrix();
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, y);
        }
    }


}

