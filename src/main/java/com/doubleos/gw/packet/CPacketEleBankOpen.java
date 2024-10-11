package com.doubleos.gw.packet;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketEleBankOpen implements IMessage, IMessageHandler<CPacketEleBankOpen, CPacketEleBankOpen>
{



    float m_bankOilCount = 0;

    int m_bankUseCount = 0;

    int m_playerOilCanAmount = 0;

    int m_oilListCount = 0;
    long posLong;

    boolean infinity = false;

    String item = "";

    public CPacketEleBankOpen()
    {

    }

    public CPacketEleBankOpen(float bankOilCount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, String item, boolean infinity)
    {
        m_bankOilCount = bankOilCount;
        m_bankUseCount = bankUseCount;
        m_playerOilCanAmount = playerOilCanAmount;
        this.m_oilListCount = oilListCount;
        posLong = pos.toLong();
        this.item = item;
        this.infinity = infinity;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_bankOilCount = buf.readFloat();
        m_bankUseCount = buf.readInt();
        m_playerOilCanAmount = buf.readInt();
        m_oilListCount = buf.readInt();
        posLong = buf.readLong();
        item = ByteBufUtils.readUTF8String(buf);
        infinity = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(m_bankOilCount);
        buf.writeInt(m_bankUseCount);
        buf.writeInt((m_playerOilCanAmount));
        buf.writeInt(m_oilListCount);
        buf.writeLong(posLong);
        ByteBufUtils.writeUTF8String(buf, item);
        buf.writeBoolean(infinity);

    }

    @Override
    public CPacketEleBankOpen onMessage(CPacketEleBankOpen message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            System.out.println( "변환후  :" + BlockPos.fromLong(message.posLong));
            GroundWorld.proxy.openEleBankScreen(message.m_bankOilCount, message.m_bankUseCount, message.m_playerOilCanAmount, message.m_oilListCount, BlockPos.fromLong(message.posLong), message.item, message.infinity);
        });
        return null;
    }
}
