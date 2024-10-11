package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketChangeNickName implements IMessage, IMessageHandler<CPacketChangeNickName, CPacketChangeNickName>
{

    public CPacketChangeNickName(String nickName, String viewName) {
        this.nickName = nickName;
        this.viewName = viewName;
    }
    public CPacketChangeNickName() {

    }

    String nickName = "";

    String viewName = "";

    @Override
    public void fromBytes(ByteBuf buf) {
        this.nickName = ByteBufUtils.readUTF8String(buf);
        this.viewName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, nickName);
        ByteBufUtils.writeUTF8String(buf, viewName);

    }

    @Override
    public CPacketChangeNickName onMessage(CPacketChangeNickName message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.m_nameToKor.put(message.nickName, message.viewName);
        });
        return null;
    }
}
