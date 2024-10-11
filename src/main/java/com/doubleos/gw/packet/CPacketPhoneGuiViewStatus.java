package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPhoneGuiViewStatus implements IMessage, IMessageHandler<CPacketPhoneGuiViewStatus, CPacketPhoneGuiViewStatus>
{

    String m_status = "";

    public CPacketPhoneGuiViewStatus(Variable.PHONE_GUI_VIEW_STATUS status)
    {
        m_status = status.name();
    }

    public CPacketPhoneGuiViewStatus()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_status = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_status);
    }

    @Override
    public CPacketPhoneGuiViewStatus onMessage(CPacketPhoneGuiViewStatus message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {

            Variable variable = Variable.Instance();
            variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.valueOf(message.m_status);
        });
        return null;
    }
}
