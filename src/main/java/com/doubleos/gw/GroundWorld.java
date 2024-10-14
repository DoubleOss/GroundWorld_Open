package com.doubleos.gw;


import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.block.tile.*;
import com.doubleos.gw.command.GuiCommand;
import com.doubleos.gw.creativetab.NunnuTab;
import com.doubleos.gw.entity.*;
import com.doubleos.gw.handler.*;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.inventory.ChestContainer;
import com.doubleos.gw.inventory.InventoryContainer;
import com.doubleos.gw.inventory.SendMailContainer;
import com.doubleos.gw.inventory.WorkBenchContainer;
import com.doubleos.gw.item.ItemHelmet;
import com.doubleos.gw.packet.Packet;
import com.doubleos.gw.proxy.CommonProxy;
import com.doubleos.gw.util.Reference;
import com.doubleos.gw.variable.GroundItemStack;
import com.doubleos.gw.variable.PlayerInfo;
import com.doubleos.gw.variable.Variable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GroundWorld
{

    @Mod.Instance
    public static GroundWorld instance;

    private static Logger logger;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("GroundWorld");
    public static final CreativeTabs SdTab = new NunnuTab("GroundWorldTab");

    public static final CreativeTabs modelTab = new NunnuTab("ModelTab");


    public static final String RESOURCEID = "gwr";
    public InventoryContainer inventoryContainer;

    public WorkBenchContainer workBenchContainer;



    public ChestContainer chestContainer;


    public boolean m_shake = false;


    public File settingDataFile;
    public SendMailContainer m_mailContainer;


    public ArrayList<String> playerList = new ArrayList<String>();

    public Map<String, PlayerInfo> playerInfoMap = new HashMap<String, PlayerInfo>();

    public boolean isLoaded = false;

    File m_modFile;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        proxy.preInit(event);
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        GameRegistry.registerTileEntity(EmergenTile.class, new ResourceLocation(Reference.MODID, "emergen"));
        GameRegistry.registerTileEntity(TileCardReader.class, new ResourceLocation(Reference.MODID, "cardreader"));
        GameRegistry.registerTileEntity(TileSubwayUnlock.class, new ResourceLocation(Reference.MODID, "subwayunlock"));
        GameRegistry.registerTileEntity(BombTile.class, new ResourceLocation(Reference.MODID, "bomb"));


        GroundItemStack.initItem();
        Packet.init();
        AutoMineList.Instance().init(event.getModConfigurationDirectory());
        HudConfig.init(event.getModConfigurationDirectory());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        DiscordConfig.init(event.getModConfigurationDirectory());
        ChargeDataConfig.init(event.getModConfigurationDirectory());
        UserBatteryConfig.init(event.getModConfigurationDirectory());
        MovieMenuConfig.init(event.getModConfigurationDirectory());



        m_modFile = event.getModConfigurationDirectory();


        int entityId = 0;
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Subway"), EntitySubWay.class, "subway", entityId++, this, 64, 1, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Subway2"), EntitySubWay2.class, "subway2", entityId++, this, 64, 2, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine"), EntityGeoAutoMine.class , "automine", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine2"), EntityGeoAutoMine2.class , "automine2", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine3"), EntityGeoAutoMine3.class , "automine3", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine4"), EntityGeoAutoMine4.class , "automine4", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine5"), EntityGeoAutoMine5.class , "automine5", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine6"), EntityGeoAutoMine6.class , "automine6", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine7"), EntityGeoAutoMine7.class , "automine7", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine8"), EntityGeoAutoMine8.class , "automine8", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine9"), EntityGeoAutoMine9.class , "automine9", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine10"), EntityGeoAutoMine10.class , "automine10", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine11"), EntityGeoAutoMine11.class , "automine11", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Automine12"), EntityGeoAutoMine12.class , "automine12", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "BaseStore"), EntityBaseStore.class , "basestore", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "St_EntityModel1"), EntityShop1.class , "st_entitymodel1", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "St_EntityModel2"), EntityShop2.class , "st_entitymodel2", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "St_EntityModel3"), EntityShop3.class , "st_entitymodel3", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "St_EntityModel4"), EntityShop4.class , "st_entitymodel4", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "St_EntityModel5"), EntityShop5.class , "st_entitymodel5", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "St_EntityModel6"), EntityShop6.class , "st_entitymodel6", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "Subway_Npc"), EntitySubWayNpc.class , "subway_npc", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "robot"), EntityRobot.class , "robot", entityId++, this, 64, 5, false, Color.WHITE.getRGB(), Color.BLACK.getRGB());


        GameRegistry.registerTileEntity(BlockNamsanTileEntity.class, new ResourceLocation(Reference.MODID, "namsan"));
    }



    @SideOnly(Side.SERVER)
    @Mod.EventHandler
    public void onServerStop(FMLServerStoppedEvent event)
    {
        AutoMineConfig.saveConfig(false);
        UserBatteryConfig.saveConfig(false);
        MailHandler.saveConfig(false);
        ShopDataConfig.saveConfig(false);
        ChargeDataConfig.saveConfig(false);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code

        proxy.init(event);
        GwSoundHandler.registerSounds();
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        MailHandler.init(m_modFile);
        Variable.Instance().init();
        ShopDataConfig.init(m_modFile);
        ClueHandler.init(m_modFile);

        for(Block block : ForgeRegistries.BLOCKS.getValues())
        {
            if(block.getRegistryName().toString().contains("cocricotmod")  || block.getRegistryName().toString().contains("littletiles")
                    || block.getRegistryName().toString().contains("miniaturia_mod") || block.getRegistryName().toString().contains("rojiuramod")
                    || block.getRegistryName().toString().contains("glass") )
            {
                block.setHardness(100f);
            }

        }
        init();

    }

    @EventHandler
    public void serverInit(FMLServerStartedEvent event)
    {


    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {
        proxy.serverInit(event);
        event.registerServerCommand(new GuiCommand());
        //HudConfig.init(event.getServer().getFile(""));


    }

    
    //아이템 등록 자동화
    //아이템 기존 등록 과정
    //1. ModItems.class 에 아이템 이름과 클래스 선언 
    // 리소스에 item 텍스쳐 연결을 위한 json 파일 생성 후 텍스쳐 경로 연결
    //이후 아이템 이름 변경을 위해 lang 파일 작성

    /*      자동화
    1. ModItems 에 한글 이름 + 영문 이름 같이 기입
    2. texture/item 에 텍스쳐 저장
    3. init 함수 실행시 ModItems 에 있는 리스트 에 등록된 아이템들이 전부 lang 파일에 자동 저장 및 texture 파일 연결을 위한 json 자동 생성 [ 덮어쓰기 방지 ]
     
     */
    void init()
    {

        File langFileLoc = new File("./../src/main/resources/assets/"+Reference.MODID+"/lang"); //언어파일 위치
        File itemJson = new File("./../src/main/resources/assets/"+Reference.MODID+"/models/item"); //itemJson

        try {
            FileWriter usFw = new FileWriter(langFileLoc+"/en_us.lang");
            //FileWriter usFw = new FileWriter(langFileLoc);

            int i = 0;
            for(Item item: ModItems.ITEMS)
            {
                if(i >= ModItems.itemKoreaName.size())
                {

                }
                else
                {
                    String itemName = item.getRegistryName().getResourcePath();
                    usFw.write("item."+itemName+".name="+ModItems.itemKoreaName.get(i)+"\r\n");
                }
                i++;

            }
            usFw.write("itemGroup.GroundWorldTab=GroundWorldTab");

            usFw.flush();
            usFw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try
        {
            FileWriter koFw = new FileWriter(langFileLoc+"/ko_kr.lang");

            int i = 0;
            for(Item item: ModItems.ITEMS)
            {
                if(i >= ModItems.itemKoreaName.size())
                {

                }
                else
                {
                    String itemName = item.getRegistryName().getResourcePath();
                    koFw.write("item."+itemName+".name="+ModItems.itemKoreaName.get(i)+"\r\n");
                }
                i++;
            }
            koFw.write("itemGroup.GroundWorldTab=지상 세계");
            koFw.flush();
            koFw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Item item: ModItems.ITEMS)
        {
            String itemName = item.getRegistryName().getResourcePath();
            System.out.println(itemName);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();

            jsonObject.addProperty("parent", "item/generated");

            jsonObject2.addProperty("layer0", Reference.MODID+":items/"+itemName);

            jsonObject.add("textures", jsonObject2);


            try {
                File existFile = new File(itemJson+"/"+itemName+".json"); // 파일 덮어쓰기 방지
                if(! existFile.isFile())
                {
                    FileWriter fw = new FileWriter(existFile);

                    gson.toJson(jsonObject, fw);
                    fw.flush();
                    fw.close();

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }

    }
}
