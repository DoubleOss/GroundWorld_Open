package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketChangeToolActive implements IMessage, IMessageHandler<CPacketChangeToolActive, CPacketChangeToolActive>
{

    boolean active = false;
    int battery = 0;

    public CPacketChangeToolActive(){}

    public CPacketChangeToolActive(boolean active, int battery)
    {
        this.active = active;
        this.battery = battery;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        active = buf.readBoolean();
        battery = buf.readInt();


    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(active);
        buf.writeInt(battery);
    }

    @Override
    public CPacketChangeToolActive onMessage(CPacketChangeToolActive message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().m_changeMod = message.active;
            Variable.Instance().m_changeHealth = message.battery;
        });
        return null;
    }
}
