package com.doubleos.gw.proxy;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.automine.AutoMine;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.automine.table.AutoMineItemTable;
import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.block.OBJItemBase2;
import com.doubleos.gw.entity.*;
import com.doubleos.gw.handler.*;
import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.packet.*;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.util.InventoryUtils;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.*;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;
import java.util.Timer;

public class CommonProxy
{

    int Count = 0;

    int m_tick = 0;

    int m_fiveMinTick = 0;

    World m_world;
    public void preInit(FMLPreInitializationEvent event)
    {


    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        GroundWorld.channel.register(this);
    }



    public void startSubWayEffect()
    {

    }
    public void subWayReset()
    {

    }

    public void startSubWayEffectEast()
    {

    }
    public void startSubWayEffectLoc(int number, BlockPos pos)
    {

    }
    public void playSound(SoundEvent name)
    {

    }
    public void openGuiScreen(boolean bool)
    {

    }
    public void openEleBankScreen(float bankAmount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, String item, boolean infinity)
    {

    }
    public void openGuiShop(String name)
    {

    }
    public void openBankScreen(float bankAmount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, boolean infinity)
    {

    }
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
    public void openGuiScreenPhoneOpen(boolean antenna)
    {

    }

    public void openGuiScreenAutoMine(int npcNumber, int npcName)
    {

    }

