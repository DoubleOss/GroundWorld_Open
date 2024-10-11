package com.doubleos.gw.packet;


import com.doubleos.gw.util.CinematicAniState;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CCinemaDirect implements IMessage, IMessageHandler<CCinemaDirect, CCinemaDirect>
{
    float m_animationTime = 1;
    float m_animationDelay = 1;


    public CCinemaDirect(){}

    public CCinemaDirect(float animationTime, float animationDelay)
    {
        m_animationTime = animationTime;
        m_animationDelay = animationDelay;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        m_animationTime = buf.readFloat();
        m_animationDelay = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(m_animationTime);
        buf.writeFloat(m_animationDelay);
    }

    @Override
    public CCinemaDirect onMessage(CCinemaDirect message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();
        CinematicAniState state = new CinematicAniState(message.m_animationTime, message.m_animationDelay);


        if(variable.m_cinematicAniState.size() == 0)
            variable.m_cinematicAniState.add(state);
        else {
            for(int i = 0; i<variable.m_cinematicAniState.size(); i++)
            {
                CinematicAniState checkState = variable.m_cinematicAniState.get(i);

                if(i == variable.m_cinematicAniState.size())
                {
                    variable.m_cinematicAniState.add(state);
                }

            }
        }


        return null;
    }
}
