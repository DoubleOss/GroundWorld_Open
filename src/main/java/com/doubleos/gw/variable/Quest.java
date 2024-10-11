package com.doubleos.gw.variable;

public class Quest
{


    public int m_questPage;

    public String m_imageName = "";

    public boolean m_Success = false;

    public boolean m_viewActive = false;

    public Quest(int page){
        m_questPage = page;
    }


    public Quest(int page, String imageName, boolean success, boolean viewActive)
    {
        m_questPage = page;
        m_imageName = imageName;
        m_Success = success;
        m_viewActive = viewActive;

    }


}
