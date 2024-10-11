package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketOpenAutoMine implements IMessage, IMessageHandler<CPacketOpenAutoMine, CPacketOpenAutoMine>
{

    int npcNumber = 0;
    int npcName = 0;



    public CPacketOpenAutoMine(int npcNumber, int npcName)
    {
        this.npcNumber = npcNumber;
        this.npcName = npcName;
    }
    public CPacketOpenAutoMine()
    {

    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        npcNumber = buf.readInt();
        npcName = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(npcNumber);
        buf.writeInt(npcName);
    }

    @Override
    public CPacketOpenAutoMine onMessage(CPacketOpenAutoMine message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.openGuiScreenAutoMine(message.npcNumber, message.npcName);
        });

        return null;
    }
}
