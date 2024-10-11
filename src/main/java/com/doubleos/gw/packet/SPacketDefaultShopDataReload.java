package com.doubleos.gw.packet;

import com.doubleos.gw.variable.ShopItemData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketDefaultShopDataReload implements IMessage, IMessageHandler<SPacketDefaultShopDataReload, SPacketDefaultShopDataReload>
{


    String shopName = "";


    public SPacketDefaultShopDataReload(){}

    public SPacketDefaultShopDataReload(String shopName)
    {
        this.shopName = shopName;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        shopName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, shopName);
    }

    @Override
    public SPacketDefaultShopDataReload onMessage(SPacketDefaultShopDataReload message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            for(int i = 0; i< Variable.Instance().m_hashShopData.get(message.shopName).itemDataList.size(); i++)
            {
                ShopItemData data = Variable.Instance().m_hashShopData.get(message.shopName).itemDataList.get(i);
                Packet.networkWrapper.sendToAll(new CPacketShopDataSync( message.shopName, data.shopItemId, data.shopItem, data.requestBuyItem, data.requestItemBuyAmount, data.requestSellItem, data.requestItemSellAmount,
                        data.dayBuyCurrentLimitCount, data.dayBuyMaxLimitCount));
            }

        });

        return null;
    }
}
