package com.doubleos.gw.handler;

import com.doubleos.gw.util.Reference;
import com.doubleos.gw.variable.ClickBlockFadeTeleportInfo;
import com.doubleos.gw.variable.ClickEventList;
import com.doubleos.gw.variable.InteractionBlock;
import com.doubleos.gw.variable.InteractionBlockEventList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class HudConfig
{
    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "GroundWorld.cfg"));
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


        if(getInt("interaction", "interaction_block_size") == 0)
        {
            writeConfig("interaction", "interaction_block_size", 0);
        }
        else
        {
            int interBlockSize = getInt("interaction", "interaction_block_size");
            InteractionBlockEventList interBlocklist = InteractionBlockEventList.Instance();
            System.out.println(interBlockSize);

            for (int i = 0; i<interBlockSize; i++)
            {
                int clickNumber = getInt("interaction", "interaction_block_"+i+"number");
                String command = getString("interaction", "interaction_block_"+i+"command");

                float x = getFloat("interaction", "interaction_block_"+i+"blockPos_X");
                float y = getFloat("interaction", "interaction_block_"+i+"blockPos_Y");
                float z = getFloat("interaction", "interaction_block_"+i+"blockPos_Z");



                BlockPos pos = new BlockPos(x, y, z);

                InteractionBlock interBlock = new InteractionBlock(clickNumber, pos, command);
                interBlocklist.m_interactionBlockList.add(interBlock);

            }

            interBlocklist.clickSize = interBlockSize;

        }
        if(getInt("click", "clickListSize") == 0)
        {
            writeConfig("click", "clickListSize", 0);
        }
        else
        {
            int clickListSize = getInt("click", "clickListSize");
            ClickEventList list = ClickEventList.Instance();

            for (int i = 0; i<clickListSize; i++)
            {
                int clickNumber = getInt("click", "click"+i+"number");
                String command = getString("click", "click"+i+"command");

                float x = getFloat("click", "click"+i+"blockPos_X");
                float y = getFloat("click", "click"+i+"blockPos_Y");
                float z = getFloat("click", "click"+i+"blockPos_Z");



                BlockPos pos = new BlockPos(x, y, z);
                ClickBlockFadeTeleportInfo teleportInfo = new ClickBlockFadeTeleportInfo(clickNumber, command, pos);
                list.m_clickAllList.add(teleportInfo);

            }

            list.clickSize = clickListSize;

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
