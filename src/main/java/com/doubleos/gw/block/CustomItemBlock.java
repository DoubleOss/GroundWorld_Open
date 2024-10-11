package com.doubleos.gw.block;

import com.doubleos.gw.variable.GroundItemStack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CustomItemBlock extends ItemBlock
{


    public CustomItemBlock(Block block) {
        super(block);
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
