package com.doubleos.gw.handler;

import com.doubleos.gw.util.MailData;
import com.doubleos.gw.util.Reference;
import com.doubleos.gw.variable.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MailHandler
{
    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "Mail.cfg"));
            initConfig();

        }
        else
        {
            initConfig();
        }


    }

    public static void writeConfig(String category, String key, int value)
    {
        Configuration config = m_config;
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, String value) {
        Configuration config = m_config;
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println( e + " Cannot load configuration file!");
        } finally {
            config.save();
        }
    }
    public static void writeConfig(String category, String key, float value) {
        Configuration config = m_config;
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(Double.valueOf(value));
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
    }
    public static void writeConfig(String category, String key, long value) {
        Configuration config = m_config;
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static long getLong(String category, String key) {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0l).getLong();
            }
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
            System.out.println(e);
        } finally {
            config.save();
        }
        return 0l;
    }


    public static int getInt(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0;
    }

    public static void saveConfig(boolean async)
    {

        if(async)
        {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
            {

                MailHandler.m_config.load();

                Variable variable = Variable.Instance();

                for(int i = 0 ; i <variable.m_mailDataList.size(); i++)
                {
                    MailData data = variable.m_mailDataList.get(i);

                    NBTTagCompound display1 = (NBTTagCompound) data.m_stack1.getTagCompound();
                    NBTTagCompound display2 = (NBTTagCompound) data.m_stack2.getTagCompound();
                    NBTTagCompound display3 = (NBTTagCompound) data.m_stack3.getTagCompound();
                    NBTTagCompound display4 = (NBTTagCompound) data.m_stack4.getTagCompound();
                    NBTTagCompound display5 = (NBTTagCompound) data.m_stack5.getTagCompound();

                    int size = variable.m_mailDataList.size();

                    String dialog = data.m_text.replace("\n", "/n");
                    String lore1 = "";
                    String lore2 = "";
                    String lore3 = "";
                    String lore4 = "";
                    String lore5 = "";


                    if (display1 != null)
                    {
                        if (display1.getCompoundTag("display") != null)
                        {
                            if ( display1.getCompoundTag("display").getTag("Lore") != null)
                                lore1 = display1.getCompoundTag("display").getTag("Lore").toString();
                        }
                    }
                    if (display2 != null)
                    {
                        if (display2.getCompoundTag("display") != null)
                        {
                            if ( display2.getCompoundTag("display").getTag("Lore") != null)
                                lore2 = display2.getCompoundTag("display").getTag("Lore").toString();
                        }
                    }
                    if (display3 != null)
                    {
                        if (display3.getCompoundTag("display") != null)
                        {
                            if ( display3.getCompoundTag("display").getTag("Lore") != null)
                                lore3 = display3.getCompoundTag("display").getTag("Lore").toString();
                        }
                    }
                    if (display4 != null)
                    {
                        if (display4.getCompoundTag("display") != null)
                        {
                            if ( display4.getCompoundTag("display").getTag("Lore") != null)
                                lore4 = display4.getCompoundTag("display").getTag("Lore").toString();
                        }
                    }
                    if (display5 != null)
                    {
                        if (display5.getCompoundTag("display") != null)
                        {
                            if ( display5.getCompoundTag("display").getTag("Lore") != null)
                                lore5 = display5.getCompoundTag("display").getTag("Lore").toString();
                        }
                    }

                    MailHandler.writeConfig("mail_category", "mail_title_"+i, data.m_title);
                    MailHandler.writeConfig("mail_category", "mail_dialog_"+i, dialog);
                    MailHandler.writeConfig("mail_category", "mail_date_"+i, data.m_date);
                    MailHandler.writeConfig("mail_category", "mail_player_"+i, data.m_recivePlayerName);
                    MailHandler.writeConfig("mail_category", "mail_read_"+i, String.valueOf(data.m_readActive));
                    MailHandler.writeConfig("mail_category", "mail_giveactive_"+i, String.valueOf(data.m_receiveActive));

                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_0", data.m_stack1.getItem().getRegistryName().toString());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_0", data.m_stack1.getMetadata());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_0", data.m_stack1.getCount());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_0", data.m_stack1.getDisplayName());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_0", loreReplaceString(lore1));

                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_1", data.m_stack2.getItem().getRegistryName().toString());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_1", data.m_stack2.getMetadata());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_1", data.m_stack2.getCount());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_1", data.m_stack2.getDisplayName());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_1", loreReplaceString(lore2));

                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_2", data.m_stack3.getItem().getRegistryName().toString());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_2", data.m_stack3.getMetadata());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_2", data.m_stack3.getCount());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_2", data.m_stack3.getDisplayName());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_2", loreReplaceString(lore3));

                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_3", data.m_stack4.getItem().getRegistryName().toString());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_3", data.m_stack4.getMetadata());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_3", data.m_stack4.getCount());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_3", data.m_stack4.getDisplayName());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_3", loreReplaceString(lore4));

                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_4", data.m_stack5.getItem().getRegistryName().toString());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_4", data.m_stack5.getMetadata());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_4", data.m_stack5.getCount());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_4", data.m_stack5.getDisplayName());
                    MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_4", loreReplaceString(lore5));

                }

                MailHandler.writeConfig("mail_category", "mail_count", variable.m_mailDataList.size());


                return "메일";
            });
        }
        else
        {

            MailHandler.m_config.load();

            Variable variable = Variable.Instance();

            for(int i = 0 ; i <variable.m_mailDataList.size(); i++)
            {
                MailData data = variable.m_mailDataList.get(i);

                NBTTagCompound display1 = (NBTTagCompound) data.m_stack1.getTagCompound();
                NBTTagCompound display2 = (NBTTagCompound) data.m_stack2.getTagCompound();
                NBTTagCompound display3 = (NBTTagCompound) data.m_stack3.getTagCompound();
                NBTTagCompound display4 = (NBTTagCompound) data.m_stack4.getTagCompound();
                NBTTagCompound display5 = (NBTTagCompound) data.m_stack5.getTagCompound();

                int size = variable.m_mailDataList.size();

                String dialog = data.m_text.replace("\n", "/n");
                String lore1 = "";
                String lore2 = "";
                String lore3 = "";
                String lore4 = "";
                String lore5 = "";


                if (display1 != null)
                {
                    if (display1.getCompoundTag("display") != null)
                    {
                        if ( display1.getCompoundTag("display").getTag("Lore") != null)
                            lore1 = display1.getCompoundTag("display").getTag("Lore").toString();
                    }
                }
                if (display2 != null)
                {
                    if (display2.getCompoundTag("display") != null)
                    {
                        if ( display2.getCompoundTag("display").getTag("Lore") != null)
                            lore2 = display2.getCompoundTag("display").getTag("Lore").toString();
                    }
                }
                if (display3 != null)
                {
                    if (display3.getCompoundTag("display") != null)
                    {
                        if ( display3.getCompoundTag("display").getTag("Lore") != null)
                            lore3 = display3.getCompoundTag("display").getTag("Lore").toString();
                    }
                }
                if (display4 != null)
                {
                    if (display4.getCompoundTag("display") != null)
                    {
                        if ( display4.getCompoundTag("display").getTag("Lore") != null)
                            lore4 = display4.getCompoundTag("display").getTag("Lore").toString();
                    }
                }
                if (display5 != null)
                {
                    if (display5.getCompoundTag("display") != null)
                    {
                        if ( display5.getCompoundTag("display").getTag("Lore") != null)
                            lore5 = display5.getCompoundTag("display").getTag("Lore").toString();
                    }
                }

                MailHandler.writeConfig("mail_category", "mail_title_"+i, data.m_title);
                MailHandler.writeConfig("mail_category", "mail_dialog_"+i, dialog);
                MailHandler.writeConfig("mail_category", "mail_date_"+i, data.m_date);
                MailHandler.writeConfig("mail_category", "mail_player_"+i, data.m_recivePlayerName);
                MailHandler.writeConfig("mail_category", "mail_read_"+i, String.valueOf(data.m_readActive));
                MailHandler.writeConfig("mail_category", "mail_giveactive_"+i, String.valueOf(data.m_receiveActive));

                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_0", data.m_stack1.getItem().getRegistryName().toString());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_0", data.m_stack1.getMetadata());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_0", data.m_stack1.getCount());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_0", data.m_stack1.getDisplayName());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_0", loreReplaceString(lore1));

                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_1", data.m_stack2.getItem().getRegistryName().toString());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_1", data.m_stack2.getMetadata());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_1", data.m_stack2.getCount());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_1", data.m_stack2.getDisplayName());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_1", loreReplaceString(lore2));

                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_2", data.m_stack3.getItem().getRegistryName().toString());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_2", data.m_stack3.getMetadata());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_2", data.m_stack3.getCount());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_2", data.m_stack3.getDisplayName());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_2", loreReplaceString(lore3));

                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_3", data.m_stack4.getItem().getRegistryName().toString());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_3", data.m_stack4.getMetadata());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_3", data.m_stack4.getCount());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_3", data.m_stack4.getDisplayName());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_3", loreReplaceString(lore4));

                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_id_4", data.m_stack5.getItem().getRegistryName().toString());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_meta_4", data.m_stack5.getMetadata());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_amount_4", data.m_stack5.getCount());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_displayname_4", data.m_stack5.getDisplayName());
                MailHandler.writeConfig("mail_category", "mail_item_"+i+"_lore_4", loreReplaceString(lore5));

            }

            MailHandler.writeConfig("mail_category", "mail_count", variable.m_mailDataList.size());

        }


    }
    static String loreReplaceString(String lore)
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



    public static void initConfig()
    {

        m_config.load();

        Variable variable = Variable.Instance();

        int mailCount = getInt("mail_category", "mail_count");//메일 쌓인 개수

        if(mailCount == 0)
        {
            writeConfig("mail_category", "mail_count", 0);
        }
        else
        {
            //mail_title_i
            //mail_dialog_i
            //mail_player_i
            //mail_item_i_j
            //mail_read_i
            //mail_giveActive_i
            int mailData = 1;
            for(int i = 0; i<mailCount; i++)
            {
                String title = getString("mail_category", "mail_title_"+i);
                String dialog = getString("mail_category", "mail_dialog_"+i);
                dialog = dialog.replace("/n", "\n");
                String date = getString("mail_category", "mail_date_"+i);
                String player = getString("mail_category", "mail_player_"+i);
                boolean read = Boolean.parseBoolean( getString("mail_category", "mail_read_"+i));
                boolean giveActive = Boolean.parseBoolean( getString("mail_category", "mail_giveactive_"+i));

                ArrayList<ItemStack> stacks = new ArrayList<>();
                for(int j = 0; j<5; j++)
                {

                    String itemId = getString("mail_category", "mail_item_"+i+"_id_"+j);//아이템 Reg
                    int itemMeta = getInt("mail_category", "mail_item_"+i+"_meta_"+j);//아이템메타
                    int itemAmount = getInt("mail_category", "mail_item_"+i+"_amount_"+j);//아이템개수
                    String itemDisplayName = getString("mail_category", "mail_item_"+i+"_displayname_"+j);//아이템 디스플 이름
                    String itemLore = getString("mail_category", "mail_item_"+i+"_lore_"+j);
                    //mail_item_1_lore_4

                    Item checkItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));

                    Item item = Items.AIR;

//                    System.out.println(itemDisplayName + "   " +  item + "    " + checkItem);

                    if(checkItem != null)
                        item = checkItem;


                    //stacks.add(GroundItemStack.createItemStackLore(item, itemDisplayName, itemLore));
                    //System.out.println(GroundItemStack.createItemStackLore(item, itemDisplayName, itemLore));

                    ItemStack requestStack = new ItemStack(item, itemAmount, itemMeta);
                    NBTTagCompound nbt = requestStack.getTagCompound();
                    if (nbt == null)
                        nbt = new NBTTagCompound();

                    NBTTagList lore = new NBTTagList();
                    lore.appendTag(new NBTTagString(itemLore));

                    NBTTagCompound display = new NBTTagCompound();
                    display.setTag("Lore", lore);
                    nbt.setTag("display", display);
                    requestStack.setTagCompound(nbt);

                    requestStack.setStackDisplayName(itemDisplayName);


                    stacks.add(requestStack);


                }

                ItemStack stack0 = stacks.get(0) == null ? Items.AIR.getDefaultInstance() : stacks.get(0);
                ItemStack stack1 = stacks.get(1) == null ? Items.AIR.getDefaultInstance() : stacks.get(1);
                ItemStack stack2 = stacks.get(2) == null ? Items.AIR.getDefaultInstance() : stacks.get(2);
                ItemStack stack3 = stacks.get(3) == null ? Items.AIR.getDefaultInstance() : stacks.get(3);
                ItemStack stack4 = stacks.get(4) == null ? Items.AIR.getDefaultInstance() : stacks.get(4);


                variable.m_mailDataList.add(new MailData(i, title, dialog, date, giveActive, read, player, stack0, stack1, stack2, stack3, stack4));


            }

            System.out.println( "등록된 메일 개수 - " + variable.m_mailDataList.size());

        }

    }


    public static float getFloat(String category, String key) {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return (float)config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0f;
    }
    public static double getDouble(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0D;
    }

    public static String getString(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception e) {
            System.out.println( e + "Cannot load configuration file!");
        } finally {
            config.save();
        }
        return "";
    }
    public static void removeResetClickWrite()
    {
        Configuration config = m_config;
        config.removeCategory(config.getCategory("click"));
        config.save();

        ClickEventList clickList = ClickEventList.Instance();

        for(int i = 0 ; i < clickList.m_clickAllList.size(); i++)
        {
            ClickBlockFadeTeleportInfo info = clickList.m_clickAllList.get(i);
            info.m_number = i;

            HudConfig.writeConfig("click", "click"+i+"number", info.m_number);
            HudConfig.writeConfig("click", "click"+i+"command", info.m_command);
            HudConfig.writeConfig("click", "click"+i+"blockPos_X", (float)info.m_clickBlockPos.getX());
            HudConfig.writeConfig("click", "click"+i+"blockPos_Y", (float)info.m_clickBlockPos.getY());
            HudConfig.writeConfig("click", "click"+i+"blockPos_Z", (float)info.m_clickBlockPos.getZ());


        }


        clickList.clickSize = clickList.m_clickAllList.size();
        HudConfig.writeConfig("click", "clickListSize", clickList.clickSize);

        int interBlockSize = getInt("click", "clickListSize");
        ClickEventList list = ClickEventList.Instance();

        list.clickSize = interBlockSize;
        config.save();
    }
    public static void removeResetInterBlockWrite()
    {
        Configuration config = m_config;
        config.removeCategory(config.getCategory("interaction"));
        config.save();

        InteractionBlockEventList interBlockList = InteractionBlockEventList.Instance();

        for(int i = 0 ; i < interBlockList.m_interactionBlockList.size(); i++)
        {
            InteractionBlock info = interBlockList.m_interactionBlockList.get(i);
            info.m_checkNumber = i;

            HudConfig.writeConfig("interaction", "interaction_block_"+i+"number", i);
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"command", info.m_command);
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"blockPos_X", (float)info.m_blockPos.getX());
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"blockPos_Y", (float)info.m_blockPos.getY());
            HudConfig.writeConfig("interaction", "interaction_block_"+i+"blockPos_Z", (float)info.m_blockPos.getZ());
            System.out.println(i);
        }


        interBlockList.clickSize = interBlockList.m_interactionBlockList.size();
        HudConfig.writeConfig("interaction", "interaction_block_size", interBlockList.clickSize);
        interBlockList.m_interactionBlockList.clear();

        int interBlockSize = getInt("interaction", "interaction_block_size");
        InteractionBlockEventList list = InteractionBlockEventList.Instance();

        for (int i = 0; i<interBlockSize; i++)
        {
            int clickNumber = getInt("interaction", "interaction_block_"+i+"number");
            String command = getString("interaction", "click"+i+"command");

            float x = getFloat("interaction", "interaction_block_"+i+"blockPos_X");
            float y = getFloat("interaction", "interaction_block_"+i+"blockPos_Y");
            float z = getFloat("interaction", "interaction_block_"+i+"blockPos_Z");



            BlockPos pos = new BlockPos(x, y, z);
            InteractionBlock interBlock = new InteractionBlock(clickNumber, pos, command);
            list.m_interactionBlockList.add(interBlock);

        }
        list.clickSize = interBlockSize;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.MODID))
        {
            initConfig();
            System.out.println("바뀜");
        }

    }
}
