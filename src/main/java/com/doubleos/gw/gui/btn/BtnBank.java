package com.doubleos.gw.gui.btn;


import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class BtnBank extends GuiButton
{


    public String m_BtnName = "";

    float m_btn_ImageWidth = 600 / 2f;
    float m_btn_ImageHeight = 51 / 2f;

    public boolean m_select = false;


    float m_bankOilPer = 100;

    int m_playerOilCan = 0;

    public BtnBank(int buttonId, int x, int y, String buttonText)
    {
        super(buttonId, x, y, buttonText);
    }

    public BtnBank(int buttonId, int x, int y, String buttonText, String memberName)
    {
        super(buttonId, x, y, buttonText);

        m_BtnName = memberName;

        m_btn_ImageWidth = 210f/3f;
        m_btn_ImageHeight = 85f/3f;

        int m_btn_Width = (int) (210f/3f);
        int m_btn_Height = (int) (85f/3f);

        if(memberName.contains("%"))
        {
            m_btn_Width = (int) (168f/3f);
            m_btn_Height = (int) (105f/3f);
            m_btn_ImageWidth = 168f/3f;
            m_btn_ImageHeight = 105f/3f;

        }
        else if(memberName.equals("startButton"))
        {

        }
        else if(memberName.equals("back") || memberName.equals("next"))
        {
            float back_Width = 168f/3f;
            float back_Height = 62f/3f;

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

    public BtnBank(int buttonId, int x, int y, String buttonText, String memberName, float bankOilPer)
    {
        super(buttonId, x, y, buttonText);

        m_BtnName = memberName;

        m_btn_ImageWidth = 210f/3f;
        m_btn_ImageHeight = 85f/3f;

        int m_btn_Width = (int) (210f/3f);
        int m_btn_Height = (int) (85f/3f);

        if(memberName.contains("%"))
        {
            m_btn_Width = (int) (168f/3f);
            m_btn_Height = (int) (105f/3f);
            m_btn_ImageWidth = 168f/3f;
            m_btn_ImageHeight = 105f/3f;

        }
        else if(memberName.equals("startButton"))
        {

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

        m_bankOilPer = bankOilPer;
        //m_playerOilCan = playerOilCan;
    }




    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {

        String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";


        if(m_BtnName.equals("1235"))
        {

        }
        else if(m_BtnName.contains("%"))
        {
            float btn_Width =  (168f/3f);
            float btn_Height =  (105f/3f);
            float btn_Str_Width = 168f/3f;
            float btn_Str_Height = 105f/3f;

            int per = Integer.parseInt(m_BtnName.replace("%", ""));

            if(m_bankOilPer >= per && m_playerOilCan + per <= 100)
            {
                if(m_select)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\active.png"));
                    Render.drawTexture(this.x, this.y, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\"+this.m_BtnName+".png"));
                    Render.drawTexture(this.x, this.y, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1);
                }
                else if(active.equals("_active"))
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\able.png"));
                    Render.drawTexture(this.x, this.y, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\"+this.m_BtnName+".png"));
                    Render.drawTexture(this.x, this.y, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1, 0.75f, 0.75f, 0.75f);
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\able.png"));
                    Render.drawTexture(this.x, this.y, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\"+this.m_BtnName+".png"));
                    Render.drawTexture(this.x, this.y, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1);
                }
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\unlock.png"));
                Render.drawTexture(this.x, this.y, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1);
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_amount\\btn\\"+this.m_BtnName+".png"));
                Render.drawTexture(this.x, this.y, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1);
            }


        }
        else
        {
            float squareStr_Width = 210f/3f;
            float squareStr_Height = 85f/3f;

            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\start_display\\startButton.png"));
                Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\start_display\\startButton.png"));
                Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 5, 1);
            }
        }


        //super.drawButton(mc, mouseX, mouseY, partialTicks);

    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(GwSoundHandler.OIL_KIOSK, 1.0F));
    }


}
