package com.doubleos.gw.util;

import net.minecraft.util.ResourceLocation;

public class PuzzleImageInfo
{

    public final int[] m_rotateValue = new int[]{0, 90, 180, 270};

    public int m_value = 0;//0~3
    public int m_textureCount = 0;

    public ResourceLocation m_texture;

    public PuzzleImageInfo(int value, int count, String loc)
    {
        m_value = value;
        m_textureCount = count;

        //"textures/gui/inventory/image_"
        if(count < 10)
            m_texture = new ResourceLocation(Reference.MODID, loc+"0"+m_textureCount+"_"+m_rotateValue[value]+".png");
        else
            m_texture = new ResourceLocation(Reference.MODID, loc+m_textureCount+"_"+m_rotateValue[value]+".png");

    }

}
