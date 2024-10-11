package com.doubleos.gw.handler;



import com.doubleos.gw.util.GallData;
import com.doubleos.gw.variable.Variable;
import com.sun.org.apache.bcel.internal.util.JavaWrapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.util.ArrayList;

public class ClueHandler
{
    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "GroundWorld_Clue.cfg"));
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

    public static void initConfig()
    {

        m_config.load();

        Variable variable = Variable.Instance();

        int clue_data_size = getInt("clue_data", "clue_data_size");
        if(clue_data_size == 0)
        {
            writeConfig("clue_data", "clue_data_size", variable.m_gallDataList.size());
            for (int i = 0; i < variable.m_gallDataList.size(); i++)
            {
                writeConfig("clue_data", "clue_data_"+i, "false");
            }

        }
        else if (variable.m_gallDataList.size() == clue_data_size)
        {
            for (int i = 0; i < variable.m_gallDataList.size(); i++)
            {
                String clueActive = getString("clue_data", "clue_data_"+i);
                Boolean active = Boolean.parseBoolean(clueActive);
                GallData data = variable.m_gallDataList.get(i);
                data.m_lock = active;
            }
        }
        else
        {
            writeConfig("clue_data", "clue_data_size", variable.m_gallDataList.size());
            for (int i = 0; i < variable.m_gallDataList.size(); i++)
            {
                String clueActive = getString("clue_data", "clue_data_"+i);
                if(clueActive.equals(""))
                {

                }
                else
                {
                    writeConfig("clue_data", "clue_data_"+i, "false");
                }

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

    }
    public static void removeResetInterBlockWrite()
    {

    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {

    }
}
