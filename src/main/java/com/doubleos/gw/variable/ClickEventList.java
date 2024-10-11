package com.doubleos.gw.variable;

import java.util.ArrayList;

public class ClickEventList
{


    //싱글톤
    private ClickEventList()
    {

    }

    private static class InnerInstanceGameVariableClazz
    {
        private static final ClickEventList uniqueGameVariable = new ClickEventList();
    }

    public static ClickEventList Instance()
    {
        return InnerInstanceGameVariableClazz.uniqueGameVariable;
    }



    public int clickSize = 0;


    public ArrayList<ClickBlockFadeTeleportInfo> m_clickAllList = new ArrayList<>();


}
