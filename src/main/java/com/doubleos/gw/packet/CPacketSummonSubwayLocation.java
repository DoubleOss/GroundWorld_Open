package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSummonSubwayLocation implements IMessage, IMessageHandler<CPacketSummonSubwayLocation, CPacketSummonSubwayLocation>
{

    int number = 0;
    long posLong = 0l;


    public CPacketSummonSubwayLocation(){

    }

    public CPacketSummonSubwayLocation(int number, long posLong)
    {
        this.number = number;
        this.posLong = posLong;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        number = buf.readInt();
        posLong = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(number);
        buf.writeLong(posLong);
    }

    @Override
    public CPacketSummonSubwayLocation onMessage(CPacketSummonSubwayLocation message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {

            GroundWorld.proxy.startSubWayEffectLoc(message.number, BlockPos.fromLong( message.posLong));

        });
        return null;
    }
}
