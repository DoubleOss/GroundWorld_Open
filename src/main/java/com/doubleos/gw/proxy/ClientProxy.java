package com.doubleos.gw.proxy;


import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.block.tile.BombTile;
import com.doubleos.gw.block.tile.EmergenTile;
import com.doubleos.gw.block.tile.TileCardReader;
import com.doubleos.gw.block.tile.TileSubwayUnlock;
import com.doubleos.gw.entity.*;
import com.doubleos.gw.geko_render.*;
import com.doubleos.gw.gui.*;
import com.doubleos.gw.gui.GuiMainMenu;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.packet.*;
import com.doubleos.gw.util.*;
import com.doubleos.gw.variable.ChargeData;
import com.doubleos.gw.variable.GroundItemStack;
import com.doubleos.gw.variable.ImageList;
import com.doubleos.gw.variable.Variable;
import com.kuusisto.tinysound.Music;
import com.kuusisto.tinysound.TinySound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandPlaySound;
import net.minecraft.command.server.CommandSummon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.io.FileUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClientProxy extends CommonProxy
{


    Minecraft minecraft = Minecraft.getMinecraft();

    ImageList imagelist;



    public static KeyBinding m_phoneCallAccept;
    public static KeyBinding m_phoneCallRefuse;


    float m_questMiniViewCurentAnimation = 100f;
    boolean m_questMiniViewActive = true;


    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    boolean active = true;

    boolean fristLoadingCheck = false;

    int number = 1;

    int m_min = 0;
    int m_sec = 0;


    float m_timerFlcker = 0;

    ISound m_sfxISound;


    public AnimationState m_currentAreaScoreGuiHud = new AnimationState();


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Variable variable = Variable.Instance();
        if(m_phoneCallAccept.isPressed())
        {
            if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER))
            {
                variable.m_phoneStatus = Variable.WATCH_STATUS.CONNETING;
                Packet.networkWrapper.sendToServer(new SPacketWatchStatus(Variable.WATCH_STATUS.CONNETING, variable.m_callingPlayer.name()));
                stopSound(GwSoundHandler.PHONE_BELL);
                String roomName = Variable.Instance().getMemberIdToKoreaNickName(minecraft.player.getName());
                minecraft.player.sendChatMessage("/통화방이동 " + variable.m_callingPlayer.name()+ " " + Variable.getMemberNameToDiscordNumber(minecraft.player.getName()));
                minecraft.player.sendChatMessage("/통화방이동 " + minecraft.player.getName()+ " " + Variable.getMemberNameToDiscordNumber(minecraft.player.getName()));
                //Packet.networkWrapper.sendToServer(new SPacketDiscordCallRoomMove(roomName, variable.m_callingPlayer.name()));

            }
        }
        else if (m_phoneCallRefuse.isPressed())
        {
            if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING) || variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER))
            {
                String roomName = Variable.Instance().getMemberIdToKoreaNickName(minecraft.player.getName());
                String roomName2 = Variable.Instance().getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
                stopSound(GwSoundHandler.PHONE_BELL);
                if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING))
                {
                    minecraft.player.sendChatMessage("/통화방이동 " + minecraft.player.getName()+ " " + Variable.getMemberNameToDiscordNumber(minecraft.player.getName()));
                    minecraft.player.sendChatMessage("/통화방이동 " + variable.m_callingPlayer.name()+ " " + Variable.getMemberNameToDiscordNumber(variable.m_callingPlayer.name()));
                    GroundWorld.proxy.playSound(GwSoundHandler.CALL_DISCONNET);
                    //Packet.networkWrapper.sendToServer(new SPacketDiscordCallRoomMove(roomName, minecraft.player.getName()));
                    //Packet.networkWrapper.sendToServer(new SPacketDiscordCallRoomMove(roomName2, variable.m_callingPlayer.name()));
                }
                Packet.networkWrapper.sendToServer(new SPacketWatchStatus(Variable.WATCH_STATUS.IDLE, variable.m_callingPlayer.name()));
                variable.m_phoneStatus = Variable.WATCH_STATUS.IDLE;
                variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.NONE;
            }
            if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER))//통화 거절
            {
                Packet.networkWrapper.sendToServer(new SPacketCallRefuse(Variable.WATCH_STATUS.IDLE, variable.m_callingPlayer.name()));
                stopSound(GwSoundHandler.PHONE_BELL);
                GroundWorld.proxy.playSound(GwSoundHandler.CALL_DISCONNET);
                variable.m_phoneStatus = Variable.WATCH_STATUS.IDLE;
                variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.NONE;
            }
        }
        else if(minecraft.gameSettings.keyBindPlayerList.isPressed())
        {
            if(variable.isWatchTimerView)
                variable.isWatchTimerView = false;
            else
                variable.isWatchTimerView = true;
        }
    }



    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Specials.Pre event)
    {

    }

    @Override
    public void onWorldJoinEvent(EntityJoinWorldEvent event)
    {
        if (event.getWorld().isRemote)
        {
            variable.clientPlayerName = minecraft.player.getName();
            minecraft.gameSettings.limitFramerate = 120;
        }


    }
    @Override
    public void openGuiScreenAutoMine(int npcNumber, int npcName)
    {
        minecraft.displayGuiScreen(new GuiAutoMine(npcNumber, npcName));
    }
    @Override
    public void openGuiShop(String name)
    {
        minecraft.displayGuiScreen(new GuiShop(name));
    }
    @Override
    public void openGuiScreen(boolean bool)
    {

    }

    public void openGuiScreenPageImage(String name, int min, int max)
    {
        minecraft.displayGuiScreen(new GuiRulePage(name, min, max));
    }

    public void openGuiScreenDialog(String name, int min, int max)
    {
        minecraft.displayGuiScreen(new GuiDialog(name, min, max));
    }

    String rayCastChargeType = "";
    //RayCast 연산
    @SubscribeEvent
    public void onBlockDraw(DrawBlockHighlightEvent event)
    {
        Variable variable = Variable.Instance();
        if (event.getTarget().typeOfHit.equals(RayTraceResult.Type.BLOCK))
        {
            //검출된 타겟이 Block 일 경우
            BlockPos viewPos = event.getTarget().getBlockPos();
            boolean search = false;
            //주유기, 충전기 리스트에서 해당 좌표값 데이터를 서치
            for(ChargeData data : variable.m_chargeDataList)
            {
                if(viewPos.equals(data.getPos()))
                {
                    search = true;
                    rayCastChargeType = data.getType();
                }
            }
            if(!search)
                rayCastChargeType = "";

        }
        else
        {
            rayCastChargeType = "";
        }

    }

    @SubscribeEvent
    public void guiOpenEvent(GuiScreenEvent event)
    {

    }
    @SubscribeEvent
    public void guiOpenEvent(GuiOpenEvent event)
    {
        Minecraft minecraft = Minecraft.getMinecraft();

        if(event.getGui() instanceof GuiIngameMenu)
        {
            event.setCanceled(true);
            minecraft.displayGuiScreen(new GuiMainMenu());
        }
        else if (event.getGui() != null && event.getGui().getClass().getName().equalsIgnoreCase("net.minecraft.client.gui.inventory.GuiInventory"))
        {
            if(!minecraft.player.isCreative())
            {
                event.setCanceled(true);
                Packet.networkWrapper.sendToServer(new SPacketClientOpenInventory());
            }

        }
        else if (event.getGui() != null && event.getGui().getClass().getName().equalsIgnoreCase("net.minecraft.client.gui.inventory.GuiCrafting"))
        {
            //Packet.networkWrapper.sendToServer(new SPacketNewWorkBench());

        }
        if(event.getGui() != null && event.getGui().getClass().getName().contains("net.minecraft.client.gui.inventory.GuiChest") && !minecraft.player.isCreative() )
        {

            GuiChest chest = (GuiChest)event.getGui();

            if(chest.inventorySlots.inventorySlots.size() == 63)
            {
                int[] widthList = new int[] {-35, -11, 13, 37, 61, 85, 108, 133, 156};
                int[] heightList = new int[] {214, 184, 161, 137};

                int[] widthChestList = new int[] {-35, -11, 13, 37, 61, 85, 108, 133, 156};
                int[] heightChestList = new int[] {14, 38, 62};

                int loopCount = 0;
                for(int i=0; i < heightChestList.length; i++)
                {
                    for(int j=0; j < widthChestList.length; j++)
                    {
                        chest.inventorySlots.inventorySlots.get(loopCount).xPos = widthChestList[j] + 19;
                        chest.inventorySlots.inventorySlots.get(loopCount).yPos = heightChestList[i] - 5;
                        loopCount++;
                    }
                }

                loopCount = 54;
                int saveCheck = 63;
                for(int i=0; i <heightList.length; i++)
                {
                    for(int j=0; j <widthList.length; j++)
                    {
                        if(loopCount == 63)
                            loopCount =27;

                        chest.inventorySlots.inventorySlots.get(loopCount).xPos = widthList[j] + 19;
                        chest.inventorySlots.inventorySlots.get(loopCount).yPos = heightList[i] - 39;

                        loopCount++;

                    }
                }
            }
            else
            {
                int[] widthList = new int[] {-35, -11, 13, 37, 61, 85, 108, 133, 156};
                int[] heightList = new int[] {214, 184, 161, 137};

                int[] widthChestList = new int[] {-35, -11, 13, 37, 61, 85, 108, 133, 156};
                int[] heightChestList = new int[] {14, 38, 62, 86, 110, 134};

                int loopCount = 0;
                for(int i=0; i < heightChestList.length; i++)
                {
                    for(int j=0; j < widthChestList.length; j++)
                    {
                        chest.inventorySlots.inventorySlots.get(loopCount).xPos = widthChestList[j] + 19;
                        chest.inventorySlots.inventorySlots.get(loopCount).yPos = heightChestList[i] - 15;
                        loopCount++;
                    }
                }
                loopCount = 54;
                int saveCheck = 63;
                for(int i=0; i <heightList.length; i++)
                {
                    for(int j=0; j <widthList.length; j++)
                    {

                        chest.inventorySlots.inventorySlots.get(loopCount).xPos = widthList[j] + 19;
                        chest.inventorySlots.inventorySlots.get(loopCount).yPos = heightList[i] + 25;

                        loopCount++;

                    }
                }
            }

        }

    }
    Variable variable = Variable.Instance();

    int batteryTick = 0;
    int batteryTickSec = 0;

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent event)
    {
        if (event.getSound().getSoundLocation().equals(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.getSoundName())) {
            event.setResultSound(null); // 사운드를 무시합니다.
        }
    }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        batteryTick++;
        if(variable.m_subWayAniActive)
        {
            variable.m_subwayTick++;
            if(variable.m_subwayTick >= 30)
            {
                variable.m_subwayTick = 0;
                System.out.println(variable.m_subWayStatus);
                if(variable.m_subWayStatus.equals(Variable.eSubWayStatus.IDLE))
                {
//                    if(variable.m_subWaySpeed <= 0)
//                    {
//                        variable.m_subWaySpeed = 0;
//                        variable.m_subWayStatus = Variable.eSubWayStatus.BREAK;
//                    }
                    if(variable.m_subWaySpeed >= 24.5f)
                        variable.m_subWaySpeed -= 0.5f;
                    else if(variable.m_subWaySpeed < 24.5f && variable.m_subWaySpeed >= 17f)
                        variable.m_subWaySpeed -= 2.5f;
                    else if(variable.m_subWaySpeed <= 17 && variable.m_subWaySpeed >= 2f)
                        variable.m_subWaySpeed -= 3f;
                    else if(variable.m_subWaySpeed < 2)
                        variable.m_subWaySpeed = 1f;
                    System.out.println(variable.m_subWaySpeed);

                }
                else if(variable.m_subWayStatus.equals(Variable.eSubWayStatus.BREAK))
                {
                    variable.m_subWayStatus = Variable.eSubWayStatus.DOOR_OPEN_L;
                }
                else if(variable.m_subWayStatus.equals(Variable.eSubWayStatus.DOOR_OPEN_L))
                {
                    variable.m_subwayDelayTick+=1;
                    if(variable.m_subwayDelayTick >= 2)
                    {
                        variable.m_subWayStatus = Variable.eSubWayStatus.DOOR_OPEN_L_ING;
                        variable.m_subwayDelayTick = 0;
                    }

                }


            }

        }
        if(minecraft.player != null)
        {
            if (minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(Items.AIR))
                variable.m_changeMod = false;
            else if (minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(ModItems.TRANS))
                variable.m_changeMod = true;
        }
