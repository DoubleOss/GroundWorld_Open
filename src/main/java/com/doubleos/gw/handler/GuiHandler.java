package com.doubleos.gw.handler;

import com.doubleos.gw.gui.*;
import com.doubleos.gw.inventory.InventoryContainer;
import com.doubleos.gw.inventory.OxygenContainer;
import com.doubleos.gw.inventory.SendMailContainer;
import com.doubleos.gw.inventory.WorkBenchContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == 1)
        {
            return new SendMailContainer(player.inventory, player);
        }
        else if(ID == 0)
        {
            return new InventoryContainer(player.inventory, player);
        }
        else if(ID == 2)
        {
            return new WorkBenchContainer(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == 1)
        {
            return new GuiSendMail(player, player.inventory);
        }
        else if(ID == 0)
        {
            return new GuiInventory(player, player.inventory);
        }
        else if (ID == 2)
        {
            return new GuiReWorkBench(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }
}
