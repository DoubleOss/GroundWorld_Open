package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.gui.btn.BtnBank;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.packet.*;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.util.Sound;
import com.doubleos.gw.variable.GroundItemStack;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class GuiEleBank extends GuiScreen
{
    public class BtnExit extends GuiButton
    {


        public String m_BtnName = "";
        Minecraft minecraft = Minecraft.getMinecraft();

        Variable variable = Variable.Instance();

        int m_btn_Width = 600 / 2;
        int m_btn_Height = 51 / 2;



        public BtnExit(int buttonId, int x, int y, String buttonText)
        {
            super(buttonId, x, y, buttonText);
        }



        @Override
        public void playPressSound(SoundHandler soundHandlerIn)
        {
            soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(GwSoundHandler.ELE_KIOSK, 1.0F));
        }

        public BtnExit(int buttonId, int x, int y, String buttonText, String memberName)
        {
            super(buttonId, x, y, buttonText);

            m_BtnName = memberName;


            float back_Width = 159f/3f;
            float back_Height = 77f/3f;

            m_btn_Width = (int) back_Width;
            m_btn_Height = (int) back_Height;


            this.width = m_btn_Width;
            this.height = m_btn_Height;
        }


        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {

            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";

            float back_Width = 159f/3f;
            float back_Height = 77f/3f;
            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_success\\"+m_BtnName+".png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_success\\"+m_BtnName+".png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 5, 1);
            }

            //super.drawButton(mc, mouseX, mouseY, partialTicks);

        }

    }

    public class BtnNextBack extends GuiButton
    {


        public String m_BtnName = "";
        Minecraft minecraft = Minecraft.getMinecraft();

        Variable variable = Variable.Instance();

        int m_btn_Width = 600 / 2;
        int m_btn_Height = 51 / 2;

        float m_btn_ImageWidth = 600 / 2f;
        float m_btn_ImageHeight = 51 / 2f;

        public boolean m_select = false;


        float m_bankOilPer = 100;

        int textureId = 0;

        public BtnNextBack(int buttonId, int x, int y, String buttonText)
        {
            super(buttonId, x, y, buttonText);
        }

        public BtnNextBack(int buttonId, int x, int y, String buttonText, String memberName)
        {
            super(buttonId, x, y, buttonText);

            m_BtnName = memberName;

            m_btn_ImageWidth = 210f/3f;
            m_btn_ImageHeight = 85f/3f;


            float back_Width = 159/3f;
            float back_Height = 77f/3f;

            m_btn_Width = (int) back_Width;
            m_btn_Height = (int) back_Height;


            this.width = m_btn_Width;
            this.height = m_btn_Height;
        }


        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {

            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";

            float back_Width = 159f/3f;
            float back_Height = 77f/3f;
            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_ready\\"+m_BtnName+".png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_ready\\"+m_BtnName+".png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 5, 1);
            }

            //super.drawButton(mc, mouseX, mouseY, partialTicks);

        }

        @Override
        public void playPressSound(SoundHandler soundHandlerIn)
        {
            soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(GwSoundHandler.ELE_KIOSK, 1.0F));
        }


    }

    public class BtnEleBank extends GuiButton
    {


        public String m_BtnName = "";

        float m_btn_ImageWidth = 600 / 2f;
        float m_btn_ImageHeight = 51 / 2f;

        public boolean m_select = false;

        String m_btnType = "";

        float m_bankOilPer = 100;

        public BtnEleBank(int buttonId, int x, int y, String buttonText)
        {
            super(buttonId, x, y, buttonText);
        }

        public BtnEleBank(int buttonId, int x, int y, String buttonText, String memberName)
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

        @Override
        public void playPressSound(SoundHandler soundHandlerIn)
        {
            soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(GwSoundHandler.ELE_KIOSK, 1.0F));
        }


        public BtnEleBank(int buttonId, int x, int y, String buttonText, String memberName, float bankOilPer)
        {
            super(buttonId, x, y, buttonText);

            m_btnType = memberName;
            m_BtnName = memberName.replace("%", "");

            m_btn_ImageWidth = 210f/3f;
            m_btn_ImageHeight = 85f/3f;

            int m_btn_Width = (int) (210f/3f);
            int m_btn_Height = (int) (85f/3f);

            if(m_btnType.contains("%"))
            {
                m_btn_Width = (int) (169f/3f);
                m_btn_Height = (int) (110f/3f);
                m_btn_ImageWidth = 169f/3f;
                m_btn_ImageHeight = 110f/3f;

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
        }


        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {

            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";


            if(m_BtnName.equals("1235"))
            {

            }
            else if(m_btnType.contains("%"))
            {
                float btn_Width =  (168f/3f);
                float btn_Height =  (105f/3f);
                float btn_Str_Width = 80f/3f;
                float btn_Str_Height = 54f/3f;

                int per = Integer.parseInt(m_BtnName.replace("%", ""));

                float ableWidth = 169f/3f;
                float ableHeight = 110f/3f;


                if(m_bankOilPer >= per)
                {
                    if(m_select)
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\active.png"));
                        Render.drawTexture(this.x, this.y, ableWidth, ableHeight, 0, 0, 1, 1, 5, 1);

                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\able\\"+this.m_BtnName+".png"));
                        Render.drawTexture(this.x+5, this.y + 12, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1);
                    }
                    else if(active.equals("_active"))
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\able.png"));
                        Render.drawTexture(this.x, this.y, ableWidth, ableHeight, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\able\\"+this.m_BtnName+".png"));
                        Render.drawTexture(this.x+5, this.y+ 12, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1, 0.75f, 0.75f, 0.75f);
                    }
                    else
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\able.png"));
                        Render.drawTexture(this.x, this.y, ableWidth, ableHeight, 0, 0, 1, 1, 5, 1);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\able\\" + this.m_BtnName + ".png"));
                        Render.drawTexture(this.x + 5, this.y + 12, btn_Str_Width, btn_Str_Height, 0, 0, 1, 1, 6, 1);
                    }
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\unlock.png"));
                    Render.drawTexture(this.x, this.y, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\btn\\unable\\"+this.m_BtnName+".png"));
                    Render.drawTexture(this.x+7, this.y+11, btn_Str_Width, btn_Str_Height, 0 ,0 , 1, 1, 6, 1);
                }


            }
            else
            {
                float squareStr_Width = 224f/3f;
                float squareStr_Height = 99f/3f;

                if(active.equals("_active"))
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_display\\startButton.png"));
                    Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 5, 1, 0.75f, 0.75f, 0.75f);
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_display\\startButton.png"));
                    Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 5, 1);
                }
            }

        }

    }



    Minecraft minecraft = Minecraft.getMinecraft();

    float m_bankOilAmount = 100f;

    float m_bankOilMaxAmount = 100f;

    int m_oilUseCount = 0;

    float m_selectOilAmount = 0;


    float m_aniOilPer = 0;

    float m_aniOilFrame = 0;



    boolean m_oilSelect = false;
    int m_oilSelectBtnNumber = 0;

    int playerOilCanAmount = 0;

    int oilListCount = 0;

    boolean infinity = false;

    BlockPos pos = BlockPos.ORIGIN;

    String item = "";


    OILBANK_STATUS m_oilStatus = OILBANK_STATUS.OPEN_VIEW;

    OILBANK_CATEGORY m_oilCategory = OILBANK_CATEGORY.NONE;


    public enum OILBANK_STATUS
    {
        OPEN_VIEW,
        OIL_AMOUNT,
        OIL_READY,
        OIL_START,
        OIL_STARTING,
        OIL_SUCCESS
    }

    public enum OILBANK_CATEGORY
    {

        NONE,
        CATEGORY_OIL_AMOUNT,
        CATEGORY_OIL_START,
        CATEGORY_OIL_SUCCESS
    }

    public GuiEleBank(float bankOilAmount, int useOilCount,  int playerOilCanAmount, int oilListCount, BlockPos pos, String item, boolean infinity)
    {
        m_bankOilAmount = bankOilAmount;
        m_oilUseCount = useOilCount;
        this.playerOilCanAmount = playerOilCanAmount;
        this.oilListCount = oilListCount;
        this.pos = pos;
        this.item = item;
        this.infinity = infinity;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

        if(this.m_oilStatus.equals(OILBANK_STATUS.OPEN_VIEW))
        {
            if(button.id == 0)
            {
                m_oilStatus = OILBANK_STATUS.OIL_AMOUNT;
                m_oilCategory = OILBANK_CATEGORY.CATEGORY_OIL_AMOUNT;
            }
        }
        else if (this.m_oilStatus.equals(OILBANK_STATUS.OIL_AMOUNT))
        {
            if(button.id >= 1 && button.id <= 10)
            {
                m_oilSelectBtnNumber = 0;
                for(int i = 1; i<11; i++)
                {
                    BtnEleBank btn = (BtnEleBank) buttonList.get(i);
                    btn.m_select = false;
                }

                BtnEleBank btn = (BtnEleBank) button;
                int per = Integer.parseInt(btn.m_BtnName.replace("%", ""));
                if(m_bankOilAmount >= per)
                {
                    btn.m_select = true;
                    m_oilSelect = true;
                    m_oilSelectBtnNumber = button.id;
                }
                else
                {
                    m_oilSelect = false;
                    m_oilSelectBtnNumber = 9999;
                }

            }
        }
        if(button instanceof BtnNextBack)
        {
            BtnNextBack btnNextBack = (BtnNextBack) button;
            if(btnNextBack.m_BtnName.equals("back"))
            {
                if(m_oilStatus.equals(OILBANK_STATUS.OIL_AMOUNT))
                {
                    m_oilStatus = OILBANK_STATUS.OPEN_VIEW;
                    m_oilCategory = OILBANK_CATEGORY.NONE;
                }
                else if(m_oilStatus.equals(OILBANK_STATUS.OIL_READY))
                {
                    m_oilStatus = OILBANK_STATUS.OIL_AMOUNT;
                    m_oilCategory = OILBANK_CATEGORY.CATEGORY_OIL_AMOUNT;
                }
            }
            else if(btnNextBack.m_BtnName.equals("next"))
            {
                if(m_oilStatus.equals(OILBANK_STATUS.OIL_AMOUNT))
                {
                    if(m_oilSelectBtnNumber != 0 && m_oilSelectBtnNumber != 9999)
                    {
                        m_oilStatus = OILBANK_STATUS.OIL_READY;
                        m_oilCategory = OILBANK_CATEGORY.CATEGORY_OIL_START;
                    }
                    
                }
                else if(m_oilStatus.equals(OILBANK_STATUS.OIL_READY))
                {
                    m_oilStatus = OILBANK_STATUS.OIL_START;
                    m_oilCategory = OILBANK_CATEGORY.CATEGORY_OIL_START;

                }

            }

        }
        if(button instanceof BtnExit)
        {
            //기름 지급하는 로직
            minecraft.player.closeScreen();
        }
    }

    @Override
    public void initGui()
    {
        //super.initGui();

        ScaledResolution scaled = new ScaledResolution(mc);

        int scaleWidth = (int)scaled.getScaledWidth();
        int scaleHeight = (int)scaled.getScaledHeight();


        buttonList.clear();

        int startButtn_Width = 224/3;
        int startButton_Height = 99/3;

        int btnID = 0;
        int count = 1;
        buttonList.add(new BtnEleBank(btnID++, scaleWidth/2 - startButtn_Width/2, scaleHeight/2 - startButton_Height/2 + 65 , "", "startButton"));
        for(int i = 1; i<3; i++)
        {
            for(int j = 1; j<6; j++)
            {

                buttonList.add(new BtnEleBank(btnID++, scaleWidth/2 - startButtn_Width/2 - 100 + (58 * (j-1)), scaleHeight/2 - startButton_Height/2 - 26 + (40 * (i-1)), count+"0", count+"0%", m_bankOilAmount));
                count++;
            }

        }

        float back_Width = 168f/3f;
        float back_Height = 62f/3f;

        buttonList.add(new BtnNextBack(btnID++, (int) (scaleWidth/2 - back_Width/2) - 20, (int) (scaleHeight/2 - back_Height/2 + 55), "", "back"));
        buttonList.add(new BtnNextBack(btnID++, (int) (scaleWidth/2 - back_Width/2) + 38, (int) (scaleHeight/2 - back_Height/2 + 55), "", "next"));


        buttonList.add(new BtnExit(btnID++, (int) (scaleWidth/2 - back_Width/2) + 10, (int) (scaleHeight/2 - back_Height/2 + 20), "", "exit"));


        for(GuiButton btn : buttonList)
        {
            btn.enabled = false;
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        //super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution scaled = new ScaledResolution(mc);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        float fpsCurrection = (120f / Minecraft.getDebugFPS()) * 0.9f;

        drawDefaultBackground();

        float backgroundWidth = 1258/3f;
        float backgroundHeight = 764/3f;


        for(GuiButton btn : buttonList)
        {
            btn.enabled = false;
        }


        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\background.png"));
        Render.drawTexture(scaleWidth/2 - backgroundWidth/2, scaleHeight/2 - backgroundHeight/2- 5, backgroundWidth, backgroundHeight, 0, 0, 1, 1, 1, 1);


        float oilAmountBackgroundWidth = 39f / 3f;
        float oilAmountBackgroundHeight = 422f / 3f;


        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_Display\\oilAmountBackground.png"));
        Render.drawTexture(scaleWidth/2 - oilAmountBackgroundWidth/2 + 180, scaleHeight/2 - oilAmountBackgroundHeight/2 + 10, oilAmountBackgroundWidth, oilAmountBackgroundHeight, 0, 0, 1, 1, 1, 1);

        float oilPer2 = m_bankOilAmount / m_bankOilMaxAmount;

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_Display\\oilAmountBar.png"));
        Render.drawYLinearTexture(scaleWidth/2 - oilAmountBackgroundWidth/2 + 180, scaleHeight/2 - oilAmountBackgroundHeight/2 + 10, oilAmountBackgroundWidth, oilAmountBackgroundHeight, oilPer2, 1, 1);

        float oilDivideBarWidth = 33f / 3f;
        float oilDivideBarHeight = 346f / 3f;

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_Display\\oilAmountBackground_divideBar.png"));
        Render.drawTexture(scaleWidth/2 - oilDivideBarWidth/2 + 180.25f, scaleHeight/2 - oilDivideBarHeight/2 + 10, oilDivideBarWidth, oilDivideBarHeight, 0, 0, 1, 1, 1, 1);

        float iconWidth = 31f / 3f;
        float iconHeight = 40f / 3f;

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_Display\\oil_icon.png"));
        Render.drawTexture(scaleWidth/2 - iconWidth/2 + 180f, scaleHeight/2 - iconHeight/2 - 71, iconWidth, iconHeight, 0, 0, 1, 1, 1, 1);


        if(m_oilStatus.equals(OILBANK_STATUS.OPEN_VIEW))
        {

            float startTitleWidth = 621f / 3f;
            float startTitleHeight = 206f/3f;


            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\start_Display\\startDisplayStr.png"));
            Render.drawTexture(scaleWidth/2 - startTitleWidth/2 , scaleHeight/2 - startTitleHeight/2 - 25, startTitleWidth, startTitleHeight, 0, 0, 1, 1, 1, 1);

            buttonList.get(0).enabled = true;
            buttonList.get(0).drawButton(mc, mouseX, mouseY, partialTicks);

        }
        if(!m_oilCategory.equals(OILBANK_CATEGORY.NONE))
        {

            float categoryTextWidth = 64f/3f;
            float categoryTextHeight = 18f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전량.png"));
            Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 , scaleHeight/2 - categoryTextHeight/2 - 58, categoryTextWidth, categoryTextHeight, 0, 0, 1, 1, 1, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전시작.png"));
            Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 , scaleHeight/2 - categoryTextHeight/2 - 3, categoryTextWidth, categoryTextHeight, 0, 0, 1, 1, 1, 1);


            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전완료.png"));
            Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 , scaleHeight/2 - categoryTextHeight/2 + 53, categoryTextWidth, categoryTextHeight, 0, 0, 1, 1, 1, 1);

            float arrowWidth = 4f/3f;
            float arrowHeight = 133f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\category_bar_background.png"));
            Render.drawTexture(scaleWidth/2 - arrowWidth/2 - 184, scaleHeight/2 - arrowHeight/2 + 25, arrowWidth, arrowHeight, 0, 0, 1, 1, 1, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\category_bar_background.png"));
            Render.drawTexture(scaleWidth/2 - arrowWidth/2 - 184, scaleHeight/2 - arrowHeight/2 - 30, arrowWidth, arrowHeight, 0, 0, 1, 1, 1, 1);

            float circle = 16f/3f;
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전전력 (x).png"));
            Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 - 58, circle, circle, 0, 0, 1, 1, 1, 1);
            
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전시작 (x).png"));
            Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 - 3, circle, circle, 0, 0, 1, 1, 1, 1);


            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전완료 (x).png"));
            Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 + 53, circle, circle, 0, 0, 1, 1, 1, 1);



            if(m_oilCategory.equals(OILBANK_CATEGORY.CATEGORY_OIL_AMOUNT))
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전전력 (O).png"));
                Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 - 58, circle, circle, 0, 0, 1, 1, 1, 1);

            }
            else if(m_oilCategory.equals(OILBANK_CATEGORY.CATEGORY_OIL_START))
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전시작 (O).png"));
                Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 - 3, circle, circle, 0, 0, 1, 1, 1, 1);


                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\category_bar.png"));
                Render.drawTexture(scaleWidth/2 - arrowWidth/2 - 184, scaleHeight/2 - arrowHeight/2 - 30, arrowWidth, arrowHeight, 0, 0, 1, 1, 1, 1);


            }
            else if(m_oilCategory.equals(OILBANK_CATEGORY.CATEGORY_OIL_SUCCESS))
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전완료 (O).png"));
                Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 + 53, circle, circle, 0, 0, 1, 1, 1, 1);

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\충전시작 (O).png"));
                Render.drawTexture(scaleWidth/2 - categoryTextWidth/2 - 167 - 9, scaleHeight/2 - categoryTextHeight/2 - 3, circle, circle, 0, 0, 1, 1, 1, 1);

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\category_bar.png"));
                Render.drawTexture(scaleWidth/2 - arrowWidth/2 - 184, scaleHeight/2 - arrowHeight/2 - 30, arrowWidth, arrowHeight, 0, 0, 1, 1, 1, 1);
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\category\\category_bar.png"));
                Render.drawTexture(scaleWidth/2 - arrowWidth/2 - 184, scaleHeight/2 - arrowHeight/2 + 25, arrowWidth, arrowHeight, 0, 0, 1, 1, 1, 1);
            }


        }

        if(m_oilStatus.equals(OILBANK_STATUS.OIL_AMOUNT))
        {

            float oilAmountTitleWidth = 156f/3f;
            float oilAmountTitleHeight = 70f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_amount\\oilbank_useAmount.png"));
            Render.drawTexture(scaleWidth/2 - oilAmountTitleWidth/2 + 124 , scaleHeight/2 - oilAmountTitleHeight/2 - 63, oilAmountTitleWidth, oilAmountTitleHeight, 0, 0, 1, 1, 1, 1);

            String oilCount = String.format("%d", m_oilUseCount);
            Render.drawStringScaleResizeByMiddleWidth(oilCount, scaleWidth/2 - oilAmountTitleWidth/2 + 141, scaleHeight/2 - oilAmountTitleHeight/2 - 55, 5, 1.2f, 1);

            int id = 1;
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;

            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(id).enabled = true;
            buttonList.get(id++).drawButton(mc, mouseX, mouseY, partialTicks);


        }

        else if(m_oilStatus.equals(OILBANK_STATUS.OIL_READY))
        {
            float oilAmountTitleWidth = 403f/3f;
            float oilAmountTitleHeight = 92f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_ready\\title.png"));
            Render.drawTexture(scaleWidth/2 - oilAmountTitleWidth/2 + 5, scaleHeight/2 - oilAmountTitleHeight/2 - 48, oilAmountTitleWidth, oilAmountTitleHeight, 0, 0, 1, 1, 1, 1);

            float textBackWidth = 536f/3f;
            float textBackHeight = 180f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_ready\\text_back.png"));
            Render.drawTexture(scaleWidth/2 - textBackWidth/2 + 5, scaleHeight/2 - textBackHeight/2 + 6, textBackWidth, textBackHeight, 0, 0, 1, 1, 1, 1);


            float textWidth = 325f/3f;
            float textHeight = 78f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_ready\\text.png"));
            Render.drawTexture(scaleWidth/2 - textWidth/2 + 5, scaleHeight/2 - textHeight/2 + 7, textWidth, textHeight, 0, 0, 1, 1, 1, 1);


            int strOil = Integer.parseInt(((BtnEleBank)buttonList.get(m_oilSelectBtnNumber)).m_BtnName.replace("%",""));

            String oilCount = String.format("%d", m_oilUseCount);
            String oilPer = String.format("%d", strOil);


            Render.drawStringScaleResizeByMiddleWidth(oilPer, scaleWidth/2 + 36, scaleHeight/2 - oilAmountTitleHeight/2 + 10, 5, 1.2f, 1);
            Render.drawStringScaleResizeByMiddleWidth(oilCount, scaleWidth/2 + 37, scaleHeight/2 - oilAmountTitleHeight/2 + 26, 5, 1.2f, 1);


            int id = 13;
            buttonList.get(11).enabled = true;
            buttonList.get(11).drawButton(mc, mouseX, mouseY, partialTicks);
            buttonList.get(12).enabled = true;
            buttonList.get(12).drawButton(mc, mouseX, mouseY, partialTicks);

        }
        else if(m_oilStatus.equals(OILBANK_STATUS.OIL_START))
        {

            float textWidth = 322f/3f;
            float textHeight = 92f/3f;

            //float AnimationFrameTime = 1f / 3f;

            m_aniOilFrame += partialTicks * 1f;

            if(m_aniOilFrame >= 100f)
            {
                m_aniOilFrame = 0;
                m_oilStatus = OILBANK_STATUS.OIL_SUCCESS;
                m_oilCategory = OILBANK_CATEGORY.CATEGORY_OIL_SUCCESS;

                minecraft.getSoundHandler().playSound(Sound.getSound(GwSoundHandler.ELECHARGE, SoundCategory.RECORDS, minecraft.gameSettings.getSoundLevel(SoundCategory.RECORDS)));



                int strOil = Integer.parseInt(((BtnEleBank)buttonList.get(m_oilSelectBtnNumber)).m_BtnName.replace("%",""));
                int addOilAmount = this.playerOilCanAmount + strOil <= 100 ? this.playerOilCanAmount + strOil : 100;

                if(minecraft.player.getHeldItemMainhand().getItem().equals(ModItems.phonew_ant))
                {
                    Variable.Instance().currentBattery = addOilAmount;
                    Packet.networkWrapper.sendToServer(new SPacketBatterySync(minecraft.player.getName(), (int) Variable.Instance().currentBattery));

                    if(! infinity)
                    {
                        m_bankOilAmount -= strOil;
                        Packet.networkWrapper.sendToServer(new SPacketChargeDataSync( this.oilListCount, (int) m_bankOilAmount));
                    }


                    Packet.networkWrapper.sendToServer(new SPacketItemRemove(new ItemStack(ModItems.ele_ticket, 1).copy(), 1));
                }
                else if (minecraft.player.getHeldItemMainhand().getItem().getRegistryName().getResourcePath().contains("corelamp"))
                {
                    ItemStack stack = minecraft.player.getHeldItemMainhand().copy();
                    NBTTagCompound display = (NBTTagCompound) stack.getTagCompound();
                    if (display != null)
                    {
                        if (display.getCompoundTag("display").getTag("Lore") != null)
                        {
                            String lore = display.getCompoundTag("display").getTag("Lore").copy().toString();
                            lore = lore.replaceAll("코어램프 배터리", "");
                            lore = lore.replaceAll(" ", "");
                            lore = lore.replaceAll(":", "");
                            lore = lore.replaceAll("%", "");
                            lore = lore.replace("[", "");
                            lore = lore.replaceAll("]", "");
                            lore = lore.replaceAll("\\\"", "");
                            lore = lore.replaceAll("/100", "");
                            lore = lore.replaceAll("§f", "");

                            int oilCanAmount = Integer.parseInt(lore);

                            if(oilCanAmount == this.playerOilCanAmount)
                            {
                                Packet.networkWrapper.sendToServer(new SPacketItemRemoveOfLore(stack.copy(), 1));
                            }
                        }
                    }

                    ItemStack coreLamp = GroundItemStack.createItemStackLore
                            (ItemBlock.getItemFromBlock(ModBlocks.CORELAMP) ,"코어램프", "코어램프 배터리: "+addOilAmount+"/100", false).copy();
                    Packet.networkWrapper.sendToServer(new SPacketItemAdd(coreLamp.copy()));

                    minecraft.player.sendChatMessage("/램프변수변경 " +addOilAmount);

                    m_bankOilAmount -= strOil;

                    Packet.networkWrapper.sendToServer(new SPacketItemRemove(new ItemStack(ModItems.ele_ticket, 1).copy(), 1));
                    Packet.networkWrapper.sendToServer(new SPacketChargeDataSync( this.oilListCount, (int) m_bankOilAmount));

                }



            }


            float linerPer = Render.easeOutCubic(m_aniOilFrame * 0.01f);


            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_start\\text.png"));
            Render.drawTexture(scaleWidth/2 - textWidth/2 + 14, scaleHeight/2 - textHeight/2 - 24, textWidth, textHeight, 0, 0, 1, 1, 1, 1);

            float barBackGroundWidth = 664f/3f;
            float barBackGroundHeight = 45f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_start\\bar_background.png"));
            Render.drawTexture(scaleWidth/2 - barBackGroundWidth/2 + 16, scaleHeight/2 - barBackGroundHeight/2 + 16, barBackGroundWidth, barBackGroundHeight, 0, 0, 1, 1, 1, 1);

            float bardWidth = 661f/3f;
            float barHeight = 39f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_start\\bar.png"));
            Render.drawXLinearTexture(scaleWidth/2 - bardWidth/2 + 16, scaleHeight/2 - barHeight/2 + 16, bardWidth, barHeight, linerPer*100f, 1, 1);


        }
        else if(m_oilStatus.equals(OILBANK_STATUS.OIL_SUCCESS))
        {
            float textWidth = 357f/3f;
            float textHeight = 39f/3f;
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\ele_bank\\oil_success\\text.png"));
            Render.drawTexture(scaleWidth/2 - textWidth/2 + 8, scaleHeight/2 - textHeight/2 - 29, textWidth, textHeight, 0, 0, 1, 1, 1, 1);

            buttonList.get(13).enabled = true;
            buttonList.get(13).drawButton(mc,mouseX, mouseY, partialTicks);
        }


        //minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\oil_bank\\oil_ready\\success.png"));
        //Render.drawTexture(0 , 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 6, 0.25f);



    }


    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Packet.networkWrapper.sendToServer(new SPacketPosActive(pos, false));
    }

}
