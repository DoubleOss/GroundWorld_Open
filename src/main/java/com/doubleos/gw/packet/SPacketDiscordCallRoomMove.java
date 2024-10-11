package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.variable.PlayerInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.awt.*;
import java.util.List;

public class SPacketDiscordCallRoomMove implements IMessage, IMessageHandler<SPacketDiscordCallRoomMove, SPacketDiscordCallRoomMove>
{

    String m_roomName = "";

    String m_movePlayer = "";

    public SPacketDiscordCallRoomMove(){}

    public SPacketDiscordCallRoomMove(String roomName, String movePlayer)
    {
        m_roomName = roomName;
        m_movePlayer = movePlayer;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        m_roomName = ByteBufUtils.readUTF8String(buf);
        m_movePlayer = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_roomName);
        ByteBufUtils.writeUTF8String(buf, m_movePlayer);

    }

    @Override
    public SPacketDiscordCallRoomMove onMessage(SPacketDiscordCallRoomMove message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.m_movePlayer);
//
//        PlayerInfo info = GroundWorld.instance.playerInfoMap.get(player.getName());
//        if(info == null) {
//            player.sendMessage( new TextComponentString("[디스코드]"+ " 서버에 등록되어 있지 않아 통화방에 연결할 수 없습니다."));
//        }
//        Member member = null;
//        if(info.getTag().contains("#")) {
//            member = GroundWorld.instance.guild.getMemberByTag(info.getTag());
//        } else
//        {
//            List<Member> memberList = GroundWorld.instance.guild.getMembersByName(info.getTag(), true);
//            if(memberList != null && !memberList.isEmpty())
//            {
//                member = memberList.get(0);
//            }
//        }
//        if(member == null)
//        {
//            player.sendMessage(new TextComponentString(Color.BLUE + "[디스코드]" + Color.WHITE + " member is null"));
//            return null;
//        }
//        if(!member.getVoiceState().inAudioChannel()) {
//            player.sendMessage(new TextComponentString(Color.BLUE + "[디스코드]" + Color.WHITE + " 음성채널에 연결하신 뒤에 시도해주세요."));
//            return null;
//        }
//        if(message.m_roomName.equals("양띵"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_d7297);
//        else if(message.m_roomName.equals("다주"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_daju);
//        else if(message.m_roomName.equals("눈꽃"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_noon);
//        else if(message.m_roomName.equals("루태"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_rutae);
//        else if(message.m_roomName.equals("후추"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_huchu);
//        else if(message.m_roomName.equals("삼식"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_samsik);
//        else if(message.m_roomName.equals("서넹"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_seo);
//        else if(message.m_roomName.equals("콩콩"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_kong);
//        else if(message.m_roomName.equals("메인"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channle_Main);
//        else if(message.m_roomName.equals("운영자"))
//            GroundWorld.instance.moveVoice(member, GroundWorld.instance.channel_Operator);

        return null;
    }
}
