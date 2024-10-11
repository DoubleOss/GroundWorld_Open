package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketShopAmountChange implements IMessage, IMessageHandler<CPacketShopAmountChange, CPacketShopAmountChange>
{


    String shopName = "";
    int number = 0;
    int amount = 0;

    public CPacketShopAmountChange()
    {

    }

    public CPacketShopAmountChange(String shopName, int number, int amount)
    {
        this.shopName = shopName;
        this.number = number;
        this.amount = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        shopName = ByteBufUtils.readUTF8String(buf);
        number = buf.readInt();
        amount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, shopName);
        buf.writeInt(number);
        buf.writeInt(amount);

    }

    @Override
    public CPacketShopAmountChange onMessage(CPacketShopAmountChange message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.m_hashShopData.get(message.shopName).itemDataList.get(message.number).requestItemBuyAmount = message.amount;
        });
        return null;
    }
}
