package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketOpenShop implements IMessage, IMessageHandler<CPacketOpenShop, CPacketOpenShop>
{

    String shopName = "";
    public CPacketOpenShop(String shopName)
    {
        this.shopName = shopName;
    }


    public CPacketOpenShop()
    {

    }




    @Override
    public void fromBytes(ByteBuf buf)
    {
        shopName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, shopName);
    }

    @Override
    public CPacketOpenShop onMessage(CPacketOpenShop message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.openGuiShop(message.shopName);
        });
        return null;
    }
}
