package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketNewWorkBench implements IMessage, IMessageHandler<SPacketNewWorkBench, SPacketNewWorkBench>
{

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public SPacketNewWorkBench onMessage(SPacketNewWorkBench message, MessageContext ctx)
    {
        EntityPlayerMP entplayer = (ctx.getServerHandler()).player;
        entplayer.openGui(GroundWorld.instance, 2, entplayer.getEntityWorld(), (int)entplayer.posX, (int)entplayer.posY, (int)entplayer.posZ);
        return null;
    }
}
