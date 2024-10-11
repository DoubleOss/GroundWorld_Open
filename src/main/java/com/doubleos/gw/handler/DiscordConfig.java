package com.doubleos.gw.handler;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.variable.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;

public class DiscordConfig
{
    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "Discord.cfg"));
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


        if(getString("discord", "discord_bot_token").equals(""))
        {
            writeConfig("discord", "discord_player_list_size", 0);
            writeConfig("discord", "discord_bot_token", "NONE");
            writeConfig("discord", "discord_guild_id", "");
            writeConfig("discord", "channel_id_1", "");
            writeConfig("discord", "channel_id_2", "");
            writeConfig("discord", "channel_id_3", "");
            writeConfig("discord", "channel_id_4", "");
            writeConfig("discord", "channel_id_5", "");
            writeConfig("discord", "channel_id_6", "");
            writeConfig("discord", "channel_id_7", "");
            writeConfig("discord", "channel_id_8", "");
            writeConfig("discord", "channel_id_9", "");
            writeConfig("discord", "channel_id_10", "");
            writeConfig("playerList", "player_name_0", "");

        }
        else if (!getString("discord", "discord_bot_token").equals("NONE"))
        {
            int playerList = getInt("discord", "discord_player_list_size");

            String botToken = getString("discord", "discord_bot_token");
            long guild = Long.parseLong(getString("discord", "discord_guild_id"));
            long channel1 = Long.parseLong(getString("discord", "channel_id_1"));
            long channel2 = Long.parseLong(getString("discord", "channel_id_2"));
            long channel3 = Long.parseLong(getString("discord", "channel_id_3"));
            long channel4 = Long.parseLong(getString("discord", "channel_id_4"));
            long channel5 = Long.parseLong(getString("discord", "channel_id_5"));
            long channel6 = Long.parseLong(getString("discord", "channel_id_6"));
            long channel7 = Long.parseLong(getString("discord", "channel_id_7"));
            long channel8 = Long.parseLong(getString("discord", "channel_id_8"));
            long channel9 = Long.parseLong(getString("discord", "channel_id_9"));
            long channel10 = Long.parseLong(getString("discord", "channel_id_10"));

            System.out.println(guild);
            DiscordApiData.m_BotToken = botToken;
            DiscordApiData.m_guild = guild;
            DiscordApiData.m_channel_1 = channel1;
            DiscordApiData.m_channel_2 = channel2;
            DiscordApiData.m_channel_3 = channel3;
            DiscordApiData.m_channel_4 = channel4;
            DiscordApiData.m_channel_5 = channel5;
            DiscordApiData.m_channel_6 = channel6;
            DiscordApiData.m_channel_7 = channel7;
            DiscordApiData.m_channel_8 = channel8;
            DiscordApiData.m_channel_9 = channel9;
            DiscordApiData.m_channel_10 = channel10;

            ArrayList<String> list = new ArrayList<>();

            for (int i = 0; i<playerList; i++)
            {
                String text = getString("playerList", "player_name_"+i);
                if(!text.equals(""))
                {
                    list.add(text);
                }
            }

            DiscordApiData.m_playerNameList = list;


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
