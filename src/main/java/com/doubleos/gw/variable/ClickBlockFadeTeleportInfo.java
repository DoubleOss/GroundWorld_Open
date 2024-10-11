package com.doubleos.gw.variable;

import net.minecraft.util.math.BlockPos;


public class ClickBlockFadeTeleportInfo
{


    public int m_number = 0;
    public String m_command = "";

    public BlockPos m_teleportPos = BlockPos.ORIGIN;

    public BlockPos m_clickBlockPos = BlockPos.ORIGIN;



    public ClickBlockFadeTeleportInfo(int number, String command, BlockPos clickBlockPos)
    {
        m_number = number;
        m_command = command;
        m_clickBlockPos = clickBlockPos;

    }

}
