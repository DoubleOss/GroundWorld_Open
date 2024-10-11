package com.doubleos.gw.handler;

import com.doubleos.gw.util.Reference;
import com.doubleos.gw.variable.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ShopDataConfig
{
    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "ShopData.cfg"));
            initConfig();

        }
        else
        {
            initConfig();
        }


    }

    public static void writeConfig(String category, String key, int value)
    {
        Configuration config = m_config;
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, String value) {
        Configuration config = m_config;
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }
    public static void writeConfig(String category, String key, float value) {
        Configuration config = m_config;
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(Double.valueOf(value));
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }
    public static void writeConfig(String category, String key, long value) {
        Configuration config = m_config;
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static long getLong(String category, String key) {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0l).getLong();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
            System.out.println(e);
        } finally {
            config.save();
        }
        return 0l;
    }


    public static int getInt(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0;
    }

    public static void saveConfig(boolean async)
    {
        if(async)
        {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
            {
                Variable variable = Variable.Instance();

                for(int i = 0; i < variable.m_hashShopData.get("기본상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("기본상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("default_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }

                for(int i = 0; i < variable.m_hashShopData.get("스마트폰상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("스마트폰상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("phone_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }


                for(int i = 0; i < variable.m_hashShopData.get("남산상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("남산상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("n_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }

                for(int i = 0; i < variable.m_hashShopData.get("서울역상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("서울역상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("s_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }

                for(int i = 0; i < variable.m_hashShopData.get("광화문상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("광화문상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("g_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }


                for(int i = 0; i < variable.m_hashShopData.get("여의도상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("여의도상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("y_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }



                for(int i = 0; i < variable.m_hashShopData.get("잠실상점").itemDataList.size(); i++)
                {
                    ShopItemData data = variable.m_hashShopData.get("잠실상점").itemDataList.get(i);

                    ShopDataConfig.writeConfig("z_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
                }

                return "저장";
            });
        }
        else
        {
            Variable variable = Variable.Instance();

            for(int i = 0; i < variable.m_hashShopData.get("기본상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("기본상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("default_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }

            for(int i = 0; i < variable.m_hashShopData.get("스마트폰상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("스마트폰상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("phone_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }


            for(int i = 0; i < variable.m_hashShopData.get("남산상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("남산상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("n_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }

            for(int i = 0; i < variable.m_hashShopData.get("서울역상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("서울역상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("s_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }

            for(int i = 0; i < variable.m_hashShopData.get("광화문상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("광화문상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("g_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }


            for(int i = 0; i < variable.m_hashShopData.get("여의도상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("여의도상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("y_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }



            for(int i = 0; i < variable.m_hashShopData.get("잠실상점").itemDataList.size(); i++)
            {
                ShopItemData data = variable.m_hashShopData.get("잠실상점").itemDataList.get(i);

                ShopDataConfig.writeConfig("z_shop", "data_"+data.shopItemId + "_count", data.dayBuyCurrentLimitCount);
            }
        }

    }

    public static void initConfig()
    {

        m_config.load();


        if(getInt("default_shop", "default_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("기본상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("default_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("default_shop", "default_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("기본상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("default_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }
        if(getInt("phone_shop", "phone_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("스마트폰상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("phone_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("phone_shop", "phone_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("스마트폰상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("phone_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }
        if(getInt("n_shop", "n_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("남산상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("n_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("n_shop", "n_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("남산상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("n_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }
        if(getInt("s_shop", "s_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("서울역상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("s_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("s_shop", "s_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("서울역상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("s_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }
        if(getInt("g_shop", "g_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("광화문상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("g_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("g_shop", "g_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("광화문상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("g_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }
        if(getInt("y_shop", "y_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("여의도상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("y_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("y_shop", "y_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("여의도상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("y_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }
        if(getInt("z_shop", "z_shop_size") == 0)
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("잠실상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                writeConfig("z_shop", "data_"+i + "_count", data.dayBuyMaxLimitCount);
            }

            writeConfig("z_shop", "z_shop_size", list.size());

        }
        else
        {

            ArrayList<ShopItemData> list =  Variable.Instance().m_hashShopData.get("잠실상점").itemDataList;
            for(int i = 0; i<list.size(); i++)
            {
                ShopItemData data = list.get(i);
                int count = getInt("z_shop", "data_"+i + "_count");

                data.dayBuyCurrentLimitCount = count;
            }

        }


    }


    public static float getFloat(String category, String key) {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return (float)config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0f;
    }
    public static double getDouble(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0D;
    }

    public static String getString(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return "";
    }
    public static void removeResetClickWrite()
    {
        Configuration config = m_config;
        config.removeCategory(config.getCategory("click"));
        config.save();

        ClickEventList clickList = ClickEventList.Instance();

        for(int i = 0 ; i < clickList.m_clickAllList.size(); i++)
        {
            ClickBlockFadeTeleportInfo info = clickList.m_clickAllList.get(i);
            info.m_number = i;

            HudConfig.writeConfig("click", "click"+i+"number", info.m_number);
            HudConfig.writeConfig("click", "click"+i+"command", info.m_command);
            HudConfig.writeConfig("click", "click"+i+"blockPos_X", (float)info.m_clickBlockPos.getX());
            HudConfig.writeConfig("click", "click"+i+"blockPos_Y", (float)info.m_clickBlockPos.getY());
            HudConfig.writeConfig("click", "click"+i+"blockPos_Z", (float)info.m_clickBlockPos.getZ());


        }


        clickList.clickSize = clickList.m_clickAllList.size();
        HudConfig.writeConfig("click", "clickListSize", clickList.clickSize);

        int interBlockSize = getInt("click", "clickListSize");
        ClickEventList list = ClickEventList.Instance();

        list.clickSize = interBlockSize;
        config.save();
    }
    public static void removeResetInterBlockWrite()
    {
        Configuration config = m_config;
        config.removeCategory(config.getCategory("interaction"));
        config.save();

        InteractionBlockEventList interBlockList = InteractionBlockEventList.Instance();

        for(int i = 0 ; i < interBlockList.m_interactionBlockList.size(); i++)
        {
            InteractionBlock info = interBlockList.m_interactionBlockList.get(i);
            info.m_checkNumber = i;

            HudConfig.writeConfig("interaction", "interaction_block_"+i+"number", i);
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"command", info.m_command);
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"blockPos_X", (float)info.m_blockPos.getX());
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"blockPos_Y", (float)info.m_blockPos.getY());
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"blockPos_Z", (float)info.m_blockPos.getZ());
            System.out.println(i);
        }


        interBlockList.clickSize = interBlockList.m_interactionBlockList.size();
        HudConfig.writeConfig("interaction", "interaction_block_size", interBlockList.clickSize);
        interBlockList.m_interactionBlockList.clear();

        int interBlockSize = getInt("interaction", "interaction_block_size");
        InteractionBlockEventList list = InteractionBlockEventList.Instance();

        for (int i = 0; i<interBlockSize; i++)
        {
            int clickNumber = getInt("interaction", "interaction_block_"+i+"number");
            String command = getString("interaction", "click"+i+"command");

            float x = getFloat("interaction", "interaction_block_"+i+"blockPos_X");
            float y = getFloat("interaction", "interaction_block_"+i+"blockPos_Y");
            float z = getFloat("interaction", "interaction_block_"+i+"blockPos_Z");



            BlockPos pos = new BlockPos(x, y, z);
            InteractionBlock interBlock = new InteractionBlock(clickNumber, pos, command);
            list.m_interactionBlockList.add(interBlock);

        }
        list.clickSize = interBlockSize;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.MODID))
        {
            initConfig();
            System.out.println("바뀜");
        }

    }
}
