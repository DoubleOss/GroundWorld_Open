package com.doubleos.gw.util;

public class ImageInfo
{

    public String m_Name = "";
    public PNGInfo m_info;

    public ImageInfo(String name, String url)
    {
        m_Name = name;
        m_info = new PNGInfo(url);
    }

    public ImageInfo(String name)
    {
        m_Name = name;
    }
}
