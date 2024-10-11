package com.doubleos.gw.init;

import com.doubleos.gw.block.*;
import com.doubleos.gw.geko_render.AnimatedItemRenderer;
import com.doubleos.gw.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    //public static final Block BTN0 = new ObjBlockBase("btn0", Material.BARRIER, true);
    //public static final Block BTN1 = new ObjBlockBase("btn1", Material.BARRIER, true);
    //public static final Block BTN2 = new ObjBlockBase("btn2", Material.BARRIER, true);


    //public static final Block TEST_DREGON = new DregonBase("dregon", Material.BARRIER, true);

    public static final ObjBlockBase BUS = new ObjBlockBase("bus", Material.BARRIER, true);

    public static final FacingBlock electricChage = new FacingBlock("elemj",Material.BARRIER, false);

    public static final FacingBlock gasChage = new FacingBlock("gasta",Material.BARRIER, false);

    public static final BlockCoreLamp CORELAMP = new BlockCoreLamp("corelamp", Material.BARRIER, true, 2f);

    public static final BlockCoreLamp CORELAMP_OFF = new BlockCoreLamp("corelamp_off", Material.BARRIER, false, 0f);

    public static final BlockCoreLamp touch = new BlockCoreLamp("touch", Material.BARRIER, true, 0.5f);

    public static final BlockCoreLamp touch_test = new BlockCoreLamp("touch_test", Material.BARRIER, true, 0.5f);


    public static final FacingBlock SKULL1 = new FacingBlock("skull", Material.BARRIER, false);
    public static final FacingBlock SKULL2 = new FacingBlock("skull_2", Material.BARRIER, false);

    public static final EmergenBlock test2 = new EmergenBlock("emergen.geo", Material.BARRIER, false);

    public static final BlockCardReader CARD_READER = new BlockCardReader("cardreader", Material.BARRIER, false);
    public static final BlockSubWayUnLock SUBWAY_UNLOCK = new BlockSubWayUnLock("subwayunlock", Material.BARRIER, false);


    public static final BombBlock BOMB_BLOCK = new BombBlock("bombblock", Material.BARRIER, false);


}
