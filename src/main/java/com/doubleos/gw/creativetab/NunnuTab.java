package com.doubleos.gw.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NunnuTab extends CreativeTabs
{
    public NunnuTab(String label)
    {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return Items.APPLE.getDefaultInstance();
    }
}
