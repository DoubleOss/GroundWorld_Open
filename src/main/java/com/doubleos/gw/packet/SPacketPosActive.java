package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketPosActive implements IMessage, IMessageHandler<SPacketPosActive,SPacketPosActive>
{

    Long posLong = 0l;

    boolean active = false;

    public SPacketPosActive(BlockPos pos, boolean active)
    {
        posLong = pos.toLong();
        this.active = active;
    }

    public SPacketPosActive()
    {

    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        posLong = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(posLong);
    }

    @Override
    public SPacketPosActive onMessage(SPacketPosActive message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            Variable.Instance().hashPosToCheckActive.put(BlockPos.fromLong(message.posLong), message.active);
        });
        return null;
    }
}