//        if(variable.m_changeMod)
//        {
//            variable.m_changeToolCheckTick++;
//            if(variable.m_changeToolCheckTick >= 40)
//            {
//                variable.m_changeToolCheckTick = 0;
//                variable.m_changeHealth -= 1;
//                if(variable.m_changeHealth <= 0)
//                {
//                    variable.m_changeHealth = 0;
//
//                }
//            }
//        }
        if(batteryTick >= 40)
        {
            batteryTick = 0;
            batteryTickSec += 1;
            if(batteryTickSec >= 30)
            {
                batteryTickSec = 0;
                if(minecraft.player != null)
                {
                    if (variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.NONE) ||
                            variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.MENU) || variable.m_phoneGuiStatus.equals(Variable.PHONE_GUI_VIEW_STATUS.URGENTTEXT) )
                        return;

                    variable.currentBattery -= 1;
                    if(variable.currentBattery <= 0)
                    {
                        if( ! variable.m_phoneStatus.equals(Variable.WATCH_STATUS.IDLE))
                        {
                            variable.m_phoneStatus = Variable.WATCH_STATUS.IDLE;
                            minecraft.player.sendChatMessage("/통화방이동 " + minecraft.player.getName()+ " " + Variable.getMemberNameToDiscordNumber(minecraft.player.getName()));
                            minecraft.player.sendChatMessage("/통화방이동 " + variable.m_callingPlayer.name()+ " " + Variable.getMemberNameToDiscordNumber(variable.m_callingPlayer.name()));

                            Packet.networkWrapper.sendToServer(new SPacketWatchStatus(Variable.WATCH_STATUS.IDLE, variable.m_callingPlayer.name()));
                            variable.m_phoneStatus = Variable.WATCH_STATUS.IDLE;
                            variable.m_callingPlayer = Variable.PHONE_CALLING_PLAYER.NONE;
                        }
                        variable.currentBattery = 0;
                    }
                    Packet.networkWrapper.sendToServer(new SPacketBatterySync(minecraft.player.getName(), (int) variable.currentBattery));

                }
            }
        }

    }
    public boolean m_areaViewActive = false;
    public boolean m_areaCloseActive = false;

    public String m_viewAreaName = "";

    public static String currentPlayedLoop = null;


    private double getVolume(SoundCategory category)
    {
        return 0.4D * minecraft.gameSettings.getSoundLevel(category);
    }
    @SubscribeEvent
    public void onClientSoundTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START) {
            if (currentPlayedLoop != null) {
                Music music = musicMap.get(currentPlayedLoop);
                if(music != null)
                    music.setVolume(getVolume(SoundCategory.AMBIENT));
            }
        }
    }

    @SubscribeEvent
    public void onBlockRender(RenderWorldLastEvent event)
    {


    }

    @Override
    public void openMovieGui(String name)
    {
        try {
            Minecraft.getMinecraft().displayGuiScreen(new VideoGui(name, 0.5f, false));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String, Music> musicMap = new HashMap<>();

    void initTinySound()
    {
        // TinySound 초기화
        TinySound.init();

        //이후 클라이언트에서 .wav 소리 파일을 읽어서 등록
        File musicFolder = new File("./music/");
        musicFolder.mkdirs();
        for (File file : musicFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".wav");
            }
        })) {
            if (file.isFile()) {
                String fileName = file.getName();
                musicMap.put(fileName.substring(0, fileName.lastIndexOf(".")), TinySound.loadMusic(file));
            }

        }
    }
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        OBJLoader.INSTANCE.addDomain(Reference.MODID);

        initTinySound();

        File modFolder = new File(Minecraft.getMinecraft().mcDataDir, "resource/gwr");
        modFolder.mkdirs();
        ClientRegistry.bindTileEntitySpecialRenderer(EmergenTile.class, new EmergenRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCardReader.class, new CardTileRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileSubwayUnlock.class, new SubwayRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(BombTile.class, new BombRenderer());


        List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "resourcePackList");
        IResourcePack pack = new ModResourcePack(modFolder);
        defaultResourcePacks.add(pack);
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(pack);

        RenderingRegistry.registerEntityRenderingHandler(EntitySubWay.class, SubWayGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySubWay2.class, SubWayGeoRenderer2::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityAutoMine.class, RenderEntityAutoMine::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine.class, EntityAutoMineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine2.class, EntityAutoMineRenderer2::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine3.class, EntityAutoMineRenderer3::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine4.class, EntityAutoMineRenderer4::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine5.class, EntityAutoMineRenderer5::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine6.class, EntityAutoMineRenderer6::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine7.class, EntityAutoMineRenderer7::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine8.class, EntityAutoMineRenderer8::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine9.class, EntityAutoMineRenderer9::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine10.class, EntityAutoMineRenderer10::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine11.class, EntityAutoMineRenderer11::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGeoAutoMine12.class, EntityAutoMineRenderer12::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBaseStore.class, EntityBaseStoreRenderer::new);


        RenderingRegistry.registerEntityRenderingHandler(EntityShop1.class, EntityShopRenderer1::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShop2.class, EntityShopRenderer2::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShop3.class, EntityShopRenderer3::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShop4.class, EntityShopRenderer4::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShop5.class, EntityShopRenderer5::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityShop6.class, EntityShopRenderer6::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRobot.class, ReplaceZombieToRobot::new);


        RenderingRegistry.registerEntityRenderingHandler(EntitySubWayNpc.class, EntitySubway::new);


        File movieFolder = new File(Minecraft.getMinecraft().mcDataDir, "movie");
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            movieFolder.mkdirs();
            initFfmpeg();
            initMovie();
        }
        else
        {

        }

    }
    public void initFfmpeg()
    {
        //필수 파일 FFmpeg 파일 없을 시 자동으로 게임 로딩구간에서 다운로드
        //후 클라이언트 폴더에서 설치
        File ffmpegDir = new File(minecraft.mcDataDir + "\\ffmpeg\\");

        if (!ffmpegDir.exists())
        {
            File ffmpegZip = new File(minecraft.mcDataDir + "\\ffmpeg.zip");
            try {
                URL url = new URL("https://www.googleapis.com/drive/v3/files/1vzUkC67d2PG11lDp5tdF9fawijg957UP?alt=media&key=AIzaSyBHm-mKOnz1ErC-sb_VSQvFslf8XGnFV-g");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537");
                System.out.println("ffmpegZip 파일 다운로드중");
                FileUtils.copyToFile(connection.getInputStream(), ffmpegZip);
                System.out.println("구글드라이브 Ffmpeg Zip 다운로드 완료");
                decompress(ffmpegZip, minecraft.mcDataDir + "\\FFMPEG\\");
                System.out.println("ffmpeg 압축 풀기 완료");
                //Files.delete(ffmpegZip.toPath());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public static void decompress(File zipFile, String directory) throws Throwable {
        FileInputStream fis = null;
        ZipInputStream zis = null;
        ZipEntry zipentry = null;
        try {
            fis = new FileInputStream(zipFile);
            zis = new ZipInputStream(fis);
            while ((zipentry = zis.getNextEntry()) != null) {
                String filename = zipentry.getName();
                File file = new File(directory, filename);
                if (zipentry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }
                createFile(file, zis);
            }
        } catch (Throwable e) {
            throw e;
        } finally {
            if (zis != null)
                zis.close();
            if (fis != null)
                fis.close();
        }
    }

    private static void createFile(File file, ZipInputStream zis) throws Throwable {
        File parentDir = new File(file.getParent());
        if (!parentDir.exists())
            parentDir.mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[256];
            int size = 0;
            while ((size = zis.read(buffer)) > 0)
                fos.write(buffer, 0, size);
        } catch (Throwable e) {
            throw e;
        }
    }
    public void initMovie()
    {
        Date date = new Date();

        File movie01 = new File(minecraft.mcDataDir + "\\movie\\mainmenu.mp4");
        if (!movie01.exists())
        {
            System.out.println("무비다운로드파일 다운로드중" + date.toString());
            URL url = null;
            try {
                url = new URL("https://www.googleapis.com/drive/v3/files/1OCi7JIbt9a7_LTzfFtDHNZkZnXjXSETP?alt=media&key=AIzaSyBHm-mKOnz1ErC-sb_VSQvFslf8XGnFV-g");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537");
                FileUtils.copyToFile(connection.getInputStream(), movie01);
                System.out.println("무비 다운로드 완료" + date.toString());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }


    @SubscribeEvent
    public void onPlayerRenderEvent(RenderLivingEvent.Specials.Pre event)
    {
        if(minecraft.player.isCreative())
            return;

        if(event.getEntity() instanceof  EntityPlayer)
        {
            EntityPlayer entPlayer = (EntityPlayer) event.getEntity();
            if(entPlayer.isSpectator())
            {
                event.setCanceled(true);
            }

        }

    }

    @SubscribeEvent
    public void onMainMenuButtonClick(GuiOpenEvent event)
    {
        if (event.getGui() instanceof net.minecraft.client.gui.GuiMainMenu)
        {
            stopLoopMusic();
        }
        else if (event.getGui() instanceof GuiMultiplayer)
        {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("win") >= 0)
            {
                event.setCanceled(true);

                Variable variable = Variable.Instance();

                if(!variable.m_directJoin)
                {
                    System.out.println("서버명 - " + variable.m_ServerName + "     서버주소  " + variable.m_ServerIp);
                    ServerData serverdata = new ServerData(variable.m_ServerName, variable.m_ServerIp, false);
                    serverdata = new ServerData(variable.m_ServerName, "double-server.asuscomm.com:14000", false);
                    net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(minecraft.currentScreen, serverdata);
                }
                else
                {
                    ServerData serverdata = new ServerData(variable.m_ServerName, variable.m_directServerIp, false);

                    net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(minecraft.currentScreen, serverdata);
                }



            }

        }
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(this);
        String category = "지상세계";
        //ClientRegistry.registerKeyBinding(m_phoneGuiOpenKey = new KeyBinding("", 20, category));
        ClientRegistry.registerKeyBinding(m_phoneCallAccept = new KeyBinding("통화 승낙", 26, category));
        ClientRegistry.registerKeyBinding(m_phoneCallRefuse = new KeyBinding("통화 거절", 27, category));


        GeckoLib.initialize();

        Display.setResizable(false);
    }

    @Override
    public void openGuiScreenPhoneOpen(boolean antenna)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            minecraft.displayGuiScreen(new GuiPhone(antenna));
        });
    }

    @Override
    public void openGuiSendMailOpen(boolean antenna)
    {

    }

    ISound ambientSound;
    boolean isPlayeAmbient = false;

    private void stopLoopMusic() {
        if (currentPlayedLoop != null) {
            if (musicMap.containsKey(currentPlayedLoop))
                ((Music)musicMap.get(currentPlayedLoop)).stop();
            currentPlayedLoop = null;
        }
    }
    private void playLoopMusic(String musicName) {
        stopLoopMusic();
        if (!musicMap.containsKey(musicName)) {
            File musicFile = new File("music/" + musicName + ".wav");
            if (!musicFile.exists())
                return;
            musicMap.put(musicName, TinySound.loadMusic(musicFile));
        }
        currentPlayedLoop = musicName;
        Music music = musicMap.get(musicName);
        music.setVolume(getVolume(SoundCategory.AMBIENT));
        music.play(true);
    }

    @Override
    public void playAmbientSound(String name)
    {
        Variable variable = Variable.Instance();

//
//        if(ambientSound != null)
//        {
//            isPlayeAmbient = false;
//            if(ambientSound.equals(Sound.getAmbientSound(event, SoundCategory.AMBIENT, minecraft.gameSettings.getSoundLevel(SoundCategory.AMBIENT), 1f)))
//                return;
//            stopAmbientSound();
//
//            ambientSound = Sound.getAmbientSound(event, SoundCategory.AMBIENT, minecraft.gameSettings.getSoundLevel(SoundCategory.AMBIENT), 1f);
//
//        }
//        else
//        {
//            ambientSound = Sound.getAmbientSound(event, SoundCategory.AMBIENT, minecraft.gameSettings.getSoundLevel(SoundCategory.AMBIENT), 1f);
//        }
//        minecraft.getSoundHandler().playSound(ambientSound);
//        isPlayeAmbient = true;


        playLoopMusic(name);


    }

    @Override
    public void openSubwayQuestionUi(String start, String end)
    {
        minecraft.displayGuiScreen(new GuiSubway(start, end));
    }
    @Override
    public void stopAmbientSound()
    {
        isPlayeAmbient = false;
        minecraft.getSoundHandler().stopSound(ambientSound);

//        if (ambientSound != null) {
//            minecraft.getSoundHandler().stopSound(ambientSound);
//            ambientSound = null;
//            isPlayeAmbient = false;
//        }
    }

    HashMap<SoundEvent, ISound> hashSoundEventToISound = new HashMap<>();
    @Override
    public void playSound(SoundEvent event)
    {
        Variable variable = Variable.Instance();

        ISound sound = hashSoundEventToISound.get(event);
        if(sound != null) {
            minecraft.getSoundHandler().stopSound(sound);
        }
        else
        {
            sound = Sound.getSound(event, SoundCategory.RECORDS, 1f);
            hashSoundEventToISound.put(event, sound);
        }
        minecraft.getSoundHandler().playSound(sound);

    }

    @Override
    public void stopSound(SoundEvent event)
    {

        ISound sound = hashSoundEventToISound.get(event);
        if(sound != null) {
            minecraft.getSoundHandler().stopSound(sound);
        }
    }

    @Override
    public void openEleBankScreen(float bankAmount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, String item, boolean infinity)
    {
        minecraft.displayGuiScreen(new GuiEleBank(bankAmount, bankUseCount, playerOilCanAmount, oilListCount, pos, item, infinity));
    }
    @Override
    public void openBankScreen(float bankAmount, int bankUseCount, int playerOilCanAmount, int oilListCount, BlockPos pos, boolean infinity)
    {
        minecraft.displayGuiScreen(new GuiOilBank(bankAmount, bankUseCount, playerOilCanAmount, oilListCount, pos, infinity));
    }
    public void startSubWayEffectEast()
    {
        startSubWayEffectLoc(2, minecraft.player.getPosition());
    }

    @Override
    public void startSubWayEffectLoc(int number, BlockPos pos)
    {
        if(number == 1)
        {
            for(Entity entity : minecraft.world.getLoadedEntityList())
            {
                if(entity instanceof EntitySubWay2 || entity instanceof EntitySubWay)
                {
                    minecraft.world.removeEntity(entity);
                }
            }

            variable.m_subWayAniActive = true;
            variable.m_subWaySpeed = 30f;

            variable.m_subWayStatus = Variable.eSubWayStatus.IDLE;
            variable.m_subwayDelayTick = 0;

            World world = minecraft.player.world;
            BlockPos vec3d = pos;
            double d0 = vec3d.getX()+0.5d;
            double d1 = vec3d.getY();
            double d2 = vec3d.getZ()+0.5d;

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            boolean flag = false;

            String s1 = "";
            try
            {
                nbttagcompound = JsonToNBT.getTagFromJson(s1);
                flag = true;
            }
            catch (NBTException nbtexception)
            {

            }

            nbttagcompound.setString("id", "groundworld:subway");

            //Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0-10, d1, d2 - 100, true); 남쪽기준

            Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0+9, d1-1.5, d2 + 100, true); // 북쪽기준


            //variable.endPosZ = d2+ 3.5f; // 남쪽기준
            variable.endPosZ = d2 - 4.5f; // 북쪽기준
        }
        else
        {

            for(Entity entity : minecraft.world.getLoadedEntityList())
            {
                if(entity instanceof EntitySubWay2 || entity instanceof EntitySubWay)
                {
                    minecraft.world.removeEntity(entity);
                }
            }

            variable.m_subWayAniActive = true;
            variable.m_subWaySpeed = 31f;

            variable.m_subWayStatus = Variable.eSubWayStatus.IDLE;
            variable.m_subwayDelayTick = 0;

            World world = minecraft.player.world;

            BlockPos vec3d = pos;
            double d0 = vec3d.getX()+0.5d;
            double d1 = vec3d.getY();
            double d2 = vec3d.getZ()+0.5d;

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            boolean flag = false;

            String s1 = "";
            try
            {
                nbttagcompound = JsonToNBT.getTagFromJson(s1);
                flag = true;
            }
            catch (NBTException nbtexception)
            {

            }

            nbttagcompound.setString("id", "groundworld:subway2");

            Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0 - 100, d1-1.5, d2 + 9, true); // 북쪽기준


            //variable.endPosZ = d2+ 3.5f; // 남쪽기준
            variable.endPosZ = d0 + 3.5f; // 북쪽기준

        }
    }

    @Override
    public void startSubWayEffect()
    {
        startSubWayEffectLoc(1, minecraft.player.getPosition());
    }

    @Override
    public void subWayReset()
    {


        Minecraft minecraft = Minecraft.getMinecraft();
        for(Entity entity : minecraft.world.getLoadedEntityList())
        {
            if(entity instanceof EntitySubWay2 || entity instanceof EntitySubWay)
            {
                minecraft.world.removeEntity(entity);
            }
        }
    }

    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent event)
    {
        if(event.getGui() instanceof GuiOptions)
        {
        }
    }

    @SubscribeEvent
    public void onItemChanged(PlayerEvent event)
    {


    }

    void drawLoading(ScaledResolution scale,  float partialTick, float fpsCurrection)
    {
        float scaleWidth = (float) scale.getScaledWidth_double();
        float scaleHeight = (float) scale.getScaledHeight_double();


        if(variable.m_animationDayStart.size() != 0) //동그라미 로딩바
        {
            AnimationState aniState = variable.m_animationDayStart.get(0);

            float fade_Back_Width = 1920f / 3f;
            float fade_Back_Height = 1080f /3f;
            if(aniState.m_animationOpen)
            {
                if(aniState.m_currentAnimationFrame <= aniState.m_maxAnimationFrame)
                {
                    aniState.m_currentAnimationFrame += partialTick * fpsCurrection * aniState.m_AnimationFrameTime;
                }
                else
                {
                    //System.out.println("페이드 인 종료 및 딜레이 시작 " + LocalTime.now());
                    aniState.m_animationOpen = false;
                    aniState.m_animationDelay = true;
                    System.out.println("완전히 블랙");
                }
            }
            else if(!aniState.m_animationOpen && aniState.m_animationDelay)
            {
                if(aniState.m_currentAnimationDelay < aniState.m_maxAnimationDelay)
                {
                    aniState.m_currentAnimationDelay += partialTick * fpsCurrection * aniState.m_AnimationDelayTime;
                }
                else
                {
                    aniState.m_animationDelay = false;
                    aniState.m_animationClose = true;
                    //System.out.println("페이드 딜레이 종료  클로즈 시작" + LocalTime.now());

                }
            }
            else if(!aniState.m_animationOpen && !aniState.m_animationDelay && aniState.m_animationClose)
            {
                if(aniState.m_currentAnimationFrame > 0)
                {
                    aniState.m_currentAnimationFrame -= partialTick * fpsCurrection  * aniState.m_AnimationFrameTime;
                }
                else
                {
                    aniState.m_currentAnimationFrame = 0f;
                    aniState.m_animationClose = false;
                    variable.m_animationDayStart.remove(0);
                    ///System.out.println("페이드 클로즈 종료 " + LocalTime.now());
                }
            }
            float alpha = aniState.m_currentAnimationFrame * 0.01f;


            if(aniState.m_String.equals("일차시작"))
            {
                GlStateManager.pushMatrix();
                {

                    minecraft.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/hud/fade_background.png"));
                    GlStateManager.translate(0, 0, 5);
                    drawTexture(scaleWidth/2 - fade_Back_Width/2, scaleHeight/2 - fade_Back_Height/2, fade_Back_Width, fade_Back_Height, 0, 0, 1, 1, 50, alpha);

                }
                GlStateManager.popMatrix();
            }
            else if (aniState.m_String.equalsIgnoreCase("일차종료"))
            {
                GlStateManager.pushMatrix();
                {

                    minecraft.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/hud/fade_background2.png"));
                    GlStateManager.translate(0, 0, 5);
                    drawTexture(scaleWidth/2 - fade_Back_Width/2, scaleHeight/2 - fade_Back_Height/2, fade_Back_Width, fade_Back_Height, 0, 0, 1, 1, 50, alpha);

                }
                GlStateManager.popMatrix();
            }
            else
            {
                GlStateManager.pushMatrix();
                {

                    minecraft.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/hud/fade_background3.png"));
                    GlStateManager.translate(0, 0, 5);
                    drawTexture(scaleWidth/2 - fade_Back_Width/2, scaleHeight/2 - fade_Back_Height/2, fade_Back_Width, fade_Back_Height, 0, 0, 1, 1, 50, alpha);

                }
                GlStateManager.popMatrix();
            }



        }
    }

    void chargeCrosshair()
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        if(rayCastChargeType.equals("oil"))
        {
            if (minecraft.player.getHeldItemMainhand().getItem().equals(Items.AIR))
                Render.drawStringScaleResizeByMiddleWidth("기름통을 들고 왼쪽 클릭 해주세요.", scaleWidth/2f, scaleHeight/2 + 10, 1, 1, -1, true);
            else if(minecraft.player.getHeldItemMainhand().getItem().equals( GroundItemStack.FULL_OILBOX.getItem()))
                Render.drawStringScaleResizeByMiddleWidth("왼쪽 클릭으로 주유 가능합니다.", scaleWidth/2f, scaleHeight/2 + 10, 1, 1, -1, true);
            else
            {
                Render.drawStringScaleResizeByMiddleWidth("기름통을 들고 왼쪽 클릭 해주세요.", scaleWidth/2f, scaleHeight/2 + 10, 1, 1, -1, true);
            }
        }
        if (rayCastChargeType.equals("ele"))
        {
            if (minecraft.player.getHeldItemMainhand().getItem().equals(Items.AIR))
                Render.drawStringScaleResizeByMiddleWidth("충전 할 아이템을 들고 왼쪽 클릭 해주세요.", scaleWidth/2f, scaleHeight/2 + 10, 1, 1, -1, true);
            else if(minecraft.player.getHeldItemMainhand().getItem().equals( ModItems.phonew_ant) || minecraft.player.getHeldItemMainhand().getItem().equals(GroundItemStack.CORELAMP.getItem())
            ||  minecraft.player.getHeldItemMainhand().getItem().equals(GroundItemStack.CORELAMP_OFF.getItem()))
                Render.drawStringScaleResizeByMiddleWidth("왼쪽 클릭으로 충전 가능합니다.", scaleWidth/2f, scaleHeight/2 + 10, 1, 1, -1, true);
            else
            {
                Render.drawStringScaleResizeByMiddleWidth("충전 할 아이템을 들고 왼쪽 클릭 해주세요.", scaleWidth/2f, scaleHeight/2 + 10, 1, 1, -1, true);
            }
        }

    }

    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent event)
    {
        Variable variable = Variable.Instance();

        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        int scaleFactor = scaled.getScaleFactor();
        float fpsCurrection = (120f / Minecraft.getDebugFPS()) * 0.9f;

        float partialTick = event.getPartialTicks();

        float rescalePosition = scaleHeight / 360f;

        float letterboxBar_Width = scaleWidth ;
        float letterboxBar_Height = 44 / 3 * rescalePosition ;

        float fade_Back_Width = 1920 / scaleFactor;
        float fade_Back_Height = 1080 /scaleFactor;

        if (event.getType().equals(RenderGameOverlayEvent.ElementType.CROSSHAIRS))
        {
            //레이케스트 검출 결과가 있을 경우
            if(! rayCastChargeType.equals(""))
            {
                event.setCanceled(true);
                chargeCrosshair();
            }
        }
        if(variable.m_animationDayStart.size() != 0)
        {
            if (event.getType().equals(RenderGameOverlayEvent.ElementType.CROSSHAIRS))
            {
                event.setCanceled(true);
            }
        }
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) || event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD)
                || event.getType().equals(RenderGameOverlayEvent.ElementType.ARMOR) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE))
        {
            event.setCanceled(true);
        }
        else if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))
        {
            if(! minecraft.player.isSpectator())
                event.setCanceled(true);

            if(variable.m_cinematicAniState.size() == 0 && variable.m_animationStateList.size() == 0 && variable.m_animationDayStart.size() == 0)
            {
                //스마트워치 Render 함수
                phone(partialTick);
                if(variable.m_changeMod)//변장도구 착용 여부
                    changeHud(partialTick);
            }

        }
        else if (event.getType().equals(RenderGameOverlayEvent.ElementType.CHAT))
        {
            event.setCanceled(true);
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0 , scaleHeight- 50,5);
                minecraft.ingameGUI.getChatGUI().drawChat(minecraft.ingameGUI.getUpdateCounter());
            }
            GlStateManager.popMatrix();

        }
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ALL))
        {

            drawLoading(scaled, partialTick, fpsCurrection);
            if(variable.m_animationStateList.size() != 0)
            {
                AnimationState aniState = variable.m_animationStateList.get(0);
                if(aniState.m_animationOpen)
                {
                    if(aniState.m_currentAnimationFrame <= aniState.m_maxAnimationFrame)
                    {
                        aniState.m_currentAnimationFrame += partialTick * fpsCurrection * aniState.m_AnimationFrameTime;
                    }
                    else
                    {
                        //System.out.println("페이드 인 종료 및 딜레이 시작 " + LocalTime.now());
                        aniState.m_animationOpen = false;
                        aniState.m_animationDelay = true;
                    }
                }
                else if(!aniState.m_animationOpen && aniState.m_animationDelay)
                {
                    if(aniState.m_currentAnimationDelay < aniState.m_maxAnimationDelay)
                    {
                        aniState.m_currentAnimationDelay += partialTick * fpsCurrection * aniState.m_AnimationDelayTime;
                    }
                    else
                    {
                        aniState.m_animationDelay = false;
                        aniState.m_animationClose = true;
                        //System.out.println("페이드 딜레이 종료  클로즈 시작" + LocalTime.now());

                    }
                }
                else if(!aniState.m_animationOpen && !aniState.m_animationDelay && aniState.m_animationClose)
                {
                    if(aniState.m_currentAnimationFrame > 0)
                    {
                        aniState.m_currentAnimationFrame -= partialTick * fpsCurrection  * aniState.m_AnimationFrameTime;
                    }
                    else
                    {
                        aniState.m_currentAnimationFrame = 0f;
                        aniState.m_animationClose = false;
                        variable.m_animationStateList.remove(0);
                        ///System.out.println("페이드 클로즈 종료 " + LocalTime.now());
                    }
                }
                float alpha = aniState.m_currentAnimationFrame * 0.01f;
                GlStateManager.pushMatrix();
                {

                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/hud/hud/fade_background.png"));
                    GlStateManager.translate(0, 0, 5);
                    drawTexture(scaleWidth/2 - fade_Back_Width/2, scaleHeight/2 - fade_Back_Height/2, fade_Back_Width, fade_Back_Height, 0, 0, 1, 1, 50, alpha);

                }
                GlStateManager.popMatrix();
            }
            if(variable.m_animationBroadcastList.size() > 0)
            {
                AnimationState animation = variable.m_animationBroadcastList.get(0);
                if(!animation.m_animationShow)
                {
                    animation.m_animationShow = true;
                    animation.m_animationPlay = true;

                    minecraft.getSoundHandler().playSound(Sound.getSound(GwSoundHandler.NOTI, SoundCategory.RECORDS, minecraft.gameSettings.getSoundLevel(SoundCategory.RECORDS)));
                    //알림 소리
                }
                else
                {
                    float aniValue = 0f;
                    float animationAlpha = 100f;

                    if(animation.m_animationOpen)
                    {
                        animation.m_currentAnimationFrame += fpsCurrection * 0.5f;
                        animation.m_curreentAniYPosPer += fpsCurrection * 0.5f;
                        aniValue = Render.easeInOutBack(((animation.m_curreentAniYPosPer)) * 0.01f);
                        //animationAlpha = easeOutCubic(animation.m_currentAnimationFrame * 0.01f);

                    }
                    else if(animation.m_animationDelay)
                    {
                        animation.m_currentAnimationFrame += fpsCurrection * 0.1f;
                        animation.m_curreentAniYPosPer = 100;
                        aniValue = Render.easeInOutBack(animation.m_curreentAniYPosPer * 0.01f);
                    }
                    else
                    {
                        animation.m_currentAnimationFrame -= fpsCurrection * 0.5f;
                        animation.m_curreentAniYPosPer -= fpsCurrection * 0.5f;
                        aniValue = Render.easeInOutBack(((animation.m_curreentAniYPosPer)) * 0.01f);
                        //animationAlpha = easeOutCubic(animation.m_currentAnimationFrame * 0.01f);

                    }
                    if(animation.m_animationOpen || animation.m_animationDelay)
                    {
                        if(animation.m_maxAnimationFrame <= animation.m_currentAnimationFrame)
                        {

                            if (animation.m_animationOpen)
                            {
                                animation.m_animationOpen = false;
                                animation.m_animationDelay = true;
                                animation.m_currentAnimationFrame = 0;

                            } else if (animation.m_animationDelay)
                            {
                                animation.m_animationDelay = false;
                                animation.m_animationClose = true;
                                animation.m_currentAnimationFrame = 100;
                            }
                        }
                    }
                    else
                    {
                        if(animation.m_currentAnimationFrame < 0)
                        {
                            animation.m_currentAnimationFrame = 0;
                            if(animation.m_animationClose)
                            {
                                animation.m_animationClose = false;
                                variable.m_animationBroadcastList.remove(0);
                                return;
                            }
                        }

                    }
                    if(variable.m_animationBroadcastList.get(0) != null)
                    {

                        float broadCastWidth = 300/3f;
                        float broadCastHeight = 126/3f;

                        float boradCastYpos = 50f;
                        float boradCastXpos = 50f;


                        String texture = variable.m_animationBroadcastList.get(0).m_String;

                        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\info\\"+texture+".png"));
                        int yPos = 0;
                        if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER) || (variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING)))
                            yPos = 49;
                        Render.drawTexture(scaleWidth - ((broadCastWidth + 12.5f) * aniValue), scaleHeight - broadCastHeight - 128 - yPos, broadCastWidth, broadCastHeight, 0, 0, 1, 1, 4, 1f);

                    }

                }

            }
            if(variable.m_cinematicAniState.size() != 0)
            {
                CinematicAniState cinemaState = variable.m_cinematicAniState.get(0);
                if(cinemaState.m_animationOpen)
                {
                    if(cinemaState.m_currentAnimationFrame <= cinemaState.m_maxAnimationFrame)
                    {
                        cinemaState.m_currentAnimationFrame += partialTick * fpsCurrection * cinemaState.m_AnimationFrameTime;
                    }
                    else
                    {
                        //System.out.println("페이드 인 종료 및 딜레이 시작 " + LocalTime.now());
                        cinemaState.m_animationOpen = false;
                        cinemaState.m_animationDelay = true;
                    }
                }
                else if(!cinemaState.m_animationOpen && cinemaState.m_animationDelay)
                {
                    if(cinemaState.m_currentAnimationDelay < cinemaState.m_maxAnimationDelay)
                    {
                        cinemaState.m_currentAnimationDelay += partialTick * fpsCurrection * cinemaState.m_AnimationDelayTime;
                    }
                    else
                    {
                        cinemaState.m_animationDelay = false;
                        cinemaState.m_animationClose = true;
                        //System.out.println("페이드 딜레이 종료  클로즈 시작" + LocalTime.now());

                    }
                }
                else if(!cinemaState.m_animationOpen && !cinemaState.m_animationDelay && cinemaState.m_animationClose)
                {
                    if(cinemaState.m_currentAnimationFrame > 0)
                    {
                        cinemaState.m_currentAnimationFrame -= partialTick * fpsCurrection  * cinemaState.m_AnimationFrameTime;
                    }
                    else
                    {
                        cinemaState.m_currentAnimationFrame = 0f;
                        cinemaState.m_animationClose = false;
                        variable.m_cinematicAniState.remove(0);
                        ///System.out.println("페이드 클로즈 종료 " + LocalTime.now());
                    }
                }
                float per = easeInOutBack(cinemaState.m_currentAnimationFrame * 0.01f);
                if(active)
                {
                    GlStateManager.pushMatrix();
                    {
                        float endper = 25f + 75 * per;

                        //minecraft.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/hud/movie.png"));
                        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/hud/hud/letterbox.png"));
                        drawYLinearTexture(scaleWidth/2 - letterboxBar_Width/2, scaleHeight - letterboxBar_Height*4, letterboxBar_Width, letterboxBar_Height * 4, endper, 1f, 10);
                        //drawTexture(scaleWidth/2 - letterboxBar_Width/2, scaleHeight - letterboxBar_Height, letterboxBar_Width, letterboxBar_Height * (1 + (per)), 0, 0, 1, 1, 10, 1f);
                        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures/hud/hud/letterbox.png"));
                        drawTexture(scaleWidth/2 - letterboxBar_Width/2,  0, letterboxBar_Width, letterboxBar_Height * (1 + (3 * (per))), 0, 0, 1, 1, 10, 1f);

                    }
                    GlStateManager.popMatrix();
                }
            }

        }

    }

    void changeHud(float partialTick)
    {

        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        float backgroundWidth = 1920f/3f;
        float backgroundHeight = 1080f/3f;

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0 , scaleHeight- 50,5);
            minecraft.ingameGUI.getChatGUI().drawChat(minecraft.ingameGUI.getUpdateCounter());
        }
        GlStateManager.popMatrix();