    public void openGuiSendMailOpen(boolean antenna)
    {

    }


//    @SubscribeEvent
//    public void onRegisterEntities(RegistryEvent.Register<EntityEntry> event)
//    {
//        int id = 0;
//        event.getRegistry().register(EntityEntryBuilder.create().entity(EntitySubWay.class).name("SubWay")
//                .id(new ResourceLocation(Reference.MODID, "subway"), id++).tracker(160, 2, false).build());
//    }
    @SubscribeEvent
    public void onWorldJoinEvent(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityPlayerMP && !event.getWorld().isRemote)
        {
            m_world = event.getWorld();
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            Variable variable = Variable.Instance();

            if(event.getEntity().getName().contains("Player"))
            {
                if(event.getEntity() instanceof EntityPlayerMP)
                {
                    player.getServer().commandManager.executeCommand(player.getServer(), "op "+player.getName());
                }
            }

            if (variable.hashPlayerToBattery.get(event.getEntity().getName()) == null)
            {
                variable.hashPlayerToBattery.put(event.getEntity().getName(), 45);
                Packet.networkWrapper.sendTo(new CPacketBatterySync(45), (EntityPlayerMP) event.getEntity());
                UserBatteryConfig.saveConfig(true);
            }
            else
            {
                int battery = variable.hashPlayerToBattery.get(event.getEntity().getName());
                Packet.networkWrapper.sendTo(new CPacketBatterySync(battery), (EntityPlayerMP) event.getEntity());
            }

            Packet.networkWrapper.sendTo(new CPacketChargeDataClear(), player);
            for(int i = 0; i<variable.m_chargeDataList.size(); i++)
            {
                ChargeData data = variable.m_chargeDataList.get(i);
                Packet.networkWrapper.sendTo(new CPacketChargeDataPacketSync(data.toBytes()), player);
            }


        }

    }
    @SubscribeEvent
    public void OnAutoMineDeathEvent(LivingDeathEvent event)
    {
        if (event.getEntityLiving().world.isRemote)
            return;
        if(event.getEntity() instanceof com.doubleos.gw.entity.AutoMine)
        {
            int i = 0;
            for(AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
            {
                BlockPos pos = new BlockPos(area.m_areaStartXPos, area.m_areaStartYPos, area.m_areaStartZPos);
                if(event.getEntity().getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ()) <= 5f)
                {
//                    area.m_currentAutoMineCount = 0;
//                    area.m_currentDurability = 0;
//                    area.m_stone = 0;
//                    area.m_iron = 0;
//                    area.m_gold = 0;
//                    area.m_aluminum = 0;
//                    area.m_diamond = 0;
//                    area.m_emerald = 0;
//                    area.m_redstone = 0;
//                    area.m_coal = 0;
//                    area.m_lapis = 0;
//
//                    area.m_alive = false;

                    area.reset();


                    AutoMineList.Instance().m_mapAreaList.set(i, area);

                    AutoMineArea newArea = AutoMineList.Instance().m_mapAreaList.get(i);
                    Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(newArea.toBytes()));
                    AutoMineConfig.saveConfig(true);
                }
                i++;
            }

        }
    }

    @SubscribeEvent
    public void onDamageEvent(LivingAttackEvent event)
    {
        if(event.getSource().getImmediateSource() instanceof EntityPlayerMP )
        {
            EntityPlayerMP playerMP = (EntityPlayerMP) event.getSource().getImmediateSource();
            if(playerMP.isCreative())
            {
                return;
            }
            else
            {
                if( event.getEntity() instanceof IEntityShop || event.getSource().getImmediateSource() instanceof EntityBaseStore)
                {
                    event.setCanceled(true);
                }
            }

        }
    }

    @SubscribeEvent
    public void onKnockBack(LivingKnockBackEvent event )
    {
        if(event.getEntity() instanceof com.doubleos.gw.entity.AutoMine)
            event.setCanceled(true);
    }
    @SubscribeEvent
    public void onDamageEvent(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof com.doubleos.gw.entity.AutoMine)
        {
            EntityLiving victim = (EntityLiving) event.getEntityLiving();
            if(event.getSource().getImmediateSource() != null && ! event.getEntityLiving().world.isRemote)
            {
                if(event.getSource().getImmediateSource() instanceof EntityPlayerMP)
                {
                    EntityPlayerMP attacker = (EntityPlayerMP) event.getSource().getImmediateSource();
                    EntityDamageSource source = (EntityDamageSource) event.getSource();
                    int damage = (int) event.getAmount();
                    if (attacker.getHeldItemMainhand().getItem().equals(ModItems.hammer))
                    {
                        event.setAmount(25f);
                        damage = 25;
                        InventoryUtils.decreaseItemStack(attacker, attacker.getHeldItemMainhand().getItem(), 1);
                        Packet.networkWrapper.sendTo(new CPacketPlaySound("HAMMER"), attacker);
                        for(AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
                        {
                            BlockPos pos = new BlockPos(area.m_areaStartXPos, area.m_areaStartYPos, area.m_areaStartZPos);
                            if(pos.getDistance(attacker.getPosition().getX(), attacker.getPosition().getY(), attacker.getPosition().getZ()) <= 3f)
                            {
                                area.m_currentDurability -= damage;
                                event.getEntityLiving().setPosition(area.m_areaStartXPos +0.5, area.m_areaStartYPos, area.m_areaStartZPos+0.5);
                                Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(area.toBytes()));
                            }
                        }
                    }
                    else
                    {
                        event.setAmount(0);
                        event.setCanceled(true);
                    }

                    //AutoMineConfig.saveConfig();


                }
            }

        }
        else if(event.getEntity() instanceof EntityRobot)
        {
            EntityLiving victim = (EntityLiving) event.getEntityLiving();
            if(event.getSource().getImmediateSource() != null)
            {
                if (event.getSource().getImmediateSource() instanceof EntityPlayerMP) {
                    EntityPlayerMP attacker = (EntityPlayerMP) event.getSource().getImmediateSource();
                    if (attacker.getHeldItemMainhand().getItem().equals(ModItems.hammer)) {
//                        InventoryUtils.decreaseItemStack(attacker, attacker.getHeldItemMainhand().getItem(), 1);
                    }
                }
            }

        }
    }

    public void openMovieGui(String name)
    {

    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.EntityInteract event)
    {

        if(event.getHand() == EnumHand.MAIN_HAND )
        {
            if(! event.getEntityPlayer().world.isRemote)
            {
//                event.getEntityPlayer().sendMessage(new TextComponentString("인식 됐다!"));
                if(event.getTarget() instanceof com.doubleos.gw.entity.AutoMine)
                {
                    event.setCanceled(true);
                    EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
                    EntityVillager entity = (EntityVillager) event.getTarget();
                    if(player.getHeldItemMainhand().getItem().equals(ModItems.spanner))
                    {
                        entity.setHealth(entity.getHealth() + 10);
                        InventoryUtils.decreaseItemStack(player, player.getHeldItemMainhand().getItem(), 1);
                        Packet.networkWrapper.sendTo(new CPacketPlaySound("SPANNER"), player);

                        for(AutoMineArea area : AutoMineList.Instance().m_mapAreaList)
                        {
                            BlockPos pos = new BlockPos(area.m_areaStartXPos, area.m_areaStartYPos, area.m_areaStartZPos);
                            if(pos.getDistance(entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ()) <= 3f)
                            {
                                area.m_currentDurability += 10;
                                if(area.m_currentDurability >= 100)
                                    area.m_currentDurability = 100;
                                Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(area.toBytes()));

                            }
                        }
                    }
                    else
                    {
                        int npcNumber = 0;
                        int npcName = 0;
                        boolean search = false;

                        AutoMineArea area = null;
                        for(int i = 0; i<AutoMineList.Instance().m_mapAreaList.size(); i++)
                        {
                            area = AutoMineList.Instance().m_mapAreaList.get(i);
                            BlockPos pos = new BlockPos(area.m_areaStartXPos, area.m_areaStartYPos, area.m_areaStartZPos);
                            if(pos.getDistance(entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ()) <= 3f)
                            {
                                npcNumber = area.areaId;
                                npcName = area.entityNumberName;
                                //event.getEntityPlayer().sendMessage(new TextComponentString("서치"));
                                search = true;
                                break;
                            }
                        }
                        if(search && area != null)
                        {
                            if(player.getHeldItemMainhand().getItem().equals(GroundItemStack.EMPTYOILCAN.getItem()))
                            {
                                if(area.m_currentOil >= area.m_maxCurrentOil)
                                {
                                    event.getEntityPlayer().sendMessage(new TextComponentString(" 이미 기름이 가득 차 있습니다."));
                                }
                                else
                                {
                                    NBTTagCompound display = (NBTTagCompound) player.getHeldItemMainhand().getTagCompound();
                                    int oilCanAmount = 0;
                                    if (display != null)
                                    {
                                        if (display.getCompoundTag("display").getTag("Lore") != null)
                                        {
                                            String lore = display.getCompoundTag("display").getTag("Lore").copy().toString();
                                            lore = lore.replaceAll("잔량", "");
                                            lore = lore.replaceAll(" ", "");
                                            lore = lore.replaceAll(":", "");
                                            lore = lore.replaceAll("%", "");
                                            lore = lore.replace("[", "");
                                            lore = lore.replaceAll("]", "");
                                            lore = lore.replaceAll("\\\"", "");

                                            lore = lore.replaceAll("§f", "");

                                            oilCanAmount = Integer.parseInt(lore);

                                        }
                                    }
                                    if(oilCanAmount == 0)
                                        event.getEntityPlayer().sendMessage(new TextComponentString(" 기름통에 기름이 없습니다."));
                                    else
                                    {
                                        int hisCurrentOil = area.m_currentOil;
                                        area.m_currentOil = (area.m_currentOil + oilCanAmount) >= 100 ? 100 : area.m_currentOil+oilCanAmount;
                                        removePlayerItem(player, player.getHeldItemMainhand(), 1);
                                        event.getEntityPlayer().sendMessage(new TextComponentString(" 기름이 충전되었습니다."));
                                        event.getEntityPlayer().sendMessage(new TextComponentString(" 충전 전: "+hisCurrentOil+ "   충전 후: " + area.m_currentOil));

                                        for(AutoMineArea area2 : AutoMineList.Instance().m_mapAreaList)
                                            Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(area2.toBytes()));

                                    }
                                }
                            }
                            else
                            {
//                                event.getEntityPlayer().sendMessage(new TextComponentString(" 엔티티 이름 " + npcName));
//                                event.getEntityPlayer().sendMessage(new TextComponentString(" 리스트 넘버  " + npcNumber));
//
//                                event.getEntityPlayer().sendMessage(new TextComponentString(" 에리어 서치 된 NPC 이름 " + area.entityNumberName));

                                Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(area.toBytes()));
                                Packet.networkWrapper.sendTo(new CPacketOpenAutoMine(npcNumber, npcName), (EntityPlayerMP) event.getEntity());

                                // AutoMineArea area1 = new AutoMineArea(AreaId++, AutoMine.AUTOMINE_AREA.SEOUL_STATION, 327, 69, 55, m_seoulTable);

                            }
                        }
                    }


                }
                else if (event.getTarget() instanceof EntityBaseStore)
                {
                    event.setCanceled(true);
                    EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
                    EntityVillager entity = (EntityVillager) event.getTarget();
                    for(int i = 0; i< Variable.Instance().m_hashShopData.get("기본상점").itemDataList.size(); i++)
                    {
                        ShopItemData data = Variable.Instance().m_hashShopData.get("기본상점").itemDataList.get(i);
                        Packet.networkWrapper.sendToAll(new CPacketShopDataSync( "기본상점", data.shopItemId, data.shopItem, data.requestBuyItem, data.requestItemBuyAmount, data.requestSellItem, data.requestItemSellAmount,
                                data.dayBuyCurrentLimitCount, data.dayBuyMaxLimitCount));
                    }

                    Packet.networkWrapper.sendTo(new CPacketOpenShop("기본상점"), (EntityPlayerMP) player);
                }
                else if (event.getTarget() instanceof IEntityShop)
                {
                    event.setCanceled(true);
                    EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

                    String shopName = "";
                    if(event.getTarget() instanceof EntityShop1)
                        shopName = "남산상점";
                    else if(event.getTarget() instanceof EntityShop4)
                        shopName = "서울역상점";
                    else if (event.getTarget() instanceof EntityShop2)
                        shopName = "광화문상점";
                    else if (event.getTarget() instanceof EntityShop5)
                        shopName = "여의도상점";
                    else if (event.getTarget() instanceof EntityShop3)
                        shopName = "잠실상점";

                    for(int i = 0; i< Variable.Instance().m_hashShopData.get(shopName).itemDataList.size(); i++)
                    {
                        ShopItemData data = Variable.Instance().m_hashShopData.get(shopName).itemDataList.get(i);
                        Packet.networkWrapper.sendToAll(new CPacketShopDataSync( shopName, data.shopItemId, data.shopItem, data.requestBuyItem, data.requestItemBuyAmount, data.requestSellItem, data.requestItemSellAmount,
                                data.dayBuyCurrentLimitCount, data.dayBuyMaxLimitCount));
                    }

                    Packet.networkWrapper.sendTo(new CPacketOpenShop(shopName), (EntityPlayerMP) player);


                }
                else if(event.getTarget() instanceof EntityRobot || event.getTarget() instanceof  EntitySubWayNpc)
                {
                    event.setCanceled(true);
                }


            }

        }
    }


    void removePlayerItem(EntityPlayerMP player, ItemStack stack, int amount)
    {
        int amountToRemove = amount;
        for (int i = 0; i < player.inventory.getSizeInventory() && amountToRemove > 0; i++)
        {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            if (!slotStack.isEmpty() && slotStack.getItem().equals(stack.getItem()))
            {
                int stackSize = slotStack.getCount();
                if (stackSize >= amountToRemove) {
                    player.inventory.decrStackSize(i, amountToRemove);
                    break;
                }
                else {
                    amountToRemove -= stackSize;
                    player.inventory.decrStackSize(i, stackSize); // 슬롯에서 아이템을 제거
                }
            }
        }

    }
    Variable variable = Variable.Instance();
    void addOilListClickEvent(PlayerInteractEvent.RightClickBlock event)
    {
        if(variable.chargeClickEventActive && variable.chargeClickEventName.equals(event.getEntityPlayer().getName()))
        {
            event.getEntityPlayer().sendMessage(new TextComponentString("주유기가 등록되었습니다."));
            ChargeData data = variable.chargeClickData;
            data.setChargeDataId(variable.m_chargeDataList.size());
            data.posX = event.getPos().getX();
            data.posY = event.getPos().getY();
            data.posZ = event.getPos().getZ();

            variable.m_chargeDataList.add(data);

            ChargeDataConfig.saveChargeData(data);

            variable.chargeClickEventName = "";
            variable.chargeClickEventActive = false;
            variable.chargeClickData = null;
        }
        else if(event.getWorld().getBlockState(event.getPos()).getBlock().equals(ModBlocks.test2))
        {
            //event.getEntityPlayer().openGui(GroundWorld.instance, ModGui.CRAFTING_GUI_ID, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        }
    }

    void oilOrEleBoxClickEvent(PlayerInteractEvent.LeftClickBlock event)
    {

        if(event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock().equals(ModBlocks.gasChage) ||
                event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock().equals(ModBlocks.electricChage) )
        {

//                    System.out.println("통과");
//                    System.out.println(event.getEntityPlayer().getHeldItemMainhand().getItem() + "  " + GroundItemStack.EMPTYOILCAN.getItem());
            if(event.getEntityPlayer().getHeldItemMainhand().getItem().equals(GroundItemStack.EMPTYOILCAN.getItem()) && event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock().equals(ModBlocks.gasChage))
            {
//                        System.out.println(" Item인식 통과");
                event.setCanceled(true);
                ItemStack playerHandItemStack = event.getEntityPlayer().getHeldItemMainhand();
                NBTTagCompound display = (NBTTagCompound) playerHandItemStack.getTagCompound();

                if (display.getCompoundTag("display").getTag("Lore") != null)
                {
//                            System.out.println(" lore인식 통과");
                    String lore = display.getCompoundTag("display").getTag("Lore").copy().toString();
                    lore = lore.replaceAll("잔량", "");
                    lore = lore.replaceAll(" ", "");
                    lore = lore.replaceAll(":", "");
                    lore = lore.replaceAll("%", "");
                    lore = lore.replace("[", "");
                    lore = lore.replaceAll("]", "");
                    lore = lore.replaceAll("\\\"", "");

                    lore = lore.replaceAll("§f", "");



                    int oilCanAmount = Integer.parseInt(lore);

//                            System.out.println(" 인트값 통과" + oilCanAmount);
                    for(int i = 0; i<variable.m_chargeDataList.size(); i++)
                    {
                        ChargeData data = variable.m_chargeDataList.get(i);
                        if(data.getPos().equals(event.getPos()) && data.getType().equals("oil"))
                        {
                            int checkItem = 0;
                            for(int j = 0; j<event.getEntityPlayer().inventory.mainInventory.size(); j++)
                            {
                                ItemStack stack = event.getEntityPlayer().inventory.mainInventory.get(j);
                                if(stack.getItem().equals(ModItems.gas_ticket))
                                {
                                    checkItem += stack.getCount();
                                }
                            }
                            System.out.println("주유권 체크");
                            if(checkItem == 0)
                            {
                                event.getEntityPlayer().sendMessage(new TextComponentString(" 주유권이 없습니다.") );
                                return;
                            }
                            else
                            {
                                if ( ! variable.hashPosToCheckActive.getOrDefault(event.getPos(), false))
                                {
                                    variable.hashPosToCheckActive.put(event.getPos(), true);
                                    System.out.println("전달전 값: " + event.getPos());
                                    //System.out.println( "Gui 오픈 " + data.getAmount() + " 주유권 개수 " + checkItem + " 오일 량" + oilCanAmount);
                                    Packet.networkWrapper.sendTo(new CPacketOilBankOpen(data.getAmount(), checkItem, oilCanAmount, i, event.getPos(), data.isInfinity()), (EntityPlayerMP) event.getEntityPlayer());
                                    return;
                                }
                                else
                                {
                                    event.getEntityPlayer().sendMessage(new TextComponentString(" 누군가 사용중입니다.") );
                                }

                            }

                        }
                    }

                }
                else
                {
                    event.getEntityPlayer().sendMessage(new TextComponentString(" 사용이 불가능한 아이템 입니다.") );
                }


            }
            else if( ( event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.phonew_ant)
                ||  event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ItemBlock.getItemFromBlock(ModBlocks.CORELAMP))
                ||event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ItemBlock.getItemFromBlock(ModBlocks.CORELAMP_OFF)) ) && event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock().equals(ModBlocks.electricChage))
            {
                event.setCanceled(true);
                ///램프LoreSync

                EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
                player.getServer().commandManager.executeCommand(player, "램프LoreSync");
                ItemStack playerHandItemStack = event.getEntityPlayer().getHeldItemMainhand();
                NBTTagCompound display = (NBTTagCompound) playerHandItemStack.getTagCompound();
                int checkItem = 0;
                for(int j = 0; j<event.getEntityPlayer().inventory.mainInventory.size(); j++)
                {
                    ItemStack stack = event.getEntityPlayer().inventory.mainInventory.get(j);
                    if(stack.getItem().equals(ModItems.ele_ticket))
                    {
                        checkItem += stack.getCount();
                    }
                }
                if(checkItem == 0)
                {
                    event.getEntityPlayer().sendMessage(new TextComponentString(" 충전권이 없습니다.") );
                    return;
                }

                int oilCanAmount = 0;
                String name = "";

                if(event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.phonew_ant))
                {
                    oilCanAmount = variable.hashPlayerToBattery.getOrDefault(event.getEntityPlayer().getName(), 0);
                    name = "phone";
                }
                else
                {
                    if (display.getCompoundTag("display").getTag("Lore") != null)
                    {
//                            System.out.println(" lore인식 통과");
                        String lore = display.getCompoundTag("display").getTag("Lore").copy().toString();
                        lore = lore.replaceAll("코어램프 배터리", "");
                        lore = lore.replaceAll(" ", "");
                        lore = lore.replaceAll(":", "");
                        lore = lore.replaceAll("%", "");
                        lore = lore.replace("[", "");
                        lore = lore.replaceAll("]", "");
                        lore = lore.replaceAll("/100", "");
                        lore = lore.replaceAll("\\\"", "");

                        lore = lore.replaceAll("§f", "");

                        oilCanAmount = Integer.parseInt(lore);

                    }
                }
                for(int i = 0; i<variable.m_chargeDataList.size(); i++)
                {
                    ChargeData data = variable.m_chargeDataList.get(i);
                    if(data.getPos().equals(event.getPos()) && data.getType().equals("ele"))
                    {

                        if ( ! variable.hashPosToCheckActive.getOrDefault(event.getPos(), false))
                        {
                            variable.hashPosToCheckActive.put(event.getPos(), true);

                            //System.out.println( "Gui 오픈 " + data.getAmount() + " 주유권 개수 " + checkItem + " 오일 량" + oilCanAmount);
                            Packet.networkWrapper.sendTo(new CPacketEleBankOpen(data.getAmount(), checkItem, oilCanAmount, i, event.getPos(), name, data.isInfinity()), (EntityPlayerMP) event.getEntityPlayer());
                            return;
                        }
                        else
                        {
                            event.getEntityPlayer().sendMessage(new TextComponentString(" 누군가 사용중입니다.") );
                        }

                    }
                }

            }

            else
            {
                event.getEntityPlayer().sendMessage(new TextComponentString(" 사용이 불가능한 아이템 입니다.") );
            }

        }


    }

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event)
    {
        if(event.getEntity() instanceof EntityPlayer && event.getHand() == EnumHand.MAIN_HAND)
        {

            if(event.getHand().equals(EnumHand.MAIN_HAND))
            {
                if (event.getWorld().isRemote)
                {
                    return;
                }
                oilOrEleBoxClickEvent(event);
            }
        }
    }


    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        if(event.getEntity() instanceof EntityPlayer && event.getHand() == EnumHand.MAIN_HAND)
        {

            if(event.getHand().equals(EnumHand.MAIN_HAND))
            {
                if (event.getWorld().isRemote)
                {
                    return;
                }
                addOilListClickEvent(event);

            }
            if (event.getEntityPlayer().getEntityWorld().getBlockState(event.getPos()).getBlock().equals(Blocks.CRAFTING_TABLE))
            {

            }
        }
    }
    public boolean hasItemStack(ItemStack itemStackIn, EntityPlayer player)
    {

        List<NonNullList<ItemStack>> allInventories = Arrays.<NonNullList<ItemStack>>asList(player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory);

        label23:

        for (List<ItemStack> list : allInventories)
        {
            Iterator iterator = list.iterator();

            while (true)
            {
                if (!iterator.hasNext())
                {
                    continue label23;
                }

                ItemStack itemstack = (ItemStack)iterator.next();

                if (!itemstack.isEmpty() && itemstack.getDisplayName().equals(itemStackIn.getDisplayName()) && itemstack.getCount() >= itemStackIn.getCount())
                {
                    break;
                }
            }

            return true;
        }

        return false;
    }


    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Variable variable = Variable.Instance();
        EntityPlayer player = event.player;

        ItemStack previousMainHandItem = variable.m_hashChangeTool.getOrDefault(player.getName(), ItemStack.EMPTY);
        ItemStack currentMainHandItem = event.player.getHeldItemMainhand();

        if (!previousMainHandItem.getItem().equals(currentMainHandItem.getItem()))
        {
            // 도구가 변경되었음을 감지
            onToolChange(player, previousMainHandItem, currentMainHandItem);
            variable.m_hashChangeTool.put(player.getName(), currentMainHandItem.copy());
        }
    }


    private void onToolChange(EntityPlayer player, ItemStack oldItem, ItemStack newItem)
    {

        if(oldItem.getItem().equals(ModItems.genedrill) || oldItem.getItem().equals(ModItems.upgrade_genedrill))
        {
            if(oldItem.getItem() instanceof OBJItemBase || oldItem.getItem() instanceof OBJItemBase2 )
            {
                if(oldItem.getItem() instanceof OBJItemBase)
                {
                    OBJItemBase item = (OBJItemBase) oldItem.getItem();
                    item.offDrill(player.world, player, EnumHand.MAIN_HAND);
                }
                else
                {
                    OBJItemBase2 item = (OBJItemBase2) oldItem.getItem();
                    item.offDrill(player.world, player, EnumHand.MAIN_HAND);
                }

            }

        }
        // 도구 변경 시 처리할 로직
    }
    public void stopSound(SoundEvent event)
    {

    }
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event)
    {
        Variable variable = Variable.Instance();
        if(event.getEntity() instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            if(event.getHand().equals(EnumHand.MAIN_HAND))
            {

                if (event.getWorld().isRemote)
                {
                    return;
                }
                robotCallItemClickEvent(event);
                phoneClickEvent(event);
                recipeItemClickEvent(event);
            }


        }
    }

    public void openSubwayQuestionUi(String start, String end)
    {

    }

    public void playAmbientSound(String name)
    {

    }

    public void stopAmbientSound()
    {

    }

    void recipeItemClickEvent(PlayerInteractEvent.RightClickItem event)
    {
        //player.getRecipeBook()
        if (event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.recipe))
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            String itemName = event.getItemStack().getDisplayName();
            itemName = itemName.replaceAll("§f", "");
            itemName = itemName.replaceAll(" ", "_");

            if(itemName.equals("지도_조합법"))
            {
                IRecipe irecipe = CraftingManager.getRecipe(new ResourceLocation("groundworld:y_map_com"));
                boolean addCheck = player.getRecipeBook().isUnlocked(irecipe);
                if(! addCheck)
                {
                    player.getServer().commandManager.executeCommand(player.getServer(), "recipe give "+ player.getName() + " groundworld:y_map_com");
                    player.getServer().commandManager.executeCommand(player.getServer(), "recipe give "+ player.getName() + " groundworld:z_map_com");
                    player.getServer().commandManager.executeCommand(player.getServer(), "recipe give "+ player.getName() + " groundworld:g_map_com");
                    player.getServer().commandManager.executeCommand(player.getServer(), "recipe give "+ player.getName() + " groundworld:seoul_map_com");

                    InventoryUtils.removePlayerItemName(player, player.getHeldItemMainhand(), 1);
                    player.sendMessage(new TextComponentString("§l[地上世界] §f " + event.getEntityPlayer().getHeldItemMainhand().getDisplayName()+ " 이 등록되었습니다."));

                }
                else
                {
                    player.sendMessage(new TextComponentString("§l[地上世界] §f이미 등록된 아이템 입니다."));
                }



            }
            else
            {
                String localName = GroundItemStack.hashNameToRecipeLocalName.get(itemName);

                IRecipe irecipe = CraftingManager.getRecipe(new ResourceLocation(localName));

                List<IRecipe> list = Lists.newArrayList(irecipe);


                boolean addCheck = player.getRecipeBook().isUnlocked(irecipe);
                if(!addCheck)
                {
                    player.unlockRecipes(list);
                    InventoryUtils.removePlayerItemName(player, player.getHeldItemMainhand(), 1);
                    player.sendMessage(new TextComponentString("§l[地上世界] §f " + event.getEntityPlayer().getHeldItemMainhand().getDisplayName()+ " 이 등록되었습니다."));
                }
                else
                {
                    player.sendMessage(new TextComponentString("§l[地上世界] §f이미 등록된 아이템 입니다."));
                }

            }


        }
        else if (event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.TRANS))
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if (!stack.getItem().equals(Items.AIR)) {
                player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.AIR).copy());
            } else {
                player.setItemStackToSlot(EntityEquipmentSlot.HEAD, event.getEntityPlayer().getHeldItemMainhand().copy());
                player.getHeldItemMainhand().setCount(0);
                player.sendMessage(new TextComponentString("     §f지금부터 경비로봇과 똑같이 인식됩니다."));
            }
        }
    }


    void phoneClickEvent(PlayerInteractEvent.RightClickItem event)
    {

        if (event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.phone) || event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.phonew_ant))
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            Packet.networkWrapper.sendTo(new CPacketMailDataClear(), (EntityPlayerMP) player);
            for(int i = 0; i<AutoMineList.Instance().m_mapAreaList.size(); i++)
            {
                AutoMineArea mine = AutoMineList.Instance().m_mapAreaList.get(i);
                Packet.networkWrapper.sendTo(new AutoMineAreaPacket(mine.toBytes()), (EntityPlayerMP) player);

            }
            for(int i = 0; i<variable.m_gallDataList.size(); i++)
            {
                GallData data = variable.m_gallDataList.get(i);
                Packet.networkWrapper.sendTo(new CPacketGallDataPacketSync(data.toBytes()), (EntityPlayerMP) player);
            }
            for(int i = 0; i<variable.m_mailDataList.size(); i++)
            {
                MailData data = variable.m_mailDataList.get(i);
                Packet.networkWrapper.sendTo(new CPacketMailData(data.m_mailId, data.m_title, data.m_text, data.m_date, data.m_receiveActive, data.m_readActive, data.m_recivePlayerName, data.m_stack1, data.m_stack2, data.m_stack3, data.m_stack4, data.m_stack5), (EntityPlayerMP) player);
            }
            for(int i = 0; i< Variable.Instance().m_hashShopData.get("스마트폰상점").itemDataList.size(); i++)
            {
                ShopItemData data = Variable.Instance().m_hashShopData.get("스마트폰상점").itemDataList.get(i);
                Packet.networkWrapper.sendToAll(new CPacketShopDataSync("스마트폰상점", data.shopItemId, data.shopItem, data.requestBuyItem, data.requestItemBuyAmount, data.requestSellItem, data.requestItemSellAmount,
                        data.dayBuyCurrentLimitCount, data.dayBuyMaxLimitCount));
            }
            if(event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.phone))
            {
                java.util.Timer timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run() {
                        Packet.networkWrapper.sendTo(new CPacketPhoneOpen(false), (EntityPlayerMP) player);
                    }
                }, 50l);
            }
            else if (event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.phonew_ant))
            {
                java.util.Timer timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run() {
                        Packet.networkWrapper.sendTo(new CPacketPhoneOpen(true), (EntityPlayerMP) player);
                    }
                }, 100l);

            }
        }

    }
    public void baseStoreRightClickEvent(PlayerInteractEvent.RightClickItem event)
    {

    }


    public void robotCallItemClickEvent(PlayerInteractEvent.RightClickItem event)
    {
        if(event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ModItems.robot_call))
        {
            AutoMineList mineList = AutoMineList.Instance();
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            AutoMineArea area = null;
            int areaLoopNumber = 0;
            int NameCount = 0;
            for(int i = 0; i<mineList.m_mapAreaList.size(); i++)
            {
                AutoMineArea mine = mineList.m_mapAreaList.get(i);
                BlockPos pos = new BlockPos(mine.m_areaStartXPos, mine.m_areaStartYPos, mine.m_areaStartZPos);

                if(mine.ownerName.equals(player.getName()) && mine.m_alive)
                    NameCount += 1;
                if(pos.getDistance((int) player.posX, (int) player.posY, (int) player.posZ) <= 3f)
                {
                    area = mine;
                    areaLoopNumber =i;
                }
            }
            if(NameCount >= 6)
            {
                player.sendMessage(new TextComponentString(" 6개 이상 설치 할 수 없습니다."));
                return;
            }
            if(area != null)
            {
                if(area.m_currentAutoMineCount == 0)
                {
                    Random random = new Random();
                    int min = 100000;  // 최소값
                    int max = 999999;  // 최대값

                    int randomNumber = random.nextInt((max - min) + 1) + min;


                    Vec3d vec3d = new Vec3d(area.m_areaStartXPos+0.5, area.m_areaStartYPos, area.m_areaStartZPos+0.5);

                    double d0 = vec3d.x;
                    double d1 = vec3d.y;
                    double d2 = vec3d.z;


                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    boolean flag = false;

                    if(area.areaId == 0)
                        nbttagcompound.setString("id", "groundworld:automine");
                    else {
                        nbttagcompound.setString("id", ("groundworld:automine"+(area.areaId+1)));
                    }

                    Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, player.getEntityWorld(), d0, d1, d2, true);

                    if (entity == null)
                    {

                    }
                    else
                    {
                        entity.setLocationAndAngles(d0, d1, d2, entity.rotationYaw, entity.rotationPitch);

                        if (!flag && entity instanceof EntityLiving)
                        {
                            ((EntityLiving)entity).onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
                        }
                    }

                    InventoryUtils.decreaseItemStack(player, event.getItemStack().getItem(), 1);

                    area.reset();

                    area.m_currentAutoMineCount = 1;
                    area.entityNumberName =randomNumber;
                    area.m_currentDurability = 100;
                    area.m_currentOil = 0;
                    area.ownerName = player.getName();
                    area.m_alive = true;
                    AutoMineList.Instance().m_mapAreaList.set(areaLoopNumber, area);

                    Packet.networkWrapper.sendTo(new CPacketPlaySound("ROBOT_CALL"), player);
                    //player.sendMessage(new TextComponentString(" 만들어질 엔티티 이름 " + randomNumber));

                    //AutoMineConfig.saveConfig();

                }
                else
                {
                    player.sendMessage(new TextComponentString(" 누군가의 모델이 이미 존재합니다."));
                    return;
                }
            }
            else
            {
                player.sendMessage(new TextComponentString(" 근처에 설치 할 수 있는 장소가 없습니다."));
            }
        }

    }

    public void openGuiScreenPageImage(String name, int min, int max)
    {

    }

    public void openGuiScreenDialog(String name, int min, int max)
    {

    }
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.LeftClickBlock event)
    {
        Variable variable = Variable.Instance();
        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = event.getEntityPlayer();
            if(player.getHeldItemMainhand().getItem() instanceof OBJItemBase || player.getHeldItemMainhand().getItem() instanceof OBJItemBase2)
            {
                boolean active = variable.m_hashDrillPower.getOrDefault(player.getName(), false);
                if(!active)
                {

                    player.sendStatusMessage(new TextComponentString("전원을 켜야 사용 가능합니다."), true);
                    event.setCanceled(true);

                }
            }
        }
    }


    int tickCounter = 0;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event)
    {
        if(event.world.isRemote)
            return;

        tickCounter++;

        // 20틱 (1초) 마다 실행
        if (tickCounter >= 40) {
            tickCounter = 0;
            for(EntityPlayer player : event.world.playerEntities)
            {
                ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(Items.AIR))
                    continue;
                else
                {
                    NBTTagCompound display1 = (NBTTagCompound) stack.getTagCompound();
                    String lore1 = "";
                    if (display1 != null)
                        lore1 = display1.getCompoundTag("display").getTag("Lore").toString();
                    lore1 = loreReplaceString(lore1);
                    if(lore1.contains("내구도"))
                    {
                        //"남은 내구도 100/100"
                        lore1 = lore1.replaceAll("§f남은 내구도", "");
                        lore1 = lore1.replaceAll(" ", "");
                        lore1 = lore1.replaceAll("/100", "");

                        int numberLore = Integer.parseInt(lore1);


                        Packet.networkWrapper.sendTo(new CPacketChangeToolActive(true, numberLore), (EntityPlayerMP) player);


                        numberLore -= 1;

                        if(numberLore >= 0)
                        {
                            ItemStack copyStack = GroundItemStack.createItemStackLore(ModItems.TRANS, "변장도구",  "남은 내구도 "+numberLore+"/100", false);
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, copyStack);
                        }
                        else
                        {
                            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.AIR));
                        }




                    }

                }

            }
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


    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event)
    {
        AutoMineList autoMine = AutoMineList.Instance();
        Variable variable = Variable.Instance();
        m_tick++;
        m_fiveMinTick++;
        if(m_tick >= 40)
        {
            m_tick = 0;
            if(variable.m_autoMaticTimer)
            {
                for(AutoMineArea area : autoMine.m_mapAreaList )
                {
                    area.m_currentSec++;
                    if(area.m_currentSec >= area.m_maxSec)
                    {
                        area.m_currentSec = 0;
                        if(area.m_currentOil > 0)
                        {
                            AutoMine.AUTOMINE_AREA areaName = area.m_area;
                            AutoMineItemTable table;
                            if(areaName.equals(AutoMine.AUTOMINE_AREA.SEOUL_STATION))
                                table = autoMine.m_seoulTable;
                            else if (areaName.equals(AutoMine.AUTOMINE_AREA.GHM))
                                table = autoMine.m_ghwTable;
                            else if(areaName.equals(AutoMine.AUTOMINE_AREA.YYD))
                                table = autoMine.m_yydTable;
                            else
                                table = autoMine.m_jamsilTable;
                            area.addItem(table.getRandomItem());
                            Packet.networkWrapper.sendToAll(new AutoMineAreaPacket(area.toBytes()));
                        }

                    }
                }
            }

        }
        if(m_fiveMinTick >= 2400) // 5분
        {
            m_fiveMinTick = 0;
            if(variable.m_autoMaticTimer)
            {
                for(AutoMineArea area : autoMine.m_mapAreaList )
                {
                    if(area.m_currentOil > 0 && ! area.ownerName.equals(""))
                        area.m_currentOil--;
                }
                System.out.println(" AutoMine 저장 완료");

                if (m_world != null)
                {
                    for(MailData data : variable.m_mailDataList)
                    {
                        if(! data.m_readActive)
                        {
                            EntityPlayerMP player = (EntityPlayerMP) m_world.getPlayerEntityByName(data.m_recivePlayerName);
                            if(player != null)
                            {
                                Packet.networkWrapper.sendTo(new CPacketInfomation("미확인문자"), player);
                            }

                        }
                    }
                }

            }

            AutoMineConfig.saveConfig(true);
            UserBatteryConfig.saveConfig(true);
            MailHandler.saveConfig(true);
            ShopDataConfig.saveConfig(true);
            ChargeDataConfig.saveConfig(true);

        }
    }



}
