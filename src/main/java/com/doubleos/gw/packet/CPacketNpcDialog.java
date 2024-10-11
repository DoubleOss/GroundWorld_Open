package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketNpcDialog implements IMessage, IMessageHandler<CPacketNpcDialog,CPacketNpcDialog>
{
    String resourceName = "";

    int min = 0;
    int max = 0;


    public CPacketNpcDialog(){}

    public CPacketNpcDialog(String resourceName, int min, int max)
    {
        this.resourceName = resourceName;
        this.min = min;
        this.max = max;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        resourceName = ByteBufUtils.readUTF8String(buf);
        min = buf.readInt();
        max = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, resourceName);
        buf.writeInt(min);
        buf.writeInt(max);
    }


    @Override
    public CPacketNpcDialog onMessage(CPacketNpcDialog message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.openGuiScreenDialog(message.resourceName, message.min, message.max);
        });
        return null;
    }
}
