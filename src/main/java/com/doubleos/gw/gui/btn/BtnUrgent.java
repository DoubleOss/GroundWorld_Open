package com.doubleos.gw.gui.btn;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.util.Sound;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class BtnUrgent extends GuiButton
{


    public String m_BtnName = "";
    Minecraft minecraft = Minecraft.getMinecraft();

    Variable variable = Variable.Instance();

    int m_btn_Width = 600 / 2;
    int m_btn_Height = 51 / 2;

    float m_btn_ImageWidth = 600 / 2f;
    float m_btn_ImageHeight = 51 / 2f;

    public MailData m_data;

    public boolean m_giveActive = false;


    int textureId = 0;

    public BtnUrgent(int buttonId, int x, int y, String buttonText)
    {
        super(buttonId, x, y, buttonText);
    }

    public BtnUrgent(int buttonId, int x, int y, String buttonText, String memberName, MailData data)
    {
        super(buttonId, x, y, buttonText);

        m_BtnName = memberName;

        m_btn_ImageWidth = 68/3f;
        m_btn_ImageHeight = 68/3f;

        int m_btn_Width = (int) ((int) 493f/3f);
        int m_btn_Height = (int) ((int) 73f/3f);

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

        m_data = data;

    }
    public BtnUrgent(int buttonId, int x, int y, String buttonText, String memberName)
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
        else if (memberName.equals("수령"))
        {
            float back_Width = 85f/3f;
            float back_Height = 29f/3f;

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
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\뒤로가기.png"));
                    Render.drawTexture(this.x/1.05f-0.25f, this.y/1.05f-0.25f, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
                }
                GlStateManager.popMatrix();

            }
            else
            {
                float back_Width = 66f/3f;
                float back_Height = 16f/3f;
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\뒤로가기.png"));
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
        else if (m_BtnName.equals("수령"))
        {
            float back_Width = 85f/3f;
            float back_Height = 29f/3f;

            if(active.equals("_active"))
            {
                if(!m_giveActive)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\수령버튼.png"));
                    Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 1, 1, 0.7f, 0.7F, 0.7F);
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\수령완료.png"));
                    Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 1, 1);
                }
            }
            else
            {
                if(!m_giveActive)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\수령버튼.png"));
                    Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 1, 1);
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\수령완료.png"));
                    Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 1, 1);
                }
            }

        }

        else
        {

            float squareStr_Width = 494 / 3f;
            float squareStr_Height = 76 / 3f;

            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\text_list_box.png"));
                Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1, 0.75f, 0.75f, 0.75f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\text_list_box.png"));
                Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1);
            }


            if(!m_data.m_readActive)
            {
                float currutionWidth = 108f / 3;
                float currutionHeight = 108f / 3;

                float newIcon = 26/3f;
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\긴급느낌표.png"));
                Render.drawTexture(this.x + width - 10, this.y+1, newIcon, newIcon, 0, 0, 1, 1, 3, 1f);
            }







        }

        //super.drawButton(mc, mouseX, mouseY, partialTicks);

    }


    public void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(x, y, depth);
            minecraft.fontRenderer.drawString(text, (x +stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }

}
