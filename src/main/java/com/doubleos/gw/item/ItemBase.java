package com.doubleos.gw.item;



import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.blockInterface.IHasModel;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.variable.GroundItemStack;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item implements IHasModel
{

    public ItemBase(String name, String koreaName, int maxStackCount)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(GroundWorld.SdTab);

        ModItems.ITEMS.add(this);
        ModItems.itemKoreaName.add(koreaName);
        setMaxStackSize(maxStackCount);

    }

    public ItemBase(String name, String koreaName)
    {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(GroundWorld.SdTab);

        ModItems.ITEMS.add(this);
        ModItems.itemKoreaName.add(koreaName);

    }

    @Override
    public void registerModels()
    {
        GroundWorld.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        if(!worldIn.isRemote)
        {

        }
        return super.onItemRightClick(worldIn, player, hand);

    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            ItemStack stack = GroundItemStack.hashItemToItemStack.getOrDefault(this, new ItemStack(this));
            items.add(stack);

        }
    }

}
