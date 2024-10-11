package com.doubleos.gw.inventory;

import com.doubleos.gw.GroundWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SendMailContainer extends Container
{

    EntityPlayer player;

    public IInventory internal;



    public SendMailContainer(InventoryPlayer inventoryPlayer, EntityPlayer player)
    {
        this.player = player;
        this.internal = new InventoryBasic("SendMail", true, 5);
        for (int i = 0; i < 41; i++)
        {
            if(i < 36)
            {
                addSlotToContainer(new Slot(inventoryPlayer, i, 0, 0));
                if(!player.inventory.mainInventory.get(i).isEmpty())
                {
                    this.getSlot(i).putStack(player.inventory.mainInventory.get(i));
                }
            }
            else
            {
                addSlotToContainer(new Slot(internal, i-36, 0, 0));
            }


        }

        int[] widthList = new int[] {242, 266, 290, 314, 338, 362};
        int[] heightList = new int[] {9, 33, 57, 81, 105, 129};


        int loopCount = 0;
        for(int i=0; i <heightList.length; i++)
        {
            for(int j=0; j <widthList.length; j++)
            {
                this.inventorySlots.get(loopCount).xPos = widthList[j];
                this.inventorySlots.get(loopCount).yPos = heightList[i];
                loopCount++;
            }
        }

        int[] widthList2 = new int[] {13, 47, 81, 115, 149};
        int[] heightList2 = new int[] {220} ;

        int check = 0;
        for(int i = 41; i > 36; i--)
        {
            this.inventorySlots.get(i-1).xPos = widthList2[check];
            this.inventorySlots.get(i-1).yPos = heightList2[0];
            check++;
        }
//        for(int i = 36; i < 41; i++)
//        {
//            this.inventorySlots.get(i).xPos = widthList2[i-36];
//            this.inventorySlots.get(i).yPos = heightList2[0];
//        }

        GroundWorld.instance.m_mailContainer = this;
    }


    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);



    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            System.out.println(index);
            // 슬롯 인덱스에 따라서 아이템을 이동시킵니다.
            if(index >= 36)
            {
                if (!this.mergeItemStack(slotStack, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                // 슬롯 인덱스가 9 이하일 때는 인벤토리로 이동합니다.
                if (!this.mergeItemStack(slotStack, 36, 41, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return itemStack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

}
