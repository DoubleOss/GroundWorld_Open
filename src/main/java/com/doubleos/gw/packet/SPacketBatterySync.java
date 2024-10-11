package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketBatterySync implements IMessage, IMessageHandler<SPacketBatterySync, SPacketBatterySync>
{

    String name = "";

    int battery = 0;

    public SPacketBatterySync()
    {

    }

    public SPacketBatterySync(String name, int battery)
    {
        this.name = name;
        this.battery = battery;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        name = ByteBufUtils.readUTF8String(buf);
        battery = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, name);
        buf.writeInt(battery);
    }

    @Override
    public SPacketBatterySync onMessage(SPacketBatterySync message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.hashPlayerToBattery.put(message.name, message.battery);
        });
        return null;
    }
}
