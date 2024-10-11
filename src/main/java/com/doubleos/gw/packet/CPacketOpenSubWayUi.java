package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketOpenSubWayUi implements IMessage, IMessageHandler<CPacketOpenSubWayUi, CPacketOpenSubWayUi>
{

    String start = "";
    String end = "";

    public CPacketOpenSubWayUi(String start, String end)
    {

        this.start = start;
        this.end = end;
    }
    public CPacketOpenSubWayUi()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        start = ByteBufUtils.readUTF8String(buf);
        end = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, start);
        ByteBufUtils.writeUTF8String(buf, end);
    }

    @Override
    public CPacketOpenSubWayUi onMessage(CPacketOpenSubWayUi message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.openSubwayQuestionUi(message.start, message.end);
        });
        return null;
    }
}
