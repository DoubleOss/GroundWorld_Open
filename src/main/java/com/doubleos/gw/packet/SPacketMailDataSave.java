package com.doubleos.gw.packet;

import com.doubleos.gw.handler.MailHandler;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.CompletableFuture;

public class SPacketMailDataSave implements IMessage, IMessageHandler<SPacketMailDataSave, SPacketMailDataSave>
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

    public SPacketMailDataSave()
    {

    }

    public SPacketMailDataSave(int id, String title, String text, String date, boolean active, boolean readActive, String recivePlayerName,
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


    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_mailId = buf.readInt();
        m_title = ByteBufUtils.readUTF8String(buf);
        m_text = ByteBufUtils.readUTF8String(buf);
        m_date = ByteBufUtils.readUTF8String(buf);
        m_receiveActive = buf.readBoolean();
        m_readActive = buf.readBoolean();
        m_recivePlayerName = ByteBufUtils.readUTF8String(buf);
        m_stack1 = ByteBufUtils.readItemStack(buf);
        m_stack2 = ByteBufUtils.readItemStack(buf);
        m_stack3 = ByteBufUtils.readItemStack(buf);
        m_stack4 = ByteBufUtils.readItemStack(buf);
        m_stack5 = ByteBufUtils.readItemStack(buf);


    }

    @Override
    public void toBytes(ByteBuf buf)
    {

        buf.writeInt(m_mailId);
        ByteBufUtils.writeUTF8String(buf, m_title);
        ByteBufUtils.writeUTF8String(buf, m_text);
        ByteBufUtils.writeUTF8String(buf, m_date);
        buf.writeBoolean(m_receiveActive);
        buf.writeBoolean(m_readActive);
        ByteBufUtils.writeUTF8String(buf, m_recivePlayerName);
        ByteBufUtils.writeItemStack(buf, m_stack1);
        ByteBufUtils.writeItemStack(buf, m_stack2);
        ByteBufUtils.writeItemStack(buf, m_stack3);
        ByteBufUtils.writeItemStack(buf, m_stack4);
        ByteBufUtils.writeItemStack(buf, m_stack5);

    }

    String loreReplaceString(String lore)
    {

        if(lore.isEmpty() || lore == null || lore.equals(""))
        {
            return "";
        }
        lore = lore.replaceAll("\"", "");
        lore = lore.replace("[", "");
        lore = lore.replaceAll("]", "");

        return lore;

    }


    @Override
    public SPacketMailDataSave onMessage(SPacketMailDataSave message, MessageContext ctx)
    {
        Variable variable = Variable.Instance();

        MailData data = new MailData(message.m_mailId, message.m_title, message.m_text, message.m_date, message.m_receiveActive ,message.m_readActive,
                message.m_recivePlayerName, message.m_stack1, message.m_stack2, message.m_stack3, message.m_stack4,message.m_stack5);

        EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.m_recivePlayerName);
        if(player != null)
            Packet.networkWrapper.sendTo(new CPacketInfomation("긴급문자"), player);

        variable.m_mailDataList.add(data);




        return null;
    }
}
