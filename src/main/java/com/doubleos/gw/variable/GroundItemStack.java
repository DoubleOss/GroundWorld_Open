package com.doubleos.gw.variable;

import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.HashMap;

public class GroundItemStack
{


    public static ItemStack BLOODSTICK;
    public static ItemStack DIAMOND_PICKAXE;

    public static ItemStack IRON_PICKAXE;
    public static ItemStack STONE_PICKAXE;


    public static ItemStack DRINK_WATER;
    public static ItemStack DONGWON;
    public static ItemStack SPAM;

    public static ItemStack CLOTH;


    public static ItemStack VITAMIN;

    public static ItemStack EMPTYOILCAN;
    public static ItemStack HARDTACK;

    public static ItemStack GEAR;

    public static ItemStack GENDRILL;

    public static ItemStack TOUCH;

    public static ItemStack IRON_ROPE;

    public static ItemStack HAMMER;

    public static ItemStack SPANNER;
    public static ItemStack MIXGOLD;

    public static ItemStack ROBOT_CORE;
    public static ItemStack ROBOT_CALL;

    public static ItemStack TRAN;

    public static ItemStack PLASTIC;

    public static ItemStack ALUMINUM;

    public static ItemStack CORELAMP;

    public static ItemStack CORELAMP_OFF;

    public static ItemStack OILLCARD;
    public static ItemStack ANTENA;

    public static ItemStack ELE_TICKET;

    public static ItemStack USB_CORD;

    public static ItemStack VI_USB;

    public static ItemStack NOT_TRANS;

    public static ItemStack UPGRADE_DRILL;

    public static ItemStack SEOUL_MAP_1;
    public static ItemStack SEOUL_MAP_2;
    public static ItemStack SEOUL_MAP_3;

    public static ItemStack SEOUL_MAP_COM;


    public static ItemStack Y_MAP_1;
    public static ItemStack Y_MAP_2;
    public static ItemStack Y_MAP_3;
    public static ItemStack Y_MAP_COM;

    public static ItemStack Z_MAP_1;
    public static ItemStack Z_MAP_2;
    public static ItemStack Z_MAP_3;
    public static ItemStack Z_MAP_COM;


    public static ItemStack G_MAP_1;
    public static ItemStack G_MAP_2;
    public static ItemStack G_MAP_3;
    public static ItemStack G_MAP_COM;

    public static ItemStack GENRATOR;

    public static ItemStack POWER;

    public static ItemStack ELE_LINE;




    public static ItemStack ANTENA_RECIPE;
    public static ItemStack DRILL_RECIPE;
    public static ItemStack TOUCH_RECIPE;
    public static ItemStack ROBOTCALL_RECIPE;
    public static ItemStack MAP_RECIPE;
    public static ItemStack GEAR_RECIPE;
    public static ItemStack HAMMER_RECIPE;
    public static ItemStack SPANNER_RECIPE;
    public static ItemStack IRONROPE_RECIPE;
    public static ItemStack USB_RECIPE;
    public static ItemStack TRANS_RECIPE;
    public static ItemStack MIXGOLD_RECIPE;
    public static ItemStack CORERAMP_RECIPE;

    public static ItemStack OILCAN_RECIPE;



    public static ItemStack UPGRADE_DRILL_RECIPE;

    public static ItemStack SEOUL_CARDKEY;
    public static ItemStack G_CARDKEY;

    public static ItemStack QUESTION_CARDKEY;
    public static ItemStack LAB_CELLAR_CARDKEY;

    public static ItemStack FULL_OILBOX;

    public static ItemStack COAL = new ItemStack(Items.REEDS);


    public static ItemStack BOOM_PAPER = new ItemStack(Items.REEDS);






    public static HashMap<Item, ItemStack> hashItemToItemStack = new HashMap<>();

    public static HashMap<String, ItemStack> hashRecipeItemToItemStack = new HashMap<>();
    public static HashMap<String, String> hashNameToRecipeLocalName = new HashMap<>();





