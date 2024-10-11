package com.doubleos.gw.gui;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.inventory.InventoryContainer;
import com.doubleos.gw.inventory.WorkBenchContainer;
import com.doubleos.gw.util.Render;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiReWorkBench extends GuiContainer implements IRecipeShownListener
{

    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
    private GuiButtonImage recipeButton;
    private GuiRecipeBook recipeBookGui = new GuiRecipeBook();;

    private boolean widthTooNarrow;

    public GuiReWorkBench(InventoryPlayer playerInv, World worldIn)
    {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }

    public GuiReWorkBench(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
    {
        super(new WorkBenchContainer(playerInv, worldIn, blockPosition));
        recipeBookGui = new GuiRecipeBook();
    }

    public void initGui()
    {
        super.initGui();
        ScaledResolution scale = new ScaledResolution(mc);

        int scaleWidth = scale.getScaledWidth();
        int scaleHeight = scale.getScaledHeight();
        //this.recipeButton = new GuiButtonImage(10, scaleWidth/2 - 115, scaleHeight/2 - 33, "", "inventory_guide");
        this.widthTooNarrow = this.width < 379;
        this.recipeBookGui.func_194303_a(this.width, this.height, this.mc, this.widthTooNarrow, ((WorkBenchContainer)this.inventorySlots).craftMatrix);
        this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);

        //this.recipeButton = new GuiButtonImage(10, this.guiLeft + 5, this.height / 2 - 49, 20, 18, 0, 168, 19, CRAFTING_TABLE_GUI_TEXTURES);

        this.buttonList.add(this.recipeButton);
    }

    public void updateScreen()
    {
        super.updateScreen();
        this.recipeBookGui.tick();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        if (this.recipeBookGui.isVisible() && this.widthTooNarrow)
        {
            this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
            this.recipeBookGui.render(mouseX, mouseY, partialTicks);
        }
        else
        {
            this.recipeBookGui.render(mouseX, mouseY, partialTicks);
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.recipeBookGui.renderGhostRecipe(this.guiLeft, this.guiTop, true, partialTicks);
        }

        this.renderHoveredToolTip(mouseX, mouseY);
        this.recipeBookGui.renderTooltip(this.guiLeft, this.guiTop, mouseX, mouseY);


    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {

    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        ScaledResolution scale = new ScaledResolution(mc);

        float scaleWidth = (float)scale.getScaledWidth();
        float scaleHeight = (float)scale.getScaledHeight();


        float inventoryWidth = 748f/3f;
        float inventoryHeight = 738/3f;

        float inventoryRecipeWidth = 1117/3f;
        float inventoryRecipeHeight = 738f/3f;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.recipeBookGui.isVisible())
        {
            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory_guide(s).png"));
            Render.drawTexture(scaleWidth/2 - inventoryRecipeWidth/2, scaleHeight/2 - inventoryRecipeHeight/2, inventoryRecipeWidth, inventoryRecipeHeight, 0, 0, 1, 1, 0, 1);

            this.inventorySlots.getSlot(0).xPos = 202;
            this.inventorySlots.getSlot(0).yPos = 29;

            int loopCount = 1;

            int[] widthList = new int[] {215, 239, 263};
            int[] heightList = new int[] {10, 34, 58};


            for(int i=0; i <heightList.length; i++)
            {
                for(int j=0; j <widthList.length; j++)
                {
                    this.inventorySlots.getSlot(loopCount).xPos = (int)(widthList[j] - 139);
                    this.inventorySlots.getSlot(loopCount).yPos = (int)(heightList[i] - 6);
                    loopCount++;
                }
            }

            widthList = new int[] {71, 94, 118, 143, 167, 191, 215, 239, 263};
            heightList = new int[] {105, 81, 58};

            for(int i=0; i <heightList.length; i++)
            {
                for(int j=0; j <widthList.length; j++)
                {
                    this.inventorySlots.getSlot(loopCount).xPos = (int)(widthList[j] - 24);
                    this.inventorySlots.getSlot(loopCount).yPos = (int)(heightList[i] + 35);
                    loopCount++;
                }
            }


            widthList = new int[] {71, 94, 118, 143, 167, 191, 215, 239, 263};
            heightList = new int[] {154};

            for(int i=0; i <heightList.length; i++)
            {
                for(int j=0; j <widthList.length; j++)
                {
                    this.inventorySlots.getSlot(loopCount).xPos = (int)(widthList[j] - 24);
                    this.inventorySlots.getSlot(loopCount).yPos = (int)(heightList[i] + 15);
                    loopCount++;
                }
            }
            this.recipeButton.x = (int) (scaleWidth/2 - 46);

            this.recipeButton.y = (int) (scaleHeight/2 - 36);

        }
        else
        {
            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\crafting_table(s).png"));
            Render.drawTexture(scaleWidth/2 - inventoryWidth/2, scaleHeight/2 - inventoryHeight/2, inventoryWidth, inventoryHeight, 0, 0, 1, 1, 0, 1);

            this.inventorySlots.getSlot(0).xPos = 142;
            this.inventorySlots.getSlot(0).yPos = 31;
            int loopCount = 1;

            int[] widthList = new int[] {215, 240, 265};
            int[] heightList = new int[] {10, 34, 58};


            for(int i=0; i <heightList.length; i++)
            {
                for(int j=0; j <widthList.length; j++)
                {
                    this.inventorySlots.getSlot(loopCount).xPos = (int)(widthList[j] - 204);
                    this.inventorySlots.getSlot(loopCount).yPos = (int)(heightList[i] - 3);
                    loopCount++;
                }
            }

            widthList = new int[] {71, 96, 121, 146, 172, 196, 221, 247, 272};
            heightList = new int[] {105, 81, 56};

            for(int i=0; i <heightList.length; i++)
            {
                for(int j=0; j <widthList.length; j++)
                {
                    this.inventorySlots.getSlot(loopCount).xPos = (int)(widthList[j] - 91);
                    this.inventorySlots.getSlot(loopCount).yPos = (int)(heightList[i] + 41);
                    loopCount++;
                }
            }


            widthList = new int[] {71, 96, 121, 146, 172, 196, 221, 247, 272};
            heightList = new int[] {154};

            for(int i=0; i <heightList.length; i++)
            {
                for(int j=0; j <widthList.length; j++)
                {
                    this.inventorySlots.getSlot(loopCount).xPos = (int)(widthList[j] - 91);
                    this.inventorySlots.getSlot(loopCount).yPos = (int)(heightList[i] + 20);
                    loopCount++;
                }
            }
            this.recipeButton.x = (int) (scaleWidth/2 - 115);
            this.recipeButton.y = (int) (scaleHeight/2 - 33);

        }

        this.guiLeft = width/2 - 89;
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        //this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY)
    {
        return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (!this.recipeBookGui.mouseClicked(mouseX, mouseY, mouseButton))
        {
            if (!this.widthTooNarrow || !this.recipeBookGui.isVisible())
            {
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    protected boolean hasClickedOutside(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_)
    {
        boolean flag = p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.xSize || p_193983_2_ >= p_193983_4_ + this.ySize;
        return this.recipeBookGui.hasClickedOutside(p_193983_1_, p_193983_2_, this.guiLeft, this.guiTop, this.xSize, this.ySize) && flag;
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 10)
        {
            this.recipeBookGui.initVisuals(this.widthTooNarrow, ((WorkBenchContainer)this.inventorySlots).craftMatrix);
            this.recipeBookGui.toggleVisibility();
            this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width , this.xSize);
            //this.recipeButton.setPosition(this.guiLeft + 5, this.height / 2 - 49);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (!this.recipeBookGui.keyPressed(typedChar, keyCode))
        {
            super.keyTyped(typedChar, keyCode);
        }

    }

    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
    {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
        this.recipeBookGui.slotClicked(slotIn);
    }

    public void recipesUpdated()
    {
        this.recipeBookGui.recipesUpdated();
    }

    public void onGuiClosed()
    {
        this.recipeBookGui.removed();
        super.onGuiClosed();
    }

    public GuiRecipeBook func_194310_f()
    {
        return this.recipeBookGui;
    }


}