//        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\background.png"));
//        Render.drawTexture(0, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1000, 1);

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\background.png"));
        Render.drawTexture(0, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1000, 1);


        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\title.png"));
        Render.drawTexture(0, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1000, 1);

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\text.png"));
        Render.drawTexture(0, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1000, 0.28f);

        float numberWidth = 140f/3f;
        float numberHeight = 100f/3f;

        String health = String.valueOf(variable.m_changeHealth);

        String warringCheck = variable.m_changeHealth >= 50 ? "none" : "warring";

        float alpha = 0.92f;

        if (variable.m_changeHealth == 100)
        {
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\1.png"));
            Render.drawTexture(scaleWidth - numberWidth - 156, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\0.png"));
            Render.drawTexture(scaleWidth - numberWidth - 117, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\0.png"));
            Render.drawTexture(scaleWidth - numberWidth - 73, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\per.png"));
            Render.drawTexture(scaleWidth - numberWidth - 28, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);
        }
        else if (variable.m_changeHealth < 10)
        {
            int one = variable.m_changeHealth % 10;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\"+one+".png"));
            Render.drawTexture(scaleWidth - numberWidth - 73, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\per.png"));
            Render.drawTexture(scaleWidth - numberWidth - 28, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);
        }
        else
        {
            int ten = variable.m_changeHealth / 10;
            int one = variable.m_changeHealth % 10;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\"+ten+".png"));
            Render.drawTexture(scaleWidth - numberWidth - 117, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\"+one+".png"));
            Render.drawTexture(scaleWidth - numberWidth - 73, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\"+warringCheck+"\\per.png"));
            Render.drawTexture(scaleWidth - numberWidth - 28, 38, numberWidth, numberHeight, 0, 0, 1, 1, 1000, 1);

        }


        if(warringCheck.equals("warring"))
        {
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\change\\warring.png"));
            Render.drawTexture(0, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1000, 1);
        }



    }


    void phone(float partialTick)
    {


        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();


        Random random = new Random();
        float randomValue = -1 + (1 - (-1)) * random.nextFloat();
        float randomValue2 = -1 + (1 - (-1)) * random.nextFloat();

        //드릴 아이템 사용시 화면 떨림 구현 로직
        if(GroundWorld.instance.m_shake)
        {
            minecraft.player.rotationPitch = minecraft.player.rotationPitch + (randomValue* 0.08f);
            minecraft.player.rotationYaw = minecraft.player.rotationYaw + (randomValue2 * 0.08f);
        }

        float background_Width = 351/3f;
        float background_Height = 369/3f;


        //플레이어 스마트폰 상태가 연결이 되어 통화 상태 일때 Render 작업
        if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING))
        {
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\핸드폰_norch.png"));
            Render.drawTexture(scaleWidth - background_Width - 4, scaleHeight - background_Height - 1, background_Width, background_Height, 0, 0, 1, 1, 5, 1);

        }
        else if (variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER ) || variable.m_phoneStatus.equals(Variable.WATCH_STATUS.IDLE) || variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER )  )
        {
            // 플레이어 스마트폰 상태가 연결중이거나 발신/수신 상태일 경우 상태
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\핸드폰.png"));
            Render.drawTexture(scaleWidth - background_Width - 4, scaleHeight - background_Height - 1, background_Width, background_Height, 0, 0, 1, 1, 5, 1);
        }
        if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_RECIVER))
        {

            //수신전화가 왔을 때
            float callingHead_Width = 106f / 3f;
            float callingHead_Height = 105f / 3f;


            String callingPlayer = variable.getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\face\\"+callingPlayer+".png"));
            Render.drawTexture(scaleWidth - callingHead_Width - 44, scaleHeight - callingHead_Height - 60, callingHead_Width, callingHead_Height, 0, 0, 1, 1, 5, 1);



            float btn_Width = 43f/3f;
            float btn_Height = 43f/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\btn\\승락아이콘.png"));
            Render.drawTexture(scaleWidth - btn_Width - 75, scaleHeight - btn_Height - 26, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1);

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\btn\\거절아이콘.png"));
            Render.drawTexture(scaleWidth - btn_Width - 34, scaleHeight - btn_Height - 26, btn_Width, btn_Height, 0, 0, 1, 1, 5, 1);

            float callingStr_Width = 45/3f;
            float callingStr_Height = 22/3f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\str\\"+callingPlayer+".png"));
            Render.drawTexture(scaleWidth - callingStr_Width - 54, scaleHeight - callingStr_Height - 48, callingStr_Width, callingStr_Height, 0, 0, 1, 1, 5, 1);

        }
        else
        {

            // 수신전화 상태가 아닐 때
            float callStr_Width = 65f/3f;
            float callStr_Height = 12f/3f;

            String callingPlayer = variable.getMemberIdToKoreaNickName(variable.m_callingPlayer.name());
            if(variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CALL_SENDER))
            {
                // 연결중일 경우
                String text = callingPlayer + " 연결중...";
                Render.drawStringScaleResizeByMiddleWidth(text, scaleWidth - 65,  scaleHeight - 109, 10, 0.65f, -1);
                float callingStr_Width = 38/3f;
                float callingStr_Height = 38/3f;
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\face\\"+callingPlayer+"서클.png"));
                Render.drawTexture(scaleWidth - callingStr_Width - 22, scaleHeight - callingStr_Height - 84, callingStr_Width, callingStr_Height, 0, 0, 1, 1, 5, 1);
                float info_width = 300f/3f;
                float info_height = 126f/3f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\info\\통화문자.png"));
                Render.drawTexture(scaleWidth - info_width - 12.5f, scaleHeight - info_height - 128, info_width, info_height, 0, 0, 1, 1, 3, 1);

            }
            else if (variable.m_phoneStatus.equals(Variable.WATCH_STATUS.CONNETING))
            {
                // 연결이 되었을 경우 작업 
                String text = callingPlayer + " 통화중...";
                Render.drawStringScaleResizeByMiddleWidth(text, scaleWidth - 65,  scaleHeight - 109, 10, 0.65f, 1);

                float callingStr_Width = 38/3f;
                float callingStr_Height = 38/3f;
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\face\\"+callingPlayer+"서클.png"));
                Render.drawTexture(scaleWidth - callingStr_Width - 22, scaleHeight - callingStr_Height - 84, callingStr_Width, callingStr_Height, 0, 0, 1, 1, 5, 1);
                float info_width = 300f/3f;
                float info_height = 125f/3f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\info\\통화문자.png"));
                Render.drawTexture(scaleWidth - info_width - 12.5f, scaleHeight/2 - info_height/2 + 30, info_width, info_height, 0, 0, 1, 1, 3, 1);

            }

            if(!variable.isWatchTimerView)
            {
                //스마트 워치 에서 타이머 숫자를 그리는 부분
                float numberImageSize = 80f/3f;

                int sec = variable.m_gameSec;
                int sec1 = variable.m_gameSec % 10;
                int sec10 = variable.m_gameSec / 10;

                int min = variable.m_gameMin;
                int min1 = variable.m_gameMin % 10;
                int min10 = variable.m_gameMin / 10;
                //sec
                {
                    {
                        //10자리
                        {
                            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\timer\\"+sec10+".png"));
                            drawTexture(scaleWidth - background_Width - 4 + 30, scaleHeight - background_Height + 65, numberImageSize, numberImageSize, 0, 0, 1, 1, 6,1);

                        }
                        //1자리
                        {
                            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\timer\\"+sec1+".png"));
                            drawTexture(scaleWidth - background_Width - 4 + 54, scaleHeight - background_Height + 65, numberImageSize, numberImageSize, 0, 0, 1, 1, 6,1);

                        }
                        //s

                        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\timer\\s.png"));
                        drawTexture(scaleWidth - background_Width - 4 + 72, scaleHeight - background_Height + 73, numberImageSize, numberImageSize, 0, 0, 1, 1, 6,1);


                    }
                }

                //min
                {
                    //12
                    {
                        //10자리
                        {
                            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\timer\\"+min10+".png"));
                            drawTexture(scaleWidth - background_Width - 4 + 30, scaleHeight - background_Height + 35, numberImageSize, numberImageSize, 0, 0, 1, 1, 6,1);

                        }
                        //1자리
                        {
                            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\timer\\"+min1+".png"));
                            drawTexture(scaleWidth - background_Width - 4 + 54, scaleHeight - background_Height + 35, numberImageSize, numberImageSize, 0, 0, 1, 1, 6,1);

                        }
                        //s

                        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\timer\\m.png"));
                        drawTexture(scaleWidth - background_Width - 4 + 72, scaleHeight - background_Height + 43, numberImageSize, numberImageSize, 0, 0, 1, 1, 6,1);

                    }
                }


            }
            else
            {
                //타이머 상태가 아닌 원형 ProgressBar 상태에 있을 경우

                float stats_background_width = 222f/3f;
                float stats_background_height = 222f/3f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\stat_background.png"));
                Render.drawTexture(scaleWidth - stats_background_width - 25, scaleHeight - stats_background_height - 18, stats_background_width, stats_background_height, 0, 0, 1, 1, 5, 1);



                float healthBarPer = minecraft.player.getHealth() / minecraft.player.getMaxHealth() * 100f;
                //float healthBarPer = 74f;


                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\체력바.png"));
                Render.drawCricleProgressToQuad(scaleWidth - stats_background_width - 25f, scaleHeight - stats_background_height - 18f, 5, stats_background_width, stats_background_height, healthBarPer, 1);

                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 205/3f;
                    float degrees = (float) ((healthBarPer * 3.6f - 88f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 18f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\체력동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                }
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 206/3f;
                    float degrees = (float) ((0 * 3.6f - 91f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 18f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\체력동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1);

                }
                GlStateManager.popMatrix();


                float hungerBarPer = minecraft.player.getFoodStats().getFoodLevel() / 20f * 100f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\허기바.png"));
                Render.drawCricleProgressToQuad(scaleWidth - stats_background_width - 25f, scaleHeight - stats_background_height - 18f, 5, stats_background_width, stats_background_height, hungerBarPer, 1);
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 167f/3f;
                    float degrees = (float) ((hungerBarPer * 3.6f - 88f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 17.8f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\허기동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                }
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 206f/3f;
                    float degrees = (float) ((0 * 3.6f - 91f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 11.5f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\허기동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1);

                }
                GlStateManager.popMatrix();



                float waterBarPer = variable.waterAmount / 100f * 100f;
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\수분바.png"));
                Render.drawCricleProgressToQuad(scaleWidth - stats_background_width - 25f, scaleHeight - stats_background_height - 18f, 5, stats_background_width, stats_background_height, waterBarPer, 1);
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 129f/3f;
                    float degrees = (float) ((waterBarPer * 3.6f - 88f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 18f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\수분동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                }
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 206f/3f;
                    float degrees = (float) ((0 * 3.6f - 91f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 5.25f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\수분동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1);

                }
                GlStateManager.popMatrix();


                float coldBarPer = variable.coldAmount / 100f * 100f;
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\추위바.png"));
                Render.drawCricleProgressToQuad(scaleWidth - stats_background_width - 25f, scaleHeight - stats_background_height - 18f, 5, stats_background_width, stats_background_height, coldBarPer, 1);
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 91f/3f;
                    float degrees = (float) ((coldBarPer * 3.6f - 88f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f - 18f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\추위동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                }
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 206f/3f;
                    float degrees = (float) ((0 * 3.6f - 91f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + circleRotateRadius/2f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f + 1.0f + circleRotateRadius/2f * Math.sin(degrees));
                    minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\추위동그라미.png"));
                    Render.drawTexture( resultX - 17f/3f/2f, resultY - 17f/3f/2f, 17f/3f, 17f/3f, 0, 0, 1, 1, 5, 1);

                }
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                {
                    float circleRotateRadius = 206f/3f;
                    float degrees = (float) ((0 * 3.6f - 120f) * Math.PI / 180.0f);
                    float resultX = (float) (scaleWidth - stats_background_width/2f - 25f + 70f * Math.cos(degrees));
                    float resultY = (float) (scaleHeight - stats_background_height/2f + 1.0f + 70f * Math.sin(degrees));
                    GlStateManager.translate(resultX, resultY, 10); // 중심점을 텍스처의 가운데로 이동
                    GlStateManager.rotate(-35, 0.0f, 0.0f, 1f); // 회전 적용
                    GlStateManager.translate(-(resultX), -(resultY), 10);
                    GlStateManager.scale(0.75, 0.75, 0.75);
                    minecraft.fontRenderer.drawString("100", (resultX - 5)/0.75f, (resultY + 5)/0.75f, -1, false);

                }
                GlStateManager.popMatrix();


                float stat_icon_width = 14f/3f;
                float stat_icon_height = 49f/3f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\stat_icon.png"));
                Render.drawTexture(scaleWidth - stat_icon_width - 60f, scaleHeight - stat_icon_height - 68f, stat_icon_width, stat_icon_height, 0, 0, 1, 1, 5, 1);



                // 3600 * (current / 5400)

                float timerPer = (variable.m_gameMin * 60 + variable.m_gameSec) / 5400f;


                float timer_width = 227f/3f;
                float timer_height = 235f/3f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\타이머.png"));
                Render.drawTexture(scaleWidth - timer_width - 24f, scaleHeight - timer_height - 16f, timer_width, timer_height, 0, 0, 1, 1, 5, 1);


                float secPinWidth = 244f/3f;
                float secPinHeight = 17/3f;
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\sec_pin.png"));
                Render.drawRotateTexture2(-90 + (360f - (360f * timerPer)), scaleWidth - secPinWidth - 21.5f, scaleHeight - secPinHeight - 52f, secPinWidth, secPinHeight, 0, 0, 1, 1, 5, 1);

                
                float timerMiddle_width = 18f/3f;
                float timerMiddle_height = 18f/3f;

                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\ui\\stats\\타이머중심.png"));
                Render.drawTexture(scaleWidth - timerMiddle_width - 59f, scaleHeight - timerMiddle_height - 52.5f, timerMiddle_width, timerMiddle_height, 0, 0, 1, 1, 50, 1);



            }


        }

        String battery = "green";

        //배터리 게이지를 표기해주는 부분 배터리 퍼센트에 따라 색깔 변경
        if(variable.currentBattery >= 70)
            battery = "green";

        else if (variable.currentBattery >= 40)
            battery = "yellow";
        else
            battery = "red";

        float battery_Width = 22/3f;
        float battery_Height = 9/3f;

        GlStateManager.pushMatrix();
        {
            minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\phone\\"+battery+".png"));
            Render.drawXLinearTexture(scaleWidth - battery_Width - 33.5f, scaleHeight - battery_Height - 105.5f, battery_Width, battery_Height, variable.currentBattery, 1, 10 );


        }
        GlStateManager.popMatrix();


        if(minecraft.player.isSpectator())
            return;

        float widgetsWidth = 740f/ 3f;
        float widgetsHeight = 112f/3f;

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\widgets.png"));
        drawTexture(scaleWidth/2f - widgetsWidth/2f, scaleHeight - widgetsHeight, widgetsWidth, widgetsHeight, 0, 0, 1, 1, 1,1f);

        float hotbarSelectWidth = 96f/3f;


        int selectSlot = minecraft.player.inventory.currentItem;

        float hotbarSelectCorrection = 0;
        if(selectSlot==1)
            hotbarSelectCorrection = 27f;
        else if(selectSlot==2)
            hotbarSelectCorrection = 27f;
        else if(selectSlot==3)
            hotbarSelectCorrection = 27f;
        else if(selectSlot==4)
            hotbarSelectCorrection = 27f;
        else if(selectSlot==5)
            hotbarSelectCorrection = 26.8f;
        else if(selectSlot==6)
            hotbarSelectCorrection = 26.8f;
        else if(selectSlot==7)
            hotbarSelectCorrection = 26.8f;
        else if(selectSlot==8)
            hotbarSelectCorrection = 26.8f;

        minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\right.png"));
        drawTexture(scaleWidth / 2.0F - hotbarSelectWidth/2f - 107 +  selectSlot * hotbarSelectCorrection , scaleHeight - hotbarSelectWidth - 1, hotbarSelectWidth, hotbarSelectWidth, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, 1.0F);

        ItemStack itemstack = minecraft.player.getHeldItemOffhand();
        EnumHandSide enumhandside = minecraft.player.getPrimaryHand().opposite();
        float yPos = 23f * 1f;

        if (!itemstack.isEmpty())
        {
            if (enumhandside == EnumHandSide.LEFT)
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\hud\\hud\\leat_w.png"));
                drawTexture(scaleWidth / 2.0F - hotbarSelectWidth/2f - 107 - 40 , scaleHeight - hotbarSelectWidth - 1, hotbarSelectWidth, hotbarSelectWidth, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, 1.0F);

                renderHotbarItem((scaleWidth / 2.0F - hotbarSelectWidth/2f - 107 - 32), (scaleHeight - yPos), partialTick, minecraft.player ,itemstack);
            }
        }






        for(int i = 0; i<9; i++)
        {
            if(i == 0)
            {
                renderHotbarItem((scaleWidth/2 - 115f), (scaleHeight - yPos), partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==1)
            {
                renderHotbarItem(scaleWidth/2 - 88f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==2)
            {
                renderHotbarItem(scaleWidth/2 - 61f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==3)
            {
                renderHotbarItem(scaleWidth/2 - 34f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==4)
            {
                renderHotbarItem(scaleWidth/2 - 7,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==5)
            {
                renderHotbarItem(scaleWidth/2 + 19f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==6)
            {
                renderHotbarItem(scaleWidth/2 + 45f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==7)
            {
                renderHotbarItem(scaleWidth/2 + 72f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }
            else if(i==8)
            {
                renderHotbarItem(scaleWidth/2 + 99f,scaleHeight - yPos, partialTick, minecraft.player, minecraft.player.inventory.mainInventory.get(i));
            }

        }

        String loc = "X " + minecraft.player.getPosition().getX() + "  Y " + minecraft.player.getPosition().getY() + "  Z " + minecraft.player.getPosition().getZ();

        float stringSize = minecraft.fontRenderer.getStringWidth(loc) * 1f;


        GlStateManager.pushMatrix();
        {
            float scale = 0.85f;
            GlStateManager.scale(scale, scale, 1f);
            GlStateManager.translate(0f, 0f, 10);
            minecraft.fontRenderer.drawString(loc, (scaleWidth/2 - stringSize/2f + 6) / scale, (scaleHeight - widgetsHeight - 10) / scale, -1, true);
        }
        GlStateManager.popMatrix();
    }

    public float easeInOutBack(float x)
    {
        float c1 = 1.70158f;
        float c2 = c1 * 1.525f;

        return (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2f);
    }

    protected void renderHotbar(ScaledResolution sr, float partialTicks, float z)
    {
        if (this.minecraft.getRenderViewEntity() instanceof EntityPlayer)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer)minecraft.getRenderViewEntity();
            ItemStack itemstack = entityplayer.getHeldItemOffhand();
            EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
            int i = sr.getScaledWidth() / 2;
            float f = z;
            int j = 182;
            int k = 91;

            minecraft.ingameGUI.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
            minecraft.ingameGUI.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);

            if (!itemstack.isEmpty())
            {
                if (enumhandside == EnumHandSide.LEFT)
                {
                    minecraft.ingameGUI.drawTexturedModalRect(i - 91 - 29, sr.getScaledHeight() - 23, 24, 22, 29, 24);
                }
                else
                {
                    minecraft.ingameGUI.drawTexturedModalRect(i + 91, sr.getScaledHeight() - 23, 53, 22, 29, 24);
                }
            }

            z = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();

            for (int l = 0; l < 9; ++l)
            {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, entityplayer.inventory.mainInventory.get(l));
            }

            if (!itemstack.isEmpty())
            {
                int l1 = sr.getScaledHeight() - 16 - 3;

                if (enumhandside == EnumHandSide.LEFT)
                {
                    this.renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
                }
                else
                {
                    this.renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
                }
            }

            if (minecraft.gameSettings.attackIndicator == 2)
            {
                float f1 = minecraft.player.getCooledAttackStrength(0.0F);

                if (f1 < 1.0F)
                {
                    int i2 = sr.getScaledHeight() - 20;
                    int j2 = i + 91 + 6;

                    if (enumhandside == EnumHandSide.RIGHT)
                    {
                        j2 = i - 91 - 22;
                    }

                    minecraft.getTextureManager().bindTexture(Gui.ICONS);
                    int k1 = (int)(f1 * 19.0F);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.ingameGUI.drawTexturedModalRect(j2, i2, 0, 94, 18, 18);
                    minecraft.ingameGUI.drawTexturedModalRect(j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    protected void renderHotbarItem(float x, float y, float tick, EntityPlayer player, ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            float f = (float)stack.getAnimationsToGo() - tick;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float)(x + 8), (float)(y + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
            }
            RenderHelper.enableGUIStandardItemLighting();
            minecraft.getRenderItem().renderItemAndEffectIntoGUI(player, stack, (int) x, (int) y);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 1);
            minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, stack, (int) x, (int) y);
            GlStateManager.popMatrix();

            RenderHelper.disableStandardItemLighting();
        }
    }


    @SubscribeEvent
    public void ReciveToPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
    {

    }


    public void registerCustomModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));


    }

    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

    }
    public void drawStringScaleResizeByRightWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize/2f)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x +stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }




    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(u, v).endVertex();
            bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
            bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
            bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float r, float g, float b)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(r, g, b, alpha);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(u, v).endVertex();
            bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
            bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
            bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();
        this.minecraft.renderEngine.bindTexture(this.minecraft.player.getLocationSkin());


        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 5f, 1f);
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(int id, float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();

        GL11.glBindTexture(3553, id);

        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 64, 64, 0, 0, 1, 1, 1, 1);
        //drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 5f, 1f);
        GlStateManager.popMatrix();
    }
    private void drawSkinHead(int id, float x, float y, float z, double scale, float r, float g, float b)
    {
        GlStateManager.pushMatrix();
        {
            GL11.glBindTexture(3553, id);

            GlStateManager.scale(scale, scale, 1.0D);
            x = x / (float)scale;
            y = y / (float)scale;
            drawTexture(x, y, 64, 64, 0, 0, 1, 1, 1, 1, r, g, b);
            Tessellator t = Tessellator.getInstance();
            BufferBuilder bb = t.getBuffer();

            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(r, g, b, 1f);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(0, 0).endVertex();
            bb.pos(x, y + 64, z).tex(0, 1f).endVertex();
            bb.pos(x + 64, y + 64, z).tex(1, 1).endVertex();
            bb.pos(x + 64, y, z).tex(1, 0).endVertex();
            t.draw();
            GlStateManager.disableBlend();
            //drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 5f, 1f);
        }
        GlStateManager.popMatrix();
    }


    private void drawYLinearTexture(float x, float y, float xSize, float ySize, float endV, float alpha, float z)
    {
        endV = endV * 0.01f;
        endV = 1f - endV;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(x, y + (ySize*endV), z).tex(0.0d,endV).endVertex();
            bufferbuilder.pos(x, y+ySize, z).tex(0.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize, y+ySize, z).tex(1.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize, y+ (ySize*endV), z).tex(1.0d,endV).endVertex();

            //bufferbuilder.pos(x, y + ySize * endU, zLevel).tex(1.0d, endU).endVertex();
            //bufferbuilder.pos(x, y + ySize * endU, zLevel).tex(0.0d, endU).endVertex();

            tessellator.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private void drawXLinearTexture(float x, float y, float xSize, float ySize, float endU, float alpha, float z)
    {
        endU = endU * 0.01f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(x, y, z).tex(0.0d,0.0d).endVertex();
            bufferbuilder.pos(x, y+ySize, z).tex(0.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize * endU, y+ySize, z).tex(endU,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize * endU, y, z).tex(endU,0.0d).endVertex();

            tessellator.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }


}
