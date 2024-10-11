package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.packet.*;
import com.doubleos.gw.util.InventoryUtils;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.variable.ShopData;
import com.doubleos.gw.variable.ShopItemData;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiShop extends GuiScreen
{

    Variable variable = Variable.Instance();

    ShopData itemData;

    public class BtnBuySlider extends GuiButton
    {
        public BtnBuySlider(int buttonId, int x, int y, String buttonText)
        {
            super(buttonId, x, y, buttonText);


            this.width = 7/3;
            this.height = 228/3;

        }


        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            float slotsWidth = 7f/3f;
            float slotsHeight = 228f/3f;

            ScaledResolution scaled = new ScaledResolution(mc);

            float scaleWidth = (float) scaled.getScaledWidth_double();
            float scaleHeight = (float) scaled.getScaledHeight_double();

            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\scrollbar.png"));
            Render.drawTexture(this.x, this.y, slotsWidth, slotsHeight, 0, 0, 1, 1, 1, 1);

        }
    }
    public class BtnBuySell extends GuiButton
    {

        public boolean selectActive = false;


        public BtnBuySell(int buttonId, int x, int y, String buttonText)
        {
            super(buttonId, x, y, buttonText);


            this.width = 124/3;
            this.height = 74/3;

        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            float slotsWidth = 124f/3f;
            float slotsHeight = 74f/3f;

            ScaledResolution scaled = new ScaledResolution(mc);

            float scaleWidth = (float) scaled.getScaledWidth_double();
            float scaleHeight = (float) scaled.getScaledHeight_double();

            String active = selectActive ? "_active" : "";
            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\"+this.displayString + active +".png"));
            Render.drawTexture(this.x, this.y, slotsWidth, slotsHeight, 0, 0, 1, 1, 1, 1);

        }
    }
    public class BtnShop extends GuiButton
    {


        int shopItemId = 0;

        public BtnShop(int buttonId, int x, int y, String buttonText, int shopItemId)
        {
            super(buttonId, x, y, buttonText);

            this.shopItemId = shopItemId;


            this.width = 200 / 3;
            this.height = 253 / 3;

        }


        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            float slotsWidth = 200f/3f;
            float slotsHeight = 253f/3f;

            ScaledResolution scaled = new ScaledResolution(mc);

            float scaleWidth = (float) scaled.getScaledWidth_double();
            float scaleHeight = (float) scaled.getScaledHeight_double();

            ShopItemData btnItemData = variable.m_hashShopData.get(shopName).itemDataList.get(this.shopItemId);

            ItemStack removeItem = itemData.shopMode.equals(ShopData.eShop_Mode.BUY) ? btnItemData.requestBuyItem: btnItemData.requestSellItem;
            int removeAmount = itemData.shopMode.equals(ShopData.eShop_Mode.BUY) ? btnItemData.requestItemBuyAmount : btnItemData.requestItemSellAmount;


            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\"+this.id+".png"));
            Render.drawTexture(this.x, this.y, slotsWidth, slotsHeight, 0, 0, 1, 1, 1, 1);

            GlStateManager.pushMatrix();
            {
                float scaleValue = 1.75f;
                GlStateManager.scale(scaleValue, scaleValue, 1);
                GlStateManager.translate(((this.x + slotsWidth/2f - 14f) / scaleValue), (this.y+25) / scaleValue, 2);
                RenderHelper.enableGUIStandardItemLighting();
                Render.renderHotbarItem(0, 0, partialTicks, mc.player, btnItemData.shopItem);
            }
            GlStateManager.popMatrix();

            float amountBackWidth = 95f/3f;
            float amountBackHeight = 40f/3f;

//            String amountText = "§l"+currentAmount + " / " + maxAmount;


            String amountText = String.format("§l%d / %d",btnItemData.dayBuyCurrentLimitCount, btnItemData.dayBuyMaxLimitCount);

            int fontWidth = fontRenderer.getStringWidth(amountText);

            float currentPer = (float) btnItemData.dayBuyCurrentLimitCount / (float)btnItemData.dayBuyMaxLimitCount;

            String big = "2";
            if(btnItemData.dayBuyMaxLimitCount >= 100)
            {
                big = "3";
                amountBackWidth = 114f/3f;
                amountBackHeight = 40f/3f;

            }

            if(currentPer >= 0.5f) // 흰색
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\"+big+"수량_back.png"));
                Render.drawTexture(this.x + this.width/2f - amountBackWidth/2f, this.y + 5, amountBackWidth, amountBackHeight, 0, 0, 1, 1, 1, 1);
                //Render.drawStringScaleResizeByLeftWidth(amountText, this.x + 17 + amountBackWidth/2f - fontWidth*0.45f /2f, this.y + 8, 2, 0.45f, 1);
                Render.drawStringScaleResizeByMiddleWidth(amountText, this.x + this.width/2f, this.y + 5 + amountBackHeight/2f - 2f, 2, 0.55f, 1);
            }
            else if(currentPer >= 0.01f) // 빨간색
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\"+big+"수량_soild.png"));
                Render.drawTexture(this.x + this.width/2f - amountBackWidth/2f, this.y + 5, amountBackWidth, amountBackHeight, 0, 0, 1, 1, 1, 1);
                Render.drawStringScaleResizeByMiddleWidth(amountText, this.x + this.width/2f, this.y + 5 + amountBackHeight/2f - 2f, 2, 0.55f, 1);
            }
            else // 회색
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\"+big+"수량_sold.png"));
                Render.drawTexture(this.x + this.width/2f - amountBackWidth/2f, this.y + 5, amountBackWidth, amountBackHeight, 0, 0, 1, 1, 1, 1);
                Render.drawStringScaleResizeByMiddleWidth(amountText, this.x + this.width/2f, this.y + 5 + amountBackHeight/2f - 2f, 2, 0.55f, -1);
            }

            String itemName = String.format("[%s§0]", btnItemData.shopItem.getDisplayName().replace("§f", "§0"));
            Render.drawStringScaleResizeByMiddleWidth(itemName, this.x + this.width/2f, this.y + this.height - 24, 2, 0.8f, 1);

            int itemNameSize = fontRenderer.getStringWidth(itemName);


            if(itemData.shopMode.equals(ShopData.eShop_Mode.BUY))
            {
                if(btnItemData.dayBuyCurrentLimitCount == 0)
                {

                    Render.drawStringScaleResizeByMiddleWidth("§l품절", this.x + this.width/2f, this.y + this.height - 12, 2, 0.8f, 1);
                }
                else
                {
                    GlStateManager.pushMatrix();
                    {
                        float scaleValue = 0.6f;
                        GlStateManager.scale(scaleValue, scaleValue, 1f);
                        GlStateManager.translate(((this.x + this.width/2f - 12f) / scaleValue), (this.y + this.height - 14) / scaleValue, 4);
                        RenderHelper.enableGUIStandardItemLighting();
                        Render.renderHotbarItem(0, 0, partialTicks, mc.player, removeItem);
                    }
                    GlStateManager.popMatrix();

                    float removeAmountWidth = fontRenderer.getStringWidth(String.format("§l%02d",removeAmount)) * 0.65f;
                    Render.drawStringScaleResizeByMiddleWidth(String.format("§l%02d",removeAmount), (this.x + this.width/2f - 10f) + removeAmountWidth/2f + 10, this.y + this.height - 12, 5, 0.7f, 1);
                }

            }
            else
            {

                if(btnItemData.requestSellItem.getItem().equals(ItemBlock.getItemFromBlock(Blocks.BEDROCK)))
                {

                    Render.drawStringScaleResizeByMiddleWidth(String.format("§l판매불가"), this.x + this.width/2f, this.y + this.height - 13, 5, 0.7f, 1);
                }
                else
                {
                    GlStateManager.pushMatrix();
                    {
                        float scaleValue = 0.6f;
                        GlStateManager.scale(scaleValue, scaleValue, 1f);
                        GlStateManager.translate(((this.x + this.width/2f - 12f) / scaleValue), (this.y + this.height - 14) / scaleValue, 4);
                        RenderHelper.enableGUIStandardItemLighting();
                        Render.renderHotbarItem(0, 0, partialTicks, mc.player, removeItem);
                    }
                    GlStateManager.popMatrix();

                    float removeAmountWidth = fontRenderer.getStringWidth(String.format("§l%02d",removeAmount)) * 0.65f;
                    Render.drawStringScaleResizeByMiddleWidth(String.format("§l%02d",removeAmount), (this.x + this.width/2f - 10f) + removeAmountWidth/2f + 10, this.y + this.height - 12, 5, 0.7f, 1);

                }
            }


            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";


            float loreBackWidth = 273f/3f;
            float loreBackHegit = 200f/3f;
            if(active.equals("_active"))
            {

                drawText(btnItemData.shopItem, (int) (scaleWidth/2 - loreBackWidth/2f + 192f), (int) (scaleHeight/2 - loreBackHegit/2 - 21), 2);

            }


        }
    }

    String shopName = "";
    public GuiShop(String name)
    {
        shopName = name;
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

        float loreLeftWidth = 31f/3f;
        float loreLeftHeight = 118f/3f;

        float loreRightWidth = 180f/3f;
        float loreRightHeight = 118f/3f;



        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\lore_left.png"));
        Render.drawTexture(scaleWidth/2 - loreLeftWidth/2f + 175f, scaleHeight/2 - loreLeftHeight/2 - 21, loreLeftWidth, loreLeftHeight, 0, 0, 1, 1, z, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\lore_right.png"));
        Render.drawTexture(scaleWidth/2 - loreLeftWidth/2f + 175f+loreLeftWidth, scaleHeight/2 - loreLeftHeight/2 - 21, loreRightWidth * widthCurr, loreRightHeight, 0, 0, 1, 1, z, 1);


//        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\lore.png"));
//        Render.drawTexture(scaleWidth/2 - loreBackWidth/2f + 192f, scaleHeight/2 - loreBackHegit/2 - 21, loreBackWidth, loreBackHegit, 0, 0, 1, 1, 1, 1);


        Render.drawTextList(m_text, (int) (scaleWidth/2 - loreLeftWidth/2f + 172f), y + 40, fontRenderer, z+5, 0.8f);

        //Render.drawHoveringText(m_text, x, y, fontRenderer, z);

    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 100)
        {
            itemData.shopMode = ShopData.eShop_Mode.BUY;
            for(GuiButton button1 : buttonList)
            {
                if(button1 instanceof BtnBuySell)
                {
                    BtnBuySell btn = (BtnBuySell) button1;
                    btn.selectActive = false;
                }
            }
            BtnBuySell btn = (BtnBuySell) button;
            btn.selectActive = true;
        }
        else if (button.id == 101)
        {
            itemData.shopMode = ShopData.eShop_Mode.SELL;
            for(GuiButton button1 : buttonList)
            {
                if(button1 instanceof BtnBuySell)
                {
                    BtnBuySell btn = (BtnBuySell) button1;
                    btn.selectActive = false;
                }
            }
            BtnBuySell btn = (BtnBuySell) button;
            btn.selectActive = true;
        }
        else if (button instanceof BtnShop)
        {
            ShopItemData data = itemData.itemDataList.get(button.id);
            if (itemData.shopMode.equals(ShopData.eShop_Mode.BUY)) // 구매
            {
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
                            if (!InventoryUtils.canAddItemToInventory(mc.player, data.shopItem.copy(), 64))
                            {
                                mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f인벤토리에 빈 공간이 부족합니다"));
                                return;
                            }
                            Packet.networkWrapper.sendToServer(new SPacketItemRemove(data.requestBuyItem.copy(), 64));
                            ItemStack fullStack = data.shopItem.copy();
                            fullStack.setCount(64);
                            Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                            //data.dayBuyCurrentLimitCount -= 64;
                            Packet.networkWrapper.sendToServer(new SPacketShopDataItemRemove(data.shopItemId, -1*64, shopName));

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

                    if(data.requestItemBuyAmount <= stackCount)
                    {
                        if (!InventoryUtils.canAddItemToInventory(mc.player, data.shopItem.copy(), 1))
                        {
                            mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f인벤토리에 빈 공간이 부족합니다"));
                            return;
                        }
                        Packet.networkWrapper.sendToServer(new SPacketItemRemove(data.requestBuyItem.copy(), data.requestItemBuyAmount));
                        ItemStack fullStack = data.shopItem.copy();
                        fullStack.setCount(1);
                        Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                        //data.dayBuyCurrentLimitCount -= 1;
                        Packet.networkWrapper.sendToServer(new SPacketShopDataItemRemove(data.shopItemId, -1, shopName));
                        mc.player.sendMessage(new TextComponentString("§l[地上世界] §f"+data.shopItem.getDisplayName() +" 아이템을 1개 구매 하였습니다."));
                    }
                    else
                    {
                        mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f요구 아이템이 부족하여 구매가 불가합니다."));
                    }

                }

                Packet.networkWrapper.sendToServer(new SPacketDefaultShopDataReload(shopName));
            }
            else //판매
            {
                int stackCount = 0;
                for (ItemStack stack : mc.player.inventory.mainInventory)
                {
                    if (stack.getItem().equals(data.shopItem.getItem()))
                        stackCount += stack.getCount();
                }

                if (Keyboard.isKeyDown(42)) //쉬프트 클릭
                {

                    if(64 <= stackCount)
                    {
                        if (!InventoryUtils.canAddItemToInventory(mc.player, data.requestSellItem.copy(), 64))
                        {
                            mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f인벤토리에 빈 공간이 부족합니다"));
                            return;
                        }
                        Packet.networkWrapper.sendToServer(new SPacketItemRemove(data.shopItem.copy(), 64));
                        ItemStack fullStack = data.requestSellItem.copy();
                        fullStack.setCount(64);
                        int sellItem = data.requestItemSellAmount * stackCount;
                        for(int i = sellItem; 0 <= i/64; i-=64)
                        {
                            Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                        }
                        fullStack.setCount(sellItem);
                        Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                        //data.dayBuyCurrentLimitCount += 64;
                        Packet.networkWrapper.sendToServer(new SPacketShopDataItemRemove(data.shopItemId, 64, shopName));
                        mc.player.sendMessage(new TextComponentString("§l[地上世界] §f"+data.shopItem.getDisplayName() +" 아이템을 64개 판매 하였습니다."));
                    }
                    else
                    {
                        mc.player.sendMessage(new TextComponentString("§l[地上世界] §f판매 아이템이 64개이상 존재 하지 않습니다.") );
                    }

                }
                else
                {
                    if(1 <= stackCount)
                    {
                        if (!InventoryUtils.canAddItemToInventory(mc.player, data.requestSellItem.copy(), 1))
                        {
                            mc.player.sendMessage(new TextComponentString( "§l[地上世界] §f인벤토리에 빈 공간이 부족합니다"));
                            return;
                        }
                        Packet.networkWrapper.sendToServer(new SPacketItemRemove(data.shopItem.copy(), 1));
                        ItemStack fullStack = data.requestSellItem.copy();
                        fullStack.setCount(data.requestItemSellAmount);
                        Packet.networkWrapper.sendToServer(new SPacketItemAdd(fullStack.copy()));
                        Packet.networkWrapper.sendToServer(new SPacketShopDataItemRemove(data.shopItemId, 1, shopName));
                        //data.dayBuyCurrentLimitCount -= 1;
                        mc.player.sendMessage(new TextComponentString("§l[地上世界] §f"+data.shopItem.getDisplayName() +" 아이템을 판매 하였습니다."));
                    }
                    else
                    {
                        mc.player.sendMessage(new TextComponentString("§l[地上世界] §f판매할 아이템이 부족합니다.") );
                    }
                }

                Packet.networkWrapper.sendToServer(new SPacketDefaultShopDataReload(shopName));

            }


        }
    }

    @Override
    public void initGui()
    {
        ScaledResolution scaled = new ScaledResolution(mc);

        int scaleWidth = scaled.getScaledWidth();
        int scaleHeight = scaled.getScaledHeight();

        buttonList.clear();

        int btnId = 0;

        int[] widths = new int[]{-149, -72, 4, 81};
        int[] heights = new int[]{-71, 25, 121, 217};


        itemData = variable.m_hashShopData.get(shopName);

        itemData.shopMode = ShopData.eShop_Mode.BUY;
        int count = 0;

        for(int i = 0; i<itemData.itemDataList.size(); i++)
        {
            int yCount = i / 4;
            //ShopItemData data = itemData.itemDataList.get(i);

            buttonList.add(new BtnShop(btnId++, scaleWidth/2 + widths[count], scaleHeight/2 + heights[yCount], "", i));
            uiMaxScrollYPos = scaleHeight/2 + heights[1];
            count += 1;
            if(count >= 4)
                count = 0;
        }
        uiMaxScrollYPos = scaleHeight/2 + heights[1];

//        for(int i = 0; i<16; i++)
//        {
//            int yCount = i / 4;
//            //ShopItemData data = itemData.itemDataList.get(i);
//
//            buttonList.add(new BtnShop(btnId++, scaleWidth/2 + widths[count], scaleHeight/2 + heights[yCount], "", 0));
//
//            count += 1;
//            if(count >= 4)
//                count = 0;
//        }


        uiMinScorllYPos = scaleHeight/2 + heights[0];

        //maxYPos = scaleHeight/2 + heights[3];

//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 - 124, scaleHeight/2 - 55, "",ModItems.drink_water, 15, 15, Items.DIAMOND, 1));
//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 - 60, scaleHeight/2 - 55, "",ModItems.hotpack, 15, 10, Items.GOLD_INGOT, 12));
//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 + 4, scaleHeight/2 - 55, "",ModItems.spam, 15, 5, Items.IRON_INGOT, 3));
//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 + 69, scaleHeight/2 - 55, "",ModItems.cloth, 15, 3, Items.EMERALD, 15));
//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 - 124, scaleHeight/2 + 28, "",ModItems.gas_ticket, 15, 0, Items.REDSTONE, 5));
//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 - 60, scaleHeight/2 + 28, "",ModItems.plastic, 15, 6, Items.BLAZE_ROD, 1));
//        buttonList.add(new BtnShop(btnId++, scaleWidth/2 + 4, scaleHeight/2 + 28, "",ModItems.bloodsitck, 15, 4, Items.COOKIE, 21));

        BtnBuySell btnBuySell = new BtnBuySell(100, scaleWidth/2 - 214, scaleHeight/2 - 72, "구매");
        btnBuySell.selectActive = true;
        buttonList.add(btnBuySell);
        buttonList.add(new BtnBuySell(101, scaleWidth/2 - 214, scaleHeight/2 - 44, "판매"));

        float shopBackgroundWidth = 986f/3f;
        float shopBackgroundHeight = 740f/3f;

        int slotsWidth = 7/3;
        int slotsHeight = 228/3;

        buttonList.add(new BtnBuySlider(999, (int) (scaleWidth/2 + shopBackgroundWidth/2 - slotsWidth - 5),buttonList.get(0).y, ""));


    }

    int uiMinScorllYPos;
    int uiMaxScrollYPos;
    float maxYPosPer = 0;

    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        ScaledResolution scaled = new ScaledResolution(mc);

        if (i != 0)
        {
            if (i < 1)// 휠 내렸을 때
            {

                for(GuiButton button : buttonList)
                {
                    if(button.id == 999 && itemData.itemDataList.size() > 8)
                    {

                        if(button.y + 5 <= uiMaxScrollYPos)
                        {
                            maxYPosPer = (float)(button.y - 109f) / ((float)uiMaxScrollYPos - 109f);
                            button.y += 5;
                            for(GuiButton button2 : buttonList)
                            {
                                if(button2 instanceof BtnShop)
                                {
                                    int[] yHeight = new int[]{0 ,5, 5, 10};
                                    int count = 1;
                                    if(itemData.itemDataList.size() > 8 && itemData.itemDataList.size() <= 12)
                                        count = 3;
                                    else if(itemData.itemDataList.size() > 12)
                                    {
                                        count = 4;
                                    }
                                    button2.y -= yHeight[count-1];
                                    //2줄 5 4줄 10
                                }

                            }
                        }
                    }
                }


            }
            else
            {
                for(GuiButton button : buttonList)
                {
                    if(button.id == 999 && itemData.itemDataList.size() > 8)
                    {

                        if(button.y - 5  >= uiMinScorllYPos)
                        {
                            maxYPosPer = (float)(button.y - 109f) / ((float)uiMaxScrollYPos - 109f);
                            button.y -= 5;

                            for(GuiButton button2 : buttonList)
                            {
                                if(button2 instanceof BtnShop)
                                {
                                    int[] yHeight = new int[]{0 ,5, 5, 10};
                                    int count = 1;
                                    if(itemData.itemDataList.size() > 8 && itemData.itemDataList.size() <= 12)
                                        count = 3;
                                    else if(itemData.itemDataList.size() > 12)
                                    {
                                        count = 4;
                                    }
                                    button2.y += yHeight[count-1];
                                }

                            }
                        }
                    }
                }
            }

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        ScaledResolution scaled = new ScaledResolution(mc);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        float shopBackgroundWidth = 986f/3f;
        float shopBackgroundHeight = 740f/3f;


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\background.png"));
        Render.drawTexture(scaleWidth/2 - shopBackgroundWidth/2, scaleHeight/2 - shopBackgroundHeight/2 + 5, shopBackgroundWidth, shopBackgroundHeight, 0, 0, 1, 1, 1, 1);


        for(GuiButton button : buttonList)
        {
            if(! (button instanceof BtnShop))
                button.drawButton(mc, mouseX, mouseY, partialTicks);
        }

        GL11.glPushMatrix();
        {
            int scaleFactor = scaled.getScaleFactor(); // 스케일 팩터 값

            int scissorWidth = (int) (scaleWidth * scaleFactor); //실제 화면 너비 기준으로 따라감
            int scissorHeight = (int) (scaleHeight * scaleFactor); //실제 화면 높이 기준으로 따라감

            float scalePer = scaleFactor == 3 ? 1f : 0.666667f;

            int scissorNotSignal_Width = (int) (986 * scalePer); //실제 이미지 크기 기준으로 계산해야함
            int scissorNotSignal_Height = (int) (740 * scalePer); //실제 이미지 크기 기준으로 계산해야함


            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            //GL11.glScissor((int) ((int) (scissorWidth/2 - scissorNotSignal_Width/2 + 20 * scalePer)), (int) ((scissorHeight/2 - scissorNotSignal_Height/2 + 20 * scalePer)), (int) ((int) (scissorWidth)), (int) ((int) ((int) scissorNotSignal_Height - 170f * scalePer)));

            GL11.glScissor((int) ((int) (scissorWidth/2 - scissorNotSignal_Width/2 + 20)), (int) ((scissorHeight/2 - scissorNotSignal_Height/2 + 20)), (int) ((int) (scissorWidth)), (int) ((int) ( scissorHeight/2 + scissorNotSignal_Height/2 - 345f * scalePer)));

            for(GuiButton button : buttonList)
            {
                if(button instanceof BtnShop)
                    button.drawButton(mc, mouseX, mouseY, partialTicks);
            }

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        GL11.glPopMatrix();

        //mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\shop\\success.png"));
        //Render.drawTexture(0, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1, 0.3f);


    }
}
