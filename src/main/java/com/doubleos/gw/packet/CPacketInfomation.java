package com.doubleos.gw.packet;

import com.doubleos.gw.util.AnimationState;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketInfomation implements IMessage, IMessageHandler<CPacketInfomation, CPacketInfomation>
{

    String m_name = "";


    public CPacketInfomation(String aniName)
    {
        m_name = aniName;
    }
    public CPacketInfomation(){

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
    public CPacketInfomation onMessage(CPacketInfomation message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().m_animationBroadcastList.add(new AnimationState(message.m_name));
        });
        return null;
    }
}
