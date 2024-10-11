package com.doubleos.gw.variable;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.util.AnimationState;
import com.doubleos.gw.util.CinematicAniState;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.ShopItemData;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Variable
{

    public int m_gamePoliceCount = 1;

    //싱글톤
    private Variable()
    {

    }

    private static class InnerInstanceGameVariableClazz
    {
        private static final Variable uniqueGameVariable = new Variable();
    }

    public static Variable Instance()
    {
        return InnerInstanceGameVariableClazz.uniqueGameVariable;
    }


    public float m_volume = 1.0f;


    public float m_alpha = 1.0f;
    public boolean m_bloodImageActive = false;


    public float currentBattery = 45f;
    public float maxBattery = 100f;

    public int m_gameMin = 89;
    public int m_gameSec = 59;

    public boolean m_timerStart = false;

    public ArrayList<Quest> m_questList = new ArrayList<>();

    public ArrayList<AnimationState> m_animationStateList = new ArrayList<>();
    public ArrayList<AnimationState> m_animationBroadcastList = new ArrayList<>();
    public ArrayList<CinematicAniState> m_cinematicAniState = new ArrayList<>();

    public ArrayList<AnimationState> m_animationDayStart = new ArrayList<>();

    public String m_ServerName = "";

    public String m_ServerIp = "";

    public boolean m_directJoin = false;
    public String m_directServerIp = "";


    public int selectQuestNumber = 0;

    public boolean m_autoMaticTimer = false;

    public float m_subWaySpeed = 30f;

    public boolean m_subWayAniActive = false;

    public int m_subwayTick = 0;

    public int m_subwayDelayTick = 0;

    public boolean m_changeMod = false; //변장도구 활성화 여부
    
    public int m_changeHealth = 100; //변장도구 퍼센트

    public int m_changeToolCheckTick = 0;
    

    public double endPosZ = 0;
    public eSubWayStatus m_subWayStatus = eSubWayStatus.IDLE;

    public float waterAmount = 20f;
    public float coldAmount = 20f;

    public HashMap<String, Boolean> m_hashDrillPower = new HashMap<>();

    public HashMap<String, ItemStack> m_hashChangeTool = new HashMap<>();

    public HashMap<String, Integer> hashPlayerToBattery = new HashMap<>();


    public String boomAni = "idle";
    public int boomTick = 0;

    public boolean boomIsActive = false;

    public boolean chargeClickEventActive = false;
    public String chargeClickEventName = "";
    public ChargeData chargeClickData;

    public String clientPlayerName = "";

    public String cardTileAnimationName = "cardreader_idle";

    public void subWayReset()
    {
        m_subWaySpeed = 30f;

        m_subWayAniActive = false;

        m_subwayTick = 0;

        m_subwayDelayTick = 0;
        m_subWayStatus = eSubWayStatus.IDLE;

    }

//#1 - 양
//#2 - 삼
//#3 - 서
//#4 - 루
//#5 - 콩
//#6 - 다
//#7 = 후
//#8 - 눈

    public static int getMemberNameToDiscordNumber(String playerName)
    {
        int number = 1;
        if(playerName.equals("d7297"))
            number = 1;
        else if (playerName.equals("samsik23"))
            number = 2;
        else if (playerName.equals("Seoneng"))
            number = 3;
        else if (playerName.equals("RuTaeY"))
            number = 4;
        else if (playerName.equals("KonG7"))
            number = 5;
        else if (playerName.equals("Daju_"))
            number = 6;
        else if(playerName.equals("Huchu95"))
            number = 7;
        else if(playerName.equals("Noonkkob"))
            number = 8;
        return number;
    }

    public enum eSubWayStatus
    {
        ENTER,
        BREAK,
        DOOR_OPEN_L,
        DOOR_OPEN_L_ING,
        DOOR_OPEN_R,
        DOOR_OPEN_R_ING,
        IDLE
    }


    public enum WATCH_STATUS
    {

        CALL_SENDER, // 연결중
        CALL_RECIVER, // 통화중

        CONNETING,
        IDLE //기본값
    }
    public enum PHONE_GUI_VIEW_STATUS
    {
        MENU,
        CALL,
        CALLING,
        CONTENT,
        SHOP,
        GALL,
        TRIBE,
        URGENTTEXT,
        NONE
    }

    public WATCH_STATUS m_phoneStatus = WATCH_STATUS.IDLE;

    public PHONE_GUI_VIEW_STATUS m_phoneGuiStatus = PHONE_GUI_VIEW_STATUS.NONE;

    public PHONE_CALLING_PLAYER m_callingPlayer = PHONE_CALLING_PLAYER.NONE;

    public ArrayList<GallData> m_gallDataList = new ArrayList<>();


    public ArrayList<MailData> m_mailDataList = new ArrayList<>();

    public ArrayList<ChargeData> m_chargeDataList = new ArrayList<>();


    public HashMap<String, ShopData> m_hashShopData = new HashMap<>();


    public HashMap<BlockPos, Boolean> hashPosToCheckActive = new HashMap<>();


    public boolean isWatchTimerView = false;



    public enum PHONE_CALLING_PLAYER
    {
        d7297,
        Seoneng,
        Daju_,
        RuTaeY,
        KonG7,
        Huchu95,
        samsik23,
        Noonkkob,
        NONE
    }

    public String getMemberIdToKoreaNickName(String id)
    {
        String result = "";
        if(id.equals("d7297"))
            result = "양띵";
        else if(id.equals("Seoneng"))
            result = "서넹";
        else if(id.equals("Daju_"))
            result = "다주";
        else if(id.equals("RuTaeY"))
            result = "루태";
        else if(id.equals("KonG7"))
            result = "콩콩";
        else if(id.equals("Huchu95"))
            result = "후추";
        else if(id.equals("samsik23"))
            result = "삼식";
        else if(id.equals("Noonkkob"))
            result = "눈꽃";

        return result;

    }


    public static boolean searchNameIsMember(String name)
    {
        if(name.equals("d7297") || name.equals("Daju_") || name.equals("RuTaeY") || name.equals("KonG7") || name.equals("Huchu95") || name.equals("samsik23") || name.equals("Seoneng") || name.equals("Noonkkob"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public HashMap<String, String> m_nameToKor = new HashMap<>();

    
    public void init()
    {
        int gallData = 1;

        int mailData = 1;

        m_nameToKor.put("d7297", "양띵");
        m_nameToKor.put("Daju_", "다주");
        m_nameToKor.put("RuTaeY", "루태");
        m_nameToKor.put("KonG7", "콩콩");
        m_nameToKor.put("Huchu95", "후추");
        m_nameToKor.put("samsik23", "삼식");
        m_nameToKor.put("Seoneng", "서넹");
        m_nameToKor.put("Noonkkob", "눈꽃");

        m_nameToKor.put("Gomm2", "빠곰");
        m_nameToKor.put("Redssun", "학");
        m_nameToKor.put("nicework23", "밍양");
        m_nameToKor.put("ju_geum", "주금");
        m_nameToKor.put("Cheong823", "청");
        m_nameToKor.put("DoubleOs", "더블");
        m_nameToKor.put("Chaeng_0", "챙");
        m_nameToKor.put("KIM_dudls", "김여인");
        m_nameToKor.put("Bandiyesol_Carol", "반디예솔");
        m_nameToKor.put("BBoomBBoom", "뿜뿜");






        m_gallDataList.add(new GallData(gallData++, "골육상잔;骨肉相殘 (1)", "골육상잔 (1)1.png"));//1
        m_gallDataList.add(new GallData(gallData++, "골육상잔;骨肉相殘 (1-2)", "골육상잔 (1)2.png"));//2
        m_gallDataList.add(new GallData(gallData++, "골육상잔;骨肉相殘 (2)", "골육상잔 (2).png"));//3
        m_gallDataList.add(new GallData(gallData++, "연구원의 일기 (1)", "연구원의 일기 (1).png"));//4
        m_gallDataList.add(new GallData(gallData++, "연구원의 일기 (2)", "연구원의 일기 (2).png"));//5
        m_gallDataList.add(new GallData(gallData++, "인간 재벌과 로봇의 연관성", "인간 재벌과 로봇의 연관성.png"));//6
        m_gallDataList.add(new GallData(gallData++, "인로봇과 인간 재벌의 연관성", "로봇과 인간 재벌의 연관성.png")); //7
        m_gallDataList.add(new GallData(gallData++, "사회적 지위에 따른 인간의 존엄 차이 1", "사회적 지위에 따른 인간의 존엄 차이 1.png"));//8
        m_gallDataList.add(new GallData(gallData++, "사회적 지위에 따른 인간의 존엄 차이 2", "사회적 지위에 따른 인간의 존엄 차이 2.png"));//9
        m_gallDataList.add(new GallData(gallData++, "떠돌이 상인의 일기 (1)", "떠돌이 상인의 일기 (1).png"));//10
        m_gallDataList.add(new GallData(gallData++, "떠돌이 상인의 일기 (2)", "떠돌이 상인의 일기 (2).png"));//11
        m_gallDataList.add(new GallData(gallData++, "도달촌(逃㣵村)", "도달촌.png"));//12

//        m_gallDataList.add(new GallData(gallData++, "못말리는 아가씨", "못말리는.png"));
//        m_gallDataList.add(new GallData(gallData++, "꽁꽁 얼어붙은 한강 위로 고양이가 걸어다닙니다.", "꽁꽁얼어붙은.png"));


        for(int i = 0; i<m_gallDataList.size(); i++)
        {
            m_gallDataList.get(i).m_lock = true;
        }






        /*
        m_mailDataList.add(new MailData(mailData++, "긴급 서버 점검 보상안",
                "안녕하세요 미정팀입니다. /n먼저 지상세계 콘텐츠를 기대해 주시고 /n기다려 주신 모든 시청자 분들께 감사드립니다./n /n금일 콘텐츠 첫 오픈 후 저희가 예상했던 것보다 " +
                        "/n서버 자원이 심각하게 부족해 지연 현상이 발생했습니다./n빠르게 문제를 해결하고자 긴급 점검을 실시할 수밖에 없었고,/n" +
                        "점검 작업을 진행하던 도중 확인이 필요한 사항들이 발견되어/n부득이하게 공지를 드렸던 시간보다 연장을 하게 되었습니다. /n /n" +
                        "실망을 드리게 되어 다시 한번 정중한 사과의 말씀 드리며/n" + "사과의 의미를 담아 보상을 지급해 드리고자 합니다.", "2024-01-26",  false, "",
                stack1.copy(), stack2.copy(), stack3.copy(), stack4.copy(), stack5.copy()));


         */

        GroundItemStack.initItem();
        initShopData();


    }



    void initShopData()
    {
        ArrayList<ShopItemData> defaultItemDataList = new ArrayList<>();

        //배드락은 판매 못할때

        ShopData defaultData = new ShopData("기본상점");
        int defaultItemDataId = 0;

        //흡혈스틱
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, GroundItemStack.BLOODSTICK,
                        new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), 1), 160,
                        new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 25));

        //철곡괭이
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, GroundItemStack.IRON_PICKAXE,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), 1), 180,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        //식다수
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++,  GroundItemStack.DRINK_WATER,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.LAPIS_BLOCK), 1), 1,
                new ItemStack(Items.DYE,1, 4), 5, 160));

        //건빵
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, GroundItemStack.HARDTACK,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), 1), 50,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 120));

        //참치캔
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, GroundItemStack.DONGWON,
                new ItemStack(Items.IRON_INGOT, 1), 15,
                new ItemStack(Items.IRON_INGOT), 5, 60));

        //스팸
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, GroundItemStack.SPAM,
                new ItemStack(Items.IRON_INGOT, 1), 32,
                new ItemStack(Items.IRON_INGOT), 20, 60));

        //천
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++,GroundItemStack.CLOTH,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.REDSTONE_BLOCK)), 1,
                new ItemStack(Items.REDSTONE), 5, 140));

        //비타민
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, GroundItemStack.VITAMIN,
                new ItemStack(Items.GOLD_INGOT), 18,
                new ItemStack(Items.GOLD_INGOT), 9, 36));
        //다이아몬드
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, new ItemStack(Items.DIAMOND),
                new ItemStack(Blocks.COBBLESTONE), 200,
                new ItemStack(Items.GOLD_INGOT), 9, 100));

        //나무
        defaultItemDataList.add(new ShopItemData(defaultItemDataId++, new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1),
                new ItemStack(Items.GOLD_INGOT), 5,
                new ItemStack(Items.GOLD_INGOT), 2, 80));


        ShopData phoneShopData = new ShopData("스마트폰상점");

        ArrayList<ShopItemData> phoneShopItemDataList = new ArrayList<>();
        int phoneShopItemData = 0;


        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++, GroundItemStack.BLOODSTICK,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), 1), 250,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 15));

        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++, GroundItemStack.STONE_PICKAXE,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), 1), 90,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++,  GroundItemStack.DRINK_WATER,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.LAPIS_BLOCK), 1), 2,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 160));

        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++,  GroundItemStack.HARDTACK,
                new ItemStack(Blocks.COBBLESTONE, 1), 100,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 100));

        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++, GroundItemStack.DONGWON,
                new ItemStack(Items.IRON_INGOT, 1), 44,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 20));

        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++,GroundItemStack.CLOTH,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.REDSTONE_BLOCK)), 2,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 100));


        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++, GroundItemStack.VITAMIN,
                new ItemStack(Items.GOLD_INGOT), 36,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 32));

        phoneShopItemDataList.add(new ShopItemData(phoneShopItemData++, new ItemStack(ItemBlock.getItemFromBlock(Blocks.LOG), 1),
                new ItemStack(Items.GOLD_INGOT), 10,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 80));

        defaultData.itemDataList = defaultItemDataList;
        phoneShopData.itemDataList = phoneShopItemDataList;


        ShopData namSanShopData = new ShopData("남산상점");

        int namsanCount = 0;
        ArrayList<ShopItemData> namsanList = new ArrayList<>();

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.ANTENA_RECIPE,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.COBBLESTONE), 1), 32,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.DRILL_RECIPE,
                new ItemStack(Items.EMERALD, 1), 2,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.TOUCH_RECIPE,
                new ItemStack(Blocks.COBBLESTONE, 1), 15,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.SEOUL_MAP_1,
                new ItemStack(Items.EMERALD, 1), 5,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.ROBOTCALL_RECIPE,
                new ItemStack(Items.EMERALD, 1), 10,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.SEOUL_CARDKEY,
                new ItemStack(Items.DIAMOND, 1), 150,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 1));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.MAP_RECIPE,
                new ItemStack(Items.EMERALD, 1), 10,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.GEAR_RECIPE,
                new ItemStack(Items.EMERALD, 1), 3,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.QUESTION_CARDKEY,
                new ItemStack(Items.DIAMOND, 1), 10,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 1));

        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.OILLCARD,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 5));
        namsanList.add(new ShopItemData(namsanCount++, GroundItemStack.ELE_TICKET,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 5));

        namSanShopData.itemDataList = namsanList;

        ShopData seoulShopData = new ShopData("서울역상점");

        int seoulCount = 0;
        ArrayList<ShopItemData> seoulList = new ArrayList<>();

        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.HAMMER_RECIPE,
                new ItemStack(Items.EMERALD, 1), 5,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.SPANNER_RECIPE,
                new ItemStack(Items.EMERALD, 1), 5,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.IRONROPE_RECIPE,
                new ItemStack(Items.EMERALD, 1), 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.OILCAN_RECIPE,
                new ItemStack(Items.EMERALD, 1), 10,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.G_MAP_1,
                new ItemStack(Items.EMERALD, 1), 15,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.OILLCARD,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 10));
        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.ELE_TICKET,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 10));

        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.G_CARDKEY,
                new ItemStack(Items.DIAMOND, 1), 150,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 1));
        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.USB_RECIPE,
                new ItemStack(Items.EMERALD, 1), 8991,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 1));



