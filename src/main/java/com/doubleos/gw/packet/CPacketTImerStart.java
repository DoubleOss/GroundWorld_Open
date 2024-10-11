package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketTImerStart implements IMessage, IMessageHandler<CPacketTImerStart, CPacketTImerStart>
{

    boolean m_active = false;

    public CPacketTImerStart(){}

    public CPacketTImerStart(boolean active)
    {
        m_active = active;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_active = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(m_active);
    }

    @Override
    public CPacketTImerStart onMessage(CPacketTImerStart message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().m_timerStart = message.m_active;
        });
        return null;
    }
}
