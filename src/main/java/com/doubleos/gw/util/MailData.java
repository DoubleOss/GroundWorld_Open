package com.doubleos.gw.util;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class MailData
{


    public int m_mailId = 0;

    public String m_title = "";
    public String m_text = "";

    public String m_date = "";

    public boolean m_receiveActive = false;

    public boolean m_readActive = false;

    public String m_recivePlayerName = "";


    public ItemStack m_stack1 = new ItemStack(Items.AIR).copy();
    public ItemStack m_stack2 = new ItemStack(Items.AIR).copy();
    public ItemStack m_stack3 = new ItemStack(Items.AIR).copy();
    public ItemStack m_stack4 = new ItemStack(Items.AIR).copy();
    public ItemStack m_stack5 = new ItemStack(Items.AIR).copy();


    public MailData(int id, String title, String text, String date, boolean active, boolean readActive, String recivePlayerName,
                    ItemStack stack1, ItemStack stack2, ItemStack stack3, ItemStack stack4, ItemStack stack5)
    {
        m_mailId = id;
        m_title = title;
        m_text = text;
        m_date = date;
        m_receiveActive = active;
        m_readActive = readActive;
        m_recivePlayerName = recivePlayerName;
        m_stack1 = stack1;
        m_stack2 = stack2;
        m_stack3 = stack3;
        m_stack4 = stack4;
        m_stack5 = stack5;

    }


    public void give(MinecraftServer server, String playerName)
    {


        server.getPlayerList().getPlayerByUsername(playerName).inventory.addItemStackToInventory(m_stack1.copy());
        server.getPlayerList().getPlayerByUsername(playerName).inventory.addItemStackToInventory(m_stack2.copy());
        server.getPlayerList().getPlayerByUsername(playerName).inventory.addItemStackToInventory(m_stack3.copy());
        server.getPlayerList().getPlayerByUsername(playerName).inventory.addItemStackToInventory(m_stack4.copy());
        server.getPlayerList().getPlayerByUsername(playerName).inventory.addItemStackToInventory(m_stack5.copy());

    }





}
