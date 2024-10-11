package com.doubleos.gw.packet;

import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.variable.GroundItemStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketItemAdd implements IMessage, IMessageHandler<SPacketItemAdd, SPacketItemAdd>
{

    ItemStack m_stack;

    public SPacketItemAdd()
    {

    }
    public SPacketItemAdd(ItemStack stack)
    {
        m_stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        m_stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, m_stack);
    }

    @Override
    public SPacketItemAdd onMessage(SPacketItemAdd message, MessageContext ctx)
    {

        if(message.m_stack.getItem().equals(ModItems.bomb_item))
        {
            ctx.getServerHandler().player.sendMessage(new TextComponentString(" 폭탄 사용법 이 지급되었습니다."));
            ctx.getServerHandler().player.inventory.addItemStackToInventory(GroundItemStack.BOOM_PAPER.copy());

        }
        ctx.getServerHandler().player.inventory.addItemStackToInventory(message.m_stack.copy());
        return null;
    }
}
