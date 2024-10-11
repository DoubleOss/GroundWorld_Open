package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketDiscordCallReicive implements IMessage, IMessageHandler<CPacketDiscordCallReicive, CPacketDiscordCallReicive>
{

    String m_watchStatus = "";

    String m_callingPlayer = "";

    String m_recivePlayer = "";

    public CPacketDiscordCallReicive(String watchStatus, String callingPlayer, String recivePlayer)
    {
        m_watchStatus = watchStatus;
        m_callingPlayer = callingPlayer;
        m_recivePlayer= recivePlayer;
    }
    public CPacketDiscordCallReicive()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        m_watchStatus = ByteBufUtils.readUTF8String(buf);
        m_callingPlayer = ByteBufUtils.readUTF8String(buf);
        m_recivePlayer = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, m_watchStatus);
        ByteBufUtils.writeUTF8String(buf, m_callingPlayer);
        ByteBufUtils.writeUTF8String(buf, m_recivePlayer);
    }

    @Override
    public CPacketDiscordCallReicive onMessage(CPacketDiscordCallReicive message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            boolean phoneActive = false;
            for(ItemStack stack : Minecraft.getMinecraft().player.inventory.mainInventory)
            {
                if(stack.getItem().equals(ModItems.phonew_ant))
                    phoneActive = true;
            }
            Variable variable = Variable.Instance();
            if( variable.m_phoneStatus.equals(Variable.WATCH_STATUS.IDLE) && phoneActive )
            {
                variable.m_phoneStatus = Variable.WATCH_STATUS.valueOf(message.m_watchStatus);
                variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.valueOf(message.m_recivePlayer);
                if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER))
                {
                    variable.m_phoneGuiStatus = Variable.PHONE_GUI_VIEW_STATUS.CALL;
                    GroundWorld.proxy.playSound(GwSoundHandler.PHONE_BELL);
                }
            }
            else //전화 걸려오거나 이미 통화중일 경우  또는 아직 스마트폰 해금 못했을 경우
            {
                Packet.networkWrapper.sendToServer(new SPacketCallNot(Variable.WATCH_STATUS.IDLE, message.m_recivePlayer));

            }


        });
        return null;
    }
}
