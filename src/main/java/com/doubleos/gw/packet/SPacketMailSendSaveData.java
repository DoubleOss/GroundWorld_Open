package com.doubleos.gw.packet;

import com.doubleos.gw.handler.MailHandler;
import com.doubleos.gw.util.MailData;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketMailSendSaveData implements IMessage, IMessageHandler<SPacketMailSendSaveData, SPacketMailSendSaveData>
{
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public SPacketMailSendSaveData onMessage(SPacketMailSendSaveData message, MessageContext ctx)
    {
        MailHandler.saveConfig(true);
        return null;
    }
}
