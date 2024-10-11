package com.doubleos.gw.automine;

import com.doubleos.gw.automine.table.AutoMineItemTable;

import java.io.*;

public class AutoMine implements Serializable
{

    private static final long serialVersionUID = 125314456l;

    public int m_currentDurability = 100;

    public int m_maxDurability = 100;

    public int m_currentOil = 100;

    public int m_maxCurrentOil = 100;

    public int m_maxSec = 20;

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

    public String m_placeArea = AUTOMINE_AREA.NONE.name();

    public String m_autoMineOwnerName = "";
    public String m_autoMineName = "";


    public enum AUTOMINE_AREA
    {
        SEOUL_STATION,
        GHM,
        YYD,
        JAMSIL,

        NONE

    }


    public AutoMine(String name)
    {
        m_autoMineName = name;
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

    public static AutoMine fromBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (AutoMine) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
