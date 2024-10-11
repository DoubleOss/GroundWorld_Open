package com.doubleos.gw.inventory;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorkBenchContainer extends Container
{
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public InventoryCraftResult craftResult = new InventoryCraftResult();
    private World world;
    private BlockPos pos;
    private EntityPlayer player;

    public WorkBenchContainer(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
    {
        this.world = worldIn;
        this.pos = posIn;
        this.player = playerInventory.player;
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 142, 31));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 10 + j * 25, 7 + i * 24));
            }
        }

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

        int[] widthList = new int[] {71, 95, 121, 145, 170, 196, 221, 246, 271};
        int[] heightList = new int[] {105, 81, 58};



        int loopCount = 10;
        for(int i=0; i <heightList.length; i++)
        {
            for(int j=0; j <widthList.length; j++)
            {
                this.inventorySlots.get(loopCount).xPos = (int)(widthList[j] - 91);
                this.inventorySlots.get(loopCount).yPos = (int)(heightList[i] + 40);
                loopCount++;
            }
        }



        widthList = new int[] {71, 95, 121, 145, 170, 196, 221, 246, 271};
        heightList = new int[] {154};

        for(int i=0; i <heightList.length; i++)
        {
            for(int j=0; j <widthList.length; j++)
            {
                this.inventorySlots.get(loopCount).xPos = (int)(widthList[j] - 91);
                this.inventorySlots.get(loopCount).yPos = (int)(heightList[i] + 20);
                loopCount++;
            }
        }

    }

    public WorkBenchContainer() {
    }

    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix, this.craftResult);
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.craftMatrix);
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (this.world.getBlockState(this.pos).getBlock() != Blocks.CRAFTING_TABLE)
        {
            return true;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 10 && index < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 37 && index < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if (index == 0)
            {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}
