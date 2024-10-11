package com.doubleos.gw.variable;

import java.util.ArrayList;

public class InteractionBlockEventList
{
    private InteractionBlockEventList()
    {

    }

    private static class InnerInstanceGameVariableClazz
    {
        private static final InteractionBlockEventList uniqueGameVariable = new InteractionBlockEventList();
    }

    public static InteractionBlockEventList Instance()
    {
        return InnerInstanceGameVariableClazz.uniqueGameVariable;
    }



    public int clickSize = 0;


    public ArrayList<InteractionBlock> m_interactionBlockList = new ArrayList<>();

}
