package com.doubleos.gw.util;

import com.doubleos.gw.automine.AutoMine;

import java.io.*;

public class GallData implements Serializable {


    private static final long serialVersionUID = 24544354352l;

    public GallData(int dataNumber, String title, String resourceName)
    {

        m_dataNumber = dataNumber;
        m_Title = title;
        m_resourceName = resourceName;

    }

    public int m_dataNumber = 0;
    public String m_Title = "임시 타이틀";

    public String m_resourceName = "";

    public boolean m_lock = false;


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

    public static GallData fromBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (GallData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }





}
