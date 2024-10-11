package com.doubleos.gw.packet;

import com.doubleos.gw.entity.EntityRobot;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketRobotSync implements IMessage, IMessageHandler<CPacketRobotSync,CPacketRobotSync>
{

    String robotMotion = "idle";

    int entityId = 0;

    public CPacketRobotSync()
    {

    }

    public CPacketRobotSync(int entityId, String robotMotion)
    {
        this.entityId = entityId;
        this.robotMotion = robotMotion;
        //System.out.println(" 엔티티 서버측 ID " + entityId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        robotMotion = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(entityId);
        ByteBufUtils.writeUTF8String(buf, robotMotion);
    }

    @Override
    public CPacketRobotSync onMessage(CPacketRobotSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            EntityRobot entity = (EntityRobot) Minecraft.getMinecraft().world.getEntityByID(message.entityId);
            if(message.robotMotion.equals("attack"))
            {
                entity.setAttacking(true);
                entity.setWalking(false);
            }
            else if(message.robotMotion.equals("idle"))
            {
                entity.setAttacking(false);
                entity.setWalking(false);
            }
            else if ( message.robotMotion.equals("walk"))
            {
                entity.setAttacking(false);
                entity.setWalking(true);
            }
            else
            {
                entity.setDeath(true);
            }
        });
        return null;
    }
}
