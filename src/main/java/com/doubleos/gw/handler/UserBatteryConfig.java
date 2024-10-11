package com.doubleos.gw.handler;

import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.variable.Variable;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class UserBatteryConfig
{


    public static Configuration m_config;



    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "userbattery.cfg"));
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
        m_config.load();
        Variable variable = Variable.Instance();

        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<Integer> valueList = new ArrayList<>();


        Set<Map.Entry<String, Integer>> entries = variable.hashPlayerToBattery.entrySet();
        for (Map.Entry<String, Integer> entry : entries)
        {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());

        }

        if(async)
        {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
            {

                writeConfig("userdata", "user_count", keyList.size());
                for (int i = 0; i < keyList.size(); i++)
                {
                    writeConfig("userdata", "user_name_"+i, keyList.get(i));
                    writeConfig("userdata", "user_battery_"+i, valueList.get(i));

                }

                return "배터리 저장 완료";

            });
        }
        else
        {
            writeConfig("userdata", "user_count", keyList.size());
            for (int i = 0; i < keyList.size(); i++)
            {
                writeConfig("userdata", "user_name_"+i, keyList.get(i));
                writeConfig("userdata", "user_battery_"+i, valueList.get(i));

            }
        }


    }
    public static void initConfig()
    {

        m_config.load();

        Variable variable = Variable.Instance();

        int auto_mine_size = getInt("userdata", "user_count");
        if(auto_mine_size == 0)
        {
            writeConfig("userdata", "user_count", 0);
        }
        else
        {

            for (int i = 0; i < auto_mine_size; i++)
            {
                String userName = getString("userdata", "user_name_"+i);
                int battery = getInt("userdata", "user_battery_"+i);
                variable.hashPlayerToBattery.put(userName, battery);

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

}
