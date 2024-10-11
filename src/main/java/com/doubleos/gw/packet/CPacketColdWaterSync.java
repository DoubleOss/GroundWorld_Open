package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketColdWaterSync implements IMessage, IMessageHandler<CPacketColdWaterSync, CPacketColdWaterSync>
{

    float water = 0f;

    float cold = 0f;

    public CPacketColdWaterSync()
    {

    }

    public CPacketColdWaterSync(float water, float cold)
    {
        this.water = water;
        this.cold = cold;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        water = buf.readFloat();
        cold = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {

        buf.writeFloat(water);
        buf.writeFloat(cold);

    }

    @Override
    public CPacketColdWaterSync onMessage(CPacketColdWaterSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().waterAmount = message.water;
            Variable.Instance().coldAmount = message.cold;

        });
        return null;
    }
}
