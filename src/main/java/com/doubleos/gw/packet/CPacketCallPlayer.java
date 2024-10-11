package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketCallPlayer implements IMessage, IMessageHandler<CPacketCallPlayer, CPacketCallPlayer>
{

    String m_playerName = "";

    public CPacketCallPlayer(){

    }

    public CPacketCallPlayer(Variable.PHONE_CALLING_PLAYER name)
    {
        m_playerName = name.name();
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_playerName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_playerName);
    }

    @Override
    public CPacketCallPlayer onMessage(CPacketCallPlayer message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
           Variable variable = Variable.Instance();
           variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.valueOf(message.m_playerName);
           if(message.m_playerName.equals(Variable.PHONE_CALLING_PLAYER.NONE.name()))
           {
               GroundWorld.proxy.stopSound(GwSoundHandler.PHONE_BELL);
               GroundWorld.proxy.playSound(GwSoundHandler.CALL_DISCONNET);
           }

        });
        return null;
    }
}
