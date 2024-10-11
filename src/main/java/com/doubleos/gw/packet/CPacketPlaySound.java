package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPlaySound implements IMessage, IMessageHandler<CPacketPlaySound, CPacketPlaySound>
{

    String m_soundName = "";
    public CPacketPlaySound()
    {

    }

    public CPacketPlaySound(String soundName)
    {
        m_soundName = soundName;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        m_soundName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, m_soundName);
    }

    @Override
    public CPacketPlaySound onMessage(CPacketPlaySound message, MessageContext ctx)
    {
        Minecraft minecraft = Minecraft.getMinecraft();

        minecraft.addScheduledTask(()->
        {
            GroundWorld.proxy.playSound(GwSoundHandler.m_hashStringGetSoundEvent.get(message.m_soundName));
            //minecraft.player.playSound(event, 1, 1);
        });
        return null;
    }
}
