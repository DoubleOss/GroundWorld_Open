package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketTimerSync implements IMessage, IMessageHandler<CPacketTimerSync, CPacketTimerSync>
{

    int m_min = 0;
    int m_sec = 0;
    public CPacketTimerSync(){}

    public CPacketTimerSync(int min, int sec)
    {
        m_min = min;
        m_sec = sec;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_min = buf.readInt();
        m_sec = buf.readInt();
        

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(m_min);
        buf.writeInt(m_sec);
    }

    @Override
    public CPacketTimerSync onMessage(CPacketTimerSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.m_gameMin = message.m_min;
            variable.m_gameSec = message.m_sec;
        });

        return null;
    }
}
