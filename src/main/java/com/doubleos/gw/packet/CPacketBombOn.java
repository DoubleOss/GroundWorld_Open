package com.doubleos.gw.packet;

import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketBombOn implements IMessage, IMessageHandler<CPacketBombOn, CPacketBombOn>
{

    String bombAniName = "";

    boolean bombActive = false;


    public CPacketBombOn() {
    }

    public CPacketBombOn(String aniName, boolean bombActive)
    {
        this.bombAniName = aniName;
        this.bombActive = bombActive;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        bombAniName = ByteBufUtils.readUTF8String(buf);
        this.bombActive = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, bombAniName);
        buf.writeBoolean(bombActive);
    }

    @Override
    public CPacketBombOn onMessage(CPacketBombOn message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.boomTick = 0;
            variable.boomAni = message.bombAniName;
            variable.boomIsActive = message.bombActive;
        });

        return null;
    }
}
