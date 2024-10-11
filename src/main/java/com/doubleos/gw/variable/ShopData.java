package com.doubleos.gw.variable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ShopData
{


    public String shopName = "";


    public eShop_Mode shopMode = eShop_Mode.BUY;

    public ShopData(String shopName) {
        this.shopName = shopName;
    }

    public ArrayList<ShopItemData> itemDataList = new ArrayList<>();

    public enum eShop_Mode
    {
        BUY,
        SELL
    }


}
