package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPhoneStatus implements IMessage, IMessageHandler<CPacketPhoneStatus, CPacketPhoneStatus>
{

    String m_phoneCalling = "";

    public CPacketPhoneStatus(){}

    public CPacketPhoneStatus(Variable.WATCH_STATUS calling)
    {
        m_phoneCalling = calling.name();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_phoneCalling = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_phoneCalling);
    }

    @Override
    public CPacketPhoneStatus onMessage(CPacketPhoneStatus message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
           Variable variable = Variable.Instance();
           variable.m_phoneStatus = Variable.WATCH_STATUS.valueOf(message.m_phoneCalling);
           if(message.m_phoneCalling.equals(Variable.WATCH_STATUS.CONNETING.name()))
           {
               GroundWorld.proxy.stopSound(GwSoundHandler.PHONE_BELL);
           }

        });
        return null;
    }
}
