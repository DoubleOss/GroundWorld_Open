package com.doubleos.gw.automine.table;

import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AutoMineItemTable implements Serializable
{

    private static final long serialVersionUID = 125367455645l;

    HashMap<ITEM, Integer> hashItemToValue = new HashMap<>();
    public AutoMineItemTable(int m_stone, int m_coal, int m_iron, int m_gold, int m_aluminum, int m_diamond, int m_emerald, int m_redstone, int m_lapis, int m_hardtack, int m_stick, int m_cloth, int plastic, int m_gear, int m_robotCore, int m_vitamin)
    {
        this.m_stone = m_stone;
        this.m_coal = m_coal;
        this.m_iron = m_iron;
        this.m_gold = m_gold;
        this.m_aluminum = m_aluminum;
        this.m_diamond = m_diamond;
        this.m_emerald = m_emerald;
        this.m_redstone = m_redstone;
        this.m_lapis = m_lapis;
        this.m_hardtack = m_hardtack;
        this.m_stick = m_stick;
        this.m_cloth = m_cloth;
        this.plastic = plastic;
        this.m_gear = m_gear;
        this.m_robotCore = m_robotCore;
        this.m_vitamin = m_vitamin;

        hashItemToValue.put(ITEM.STONE, m_stone);
        hashItemToValue.put(ITEM.COAL, m_coal);
        hashItemToValue.put(ITEM.IRON, m_iron);
        hashItemToValue.put(ITEM.GOLD, m_gold);
        hashItemToValue.put(ITEM.ALUMINUM, m_aluminum);
        hashItemToValue.put(ITEM.DIAMOND, m_diamond);
        hashItemToValue.put(ITEM.EMERALD, m_emerald);
        hashItemToValue.put(ITEM.REDSTONE, m_redstone);
        hashItemToValue.put(ITEM.LAPIS, m_lapis);
        hashItemToValue.put(ITEM.HARDTRAK, m_hardtack);
        hashItemToValue.put(ITEM.STICK, m_stick);
        hashItemToValue.put(ITEM.CLOTH, m_cloth);
        hashItemToValue.put(ITEM.PLASTIC, plastic);
        hashItemToValue.put(ITEM.GEAR, m_gear);
        hashItemToValue.put(ITEM.ROBOTCORE, m_robotCore);
        hashItemToValue.put(ITEM.VICTAMIN, m_vitamin);


    }

    int m_stone = 0;
    int m_coal = 0;
    int m_iron = 0;
    int m_gold = 0;
    int m_aluminum = 0;
    int m_diamond = 0;
    int m_emerald = 0;
    int m_redstone = 0;
    int m_lapis = 0;

    int m_hardtack = 0; // 건빵

    int m_stick = 0; //막대기

    int m_cloth = 0;

    int plastic = 0;

    int m_gear = 0;

    int m_robotCore = 0;

    int m_vitamin = 0;

    public enum ITEM
    {
        STONE,
        COAL,
        IRON,
        GOLD,
        ALUMINUM,
        DIAMOND,
        EMERALD,
        REDSTONE,
        LAPIS,
        HARDTRAK,
        STICK,
        CLOTH,
        PLASTIC,
        GEAR,
        ROBOTCORE,
        VICTAMIN
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

    public static AutoMineItemTable fromBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (AutoMineItemTable) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public ITEM getRandomItem()
//    {
//        Random random = new Random();
//        random.setSeed(System.currentTimeMillis());
//
//        int seedValue = random.nextInt(100) + 1;
//
//        System.out.println( "랜덤 시드값"  +seedValue);
//        if(seedValue <= m_stone)
//            return ITEM.STONE;
//        else if(seedValue <= m_coal)
//            return ITEM.COAL;
//        else if(seedValue <= m_iron)
//            return ITEM.IRON;
//        else if(seedValue <= m_gold)
//            return ITEM.GOLD;
//        else if(seedValue <= m_aluminum)
//            return ITEM.ALUMINUM;
//        else if(seedValue <= m_diamond)
//            return ITEM.EMERALD;
//        else if(seedValue <= m_redstone)
//            return ITEM.REDSTONE;
//        else
//            return ITEM.LAPIS;
//
//    }

    public ITEM getRandomItem()
    {
        double totalProbability = 0;
        for (int probability : hashItemToValue.values())
        {
            totalProbability += probability;
        }

        System.out.println(totalProbability);

        // 랜덤한 값을 생성합니다.
        Random random = new Random();
        double randomValue = random.nextDouble() * 1000;  // 0에서 100 사이의 랜덤 값

        // 랜덤 값을 기반으로 아이템을 선택합니다.
        double cumulativeProbability = 0.0;
        for (Map.Entry<ITEM, Integer> entry : hashItemToValue.entrySet())
        {
            cumulativeProbability += entry.getValue();
            if (randomValue < cumulativeProbability)
            {
                return entry.getKey();
            }
        }
        return null;
    }

}
