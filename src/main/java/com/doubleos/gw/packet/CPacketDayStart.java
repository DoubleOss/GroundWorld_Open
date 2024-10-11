package com.doubleos.gw.packet;


import com.doubleos.gw.util.AnimationState;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketDayStart implements IMessage, IMessageHandler<CPacketDayStart, CPacketDayStart>
{


    float m_animationTime = 1;
    float m_animationDelay = 1;

    String name = "";



    public CPacketDayStart(){}
    public CPacketDayStart(String name, float animationTime, float animationDelay)
    {

        this.name = name;
        m_animationTime = animationTime;
        m_animationDelay = animationDelay;


    }
    @Override
    public void fromBytes(ByteBuf buf)
    {

        name = ByteBufUtils.readUTF8String(buf);

        m_animationTime = buf.readFloat();
        m_animationDelay = buf.readFloat();



    }

    @Override
    public void toBytes(ByteBuf buf)
    {

        ByteBufUtils.writeUTF8String(buf,name);
        buf.writeFloat(m_animationTime);
        buf.writeFloat(m_animationDelay);

    }

    @Override
    public CPacketDayStart onMessage(CPacketDayStart message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        AnimationState state = new AnimationState(message.m_animationTime, message.m_animationDelay);

        state.m_String = message.name;

        if(variable.m_animationDayStart.size() == 0)
            variable.m_animationDayStart.add(state);
        else {
            for(int i = 0; i<variable.m_animationDayStart.size(); i++)
            {
                AnimationState checkState = variable.m_animationDayStart.get(i);

                if(i == variable.m_animationDayStart.size())
                {
                    variable.m_animationDayStart.add(state);
                }

            }
        }


        //System.out.println("페이드 인 시작 " + LocalTime.now());
        return null;
    }
}
