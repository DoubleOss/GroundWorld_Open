package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.gui.btn.BtnInvisible;
import com.doubleos.gw.inventory.InventoryContainer;
import com.doubleos.gw.inventory.WorkBenchContainer;
import com.doubleos.gw.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiInventory extends GuiContainer
{

    float m_mouseX;

    float m_mouseY;
    private float oldMouseX;
    private float oldMouseY;


    WorkBenchContainer m_inventoryContainer;

    public GuiInventory(EntityPlayer player, InventoryPlayer inventoryPlayer)
    {
        super(new InventoryContainer(inventoryPlayer, player));

        m_inventoryContainer = GroundWorld.instance.workBenchContainer;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.m_mouseX = mouseX;
        this.m_mouseY = mouseY;
        renderHoveredToolTip(mouseX, mouseY);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;


    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {

        ScaledResolution scaled = new ScaledResolution(mc);


    }

    Minecraft minecraft =Minecraft.getMinecraft();

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 1)
            minecraft.player.closeScreen();
        if(keyCode == 18)
            minecraft.player.closeScreen();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        drawDefaultBackground();
        ScaledResolution scale = new ScaledResolution(mc);

        float scaleWidth = (float)scale.getScaledWidth();
        float scaleHeight = (float)scale.getScaledHeight();


        int scaleFactor = scale.getScaleFactor();
        float rescalePosition = height / 360f;



        float inventoryWidth = 710f/3f;
        float inventoryHeight = 726/3f;

        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory(s).png"));
        Render.drawTexture(scaleWidth/2 - inventoryWidth/2, scaleHeight/2 - inventoryHeight/2, inventoryWidth, inventoryHeight, 0, 0, 1, 1, 0, 1);


        float yPos = 44 * 1;


        int i = this.guiLeft;
        int j = this.guiTop;

        //drawEntityOnScreen((int) (width/2) - 27, (int) (height/2) - 50, 32, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 40) - this.oldMouseY, mc.player);

        drawEntityOnScreen((int) (scaleWidth/2) - 49, height/2 - 10, 34, (float)(scaleWidth/2 - 49) - this.oldMouseX, height/2 - 10 - this.oldMouseY, this.mc.player);

    }


    protected void renderHotbarItem(float p_184044_1_, float p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack)
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


    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
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
