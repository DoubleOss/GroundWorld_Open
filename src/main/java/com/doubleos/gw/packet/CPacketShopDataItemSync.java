package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketShopDataItemSync implements IMessage, IMessageHandler<CPacketShopDataItemSync, CPacketShopDataItemSync>
{

    int itemId = 0;
    int itemAmount = 0;

    String shopName = "";

    public CPacketShopDataItemSync()
    {

    }

    public CPacketShopDataItemSync(String shopName, int shopItemId, int amount)
    {

        this.shopName = shopName;
        itemId = shopItemId;
        itemAmount = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        shopName = ByteBufUtils.readUTF8String(buf);
        itemId = buf.readInt();
        itemAmount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, shopName);
        buf.writeInt(itemId);
        buf.writeInt(itemAmount);
    }

    @Override
    public CPacketShopDataItemSync onMessage(CPacketShopDataItemSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).dayBuyCurrentLimitCount = message.itemAmount;

        });
        return null;
    }
}
