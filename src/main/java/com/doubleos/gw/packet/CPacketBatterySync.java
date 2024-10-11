package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketBatterySync implements IMessage, IMessageHandler<CPacketBatterySync,CPacketBatterySync>
{

    int battery = 0;
    public CPacketBatterySync()
    {

    }

    public CPacketBatterySync(int battery)
    {
        this.battery = battery;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        battery = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(battery);
    }

    @Override
    public CPacketBatterySync onMessage(CPacketBatterySync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().currentBattery = message.battery;
        });
        return null;
    }
}
