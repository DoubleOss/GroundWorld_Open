package com.doubleos.gw.packet;


import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketOpenMovie implements IMessage, IMessageHandler<CPacketOpenMovie, CPacketOpenMovie>
{

    String m_name;

    public CPacketOpenMovie(String name)
    {
        m_name = name;
    }
    public CPacketOpenMovie()
    {

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        m_name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, m_name);
    }

    @Override
    public CPacketOpenMovie onMessage(CPacketOpenMovie message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.openMovieGui(message.m_name);
        });

        return null;
    }
}
