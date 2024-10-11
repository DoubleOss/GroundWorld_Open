package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketShopDataItemRemove implements IMessage, IMessageHandler<SPacketShopDataItemRemove, SPacketShopDataItemRemove>
{

    int itemId = 0;
    int itemAmount = 0;

    String shopName = "";


    public SPacketShopDataItemRemove()
    {

    }

    public SPacketShopDataItemRemove(int shopItemId, int amount, String shopName)
    {
        itemId = shopItemId;
        itemAmount = amount;
        this.shopName = shopName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        itemId = buf.readInt();
        itemAmount = buf.readInt();
        shopName  = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(itemId);
        buf.writeInt(itemAmount);
        ByteBufUtils.writeUTF8String(buf, shopName);
    }

    @Override
    public SPacketShopDataItemRemove onMessage(SPacketShopDataItemRemove message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            if(message.itemAmount <= 0)
                variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).currntBuyItemRemoveLimit(message.itemAmount, message.shopName);
            else
                variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).currntBuyItemAddLimit(message.itemAmount, message.shopName);
            Packet.networkWrapper.sendToAll(new CPacketShopDataItemSync(message.shopName ,message.itemId, variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).dayBuyCurrentLimitCount));
        });
        return null;
    }
}
