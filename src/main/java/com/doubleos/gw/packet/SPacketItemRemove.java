package com.doubleos.gw.packet;

import com.doubleos.gw.util.InventoryUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketItemRemove implements IMessage, IMessageHandler<SPacketItemRemove, SPacketItemRemove>
{
    ItemStack m_stack;

    int m_amount = 1;

    public SPacketItemRemove() {}

    public SPacketItemRemove(ItemStack stack, int amount){
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
    public SPacketItemRemove onMessage(SPacketItemRemove message, MessageContext ctx)
    {

        int removedAmount = 0;
        EntityPlayerMP player = ctx.getServerHandler().player;
        int amount = message.m_amount;
        //System.out.println(message.m_stack);
        int saveAmount = message.m_amount;
        InventoryPlayer inventory = player.inventory;

        InventoryUtils.removePlayerItem(player, message.m_stack, message.m_amount);

//        if(message.m_amount > 64)
//        {
//            for(int j = message.m_amount / 64; j>= 0; j-- )
//            {
//                int removeAmount = (saveAmount / 64) > 0 ? 64 : saveAmount % 64;
//                for (int i = 0; i < player.inventory.getSizeInventory(); i++)
//                {
//                    ItemStack slotStack = player.inventory.getStackInSlot(i);
//                    //System.out.println(" j  " + j +  "   " + removeAmount  + "   " + (saveAmount / 64));
//                    ItemStack sendStack = message.m_stack.copy();
//                    sendStack.setCount(removeAmount);
//                    int stackAmount = sendStack.getCount();
//                    //System.out.println("스택비교 " + slotStack.isItemEqual(sendStack) + "  " + slotStack + "  "  + sendStack + "  " + removeAmount);
//                    if (slotStack.getItem().equals(sendStack.getItem()))
//                    {
//                        int stackSize = slotStack.getCount();
//                        //System.out.println("이프문쪽" + (stackSize >= stackAmount));
//                        if (stackSize >= stackAmount) {
//                            inventory.decrStackSize(i, stackAmount);
//                            removedAmount += stackAmount;
//                            break;
//                        } else {
//                            inventory.decrStackSize(i, stackSize);
//                            removedAmount += stackSize;
//                            stackAmount -= stackSize;
//                        }
//                    }
//
//                }
//                saveAmount -= removeAmount;
//
//            }
//        }
//        else
//        {
//            int amountToRemove = amount;
//            for (int i = 0; i < player.inventory.getSizeInventory() && amountToRemove > 0; i++)
//            {
//                ItemStack slotStack = player.inventory.getStackInSlot(i);
//                if (!slotStack.isEmpty() && slotStack.getItem().equals(message.m_stack.getItem()))
//                {
//                    int stackSize = slotStack.getCount();
//                    if (stackSize >= amountToRemove) {
//                        player.inventory.decrStackSize(i, amountToRemove);
//                        break;
//                    }
//                    else {
//                        amountToRemove -= stackSize;
//                        player.inventory.decrStackSize(i, stackSize); // 슬롯에서 아이템을 제거
//                    }
//                }
//            }
//
//        }
        return null;
    }
}
