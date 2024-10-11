package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Quest;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketQuestInfo implements IMessage, IMessageHandler<CPacketQuestInfo, CPacketQuestInfo>
{

    int m_page;
    String m_imageName = "";
    boolean m_success = false;
    boolean m_view = false;

    public CPacketQuestInfo(){}

    public CPacketQuestInfo(int page, String imageName, boolean success, boolean view)
    {
        m_page = page;
        m_imageName = imageName;
        m_success = success;
        m_view = view;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_page = buf.readInt();
        m_imageName = ByteBufUtils.readUTF8String(buf);
        m_success = buf.readBoolean();
        m_view = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(m_page);
        ByteBufUtils.writeUTF8String(buf, m_imageName);
        buf.writeBoolean(m_success);
        buf.writeBoolean(m_view);

    }

    @Override
    public CPacketQuestInfo onMessage(CPacketQuestInfo message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Quest quest = variable.m_questList.get(message.m_page);
            quest.m_questPage = message.m_page;
            quest.m_imageName = message.m_imageName;
            quest.m_Success = message.m_success;
            quest.m_viewActive = message.m_view;


        });
        return null;
    }
}
