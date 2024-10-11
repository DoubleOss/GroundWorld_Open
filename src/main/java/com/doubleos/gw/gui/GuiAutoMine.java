package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.automine.table.AutoMineItemTable;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.packet.Packet;
import com.doubleos.gw.packet.SPacketAddItemAmount;
import com.doubleos.gw.packet.SPacketAreaitemRemove;
import com.doubleos.gw.packet.SPacketItemAdd;
import com.doubleos.gw.util.InventoryUtils;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.variable.GroundItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class GuiAutoMine extends GuiScreen
{


    class BtnGuiAutoMine extends GuiButton
    {

        public String m_BtnName = "";

        float m_btn_ImageWidth = 600 / 2f;
        float m_btn_ImageHeight = 51 / 2f;

        public boolean m_select = false;


        float m_bankOilPer = 100;

        int m_playerOilCan = 0;
        public BtnGuiAutoMine(int buttonId, int x, int y, String buttonText)
        {
            super(buttonId, x, y, buttonText);

            m_btn_ImageWidth = 106f/3f;
            m_btn_ImageHeight = 35f/3f;

            int m_btn_Width = (int) (106f/3f);
            int m_btn_Height = (int) (35f/3f);


            this.width = m_btn_Width;
            this.height = m_btn_Height;
        }

        public BtnGuiAutoMine(int buttonId, int x, int y, String buttonText, String memberName)
        {
            super(buttonId, x, y, buttonText);

            m_BtnName = memberName;

            m_btn_ImageWidth = 106f/3f;
            m_btn_ImageHeight = 35f/3f;

            int m_btn_Width = (int) (106f/3f);
            int m_btn_Height = (int) (35f/3f);


            this.width = m_btn_Width;
            this.height = m_btn_Height;

        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";
            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/btn.png"));
                Render.drawTexture(this.x, this.y, m_btn_ImageWidth, m_btn_ImageHeight, 0, 0, 1, 1, 1, 1, 0.4f, 0.4f, 0.4f);

            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/btn.png"));
                Render.drawTexture(this.x, this.y, m_btn_ImageWidth, m_btn_ImageHeight, 0, 0, 1, 1, 1, 1);

            }

        }
    }

    public GuiAutoMine(int npcResouceNumber, int npcName)
    {
        this.npcResouceNumber = npcResouceNumber;
        this.npcRandomName = npcName;
        ownerNpc = "주인이 없음";

        for( AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
        {
            if(area.entityNumberName == this.npcRandomName)
            {
                ownerNpc = area.ownerName;
                this.area = area;
            }
        }

    }

    int npcResouceNumber = 1;

    int npcRandomName = 0;

    String ownerNpc = "";

    AutoMineArea area = null;

    boolean buttonGive = false;
    @Override
    public void initGui()
    {
        buttonList.clear();

        ScaledResolution scale = new ScaledResolution(mc);
        int scaleWidth = scale.getScaledWidth();
        int scaleHeight = scale.getScaledHeight();

        int btnId = 0;

        int m_btn_ImageWidth = 106/3;
        int m_btn_ImageHeight = 35/3;

        buttonList.add(new BtnGuiAutoMine(btnId++, scaleWidth/2 - m_btn_ImageWidth/2 + 47, scaleHeight/2 - m_btn_ImageHeight/2 + 59, ""));

    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    protected void renderToolTip(ItemStack stack, int x, int y) {
        super.renderToolTip(stack, x, y);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {


        if(area == null)
            return;
        drawDefaultBackground();
        for( AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
        {
            if(area.entityNumberName == this.npcRandomName)
            {
                this.area = area;
            }
        }


        ScaledResolution scale = new ScaledResolution(mc);

        int scaleFactor = scale.getScaleFactor();

        float scaleWidth = (float) scale.getScaledWidth_double();
        float scaleHeight = (float) scale.getScaledHeight_double();

        float npcBackWidth = 1110f/3f;
        float npcBackHeight = 873f/3f;

        float durIconWidth = 36f/3f;

        float npcBackgroundPosWidth = scaleWidth/2f - npcBackWidth/2f;
        float npcBackgroundPosHeight = scaleHeight/2f - npcBackHeight/2f;


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/npc/"+(this.npcResouceNumber+1)+".png"));
        Render.drawTexture(npcBackgroundPosWidth, npcBackgroundPosHeight, npcBackWidth, npcBackHeight, 0, 0 , 1, 1, 1 ,1f);


        float dur_iconPosX = npcBackgroundPosWidth + npcBackWidth/2f - 7f;

        float dur_iconPosY = npcBackgroundPosHeight + npcBackHeight/2f - 43f;

        float oil_iconPosY = npcBackgroundPosHeight + npcBackHeight/2f - 28f;


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/dur_icon.png"));
        Render.drawTexture(dur_iconPosX, dur_iconPosY, durIconWidth, durIconWidth, 0, 0 , 1, 1, 1 ,1f);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/oil_icon.png"));
        Render.drawTexture(dur_iconPosX, oil_iconPosY, durIconWidth, durIconWidth, 0, 0 , 1, 1, 1 ,1f);


        float npcNamePosX = npcBackgroundPosWidth + npcBackWidth/2f - 7;
        float npcNamePosY = npcBackgroundPosHeight + npcBackHeight/2f - 65f;

        int npcNameWidth = (int) (fontRenderer.getStringWidth("§l"+String.valueOf(npcRandomName)) * 1.3f);


        Render.drawStringScaleResizeByLeftWidth("§l"+String.valueOf(npcRandomName), npcNamePosX,
                npcNamePosY, 2, 1.3f, 1);


        Render.drawStringScaleResizeByLeftWidth(ownerNpc+"의 로봇", npcNamePosX + npcNameWidth + 5,
                npcBackgroundPosHeight + npcBackHeight/2f - 65f + 4f, 2, 0.7f, 1);

        float durImageWidth = 253f/3f;
        float durImageHeight = 35f/3f;

        float barImageWidth = 256f/3f;
        float barImageHeight = 36f/3f;


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/oil_back.png"));
        Render.drawTexture(dur_iconPosX + 14, oil_iconPosY + 1f, durImageWidth, durImageHeight, 0, 0 , 1, 1, 1 ,1f);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/dur_back.png"));
        Render.drawTexture(dur_iconPosX + 14, dur_iconPosY + 1f, durImageWidth, durImageHeight, 0, 0 , 1, 1, 1 ,1f);


        float durabilityPer = (float) area.m_currentDurability / area.m_maxDurability;
        float oilPer = (float) area.m_currentOil / area.m_maxCurrentOil;



        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/oil.png"));
        Render.drawXLinearTexture(dur_iconPosX + 14, oil_iconPosY + 1f, barImageWidth, barImageHeight, oilPer * 100, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/dur.png"));
        Render.drawXLinearTexture(dur_iconPosX + 14, dur_iconPosY + 1f, barImageWidth, barImageHeight, durabilityPer * 100, 1, 1);

        Render.drawStringScaleResizeByMiddleWidth((int)(oilPer*100)+"%", dur_iconPosX + barImageWidth + 23, oil_iconPosY + 4f, 2, 0.65f, 1);

        Render.drawStringScaleResizeByMiddleWidth((int)(durabilityPer*100)+"%", dur_iconPosX + barImageWidth + 23, dur_iconPosY + 4f, 2, 0.65f, 1);



        float slotWidth = 74f/3f;



        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f + 10, scaleHeight/2 - slotWidth/2f + 10, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f + 10 + 25, scaleHeight/2 - slotWidth/2f + 10, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f + 10 + 25 + 25, scaleHeight/2 - slotWidth/2f + 10, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f + 10 + 25 + 25 + 25, scaleHeight/2 - slotWidth/2f + 10, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f - 2, scaleHeight/2 - slotWidth/2f + 10 + 26, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f - 2 + 25, scaleHeight/2 - slotWidth/2f + 10 + 26, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f - 2 + 25 + 25, scaleHeight/2 - slotWidth/2f + 10 + 26, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f - 2 + 25 + 25 + 25, scaleHeight/2 - slotWidth/2f + 10 + 26, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);


        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/automine/slot.png"));
        Render.drawTexture(scaleWidth/2 - slotWidth/2f - 2 + 25 + 25 + 25 + 25, scaleHeight/2 - slotWidth/2f + 10 + 26, slotWidth, slotWidth, 0, 0, 1, 1, 1, 1);


        int[] itemWidths = new int[]{16, 41, 66, 91, 4, 29, 54, 79, 104};
        //텍스트 간격은 기존 itemWidths 의 +14만큼

        int[] itemHeights = new int[]{15, 41};
        //텍스트 간격은 기존 itemHeights 의 +9 만큼




        int i = 0;
        int j = 0;

        for (Map.Entry<AutoMineItemTable.ITEM, Integer> entry : area.hashItemToCount.entrySet())
        {
            //System.out.println(entry.getKey() + ": " + entry.getValue());
            j = (i / 4) < 1 ? 0 : 1;
            if(entry.getValue() != 0)
            {
                GlStateManager.pushMatrix();
                {
                    ItemStack stack = area.getItem(entry.getKey());
                    float itemScale = 0.9f;
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.scale(itemScale, itemScale, 1);

                    Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f + itemWidths[i]) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + itemHeights[j]) / itemScale), partialTicks, mc.player, stack);
                }
                GlStateManager.popMatrix();
                Render.drawStringScaleResizeByRightWidth(String.format("%d", entry.getValue()), scaleWidth/2 - slotWidth/2f + itemWidths[i] + 14, scaleHeight/2 - slotWidth/2 + itemHeights[j] + 9, 1000, 0.8f, -1);

                i++;
            }

        }



//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f + 10 + 6) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 15) / itemScale), partialTicks, mc.player, ItemBlock.getItemFromBlock(Blocks.COBBLESTONE).getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_stone), scaleWidth/2 - slotWidth/2f + 10 + 20, scaleHeight/2 - slotWidth/2 + 10 + 14, 1000, 0.8f, -1);
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f + 10 + 25 + 6) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 15) / itemScale), partialTicks, mc.player, Items.COAL.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_coal), scaleWidth/2 - slotWidth/2f + 10 + 25 + 20, scaleHeight/2 - slotWidth/2 + 10 + 14, 1000, 0.8f, -1);
//
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f + 10 + 25 + 25 + 6) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 15) / itemScale), partialTicks, mc.player, Items.IRON_INGOT.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_iron), scaleWidth/2 - slotWidth/2f + 10 + 25 + 25+ 20, scaleHeight/2 - slotWidth/2 + 10 + 14, 1000, 0.8f, -1);
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f + 10 + 25 + 25 + 25+ 6) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 15) / itemScale), partialTicks, mc.player, Items.GOLD_INGOT.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_gold), scaleWidth/2 - slotWidth/2f + 10 + 25 + 25 + 25 + 20, scaleHeight/2 - slotWidth/2 + 10 + 14, 1000, 0.8f, -1);
//
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f - 2 + 6) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 10 + 26 + 5) / itemScale), partialTicks, mc.player, ModItems.aluminum.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_aluminum), scaleWidth/2 - slotWidth/2f - 2 + 20, (scaleHeight/2 - slotWidth/2f + 10 + 26 + 14), 1000, 0.8f, -1);
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f - 2 + 6 + 25) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 10 + 26 + 5) / itemScale), partialTicks, mc.player, Items.DIAMOND.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_diamond), scaleWidth/2 - slotWidth/2f - 2 + 25 + 20, (scaleHeight/2 - slotWidth/2f + 10 + 26 + 14), 1000, 0.8f, -1);
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f - 2 + 6 + 25 + 25) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 10 + 26 + 5) / itemScale), partialTicks, mc.player, Items.EMERALD.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_emerald), scaleWidth/2 - slotWidth/2f - 2 + 25 + 25+ 20, (scaleHeight/2 - slotWidth/2f + 10 + 26 + 14), 1000, 0.8f, -1);
//
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f - 2 + 6 + 25 + 25 + 25) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 10 + 26 + 5) / itemScale), partialTicks, mc.player, Items.REDSTONE.getDefaultInstance());
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_redstone), scaleWidth/2 - slotWidth/2f - 2 + 25 + 25 + 25 + 20, (scaleHeight/2 - slotWidth/2f + 10 + 26 + 14), 1000, 0.8f, -1);
//
//
//        GlStateManager.pushMatrix();
//        {
//            float itemScale = 0.9f;
//            GlStateManager.scale(itemScale, itemScale, 1);
//
//            Render.renderHotbarItem((int)((scaleWidth/2 - slotWidth/2f - 2 + 6 + 25 + 25+ 25 + 25) / itemScale), (int)((scaleHeight/2 - slotWidth/2f + 10 + 26 + 5) / itemScale), partialTicks, mc.player, new ItemStack(Items.DYE, 1, 4));
//
//        }
//        GlStateManager.popMatrix();
//        Render.drawStringScaleResizeByRightWidth(String.format("%d", area.m_lapis), scaleWidth/2 - slotWidth/2f - 2 + 25 + 25 + 25 + 25  + 20, (scaleHeight/2 - slotWidth/2f + 10 + 26 + 14), 1000, 0.8f, -1);





        buttonList.get(0).drawButton(mc, mouseX, mouseY, partialTicks);



    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 0)
        {
            if (! buttonGive)
            {
                if(mc.player.getName().equals(this.ownerNpc))
                {

                    //mc.player.sendMessage(new TextComponentString("§l"+area.entityNumberName+ "§f아이템 지급중입니다..."));
                    mc.player.closeScreen();
                    buttonGive = true;

                    ItemStack stone = new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), area.m_stone);
                    ItemStack coal = new ItemStack(Items.REEDS, area.m_coal);
                    coal.setStackDisplayName("§f석탄");
                    ItemStack iron = new ItemStack(Items.IRON_INGOT, area.m_iron);
                    ItemStack gold = new ItemStack(Items.GOLD_INGOT, area.m_gold);
                    ItemStack al = GroundItemStack.ALUMINUM.copy();
                    al.setCount(area.m_aluminum);
                    ItemStack diamond = new ItemStack(Items.DIAMOND, area.m_diamond);
                    ItemStack emer = new ItemStack(Items.EMERALD, area.m_emerald);
                    ItemStack redstone = new ItemStack(Items.REDSTONE, area.m_redstone);
                    ItemStack lapis = new ItemStack(Items.DYE, area.m_lapis, 4);
                    ItemStack hardtack = GroundItemStack.HARDTACK.copy();
                    hardtack.setCount(area.m_hardtack);
                    ItemStack stick = new ItemStack(Items.STICK, area.m_stick);
                    ItemStack cloth = GroundItemStack.CLOTH.copy();
                    cloth.setCount(area.m_cloth);
                    ItemStack plastic = GroundItemStack.PLASTIC.copy();
                    plastic.setCount(area.plastic);

                    ItemStack gear = GroundItemStack.GEAR.copy();
                    gear.setCount(area.m_gear);

                    ItemStack robotCore = GroundItemStack.ROBOT_CORE.copy();
                    robotCore.setCount(area.m_robotCore);

                    ItemStack vitamin = GroundItemStack.VITAMIN.copy();
                    vitamin.setCount(area.m_vitamin);


                    long tick = 0l;


                    //(int m_stone, int m_coal, int m_iron, int m_gold, int m_aluminum, int m_diamond, int m_emerald, int m_redstone, int
                    // m_lapis, int m_hardtack, int m_stick, int m_cloth, int plastic, int m_gear, int m_robotCore, int m_vitamin)


                    boolean[] giveStoneCheck = {InventoryUtils.canAddItemToInventory(mc.player, stone, area.m_stone)};


                    if(giveStoneCheck[0])
                    {
                        stone.setCount(1);
                        Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(stone, area.m_stone));
                    }

                    final boolean[] giveCoalCheck = {InventoryUtils.canAddItemToInventory(mc.player, coal, area.m_stone)};
                    Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveCoalCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, coal, area.m_coal);
                            if(giveCoalCheck[0])
                            {
                                coal.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(coal, area.m_coal));
                            }

                        }
                    },tick+=20);


                    final boolean[] giveIronCheck = {InventoryUtils.canAddItemToInventory(mc.player, iron, area.m_iron)};

                    Timer timer3 = new Timer();
                    timer3.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveIronCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, iron, area.m_iron);
                            if(giveIronCheck[0])
                            {
                                iron.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(iron, area.m_iron));
                            }

                        }
                    },tick+=20);


                    final boolean[] giveGoldCheck = {InventoryUtils.canAddItemToInventory(mc.player, gold, area.m_gold)};


                    Timer timer4 = new Timer();
                    timer4.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveGoldCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, gold, area.m_gold);
                            if(giveGoldCheck[0])
                            {
                                gold.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(gold, area.m_gold));
                            }

                        }
                    },tick+=20);

                    final boolean[] giveAlCheck = {InventoryUtils.canAddItemToInventory(mc.player, al, area.m_aluminum)};

                    Timer timer5 = new Timer();
                    timer5.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveAlCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, al, area.m_aluminum);
                            if(giveAlCheck[0])
                            {
                                al.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(al, area.m_aluminum));
                            }

                        }
                    },tick+=20);

                    final boolean[] giveDiamondCheck = {InventoryUtils.canAddItemToInventory(mc.player, diamond, area.m_diamond)};

                    Timer timer6 = new Timer();
                    timer6.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveDiamondCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, diamond, area.m_diamond);
                            if(giveDiamondCheck[0])
                            {
                                diamond.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(diamond, area.m_diamond));
                            }

                        }
                    },tick+=20);


                    final boolean[] giveEmerCheck = {InventoryUtils.canAddItemToInventory(mc.player, emer, area.m_emerald)};

                    Timer timer7 = new Timer();
                    timer7.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveEmerCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, emer, area.m_emerald);
                            if(giveEmerCheck[0])
                            {
                                emer.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(emer, area.m_emerald));
                            }

                        }
                    },tick+=20);

                    final boolean[] giveRedStoneCheck = {InventoryUtils.canAddItemToInventory(mc.player, redstone, area.m_redstone)};

                    Timer timer8 = new Timer();
                    timer8.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            giveRedStoneCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, redstone, area.m_redstone);
                            if(giveRedStoneCheck[0])
                            {
                                redstone.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(redstone, area.m_redstone));
                            }

                        }
                    },tick+=20);

                    final boolean[] giveLapisCheck = {InventoryUtils.canAddItemToInventory(mc.player, lapis, area.m_lapis)};

                    Timer timer9 = new Timer();
                    timer9.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveLapisCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, lapis, area.m_lapis);
                            if(giveLapisCheck[0])
                            {
                                lapis.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(lapis, area.m_lapis));
                            }
                        }
                    }, tick+=20);


                    final boolean[] giveHardtackCheck = {InventoryUtils.canAddItemToInventory(mc.player, hardtack, area.m_hardtack)};

                    Timer timer10 = new Timer();
                    timer10.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveHardtackCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, lapis, area.m_hardtack);
                            if(giveHardtackCheck[0])
                            {
                                hardtack.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(hardtack, area.m_hardtack));
                            }
                        }
                    }, tick+=20);

                    final boolean[] giveStickCheck = {InventoryUtils.canAddItemToInventory(mc.player, hardtack, area.m_stick)};

                    Timer timer11 = new Timer();
                    timer11.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveStickCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, stick, area.m_stick);
                            if(giveStickCheck[0])
                            {
                                stick.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(stick, area.m_stick));
                            }
                        }
                    }, tick+=20);

                    final boolean[] giveClothCheck = {InventoryUtils.canAddItemToInventory(mc.player, cloth, area.m_cloth)};

                    Timer timer12 = new Timer();
                    timer12.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveClothCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, cloth, area.m_cloth);
                            if(giveClothCheck[0])
                            {
                                cloth.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(cloth, area.m_cloth));
                            }
                        }
                    }, tick+=20);

                    final boolean[] givePlasticCheck = {InventoryUtils.canAddItemToInventory(mc.player, plastic, area.plastic)};

                    Timer timer13 = new Timer();
                    timer13.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            givePlasticCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, plastic, area.plastic);
                            if(givePlasticCheck[0])
                            {
                                plastic.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(plastic, area.plastic));
                            }
                        }
                    }, tick+=20);

                    final boolean[] giveGearCheck = {InventoryUtils.canAddItemToInventory(mc.player, gear, area.m_gear)};

                    Timer timer14 = new Timer();
                    timer14.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveGearCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, gear, area.m_gear);
                            if(giveGearCheck[0])
                            {
                                gear.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(gear, area.m_gear));
                            }
                        }
                    }, tick+=20);

                    final boolean[] giveRobotCoreCheck = {InventoryUtils.canAddItemToInventory(mc.player, robotCore, area.m_robotCore)};

                    Timer timer15 = new Timer();
                    timer15.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveRobotCoreCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, robotCore, area.m_robotCore);
                            if(giveRobotCoreCheck[0])
                            {
                                robotCore.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(robotCore, area.m_robotCore));
                            }
                        }
                    }, tick+=20);

                    final boolean[] giveVitaminCheck = {InventoryUtils.canAddItemToInventory(mc.player, vitamin, area.m_vitamin)};

                    Timer timer16 = new Timer();
                    timer16.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            giveVitaminCheck[0] = InventoryUtils.canAddItemToInventory(mc.player, vitamin, area.m_vitamin);
                            if(giveVitaminCheck[0])
                            {
                                vitamin.setCount(1);
                                Packet.networkWrapper.sendToServer(new SPacketAddItemAmount(vitamin, area.m_vitamin));
                            }
                        }
                    }, tick+=20);


                    Timer timer20 = new Timer();
                    timer20.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if(giveStoneCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "stone"));
                            if(giveCoalCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "coal"));
                            if(giveIronCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "iron"));
                            if(giveGoldCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "gold"));
                            if(giveAlCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "al"));
                            if(giveDiamondCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "dia"));
                            if(giveEmerCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "eme"));
                            if(giveRedStoneCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "red"));
                            if(giveLapisCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "lap"));
                            if(giveHardtackCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "hard"));
                            if(giveStickCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "stick"));
                            if(giveClothCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "cloth"));
                            if(giveGearCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "gear"));
                            if(givePlasticCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "plastic"));
                            if(giveVitaminCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "vitamin"));
                            if(giveRobotCoreCheck[0])
                                Packet.networkWrapper.sendToServer(new SPacketAreaitemRemove(area.areaId, "robot"));


                            System.out.println(" 돌 지급 가능 여부 - " + giveStoneCheck);
                            System.out.println(" 석탄 지급 가능 여부 - " + giveCoalCheck[0]);
                            System.out.println(" 철 지급 가능 여부 - " + giveIronCheck[0]);
                            System.out.println(" 금 지급 가능 여부 - " + giveGoldCheck[0]);
                            System.out.println(" 알루미늄 지급 가능 여부 - " + giveAlCheck[0]);
                            System.out.println(" 다이아 지급 가능 여부 - " + giveDiamondCheck[0]);
                            System.out.println(" 에메랄드 지급 가능 여부 - " + giveEmerCheck[0]);
                            System.out.println(" 레드스톤 지급 가능 여부 - " + giveRedStoneCheck[0]);
                            System.out.println(" 청금석 지급 가능 여부 - " + giveLapisCheck[0]);
                            System.out.println(" 건빵 지급 가능 여부 - " + giveHardtackCheck[0]);
                            System.out.println(" 막대기 지급 가능 여부 - " + giveStickCheck[0]);
                            System.out.println(" 천조각 지급 가능 여부 - " + giveClothCheck[0]);
                            System.out.println(" 플라스틱 지급 가능 여부 - " + givePlasticCheck[0]);
                            System.out.println(" 기어 지급 가능 여부 - " + giveGearCheck[0]);
                            System.out.println(" 로봇코어 지급 가능 여부 - " + giveRobotCoreCheck[0]);
                            System.out.println(" 비타민 지급 가능 여부 - " + giveVitaminCheck[0]);

                            //mc.player.sendMessage(new TextComponentString("§l"+area.entityNumberName+ "§f지급 완료되었습니다."));


                        }
                    }, tick+=50);






                }
                else
                {
                    mc.player.sendMessage(new TextComponentString(" §l[地上世界] §f 당신은 아이템을 수령 할 수 없습니다."));
                }


            }
            else
            {
                mc.player.sendMessage(new TextComponentString(" §l[地上世界] §f 다시 한번 Gui를 열어주세요."));
            }

        }
    }
}
