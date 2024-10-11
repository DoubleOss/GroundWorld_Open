package com.doubleos.gw.packet;

import com.doubleos.gw.handler.MailHandler;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.CompletableFuture;

public class SPacketMailDataGive implements IMessage, IMessageHandler<SPacketMailDataGive, SPacketMailDataGive>
{

    int m_mailId = 0;

    boolean m_giveActive =false;

    public SPacketMailDataGive(){}

    public SPacketMailDataGive(int mailId, boolean giveActive)
    {
        m_mailId = mailId;
        m_giveActive = giveActive;
    }


    @Override
    public void fromBytes(ByteBuf buf) {

        m_mailId = buf.readInt();
        m_giveActive = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(m_mailId);
        buf.writeBoolean(m_giveActive);

    }

    @Override
    public SPacketMailDataGive onMessage(SPacketMailDataGive message, MessageContext ctx)
    {

        Variable  variable = Variable.Instance();
        EntityPlayerMP playerMP = ctx.getServerHandler().player;

        for(int i = 0; i<variable.m_mailDataList.size(); i++)
        {
            MailData data = variable.m_mailDataList.get(i);
            if(data.m_mailId == message.m_mailId)
            {
                data.m_receiveActive = message.m_giveActive;
                data.give(playerMP.getServer(), playerMP.getName());

            }
        }


        return null;
    }
}
