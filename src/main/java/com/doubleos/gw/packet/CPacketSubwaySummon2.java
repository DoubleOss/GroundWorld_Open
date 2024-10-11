package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSubwaySummon2 implements IMessage, IMessageHandler<CPacketSubwaySummon2, CPacketSubwaySummon2>
{

    public CPacketSubwaySummon2(){}



    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketSubwaySummon2 onMessage(CPacketSubwaySummon2 message, MessageContext ctx)
    {

        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.startSubWayEffectEast();
        });

        return null;
    }
}
