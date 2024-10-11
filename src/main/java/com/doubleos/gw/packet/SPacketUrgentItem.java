package com.doubleos.gw.packet;

import com.doubleos.gw.handler.MailHandler;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.CompletableFuture;

public class SPacketUrgentItem implements IMessage, IMessageHandler<SPacketUrgentItem,SPacketUrgentItem>
{


    int m_mailId = 0;

    boolean m_itemActive = false;

    public SPacketUrgentItem(){}

    public SPacketUrgentItem(int mailId, boolean itemActive)
    {
        m_mailId = mailId;
        m_itemActive = itemActive;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_mailId  = buf.readInt();
        m_itemActive = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(m_mailId);
        buf.writeBoolean(m_itemActive);

    }

    @Override
    public SPacketUrgentItem onMessage(SPacketUrgentItem message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        for(MailData data : variable.m_mailDataList)
        {
            if(data.m_mailId == message.m_mailId)
            {
                data.m_receiveActive = message.m_itemActive;
                
            }
        }
        return null;
    }
}
