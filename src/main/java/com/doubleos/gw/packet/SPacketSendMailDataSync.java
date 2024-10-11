package com.doubleos.gw.packet;

import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketSendMailDataSync implements IMessage, IMessageHandler<SPacketSendMailDataSync, SPacketSendMailDataSync>
{




    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public SPacketSendMailDataSync onMessage(SPacketSendMailDataSync message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();

        for(int i = 0; i<variable.m_mailDataList.size(); i++)
        {
            MailData data = variable.m_mailDataList.get(i);

            Packet.networkWrapper.sendTo(new CPacketMailData(data.m_mailId, data.m_title, data.m_text, data.m_date,data.m_receiveActive, data.m_readActive, data.m_recivePlayerName, data.m_stack1, data.m_stack2, data.m_stack3, data.m_stack4, data.m_stack5), ctx.getServerHandler().player);
        }
        return null;
    }
}


