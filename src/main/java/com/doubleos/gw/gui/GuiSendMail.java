package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.gui.btn.BtnUrgent;
import com.doubleos.gw.inventory.SendMailContainer;
import com.doubleos.gw.packet.Packet;
import com.doubleos.gw.packet.SPacketMailDataSave;
import com.doubleos.gw.packet.SPacketMailSendSaveData;
import com.doubleos.gw.packet.SPacketSendMailDataSync;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GuiSendMail extends GuiContainer
{


    GuiMultiLineTextField m_textFieldMulti;
    GuiMultiLineTextField m_textSenderMulti;


    GuiTextField m_textFieldTitle;

    SENDMAILVIEW m_sendMailGuiView;


    SendMailContainer m_container;

    public enum SENDMAILVIEW
    {
        SEND_GUI,
        SEND_PLAYER_GUI
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if(m_sendMailGuiView.equals(SENDMAILVIEW.SEND_GUI))
        {
            if (keyCode == Keyboard.KEY_ESCAPE)
            {
                this.mc.player.closeScreen();
                return;
            }
            if(m_textFieldTitle.isFocused())
                m_textFieldTitle.textboxKeyTyped(typedChar, keyCode);
            else if(m_textFieldMulti.isFocused())
                m_textFieldMulti.textboxKeyTyped(typedChar, keyCode);
        }
        else
        {
            if (keyCode == Keyboard.KEY_ESCAPE)
            {
                m_sendMailGuiView = SENDMAILVIEW.SEND_GUI;
            }
            if(m_textSenderMulti.isFocused())
            {
                m_textSenderMulti.textboxKeyTyped(typedChar, keyCode);
            }
        }

    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        Variable variable = Variable.Instance();
        if(m_sendMailGuiView.equals(SENDMAILVIEW.SEND_GUI))
        {
            if(button.id == 0)
            {
                m_sendMailGuiView = SENDMAILVIEW.SEND_PLAYER_GUI;
            }
        }
        else
        {
            if(button.id == 0)
            {
                //보내는 로직
                String text = m_textSenderMulti.getText();
                String ydCrew = "d7297\nSeoneng\nDaju_\nRuTaeY\nKonG7\nHuchu95\nsamsik23\nNoonkkob";
                text = text.replaceAll("양띵크루", ydCrew);
                m_sendMailGuiView = SENDMAILVIEW.SEND_GUI;

                Date today = new Date();

                SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

                String[] names = text.split("\n");

                System.out.println(m_container.internal.getStackInSlot(0).copy().getDisplayName());
                System.out.println(m_container.internal.getStackInSlot(1).copy());
                System.out.println(m_container.internal.getStackInSlot(2).copy());
                System.out.println(m_container.internal.getStackInSlot(3).copy());
                System.out.println(m_container.internal.getStackInSlot(4).copy());


                int size = variable.m_mailDataList.size();

                for(String name : names)
                {
                    Packet.networkWrapper.sendToServer(new SPacketMailDataSave(size++, m_textFieldTitle.getText(), m_textFieldMulti.getText(), date.format(today),
                            false, false, name, m_container.internal.getStackInSlot(0).copy(), m_container.internal.getStackInSlot(1).copy(), m_container.internal.getStackInSlot(2).copy()
                    , m_container.internal.getStackInSlot(3).copy(), m_container.internal.getStackInSlot(4).copy()));
                }


                Packet.networkWrapper.sendToServer(new SPacketMailSendSaveData());




            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX,mouseY,mouseButton);
        if(m_sendMailGuiView.equals(SENDMAILVIEW.SEND_GUI))
        {
            if(m_textFieldTitle.mouseClicked(mouseX, mouseY, mouseButton))
            {
                m_textFieldTitle.setFocused(true);
                m_textFieldMulti.setFocused(false);

            }
            else if(m_textFieldMulti.mouseClicked(mouseX, mouseY, mouseButton))
            {
                m_textFieldMulti.setFocused(true);
                m_textFieldTitle.setFocused(false);
            }

        }
        else
        {
            if(m_textSenderMulti.mouseClicked(mouseX, mouseY, mouseButton))
            {
                m_textSenderMulti.setFocused(true);
                m_textFieldMulti.setFocused(false);
                m_textFieldTitle.setFocused(false);
            }

        }






    }

    @Override
    public void initGui() {
        super.initGui();

        Variable variable = Variable.Instance();
        variable.m_mailDataList.clear();
        Packet.networkWrapper.sendToServer(new SPacketSendMailDataSync());

        m_textFieldTitle = new GuiTextField(0, fontRenderer, width/2-80, height/2 - 9, 160 ,9);
        m_textFieldTitle.setTextColor(Render.RGB_TO_DECIMAL(40, 40, 40));


        Keyboard.enableRepeatEvents(true);

        m_textFieldTitle.setMaxStringLength(30);
        m_textFieldTitle.setEnableBackgroundDrawing(false);
        m_textFieldTitle.setVisible(true);
        m_textFieldTitle.setFocused(true);

        m_textFieldMulti = new GuiMultiLineTextField(2, fontRenderer, width/2-80, height/2 + 20, 160 ,80);
        m_textFieldMulti.setEnableBackgroundDrawing(false);
        m_textFieldMulti.setTextColor(Render.RGB_TO_DECIMAL(40, 40, 40));

        m_textSenderMulti = new GuiMultiLineTextField(3, fontRenderer, width/2-80, height/2 + 20, 160, 80);
        m_textSenderMulti.setEnableBackgroundDrawing(true);


        int btnId = 0;
        buttonList.add(new GuiButtonExt(btnId++, width/2 - 60/2, height/2 + 160, 60, 20, "송 신"));


    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {



        drawDefaultBackground();
        ScaledResolution scaled = new ScaledResolution(mc);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();


        float urgentBox_Width = 489f/3f;
        float urgentBox_Height = 461f/3f;

        float antenna_Width = 670f/3f;
        float antenna_Height = 120f/3f;

        float notSignal_Width = 616f/3f;
        float notSignal_Height = 877f/3f;

        float gall_Str_Width = 145f / 3f;
        float gall_Str_Height = 37f / 3f;

        Render.drawStringScaleResizeByLeftWidth("메일 보내기 Gui", width/2+20,  height/2-30, 5, 1, Render.RGB_TO_DECIMAL(0, 0, 0));

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\str\\긴급 문자.png"));
        Render.drawTexture (scaleWidth/2 - gall_Str_Width/2 - 55, scaleHeight/2 - gall_Str_Height/2 - 41, gall_Str_Width, gall_Str_Height, 0, 0 ,1, 1, 1, 1);



        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\안테나.png"));
        Render.drawTexture(scaleWidth/2 - antenna_Width/2 + 34, scaleHeight/2 - antenna_Height/2 - 125, antenna_Width, antenna_Height, 0, 0, 1, 1, 0, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\긴급문자핸드폰.png"));
        Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 0, 1);


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\긴급문자박스.png"));
        Render.drawTexture (scaleWidth/2 - urgentBox_Width/2, scaleHeight/2 - urgentBox_Height/2 + 80.5f, urgentBox_Width, urgentBox_Height, 0, 0 ,1, 1, 0, 1);



        buttonList.get(0).drawButton(mc, mouseX,mouseY,partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);



        if(m_sendMailGuiView.equals(SENDMAILVIEW.SEND_GUI))
        {
            m_textFieldMulti.drawTextBox();
            m_textFieldTitle.updateCursorCounter();


            m_textFieldTitle.drawTextBox();
            m_textFieldTitle.updateCursorCounter();

            if(m_textFieldTitle.getText().length() == 0)
            {
                Render.drawStringScaleResizeByLeftWidth("제목을 입력해주세요", width/2-77,  height/2-9, 5, 1, Render.RGB_TO_DECIMAL(0, 0, 0));
            }
        }
        else
        {

            m_textSenderMulti.drawTextBox();
            m_textSenderMulti.updateCursorCounter();
            if(m_textSenderMulti.getText().length() == 0)
            {
                Render.drawStringScaleResizeByLeftWidth("보낼 플레이어를 입력해주세요. [엔터로 구분]", width/2-77,  height/2+20, 5, 1, Render.RGB_TO_DECIMAL(255, 255, 255));
                Render.drawStringScaleResizeByLeftWidth("간편 기능 [양띵크루] < 입력시 자동적용.", width/2-77,  height/2+40, 5, 1, Render.RGB_TO_DECIMAL(255, 255, 255));

            }

        }





    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

    }

    public GuiSendMail(EntityPlayer player, InventoryPlayer inventoryPlayer)
    {
        super(new SendMailContainer(inventoryPlayer, player));

        m_container = (SendMailContainer) this.inventorySlots;
        m_sendMailGuiView = SENDMAILVIEW.SEND_GUI;


    }
}
