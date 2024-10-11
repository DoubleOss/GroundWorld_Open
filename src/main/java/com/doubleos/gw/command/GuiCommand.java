package com.doubleos.gw.command;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.handler.*;
import com.doubleos.gw.packet.*;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.variable.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GuiCommand extends CommandBase
{

    @Override
    public String getName() {
        return "지상세계";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length >= 1)
        {
            Variable variable = Variable.Instance();
            World serverWorld = ((EntityPlayerMP) sender).world;
            EntityPlayer entPlayer = (EntityPlayer) sender;
            World world = sender.getEntityWorld();

            if(args[0].equals("테스트"))
                entPlayer.openGui(GroundWorld.instance, 2, entPlayer.getEntityWorld(), (int)entPlayer.posX, (int)entPlayer.posY, (int)entPlayer.posZ);
            //지상세계 디스코드 등록 닉네임 디스코드ID
            if(args[0].equals("디스코드"))
            {
                if(args[1].equals("등록"))
                {
                    if(!args[2].equals("") && !args[3].equals(""))
                    {
                        String saveName = args[2] + "-"+args[3];
                        int size = GroundWorld.instance.playerList.size();
                        DiscordConfig.writeConfig("playerlist", "player_name_"+size, saveName);
                        GroundWorld.instance.playerList.add(args[2]);
                        size = GroundWorld.instance.playerList.size();
                        DiscordConfig.writeConfig("discord", "discord_player_list_size", size);

                        PlayerInfo info = new PlayerInfo(args[2], args[3]);
                        GroundWorld.instance.playerInfoMap.put(args[2], info);
                        System.out.println( " 등록되었습니다.");
                    }
                }
                else if(args[1].equals("이동"))
                {
                    //지상세계 디스코드 이동 방이름
                    EntityPlayerMP player = (EntityPlayerMP)sender;

                    PlayerInfo info = GroundWorld.instance.playerInfoMap.get(player.getName());
                    if(info == null) {
                        player.sendMessage( new TextComponentString(Color.BLUE + "[디스코드]" + Color.WHITE + " 서버에 등록되어 있지 않아 통화방에 연결할 수 없습니다."));
                    }

                }
            }
            //지상세계 퀘스트북 페이지명 이미지명 완료여부 보일여부
            else if(args[0].equals("퀘스트북"))
            {
                int page = Integer.parseInt(args[1]);
                String imageName = args[2];
                boolean success = Boolean.parseBoolean(args[3]);
                boolean view = Boolean.parseBoolean(args[4]);
            }
            else if(args[0].equals("스마트폰"))
            {
                Boolean active = Boolean.parseBoolean(args[1]);
                Packet.networkWrapper.sendTo(new CPacketMailDataClear(), (EntityPlayerMP) sender);
                for(int i = 0; i<AutoMineList.Instance().m_mapAreaList.size(); i++)
                {
                    AutoMineArea mine = AutoMineList.Instance().m_mapAreaList.get(i);
                    Packet.networkWrapper.sendTo(new AutoMineAreaPacket(mine.toBytes()), (EntityPlayerMP) sender);

                }
                for(int i = 0; i< Variable.Instance().m_hashShopData.get("스마트폰상점").itemDataList.size(); i++)
                {
                    ShopItemData data = Variable.Instance().m_hashShopData.get("스마트폰상점").itemDataList.get(i);
                    Packet.networkWrapper.sendToAll(new CPacketShopDataSync("스마트폰상점", data.shopItemId, data.shopItem, data.requestBuyItem, data.requestItemBuyAmount, data.requestSellItem, data.requestItemSellAmount,
                            data.dayBuyCurrentLimitCount, data.dayBuyMaxLimitCount));
                }

                for(int i = 0; i<variable.m_gallDataList.size(); i++)
                {
                    GallData data = variable.m_gallDataList.get(i);
                    Packet.networkWrapper.sendTo(new CPacketGallDataPacketSync(data.toBytes()), (EntityPlayerMP) sender);
                }
                for(int i = 0; i<variable.m_mailDataList.size(); i++)
                {
                    MailData data = variable.m_mailDataList.get(i);
                    Packet.networkWrapper.sendTo(new CPacketMailData(data.m_mailId, data.m_title, data.m_text, data.m_date, data.m_receiveActive, data.m_readActive, data.m_recivePlayerName, data.m_stack1, data.m_stack2, data.m_stack3, data.m_stack4, data.m_stack5), (EntityPlayerMP) sender);
                }
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Packet.networkWrapper.sendTo(new CPacketPhoneOpen(active), (EntityPlayerMP) sender);
                    }
                }, 200l);


            }
            else if(args[0].equals("Gui상태설정"))
            {
                Variable.PHONE_GUI_VIEW_STATUS status = Variable.PHONE_GUI_VIEW_STATUS.valueOf(args [1]);

                Packet.networkWrapper.sendTo(new CPacketPhoneGuiViewStatus(status), (EntityPlayerMP) sender);
            }
            else if (args[0].equalsIgnoreCase("배경음"))
            {
                if(args.length == 2)
                    Packet.networkWrapper.sendTo(new CPacketAmbientSound(args[1]), (EntityPlayerMP) entPlayer);
                else
                {
                    EntityPlayerMP playerMP = (EntityPlayerMP) server.getEntityWorld().getPlayerEntityByName(args[2]);
                    Packet.networkWrapper.sendTo(new CPacketAmbientSound(args[1]), (EntityPlayerMP) playerMP);
                }
            }
            else if (args[0].equalsIgnoreCase("효과음"))
            {
                //escape 효과음 재생명 플레이어
                if(args.length == 2)
                    Packet.networkWrapper.sendTo(new CPacketPlaySound(args[1]), (EntityPlayerMP) entPlayer);
                else
                {
                    EntityPlayerMP playerMP = (EntityPlayerMP) server.getEntityWorld().getPlayerEntityByName(args[2]);
                    Packet.networkWrapper.sendTo(new CPacketPlaySound(args[1]), (EntityPlayerMP) playerMP);
                }

            }

            else if (args[0].equalsIgnoreCase("전체효과음"))
            {
                //escape 전체효과음 재생명

                Packet.networkWrapper.sendToAll(new CPacketPlaySound(args[1]));


            }
            else if(args[0].equals("전화상태설정"))
            {
                Variable.WATCH_STATUS status = Variable.WATCH_STATUS.valueOf(args[1]);
                Packet.networkWrapper.sendTo(new CPacketPhoneStatus(status), (EntityPlayerMP) sender);
            }
            else if(args[0].equals("전화플레이어"))
            {
                Variable.PHONE_CALLING_PLAYER status = Variable.PHONE_CALLING_PLAYER.valueOf(args[1]);
                Packet.networkWrapper.sendTo(new CPacketCallPlayer(status), (EntityPlayerMP) sender);
            }
            else if(args[0].equals("룰지"))
            {
                //지상세계 룰지

                // 경로 텍스쳐 gui rule 파일명_0 .png "textures/gui/rule/"+name+"_"+currnet+".png"));
                int min = Integer.parseInt(args[2]);
                int max = Integer.parseInt(args[3]);
                Packet.networkWrapper.sendTo(new CPacketOpenPage(args[1], min, max), (EntityPlayerMP) sender);
            }
            else if(args[0].equals("다이얼로그"))
            {
                //지상세계 다이얼로그

                // 경로 텍스쳐 gui rule 파일명_0 .png "textures/gui/rule/"+name+"_"+currnet+".png"));
                int min = Integer.parseInt(args[2]);
                int max = Integer.parseInt(args[3]);
                Packet.networkWrapper.sendTo(new CPacketNpcDialog(args[1], min, max), (EntityPlayerMP) sender);
            }
            else if(args[0].equals("메일"))
            {

                entPlayer.openGui(GroundWorld.instance, 1,  entPlayer.world, entPlayer.getPosition().getX(), entPlayer.getPosition().getY(), entPlayer.getPosition().getZ());
            }
            else if(args[0].equals("타이머시작"))
            {
                boolean active = Boolean.parseBoolean(args[1]);
                for(EntityPlayerMP playerMP : server.getPlayerList().getPlayers())
                {
                    Packet.networkWrapper.sendTo(new CPacketTImerStart(active), playerMP);
                }

            }
            else if(args[0].equals("타이머"))
            {
                //지상세계 타이머 분 초
                int min = Integer.parseInt(args[1]);
                int sec = Integer.parseInt(args[2]);

                Packet.networkWrapper.sendToAll(new CPacketTimerSync(min, sec));
            }
            else if(args[0].equals("배터리"))
            {
                //지상세계 배터리 수치 닉네임
                int per = Integer.parseInt(args[1]);
                EntityPlayerMP playerMp = server.getPlayerList().getPlayerByUsername(args[2]);
                Packet.networkWrapper.sendTo(new CPacketBatterySync(per), playerMp);
                variable.hashPlayerToBattery.put(playerMp.getName(), per);

            }
            else if(args[0].equals("추위수분싱크"))
            {
                //지상세계 추위수분싱크 추위 수분
                float water = Float.parseFloat(args[1]);
                float cold = Float.parseFloat(args[2]);

                Packet.networkWrapper.sendTo(new CPacketColdWaterSync(cold, water), (EntityPlayerMP) sender);
            }
            else if(args[0].equals("갤러리해금"))
            {
                //지상세계 갤러리해금 active
                int number = Integer.parseInt((args[1]));
                boolean active = Boolean.parseBoolean(args[2]);

                //Packet.networkWrapper.sendToAll(new CPacketClueSync(number, active));
                Packet.networkWrapper.sendToAll(new CPacketInfomation("해금문자"));

                for(int i = 0; i < variable.m_gallDataList.size(); i++)
                {
                    if( number == variable.m_gallDataList.get(i).m_dataNumber )
                    {
                        variable.m_gallDataList.get(i).m_lock = active;
                        ClueHandler.writeConfig("clue_data", "clue_data_"+i, String.valueOf(active));
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("페이드연출"))
            {

                if(!args[1].isEmpty() && !args[2].isEmpty() && !args[3].isEmpty() && !args[4].isEmpty() && !args[5].isEmpty())
                {
                    int aniTime = Integer.parseInt(args[1]);
                    int aniDelay = Integer.parseInt(args[2]);
                    float x = Float.parseFloat(args[3]);
                    float y = Float.parseFloat(args[4]);
                    float z = Float.parseFloat(args[5]);


                    Packet.networkWrapper.sendTo(new CPacketFadeEffectAdd(aniTime, aniDelay, x, y, z), (EntityPlayerMP) sender);
                }

            }
            else if(args[0].equals("부족구역"))
            {
                //지상세계 부족구역 현재위치
                //지상세계 부족구역 설치
                //지상세계 부족구역 근처삭제 범위(칸)
                AutoMineList mineList = AutoMineList.Instance();
                BlockPos playerPos = sender.getPosition();
                if(args[1].equals("현재위치"))
                {
                    for(AutoMineArea mine : mineList.m_mapAreaList)
                    {
                        BlockPos pos = new BlockPos(mine.m_areaStartXPos, mine.m_areaStartYPos, mine.m_areaStartZPos);

                        if(pos.getDistance(playerPos.getX(), playerPos.getY(), playerPos.getZ()) <= 3f)
                        {
                            sender.sendMessage(new TextComponentString(" 에리어 위치 " + mine.m_area.name()));
                            sender.sendMessage(new TextComponentString(" 에리어 현재 설치 로봇 개수 " + mine.m_currentAutoMineCount + "  /  " + mine.m_maxAutoMineCount));
                            System.out.println(" 여기 에리어 안에 있다!!");
                            System.out.println(" 에리어 위치 " + mine.m_area.name());
                            System.out.println(" 에리어 현재 설치 로봇 개수 " + mine.m_currentAutoMineCount + "  /  " + mine.m_maxAutoMineCount);

                        }
                    }
                }
                else if(args[1].equals("근처삭제"))
                {
                    for(int i = 0; i < serverWorld.loadedEntityList.size(); i++)
                    {
                        Entity entity = serverWorld.loadedEntityList.get(i);
//                        if(entity.getDistance((Entity) sender) <= 5)
//                        {
//                            System.out.println("감지");
//                            System.out.println(entity.serializeNBT().getString("id"));
//                        }
                        if(entity instanceof EntityGeoAutoMine)
                        {

                            System.out.println(entity.getDistance((Entity) sender));
                            if(entity.getDistance((Entity) sender) <= 5)
                            {
                                System.out.println("여기옴");
                                serverWorld.removeEntity(entity);

                            }

                        }

                    }
                    AutoMineArea area = null;
                    for(AutoMineArea mine : mineList.m_mapAreaList)
                    {
                        BlockPos pos = new BlockPos(mine.m_areaStartXPos, mine.m_areaStartYPos, mine.m_areaStartZPos);

                        if(pos.getDistance(playerPos.getX(), playerPos.getY(), playerPos.getZ()) <= 5f)
                        {
                            area = mine;
                        }
                    }
                    if( area != null)
                    {
                        area.m_currentAutoMineCount = 0;
                        area.entityNumberName = 0;
                        area.ownerName = "";

                        AutoMineConfig.saveConfig(true);

                    }


                }
                else if(args[1].equals("설치"))
                {


                    EntityPlayerMP player = (EntityPlayerMP) sender;
                    AutoMineArea area = null;
                    int NameCount = 0;
                    for(AutoMineArea mine : mineList.m_mapAreaList)
                    {
                        BlockPos pos = new BlockPos(mine.m_areaStartXPos, mine.m_areaStartYPos, mine.m_areaStartZPos);

                        if(mine.ownerName.equals(player.getName()))
                            NameCount += 1;
                        if(pos.getDistance((int) player.posX, (int) player.posY, (int) player.posZ) <= 3f)
                        {
                            area = mine;
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

                            //InventoryUtils.decreaseItemStack(player, event.getItemStack().getItem(), 1);


                            area.m_currentAutoMineCount = 1;
                            area.entityNumberName =randomNumber;
                            area.m_currentDurability = 100;
                            area.m_currentOil = 0;
                            area.ownerName = player.getName();
                            AutoMineList.Instance().m_mapAreaList.set(area.areaId, area);
                            //player.sendMessage(new TextComponentString(" 만들어질 엔티티 이름 " + randomNumber));

                            AutoMineConfig.saveConfig(true);

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



                //지상세계 부족구역 현재구역

            }
            else if(args[0].equals("오토매틱"))
            {
                //지상세계 오토매틱 true/false
                boolean active = Boolean.parseBoolean(args[1]);
                variable.m_autoMaticTimer = active;
                sender.sendMessage(new TextComponentString(" 오토매틱 이 " + active + " 변경되었습니다."));
            }
            else if(args[0].equals("주유소"))
            {
                //지상세계 주유소 주유소주유량[풀 100기준] 주유권개수 들고있는 기름말통값
                float oilAmount = Float.parseFloat(args[1]);
                int oilCount = Integer.parseInt(args[2]);
                int playerOilCount = Integer.parseInt(args[3]);
                int oilListCount = Integer.parseInt(args[4]);

                Packet.networkWrapper.sendTo(new CPacketOilBankOpen(oilAmount, oilCount, playerOilCount, oilListCount, BlockPos.ORIGIN, false), (EntityPlayerMP) entPlayer);

            }
            else if(args[0].equals("말통지급"))
            {
                ((EntityPlayer) sender).inventory.addItemStackToInventory(GroundItemStack.EMPTYOILCAN.copy());
                sender.sendMessage(new TextComponentString(" 기름통이 지급되었습니다."));
            }
            else if(args[0].equals("주유기등록"))
            {
                //지상세계 주유기설치 주유소주유량[풀 100기준]
                //지상세계 주유기등록 이름 지역명 타입 양 무한여부
                int amount = Integer.parseInt(args[4]);
                boolean infinity = Boolean.parseBoolean(args[5]);

                variable.chargeClickEventActive = true;
                variable.chargeClickEventName = sender.getName();
                variable.chargeClickData = new ChargeData(999, args[1], args[2], args[3], amount, infinity);
                
                sender.sendMessage(new TextComponentString("등록하실 주유기에 우클릭 해주세요"));


            }
            else if(args[0].equals("전기충전"))
            {
                //지상세계 전기충전 전기충전량[풀 100기준] 주유권개수 기존충전량 해당리스트번호 pos item
                //(float bankOilCount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, String item)
                float oilAmount = Float.parseFloat(args[1]);
                int oilCount = Integer.parseInt(args[2]);

                //Packet.networkWrapper.sendTo(new CPacketEleBankOpen(oilAmount, oilCount, ), (EntityPlayerMP) entPlayer);

            }
            else if(args[0].equals("부족"))
            {
                //지상세계 부족 npc번호 npc이름
                int npcNumber = Integer.parseInt(args[1]);
                int npcName = Integer.parseInt(args[2]);
                Packet.networkWrapper.sendTo(new CPacketOpenAutoMine(npcNumber, npcName), (EntityPlayerMP) entPlayer);
            }
            else if(args[0].equals("변장도구"))
            {
                //지상세계 변장도구 true/false 손에들고있는 모자템
                boolean active = Boolean.parseBoolean(args[1]);
                int battery = Integer.parseInt(args[2]);
                Packet.networkWrapper.sendTo(new CPacketChangeToolActive(active, battery), (EntityPlayerMP) entPlayer);
            }
            else if(args[0].equals("상점오픈"))
            {
                //지상세계 상점오픈 기본상점/남산상점
                //지상세계 상점오픈 오픈

                for(int i = 0; i< Variable.Instance().m_hashShopData.get(args[1]).itemDataList.size(); i++)
                {
                    ShopItemData data = Variable.Instance().m_hashShopData.get(args[1]).itemDataList.get(i);
                    Packet.networkWrapper.sendToAll(new CPacketShopDataSync(args[1], data.shopItemId, data.shopItem, data.requestBuyItem, data.requestItemBuyAmount, data.requestSellItem, data.requestItemSellAmount,
                            data.dayBuyCurrentLimitCount, data.dayBuyMaxLimitCount));
                }

                Packet.networkWrapper.sendTo(new CPacketOpenShop(args[1]), (EntityPlayerMP) entPlayer);



            }
            else if (args[0].equalsIgnoreCase("지하철소환"))
            {
                Packet.networkWrapper.sendTo(new CPacketSubwaySummon(), (EntityPlayerMP) sender);
                //entity.setVelocity(5, -0.5f, 1);
            }
            else if (args[0].equalsIgnoreCase("지하철소환2")) // 이스트
            {
                Packet.networkWrapper.sendTo(new CPacketSubwaySummon2(), (EntityPlayerMP) sender);
                //entity.setVelocity(5, -0.5f, 1);
            }
            else if (args[0].equalsIgnoreCase("지하철초기화")) // 이스트
            {
                Packet.networkWrapper.sendTo(new CPacketSubwayReset(), (EntityPlayerMP) sender);
                //entity.setVelocity(5, -0.5f, 1);
            }
            else if (args[0].equalsIgnoreCase("지하철소환좌표식")) // 이스트
            {
                //지상세계 지하철소환좌표식 1~2 x y z

                int number = Integer.parseInt(args[1]);
                BlockPos pos = new BlockPos(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));

                Packet.networkWrapper.sendTo(new CPacketSummonSubwayLocation(number, pos.toLong()), (EntityPlayerMP) sender);
                //entity.setVelocity(5, -0.5f, 1);
            }
            else if (args[0].equalsIgnoreCase("시네마연출"))
            {

                if(!args[1].isEmpty() && !args[2].isEmpty())
                {
                    int aniTime = Integer.parseInt(args[1]);
                    int aniDelay = Integer.parseInt(args[2]);

                    Packet.networkWrapper.sendTo(new CCinemaDirect(aniTime, aniDelay), (EntityPlayerMP) sender);
                }

            }
            if(args[0].equals("재생"))
            {
                if (args.length == 2)
                {
                    Packet.networkWrapper.sendTo(new CPacketOpenMovie(args[1]), (EntityPlayerMP) sender);
                }
                if (args.length == 3)
                {
                    EntityPlayerMP player =  server.getPlayerList().getPlayerByUsername(args[2]);
                    Packet.networkWrapper.sendTo(new CPacketOpenMovie(args[1]), (EntityPlayerMP) player);
                }

            }
            else if(args[0].equals("전체재생"))
            {

                Packet.networkWrapper.sendToAll(new CPacketOpenMovie(args[1]));


            }
            else if(args[0].equals("카드리더기재생"))
            {
               Packet.networkWrapper.sendToAll(new CPacketPlaySound("CARDREADER"));
                Packet.networkWrapper.sendToAll(new CPacketCardReaderPlay("cardreader_put"));
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Packet.networkWrapper.sendToAll(new CPacketCardReaderPlay("cardreader_idle"));
                    }
                }, 1000l);
            }
            else if(args[0].equals("레시피지급"))
            {
                for (Map.Entry<String, ItemStack> entry : GroundItemStack.hashRecipeItemToItemStack.entrySet())
                {
                    ((EntityPlayerMP) sender).addItemStackToInventory(entry.getValue().copy());
                }
                    //System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            else if(args[0].equals("일차연출"))
            {

                int aniTime = Integer.parseInt(args[2]);
                int aniDelay = Integer.parseInt(args[3]);
                Packet.networkWrapper.sendToAll(new CPacketPlaySound("DAY_SFX"));
                // 일차시작 | 일차종료
                Packet.networkWrapper.sendToAll(new CPacketDayStart(args[1], aniTime, aniDelay));
            }
            else if(args[0].equals("레시피세팅"))
            {
                EntityPlayerMP playerMP = server.getPlayerList().getPlayerByUsername(args[1]);
                server.commandManager.executeCommand(server, "recipe take "+ playerMP.getName() + " *");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:lapis_block");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:redstone_block");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:iron_block");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:emerald_block");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:iron_chestplate");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:iron_leggings");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:diamond_sword");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " groundworld:phonew_ant");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:stick");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:oak_planks");
                server.commandManager.executeCommand(server, "recipe give "+ playerMP.getName() + " minecraft:diamond_block");


            }

            else if(args[0].equals("닉네임변경"))
            {
                //지상세계 닉네임변경 마크닉 변경할닉
                Packet.networkWrapper.sendToAll(new CPacketChangeNickName(args[1], args[2]));

                variable.m_nameToKor.put(args[1], args[2]);
            }

            else if(args[0].equals("배터리증가"))
            {

                int amount = Integer.parseInt(args[1]);
                //지상세계 베터리증가 수치
                //variable.hashPlayerToBattery
                for (Map.Entry<String, Integer> entry : variable.hashPlayerToBattery.entrySet())
                {
                    String name = entry.getKey();
                    int saveAmount = entry.getValue();
                    saveAmount = (saveAmount + amount) <= 100 ? (saveAmount+amount) : 100;
                    variable.hashPlayerToBattery.put(name, saveAmount);
                }

                sender.sendMessage(new TextComponentString("수치가 변경되었습니다."));

                UserBatteryConfig.saveConfig(true);
            }

            else if(args[0].equals("사용중전체해제"))
            {

                variable.hashPosToCheckActive.clear();
                sender.sendMessage(new TextComponentString("사용중 상태가 모두 해제 되었습니다."));
            }
            else if(args[0].equals("지하철"))
            {
                Packet.networkWrapper.sendTo(new CPacketOpenSubWayUi(args[1], args[2]), (EntityPlayerMP) entPlayer);
            }
            else if(args[0].equals("폭탄사용법지급"))
            {
                //지상세계 폭탄온 애니값 bool
                entPlayer.addItemStackToInventory(GroundItemStack.BOOM_PAPER.copy());
            }
            else if(args[0].equals("폭탄온"))
            {
                //지상세계 폭탄온 애니값 bool
                Boolean active = Boolean.parseBoolean(args[2]);
                variable.boomTick = 0;
                variable.boomIsActive = active;
                Packet.networkWrapper.sendToAll(new CPacketBombOn(args[1], active));
            }
            else if(args[0].equals("상점가격변경"))
            {
                //지상세계 상점가격변경 상점명 물품번호 물품가격
                Integer number = Integer.parseInt(args[2]);
                Integer amount = Integer.parseInt(args[3]);

                ShopData data = variable.m_hashShopData.get(args[1]);
                ArrayList<ShopItemData> list = data.itemDataList;
                list.get(number).requestItemBuyAmount = amount;
                Packet.networkWrapper.sendToAll(new CPacketShopAmountChange(args[1], number, amount));
                sender.sendMessage(new TextComponentString(" " + args[1] + " 의 가격이 "+amount+" 개로 변경 되었습니다."));
            }

        }
    }



    public List<String> getTabCompletions (MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if(args.length == 1)
        {
            String[] commands = new String[]{"페이드연출", "퍼즐", "클릭이벤트", "생성", "효과음", "상호블럭", "정보책", "전체효과음", "시네마연출"};
            return getListOfStringsMatchingLastWord(args, commands);
        }
        else if(args.length >= 2)
        {
            String[] commands = new String[]{ "양띵", "삼식", "콩콩", "다주", "루태", "서넹", "눈꽃", "후추"};
            if(args[0].equals("퍼즐") || args[0].equals("닉네임변경"))
            {
                return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            }
            else if(args[0].equalsIgnoreCase("효과음") && args.length == 2 || args[0].equalsIgnoreCase("전체효과음") && args.length == 2  || args[0].equalsIgnoreCase("배경음") && args.length == 2)
            {
                String[] soundList = GwSoundHandler.m_soundListString.toArray(new String[0]);
                return getListOfStringsMatchingLastWord(args, soundList);
            }
            else if(args[0].equalsIgnoreCase("효과음") && args.length >= 3)
            {
                return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            }
        }

        return Collections.emptyList();
    }
}
