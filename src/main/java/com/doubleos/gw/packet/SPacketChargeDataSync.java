package com.doubleos.gw.packet;

import com.doubleos.gw.handler.ChargeDataConfig;
import com.doubleos.gw.variable.ChargeData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketChargeDataSync implements IMessage, IMessageHandler<SPacketChargeDataSync, SPacketChargeDataSync>
{

    int dataListCount = 0;
    int oilBankCount = 0;

    public SPacketChargeDataSync(){}

    public SPacketChargeDataSync(int dataListCount, int oilBankCount)
    {
        this.dataListCount = dataListCount;
        this.oilBankCount = oilBankCount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.dataListCount = buf.readInt();
        this.oilBankCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dataListCount);
        buf.writeInt(oilBankCount);
    }

    @Override
    public SPacketChargeDataSync onMessage(SPacketChargeDataSync message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            ChargeData data = Variable.Instance().m_chargeDataList.get(message.dataListCount);
            data.setAmount(message.oilBankCount);

            Variable.Instance().m_chargeDataList.set(message.dataListCount, data);

        });
        return null;
    }
}
