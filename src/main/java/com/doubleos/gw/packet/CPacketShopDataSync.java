package com.doubleos.gw.packet;

import com.doubleos.gw.variable.ShopItemData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketShopDataSync implements IMessage, IMessageHandler<CPacketShopDataSync, CPacketShopDataSync>
{



    String shopName = "";
    public int shopItemId = 0;

    public ItemStack shopItem = new ItemStack(Items.AIR);

    public ItemStack requestBuyItem = new ItemStack(Items.AIR);

    public int requestItemBuyAmount = 0;

    public ItemStack requestSellItem = new ItemStack(Items.AIR);

    public int requestItemSellAmount = 0;

    public int dayBuyMaxLimitCount = 999;
    public int dayBuyCurrentLimitCount = 999;


    public CPacketShopDataSync(String shopName, int shopItemId, ItemStack shopItem, ItemStack requestBuyItem, int requestItemBuyAmount, ItemStack requestSellItem, int requestItemSellAmount, int dayBuyCurrentLimitCount, int dayBuyMaxLimitCount)
    {
        this.shopName = shopName;
        this.shopItemId = shopItemId;
        this.shopItem = shopItem;
        this.requestBuyItem = requestBuyItem;
        this.requestItemBuyAmount = requestItemBuyAmount;
        this.requestSellItem = requestSellItem;
        this.requestItemSellAmount = requestItemSellAmount;
        this.dayBuyMaxLimitCount = dayBuyMaxLimitCount;
        this.dayBuyCurrentLimitCount = dayBuyCurrentLimitCount;
    }

    public CPacketShopDataSync()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        shopName = ByteBufUtils.readUTF8String(buf);
        shopItemId = buf.readInt();
        shopItem = ByteBufUtils.readItemStack(buf);
        requestBuyItem = ByteBufUtils.readItemStack(buf);
        requestItemBuyAmount = buf.readInt();
        requestSellItem = ByteBufUtils.readItemStack(buf);
        requestItemSellAmount = buf.readInt();
        dayBuyMaxLimitCount = buf.readInt();
        dayBuyCurrentLimitCount = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

        ByteBufUtils.writeUTF8String(buf, shopName);
        buf.writeInt(shopItemId);
        ByteBufUtils.writeItemStack(buf, shopItem);
        ByteBufUtils.writeItemStack(buf, requestBuyItem);
        buf.writeInt(requestItemBuyAmount);
        ByteBufUtils.writeItemStack(buf, requestSellItem);
        buf.writeInt(requestItemSellAmount);
        buf.writeInt(dayBuyMaxLimitCount);
        buf.writeInt(dayBuyCurrentLimitCount);

    }

    @Override
    public CPacketShopDataSync onMessage(CPacketShopDataSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            //int shopItemId, ItemStack shopItem, ItemStack requestBuyItem, int requestItemBuyAmount, ItemStack requestSellItem, int requestItemSellAmount, int dayBuyMaxLimitCount)
            ShopItemData data = new ShopItemData(message.shopItemId, message.shopItem, message.requestBuyItem, message.requestItemBuyAmount, message.requestSellItem, message.requestItemSellAmount, message.dayBuyMaxLimitCount);
            data.dayBuyCurrentLimitCount = message.dayBuyCurrentLimitCount;
            variable.m_hashShopData.get(message.shopName).itemDataList.set(message.shopItemId, data);
        });
        return null;
    }
}
