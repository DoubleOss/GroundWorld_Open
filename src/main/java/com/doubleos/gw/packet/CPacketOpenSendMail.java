package com.doubleos.gw.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketOpenSendMail implements IMessage, IMessageHandler<CPacketOpenSendMail, CPacketOpenSendMail>
{

    public CPacketOpenSendMail(){}




    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    @Override
    public CPacketOpenSendMail onMessage(CPacketOpenSendMail message, MessageContext ctx)
    {



        return null;
    }
}
