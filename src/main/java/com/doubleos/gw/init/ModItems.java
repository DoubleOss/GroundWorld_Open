package com.doubleos.gw.init;


import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.block.OBJItemBase2;
import com.doubleos.gw.geko_render.AnimatedItemRenderer;
import com.doubleos.gw.geko_render.AnimatedItemRenderer2;
import com.doubleos.gw.geko_render.jackRender;
import com.doubleos.gw.item.ItemBase;
import com.doubleos.gw.item.ItemHelmet;
import com.doubleos.gw.util.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid= Reference.MODID)
public class ModItems
{
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final List<String> itemKoreaName = new ArrayList<String>();
    public static final Item SPHERE = new OBJItemBase("sphere_rutae", "테스트루태");
    public static final Item genedrill = new OBJItemBase("genedrill", "드릴");

    public static final Item upgrade_genedrill = new OBJItemBase2("upgradegenedrill", "드릴");

    public static final Item aluminum = new ItemBase("aluminum", "알루미늄");
    public static final Item antena = new ItemBase("antena", "안테나");
    public static final Item bloodsitck = new ItemBase("bloodsitck", "흡혈스틱");
    public static final Item cloth = new ItemBase("cloth", "천조각");
    public static final Item dongwon = new ItemBase("dongwon", "참치캔");
    public static final Item dress_tool = new ItemBase("dress_tool", "변장도구");
    public static final Item drink_water = new ItemBase("drink_water", "식다수");
    public static final Item gas_ticket = new ItemBase("gas_ticket", "주유권");
    public static final Item gear = new ItemBase("gear", "기어");
    public static final Item hammer = new ItemBase("hammer", "망치", 1);

    public static final Item hardtack = new ItemBase("hardtack", "건빵");

    public static final Item hotpack = new ItemBase("hotpack", "전기 손난로");

    public static final Item iron_rope = new ItemBase("iron_rope", "철끈");

    public static final Item mixgold = new ItemBase("mixgold", "합금");

    public static final Item oilcan = new ItemBase("oilcan", "기름통");

    public static final Item phone = new ItemBase("phone", "스마트폰", 1);
    public static final Item plastic = new ItemBase("plastic", "플라스틱");
    public static final Item recipe = new ItemBase("recipe", "레시피");
    public static final Item report = new ItemBase("report", "연구일지");
    public static final Item robot_call = new ItemBase("robot_call", "로봇호출기");
    public static final Item robot_core = new ItemBase("robot_core", "로봇코어");

    public static final Item shop_info = new ItemBase("shop_info", "상인정보");
    public static final Item spam = new ItemBase("spam", "햄통조림");
    public static final Item spanner = new ItemBase("spanner", "스패너", 1);
    //public static final Item touch = new ItemBase("touch", "횃불", 1);
    public static final Item vitamin = new ItemBase("vitamin", "종합 비타민");

    public static final Item TRANS = new ItemBase("trans", "변장 도구", 1);

    public static final Item OilBox = new ItemBase("oil", "기름통", 1);

    public static final Item usbvax = new ItemBase("usbvax", "USBVax");

    public static final Item CARD = new ItemBase("card", "카드");

    public static final Item usbori = new ItemBase("usbori", "USB");

    public static final Item phonew_ant = new ItemBase("phonew_ant", "안테나 달린 스마트폰", 1);

    public static final Item ele_ticket = new ItemBase("ele_ticket", "전기 충전권");


    //public static final Item jackintheboxitem = new OBJItemBase("jackintheboxitem");


    public static final Item johab = new ItemBase("johab", "조합");

    public static final Item seoulMap_1 = new ItemBase("seoul_map1", "서울역 지도 조각1");
    public static final Item seoulMap_2 = new ItemBase("seoul_map2", "서울역 지도 조각2");
    public static final Item seoulMap_3 = new ItemBase("seoul_map3", "서울역 지도 조각3");
    public static final Item seoulMap_Com = new ItemBase("seoul_map_com", "서울역 지도");

    public static final Item yMap_1 = new ItemBase("y_map1", "여의도 지도 조각 1");
    public static final Item yMap_2 = new ItemBase("y_map2", "여의도 지도 조각 2");
    public static final Item yMap_3 = new ItemBase("y_map3", "여의도 지도 조각 3");
    public static final Item yMap_Com = new ItemBase("y_map_com", "여의도 지도");

    public static final Item zMap_1 = new ItemBase("z_map1", "잠실 지도 조각 1");
    public static final Item zMap_2 = new ItemBase("z_map2", "잠실 지도 조각 2");
    public static final Item zMap_3 = new ItemBase("z_map3", "잠실 지도 조각 3");
    public static final Item zMap_Com = new ItemBase("z_map_com", "잠실 지도");



    public static final Item gMap_1 = new ItemBase("g_map1", "광화문 지도 조각 1");
    public static final Item gMap_2 = new ItemBase("g_map2", "광화문 지도 조각 2");
    public static final Item gMap_3 = new ItemBase("g_map3", "광화문 지도 조각 3");
    public static final Item gMap_Com = new ItemBase("g_map_com", "광화문 지도");


    public static final Item power_sub = new ItemBase("power_sub", "파워 서플라이");
    public static final Item gener_sub = new ItemBase("gener_sub", "제네레이터");
    public static final Item sub_wire = new ItemBase("sub_wire", "전선");
    public static final Item bomb_item = new ItemBase("bomb_item", "폭탄");


    public static final Item ruleInfo = new ItemBase("cinfo", "룰지");












    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {

        genedrill.setTileEntityItemStackRenderer(new AnimatedItemRenderer());
        upgrade_genedrill.setTileEntityItemStackRenderer(new AnimatedItemRenderer2());

        //jackintheboxitem.setTileEntityItemStackRenderer(new jackRender());
    }





}
