package com.doubleos.gw.automine;

import com.doubleos.gw.automine.table.AutoMineItemTable;
import com.doubleos.gw.handler.AutoMineConfig;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import org.json.JSONArray;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AutoMineList
{

    //싱글톤
    private AutoMineList()
    {

    }

    private static class AutoMineListVariableClazz
    {
        private static final AutoMineList uniqueGameVariable = new AutoMineList();
    }

    public static AutoMineList Instance()
    {
        return AutoMineList.AutoMineListVariableClazz.uniqueGameVariable;
    }

    public static boolean isWithinBounds(BlockPos pos, BlockPos min, BlockPos max) {
        return pos.getX() >= min.getX() && pos.getX() <= max.getX() &&
                pos.getY() >= min.getY() && pos.getY() <= max.getY() &&
                pos.getZ() >= min.getZ() && pos.getZ() <= max.getZ();
    }

    public void init(File file)
    {

        //(int m_stone, int m_coal, int m_iron, int m_gold, int m_aluminum, int m_diamond, int m_emerald, int m_redstone, int
       // m_lapis, int m_hardtack, int m_stick, int m_cloth, int plastic, int m_gear, int m_robotCore, int m_vitamin)

        //1000 단위
        m_seoulTable = new AutoMineItemTable(350, 200, 100, 150, 60, 25, 25, 0, 0, 50, 40, 0, 0, 0 ,0, 0);
        m_ghwTable = new AutoMineItemTable(350, 150, 0, 0, 100, 55, 55, 70, 70, 0, 50, 100, 0, 0, 0, 0);
        m_yydTable = new AutoMineItemTable(0, 0, 50, 50, 55, 100, 100, 120, 120, 0, 0, 0, 400, 5, 0 ,0);
        m_jamsilTable = new AutoMineItemTable(0, 50, 0, 125, 150, 150, 150, 170, 147, 0, 0, 0, 0, 0, 3, 55);

        int AreaId = 0;


        AutoMineArea area1 = new AutoMineArea(8, AutoMine.AUTOMINE_AREA.SEOUL_STATION, 68, 64, -188, m_seoulTable);
        AutoMineArea area2 = new AutoMineArea(6, AutoMine.AUTOMINE_AREA.SEOUL_STATION, 77, 74, -522, m_seoulTable);
        AutoMineArea area3 = new AutoMineArea(7, AutoMine.AUTOMINE_AREA.SEOUL_STATION, 259, 100, -133, m_seoulTable);


        AutoMineArea area4 = new AutoMineArea(5, AutoMine.AUTOMINE_AREA.GHM, -390, 63, -310, m_ghwTable);
        AutoMineArea area5 = new AutoMineArea(0, AutoMine.AUTOMINE_AREA.GHM, -438, 85, -506, m_ghwTable);
        AutoMineArea area6 = new AutoMineArea(9, AutoMine.AUTOMINE_AREA.GHM, -489, 16, -203, m_ghwTable);


        AutoMineArea area7 = new AutoMineArea(1, AutoMine.AUTOMINE_AREA.YYD, -103, 66, 716, m_yydTable);
        AutoMineArea area8 = new AutoMineArea(2, AutoMine.AUTOMINE_AREA.YYD, -683, 65, 238, m_yydTable);
        AutoMineArea area9 = new AutoMineArea(10, AutoMine.AUTOMINE_AREA.YYD, -907, 65, 561, m_yydTable);


        //AutoMineArea area10 = new AutoMineArea(AreaId++, AutoMine.AUTOMINE_AREA.SEOUL_STATION, 270, 40, -652, m_jamsilTable); //테스트맵
        AutoMineArea area10 = new AutoMineArea(4, AutoMine.AUTOMINE_AREA.JAMSIL, 814, 123, 1002, m_jamsilTable);
        AutoMineArea area11 = new AutoMineArea(3, AutoMine.AUTOMINE_AREA.JAMSIL, 630, 77, 635, m_jamsilTable);
        AutoMineArea area12 = new AutoMineArea(11, AutoMine.AUTOMINE_AREA.JAMSIL, 554, 61, 882, m_jamsilTable);



        m_mapAreaList.add(area1);
        m_mapAreaList.add(area2);
        m_mapAreaList.add(area3);
        m_mapAreaList.add(area4);
        m_mapAreaList.add(area5);
        m_mapAreaList.add(area6);
        m_mapAreaList.add(area7);
        m_mapAreaList.add(area8);
        m_mapAreaList.add(area9);
        m_mapAreaList.add(area10);
        m_mapAreaList.add(area11);
        m_mapAreaList.add(area12);


        AutoMineConfig.init(file);


    }


    public AutoMineItemTable m_seoulTable;

    public AutoMineItemTable m_ghwTable;
    public AutoMineItemTable m_yydTable;
    public AutoMineItemTable m_jamsilTable;

    public ArrayList<AutoMineArea> m_mapAreaList = new ArrayList<>();


    public ArrayList<AutoMine> m_autoMineList = new ArrayList<>();

    public void loadAutoAreaListJson(File file)
    {

    }
    public void saveAutoAreaListJson(File file)
    {


    }


    public void saveAutoAreaJson(File file)
    {

        AutoMine mine = new AutoMine("2132145");
        mine.m_autoMineOwnerName = "samsik23";

        AutoMine mine2 = new AutoMine("41234123");
        mine.m_autoMineOwnerName = "12";

        AutoMine mine3 = new AutoMine("45312421");
        mine.m_autoMineOwnerName = "124124";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonStr = gson.toJson(mine);
        String jsonStr2 = gson.toJson(mine2);
        String jsonStr3 = gson.toJson(mine3);


        JSONArray jsonArray = new JSONArray();

        JsonParser parser = new JsonParser();

        jsonArray.put(parser.parse(jsonStr));
        jsonArray.put(parser.parse(jsonStr2));
        jsonArray.put(parser.parse(jsonStr3));


        File hudConfigDic = new File(file.getPath(), "/Gw");
        hudConfigDic.mkdir();
        File fileJson = new File(hudConfigDic+"/AutoMineData.json");
        if(fileJson.exists())
        {

        }
        else
        {
            try {
                FileWriter fw = new FileWriter(hudConfigDic + "/AutoMineData.json");

                gson.toJson(jsonArray, fw);
                fw.flush();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }




}
