package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPhoneOpen implements IMessage, IMessageHandler<CPacketPhoneOpen,CPacketPhoneOpen>
{

    boolean m_antenna = false;
    public CPacketPhoneOpen(){}

    public CPacketPhoneOpen(boolean antenna)
    {
        m_antenna = antenna;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_antenna = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(m_antenna);
    }

    @Override
    public CPacketPhoneOpen onMessage(CPacketPhoneOpen message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.openGuiScreenPhoneOpen(message.m_antenna);
        });
        return null;
    }
}
