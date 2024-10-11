package com.doubleos.gw.packet;

import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketDrillChange implements IMessage, IMessageHandler<SPacketDrillChange, SPacketDrillChange>
{

    String playerName = "";

    public SPacketDrillChange()
    {

    }

    public SPacketDrillChange(String name)
    {
        this.playerName = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playerName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerName);
    }

    @Override
    public SPacketDrillChange onMessage(SPacketDrillChange message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.playerName);
        for(ItemStack stack : player.inventory.mainInventory)
        {
            if(stack != null)
            {
                if(stack.getItem().equals(ModItems.genedrill) || stack.getItem().equals(ModItems.upgrade_genedrill))
                {
                    OBJItemBase item = (OBJItemBase) stack.getItem();
                    item.m_powerOn = false;
                }
            }
        }
        return null;
    }
}
