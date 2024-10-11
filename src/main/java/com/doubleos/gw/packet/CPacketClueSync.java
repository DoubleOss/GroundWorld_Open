package com.doubleos.gw.packet;

import com.doubleos.gw.util.AnimationState;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketClueSync implements IMessage, IMessageHandler<CPacketClueSync, CPacketClueSync>
{

    boolean active = false;

    int number = 0;


    public CPacketClueSync(int number, boolean active)
    {
        this.number = number;
        this.active = active;
    }
    public CPacketClueSync(){

    }


    @Override
    public void fromBytes(ByteBuf buf) {
        number = buf.readInt();
        active = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(number);
        buf.writeBoolean(active);
    }

    @Override
    public CPacketClueSync onMessage(CPacketClueSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {

        });
        return null;
    }
}

