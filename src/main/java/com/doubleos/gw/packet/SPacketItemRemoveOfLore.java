package com.doubleos.gw.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketItemRemoveOfLore implements IMessage, IMessageHandler<SPacketItemRemoveOfLore, SPacketItemRemoveOfLore>
{

    ItemStack m_stack;

    int m_amount = 1;

    public SPacketItemRemoveOfLore() {}

    public SPacketItemRemoveOfLore(ItemStack stack, int amount){
        m_stack = stack;
        m_amount = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_stack = ByteBufUtils.readItemStack(buf);
        m_amount = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, m_stack);
        buf.writeInt(m_amount);
    }

    @Override
    public SPacketItemRemoveOfLore onMessage(SPacketItemRemoveOfLore message, MessageContext ctx)
    {

        int removedAmount = 0;
        EntityPlayerMP player = ctx.getServerHandler().player;
        int amount = message.m_amount;
        //System.out.println(message.m_stack);
        int saveAmount = message.m_amount;

        ItemStack itemStack = message.m_stack;
        NonNullList<ItemStack> inventory = player.inventory.mainInventory;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stackInSlot = inventory.get(i);

            if (!stackInSlot.isEmpty() && stackInSlot.getItem() == itemStack.getItem() && ItemStack.areItemStackTagsEqual(stackInSlot, itemStack)) {
                stackInSlot.shrink(1); // 아이템 1개 제거
                if (stackInSlot.getCount() <= 0) {
                    inventory.set(i, ItemStack.EMPTY); // 스택이 비었다면 슬롯 비우기
                }
                break; // 첫 번째로 발견한 슬롯에서만 아이템 제거 후 종료
            }
        }
        return null;
    }
}
