package net.minecraft.client.gui.inventory;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChest extends GuiContainer
{
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/.png");

    Minecraft minecraft = Minecraft.getMinecraft();
    private final IInventory upperChestInventory;
    private final IInventory lowerChestInventory;
    private final int inventoryRows;

    public GuiChest(IInventory upperInv, IInventory lowerInv)
    {
        super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().player));
        this.upperChestInventory = upperInv;
        this.lowerChestInventory = lowerInv;
        this.allowUserInput = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = lowerInv.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        ScaledResolution scale = new ScaledResolution(mc);


        float width = (float) scale.getScaledWidth_double();
        float height = (float) scale.getScaledHeight_double();

        int scaleFactor = scale.getScaleFactor();
        float rescalePosition = height / 360f;



        if (this.inventoryRows == 6)
        {
            float backInvSizeX = 713f / 3f;
            float backInvSizeY = 955f/ 3f;
            GlStateManager.pushMatrix();
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/hud/hud/generic_54.png"));
                drawTexture(width/2f-backInvSizeX/2f, height/2f - backInvSizeY/2f, backInvSizeX, backInvSizeY, 0,0, 1, 1, 2, 1);
            }
            GlStateManager.popMatrix();
        }
        else
        {
            float backInvSizeX = 713f / 3f;
            float backInvSizeY = 727f/ 3f;
            GlStateManager.pushMatrix();
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/hud/hud/shulker_box(s).png"));
                drawTexture(width/2f-backInvSizeX/2f, height/2f - backInvSizeY/2f, backInvSizeX, backInvSizeY, 0,0, 1, 1, 2, 1);
            }
            GlStateManager.popMatrix();
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {

    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

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
}