package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketCardReaderPlay implements IMessage, IMessageHandler<CPacketCardReaderPlay, CPacketCardReaderPlay>
{

    String playName = "";

    public CPacketCardReaderPlay(){}

    public CPacketCardReaderPlay(String name)
    {
        playName = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playName);
    }

    @Override
    public CPacketCardReaderPlay onMessage(CPacketCardReaderPlay message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().cardTileAnimationName = message.playName;
        });
        return null;
    }
}
