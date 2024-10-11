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

import java.rmi.MarshalException;

public class CPacketOilBankOpen implements IMessage, IMessageHandler<CPacketOilBankOpen, CPacketOilBankOpen>
{



    float m_bankOilCount = 0;

    int m_bankUseCount = 0;

    int m_playerOilCanAmount = 0;

    int m_oilListCount = 0;
    long posLong;

    boolean infinity = false;

    public CPacketOilBankOpen()
    {

    }

    public CPacketOilBankOpen(float bankOilCount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, boolean infinity)
    {
        m_bankOilCount = bankOilCount;
        m_bankUseCount = bankUseCount;
        m_playerOilCanAmount = playerOilCanAmount;
        this.m_oilListCount = oilListCount;
        posLong = pos.toLong();
        infinity = false;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_bankOilCount = buf.readFloat();
        m_bankUseCount = buf.readInt();
        m_playerOilCanAmount = buf.readInt();
        m_oilListCount = buf.readInt();
        posLong = buf.readLong();
        infinity =buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(m_bankOilCount);
        buf.writeInt(m_bankUseCount);
        buf.writeInt((m_playerOilCanAmount));
        buf.writeInt(m_oilListCount);
        buf.writeLong(posLong);
        buf.writeBoolean(infinity);

    }

    @Override
    public CPacketOilBankOpen onMessage(CPacketOilBankOpen message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            System.out.println( "변환후  :" + BlockPos.fromLong(message.posLong));
            GroundWorld.proxy.openBankScreen(message.m_bankOilCount, message.m_bankUseCount, message.m_playerOilCanAmount, message.m_oilListCount, BlockPos.fromLong(message.posLong), message.infinity);
        });
        return null;
    }
}