    public static void initItem()
    {
        COAL.setStackDisplayName("§f석탄");
        hashItemToItemStack.put(COAL.getItem(), COAL);
        BLOODSTICK = createItemStackLore(ModItems.bloodsitck, "흡혈스틱", "특정한 ??의 배고픔을 빼앗을 수 있다.", true);
        DIAMOND_PICKAXE = createItemStackLore(Items.DIAMOND_PICKAXE, "다이아몬드 곡괭이", "요즘 같은 시대에 곡괭이...?", true);
        IRON_PICKAXE = createItemStackLore(Items.IRON_PICKAXE, "철 곡괭이", "요즘 같은 시대에 곡괭이...?", true);
        STONE_PICKAXE = createItemStackLore(Items.STONE_PICKAXE, "돌 곡괭이", "요즘 같은 시대에 곡괭이...?", true);

        DRINK_WATER = createItemStackLore(ModItems.drink_water, "식다수", "갈증을 해결해 줄 유일무이 존재.", true);
        DONGWON = createItemStackLore(ModItems.dongwon, "참치캔", "고소한 맛이 일품인 참치 통조림.", true);
        SPAM = createItemStackLore(ModItems.spam, "햄통조림", "짭짤한 맛이 일품인 햄 통조림.", true);
        CLOTH = createItemStackLore(ModItems.cloth, "천조각", "횃불을 만들 수 있는 재료.", true);
        VITAMIN = createItemStackLore(ModItems.vitamin, "종합비타민", "몸이 건강해지는 알약.", true);
        EMPTYOILCAN = createItemStackLore(ModItems.OilBox, "기름통", " 잔량: 0%", true);
        HARDTACK = createItemStackLore(ModItems.hardtack, "건빵", "천천히 먹지 않으면 목이 막히는 과자.", true);

        GEAR = createItemStackLore(ModItems.gear, "기어", "중요한 물건들을 만들기 위한 재료.", true);

        GENDRILL = createItemStackLore(ModItems.genedrill, "드릴", "벽을 파낼 수 있는 편리한 도구.", true);

        UPGRADE_DRILL = createItemStackLore(ModItems.upgrade_genedrill, "강화된 드릴", "일반 드릴보다 더 빠른 속도를 자랑하는 드릴.", true);


        TOUCH = createItemStackLore(ModBlocks.touch, "횃불", "추위로부터 우리의 몸을 지켜준다.", true);

        IRON_ROPE = createItemStackLore(ModItems.iron_rope, "철끈", "무언가 연결을 위한 재료.", true);

        HAMMER = createItemStackLore(ModItems.hammer, "망치", "인?간 보단... 기계를 망가뜨리기 좋은 무기.", true);

        SPANNER = createItemStackLore(ModItems.spanner, "스패너", "고장난 기계를 고칠 수 있는 만능 도구.", true);

        ROBOT_CORE = createItemStackLore(ModItems.robot_core, "로봇코어", "로봇들의 심장이라 불리는 부품.", true);
        MIXGOLD = createItemStackLore(ModItems.mixgold, "합금", "여러 가지 금속을 섞어 만든 단단한 금속.", true);
        ROBOT_CALL = createItemStackLore(ModItems.robot_call, "로봇호출기", "지정된 위치에서 호출기를 사용할 수 있다.", true);

        TRAN = createItemStackLore(ModItems.TRANS, "변장도구",  "남은 내구도 100/100", true);

        PLASTIC = createItemStackLore(ModItems.plastic, "플라스틱", "오랜 시간 동안 세상에 남아있어 줄 최고의 쓰레기.", true);

        ALUMINUM = createItemStackLore(ModItems.aluminum, "알루미늄", "은백색의 부드러운 금속.", true);
        CORELAMP = createItemStackLore(ModBlocks.CORELAMP, "코어램프", "코어램프 배터리: 0/100", true);
        CORELAMP_OFF = createItemStackLore(ModBlocks.CORELAMP_OFF, "코어램프", "코어램프 배터리: 0/100", true);

        OILLCARD = createItemStackLore(ModItems.gas_ticket, "주유권", "주유기를 1회 이용할 수 있다.", true);

        ANTENA = createItemStackLore(ModItems.antena, "안테나", "핸드폰의 주파수를 찾아주는 도구.", true);

        ELE_TICKET = createItemStackLore(ModItems.ele_ticket, "충전권", "전기 충전기를 1회 이용할 수 있다.", true);

        USB_CORD = createItemStackLore(ModItems.usbori, "코드 USB", "고유 식별 코드가 담긴 USB.", true);

        VI_USB = createItemStackLore(ModItems.usbvax, "바이러스 USB", "낙원을 막을 수 있는 바이러스USB.", true);

        SEOUL_MAP_1 = createItemStackLore(ModItems.seoulMap_1, "서울역 지도 조각1", "", true);
        SEOUL_MAP_2 = createItemStackLore(ModItems.seoulMap_2, "서울역 지도 조각2", "", true);
        SEOUL_MAP_3 = createItemStackLore(ModItems.seoulMap_3, "서울역 지도 조각3", "", true);
        SEOUL_MAP_COM = createItemStackLore(ModItems.seoulMap_Com, "서울역 지도", "", true);


        Y_MAP_1 = createItemStackLore(ModItems.yMap_1, "여의도 지도 조각1", "", true);
        Y_MAP_2 = createItemStackLore(ModItems.yMap_2, "여의도 지도 조각2", "", true);
        Y_MAP_3 = createItemStackLore(ModItems.yMap_3, "여의도 지도 조각3", "", true);
        Y_MAP_COM = createItemStackLore(ModItems.yMap_Com, "여의도 지도", "", true);

        Z_MAP_1 = createItemStackLore(ModItems.zMap_1, "잠실 지도 조각1", "", true);
        Z_MAP_2 = createItemStackLore(ModItems.zMap_2, "잠실 지도 조각2", "", true);
        Z_MAP_3 = createItemStackLore(ModItems.zMap_3, "잠실 지도 조각3", "", true);
        Z_MAP_COM = createItemStackLore(ModItems.zMap_Com, "잠실 지도", "", true);

        G_MAP_1 = createItemStackLore(ModItems.gMap_1, "광화문 지도 조각1", "", true);
        G_MAP_2 = createItemStackLore(ModItems.gMap_2, "광화문 지도 조각2", "", true);
        G_MAP_3 = createItemStackLore(ModItems.gMap_3, "광화문 지도 조각3", "", true);
        G_MAP_COM = createItemStackLore(ModItems.gMap_Com, "광화문 지도", "", true);

        GENRATOR = createItemStackLore(ModItems.gener_sub, "발전기", "", true);

        POWER = createItemStackLore(ModItems.power_sub, "전력장치", "", true);

        ELE_LINE = createItemStackLore(ModItems.sub_wire, "전선", "", true);

        FULL_OILBOX = createItemStackLore(ModItems.OilBox, "기름통", " 잔량: 100%", false);





        ANTENA_RECIPE = createRecipeItemStackLore(ModItems.recipe, "안테나 조합법", ModItems.antena);
        DRILL_RECIPE = createRecipeItemStackLore(ModItems.recipe, "드릴 조합법", ModItems.genedrill);
        TOUCH_RECIPE = createRecipeItemStackLore(ModItems.recipe, "횃불 조합법", ItemBlock.getItemFromBlock(ModBlocks.touch));
        ROBOTCALL_RECIPE = createRecipeItemStackLore(ModItems.recipe, "로봇 호출기 조합법", ModItems.robot_call);
        MAP_RECIPE = createRecipeItemStackLore(ModItems.recipe, "지도 조합법", ModItems.seoulMap_1);
        GEAR_RECIPE = createRecipeItemStackLore(ModItems.recipe, "기어 조합법",ModItems.gear);
        HAMMER_RECIPE =  createRecipeItemStackLore(ModItems.recipe, "망치 조합법", ModItems.hammer);
        SPANNER_RECIPE = createRecipeItemStackLore(ModItems.recipe, "스패너 조합법", ModItems.spanner);
        IRONROPE_RECIPE = createRecipeItemStackLore(ModItems.recipe, "철끈 조합법", ModItems.iron_rope);
        USB_RECIPE = createRecipeItemStackLore(ModItems.recipe, "USB 조합법", ModItems.usbvax);
        TRANS_RECIPE = createRecipeItemStackLore(ModItems.recipe, "변장도구 조합법", ModItems.TRANS);
        MIXGOLD_RECIPE = createRecipeItemStackLore(ModItems.recipe, "합금 조합법", ModItems.mixgold);
        CORERAMP_RECIPE = createRecipeItemStackLore(ModItems.recipe, "코어 램프 조합법", ItemBlock.getItemFromBlock(ModBlocks.CORELAMP_OFF));
        UPGRADE_DRILL = createRecipeItemStackLore(ModItems.recipe, "강화된 드릴 조합법", ModItems.upgrade_genedrill);
        OILCAN_RECIPE = createRecipeItemStackLore(ModItems.recipe, "기름통 조합법", ModItems.OilBox);



        SEOUL_CARDKEY = createItemStackLore(ModItems.CARD, "서울역 카드키", "", false);
        G_CARDKEY = createItemStackLore(ModItems.CARD, "광화문 카드키", "", false);
        QUESTION_CARDKEY = createItemStackLore(ModItems.CARD, "??? 카드키", "", false);
        LAB_CELLAR_CARDKEY = createItemStackLore(ModItems.CARD, "연구소 지하카드키", "" ,false);

        BOOM_PAPER = createItemStackLore(Items.PRISMARINE_SHARD, "폭탄 사용법", " 폭탄 사용법이 적힌 종이다", false);




        NOT_TRANS = createItemStackLore(ModItems.dress_tool, "이거 사용 안하는 템임", "이거로 넣었으면 진짜 안대욧!!!!!!!!!", true);
    }


