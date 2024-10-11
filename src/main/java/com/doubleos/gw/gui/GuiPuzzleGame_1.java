package com.doubleos.gw.gui;


import com.doubleos.gw.util.Reference;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiPuzzleGame_1 extends GuiScreen
{


    Minecraft minecraft = Minecraft.getMinecraft();

    public GuiPuzzleGame_1(EntityPlayer player, InventoryPlayer inventory)
    {
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        Variable variable = Variable.Instance();


    }

    @Override
    public void initGui()
    {


        buttonList.clear();
        ScaledResolution scaled = new ScaledResolution(mc);

        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        int scaleFactor = scaled.getScaleFactor();

        float rescale = height / 360f;


        int bookNoteWidth = (int) (100 / 2 * rescale);
        int bookNoteHeight = (int) (580 /2 * rescale);


        int bookMarkWidth = (int) (80 / 2 * rescale);
        int bookMarkHeight = (int) (529 / 2 * rescale);


        Variable variable = Variable.Instance();

        int buttonRescaleWidth = (int) (50 * rescale);

        int buttonPosX = (int) (width/2 - 200 * rescale);
        String resourceName = "";

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        Variable variable = Variable.Instance();

        drawDefaultBackground();

        ScaledResolution scale = new ScaledResolution(minecraft);

        int discordBackgroundXSize = 1382/2;
        int discordBackgroundYSize = 530/2;

        int scaleFactor = scale.getScaleFactor();

        float width = (float) scale.getScaledWidth_double();
        float height = (float) scale.getScaledHeight_double();
        float rescalePosition = height / 360f;

        //System.out.println("WIDTH = " + width + "   Height = " + height + "   scaleFactor = " + scaleFactor);
        //System.out.println("ResclaePosition  " + rescalePosition);

        float puzzleWidth = 100 / 2 * rescalePosition;
        float puzzleHeight = 100 / 2 * rescalePosition;

        float puzzleBackWidth = 204 / (2 * (scaleFactor / 2));
        float puzzleBackHeight = 204 / (2 * (scaleFactor / 2));


        int ci = 1;

        int[] clickList1 = new int[]{0, 3, 7, 11};
        int[] clickList2 = new int[]{1, 5, 8, 9};
        int[] clickList3 = new int[]{2, 6, 12, 14};
        int[] clickList4 = new int[]{4, 10, 11, 15};


        buttonList.get(0).drawButton(mc, mouseX, mouseY, partialTicks);
        buttonList.get(1).drawButton(mc, mouseX, mouseY, partialTicks);
        buttonList.get(2).drawButton(mc, mouseX, mouseY, partialTicks);
        buttonList.get(3).drawButton(mc, mouseX, mouseY, partialTicks);


    }


    public void drawTexture(String texture, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z)
    {
        ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/" + texture + ".png");
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
            //GlStateManager.translate((width - height * 16.0 / 9) / 2, 0.0, 0.0);
            //GlStateManager.scale(height / 1080.0, height / 1080.0, height / 1080.0);
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
