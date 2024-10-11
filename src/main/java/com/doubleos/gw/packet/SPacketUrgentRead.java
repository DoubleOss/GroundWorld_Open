package com.doubleos.gw.packet;

import com.doubleos.gw.handler.MailHandler;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.CompletableFuture;

public class SPacketUrgentRead implements IMessage, IMessageHandler<SPacketUrgentRead, SPacketUrgentRead>
{
    int m_id = 0;
    boolean m_read = false;
    public SPacketUrgentRead(){}

    public SPacketUrgentRead(int id, boolean read)
    {
        m_id = id;
        m_read = read;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_id  = buf.readInt();
        m_read = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(m_id);
        buf.writeBoolean(m_read);

    }


    @Override
    public SPacketUrgentRead onMessage(SPacketUrgentRead message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        for(MailData data : variable.m_mailDataList)
        {
            if(data.m_mailId == message.m_id)
            {
                data.m_readActive = message.m_read;

                CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                {
                    MailHandler.writeConfig("mail_category", "mail_read_"+data.m_mailId, String.valueOf(data.m_readActive));
                    return "";
                });

            }
        }

        return null;
    }
}
