package com.doubleos.gw.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketAddItemAmount implements IMessage, IMessageHandler<SPacketAddItemAmount,SPacketAddItemAmount>
{

    ItemStack m_stack;

    int m_amount = 0;

    public SPacketAddItemAmount() {
    }

    public SPacketAddItemAmount(ItemStack stack, int amount)
    {
        this.m_stack = stack;
        m_amount = amount;
    }



    @Override
    public void fromBytes(ByteBuf buf) {
        m_stack = ByteBufUtils.readItemStack(buf);
        m_amount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, m_stack);
        buf.writeInt(m_amount);
    }

    @Override
    public SPacketAddItemAmount onMessage(SPacketAddItemAmount message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            EntityPlayerMP playerMP = ctx.getServerHandler().player;
            int remainingAmount = message.m_amount;
            while (remainingAmount > 0) {
                int stackSize = Math.min(message.m_stack.getMaxStackSize(), remainingAmount);
                ItemStack itemStackToAdd = message.m_stack.copy();
                itemStackToAdd.setCount(stackSize);
                playerMP.inventory.addItemStackToInventory(itemStackToAdd);
                remainingAmount -= stackSize;
            }
        });
        return null;
    }
}
