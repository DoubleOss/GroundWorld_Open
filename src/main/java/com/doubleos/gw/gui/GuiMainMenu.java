package com.doubleos.gw.gui;


import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.gui.btn.BtnEsc;
import com.doubleos.gw.gui.btn.BtnMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiMainMenu extends GuiScreen
{
    Minecraft minecraft = Minecraft.getMinecraft();


    float m_backYPos = 0;


    float m_backYPos2 = 0;
    float m_buttonAlpha = 0;

    public GuiMainMenu()
    {

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

        if(button.id == 0)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
        else if(button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        else if(button.id == 2)
        {
            boolean flag = this.mc.isIntegratedServerRunning();
            boolean flag1 = this.mc.isConnectedToRealms();
            button.enabled = false;
            this.mc.world.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((WorldClient)null);

            if (flag)
            {
                try {
                    this.mc.displayGuiScreen(new net.minecraft.client.gui.GuiMainMenu());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else if (flag1)
            {
                RealmsBridge realmsbridge = new RealmsBridge();
                try {
                    realmsbridge.switchToRealms(new net.minecraft.client.gui.GuiMainMenu());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                try {
                    this.mc.displayGuiScreen(new net.minecraft.client.gui.GuiMainMenu());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @Override
    public void initGui()
    {

        ScaledResolution scaled = new ScaledResolution(mc);


        float width = (float) scaled.getScaledWidth_double();

        float height = (float) scaled.getScaledHeight_double();



        float btn_Width = 210f / 3f;
        float btn_Height = 24f / 3f;



        GuiButton btn1 = new BtnEsc(0, (int)btn_Width - 15, (int)(height/2 - btn_Height/2) + 18, "돌아가기", "back");
        GuiButton btn2 = new BtnEsc(1, (int)btn_Width - 15, (int)(height/2 - btn_Height/2) + 44, "환경설정", "설정");
        GuiButton btn3 = new BtnEsc(2, (int)btn_Width - 15, (int)(height/2 - btn_Height/2) + 69, "나가기", "종료");
        buttonList.add(btn1);
        buttonList.add(btn2);
        buttonList.add(btn3);


        //buttonList.add(new MapBtn(7, (int)(width/2 - 80/2 + (250 / currutionWidth)), (int)(height/2 - 30/2 + (135 / currutionHeight)), 80, 30, "", "move"));


//        buttonList.add(btn1);
//        buttonList.add(btn2);
//        buttonList.add(btn3);



        super.initGui();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 1)
        {
            minecraft.player.closeScreen();

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        //drawDefaultBackground();
        //super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution scale = new ScaledResolution(minecraft);


        //1920 1080 495 270
        //1280 720 640 360

        float width = (float) scale.getScaledWidth_double();

        float height = (float) scale.getScaledHeight_double();


        float currutionWidth = 495 / width;
        float currutionHeight = 270 / height;

        float menuBackgroundXSize = 960 / 3;
        float menuBackgroundYSize = 1080 / 3;


        float backgroundWidth = 554 / 3;
        float backgroundHeight = 1009 / 3;


        //System.out.println(width);
        //System.out.println(height);

        float fpsCurrection = (120f / Minecraft.getDebugFPS()) * 0.5f;

        float random = (float) ((Math.random() * (10 - 5) + 5) * 0.1f);


        float partialTicksCurrection = (0.25f + random)  * fpsCurrection;

        float ypos = 0;
        float alpha = 0f;
        if(m_backYPos < 100)
        {
            m_backYPos += partialTicksCurrection;
            ypos = easeOutCubic(m_backYPos * 0.01f);
        }
        else
        {
            ypos = 1;
        }
        float ypos2 = 0;
        if(m_backYPos2 < 100)
        {
            m_backYPos2 += partialTicksCurrection;
            ypos2 = easeOutCubic(m_backYPos2 * 0.01f);
        }
        else
        {
            ypos2 = 1;
        }
        //System.out.println(m_backYPos);
        //System.out.println(ypos);

        //(height/2 - menuBackgroundYSize/2)

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/esc/background.png"));
        drawTexture(0, 0, scale.getScaledWidth(), scale.getScaledHeight(), 0, 0 , 1, 1, 1 ,1);



//        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/esc/right_background.png"));
//        drawTexture(width - menuBackgroundXSize/2 - (menuBackgroundXSize/2 * ypos), height/2 - menuBackgroundYSize/2 , menuBackgroundXSize, menuBackgroundYSize, 0, 0 , 1, 1, 1 ,1);
//
//
//        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/gui/esc/background.png"));
//        drawTexture(width/2 - backgroundWidth/2, (height/2 - backgroundHeight/2 + (height/2 - backgroundHeight/2 * ypos2) ) + 5 , backgroundWidth, backgroundHeight, 0, 0 , 1, 1, 3 ,1);


//        if(m_backYPos > 60)
//        {
//            m_buttonAlpha += partialTicksCurrection;
//            alpha = easeOutCubic(m_buttonAlpha * 0.01f);
//            for(int i = 0 ; i<buttonList.size(); i++)
//            {
//                buttonList.get(i).drawButton(mc, mouseX, mouseY, partialTicks);
//                ((BtnMenu) buttonList.get(i)).m_Alpha = alpha;
//            }
//
//        }
            for(int i = 0 ; i<buttonList.size(); i++)
            {
                buttonList.get(i).drawButton(mc, mouseX, mouseY, partialTicks);
            }

    }

    public float easeOutCubic(float x)
    {
        return (float)(1 - Math.pow(1 - x, 3));
    }

    public void drawTexture(String texture, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z)
    {
        ResourceLocation location = new ResourceLocation("sdf", "textures/gui/" + texture + ".png");
        Minecraft.getMinecraft().renderEngine.bindTexture(location);
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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


    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
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
