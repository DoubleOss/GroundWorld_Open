package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketDisCordCallRecive implements IMessage, IMessageHandler<SPacketDisCordCallRecive, SPacketDisCordCallRecive>
{

    String m_watchStatus = "";

    String m_callingPlayer = "";

    String m_recivePlayer = "";


    public SPacketDisCordCallRecive(){}

    public SPacketDisCordCallRecive(Variable.WATCH_STATUS watchStatus, String callingPlayer, String recivePlayer)
    {
        m_watchStatus = watchStatus.name();
        m_callingPlayer = callingPlayer;
        m_recivePlayer= recivePlayer;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_watchStatus = ByteBufUtils.readUTF8String(buf);
        m_callingPlayer = ByteBufUtils.readUTF8String(buf);
        m_recivePlayer = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_watchStatus);
        ByteBufUtils.writeUTF8String(buf, m_callingPlayer);
        ByteBufUtils.writeUTF8String(buf, m_recivePlayer);
    }

    @Override
    public SPacketDisCordCallRecive onMessage(SPacketDisCordCallRecive message, MessageContext ctx)
    {
        EntityPlayerMP recivePlayer = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.m_callingPlayer);
        if (recivePlayer != null)
            Packet.networkWrapper.sendTo(new CPacketDiscordCallReicive(message.m_watchStatus, message.m_callingPlayer, message.m_recivePlayer), recivePlayer);

        return null;
    }
}
