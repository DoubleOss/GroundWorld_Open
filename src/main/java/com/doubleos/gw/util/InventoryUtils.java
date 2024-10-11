package com.doubleos.gw.util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;

public class InventoryUtils {


    public static void removePlayerItemName(EntityPlayerMP player, ItemStack stack, int amount)
    {
        int amountToRemove = amount;
        for (int i = 0; i < player.inventory.getSizeInventory() && amountToRemove > 0; i++)
        {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            if (!slotStack.isEmpty() && slotStack.getItem().equals(stack.getItem()) && slotStack.getDisplayName().equals(stack.getDisplayName()))
            {
                int stackSize = slotStack.getCount();
                if (stackSize >= amountToRemove) {
                    player.inventory.decrStackSize(i, amountToRemove);
                    break;
                }
                else {
                    amountToRemove -= stackSize;
                    player.inventory.decrStackSize(i, stackSize); // 슬롯에서 아이템을 제거
                }
            }
        }

    }


    public static void removePlayerItem(EntityPlayerMP player, ItemStack stack, int amount)
    {
        int amountToRemove = amount;
        for (int i = 0; i < player.inventory.getSizeInventory() && amountToRemove > 0; i++)
        {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            if (!slotStack.isEmpty() && slotStack.getItem().equals(stack.getItem()))
            {
                int stackSize = slotStack.getCount();
                if (stackSize >= amountToRemove) {
                    player.inventory.decrStackSize(i, amountToRemove);
                    break;
                }
                else {
                    amountToRemove -= stackSize;
                    player.inventory.decrStackSize(i, stackSize); // 슬롯에서 아이템을 제거
                }
            }
        }

    }

    // 아이템 스택과 수량을 추가할 수 있는지 확인하는 메서드
    public static boolean canAddItemToInventory(EntityPlayer player, ItemStack stack, int amount) {
        IInventory inventory = player.inventory;
        int remainingAmount = amount;

        // 인벤토리의 각 슬롯을 검사합니다.
        for (int i = 0; i < 36; i++) {
            ItemStack currentStack = inventory.getStackInSlot(i);

            if (currentStack.isEmpty()) {
                // 빈 슬롯이면 바로 true 반환
                return true;
            } else if (ItemStack.areItemsEqual(currentStack, stack) && ItemStack.areItemStackTagsEqual(currentStack, stack)) {
                // 같은 아이템이고 스택 크기가 최대가 아닌 경우
                remainingAmount -= (stack.getMaxStackSize() - currentStack.getCount());

                // 남은 수량이 0 이하이면 추가 가능
                if (remainingAmount <= 0) {
                    return true;
                }
            }
        }

        // 남은 수량이 0보다 크면 추가 불가
        return false;
    }

    public static int getAirCountToInventory(EntityPlayer player) {
        IInventory inventory = player.inventory;

        int count = 0;
        // 인벤토리의 각 슬롯을 검사합니다.
        for (int i = 0; i < 36; i++) {
            ItemStack currentStack = inventory.getStackInSlot(i);

            if (currentStack.isEmpty()) {
                // 빈 슬롯이면 바로 true 반환
                count+=1;
            }
            else if(currentStack.getItem().equals(Items.AIR))
                count+=1;
        }

        // 남은 수량이 0보다 크면 추가 불가
        return count;
    }

    public static void decreaseItemStack(EntityPlayer player, Item item, int amount) {
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() == item) {
                int newCount = stack.getCount() - amount;
                if (newCount > 0) {
                    stack.setCount(newCount);
                } else {
                    player.inventory.deleteStack(stack);
                }
                player.inventory.markDirty();
                break;
            }
        }
    }
}
