package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketAmbientSound implements IMessage, IMessageHandler<CPacketAmbientSound, CPacketAmbientSound>
{


    String m_soundName = "";

    public CPacketAmbientSound(String soundName)
    {
        m_soundName = soundName;
    }

    public CPacketAmbientSound()
    {

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
    public CPacketAmbientSound onMessage(CPacketAmbientSound message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            if(message.m_soundName.equals("stop"))
            {
                GroundWorld.proxy.stopAmbientSound();
            }
            else
            {
                //GroundWorld.proxy.playAmbientSound(GwSoundHandler.m_hashStringGetSoundEvent.get(message.m_soundName));
                GroundWorld.proxy.playAmbientSound(message.m_soundName);

            }

        });
        return null;
    }
}
