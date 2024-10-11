package com.doubleos.gw.handler;

import com.doubleos.gw.automine.AutoMine;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.variable.Variable;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AutoMineConfig
{


    public static Configuration m_config;



    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "automine.cfg"));
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
        AutoMineList autoMine = AutoMineList.Instance();

        if(async)
        {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
            {

                for (int i = 0; i < autoMine.m_mapAreaList.size(); i++)
                {
                    AutoMineArea area = autoMine.m_mapAreaList.get(i);
                    writeConfig("automine", "auto_mine_number_"+ area.areaId, area.areaId);
                    writeConfig("automine", "auto_mine_pos_x_"+ area.areaId, area.m_areaStartXPos);
                    writeConfig("automine", "auto_mine_pos_y_"+ area.areaId, area.m_areaStartYPos);
                    writeConfig("automine", "auto_mine_pos_z_"+ area.areaId, area.m_areaStartZPos);
                    writeConfig("automine", "auto_mine_current_count_"+ area.areaId, area.m_currentAutoMineCount);
                    writeConfig("automine", "auto_mine_area_name_"+ area.areaId, area.m_area.name());
                    writeConfig("automine", "auto_mine_owner_name_"+ area.areaId, area.ownerName);
                    writeConfig("automine", "auto_mine_entity_name_"+ area.areaId, area.entityNumberName);
                    writeConfig("automine", "auto_mine_durability_"+ area.areaId, area.m_currentDurability);
                    writeConfig("automine", "auto_mine_oil_"+ area.areaId, area.m_currentOil);
                    writeConfig("automine", "auto_mine_stone_"+ area.areaId, area.m_stone);
                    writeConfig("automine", "auto_mine_coal_"+ area.areaId, area.m_coal);
                    writeConfig("automine", "auto_mine_iron_"+ area.areaId, area.m_iron);
                    writeConfig("automine", "auto_mine_gold_"+ area.areaId, area.m_gold);
                    writeConfig("automine", "auto_mine_aluminum_"+ area.areaId, area.m_aluminum);
                    writeConfig("automine", "auto_mine_emerald_"+ area.areaId, area.m_emerald);
                    writeConfig("automine", "auto_mine_lapis_"+ area.areaId, area.m_lapis);
                    writeConfig("automine", "auto_mine_hardtack_"+ area.areaId, area.m_hardtack);
                    writeConfig("automine", "auto_mine_stick_"+ area.areaId, area.m_stick);
                    writeConfig("automine", "auto_mine_cloth_"+ area.areaId, area.m_cloth);
                    writeConfig("automine", "auto_mine_plastic_"+ area.areaId, area.plastic);
                    writeConfig("automine", "auto_mine_gear_"+ area.areaId, area.m_gear);
                    writeConfig("automine", "auto_mine_vitamin_"+ area.areaId, area.m_vitamin);
                    writeConfig("automine", "auto_mine_robotcore_"+ area.areaId, area.m_robotCore);
                    writeConfig("automine", "auto_mine_alive_"+ area.areaId, String.valueOf(area.m_alive));


                }

                return "부족 저장 완료";

            });

        }
        else
        {
            for (int i = 0; i < autoMine.m_mapAreaList.size(); i++)
            {
                AutoMineArea area = autoMine.m_mapAreaList.get(i);
                writeConfig("automine", "auto_mine_number_"+ area.areaId, area.areaId);
                writeConfig("automine", "auto_mine_pos_x_"+ area.areaId, area.m_areaStartXPos);
                writeConfig("automine", "auto_mine_pos_y_"+ area.areaId, area.m_areaStartYPos);
                writeConfig("automine", "auto_mine_pos_z_"+ area.areaId, area.m_areaStartZPos);
                writeConfig("automine", "auto_mine_current_count_"+ area.areaId, area.m_currentAutoMineCount);
                writeConfig("automine", "auto_mine_area_name_"+ area.areaId, area.m_area.name());
                writeConfig("automine", "auto_mine_owner_name_"+ area.areaId, area.ownerName);
                writeConfig("automine", "auto_mine_entity_name_"+ area.areaId, area.entityNumberName);
                writeConfig("automine", "auto_mine_durability_"+ area.areaId, area.m_currentDurability);
                writeConfig("automine", "auto_mine_oil_"+ area.areaId, area.m_currentOil);
                writeConfig("automine", "auto_mine_stone_"+ area.areaId, area.m_stone);
                writeConfig("automine", "auto_mine_coal_"+ area.areaId, area.m_coal);
                writeConfig("automine", "auto_mine_iron_"+ area.areaId, area.m_iron);
                writeConfig("automine", "auto_mine_gold_"+ area.areaId, area.m_gold);
                writeConfig("automine", "auto_mine_aluminum_"+ area.areaId, area.m_aluminum);
                writeConfig("automine", "auto_mine_emerald_"+ area.areaId, area.m_emerald);
                writeConfig("automine", "auto_mine_lapis_"+ area.areaId, area.m_lapis);
                writeConfig("automine", "auto_mine_hardtack_"+ area.areaId, area.m_hardtack);
                writeConfig("automine", "auto_mine_stick_"+ area.areaId, area.m_stick);
                writeConfig("automine", "auto_mine_cloth_"+ area.areaId, area.m_cloth);
                writeConfig("automine", "auto_mine_plastic_"+ area.areaId, area.plastic);
                writeConfig("automine", "auto_mine_gear_"+ area.areaId, area.m_gear);
                writeConfig("automine", "auto_mine_vitamin_"+ area.areaId, area.m_vitamin);
                writeConfig("automine", "auto_mine_robotcore_"+ area.areaId, area.m_robotCore);
                writeConfig("automine", "auto_mine_alive_"+ area.areaId, String.valueOf(area.m_alive));

            }
        }



    }
    public static void initConfig()
    {

        m_config.load();

        AutoMineList autoMine = AutoMineList.Instance();

        int auto_mine_size = getInt("automine", "auto_mine_size");
        if(auto_mine_size == 0)
        {
            writeConfig("automine", "auto_mine_size", autoMine.m_mapAreaList.size());
            for (int i = 0; i < autoMine.m_mapAreaList.size(); i++)
            {
                AutoMineArea area = autoMine.m_mapAreaList.get(i);

                int id = area.areaId;
                writeConfig("automine", "auto_mine_number_"+id, area.areaId);
                writeConfig("automine", "auto_mine_pos_x_"+id, area.m_areaStartXPos);
                writeConfig("automine", "auto_mine_pos_y_"+id, area.m_areaStartYPos);
                writeConfig("automine", "auto_mine_pos_z_"+id, area.m_areaStartZPos);
                writeConfig("automine", "auto_mine_current_count_"+id, area.m_currentAutoMineCount);
                writeConfig("automine", "auto_mine_area_name_"+id, area.m_area.name());
                writeConfig("automine", "auto_mine_owner_name_"+id, area.ownerName);
                writeConfig("automine", "auto_mine_entity_name_"+id, area.entityNumberName);
                writeConfig("automine", "auto_mine_durability_"+id, area.m_currentDurability);
                writeConfig("automine", "auto_mine_oil_"+id, area.m_currentOil);
                writeConfig("automine", "auto_mine_stone_"+id, area.m_stone);
                writeConfig("automine", "auto_mine_coal_"+id, area.m_coal);
                writeConfig("automine", "auto_mine_iron_"+id, area.m_iron);
                writeConfig("automine", "auto_mine_gold_"+id, area.m_gold);
                writeConfig("automine", "auto_mine_aluminum_"+id, area.m_aluminum);
                writeConfig("automine", "auto_mine_emerald_"+id, area.m_emerald);
                writeConfig("automine", "auto_mine_lapis_"+id, area.m_lapis);
                writeConfig("automine", "auto_mine_hardtack_"+id, area.m_hardtack);
                writeConfig("automine", "auto_mine_stick_"+id, area.m_stick);
                writeConfig("automine", "auto_mine_cloth_"+id, area.m_cloth);
                writeConfig("automine", "auto_mine_plastic_"+id, area.plastic);
                writeConfig("automine", "auto_mine_gear_"+id, area.m_gear);
                writeConfig("automine", "auto_mine_vitamin_"+id, area.m_vitamin);
                writeConfig("automine", "auto_mine_robotcore_"+id, area.m_robotCore);
                writeConfig("automine", "auto_mine_alive_"+ area.areaId, String.valueOf(area.m_alive));



            }

        }
        else
        {

            for (int i = 0; i < auto_mine_size; i++)
            {
                //(int areaId, AutoMine.AUTOMINE_AREA area, int areaXpos, int areaYpos, int areaZpos, AutoMineItemTable table)
                AutoMineArea area = new AutoMineArea();
                area.areaId = getInt("automine", "auto_mine_number_" + i);

                area.m_areaStartXPos = getInt("automine", "auto_mine_pos_x_" + i);
                area.m_areaStartYPos = getInt("automine", "auto_mine_pos_y_" + i);
                area.m_areaStartZPos = getInt("automine", "auto_mine_pos_z_" + i);
                area.m_currentAutoMineCount = getInt("automine", "auto_mine_current_count_" + i);
                String areaStr = getString("automine", "auto_mine_area_name_" + i);
                area.m_area = AutoMine.AUTOMINE_AREA.valueOf(areaStr);
                area.ownerName = getString("automine", "auto_mine_owner_name_" + i);
                area.entityNumberName = getInt("automine", "auto_mine_entity_name_" + i);
                area.m_currentDurability = getInt("automine", "auto_mine_durability_" + i);
                area.m_currentOil = getInt("automine", "auto_mine_oil_" + i);
                area.m_stone = getInt("automine", "auto_mine_stone_" + i);
                area.m_coal = getInt("automine", "auto_mine_coal_" + i);
                area.m_iron = getInt("automine", "auto_mine_iron_" + i);
                area.m_gold = getInt("automine", "auto_mine_gold_" + i);
                area.m_aluminum = getInt("automine", "auto_mine_aluminum_" + i);
                area.m_emerald = getInt("automine", "auto_mine_emerald_" + i);
                area.m_lapis = getInt("automine", "auto_mine_lapis_" + i);
                area.m_hardtack = getInt("automine", "auto_mine_hardtack_" + i);
                area.m_stick = getInt("automine", "auto_mine_stick_" + i);
                area.m_cloth = getInt("automine", "auto_mine_cloth_" + i);
                area.plastic = getInt("automine", "auto_mine_plastic_" + i);
                area.m_gear = getInt("automine", "auto_mine_gear_" + i);
                area.m_vitamin = getInt("automine", "auto_mine_vitamin_" + i);
                area.m_robotCore = getInt("automine", "auto_mine_robotcore_" + i);
                area.m_alive = Boolean.parseBoolean( getString("automine", "auto_mine_alive_"+i));

                autoMine.m_mapAreaList.set(i, area);

                System.out.println(" 리스트 넘버 - " + area.areaId);
                System.out.println(" 지역명 - " + area.m_area.name());
                System.out.println(" OWNER - " + area.ownerName);



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
