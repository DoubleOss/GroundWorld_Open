package net.minecraft.client.gui.recipebook;

import java.util.List;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.RecipeCommand;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonRecipe extends GuiButton
{
    private static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
    private RecipeBook book;
    private RecipeList list;
    private float time;
    private float animationTime;
    private int currentIndex;

    public GuiButtonRecipe()
    {
        super(0, 0, 0, 25, 25, "");
    }

    public void init(RecipeList p_193928_1_, RecipeBookPage p_193928_2_, RecipeBook p_193928_3_)
    {
        this.list = p_193928_1_;
        this.book = p_193928_3_;
        List<IRecipe> list = p_193928_1_.getRecipes(p_193928_3_.isFilteringCraftable());

        for (IRecipe irecipe : list)
        {
            if (p_193928_3_.isNew(irecipe))
            {
                p_193928_2_.recipesShown(list);
                this.animationTime = 15.0F;
                break;
            }
        }
    }

    public RecipeList getList()
    {
        return this.list;
    }

    public void setPosition(int p_191770_1_, int p_191770_2_)
    {
        this.x = p_191770_1_;
        this.y = p_191770_2_;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            if (!GuiScreen.isCtrlKeyDown())
            {
                this.time += partialTicks;
            }

            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            RenderHelper.enableGUIStandardItemLighting();

            float guideBtnWidth = 72f/3f;
            float guideBtnHeight = 72f/3f;

            mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\inventory_guide(box_ge).png"));
            Render.drawTexture(this.x,  this.y, guideBtnWidth, guideBtnHeight, 0, 0, 1, 1, 200, 1);


            int i = 29;

            if (!this.list.containsCraftableRecipes())
            {
                i += 25;
            }

            int j = 206;

            if (this.list.getRecipes(this.book.isFilteringCraftable()).size() > 1)
            {
                j += 25;
            }

            boolean flag = this.animationTime > 0.0F;

            if (flag)
            {
                float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(this.x + 8), (float)(this.y + 12), 1.0F);
                GlStateManager.scale(f, f, 1.0F);
                GlStateManager.translate((float)(-(this.x + 8)), (float)(-(this.y + 12)), 1.0F);
                this.animationTime -= partialTicks;
            }

            List<IRecipe> list = this.getOrderedRecipes();
            this.currentIndex = MathHelper.floor(this.time / 30.0F) % list.size();
            ItemStack itemstack = ((IRecipe)list.get(this.currentIndex)).getRecipeOutput();
            int k = 4;

            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0, 0, 200);
                if (this.list.hasSingleResultItem() && this.getOrderedRecipes().size() > 1)
                {
                    Render.renderHotbarItem(this.x + k + 1, this.y + k + 1, partialTicks, mc.player, itemstack);
                    //mc.getRenderItem().renderItemAndEffectIntoGUI(mc.player, itemstack, x, y);
                    //mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.x + k + 1, this.y + k + 1);
                    --k;
                }
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.x + k, this.y + k);
            }
            GlStateManager.popMatrix();



            if (flag)
            {
                GlStateManager.popMatrix();
            }

            RenderHelper.disableStandardItemLighting();
        }
    }

    private List<IRecipe> getOrderedRecipes()
    {
        List<IRecipe> list = this.list.getDisplayRecipes(true);

        if (!this.book.isFilteringCraftable())
        {
            list.addAll(this.list.getDisplayRecipes(false));
        }

        return list;
    }

    public boolean isOnlyOption()
    {
        return this.getOrderedRecipes().size() == 1;
    }

    public IRecipe getRecipe()
    {
        List<IRecipe> list = this.getOrderedRecipes();
        return list.get(this.currentIndex);
    }

    public List<String> getToolTipText(GuiScreen p_191772_1_)
    {
        ItemStack itemstack = ((IRecipe)this.getOrderedRecipes().get(this.currentIndex)).getRecipeOutput();
        List<String> list = p_191772_1_.getItemToolTip(itemstack);

        if (this.list.getRecipes(this.book.isFilteringCraftable()).size() > 1)
        {
            list.add(I18n.format("gui.recipebook.moreRecipes"));
        }

        return list;
    }

    public int getButtonWidth()
    {
        return 25;
    }
}
