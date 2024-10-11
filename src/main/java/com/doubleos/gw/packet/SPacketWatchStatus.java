package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketWatchStatus implements IMessage, IMessageHandler<SPacketWatchStatus, SPacketWatchStatus>
{

    String m_Status = "";
    String m_senderPlayer = "";

    public SPacketWatchStatus(){}

    public SPacketWatchStatus(Variable.WATCH_STATUS status, String senderPlayer)
    {
        m_Status = status.name();
        m_senderPlayer = senderPlayer;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_Status = ByteBufUtils.readUTF8String(buf);
        m_senderPlayer = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_Status);
        ByteBufUtils.writeUTF8String(buf, m_senderPlayer);
    }

    @Override
    public SPacketWatchStatus onMessage(SPacketWatchStatus message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.m_senderPlayer);
        if(player != null)
        {
            Packet.networkWrapper.sendTo(new CPacketPhoneStatus(Variable.WATCH_STATUS.valueOf( message.m_Status)), player);
            if(message.m_Status.equals(Variable.WATCH_STATUS.IDLE.name()))
            {
                Packet.networkWrapper.sendTo(new CPacketCallPlayer(Variable.PHONE_CALLING_PLAYER.NONE), (EntityPlayerMP) player);
                Packet.networkWrapper.sendTo(new CPacketPhoneGuiViewStatus(Variable.PHONE_GUI_VIEW_STATUS.CONTENT), (EntityPlayerMP) player);

            }
        }

        return null;
    }
}
