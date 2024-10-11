package com.doubleos.gw.automine;

import com.doubleos.gw.automine.table.AutoMineItemTable;
import com.doubleos.gw.init.ModItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.json.JSONArray;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class AutoMineArea implements Serializable
{
    private static final long serialVersionUID = 1253674556l;



    public int m_areaStartXPos = 0;

    public int m_areaStartZPos = 0;

    public int m_areaStartYPos = 0;


    public int m_currentAutoMineCount = 0;

    public int m_maxAutoMineCount = 1;


    public AutoMine.AUTOMINE_AREA m_area = AutoMine.AUTOMINE_AREA.NONE;

    public AutoMineItemTable m_areaTable;

    public String ownerName = "";

    public int entityNumberName = 0;

    public int areaId = 0;

    public int m_currentDurability = 100;

    public int m_maxDurability = 100;
    public int m_currentOil = 0;

    public int m_maxCurrentOil = 100;

    public int m_maxSec = 8;

    public int m_currentSec = 0;

    public int m_stone = 0;
    public int m_coal = 0;
    public int m_iron = 0;
    public int m_gold = 0;
    public int m_aluminum = 0;
    public int m_diamond = 0;
    public int m_emerald = 0;
    public int m_redstone = 0;
    public int m_lapis = 0;

    public int m_hardtack = 0; // 건빵

    public int m_stick = 0; //막대기

    public int m_cloth = 0;

    public int plastic = 0;

    public int m_gear = 0;

    public int m_robotCore = 0;

    public int m_vitamin = 0;

    public boolean m_alive = false;

    public HashMap<AutoMineItemTable.ITEM, Integer> hashItemToCount = new HashMap<>();

    public AutoMineArea()
    {
        initHash();
    }

    public AutoMineArea(int areaId, AutoMine.AUTOMINE_AREA area, int areaXpos, int areaYpos, int areaZpos, AutoMineItemTable table)
    {
        this.areaId = areaId;
        m_area = area;
        m_areaStartXPos = areaXpos;
        m_areaStartZPos = areaZpos;
        m_areaStartYPos = areaYpos;

        initHash();

        m_areaTable = table;
    }


    public void reset()
    {
        m_currentDurability = 0;

        m_currentOil = 0;
        m_currentAutoMineCount = 0;

        m_maxSec = 8;

        m_currentSec = 0;

        m_stone = 0;
        m_coal = 0;
        m_iron = 0;
        m_gold = 0;
        m_aluminum = 0;
        m_diamond = 0;
        m_emerald = 0;
        m_redstone = 0;
        m_lapis = 0;

        m_hardtack = 0; // 건빵

        m_stick = 0; //막대기

        m_cloth = 0;

        plastic = 0;

        m_gear = 0;

        m_robotCore = 0;

        m_vitamin = 0;

        m_alive = false;

        //ownerName = "";

    }
    public void initHash()
    {
        hashItemToCount.put(AutoMineItemTable.ITEM.STONE, m_stone);
        hashItemToCount.put(AutoMineItemTable.ITEM.COAL, m_coal);
        hashItemToCount.put(AutoMineItemTable.ITEM.IRON, m_iron);
        hashItemToCount.put(AutoMineItemTable.ITEM.GOLD, m_gold);
        hashItemToCount.put(AutoMineItemTable.ITEM.ALUMINUM, m_aluminum);
        hashItemToCount.put(AutoMineItemTable.ITEM.DIAMOND, m_diamond);
        hashItemToCount.put(AutoMineItemTable.ITEM.EMERALD, m_emerald);
        hashItemToCount.put(AutoMineItemTable.ITEM.REDSTONE, m_redstone);
        hashItemToCount.put(AutoMineItemTable.ITEM.LAPIS, m_lapis);
        hashItemToCount.put(AutoMineItemTable.ITEM.HARDTRAK, m_hardtack);
        hashItemToCount.put(AutoMineItemTable.ITEM.STICK, m_stick);
        hashItemToCount.put(AutoMineItemTable.ITEM.CLOTH, m_cloth);
        hashItemToCount.put(AutoMineItemTable.ITEM.PLASTIC, plastic);
        hashItemToCount.put(AutoMineItemTable.ITEM.GEAR, m_gear);
        hashItemToCount.put(AutoMineItemTable.ITEM.ROBOTCORE, m_robotCore);
    }

    public byte[] toBytes() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AutoMineArea fromBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (AutoMineArea) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ItemStack getItem(AutoMineItemTable.ITEM item)
    {
        switch (item) {
            case STONE:
                return ItemBlock.getItemFromBlock(Blocks.COBBLESTONE).getDefaultInstance();
            case COAL:
                return Items.COAL.getDefaultInstance();
            case IRON:
                return Items.IRON_INGOT.getDefaultInstance();

            case GOLD:
                return Items.GOLD_INGOT.getDefaultInstance();

            case ALUMINUM:
                return ModItems.aluminum.getDefaultInstance();

            case DIAMOND:
                return Items.DIAMOND.getDefaultInstance();

            case EMERALD:
                return Items.EMERALD.getDefaultInstance();

            case REDSTONE:
                return Items.REDSTONE.getDefaultInstance();

            case LAPIS:
                return new ItemStack(Items.DYE, 1, 4);

            case HARDTRAK:
                return ModItems.hardtack.getDefaultInstance();

            case STICK:
                return Items.STICK.getDefaultInstance();

            case CLOTH:
                return ModItems.cloth.getDefaultInstance();

            case PLASTIC:
                return ModItems.plastic.getDefaultInstance();

            case GEAR:
                return ModItems.gear.getDefaultInstance();

            case ROBOTCORE:
                return ModItems.robot_core.getDefaultInstance();

            case VICTAMIN:
                return ModItems.vitamin.getDefaultInstance();

            default:
                System.out.println(" 아이템 반환 중 에러가 났습니다.");
                return Items.AIR.getDefaultInstance();
        }
    }

    public void addItem(AutoMineItemTable.ITEM item) {
        switch (item) {
            case STONE:
                m_stone++;
                break;
            case COAL:
                m_coal++;
                break;
            case IRON:
                m_iron++;
                break;
            case GOLD:
                m_gold++;
                break;
            case ALUMINUM:
                m_aluminum++;
                break;
            case DIAMOND:
                m_diamond++;
                break;
            case EMERALD:
                m_emerald++;
                break;
            case REDSTONE:
                m_redstone++;
                break;
            case LAPIS:
                m_lapis++;
                break;
            case HARDTRAK:
                m_hardtack++;
                break;
            case STICK:
                m_stick++;
                break;
            case CLOTH:
                m_cloth++;
                break;
            case PLASTIC:
                plastic++;
                break;
            case GEAR:
                m_gear++;
                break;
            case ROBOTCORE:
                m_robotCore++;
                break;
            case VICTAMIN:
                m_vitamin++;
                break;
            default:
                System.out.println(" 오토마인 생성중 에러가 났습니다.");
        }
    }


}
