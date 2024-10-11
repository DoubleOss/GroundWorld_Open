package com.doubleos.gw.variable;

import com.doubleos.gw.handler.ShopDataConfig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.concurrent.CompletableFuture;

public class ShopItemData
{

    public ShopItemData(int shopItemId, ItemStack shopItem, ItemStack requestBuyItem, int requestItemBuyAmount, ItemStack requestSellItem, int requestItemSellAmount, int dayBuyMaxLimitCount)
    {
        this.shopItemId = shopItemId;
        this.shopItem = shopItem;
        this.requestBuyItem = requestBuyItem;
        this.requestItemBuyAmount = requestItemBuyAmount;
        this.requestSellItem = requestSellItem;
        this.requestItemSellAmount = requestItemSellAmount;
        this.dayBuyMaxLimitCount = dayBuyMaxLimitCount;
        this.dayBuyCurrentLimitCount = dayBuyMaxLimitCount;
    }

    public int shopItemId = 0;

    public ItemStack shopItem = new ItemStack(Items.AIR);

    public ItemStack requestBuyItem = new ItemStack(Items.AIR);

    public int requestItemBuyAmount = 0;

    public ItemStack requestSellItem = new ItemStack(Items.AIR);

    public int requestItemSellAmount = 0;

    public int dayBuyMaxLimitCount = 999;
    public int dayBuyCurrentLimitCount = 999;

//            m_hashShopData.put("스마트폰상점", phoneShopData);
//        m_hashShopData.put("남산상점", namSanShopData);
//        m_hashShopData.put("서울역상점", seoulShopData);
//        m_hashShopData.put("광화문상점", gShopData);
//        m_hashShopData.put("여의도상점", yShopData);
//        m_hashShopData.put("잠실상점", zShopData);
    public void currntBuyItemAddLimit(int count, String shopName)
    {
        if(dayBuyCurrentLimitCount + count >= dayBuyMaxLimitCount)
            return;
        dayBuyCurrentLimitCount += count;

    }
    public void currntBuyItemRemoveLimit(int count, String shopName)
    {
        if(dayBuyCurrentLimitCount + count >= dayBuyMaxLimitCount)
            return;
        dayBuyCurrentLimitCount += count;


    }

}