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
        //기본적인 패킷 연산은 비동기로 연산 
        //작업을 서버측 동기로 변경
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            //클라이언트에서 서버측에 넘어온 상점 데이터를 서버측에 반영
            if(message.itemAmount <= 0)
                variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).currntBuyItemRemoveLimit(message.itemAmount, message.shopName);
            else
                variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).currntBuyItemAddLimit(message.itemAmount, message.shopName);
            //이후 Gui를 열어 놓을 수 있는 플레이어를 위해 반영된 서버측 값을 모든 플레이어에게 전송
            Packet.networkWrapper.sendToAll(new CPacketShopDataItemSync(message.shopName ,message.itemId, variable.m_hashShopData.get(message.shopName).itemDataList.get(message.itemId).dayBuyCurrentLimitCount));
        });
        return null;
    }
}
