package com.doubleos.gw.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Render
{

    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static float easeOutCubic(float x)
    {
        return (float)(1 - Math.pow(1 - x, 3));
    }

    public static float easeInOutBack(float x)
    {
        float c1 = 1.70158f;
        float c2 = c1 * 1.525f;

        return (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2f);
    }
    public static float easeInOutSine(float x)
    {
        return (float)-(Math.cos(Math.PI * x) - 1) / 2f;
    }
    public static float easeOutBounce(float x)
    {
        float n1 = 7.5625f;
        float d1 = 2.75f;

        if (x < 1 / d1)
        {
            return n1 * x * x;
        }
        else if (x < 2 / d1)
        {
            return n1 * (x -= 1.5 / d1) * x + 0.75f;
        }
        else if (x < 2.5 / d1)
        {
            return n1 * (x -= 2.25 / d1) * x + 0.9375f;
        }
        else
        {
            return n1 * (x -= 2.625f / d1) * x + 0.984375f;
        }
    }

    public static void drawTexture(String texture, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z)
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


    public static void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
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

    public static void drawCricleNewProgressToQuad(float x, float y, float z, double textSizeX, double textSizeY, double per, double color) {

        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1f, 1f, 1f, 1f);
            //GlStateManager.translate(x, y, z);
            bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x+textSizeX/2f, y+textSizeY/2, z).tex(0.5, 0.5).endVertex();
            for (float i = 0; i <= per * 100 / 100; i++) {
                float theta = (float) (2.0f * Math.PI * i / 100f); // 현재 각도 계산
                float xPos = (float) (textSizeX * Math.cos(theta)); // x 좌표 계산
                float yPos = (float) (textSizeY * Math.sin(theta)); // y 좌표 계산
                float u = (x + 1.0f) / 2.0f; // x 좌표를 [0, 1] 범위로 변환하여 텍스처 좌표 계산
                float v = (y + 1.0f) / 2.0f;
                bb.pos(xPos + (x+textSizeX/2f), yPos+(y+textSizeY/2f), z).tex(u, v).endVertex();
            }

            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    public static void drawCricleProgressToQuad(float x, float y, float z, double textSizeX, double textSizeY, double per, double color)
    {

        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1f, 1f, 1f, 1f);

            bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);

            bb.pos(x +textSizeX/2, y+textSizeY/2, z).tex(0.5d, 0.5d).endVertex();
            if(per >= 87)
            {
                double b0 = (1d-(99d-per) / 13d);
                bb.pos(x + (textSizeX/2)*b0, y, z).tex(0.5d*b0, 0d).endVertex();
            }
            if(per >= 62)
            {
                if(per > 62 && per <= 74)
                {
                    double b1 = (86.5d-per) / 25d;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0d, 1d* b1).endVertex();
                }
                else if(per > 74f && per <= 80f)
                {
                    double b1 = (87.5d-per) / 25d;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0d, 1d* b1).endVertex();
                }
                else if(per > 80f && per <= 87f)
                {
                    double b1 = (87.5-per) / 25d;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0d, 1d* b1).endVertex();
                }
                else if(per > 87)
                {
                    bb.pos(x, y, z).tex(0d, 0d).endVertex();
                }
                bb.pos(x, y+textSizeY, z).tex(0d, 1d).endVertex();

            }
            if(per >= 38)
            {

                if(per >= 38 && per < 40)
                {
                    double b2 = (62-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per >= 40 && per <= 47)
                {
                    double b2 = (61.2f-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per > 47 && per <= 54)
                {
                    double b2 = (61.8f-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per > 54 && per <= 62)
                {
                    double b2 = (63.2d-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per > 62)
                {
                    bb.pos(x, y+textSizeY, z).tex(0d, 1d).endVertex();
                }
                bb.pos(x+textSizeX, y+textSizeY, z).tex(1d, 1d).endVertex();
            }
            if(per > 12)
            {
                if(per > 12 && per <= 38)
                {
                    if (per >= 14f && per < 18f)
                    {
                        double b3 = (1-((36.5f-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();

                    }
                    else if(per >= 18 && per < 20)
                    {
                        double b3 = (1-((36-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }
                    else if(per >= 20 && per < 24)
                    {
                        double b3 = (1-((37-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }
                    else if (per >= 24 && per < 35)
                    {
                        double b3 = (1-((37.5f-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }
                    else
                    {
                        double b3 = (1-((37-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }


                }
                else if(per > 38)
                {
                    //bb.pos(x+textSizeX, y+textSizeY, z).tex(1d, 1d).endVertex();
                }
                bb.pos(x+textSizeX, y, z).tex(1d, 0d).endVertex();
            }
            if(per <= 12)
            {

                double b0 = (1-(12-per) / 12);
                bb.pos(x+textSizeX/2 + (textSizeX/2 * b0), y, z).tex(0.5d + (b0 * 0.5d), 0d).endVertex();
            }
            bb.pos(x+textSizeX/2, y, z).tex(0.5d, 0d).endVertex();


            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();

    }
    public static void drawStringScaleResizeByRightWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = (int) (minecraft.fontRenderer.getStringWidth(text) * scale);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1);
            GlStateManager.translate(0, 0, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize)/scale, (y)/scale, color, true);
        }
        GlStateManager.popMatrix();
    }
    public static void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public static void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color, boolean active)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1f);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x)/scale, (y)/scale, color, active);
        }
        GlStateManager.popMatrix();
    }
    public static void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float r, float g, float b)
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
    public static void drawRotateTexture2(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.translate(x + xSize / 2, y + ySize / 2, z); // 중심점을 텍스처의 가운데로 이동
            GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f); // 회전 적용
            GlStateManager.translate(-(x + xSize / 2), -(y + ySize / 2), z);
            GlStateManager.color(1, 1, 1, alpha);

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
    public static void drawSkinHead(float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();
        minecraft.renderEngine.bindTexture(minecraft.player.getLocationSkin());

        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 1f, 1f);
        GlStateManager.popMatrix();
    }



    public static void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float scale)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            xSize *= scale;
            ySize *= scale;
            GlStateManager.scale(scale, scale, 1);
            GlStateManager.color(1f, 1f, 1f, alpha);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x / scale, y / scale, z).tex(u, v).endVertex();
            bb.pos(x / scale, (y + ySize) / scale, z).tex(u, vAfter).endVertex();
            bb.pos((x + xSize) /scale, (y + ySize) / scale, z).tex(uAfter, vAfter).endVertex();
            bb.pos((x + xSize)/scale, y/scale, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();

    }


    public static void drawTextureMiddle(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float scale)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();

        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            GlStateManager.color(0.7f, 0.45f, 0.9f);
            GlStateManager.scale(scale, scale, 1);
            bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);
            scale = 1f;
            bb.pos((x + (xSize/2))/scale, y/scale, z).tex(0.5f, 0f).endVertex();
            bb.pos(x/scale, y/scale, z).tex(0f, 0f).endVertex();
            bb.pos(x/scale, (y+ySize)/scale, z).tex(0f, 1f).endVertex();
            bb.pos((x+xSize)/scale, (y+ySize)/scale, z).tex(1f, 1f).endVertex();
            bb.pos((x+xSize)/scale, y/scale, z).tex(1f, 0f).endVertex();
            bb.pos((x+xSize/2)/scale, y/scale, z).tex(0.5f, 0f).endVertex();
//            bb.pos(x + (xSize/2), y, z).tex(0.5f, 0f).endVertex();
//            bb.pos(x, y, z).tex(0f, 0f).endVertex();
//            bb.pos(x, y+ySize, z).tex(0f, 1f).endVertex();
//            bb.pos(x+xSize, y+ySize, z).tex(1f, 1f).endVertex();
//            bb.pos(x+xSize, y, z).tex(1f, 0f).endVertex();
//            bb.pos(x+xSize/2, y, z).tex(0.5f, 0f).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    public static void drawGradientRect(int left, int top, int right, int bottom, float r, float g, float b, float zLevel, float alpha)
    {

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)zLevel).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)zLevel).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)zLevel).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)zLevel).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    public static void drawTextList(List<String> textLines, int x, int y, FontRenderer font, float zLevel, float scaled)
    {
        ScaledResolution scale = new ScaledResolution(minecraft);

        int i = 0;

        for (String s : textLines)
        {
            int j = (int) (minecraft.fontRenderer.getStringWidth(s) * scaled);

            if (j > i)
            {
                i = j;
            }
        }

        int l1 = x + 12;
        int i2 = y - 12;
        int k = 8;

        if (textLines.size() > 1)
        {
            k += 2 + (textLines.size() - 1) * 10;
        }

        if (l1 + i > scale.getScaledWidth())
        {
            l1 -= 28 + i;
        }

        if (i2 + k + 6 > scale.getScaledHeight())
        {
            i2 = scale.getScaledHeight() - k - 6;
        }

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, 0, zLevel);
            GlStateManager.scale(scaled, scaled, 1);

            for (int k1 = 0; k1 < textLines.size(); ++k1)
            {
                String s1 = textLines.get(k1);
                minecraft.fontRenderer.drawString(s1.replace("§f", "§0"), (float)l1 / scaled, (float)i2 / scaled, 1, false);

                if (k1 == 0)
                {
                    i2 += 2;
                }

                i2 += 10;
            }

        }
        GlStateManager.popMatrix();

    }
    public static void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font, float zLevel)
    {
        ScaledResolution scale = new ScaledResolution(minecraft);
        //net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, scale.getScaledWidth(), scale.getScaledHeight(), -1, font);

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        int i = 0;

        for (String s : textLines)
        {
            int j = minecraft.fontRenderer.getStringWidth(s);

            if (j > i)
            {
                i = j;
            }
        }

        int l1 = x + 12;
        int i2 = y - 12;
        int k = 8;

        if (textLines.size() > 1)
        {
            k += 2 + (textLines.size() - 1) * 10;
        }

        if (l1 + i > scale.getScaledWidth())
        {
            l1 -= 28 + i;
        }

        if (i2 + k + 6 > scale.getScaledHeight())
        {
            i2 = scale.getScaledHeight() - k - 6;
        }

        int l = -267386864;
        drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, 0.92470f, 0.92470f, 0.92470f, zLevel, 0.4f);
        drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, 0.92470f, 0.92470f, 0.92470f, zLevel, 0.4f);
        drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, 0.92470f, 0.92470f, 0.92470f, zLevel, 0.4f);
        drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, 0.92470f, 0.92470f, 0.92470f, zLevel, 0.4f);
        drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, 0.92470f, 0.92470f, 0.92470f, zLevel, 0.4f);
        int i1 = 1347420415;
        int j1 = 1344798847;
        drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 0.96470f, 0.96470f, 0.96470f, zLevel, 0.4f);
        drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 0.96470f, 0.96470f, 0.96470f, zLevel, 0.4f);
        drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 0.96470f, 0.96470f, 0.96470f, zLevel, 0.4f);
        drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 0.96470f, 0.96470f, 0.96470f, zLevel, 0.4f);

        for (int k1 = 0; k1 < textLines.size(); ++k1)
        {
            String s1 = textLines.get(k1);
            minecraft.fontRenderer.drawStringWithShadow(s1, (float)l1, (float)i2, -1);

            if (k1 == 0)
            {
                i2 += 2;
            }

            i2 += 10;
        }

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();

    }

    public static int RGB_TO_DECIMAL(int r, int g, int b)
    {
        int result = (r << 16) + (g << 8) + b;

        return result;
    }

    public static void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color)
    {
        drawStringScaleResizeByMiddleWidth(text, x, y, depth, scale, color, false);
    }


    public static void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color, boolean active)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        float stringSize = minecraft.fontRenderer.getStringWidth(text) * scale;

        float fontHeight = minecraft.fontRenderer.FONT_HEIGHT * scale;

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1f);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize/2f)/scale, (y)/scale, color, active);
        }
        GlStateManager.popMatrix();
    }

    public static void drawYLinearTexture(float x, float y, float xSize, float ySize, float endV, float alpha, float z)
    {
        endV = 1 - endV;
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

    public static void drawXLinearTexture(float x, float y, float xSize, float ySize, float endU, float alpha, float z)
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

//
//    public static void renderHotbarItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
//    {
//        Minecraft mc = Minecraft.getMinecraft();
//        if (!stack.isEmpty())
//        {
//            float f = stack.getAnimationsToGo() - partialTicks;
//            if (f > 0.0F)
//            {
//                GlStateManager.pushMatrix();
//                {
//                    GlStateManager.enableBlend();
//                    GlStateManager.blendFunc(770, 771);
//                    float f1 = 1.0F + f / 5.0F;
//                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//                    GlStateManager.translate((x + 8), (y + 12), 1.0F);
//                    GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
//                    GlStateManager.translate(-(x + 8), -(y + 12), 1.0F);
//
//                    GlStateManager.disableBlend();
//                }
//
//            }
//            mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) player, stack, x, y);
//            if (f > 0.0F)
//                GlStateManager.popMatrix();
//            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, y);
//        }
//    }

    public static void renderHotbarItem(float p_184044_1_, float p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            float f = (float)stack.getAnimationsToGo() - p_184044_3_;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float)(p_184044_1_ + 8), (float)(p_184044_2_ + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(p_184044_1_ + 8)), (float)(-(p_184044_2_ + 12)), 0.0F);
            }
            RenderHelper.enableGUIStandardItemLighting();
            minecraft.getRenderItem().renderItemAndEffectIntoGUI(player, stack, (int) p_184044_1_, (int) p_184044_2_);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 1);
            minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, stack, (int) p_184044_1_, (int) p_184044_2_);
            GlStateManager.popMatrix();

        }
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50f);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
