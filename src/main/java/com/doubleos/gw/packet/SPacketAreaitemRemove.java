package com.doubleos.gw.packet;

import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketAreaitemRemove implements IMessage, IMessageHandler<SPacketAreaitemRemove,SPacketAreaitemRemove>
{

    String itemName = "";
    int areaCount = 0;

    public SPacketAreaitemRemove() {
    }

    public SPacketAreaitemRemove(int areaCount, String itemName)
    {
        this.itemName = itemName;
        this.areaCount = areaCount;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        itemName = ByteBufUtils.readUTF8String(buf);
        areaCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, itemName);
        buf.writeInt(areaCount);
    }

    @Override
    public SPacketAreaitemRemove onMessage(SPacketAreaitemRemove message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            for(AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
            {
                if( area.areaId == message.areaCount )
                {
                    if(message.itemName.equals("stone"))
                        area.m_stone = 0;
                    if(message.itemName.equals("coal"))
                        area.m_coal = 0;
                    if(message.itemName.equals("iron"))
                        area.m_iron = 0;
                    if(message.itemName.equals("gold"))
                        area.m_gold = 0;
                    if(message.itemName.equals("al"))
                        area.m_aluminum = 0;
                    if(message.itemName.equals("dia"))
                        area.m_diamond = 0;
                    if(message.itemName.equals("eme"))
                        area.m_emerald = 0;
                    if(message.itemName.equals("red"))
                        area.m_redstone = 0;
                    if(message.itemName.equals("lap"))
                        area.m_lapis = 0;
                    if(message.itemName.equals("hard"))
                        area.m_hardtack = 0;
                    if(message.itemName.equals("stick"))
                        area.m_stick = 0;
                    if(message.itemName.equals("cloth"))
                        area.m_cloth = 0;
                    if(message.itemName.equals("plastic"))
                        area.plastic = 0;
                    if(message.itemName.equals("gear"))
                        area.m_gear = 0;
                    if(message.itemName.equals("vitamin"))
                        area.m_vitamin = 0;
                    if(message.itemName.equals("robot"))
                        area.m_robotCore = 0;

                    Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(area.toBytes()));

                }
            }
        });
        return null;
    }
}
