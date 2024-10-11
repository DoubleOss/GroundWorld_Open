package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.gui.btn.*;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.packet.*;
import com.doubleos.gw.util.*;
import com.doubleos.gw.variable.ShopData;
import com.doubleos.gw.variable.ShopItemData;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiPhone extends GuiScreen
{
    Variable variable = Variable.Instance();
    boolean m_antenna = false;

    boolean m_callStrActive = false;





    GuiButton m_sliderBtn;

    int m_MouseY = 0;

    boolean m_scrollActive = false;

    int m_contentSliderYPos = -15;
    float m_lastYPos = 0f;
    float m_minYPos = 0f;

    float m_lastYPosMail = 0f;

    boolean m_clueOpenActive = false;

    float m_scaleAni = 1f;

    boolean m_scaleAnimation = false;

    GuiButton m_UngentLastBtn;


    GallData m_selectGallBtn;

    BtnUrgent m_selectUrgentBtn;
    private boolean m_openTextBox = false;

    boolean m_readUrgenttext = false;



    public class BtnPhoneShop extends GuiButton
    {


        public String m_BtnName = "";
        Minecraft minecraft = Minecraft.getMinecraft();

        Variable variable = Variable.Instance();

        int m_btn_Width = 600 / 2;
        int m_btn_Height = 51 / 2;

        float m_btn_ImageWidth = 600 / 2f;
        float m_btn_ImageHeight = 51 / 2f;

        public ShopItemData m_data;

        public boolean m_giveActive = false;


        int textureId = 0;

        int shopId = 0;
        public BtnPhoneShop(int buttonId, int x, int y, String buttonText, String memberName)
        {
            this(buttonId,  x,  y,  buttonText,  memberName, 0);
        }
        public BtnPhoneShop(int buttonId, int x, int y, String buttonText, String memberName,  int shopItemId)
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
            else if (memberName.equals("아이템"))
            {
                float back_Width = 164f/3f;
                float back_Height = 196f/3f;

                m_btn_Width = (int) back_Width;
                m_btn_Height = (int) back_Height;
                shopId = shopItemId;
                m_data =  variable.m_hashShopData.get("스마트폰상점").itemDataList.get(shopId);
            }


            this.width = m_btn_Width;
            this.height = m_btn_Height;
        }

        void drawText(ItemStack stack,  int x, int y, int z)
        {
            float loreBackWidth = 273f/3f;
            float loreBackHegit = 200f/3f;

            Variable variable = Variable.Instance();

            List<String> m_text = new ArrayList<>();
            //m_text.add(stack.getDisplayName());

            NBTTagCompound tagCompound = stack.getTagCompound();

            if (tagCompound != null && tagCompound.hasKey("display"))
            {
                NBTTagCompound displayTag = tagCompound.getCompoundTag("display");

                if (displayTag.hasKey("Lore")) {
                    NBTTagList loreList = displayTag.getTagList("Lore", 8); // 8은 문자열 타입

                    // 모든 Lore 값을 출력
                    for (int i = 0; i < loreList.tagCount(); i++) {
                        NBTTagString loreTag = (NBTTagString) loreList.get(i);
                        String loreString = loreTag.getString();
                        m_text.add(loreString);
                    }
                }
            }

            //
            ScaledResolution scaled = new ScaledResolution(mc);

            float scaleWidth = (float) scaled.getScaledWidth_double();
            float scaleHeight = (float) scaled.getScaledHeight_double();

            int maxWidth = 0;
            for(String text : m_text)
            {
                int textWidth = fontRenderer.getStringWidth(text);
                if(textWidth >= maxWidth)
                    maxWidth = textWidth;
            }

            float widthCurr = maxWidth <= 73 ? 1 : maxWidth/73f;

            float loreLeftWidth = 28f/3f * 0.8f;
            float loreLeftHeight = 200f/3 * 0.8f;

            float loreRightWidth = 245f/3f * 0.8f;
            float loreRightHeight = 200f/3f * 0.8f;

            if(m_text.size() != 0)
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\lore_left.png"));
                Render.drawTexture(scaleWidth/2 - loreLeftWidth/2f + 110f, scaleHeight/2 - loreLeftHeight/2 + 25f, loreLeftWidth, loreLeftHeight, 0, 0, 1, 1, z, 1);

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\lore_right.png"));
                Render.drawTexture(scaleWidth/2 - loreLeftWidth/2f + 110f+loreLeftWidth, scaleHeight/2 - loreLeftHeight/2 + 25f, loreRightWidth * widthCurr, loreRightHeight, 0, 0, 1, 1, z, 1);


//        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\lore.png"));
//        Render.drawTexture(scaleWidth/2 - loreBackWidth/2f + 192f, scaleHeight/2 - loreBackHegit/2 - 21, loreBackWidth, loreBackHegit, 0, 0, 1, 1, 1, 1);


                Render.drawTextList(m_text, (int) (scaleWidth/2 - loreLeftWidth/2f + 110f), (int) (scaleHeight/2 - loreLeftHeight/2 + 60), fontRenderer, z+5, 0.8f);
                //Render.drawHoveringText(m_text, x, y, fontRenderer, z);

            }


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

            ScaledResolution scaled = new ScaledResolution(mc);
            float scaleWidth = (float) scaled.getScaledWidth_double();
            float scaleHeight = (float) scaled.getScaledHeight_double();

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
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\btn\\뒤로가기.png"));
                        Render.drawTexture(this.x/1.05f-0.25f, this.y/1.05f-0.25f, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
                    }
                    GlStateManager.popMatrix();

                }
                else
                {
                    float back_Width = 66f/3f;
                    float back_Height = 16f/3f;
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\btn\\뒤로가기.png"));
                    Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
                }

            }
            else if (m_BtnName.equals("슬라이더"))
            {
                float back_Width = 6/3f;
                float back_Height = 192f/3f;

                if(active.equals("_active"))
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\scroll_bar.png"));
                    Render.drawTexture(this.x+1, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1, 0.5f, 0.5f, 0.5f);
                }
                else
                {

                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\scroll_bar.png"));
                    Render.drawTexture(this.x+1, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
                }

            }
            else //아이템 리스트
            {
                float back_Width = 164f/3f;
                float back_Height = 196f/3f;
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\btn\\btnBack.png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);

                ItemStack removeItem = m_data.requestBuyItem;
                int removeAmount = m_data.requestItemBuyAmount;

                float currentPer = (float) m_data.dayBuyCurrentLimitCount / (float)m_data.dayBuyMaxLimitCount;

                float amountBackWidth = 68f/3f;
                float amountBackHeight = 25f/3f;
                String big = "2";


                GlStateManager.pushMatrix();
                {
                    float scaleValue = 1.5f;
                    GlStateManager.scale(scaleValue, scaleValue, 1);
                    GlStateManager.translate(((this.x + back_Width/2f - 12f) / scaleValue), (this.y+18) / scaleValue, 2);
                    RenderHelper.enableGUIStandardItemLighting();
                    Render.renderHotbarItem(0, 0, partialTicks, mc.player, m_data.shopItem);
                }
                GlStateManager.popMatrix();
                if(m_data.dayBuyMaxLimitCount >= 100)
                {
                    big = "3";
                }
                String amountText = String.format("§l%d / %d",m_data.dayBuyCurrentLimitCount, m_data.dayBuyMaxLimitCount);

                if(currentPer >= 0.5f) // 흰색
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\btn\\"+big+"수량_back.png"));
                    Render.drawTexture(this.x + this.width/2f - amountBackWidth/2f, this.y + 8, amountBackWidth, amountBackHeight, 0, 0, 1, 1, 5, 1);
                    //Render.drawStringScaleResizeByLeftWidth(amountText, this.x + 17 + amountBackWidth/2f - fontWidth*0.45f /2f, this.y + 8, 2, 0.45f, 1);
                    Render.drawStringScaleResizeByMiddleWidth(amountText, this.x + this.width/2f, this.y + 8.5f + amountBackHeight/2f - 2f, 6, 0.4f, 1);

                }
                else if(currentPer >= 0.01f) // 빨간색
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\btn\\"+big+"수량_soild.png"));
                    Render.drawTexture(this.x + this.width/2f - amountBackWidth/2f, this.y + 8, amountBackWidth, amountBackHeight, 0, 0, 1, 1, 5, 1);
                    Render.drawStringScaleResizeByMiddleWidth(amountText, this.x + this.width/2f, this.y + 8.5f + amountBackHeight/2f - 2f, 6, 0.4f, 1);

                }
                else // 회색
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\btn\\"+big+"수량_sold.png"));
                    Render.drawTexture(this.x + this.width/2f - amountBackWidth/2f, this.y + 8, amountBackWidth, amountBackHeight, 0, 0, 1, 1, 5, 1);
                    Render.drawStringScaleResizeByMiddleWidth(amountText, this.x + this.width/2f, this.y + 8.5f + amountBackHeight/2f - 2f, 6, 0.4f, -1);

                }

                String itemName = String.format("[%s§0]", m_data.shopItem.getDisplayName().replace("§f", "§0"));
                Render.drawStringScaleResizeByMiddleWidth(itemName, this.x + this.width/2f, this.y + this.height - 20, 5, 0.5f, 1);


                if(m_data.dayBuyCurrentLimitCount == 0)
                {

                    Render.drawStringScaleResizeByMiddleWidth("§l품절", this.x + this.width/2f, this.y + this.height - 12, 5, 0.8f, 1);
                }
                else
                {
                    GlStateManager.pushMatrix();
                    {
                        float scaleValue = 0.50f;
                        GlStateManager.scale(scaleValue, scaleValue, 1f);
                        GlStateManager.translate(((this.x + this.width/2f - 10f) / scaleValue), (this.y + this.height - 14) / scaleValue, 5);
                        RenderHelper.enableGUIStandardItemLighting();
                        Render.renderHotbarItem(0, 0, partialTicks, mc.player, removeItem);
                    }
                    GlStateManager.popMatrix();

                    float removeAmountWidth = fontRenderer.getStringWidth(String.format("§l%02d",removeAmount)) * 0.6f;
                    Render.drawStringScaleResizeByMiddleWidth(String.format("§l%02d",removeAmount), (this.x + this.width/2f - 10f) + removeAmountWidth/2f + 10, this.y + this.height - 12, 5, 0.6f, 1);
                }

                float loreBackWidth = 273f/3f;
                float loreBackHegit = 200f/3f;
                if(active.equals("_active"))
                {

                    drawText(m_data.shopItem, (int) (scaleWidth/2 - loreBackWidth/2f + 192f), (int) (scaleHeight/2 - loreBackHegit/2 - 21), 10);

                }

            }
            
        }
        
    }


    public GuiPhone() {
        super();

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);

        ScaledResolution scaled = new ScaledResolution(mc);


        int scaleWidth = scaled.getScaledWidth();
        int scaleHeight = scaled.getScaledHeight();

        if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT) || variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.GALL))
        {
            if(m_scrollActive)
                m_scrollActive = false;
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.NONE;
    }

    public GuiPhone(boolean antenna)
    {
        super();

        m_antenna = antenna;
        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        Minecraft  minecraft = Minecraft.getMinecraft();
        if(keyCode == 1)
        {
            if(m_clueOpenActive)
            {
                m_clueOpenActive = false;
            }
            else
            {
                minecraft.player.closeScreen();
            }

        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        //super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0)
        {
            for (int i = 0; i < this.buttonList.size(); ++i)
            {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                        break;
                    guibutton = event.getButton();
                    this.selectedButton = guibutton;
                    mc.getSoundHandler().playSound(Sound.getSound(GwSoundHandler.PHONETOUCH, SoundCategory.MASTER, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER)));
                    //guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.buttonList));
                }
            }
        }
        ScaledResolution scaled = new ScaledResolution(mc);


        int scaleWidth = scaled.getScaledWidth();
        int scaleHeight = scaled.getScaledHeight();
        if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER))
        {
            float norch_Calling_Width = 360f / 3f;
            float norch_Calling_Height = 56f / 3f;

            m_callStrActive = (mouseX >= scaleWidth/2f - norch_Calling_Width/2f && mouseY >= scaleHeight/2f - norch_Calling_Height/2f - 90.5f && mouseX < scaleWidth/2f + norch_Calling_Width/2f && mouseY < scaleHeight/2f + norch_Calling_Height/2f - 90.5f) ? true : false;

            variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CALL;
        }
        else if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER) || variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING))
        {
            //region 통화중 노치 부분 클릭 로직
            float norch_Calling_Width = 360f / 3f;
            float norch_Calling_Height = 56f / 3f;

            m_callStrActive = (mouseX >= scaleWidth/2 - norch_Calling_Width/2 && mouseY >= scaleHeight/2 - norch_Calling_Height/2 - 90.5f && mouseX < scaleWidth/2 + norch_Calling_Width/2 && mouseY < scaleHeight/2 + norch_Calling_Height/2 - 90.5f) ? true : false;

            if(m_callStrActive)
            {
                variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CALLING;
            }

        }

        else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT))
        {

            for(GuiButton button : buttonList)
            {
                if(button instanceof BtnContent)
                {
                    BtnContent btn = (BtnContent) button;
                    if(btn.equals("슬라이더"))
                    {
                        m_sliderBtn = btn;

                        boolean active = mouseX >= btn.x && mouseY >= btn.y && mouseX < btn.x + btn.width && mouseY < btn.y + btn.height;
                        if(active)
                            m_scrollActive = true;
                    }

                }

            }

        }

    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT))
        {
           // System.out.println("클릭");
        }
    }



    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0)
        {
            float yPosPer = 0f;
            if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT))
            {
                if (i > 1)
                {
                    if (m_contentSliderYPos >= -15)
                        return;
                    i = 1;
                }
                else if (i < -1)
                {
                    if (m_contentSliderYPos < -86)
                        return;
                    i = -1;
                }
                m_contentSliderYPos +=  i * 5;
                if(m_contentSliderYPos <= -86)
                {
                    m_contentSliderYPos = -86;
                }
                yPosPer = (((-1*m_contentSliderYPos)) / (120f)); // 8개 일 때

            }
            else if (variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.GALL))
            {
                float size = variable.m_gallDataList.size();
                int yPos = (int) ((int) (-1*m_lastYPos + 165) + (73f/3f + 32) * 2.5f);
                if (i > 1)
                {
                    if (m_contentSliderYPos >= -15)
                    {
                        m_contentSliderYPos = -15;
                        return;
                    }

                    i = 1;
                }
                else if (i < -1)
                {
                    if (m_contentSliderYPos < yPos)
                        return;
                    i = -1;
                }
                m_contentSliderYPos += (int) (i * 5 * (1 * (size / 8f) ));
                if(m_contentSliderYPos <= yPos)
                {
                    m_contentSliderYPos = yPos;
                }

                yPosPer = -1*m_contentSliderYPos / ((int) (1*m_lastYPos + 165) + (73f/3f + 32) * 2.5f);

            }
            else if (variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT))
            {
                ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

                int scaleHeight = scaled.getScaledHeight();

                float size = m_MouseY;
                int yPos = (int) ((int) (-1*m_lastYPosMail + 165) + (73f/3f + 32) * 2.5f);
                if (i > 1)
                {
                    if (m_contentSliderYPos >= -15)
                    {
                        m_contentSliderYPos = -15;
                        return;
                    }

                    i = 1;
                }
                else if (i < -1)
                {
                    if (m_contentSliderYPos < yPos)
                        return;
                    i = -1;
                }
                m_contentSliderYPos += (int) (i * 5 * (1 * (size / 8f) ));
                if(m_contentSliderYPos <= yPos)
                {
                    m_contentSliderYPos = yPos;
                }

                yPosPer = -1*m_contentSliderYPos / ((int) (1*m_lastYPosMail + 165) + (73f/3f + 32) * 2.5f);

                int afterY = scaleHeight - 30;

                int check = 0;
                if(m_UngentLastBtn != null)
                {
                    check = (m_UngentLastBtn.y >= afterY) ? 1 : 0;
                }

            }
            //-465 까지 내려가야함


            for(GuiButton button : buttonList)
            {
                if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT))
                {
                    if(button instanceof BtnContent)
                    {
                        if(((BtnContent) button).m_BtnName.equals("슬라이더"))
                        {
                            button.y = (int) (121 * yPosPer + 168f);
                        }

                    }
                }
                else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.GALL))
                {
                    if(button instanceof BtnGall)
                    {
                        if (((BtnGall) button).m_BtnName.equals("슬라이더"))
                        {

                            button.y = (int) ((121 * (yPosPer * 4.5f) + 168f));
                        }
                    }

                }
                else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT))
                {
                    if(button instanceof BtnUrgent)
                    {
                        if (((BtnUrgent) button).m_BtnName.equals("슬라이더"))
                        {
                            button.y = (int) ((int) ((121 * (yPosPer * 2f) + 164f)));
                            //button.y = (int) ((int) ((121 * (yPosPer * 2f) + 164f)));

                        }
                    }

                }
                else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.SHOP))
                {

                    ShopData itemData = variable.m_hashShopData.get("스마트폰상점");
                    if (i != 0)
                    {
                        if (i < 1)// 휠 내렸을 때
                        {
                            boolean downCheck = false;
                            for (GuiButton button2 : buttonList)
                            {
                                if (button2 instanceof BtnPhoneShop && itemData.itemDataList.size() > 6)
                                {
                                    BtnPhoneShop btn2 = (BtnPhoneShop) button2;
                                    if(btn2.m_BtnName.equals("슬라이더"))
                                    {
                                        if (btn2.y + 5 <= this.phoneShopUiMaxScrollYPos - 192f/3f)
                                        {
                                            btn2.y += 5;
                                        }
                                        else
                                        {
                                            downCheck = true;
                                        }
                                    }

                                }
                            }
                            if(!downCheck)
                            {
                                for (GuiButton button3 : buttonList) {
                                    if (button3 instanceof BtnPhoneShop)
                                    {
                                        BtnPhoneShop btn = (BtnPhoneShop) button3;
                                        if(btn.m_BtnName.equals("아이템"))
                                        {
                                            int[] yHeight = new int[]{0, 1, 1, 1};
                                            int count = 1;
                                            if (itemData.itemDataList.size() > 3 && itemData.itemDataList.size() <= 6)
                                                count = 3;
                                            else if (itemData.itemDataList.size() > 6) {
                                                count = 4;
                                            }
                                            btn.y -= yHeight[count - 1];
                                            //2줄 5 4줄 10
                                        }

                                    }

                                }
                            }


                        }
                        else
                        {
                            boolean downCheck = false;
                            for (GuiButton button2 : buttonList)
                            {
                                if (button2 instanceof BtnPhoneShop && itemData.itemDataList.size() > 6)
                                {
                                    BtnPhoneShop btn2 = (BtnPhoneShop) button2;
                                    if(btn2.m_BtnName.equals("슬라이더"))
                                    {
                                        if (btn2.y - 5 >= this.phoneShopUiMinScrollYPos)
                                        {
                                            btn2.y -= 5;
                                        }
                                        else
                                        {
                                            downCheck = true;
                                        }
                                    }

                                }
                            }
                            if(!downCheck)
                            {
                                for (GuiButton button3 : buttonList) {
                                    if (button3 instanceof BtnPhoneShop)
                                    {
                                        BtnPhoneShop btn = (BtnPhoneShop) button3;
                                        if(btn.m_BtnName.equals("아이템"))
                                        {
                                            int[] yHeight = new int[]{0, 1, 1, 1};
                                            int count = 1;
                                            if (itemData.itemDataList.size() > 3 && itemData.itemDataList.size() <= 6)
                                                count = 3;
                                            else if (itemData.itemDataList.size() > 6) {
                                                count = 4;
                                            }
                                            btn.y += yHeight[count - 1];
                                            //2줄 5 4줄 10
                                        }

                                    }

                                }
                            }

                        }
                    }
                }

            }


        }
    }

    float phoneMaxYPosPer = 0f;
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

        Minecraft.getMinecraft().addScheduledTask(()->
        {
            ScaledResolution scale = new ScaledResolution(mc);
            if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.MENU))
            {
                if (button instanceof BtnMenu )
                {
                    BtnMenu btn = (BtnMenu) button;
                    m_contentSliderYPos = -15;
                    for(int i = 0; i<buttonList.size(); i++)
                    {
                        if(buttonList.get(i) instanceof  BtnUrgent)
                        {
                            if(((BtnUrgent) buttonList.get(i)).m_BtnName.equals("슬라이더"))
                                buttonList.get(i).y = scale.getScaledHeight()/2 - 13;
                        }
                        else if (buttonList.get(i) instanceof BtnGall)
                        {
                            if(((BtnGall) buttonList.get(i)).m_BtnName.equals("슬라이더"))
                                buttonList.get(i).y = scale.getScaledHeight()/2 - 13;
                        }
                        else if(buttonList.get(i) instanceof BtnContent)
                        {
                            if(((BtnContent) buttonList.get(i)).m_BtnName.equals("슬라이더"))
                                buttonList.get(i).y = scale.getScaledHeight()/2 - 13;
                        }
                    }
                    if(btn.m_BtnName.equals("통화"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CONTENT;
                    }
                    else if(btn.m_BtnName.equals("긴급 문자"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT;
                    }
                    else if(btn.m_BtnName.equals("갤러리"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.GALL;
                    }
                    else if(btn.m_BtnName.equals("상점"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.SHOP;
                    }
                    else if(btn.m_BtnName.equals("부족"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.TRIBE;
                    }

                }
            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CALLING))
            {
                if (button instanceof BtnCalling )
                {
                    BtnCalling btn = (BtnCalling) button;
                    if(btn.m_BtnName.equals("통화종료"))
                    {
                        String roomName = Variable.Instance().getMemberIdToKoreaNickName(mc.player.getName());
                        mc.player.sendChatMessage("/통화방이동 " + mc.player.getName() + " " + Variable.getMemberNameToDiscordNumber(mc.player.getName()));
                        //Packet.networkWrapper.sendToServer(new SPacketDiscordCallRoomMove(roomName, mc.player.getName()));
                        String roomName2 = Variable.Instance().getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
                        Packet.networkWrapper.sendToServer(new SPacketDiscordCallRoomMove(roomName2, variable.m_callingPlayer.name()));
                        mc.player.sendChatMessage("/통화방이동 " + variable.m_callingPlayer.name() + " " + Variable.getMemberNameToDiscordNumber(variable.m_callingPlayer.name()));

                        Packet.networkWrapper.sendToServer(new SPacketWatchStatus(Variable.WATCH_STATUS.IDLE, variable.m_callingPlayer.name()));
                        variable.m_phoneStatus = Variable.WATCH_STATUS.IDLE;
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CONTENT;
                        variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.NONE;
                        GroundWorld.proxy.stopSound(GwSoundHandler.PHONE_BELL);
                        GroundWorld.proxy.playSound(GwSoundHandler.CALL_DISCONNET);


                    }
                    else if(btn.m_BtnName.equals("뒤로가기"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
                    }
                }

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CALL))
            {
                if (button instanceof BtnCall )
                {
                    BtnCall btn = (BtnCall) button;
                    if(btn.m_BtnName.equals("승낙아이콘")) //통화 승낙
                    {

                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CALLING;
                        variable.m_phoneStatus = Variable.WATCH_STATUS.CONNETING;
                        Packet.networkWrapper.sendToServer(new SPacketWatchStatus(Variable.WATCH_STATUS.CONNETING, variable.m_callingPlayer.name()));
                        String roomName = Variable.Instance().getMemberIdToKoreaNickName(mc.player.getName());
                        mc.player.sendChatMessage("/통화방이동 " + mc.player.getName() + " " + Variable.getMemberNameToDiscordNumber(mc.player.getName()));
                        GroundWorld.proxy.stopSound(GwSoundHandler.PHONE_BELL);
                        //Packet.networkWrapper.sendToServer(new SPacketDiscordCallRoomMove(roomName, variable.m_callingPlayer.name()));

                    }
                    else if (btn.m_BtnName.equals("거절아이콘")) // 통화 거절
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
                        variable.m_phoneStatus = Variable.WATCH_STATUS.IDLE;
                        variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.NONE;
                        GroundWorld.proxy.stopSound(GwSoundHandler.PHONE_BELL);
                        GroundWorld.proxy.playSound(GwSoundHandler.CALL_DISCONNET);
                        Packet.networkWrapper.sendToServer(new SPacketWatchStatus(Variable.WATCH_STATUS.IDLE, variable.m_callingPlayer.name()));


                    }
                }

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT))
            {
                if(button instanceof BtnContent)
                {
                    BtnContent btn = (BtnContent) button;
                    if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더"))
                    {
                        if(btn.m_callActive)
                        {
                            //통화 거는 로직
                            mc.player.sendChatMessage("/통화방이동 " + mc.player.getName() + " " + Variable.getMemberNameToDiscordNumber(mc.player.getName()));
                            GroundWorld.proxy.playSound(GwSoundHandler.PHONE_BELL);
                            variable.m_phoneStatus = Variable.WATCH_STATUS.CALL_SENDER;
                            variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.valueOf(btn.m_BtnName);
                            variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CALLING;
                            Packet.networkWrapper.sendToServer(new SPacketDisCordCallRecive(Variable.WATCH_STATUS.CALL_RECIVER, variable.m_callingPlayer.name(), mc.player.getName()));
                        }
                    }
                    if (btn.m_BtnName.equals("뒤로가기"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
                    }
                    else if (btn.m_BtnName.equals("슬라이더"))
                    {
                        m_sliderBtn = button;
                    }
                }
            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.GALL))
            {

                if(button instanceof BtnGall)
                {
                    BtnGall btn = (BtnGall) button;
                    if(m_clueOpenActive && ( btn.m_BtnName.equals("갤러리_front") || btn.m_BtnName.equals("갤러리_back") || btn.m_BtnName.equals("뒤로가기_레이아웃")))
                    {
                        int hisId = m_selectGallBtn.m_dataNumber;
                        if(btn.m_BtnName.equals("갤러리_front") && hisId < variable.m_gallDataList.size())
                        {
                            int stepCount = 0;
                            for(int i = hisId; i<variable.m_gallDataList.size(); i++)
                            {
                                //System.out.println( "check " + variable.m_gallDataList.get(i) + " Id " + variable.m_gallDataList.get(i).m_dataNumber + " title "  + variable.m_gallDataList.get(i).m_Title);
                                if(!variable.m_gallDataList.get(i).m_lock)
                                    stepCount += 1;
                                else
                                    break;
                            }
                            //System.out.println( "  "  + stepCount);
                            hisId +=1 + stepCount;
                            //System.out.println("hisid " + hisId);
                            m_selectGallBtn = variable.m_gallDataList.get(hisId-1);
                        }
                        else if(btn.m_BtnName.equals("갤러리_back") && hisId >= 1)
                        {
                            int stepCount = 0;
                            for(int i = hisId-1; i > 0; i--)
                            {
                                if(!variable.m_gallDataList.get(i).m_lock)
                                    stepCount--;
                            }
                            hisId +=  -1 + stepCount;
                            if(hisId >= 0)
                            {
                                if( !variable.m_gallDataList.get(hisId-1).m_lock)
                                    return;
                                m_selectGallBtn = variable.m_gallDataList.get(hisId-1);
                            }

                            //System.out.println("hisid " + hisId);
                        }
                        else if(btn.m_BtnName.equals("뒤로가기_레이아웃"))
                        {
                            m_clueOpenActive = false;
                        }


                    }
                    if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더"))
                    {
                        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

                        int scaleHeight = scaled.getScaledHeight();

                        int afterY = scaleHeight/2 - 30;

                        boolean active = (btn.y >= afterY) ? true : false;
                        if(active)
                        {
                            if(!m_clueOpenActive)
                            {
                                m_selectGallBtn = btn.m_data;
                                if(btn.m_data.m_lock)
                                {
                                    m_clueOpenActive = true;
                                }
                            }

                        }

                    }
                    if (btn.m_BtnName.equals("뒤로가기"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
                    }
                    else if (btn.m_BtnName.equals("슬라이더"))
                    {
                        m_sliderBtn = button;
                    }
                }
            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT))
            {
                if(button instanceof BtnUrgent)
                {
                    BtnUrgent btn = (BtnUrgent) button;

                    //클릭한 요소가 메일 리스트 오브젝트 일경우

                    if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") && !btn.m_BtnName.equals("수령"))
                    {
                        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

                        int scaleHeight = scaled.getScaledHeight();

                        int afterY = scaleHeight/2 - 30;

                        boolean active = (btn.y >= afterY) ? true : false;
                        // 화면밖에 있는 오브젝트 일경우 ( gl_scissor 로 인한 안보이는 요소 클릭된 경우 체크 )
                        if(active)
                        {
                            //안 읽은 메일 경우 readActive == false 읽었을 경우 true
                            if(!btn.m_data.m_readActive)
                            {
                                btn.m_data.m_readActive = true;
                                Packet.networkWrapper.sendToServer(new SPacketUrgentRead(btn.m_data.m_mailId, btn.m_data.m_readActive));
                            }
                            //세부 메일내용 Gui 열렸는지 안열렸는지 체크하는 부분
                            //위에 덧그리는 부분이기 때문에 뒤에 버튼은 살아있는 상태이기에 간섭 방지용
                            if(!m_openTextBox)
                            {
                                m_scaleAni = 0f;
                                m_scaleAnimation = false;
                                m_selectUrgentBtn = btn;
                                m_openTextBox = true;
                            }

                        }

                    }
                    if (btn.m_BtnName.equals("뒤로가기"))
                    {
                        //뒤로가기 버튼 클릭시 로직
                        if(m_openTextBox)
                        {
                            variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT;
                            m_openTextBox = false;
                        }
                        else
                        {
                            variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;

                        }

                    }
                    if(m_openTextBox)
                    {
                        
                        if (btn.m_BtnName.equals("수령"))
                        {
                            //세부 메일 내용에서 수령하기가 정상적으로 작동했을 경우
                            if(m_selectUrgentBtn.m_giveActive)
                            {
                                //1회성 애니메이션 작동 방지 변수
                                m_scaleAnimation = true;

                            }
                            else
                            {
                                int airSlot = InventoryUtils.getAirCountToInventory(mc.player);
                                int itemCount = 0;
                                itemCount += ! m_selectUrgentBtn.m_data.m_stack1.getItem().equals(Items.AIR) ? 1 : 0;
                                itemCount += ! m_selectUrgentBtn.m_data.m_stack2.getItem().equals(Items.AIR) ? 1 : 0;
                                itemCount += ! m_selectUrgentBtn.m_data.m_stack3.getItem().equals(Items.AIR) ? 1 : 0;
                                itemCount += ! m_selectUrgentBtn.m_data.m_stack4.getItem().equals(Items.AIR) ? 1 : 0;
                                itemCount += ! m_selectUrgentBtn.m_data.m_stack5.getItem().equals(Items.AIR) ? 1 : 0;

                                // 아이템 개수 파악 후 인벤토리 아이템 빈공간 체크
                                if(itemCount <= airSlot)
                                {
                                    //m_selectUrgentBtn.m_data.give();
                                    m_scaleAnimation = true;
                                    m_selectUrgentBtn.m_data.m_receiveActive = true;

                                    m_selectUrgentBtn.m_giveActive = true;
                                    //아이템 정상적으로 수령시 서버단 메일데이터에도 반영
                                    Packet.networkWrapper.sendToServer(new SPacketMailDataGive(m_selectUrgentBtn.m_data.m_mailId, m_selectUrgentBtn.m_data.m_receiveActive));
                                }
                                else
                                {
                                    mc.player.sendMessage( new TextComponentString(" 인벤토리에 최소 " + itemCount + " 개의 빈 공간이 필요합니다."));
                                }



                            }

                        }
                    }
                    else if (btn.m_BtnName.equals("슬라이더"))
                    {
                        m_sliderBtn = button;
                    }
                }
            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.TRIBE))
            {
                if(button instanceof BtnAutoMine)
                {
                    BtnAutoMine btn = (BtnAutoMine) button;
                    if (btn.m_BtnName.equals("뒤로가기"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
                    }
                }

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.SHOP))
            {
                if(button instanceof BtnPhoneShop)
                {
                    BtnPhoneShop btn = (BtnPhoneShop) button;
                    if (btn.m_BtnName.equals("뒤로가기"))
                    {
                        variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.MENU;
                    }
                    else if (btn.m_BtnName.equals("아이템"))
                    {
                        BtnPhoneShop phoneBtn = (BtnPhoneShop) button;
                        ShopData itemData = variable.m_hashShopData.get("스마트폰상점");
                        ShopItemData data = itemData.itemDataList.get(phoneBtn.shopId);

                        int stackCount = 0;

                        for (ItemStack stack : mc.player.inventory.mainInventory)
                        {
                            if (stack.getItem().equals(data.requestBuyItem.getItem()))
                                stackCount += stack.getCount();
                        }
                        if(data.dayBuyCurrentLimitCount > 0 && Keyboard.isKeyDown(42))
                        {
                            if(data.dayBuyCurrentLimitCount >= 64)
                            {
                                if(data.requestItemBuyAmount * 64 <= stackCount)
                                {
                                    if ( ! InventoryUtils.canAddItemToInventory(mc.player, data.shopItem.copy(), 64))
                                    {
                                        mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f인벤토리에 빈 공간이 부족합니다"));
                                        return;
                                    }
                                    Packet.networkWrapper.sendToServer(new SPacketItemRemove(data.requestBuyItem.copy(), 64));
                                    ItemStack fullStack = data.shopItem.copy();
                                    fullStack.setCount(64);
                                    Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                                    //data.dayBuyCurrentLimitCount -= 64;
                                    Packet.networkWrapper.sendToServer(new SPacketShopDataItemRemove(data.shopItemId, -1*64, "스마트폰상점"));
                                    Packet.networkWrapper.sendToServer(new SPacketDefaultShopDataReload("스마트폰상점"));

                                    mc.player.sendMessage(new TextComponentString("§l[地上世界] §f"+data.shopItem.getDisplayName() +" 아이템을 64개 구매 하였습니다."));
                                }
                                else
                                {
                                    mc.player.sendMessage(new TextComponentString("§l[地上世界] §f요구 아이템이 부족하여 구매가 불가합니다."));
                                }
                            }
                            else
                            {
                                mc.player.sendMessage(new TextComponentString("§l[地上世界] §f재고가 부족하여 구매가 불가능 합니다."));
                            }

                        }
                        else if (data.dayBuyCurrentLimitCount <= 0)
                        {
                            mc.player.sendMessage(new TextComponentString("§l[地上世界] §f재고가 부족하여 구매가 불가능 합니다."));
                        }
                        else
                        {
                            if ( ! InventoryUtils.canAddItemToInventory(mc.player, data.shopItem.copy(), 1))
                            {
                                mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f인벤토리에 빈 공간이 부족합니다"));
                                return;
                            }

                            if(data.requestItemBuyAmount <= stackCount)
                            {
                                Packet.networkWrapper.sendToServer(new SPacketItemRemove(data.requestBuyItem.copy(), data.requestItemBuyAmount));
                                ItemStack fullStack = data.shopItem.copy();
                                fullStack.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                                data.dayBuyCurrentLimitCount -= 1;
                                Packet.networkWrapper.sendToServer(new SPacketShopDataItemRemove(data.shopItemId, -1, "스마트폰상점"));
                                mc.player.sendMessage(new TextComponentString("§l[地上世界] §f"+data.shopItem.getDisplayName() +" 아이템을 1개 구매 하였습니다."));
                            }
                            else
                            {
                                mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f요구 아이템이 부족하여 구매가 불가합니다."));
                            }

                        }



                    }

                }

            }
        });


    }

    void initMemberSlot()
    {

    }

    @Override
    public void initGui() {
        super.initGui();
        int btnId = 0;

        ScaledResolution scaled = new ScaledResolution(mc);

        int scaleWidth = scaled.getScaledWidth();
        int scaleHeight = scaled.getScaledHeight();
        if(m_antenna)
        {

            //region 메뉴 버튼
            buttonList.add(new BtnMenu(btnId++, scaleWidth/2 - 80, scaleHeight/2 - 44, "", "통화")); // 0
            buttonList.add(new BtnMenu(btnId++, scaleWidth/2 - 38, scaleHeight/2 - 44, "", "긴급 문자")); // 1
            buttonList.add(new BtnMenu(btnId++, scaleWidth/2 + 3, scaleHeight/2 - 44, "", "갤러리")); // 2
            buttonList.add(new BtnMenu(btnId++, scaleWidth/2 + 44, scaleHeight/2 - 44, "", "상점")); // 3
            buttonList.add(new BtnMenu(btnId++, scaleWidth/2 - 80, scaleHeight/2 + 9, "", "부족")); // 4


            //endregion

            //region 통화중 버튼
            buttonList.add(new BtnCalling(btnId++, scaleWidth/2 - 13, scaleHeight/2 + 105, "", "통화종료")); // 3
            buttonList.add(new BtnCalling(btnId++, scaleWidth/2 - 80, scaleHeight/2 + 10, "", "뒤로가기"));  // 4
            //endregion

            //region 통화 버튼
            buttonList.add(new BtnCall(btnId++, scaleWidth/2 - 58, scaleHeight/2 + 103, "", "승낙아이콘")); // 5
            buttonList.add(new BtnCall(btnId++, scaleWidth/2 + 31, scaleHeight/2 + 103, "", "거절아이콘")); // 6


            //endregion

            //region 연락처버튼

            String[] meneber = new String[]{Variable.PHONE_CALLING_PLAYER.d7297.name(), Variable.PHONE_CALLING_PLAYER.Daju_.name(),Variable.PHONE_CALLING_PLAYER.Noonkkob.name(),
            Variable.PHONE_CALLING_PLAYER.RuTaeY.name(), Variable.PHONE_CALLING_PLAYER.Huchu95.name(),  Variable.PHONE_CALLING_PLAYER.samsik23.name(),
                    Variable.PHONE_CALLING_PLAYER.Seoneng.name(), Variable.PHONE_CALLING_PLAYER.KonG7.name()};

            for(int i = 0; i<8; i++)
            {
                if(!meneber[i].equals(mc.player.getName()))
                    buttonList.add(new BtnContent(btnId++, scaleWidth/2 - 100, scaleHeight/2 - m_contentSliderYPos + (i * 32), "", meneber[i])); //7부터
            }
            buttonList.add(new BtnContent(btnId++, scaleWidth/2 - 78, scaleHeight/2 - 60, "", "뒤로가기")); //뒤로가기
            buttonList.add(new BtnContent(btnId++, scaleWidth/2 + 81, scaleHeight/2 - 13, "", "슬라이더")); //뒤로가기


            buttonList.add(new BtnGall(btnId++, scaleWidth/2 - 78, scaleHeight/2 - 60, "", "뒤로가기")); //뒤로가기
            buttonList.add(new BtnGall(btnId++, scaleWidth/2 + 82, scaleHeight/2 - 13, "", "슬라이더")); //뒤로가기

            boolean test = true;
            if (test)
            {
                for (int i = 0; i<variable.m_gallDataList.size(); i++)
                {
                    GallData data = variable.m_gallDataList.get(i);
                    buttonList.add(new BtnGall(btnId++, scaleWidth/2 - 85, scaleHeight/2 - m_contentSliderYPos + (i * 32) - 30, "", "단서", data));

                    m_lastYPos = scaleHeight/2 - m_contentSliderYPos + (i * 32) - 30;

                }
            }

            m_minYPos = scaleHeight/2 - m_contentSliderYPos + (0 * 32) - 30;

            buttonList.add(new BtnGall(btnId++, 15, 20, "", "뒤로가기_레이아웃"));
            buttonList.add(new BtnGall(btnId++, scaleWidth-50, scaleHeight/2 - 10, "", "갤러리_front"));
            buttonList.add(new BtnGall(btnId++, 40, scaleHeight/2 - 10, "", "갤러리_back"));



            buttonList.add(new BtnUrgent(btnId++, scaleWidth/2 - 78, scaleHeight/2 - 60, "", "뒤로가기")); //뒤로가기
            buttonList.add(new BtnUrgent(btnId++, scaleWidth/2 + 82, scaleHeight/2 - 13, "", "슬라이더")); //뒤로가기

            if (test)
            {
                m_MouseY = 0;
                for (int i = variable.m_mailDataList.size()-1; i >= 0; i--)
                {
                    MailData data = variable.m_mailDataList.get(i);
                    if(data.m_recivePlayerName.equals(mc.player.getName()))
                    {
                        if(!data.m_readActive)
                            m_readUrgenttext = true;
                        BtnUrgent btn = new BtnUrgent(btnId++, scaleWidth/2 - 85, scaleHeight/2 - m_contentSliderYPos + (m_MouseY * 32) - 30, "", "메일", data);
                        buttonList.add(btn);
                        m_UngentLastBtn = btn;
                        m_lastYPosMail = scaleHeight/2 - m_contentSliderYPos + (m_MouseY * 32) - 30;
                        m_MouseY++;
                    }

                }
            }
            System.out.println("init 완료");

            buttonList.add(new BtnUrgent(btnId++, scaleWidth/2 + 48, scaleHeight/2 + 115, "", "수령"));

            ArrayList<AutoMineArea> arrayList = new ArrayList<>();
            for(AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
            {
                if(area.ownerName.equals(mc.player.getName()))
                {
                    arrayList.add(area);
                }
            }

            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 - 60, "", "뒤로가기", arrayList));
            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 - 8, "", "1", arrayList));
            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 + 23, "", "2", arrayList));
            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 + 54, "", "3", arrayList));
            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 + 85, "", "4", arrayList));
            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 + 116, "", "5", arrayList));
            buttonList.add(new BtnAutoMine(btnId++, scaleWidth/2 - 78, scaleHeight/2 + 147, "", "6", arrayList));


            int[] widths = new int[]{-85, -29, 27};
            int[] heights = new int[]{-10, 58, 126};


            ShopData itemData = variable.m_hashShopData.get("스마트폰상점");

            itemData.shopMode = ShopData.eShop_Mode.BUY;
            int count = 0;

            for(int i = 0; i<itemData.itemDataList.size(); i++)
            {
                int yCount = i / 3;
                //ShopItemData data = itemData.itemDataList.get(i);

                buttonList.add(new BtnPhoneShop(btnId++, scaleWidth/2 + widths[count], scaleHeight/2 + heights[yCount], "", "아이템", i));

                phoneShopUiMaxScrollYPos = scaleHeight/2+ heights[yCount] + 196f/3f/2f;
                count += 1;
                if(count >= 3)
                    count = 0;
            }

            System.out.println(phoneShopUiMaxScrollYPos);


            phoneShopUiMinScrollYPos = scaleHeight/2 - 0;


            buttonList.add(new BtnPhoneShop(btnId++, scaleWidth/2 - 78, scaleHeight/2 -60, "", "뒤로가기"));  // 4
            buttonList.add(new BtnPhoneShop(btnId++, scaleWidth/2 + 82, scaleHeight/2 - 0, "", "슬라이더"));


            //endregion
        }

        for(GuiButton btn : buttonList)
            btn.enabled = false;


    }
    float phoneShopUiMaxScrollYPos;
    float phoneShopUiMinScrollYPos;



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        ScaledResolution scaled = new ScaledResolution(mc);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();


        drawDefaultBackground();
        float notSignal_Width = 616f/3f;
        float notSignal_Height = 877f/3f;

        for(GuiButton btn : buttonList)
            btn.enabled = false;

        if(variable.currentBattery <= 0)
        {

            float antenna_Width = 670f/3f;
            float antenna_Height = 120f/3f;
            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\안테나.png"));
            Render.drawTexture(scaleWidth/2 - antenna_Width/2 + 34, scaleHeight/2 - antenna_Height/2 - 125, antenna_Width, antenna_Height, 0, 0, 1, 1, 0, 1);


            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\0.png"));
            Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 1, 1);

        }
        else if(!m_antenna)
        {

            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\not_signal.png"));
            Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 1, 1);

            String battery = "green";
            if(variable.currentBattery >= 70)
                battery = "green";

            else if (variable.currentBattery >= 40)
                battery = "yellow";
            else
                battery = "red";


            float battery_Width = 37/3f;
            float battery_Height = 14/3f;


            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\배터리_"+battery+".png"));
            Render.drawXLinearTexture (scaleWidth/2 - battery_Width/2 + 43.8f, scaleHeight/2 - battery_Height/2 - 90.3f, battery_Width, battery_Height, variable.currentBattery, 1, 2);

        }
        else
        {

            float antenna_Width = 670f/3f;
            float antenna_Height = 120f/3f;
            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\안테나.png"));
            Render.drawTexture(scaleWidth/2 - antenna_Width/2 + 34, scaleHeight/2 - antenna_Height/2 - 125, antenna_Width, antenna_Height, 0, 0, 1, 1, 0, 1);


            if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT) ||  variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.GALL) || variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.TRIBE) || variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.SHOP))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\갤러리핸드폰.png"));
                Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 1, 1f);

            }
            else if (variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT))
            {
                if(m_openTextBox)
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\긴급문자핸드폰.png"));
                    Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 1, 1);

                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\갤러리핸드폰.png"));
                    Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 1, 1f);

                }

            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\메뉴배경화면.png"));
                Render.drawTexture(scaleWidth/2 - notSignal_Width/2, scaleHeight/2 - notSignal_Height/2 + 34, notSignal_Width, notSignal_Height, 0, 0, 1, 1, 1, 1);

            }


            if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING))
            {
                float norch_Calling_Width = 360f / 3f;
                float norch_Calling_Height = 56f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\노치통화중3.png"));
                Render.drawTexture (scaleWidth/2 - norch_Calling_Width/2, scaleHeight/2 - norch_Calling_Height/2 - 90.5f, norch_Calling_Width, norch_Calling_Height, 0, 0 ,1, 1, 1, 1);

                float callingStr_Width = 114f/3f;
                float callingStr_Height = 20f/3f;

                m_callStrActive = (mouseX >= scaleWidth/2 - norch_Calling_Width/2 && mouseY >= scaleHeight/2 - norch_Calling_Height/2 - 90.5f && mouseX < scaleWidth/2 + norch_Calling_Width/2 && mouseY < scaleHeight/2 + norch_Calling_Height/2 - 90.5f) ? true : false;


                String callingPlayer = variable.getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
                if(m_callStrActive)
                {
                    GlStateManager.pushMatrix();
                    {
                        float scale = 1.05f;
                        GlStateManager.scale(scale, scale, 1);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\calling\\"+callingPlayer+"통화중_.png"));
                        Render.drawTexture ((scaleWidth/2 - callingStr_Width/2 - 5)/scale - 0.25f , (scaleHeight/2 - callingStr_Height/2 - 90.5f)/scale - 0.25f, callingStr_Width, callingStr_Height, 0, 0 ,1, 1, 1, 1);
                    }
                    GlStateManager.popMatrix();
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\calling\\"+callingPlayer+"통화중_.png"));
                    Render.drawTexture (scaleWidth/2 - callingStr_Width/2 - 5, scaleHeight/2 - callingStr_Height/2 - 90.5f, callingStr_Width, callingStr_Height, 0, 0 ,1, 1, 1, 1);

                }

            }
            else if (variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER) || (variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER)))
            {
                float norch_Calling_Width = 360f / 3f;
                float norch_Calling_Height = 56f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\노치통화중3.png"));
                Render.drawTexture (scaleWidth/2 - norch_Calling_Width/2, scaleHeight/2 - norch_Calling_Height/2 - 90.5f, norch_Calling_Width, norch_Calling_Height, 0, 0 ,1, 1, 1, 0);

                float callingStr_Width = 114f/3f;
                float callingStr_Height = 20f/3f;

                m_callStrActive = (mouseX >= scaleWidth/2 - norch_Calling_Width/2 && mouseY >= scaleHeight/2 - norch_Calling_Height/2 - 90.5f && mouseX < scaleWidth/2 + norch_Calling_Width/2 && mouseY < scaleHeight/2 + norch_Calling_Height/2 - 90.5f) ? true : false;


                String callingPlayer = variable.getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
                if(m_callStrActive)
                {
                    GlStateManager.pushMatrix();
                    {
                        float scale = 1.05f;
                        GlStateManager.scale(scale, scale, 1);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\calling\\"+callingPlayer+"연결중_.png"));
                        Render.drawTexture ((scaleWidth/2 - callingStr_Width/2 - 5)/scale - 0.25f , (scaleHeight/2 - callingStr_Height/2 - 90.5f)/scale - 0.25f, callingStr_Width, callingStr_Height, 0, 0 ,1, 1, 1, 1);
                    }
                    GlStateManager.popMatrix();
                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\calling\\"+callingPlayer+"연결중_.png"));
                    Render.drawTexture (scaleWidth/2 - callingStr_Width/2 - 5, scaleHeight/2 - callingStr_Height/2 - 90.5f, callingStr_Width, callingStr_Height, 0, 0 ,1, 1, 1, 1);

                }
            }


            //region 통화권 로직
            float conneting_Width = 24/3f;
            float conneting_Height = 14/3f;

            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\통화권성공.png"));
            Render.drawTexture(scaleWidth/2 - conneting_Width/2 + 27f, scaleHeight/2 - conneting_Height/2 - 90, conneting_Width, conneting_Height, 0, 0, 1, 1, 2, 1);

            //endregion

            //region 배터리 UI 로직

            String battery = "green";
            if(variable.currentBattery >= 70)
                battery = "green";

            else if (variable.currentBattery >= 40)
                battery = "yellow";
            else
                battery = "red";


            float battery_Width = 37/3f;
            float battery_Height = 14/3f;


            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\phone\\배터리_"+battery+".png"));
            Render.drawXLinearTexture (scaleWidth/2 - battery_Width/2 + 43.8f, scaleHeight/2 - battery_Height/2 - 90.3f, battery_Width, battery_Height, variable.currentBattery, 1, 2);

            //endregion

            if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.MENU))
            {
                //region 스마트폰 메뉴 상태 로직

                float call_Str_Width = 35/3f;
                float call_Str_Height = 19/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\통화.png"));
                Render.drawTexture (scaleWidth/2 - call_Str_Width/2 - 62, scaleHeight/2 - call_Str_Height/2 - 6, call_Str_Width, call_Str_Height, 0, 0 ,1, 1, 1, 4);


                float UrgentText_Str_Width = 71/3f;
                float UrgentText_Str_Height = 19/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\긴급 문자.png"));
                Render.drawTexture (scaleWidth/2 - UrgentText_Str_Width/2 - 21, scaleHeight/2 - UrgentText_Str_Height/2 - 6, UrgentText_Str_Width, UrgentText_Str_Height, 0, 0 ,1, 1, 1, 4);


                float gall_Str_Width = 46/3f;
                float gall_Str_Height = 21/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\갤러리.png"));
                Render.drawTexture (scaleWidth/2 - gall_Str_Width/2 + 21, scaleHeight/2 - gall_Str_Height/2 - 6, gall_Str_Width, gall_Str_Height, 0, 0 ,1, 1, 1, 4);

                float shop_Str_Width = 32/3f;
                float shop_Str_Height = 19/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\상점.png"));
                Render.drawTexture (scaleWidth/2 - shop_Str_Width/2 + 63, scaleHeight/2 - shop_Str_Height/2 - 6, shop_Str_Width, shop_Str_Height, 0, 0 ,1, 1, 1, 4);

                float tribe_Str_Width = 34/3f;
                float tribe_Str_Height = 18/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\부족.png"));
                Render.drawTexture (scaleWidth/2 - tribe_Str_Width/2 - 62, scaleHeight/2 - tribe_Str_Height/2 + 48, tribe_Str_Width, tribe_Str_Height, 0, 0 ,1, 1, 1, 4);




                float five_one_Width = 118/3f;
                float five_one_Height = 12/3f;


                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\menu\\icon.png"));
                Render.drawTexture (scaleWidth/2 - five_one_Width/2, scaleHeight/2 - five_one_Height/2 + 165, five_one_Width, five_one_Height, 0, 0 ,1, 1, 1, 4);

                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnMenu)
                    {
                        button.enabled = true;
                        button.drawButton(mc, mouseX, mouseY, partialTicks);
                    }
                }

                //endregion
            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CALLING))
            {
                //region 통화중 로직
                float callingHead_Width = 164f / 3f;

                String callingPlayer = variable.getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\face\\"+callingPlayer+".png"));
                Render.drawTexture(scaleWidth/2 - callingHead_Width/2, scaleHeight/2 - callingHead_Width/2 + 13, callingHead_Width, callingHead_Width, 0, 0, 1, 1, 2, 1);

                float callingPlayerStr_Width  = 65f / 3f;
                float callingPlayerStr_Height  = 33f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\str\\"+callingPlayer+".png"));
                Render.drawTexture(scaleWidth/2 - callingPlayerStr_Width/2, scaleHeight/2 - callingPlayerStr_Height/2 + 57, callingPlayerStr_Width, callingPlayerStr_Height, 0, 0, 1, 1, 2, 1);

                float callingStr_Width = 72/3f;
                float callingStr_Height = 20/3f;

                if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER))
                {

                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\str\\연결중.png"));
                    Render.drawTexture((scaleWidth/2 - callingStr_Width/2), (scaleHeight/2 - callingStr_Height/2 + 72), callingStr_Width, callingStr_Height, 0, 0, 1, 1, 2, 1);

                }
                else
                {
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\calling\\str\\통화중.png"));
                    Render.drawTexture(scaleWidth/2 - callingStr_Width/2, scaleHeight/2 - callingStr_Height/2 + 72, callingStr_Width, callingStr_Height, 0, 0, 1, 1, 2, 1);

                }

                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnCalling)
                    {
                        button.enabled = true;
                        button.drawButton(mc, mouseX, mouseY, partialTicks);
                    }
                }

                //endregion

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CALL))
            {

                //region 통화 옴
                float callingHead_Width = 164f / 3f;

                String callingPlayer = variable.getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\call\\face\\"+callingPlayer+".png"));
                Render.drawTexture(scaleWidth/2 - callingHead_Width/2, scaleHeight/2 - callingHead_Width/2 - 3, callingHead_Width, callingHead_Width, 0, 0, 1, 1, 2, 1);

                float callingPlayerStr_Width  = 65f / 3f;
                float callingPlayerStr_Height  = 33f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\call\\str\\"+callingPlayer+".png"));
                Render.drawTexture(scaleWidth/2 - callingPlayerStr_Width/2, scaleHeight/2 - callingPlayerStr_Height/2 + 40, callingPlayerStr_Width, callingPlayerStr_Height, 0, 0, 1, 1, 2, 1);

                float callingStr_Width = 42/3f;
                float callingStr_Height = 22/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\call\\str\\승낙.png"));
                Render.drawTexture(scaleWidth/2 - callingStr_Width/2 - 45, scaleHeight/2 - callingStr_Height/2 + 139, callingStr_Width, callingStr_Height, 0, 0, 1, 1, 2, 1);

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\call\\str\\거절.png"));
                Render.drawTexture(scaleWidth/2 - callingStr_Width/2 + 45, scaleHeight/2 - callingStr_Height/2 + 139, callingStr_Width, callingStr_Height, 0, 0, 1, 1, 2, 1);


                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnCall)
                    {
                        button.enabled = true;
                        button.drawButton(mc, mouseX, mouseY, partialTicks);
                    }
                }


                //endregion
            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.CONTENT))
            {
                //region 연락처 Gui

                float semiColumn_Width = 5f/3f;
                float semiColumn_Height = 24f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\_.png"));
                Render.drawTexture (scaleWidth/2 - semiColumn_Width/2 + 74, scaleHeight/2 - semiColumn_Height/2 - 40, semiColumn_Width, semiColumn_Height, 0, 0 ,1, 1, 1, 1);



                float contentStr_Width = 100/3f;
                float contentStr_Height = 37/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\연락처.png"));
                Render.drawTexture (scaleWidth/2 - contentStr_Width/2 - 62, scaleHeight/2 - contentStr_Height/2 - 41, contentStr_Width, contentStr_Height, 0, 0 ,1, 1, 1, 1);


                float contentLine_Width  = 520f / 3f;
                float contentLine_Height  = 2f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\str\\line.png"));
                Render.drawTexture(scaleWidth/2 - contentLine_Width/2, scaleHeight/2 - contentLine_Height/2 - 18, contentLine_Width, contentLine_Height, 0, 0, 1, 1, 3, 1);


                //15
                GL11.glPushMatrix();
                {

                    int scaleFactor = scaled.getScaleFactor(); // 스케일 팩터 값

                    int scissorWidth = (int) (scaleWidth * scaleFactor); //실제 화면 너비 기준으로 따라감
                    int scissorHeight = (int) (scaleHeight * scaleFactor); //실제 화면 높이 기준으로 따라감



                    int scissorNotSignal_Width = 616; //실제 이미지 크기 기준으로 계산해야함
                    int scissorNotSignal_Height = 877; //실제 이미지 크기 기준으로 계산해야함

                    GL11.glEnable(GL11.GL_SCISSOR_TEST);

                    float scalePer = scaleFactor == 3 ? 1 : 1.725f;
                    GL11.glScissor((int) (scissorWidth/2 - scissorNotSignal_Width/2) + 50, 0, (int) scissorNotSignal_Width - 100, (int) ((int) scissorNotSignal_Height - (283 * scalePer)));

                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof  BtnContent)
                        {
                            BtnContent btn = (BtnContent) button;
                            if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") )
                            {
                                button.enabled = true;
                                button.drawButton(mc, mouseX, mouseY, partialTicks);
                            }

                        }
                    }
                    int j = 0;
                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof  BtnContent)
                        {
                            BtnContent btn = (BtnContent) button;
                            if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") )
                            {
                                btn.y = (int) (scaleHeight/2 + m_contentSliderYPos + (j * 32));
                                j++;
                            }
                        }
                    }

                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }
                GL11.glPopMatrix();


                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnContent)
                    {
                        if(((BtnContent) button).m_BtnName.equals("뒤로가기") || ((BtnContent) button).m_BtnName.equals("슬라이더") )
                        {
                            button.enabled = true;
                            button.drawButton(mc, mouseX, mouseY, partialTicks);
                        }

                    }
                }

                if(m_scrollActive)
                {
                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof  BtnContent)
                        {
                            if(((BtnContent) button).m_BtnName.equals("슬라이더"))
                            {
                                BtnContent btn = (BtnContent) button;
                                float back_Height = 192f/3f;

                                if(scaleHeight/2 - 13 >= mouseY - 37)
                                {

                                }
                                else if (scaleHeight - back_Height - 5 >= mouseY)
                                {
                                    btn.y = mouseY - 20;
                                }

                                float per =  (btn.y - 168f) / ( 289f - 168f );

                                //-15 ~ -86
                                m_contentSliderYPos = (int) ((-86 * per));
                            }
                        }
                    }

                }

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.GALL))
            {
                float gall_Str_Width = 100f / 3f;
                float gall_Str_Height = 37f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\str\\갤러리.png"));
                Render.drawTexture (scaleWidth/2 - gall_Str_Width/2 - 62, scaleHeight/2 - gall_Str_Height/2 - 41, gall_Str_Width, gall_Str_Height, 0, 0 ,1, 1, 1, 1);

                float line_Str_Width = 536f/3f;
                float line_Str_Height = 2f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\str\\line.png"));
                Render.drawTexture (scaleWidth/2 - line_Str_Width/2, scaleHeight/2 - line_Str_Height/2 - 20, line_Str_Width, line_Str_Height, 0, 0 ,1, 1, 1, 1);

                float semiColumn_Width = 5f/3f;
                float semiColumn_Height = 24f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\_.png"));
                Render.drawTexture (scaleWidth/2 - semiColumn_Width/2 + 74, scaleHeight/2 - semiColumn_Height/2 - 40, semiColumn_Width, semiColumn_Height, 0, 0 ,1, 1, 1, 1);


                GL11.glPushMatrix();
                {
                    int scaleFactor = scaled.getScaleFactor(); // 스케일 팩터 값

                    int scissorWidth = (int) (scaleWidth * scaleFactor); //실제 화면 너비 기준으로 따라감
                    int scissorHeight = (int) (scaleHeight * scaleFactor); //실제 화면 높이 기준으로 따라감

                    int scissorNotSignal_Width = 616; //실제 이미지 크기 기준으로 계산해야함
                    int scissorNotSignal_Height = 877; //실제 이미지 크기 기준으로 계산해야함

                    float scalePer = scaleFactor == 3 ? 1f : 1.725f;
                    GL11.glEnable(GL11.GL_SCISSOR_TEST);
                    GL11.glScissor((int) (scissorWidth/2 - scissorNotSignal_Width/2) + 50, 0, (int) scissorNotSignal_Width - 100, (int) ((int) scissorNotSignal_Height - (283f * scalePer)));


                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof BtnGall)
                        {
                            BtnGall btn = (BtnGall) button;
                            if(btn.m_BtnName.equals("단서"))
                            {
                                button.enabled = true;
                                button.drawButton(mc, mouseX, mouseY, partialTicks);

                                //갤러리 리스트에서 단서 제목 표기
                                String title = btn.m_data.m_dataNumber + "번 단서 - " + btn.m_data.m_Title;
                                //갤러리 리스트에서 해금 안된 단서일 경우 제목 모자이크
                                if(btn.m_data.m_lock)
                                    title = title.length() <= 27 ? title : title.substring(0, 27);
                                else
                                {
                                    title = "?섎씪???듭꽦???섏쓿쟾??洹쇰낯?꾩쓣源5떖???먯씠? 沅???곕??梨낆엥怨??섎??ᄲ븯??";
                                    title = title.length() <= 29 ? title : title.substring(0, 29);
                                }

                                Render.drawStringScaleResizeByLeftWidth(title, btn.x + 6 , btn.y + btn.height/2f -3, 5, 0.8f, 1);
                            }
                            if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") && !btn.m_BtnName.equals("뒤로가기_레이아웃")&& !btn.m_BtnName.equals("갤러리_front") && !btn.m_BtnName.equals("갤러리_back") )
                            {
                                button.enabled = true;
                                button.drawButton(mc, mouseX, mouseY, partialTicks);
                            }

                        }
                    }
                    int j = 0;
                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof  BtnGall)
                        {
                            BtnGall btn = (BtnGall) button;
                            if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") && !btn.m_BtnName.equals("뒤로가기_레이아웃")
                                    && !btn.m_BtnName.equals("갤러리_front") && !btn.m_BtnName.equals("갤러리_back") )
                            {
                                btn.y = (int) (scaleHeight/2 + m_contentSliderYPos + (j * 32));
                                j++;
                            }
                        }
                    }
                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }
                GL11.glPopMatrix();

                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnGall)
                    {
                        BtnGall btn = (BtnGall) button;
                        if(btn.m_BtnName.equals("뒤로가기") || btn.m_BtnName.equals("슬라이더") )
                        {
                            button.enabled = true;
                            button.drawButton(mc, mouseX, mouseY, partialTicks);
                        }
                    }
                }

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT))
            {

                float fpsCurrection = (120f / Minecraft.getDebugFPS()) * 0.5f;

                float random = (float) ((Math.random() * (10 - 5) + 5) * 0.1f);


                float partialTicksCurrection = ((0.25f + random)  * fpsCurrection) * 0.03f;

                float scaleAni = 1f;
                float alpha = 0f;
                //애니메이션 재생중일 경우 작동하는 구문
                if(m_scaleAnimation)
                {
                    if(m_scaleAni < 1f)
                    {
                        m_scaleAni += partialTicksCurrection;
                        scaleAni = Render.easeOutBounce(m_scaleAni);
                    }
                    else
                    {
                        scaleAni = 1;
                        m_scaleAni = 1;
                        m_scaleAnimation = false;

                    }
                }

                float gall_Str_Width = 145f / 3f;
                float gall_Str_Height = 37f / 3f;

                float semiColumn_Width = 5f/3f;
                float semiColumn_Height = 24f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\_.png"));
                Render.drawTexture (scaleWidth/2 - semiColumn_Width/2 + 74, scaleHeight/2 - semiColumn_Height/2 - 40, semiColumn_Width, semiColumn_Height, 0, 0 ,1, 1, 1, 1);

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\str\\긴급 문자.png"));
                Render.drawTexture (scaleWidth/2 - gall_Str_Width/2 - 55, scaleHeight/2 - gall_Str_Height/2 - 41, gall_Str_Width, gall_Str_Height, 0, 0 ,1, 1, 1, 1);

                //메일창을 클릭 했을 경우
                if(m_openTextBox)
                {

                    if(m_selectUrgentBtn != null) // null 체크
                    {
                        //서버측에서 넘어온 메일 데이터에서 이미 아이템 수령 완료일 경우 
                        if(m_selectUrgentBtn.m_data.m_receiveActive)
                            m_selectUrgentBtn.m_giveActive = true; // 클라이언트 측 데이터 싱크
                    }



                    float urgentBox_Width = 489f/3f;
                    float urgentBox_Height = 461f/3f;
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\긴급문자박스.png"));
                    Render.drawTexture (scaleWidth/2 - urgentBox_Width/2, scaleHeight/2 - urgentBox_Height/2 + 80.5f, urgentBox_Width, urgentBox_Height, 0, 0 ,1, 1, 3, 1);


                    String title = m_selectUrgentBtn.m_data.m_title;
                    int fontWidth = fontRenderer.getStringWidth(title);
                    Render.drawStringScaleResizeByLeftWidth(title, scaleWidth/2 - 80f, scaleHeight/2 - 10.5f, 4, 1.25f, 1, false);


                    String date = m_selectUrgentBtn.m_data.m_date;
                    int fontdate = fontRenderer.getStringWidth(title);
                    Render.drawStringScaleResizeByLeftWidth(date, scaleWidth/2 + 45, scaleHeight/2 + 5, 4, 0.6f, 1, false);

                    //서버측에서 넘어오는 색코드 변환
                    String text =  m_selectUrgentBtn.m_data.m_text.replaceAll("&", "§");
                    ArrayList<String> strList = new ArrayList<>();
                    int textCount = 20;
                    int forCount = text.length() / textCount;

                    if(text.length() > textCount )
                    {
                        for(int i = 0; i<=forCount; i++)
                        {
                            //글자가 30자가 넘으면 10자씩 잘라서 출력
                            if(i==0)
                                strList.add(text.substring(0, (i+1)*textCount));

                            else if(i == forCount)
                            {
                                strList.add(text.substring(i*textCount));
                            }
                            else
                            {
                                strList.add(text.substring((i)*textCount, (i+1)*textCount));
                            }
                        }

                    }
                    else {
                        strList.add(text);
                    }

                    strList.add(text);

                    String[] descriptions = text.split("\n");


                    for(int i = 0; i<descriptions.length; i++)
                    {
                        int fontText = fontRenderer.getStringWidth(descriptions[i]);
                        Render.drawStringScaleResizeByLeftWidth(descriptions[i], scaleWidth/2 - 77, scaleHeight/2 + 20 + (i * 7), 5, 0.65f, 1, false);

                    }
                    
                    //GUI에 아이템 배치하는 로직

                    ItemStack stack1 = m_selectUrgentBtn.m_data.m_stack1.copy();
                    ItemStack stack2 = m_selectUrgentBtn.m_data.m_stack2.copy();
                    ItemStack stack3 = m_selectUrgentBtn.m_data.m_stack3.copy();
                    ItemStack stack4 = m_selectUrgentBtn.m_data.m_stack4.copy();
                    ItemStack stack5 = m_selectUrgentBtn.m_data.m_stack5.copy();


                    RenderHelper.enableGUIStandardItemLighting();
                    Render.renderHotbarItem((int) (scaleWidth/2 - 76), (int) (scaleHeight/2 + 135), partialTicks, mc.player, stack5);
                    Render.renderHotbarItem((int) (scaleWidth/2 - 41), (int) (scaleHeight/2 + 135), partialTicks, mc.player, stack4);
                    Render.renderHotbarItem((int) (scaleWidth/2 - 7), (int) (scaleHeight/2 + 135), partialTicks, mc.player, stack3);
                    Render.renderHotbarItem((int) (scaleWidth/2 + 27), (int) (scaleHeight/2 + 135), partialTicks, mc.player, stack2);
                    Render.renderHotbarItem((int) (scaleWidth/2 + 60), (int) (scaleHeight/2 + 135), partialTicks, mc.player, stack1);

                    RenderHelper.disableStandardItemLighting();


                    // 아이템 수령시 위에 덧그려지는 이미지
                    if(m_selectUrgentBtn.m_giveActive)
                    {
                        float blackWidth = 85f/3f;

                            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\btn\\수령검은색.png"));
                            Render.drawTexture ((scaleWidth/2 - blackWidth/2 - 67.5f) - (3f - ( 3f * scaleAni)), (scaleHeight/2 - blackWidth/2 + 143) - (3f - ( 3f * scaleAni)), blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, 1.25f - (0.25f * scaleAni));
                            Render.drawTexture ((scaleWidth/2 - blackWidth/2 - 67.5f + 34) - (3f - ( 3f * scaleAni)), (scaleHeight/2 - blackWidth/2 + 143) - (3f - ( 3f * scaleAni)), blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, 1.25f - (0.25f * scaleAni));
                            Render.drawTexture ((scaleWidth/2 - blackWidth/2 - 67.5f + 34+ 34) - (3f - ( 3f * scaleAni)), (scaleHeight/2 - blackWidth/2 + 143) - (3f - ( 3f * scaleAni)), blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, 1.25f - (0.25f * scaleAni));
                            Render.drawTexture ((scaleWidth/2 - blackWidth/2 - 67.5f + 34 + 34 + 34) - (3f - ( 3f * scaleAni)), (scaleHeight/2 - blackWidth/2 + 143) - (3f - ( 3f * scaleAni)), blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, 1.25f - (0.25f * scaleAni));
                            Render.drawTexture ((scaleWidth/2 - blackWidth/2 - 67.5f + 34+ 34 + 34 + 33) - (3f - ( 3f * scaleAni)), (scaleHeight/2 - blackWidth/2 + 143) - (3f - ( 3f * scaleAni)), blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, 1.25f - (0.25f * scaleAni));


                            //Render.drawTexture ((scaleWidth/2 - blackWidth/2) - (3f - (3f* scaleAni) ), (scaleHeight/2 - blackWidth/2) - (3f - ( 3f * scaleAni)), blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, scaleAniPer);
//                        Render.drawTexture ((scaleWidth/2 - blackWidth/2) - (3f - (3* scaleAni) )/ scaleAniPer, (scaleHeight/2 - blackWidth/2) / scaleAniPer, blackWidth, blackWidth, 0, 0 ,1, 1, 1000, 1, scaleAniPer);

                    }

                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof BtnUrgent)
                        {
                            BtnUrgent btn = (BtnUrgent) button;
                            if(btn.m_BtnName.equals("뒤로가기")  || btn.m_BtnName.equals("수령"))
                            {
                                if(btn.m_BtnName.equals("수령"))
                                {
                                    if (!m_selectUrgentBtn.m_giveActive)
                                        btn.m_giveActive = false;
                                    else
                                        btn.m_giveActive = true;

                                }
                                button.enabled = true;
                                button.drawButton(mc, mouseX, mouseY, partialTicks);
                            }
                        }
                    }
                    if( mouseX > scaleWidth/2 - 76 && mouseX<= (scaleWidth/2 - 76) + 16 && mouseY > scaleHeight/2 + 135 && mouseY <= scaleHeight/2 + 135 + 16)
                        renderHoveredToolTip(stack5, mouseX, mouseY);
                    else if (mouseX > (scaleWidth/2 - 41) && mouseX <= (scaleWidth/2 - 41) + 16 && mouseY > scaleHeight/2 + 135 && mouseY <= scaleHeight/2 + 135 + 16)
                        renderHoveredToolTip(stack4, mouseX, mouseY);
                    else if (mouseX > (scaleWidth/2 - 7) && mouseX <= (scaleWidth/2 - 7) + 16 && mouseY > scaleHeight/2 + 135 && mouseY <= scaleHeight/2 + 135 + 16)
                        renderHoveredToolTip(stack3, mouseX, mouseY);
                    else if (mouseX > (scaleWidth/2 + 27) && mouseX <= (scaleWidth/2 + 27) + 16 && mouseY > scaleHeight/2 + 135 && mouseY <= scaleHeight/2 + 135 + 16)
                        renderHoveredToolTip(stack2, mouseX, mouseY);
                    else if (mouseX > (scaleWidth/2 + 60) && mouseX <= (scaleWidth/2 + 60) + 16 && mouseY > scaleHeight/2 + 135 && mouseY <= scaleHeight/2 + 135 + 16)
                        renderHoveredToolTip(stack1, mouseX, mouseY);

                }
                else
                {

                    //메일 리스트를 Render 하는 부분

                    float line_Str_Width = 536f/3f;
                    float line_Str_Height = 2f/3f;


                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\str\\line.png"));
                    Render.drawTexture (scaleWidth/2 - line_Str_Width/2, scaleHeight/2 - line_Str_Height/2 - 20, line_Str_Width, line_Str_Height, 0, 0 ,1, 1, 1, 1);


                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof BtnUrgent)
                        {
                            BtnUrgent btn = (BtnUrgent) button;
                            if(btn.m_BtnName.equals("뒤로가기") ||btn.m_BtnName.equals("슬라이더")  )
                            {
                                button.enabled = true;
                                button.drawButton(mc, mouseX, mouseY, partialTicks);
                            }
                        }
                    }

                    //스크롤을 위한 glScissor 사용
                    GL11.glPushMatrix();
                    {
                        int scaleFactor = scaled.getScaleFactor(); // 스케일 팩터 값

                        int scissorWidth = (int) (scaleWidth * scaleFactor); //실제 화면 너비 기준으로 따라감
                        int scissorHeight = (int) (scaleHeight * scaleFactor); //실제 화면 높이 기준으로 따라감

                        int scissorNotSignal_Width = 616; //실제 이미지 크기 기준으로 계산해야함
                        int scissorNotSignal_Height = 877; //실제 이미지 크기 기준으로 계산해야함

                        float scalePer = scaleFactor == 3 ? 1f : 1.725f;
                        GL11.glEnable(GL11.GL_SCISSOR_TEST);
                        GL11.glScissor((int) (scissorWidth/2 - scissorNotSignal_Width/2) + 50, 0, (int) scissorNotSignal_Width - 100, (int) ((int) scissorNotSignal_Height - (283f * scalePer)));


                        for(GuiButton button : buttonList)
                        {
                            if(button instanceof BtnUrgent)
                            {
                                BtnUrgent btn = (BtnUrgent) button;
                                if(btn.m_BtnName.equals("메일"))
                                {
                                    button.enabled = true;
                                    button.drawButton(mc, mouseX, mouseY, partialTicks);

                                    String title = btn.m_data.m_title;
                                    Render.drawStringScaleResizeByLeftWidth(title, btn.x + 6 , btn.y + btn.height/2f -7, 5, 0.9f, 1);

                                    String sender = "발신자: 운영자";
                                    Render.drawStringScaleResizeByLeftWidth(sender, btn.x + 6 , btn.y + btn.height/2f + 2.5f, 5, 0.75f, 1);

                                    String date = btn.m_data.m_date;
                                    Render.drawStringScaleResizeByLeftWidth(date, btn.x + btn.width/2f + 32, btn.y + btn.height/2f + 2.5f, 5, 0.75f, 1);
                                }
                                if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") && !btn.m_BtnName.equals("수령"))
                                {
                                    button.enabled = true;
                                    button.drawButton(mc, mouseX, mouseY, partialTicks);
                                }

                            }
                        }
                        int j = 0;
                        for(GuiButton button : buttonList)
                        {
                            if(button instanceof  BtnUrgent)
                            {
                                BtnUrgent btn = (BtnUrgent) button;
                                if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더")  && !btn.m_BtnName.equals("수령"))
                                {
                                    btn.y = (int) (scaleHeight/2 + m_contentSliderYPos + (j * 32));
                                    j++;
                                }
                            }
                        }
                        GL11.glDisable(GL11.GL_SCISSOR_TEST);
                    }
                    GL11.glPopMatrix();

                }



            }
            else if (variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.TRIBE))
            {


                float gall_Str_Width = 68f / 3f;
                float gall_Str_Height = 36f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\str\\부족.png"));
                Render.drawTexture (scaleWidth/2 - gall_Str_Width/2 - 67, scaleHeight/2 - gall_Str_Height/2 - 41, gall_Str_Width, gall_Str_Height, 0, 0 ,1, 1, 1, 1);


                float semiColumn_Width = 5f/3f;
                float semiColumn_Height = 24f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\_.png"));
                Render.drawTexture (scaleWidth/2 - semiColumn_Width/2 + 74, scaleHeight/2 - semiColumn_Height/2 - 40, semiColumn_Width, semiColumn_Height, 0, 0 ,1, 1, 1, 1);

                float line_Str_Width = 536f/3f;
                float line_Str_Height = 2f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\str\\line.png"));
                Render.drawTexture (scaleWidth/2 - line_Str_Width/2, scaleHeight/2 - line_Str_Height/2 - 20, line_Str_Width, line_Str_Height, 0, 0 ,1, 1, 1, 1);



                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnAutoMine)
                    {
                        BtnAutoMine btn = (BtnAutoMine) button;
                        button.enabled = true;
                        button.drawButton(mc, mouseX, mouseY, partialTicks);
                    }
                }

            }
            else if(variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.SHOP))
            {

                float gall_Str_Width = 68f / 3f;
                float gall_Str_Height = 40f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\title.png"));
                Render.drawTexture (scaleWidth/2 - gall_Str_Width/2 - 67, scaleHeight/2 - gall_Str_Height/2 - 41, gall_Str_Width, gall_Str_Height, 0, 0 ,1, 1, 1, 1);

                float subTextWidth = 428f / 3f;
                float subTextHeight = 14f / 3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\shop\\subtitle.png"));
                Render.drawTexture (scaleWidth/2 - subTextWidth/2 - 6, scaleHeight/2 - subTextHeight/2 - 28, subTextWidth, subTextHeight, 0, 0 ,1, 1, 1, 1);


                float line_Str_Width = 536f/3f;
                float line_Str_Height = 2f/3f;


                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\urgenttext\\str\\line.png"));
                Render.drawTexture (scaleWidth/2 - line_Str_Width/2, scaleHeight/2 - line_Str_Height/2 - 20, line_Str_Width, line_Str_Height, 0, 0 ,1, 1, 1, 1);


                //15
                GL11.glPushMatrix();
                {

                    int scaleFactor = scaled.getScaleFactor(); // 스케일 팩터 값

                    int scissorWidth = (int) (scaleWidth * scaleFactor); //실제 화면 너비 기준으로 따라감
                    int scissorHeight = (int) (scaleHeight * scaleFactor); //실제 화면 높이 기준으로 따라감



                    int scissorNotSignal_Width = 616; //실제 이미지 크기 기준으로 계산해야함
                    int scissorNotSignal_Height = 877; //실제 이미지 크기 기준으로 계산해야함

                    GL11.glEnable(GL11.GL_SCISSOR_TEST);

                    float scalePer = scaleFactor == 3 ? 1 : 1.725f;
                    GL11.glScissor((int) (scissorWidth/2 - scissorNotSignal_Width/2) + 50, 0, (int) scissorNotSignal_Width + 500, (int) ((int) scissorNotSignal_Height - (283 * scalePer)));

                    for(GuiButton button : buttonList)
                    {
                        if(button instanceof  BtnPhoneShop)
                        {
                            BtnPhoneShop btn = (BtnPhoneShop) button;
                            if(!btn.m_BtnName.equals("뒤로가기") && !btn.m_BtnName.equals("슬라이더") )
                            {
                                button.enabled = true;
                                button.drawButton(mc, mouseX, mouseY, partialTicks);
                            }

                        }
                    }

                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }
                GL11.glPopMatrix();


                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnPhoneShop)
                    {
                        BtnPhoneShop btn = (BtnPhoneShop) button;
                        if(btn.m_BtnName.equals("뒤로가기") || btn.m_BtnName.equals("슬라이더") )
                        {
                            button.enabled = true;
                            button.drawButton(mc, mouseX, mouseY, partialTicks);
                        }

                    }
                }

            }
            //갤러리 리스트에서 클릭 했을 경우
            if(m_clueOpenActive)
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\black.png"));
                Render.drawTexture (0, 0, scaleWidth, scaleHeight, 0, 0 ,1, 1, 10, 0.7f);

                float imageWidth = 1448f/3f;
                float imageHeight = 958f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\clue\\"+m_selectGallBtn.m_resourceName));

                Render.drawTexture (scaleWidth/2f-imageWidth/2f, scaleHeight/2f-imageHeight/2f, imageWidth, imageHeight, 0, 0 ,1, 1, 10, 1);

                for(GuiButton button : buttonList)
                {
                    if(button instanceof  BtnGall)
                    {
                        BtnGall btn = (BtnGall) button;
                        if(btn.m_BtnName.equals("뒤로가기_레이아웃") || btn.m_BtnName.equals("갤러리_front") || btn.m_BtnName.equals("갤러리_back") )
                        {
                            button.enabled = true;
                            button.drawButton(mc, mouseX, mouseY, partialTicks);
                        }

                    }
                }


                float filenameWidth = 696f/3f;
                float filenameHeight = 99f/3f;

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\layout\\filename.png"));
                Render.drawTexture (scaleWidth/2 - filenameWidth/2f, scaleHeight - filenameHeight, filenameWidth, filenameHeight, 0, 0 ,1, 1, 10, 1);
                Render.drawStringScaleResizeByMiddleWidth("§l"+m_selectGallBtn.m_Title, scaleWidth/2, scaleHeight - filenameHeight - fontRenderer.FONT_HEIGHT + filenameHeight/2f, 11, 1, 1);


            }


        }



//        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\긴급문자_success.png"));
//        Render.drawTexture (0, 0, scaleWidth, scaleHeight, 0, 0 ,1, 1, 5, 0.3f);




    }


    public void renderHoveredToolTip(ItemStack stack, int x, int y)
    {

        this.renderToolTip(stack, x, y);
    }

}
