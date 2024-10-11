package com.doubleos.gw.handler;

import com.doubleos.gw.util.Reference;
import com.doubleos.gw.variable.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ChargeDataConfig
{

    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/Gw");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "Chage.cfg"));
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

    public static void initConfig()
    {

        m_config.load();

        Variable variable = Variable.Instance();

        int chargeCount = getInt("charge", "count");//충전기개수

        if(chargeCount == 0)
        {
            writeConfig("charge", "count", 0);
        }
        else
        {
            //charge_title_i
            //charge_area_i
            //charge_type_i //기름 or 전기
            //charge_amount_i
            //charge_pos_x_i
            //charge_infi_i // 무제한 여부

            for(int i = 0; i<chargeCount; i++)
            {
                int chargeDataId = getInt("charge", "charge_id_" + i);
                String title = getString("charge", "charge_title_"+i);
                String area = getString("charge", "charge_area_"+i);
                String type = getString("charge", "charge_type_"+i);
                int posX = getInt("charge", "charge_pos_x_"+i);
                int posY = getInt("charge", "charge_pos_y_"+i);
                int posZ = getInt("charge", "charge_pos_z_"+i);

                int amount = getInt("charge", "charge_amount_"+i);
                boolean infinity = Boolean.parseBoolean( getString("charge", "charge_infi_"+i));

                BlockPos pos = new BlockPos(posX, posY, posZ);


                variable.m_chargeDataList.add(new ChargeData(chargeDataId, title, area, type, amount, infinity, pos));
            }

            System.out.println( "등록된 충전소 데이터 개수 - " + variable.m_chargeDataList.size());

        }

    }

    public static void saveConfig(boolean async)
    {

        if(async)
        {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
            {

                Variable variable = Variable.Instance();

                for(int i = 0; i<variable.m_chargeDataList.size(); i++)
                {
                    ChargeData data = variable.m_chargeDataList.get(i);
                    writeConfig("charge", "count", Variable.Instance().m_chargeDataList.size());
                    writeConfig("charge", "charge_id_"+data.getChargeDataId(), data.getChargeDataId());
                    writeConfig("charge", "charge_title_"+data.getChargeDataId(), data.getTitle());
                    writeConfig("charge", "charge_area_"+data.getChargeDataId(), data.getArea());
                    writeConfig("charge", "charge_type_"+data.getChargeDataId(), data.getType());
                    writeConfig("charge", "charge_pos_x_"+data.getChargeDataId(), data.getPos().getX());
                    writeConfig("charge", "charge_pos_y_"+data.getChargeDataId(), data.getPos().getY());
                    writeConfig("charge", "charge_pos_z_"+data.getChargeDataId(), data.getPos().getZ());
                    writeConfig("charge", "charge_amount_"+data.getChargeDataId(), data.getAmount());
                    writeConfig("charge", "charge_infi_"+data.getChargeDataId(), String.valueOf(data.isInfinity()));
                }


                return "주유소 저장 완료";

            });
        }
        else
        {
            Variable variable = Variable.Instance();

            for(int i = 0; i<variable.m_chargeDataList.size(); i++)
            {
                ChargeData data = variable.m_chargeDataList.get(i);
                writeConfig("charge", "count", Variable.Instance().m_chargeDataList.size());
                writeConfig("charge", "charge_id_"+data.getChargeDataId(), data.getChargeDataId());
                writeConfig("charge", "charge_title_"+data.getChargeDataId(), data.getTitle());
                writeConfig("charge", "charge_area_"+data.getChargeDataId(), data.getArea());
                writeConfig("charge", "charge_type_"+data.getChargeDataId(), data.getType());
                writeConfig("charge", "charge_pos_x_"+data.getChargeDataId(), data.getPos().getX());
                writeConfig("charge", "charge_pos_y_"+data.getChargeDataId(), data.getPos().getY());
                writeConfig("charge", "charge_pos_z_"+data.getChargeDataId(), data.getPos().getZ());
                writeConfig("charge", "charge_amount_"+data.getChargeDataId(), data.getAmount());
                writeConfig("charge", "charge_infi_"+data.getChargeDataId(), String.valueOf(data.isInfinity()));
            }

        }

    }
        public static void saveChargeData(ChargeData data)
    {


        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
        {

            writeConfig("charge", "count", Variable.Instance().m_chargeDataList.size());
            writeConfig("charge", "charge_id_"+data.getChargeDataId(), data.getChargeDataId());
            writeConfig("charge", "charge_title_"+data.getChargeDataId(), data.getTitle());
            writeConfig("charge", "charge_area_"+data.getChargeDataId(), data.getArea());
            writeConfig("charge", "charge_type_"+data.getChargeDataId(), data.getType());
            writeConfig("charge", "charge_pos_x_"+data.getChargeDataId(), data.getPos().getX());
            writeConfig("charge", "charge_pos_y_"+data.getChargeDataId(), data.getPos().getY());
            writeConfig("charge", "charge_pos_z_"+data.getChargeDataId(), data.getPos().getZ());
            writeConfig("charge", "charge_amount_"+data.getChargeDataId(), data.getAmount());
            writeConfig("charge", "charge_infi_"+data.getChargeDataId(), String.valueOf(data.isInfinity()));

            return "주유소 저장 완료";

        });

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
