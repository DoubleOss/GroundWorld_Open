package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.entity.EntitySubWay;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSubwaySummon implements IMessage, IMessageHandler<CPacketSubwaySummon, CPacketSubwaySummon>
{

    public CPacketSubwaySummon(){}



    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketSubwaySummon onMessage(CPacketSubwaySummon message, MessageContext ctx)
    {

        Minecraft.getMinecraft().addScheduledTask(()->
        {
            GroundWorld.proxy.startSubWayEffect();
        });

        return null;
    }
}
