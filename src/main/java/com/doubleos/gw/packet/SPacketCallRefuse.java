package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketCallRefuse implements IMessage, IMessageHandler<SPacketCallRefuse, SPacketCallRefuse>
{

    String m_Status = "";
    String m_senderPlayer = "";

    public SPacketCallRefuse(){}

    public SPacketCallRefuse(Variable.WATCH_STATUS status, String senderPlayer)
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
    public SPacketCallRefuse onMessage(SPacketCallRefuse message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.m_senderPlayer);

        if(player != null)
        {
            player.sendMessage(new TextComponentString(" 상대방이 통화를 거절 했습니다."));
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
