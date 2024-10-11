package com.doubleos.gw.variable;

import net.minecraft.util.math.BlockPos;

public class InteractionBlock
{
    public String m_areaName = "";

    public boolean m_areaCloseActive = false;

    public String m_activePlayerName = "";

    public BlockPos m_blockPos = BlockPos.ORIGIN;

    public int m_checkNumber = 0;

    public String m_command = "";

    public boolean m_active = true;

    public InteractionBlock(int number, BlockPos pos, String command)
    {
        m_checkNumber = number;
        m_command = command;
        m_blockPos = pos;
    }



}