//        seoulList.add(new ShopItemData(seoulCount++, GroundItemStack.USB_RECIPE,
//                new ItemStack(Items.EMERALD, 1), 40,
//                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8)); //5일차 추가


        seoulShopData.itemDataList = seoulList;

        ShopData gShopData = new ShopData("광화문상점");

        int gCount = 0;
        ArrayList<ShopItemData> gList = new ArrayList<>();

        gList.add(new ShopItemData(gCount++, GroundItemStack.TRANS_RECIPE,
                new ItemStack(Items.EMERALD, 1), 25,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));

        gList.add(new ShopItemData(gCount++, GroundItemStack.MIXGOLD_RECIPE,
                new ItemStack(Items.EMERALD, 1), 25,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        gList.add(new ShopItemData(gCount++, GroundItemStack.CORERAMP_RECIPE,
                new ItemStack(Items.EMERALD, 1), 35,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        gList.add(new ShopItemData(gCount++, GroundItemStack.Y_MAP_1,
                new ItemStack(Items.EMERALD, 1), 25,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        gList.add(new ShopItemData(gCount++, GroundItemStack.OILLCARD,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 15));
        gList.add(new ShopItemData(gCount++, GroundItemStack.ELE_TICKET,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 15));


        gShopData.itemDataList = gList;

        ShopData yShopData = new ShopData("여의도상점");

        int yCount = 0;
        ArrayList<ShopItemData> yList = new ArrayList<>();

        yList.add(new ShopItemData(yCount++, GroundItemStack.Z_MAP_1,
                new ItemStack(Items.EMERALD, 1), 35,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 8));
        yList.add(new ShopItemData(yCount++, GroundItemStack.OILLCARD,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 20));
        yList.add(new ShopItemData(yCount++, GroundItemStack.ELE_TICKET,
                new ItemStack(Items.DIAMOND, 1), 20,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 20));
        ItemStack eleStack2 = GroundItemStack.ELE_TICKET.copy();
        eleStack2.setCount(2);
        yList.add(new ShopItemData(yCount++, GroundItemStack.ELE_TICKET,
                GroundItemStack.DRINK_WATER, 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));

        yList.add(new ShopItemData(yCount++, GroundItemStack.ELE_TICKET,
                GroundItemStack.DONGWON, 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));
        yList.add(new ShopItemData(yCount++, eleStack2,
                GroundItemStack.SPAM, 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));

        ItemStack oil = GroundItemStack.OILLCARD.copy();
        oil.setCount(2);
        yList.add(new ShopItemData(yCount++, GroundItemStack.OILLCARD,
                GroundItemStack.DRINK_WATER, 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));

        yList.add(new ShopItemData(yCount++, GroundItemStack.OILLCARD,
                GroundItemStack.DONGWON, 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));
        yList.add(new ShopItemData(yCount++, oil,
                GroundItemStack.SPAM, 1,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));


        yList.add(new ShopItemData(yCount++, new ItemStack(ModItems.bomb_item), new ItemStack(Items.EMERALD), 100,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 1));


        yShopData.itemDataList = yList;


        ShopData zShopData = new ShopData("잠실상점");

        int zCount = 0;
        ArrayList<ShopItemData> zList = new ArrayList<>();

        zList.add(new ShopItemData(zCount++, GroundItemStack.FULL_OILBOX,
                GroundItemStack.OILLCARD, 5,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 999));
        zList.add(new ShopItemData(zCount++, GroundItemStack.DRINK_WATER,
                new ItemStack(Blocks.COBBLESTONE), 128,
                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 250));
//        zList.add(new ShopItemData(zCount++, GroundItemStack.QUESTION_CARDKEY,
//                new ItemStack(Blocks.DIAMOND_BLOCK), 100,
//                new ItemStack(ItemBlock.getItemFromBlock(Blocks.BEDROCK)), 64, 1));


        zShopData.itemDataList = zList;
        

        m_hashShopData.put("기본상점", defaultData);
        m_hashShopData.put("스마트폰상점", phoneShopData);
        m_hashShopData.put("남산상점", namSanShopData);
        m_hashShopData.put("서울역상점", seoulShopData);
        m_hashShopData.put("광화문상점", gShopData);
        m_hashShopData.put("여의도상점", yShopData);
        m_hashShopData.put("잠실상점", zShopData);






    }




}