    public static ItemStack createRecipeItemStackLore(Item item, String displayName, Item uncalItem)
    {

        ItemStack stack = createItemStackLore(item, displayName, "", false);
        hashRecipeItemToItemStack.put(displayName.replaceAll(" ", "_"), stack);
        hashNameToRecipeLocalName.put(displayName.replaceAll(" ", "_"), uncalItem.getRegistryName().toString());
        return stack;
    }
    public static ItemStack createItemStackLore(Block block, String displayName, String loreText, boolean addHash)
    {
        ItemStack stack = new ItemStack(block);
        return createItemStackLore(stack, displayName, loreText, addHash);

    }
    public static ItemStack createItemStackLore(Item item, String displayName, String loreText, boolean addHash)
    {
        ItemStack stack = new ItemStack(item, 1);
        return createItemStackLore(stack, displayName, loreText, addHash);

    }
    public static ItemStack createItemStackLore(ItemStack stack, String displayName, String loreText, boolean addHash)
    {

        stack.setStackDisplayName(displayName);

        NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound();

        if (nbt == null)
            nbt = new NBTTagCompound();

        if(!loreText.equals(""))
        {

            NBTTagList lore = new NBTTagList();
            lore.appendTag(new NBTTagString("§f"+loreText));

            NBTTagCompound display = new NBTTagCompound();
            display.setTag("Lore", lore);

            nbt.setTag("display", display);
            stack.setTagCompound(nbt);
        }
        stack.setStackDisplayName("§f"+displayName);

        if(addHash)
            hashItemToItemStack.put(stack.getItem(), stack);
        return stack;

    }

}
