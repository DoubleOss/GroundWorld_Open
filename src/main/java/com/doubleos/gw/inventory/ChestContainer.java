package com.doubleos.gw.inventory;


import com.doubleos.gw.GroundWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ChestContainer extends Container
{

    EntityPlayer player;


    public ChestContainer(InventoryPlayer inventoryPlayer, EntityPlayer player)
    {
        this.player = player;
        for (int i = 0; i < 36; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, 0, 0));
            if(!player.inventory.mainInventory.get(i).isEmpty())
            {
                this.getSlot(i).putStack(player.inventory.mainInventory.get(i));
            }

        }
        int[] widthList = new int[] {242, 266, 290, 314, 338, 362};
        int[] heightList = new int[] {9, 33, 57, 81, 105, 220};


        //m_inventoryContainer.inventorySlots.get(0).xPos = (int)(width/2 - 189 + (488/2.5f));
        //m_inventoryContainer.inventorySlots.get(0).yPos = (int)(height/2 - 117);
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

        GroundWorld.instance.chestContainer = this;
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
            if (index < 5) {
                // 슬롯 인덱스가 9 이하일 때는 인벤토리로 이동합니다.
                if (!this.mergeItemStack(slotStack, 5, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(slotStack, 0, 6, false)) {
                // 슬롯 인덱스가 9 이상일 때는 핫바로 이동합니다.
                return ItemStack.EMPTY;
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
