package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketChargeDataClear implements IMessage, IMessageHandler<CPacketChargeDataClear, CPacketChargeDataPacketSync>
{

    public CPacketChargeDataClear(){}



    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }



    @Override
    public CPacketChargeDataPacketSync onMessage(CPacketChargeDataClear message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().m_chargeDataList.clear();
        });
        return null;
    }
}
