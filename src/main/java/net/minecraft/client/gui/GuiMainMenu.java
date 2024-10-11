package net.minecraft.client.gui;


import com.doubleos.gw.Render;
import com.doubleos.gw.media.MCMediaPlayer;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
@SideOnly(Side.CLIENT)
public class GuiMainMenu extends GuiScreen
{

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private float minceraftRoll;
    private String splashText;
    private GuiButton buttonResetDemo;
    private float panoramaTimer;
    private DynamicTexture viewportTexture;
    private final Object threadLock = new Object();
    public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
    private int openGLWarning2Width;
    private int openGLWarning1Width;
    private int openGLWarningX1;
    private int openGLWarningY1;
    private int openGLWarningX2;
    private int openGLWarningY2;
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;
    private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
    private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[] {new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    private ResourceLocation backgroundTexture;
    private GuiButton realmsButton;
    private boolean hasCheckedForRealmsNotification;
    private GuiScreen realmsNotification;
    private int widthCopyright;
    private int widthCopyrightRest;
    private GuiButton modButton;
    private net.minecraftforge.client.gui.NotificationModUpdateScreen modUpdateNotification;

    private Path ffprobePath;
    private Path ffmpegPath;

    private MCMediaPlayer mediaPlayer;
    private Path videoPath;
    private int videoWidth, videoHeight;

    private int texture;

    private boolean skippable;

    private int volume;
    private int volume_tick;
    private int skip;
    private int skip_tick;

    private boolean started;
    private int delay;

    private int renderDistance;

    private int renderFps;

    public int m_width = 0;
    public int m_height = 0;




    private float[] soundLevelArr;
    private static SoundCategory[] soundCategoryArr = { SoundCategory.MASTER, SoundCategory.MUSIC,
            SoundCategory.RECORDS, SoundCategory.WEATHER, SoundCategory.BLOCKS, SoundCategory.HOSTILE,
            SoundCategory.NEUTRAL, SoundCategory.PLAYERS, SoundCategory.AMBIENT, SoundCategory.VOICE };

    public GuiMainMenu() throws Exception
    {
        this("mainmenu.mp4", 0.5f, false);
    }

    public GuiMainMenu(String videoName, float volume, boolean skippable) throws Exception
    {
        Minecraft mc = Minecraft.getMinecraft();
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println(osName.indexOf("win"));
        if (osName.indexOf("win") >= 0)
        {
            File file = new File(mc.mcDataDir, "movie/" + videoName);
            if(!file.exists()) {
                mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "영상을 재생하지 못했습니다. (" + videoName + " 파일을 찾을 수 없습니다.)"));
                throw new Exception("VideoGui : Couldn't find the video file.");
            }

            File ffmpegFolder = new File(mc.mcDataDir, "/FFMPEG");
            if(!ffmpegFolder.exists()) {
                mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "영상을 재생하지 못했습니다. (영상 재생을 위한 FFMPEG 폴더를 찾을 수 없습니다.)"));
                throw new Exception("VideoGui : ffmpeg folder does not exist.");
            }

            ffprobePath = null;
            ffmpegPath = null;
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("win")) {
                ffprobePath = new File(mc.mcDataDir, "/FFMPEG/bin/ffprobe.exe").toPath();
                ffmpegPath = new File(mc.mcDataDir, "/FFMPEG/bin/ffmpeg.exe").toPath();
            }
            if(ffprobePath == null) {
                mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "영상을 재생하지 못했습니다. (영상 재생은 윈도우에서만 지원됩니다.)"));
                throw new Exception("VideoGui : Unsupported OS.");
            }

            videoPath = file.toPath();
            this.volume = (int) (volume * 100);
            this.skippable = skippable;

            started = false;
            delay = 0;
        }
        else
        {
            this.openGLWarning2 = MORE_INFO_TEXT;
            this.splashText = "missingno";
            IResource iresource = null;

            try
            {
                List<String> list = Lists.<String>newArrayList();
                iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
                String s;

                while ((s = bufferedreader.readLine()) != null)
                {
                    s = s.trim();

                    if (!s.isEmpty())
                    {
                        list.add(s);
                    }
                }

                if (!list.isEmpty())
                {
                    while (true)
                    {
                        this.splashText = list.get(RANDOM.nextInt(list.size()));

                        if (this.splashText.hashCode() != 125780783)
                        {
                            break;
                        }
                    }
                }
            }
            catch (IOException var8)
            {
                ;
            }
            finally
            {
                IOUtils.closeQuietly((Closeable)iresource);
            }

            this.minceraftRoll = RANDOM.nextFloat();
            this.openGLWarning1 = "";

            if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
            {
                this.openGLWarning1 = I18n.format("title.oldgl1");
                this.openGLWarning2 = I18n.format("title.oldgl2");
                this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
            }
        }


    }


    private boolean areRealmsNotificationsEnabled()
    {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.realmsNotification != null;
    }


    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui()
    {

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            mc.mouseHelper.grabMouseCursor();

            Mouse.setGrabbed(false);
            volume_tick = 0;

            if(mediaPlayer == null)
            {
                mediaPlayer = new MCMediaPlayer(ffprobePath, ffmpegPath, videoPath);
            }
            else
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                Minecraft mc = Minecraft.getMinecraft();
                File file = new File(mc.mcDataDir, "movie/" + "mainmenu.mp4");
                if(!file.exists()) {
                    mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "영상을 재생하지 못했습니다. (" + "mainmenu.mp4" + " 파일을 찾을 수 없습니다.)"));
                    try {
                        throw new Exception("VideoGui : Couldn't find the video file.");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                File ffmpegFolder = new File(mc.mcDataDir, "/FFMPEG");
                if(!ffmpegFolder.exists()) {
                    mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "영상을 재생하지 못했습니다. (영상 재생을 위한 FFMPEG 폴더를 찾을 수 없습니다.)"));
                    try {
                        throw new Exception("VideoGui : ffmpeg folder does not exist.");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                ffprobePath = null;
                ffmpegPath = null;
                String os = System.getProperty("os.name").toLowerCase();
                if(os.contains("win")) {
                    ffprobePath = new File(mc.mcDataDir, "/FFMPEG/bin/ffprobe.exe").toPath();
                    ffmpegPath = new File(mc.mcDataDir, "/FFMPEG/bin/ffmpeg.exe").toPath();
                }
                if(ffprobePath == null) {
                    mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "영상을 재생하지 못했습니다. (영상 재생은 윈도우에서만 지원됩니다.)"));
                    try {
                        throw new Exception("VideoGui : Unsupported OS.");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                videoPath = file.toPath();

                started = false;
                delay = 0;
                mediaPlayer = new MCMediaPlayer(ffprobePath, ffmpegPath, videoPath);
            }
            mediaPlayer.setVolume(volume / 100.0F);

            videoWidth = mediaPlayer.getWidth();
            videoHeight = mediaPlayer.getHeight();


            buttonList.add(new button(0, 0, 0, width, height, ""));
        }
        else
        {
            this.viewportTexture = new DynamicTexture(256, 256);
            this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
            this.widthCopyright = this.fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!");
            this.widthCopyrightRest = this.width - this.widthCopyright - 2;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
            {
                this.splashText = "Merry X-mas!";
            }
            else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
            {
                this.splashText = "Happy new year!";
            }
            else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
            {
                this.splashText = "OOoooOOOoooo! Spooky!";
            }

            int i = 24;
            int j = this.height / 4 + 48;

            if (this.mc.isDemo())
            {
                this.addDemoButtons(j, 24);
            }
            else
            {
                this.addSingleplayerMultiplayerButtons(j, 24);
            }

            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options")));
            this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit")));
            this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));

            synchronized (this.threadLock)
            {
                this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
                this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
                int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
                this.openGLWarningX1 = (this.width - k) / 2;
                this.openGLWarningY1 = (this.buttonList.get(0)).y - 24;
                this.openGLWarningX2 = this.openGLWarningX1 + k;
                this.openGLWarningY2 = this.openGLWarningY1 + 24;
            }

            this.mc.setConnectedToRealms(false);

            if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.hasCheckedForRealmsNotification)
            {
                RealmsBridge realmsbridge = new RealmsBridge();
                this.realmsNotification = realmsbridge.getNotificationScreen(this);
                this.hasCheckedForRealmsNotification = true;
            }

            if (this.areRealmsNotificationsEnabled())
            {
                this.realmsNotification.setGuiSize(this.width, this.height);
                this.realmsNotification.initGui();
            }
            modUpdateNotification = net.minecraftforge.client.gui.NotificationModUpdateScreen.init(this, modButton);
        }

    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer")));
        this.realmsButton = this.addButton(new GuiButton(14, this.width / 2 + 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("menu.online").replace("Minecraft", "").trim()));
        this.buttonList.add(modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods")));
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_)
    {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
        this.buttonResetDemo = this.addButton(new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo")));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null)
        {
            this.buttonResetDemo.enabled = false;
        }
    }


    @Override
    public void updateScreen()
    {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            if(!started)
            {
                started = true;
                mediaPlayer.play();
			/*
						if(delay < 10)
			{
				if(delay == 5)
				{
					mc.gameSettings.renderDistanceChunks = 0;
				}
				delay ++;
			}
			else {
				started = true;
				mediaPlayer.play();
			}
			 */

            }
        }
        else
        {
            if (this.areRealmsNotificationsEnabled())
            {
                this.realmsNotification.updateScreen();
            }
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float far)
    {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            if(mc.player != null)
            {
                this.onGuiClosed();
            }
            mc.gameSettings.limitFramerate = (int)mediaPlayer.videoFrameRate;
            if(started)
            {

                if(texture == 0)
                {
                    texture = GL11.glGenTextures();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                    try {
                        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, videoWidth, videoHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, mediaPlayer.getRenderBuffer());
                    } catch(Exception e) { }
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                } else {
                    GlStateManager.pushMatrix();
                    GlStateManager.disableLighting();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
                    try {
                        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, videoWidth, videoHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, mediaPlayer.getRenderBuffer());
                    } catch(Exception e) { }
                    Render.setColor(0xffffffff);
                    GlStateManager.translate(0, 0, 3);
                    Render.drawTexturedRect(0, 0, width, height);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                    GlStateManager.popMatrix();
                }

                if(!mediaPlayer.isFinished())
                {
                    mediaPlayer.update();
                }
                else
                {
                    //mc.player.closeScreen();
                    mediaPlayer.reset();
                    mediaPlayer.play();
                }

                buttonList.get(0).drawButton(mc, mouseX, mouseY, far);


                if(mediaPlayer.isPaused()) {
                    fontRenderer.drawStringWithShadow("일시정지됨", width - 48, height - 15, 0xffffffff);
                }

                if(volume_tick > 0) {
                    volume_tick --;
                    fontRenderer.drawStringWithShadow("볼륨 " + volume + "%", 7, height - 15, 0xffffffff);
                }

                if(skip_tick > 0) {
                    skip_tick --;
                    if(skip > 0) {
                        String s = String.format("%d초 ▶▶", skip);
                        fontRenderer.drawStringWithShadow(s, width - fontRenderer.getStringWidth(s) - 7, height / 2 - 15, 0xffffffff);
                    } else {
                        String s = String.format("◀◀ %d초", -skip);
                        fontRenderer.drawStringWithShadow(s, 7, height / 2 - 15, 0xffffffff);
                    }

                    if(skip_tick == 0) {
                        mediaPlayer.skip(skip * 1000L);
                        skip = 0;
                    }
                }
            }

            //super.drawScreen(mouseX, mouseY, f);
        }
        else
        {
            float partialTicks = far;
            this.panoramaTimer += partialTicks;
            GlStateManager.disableAlpha();
            this.renderSkybox(mouseX, mouseY, partialTicks);
            GlStateManager.enableAlpha();
            int i = 274;
            int j = this.width / 2 - 137;
            int k = 30;
            this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
            this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
            this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if ((double)this.minceraftRoll < 1.0E-4D)
            {
                this.drawTexturedModalRect(j + 0, 30, 0, 0, 99, 44);
                this.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
                this.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
                this.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
                this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
            }
            else
            {
                this.drawTexturedModalRect(j + 0, 30, 0, 0, 155, 44);
                this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
            }

            this.mc.getTextureManager().bindTexture(field_194400_H);
            drawModalRectWithCustomSizedTexture(j + 88, 67, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);

            this.splashText = net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, this.fontRenderer, this.width, this.height, this.splashText);

            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
            GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
            float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
            f = f * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
            GlStateManager.scale(f, f, f);
            this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, -256);
            GlStateManager.popMatrix();
            String s = "Minecraft 1.12.2";

            if (this.mc.isDemo())
            {
                s = s + " Demo";
            }
            else
            {
                s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
            }

            List<String> brandings = Lists.reverse(net.minecraftforge.fml.common.FMLCommonHandler.instance().getBrandings(true));
            for (int brdline = 0; brdline < brandings.size(); brdline++)
            {
                String brd = brandings.get(brdline);
                if (!com.google.common.base.Strings.isNullOrEmpty(brd))
                {
                    this.drawString(this.fontRenderer, brd, 2, this.height - ( 10 + brdline * (this.fontRenderer.FONT_HEIGHT + 1)), 16777215);
                }
            }

            this.drawString(this.fontRenderer, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, -1);

            if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height && Mouse.isInsideWindow())
            {
                drawRect(this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, -1);
            }

            if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty())
            {
                drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
                this.drawString(this.fontRenderer, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
                this.drawString(this.fontRenderer, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, (this.buttonList.get(0)).y - 12, -1);
            }

            super.drawScreen(mouseX, mouseY, partialTicks);

            if (this.areRealmsNotificationsEnabled())
            {
                this.realmsNotification.drawScreen(mouseX, mouseY, partialTicks);
            }
            modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
        }

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {


    }

    private void switchToRealms()
    {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            if (button.id == 0)
            {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }
        }
        else
        {
            if (button.id == 0)
            {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            }

            if (button.id == 5)
            {
                this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
            }

            if (button.id == 1)
            {
                this.mc.displayGuiScreen(new GuiWorldSelection(this));
            }

            if (button.id == 2)
            {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
            }

            if (button.id == 14 && this.realmsButton.visible)
            {
                this.switchToRealms();
            }

            if (button.id == 4)
            {
                this.mc.shutdown();
            }

            if (button.id == 6)
            {
                this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
            }

            if (button.id == 11)
            {
                this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
            }

            if (button.id == 12)
            {
                ISaveFormat isaveformat = this.mc.getSaveLoader();
                WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

                if (worldinfo != null)
                {
                    this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
                }
            }
        }

    }

    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 12)
        {
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop").invoke((Object)null);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                }
                catch (Throwable throwable)
                {
                    LOGGER.error("Couldn't open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    private void drawPanorama(int mouseX, int mouseY, float partialTicks)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = 8;

        for (int j = 0; j < 64; ++j)
        {
            GlStateManager.pushMatrix();
            float f = ((float)(j % 8) / 8.0F - 0.5F) / 64.0F;
            float f1 = ((float)(j / 8) / 8.0F - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, 0.0F);
            GlStateManager.rotate(MathHelper.sin(this.panoramaTimer / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-this.panoramaTimer * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; ++k)
            {
                GlStateManager.pushMatrix();

                if (k == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                this.mc.getTextureManager().bindTexture(TITLE_PANORAMA_PATHS[k]);
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                float f3 = 0.0F;
                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox()
    {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GlStateManager.glTexParameteri(3553, 10241, 9729);
        GlStateManager.glTexParameteri(3553, 10240, 9729);
        GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;

        for (int j = 0; j < 3; ++j)
        {
            float f = 1.0F / (float)(j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float)(j - 1) / 256.0F;
            bufferbuilder.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            bufferbuilder.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            bufferbuilder.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int mouseX, int mouseY, float partialTicks)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(mouseX, mouseY, partialTicks);
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f = 120.0F / (float)(this.width > this.height ? this.width : this.height);
        float f1 = (float)this.height * f / 256.0F;
        float f2 = (float)this.width * f / 256.0F;
        int i = this.width;
        int j = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos((double)i, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos((double)i, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }

    @Override
    public void onGuiClosed()
    {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            GL11.glDeleteTextures(texture);

        }
        else
        {
            if (this.realmsNotification != null)
            {
                this.realmsNotification.onGuiClosed();
            }
        }


    }

    // 0 = LMB, 1 = RMB, 2 = MMB
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {

        }
        else
        {
            synchronized (this.threadLock)
            {
                if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2)
                {
                    GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                    guiconfirmopenlink.disableSecurityWarning();
                    this.mc.displayGuiScreen(guiconfirmopenlink);
                }
            }

            if (this.areRealmsNotificationsEnabled())
            {
                this.realmsNotification.mouseClicked(mouseX, mouseY, mouseButton);
            }

            if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height)
            {
                this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
            }
        }


    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0) {
            if(started && !mediaPlayer.isFinished())
            {
                if (i > 1) {
                    if (volume >= 100)
                        return;
                    i = 1;
                } else if (i < -1)
                {
                    if (volume < 0)
                        return;
                    i = -1;
                }
                volume +=  i * 5;
                if(volume == -5)
                    volume = 0;
                mediaPlayer.setVolume(volume / 100.0F);
                volume_tick = 120;
            }

        }
    }

    @Override
    protected void keyTyped(char c, int key)
    {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0)
        {
            switch(key) {
                case Keyboard.KEY_ESCAPE:
                    mc.displayGuiScreen(null);
                    break;
                case Keyboard.KEY_SPACE:
                    if(started && !mediaPlayer.isFinished()) {
                        if(mediaPlayer.isPaused()) {
                            mediaPlayer.resume();
                        } else {
                            mediaPlayer.pause();
                        }
                    }
                    break;
                case Keyboard.KEY_UP:
                    if(started && !mediaPlayer.isFinished()) {
                        volume += 5;
                        if(volume > 100)
                            volume = 100;
                        mediaPlayer.setVolume(volume / 100.0F);
                        volume_tick = 120;
                    }
                    break;
                case Keyboard.KEY_DOWN:
                    if(started && !mediaPlayer.isFinished()) {
                        volume -= 5;
                        if(volume < 0)
                            volume = 0;
                        mediaPlayer.setVolume(volume / 100.0F);
                        volume_tick = 120;
                    }
                    break;
                case Keyboard.KEY_LEFT:
                    if(started && skippable && !mediaPlayer.isFinished() && skip <= 0) {
                        skip -= 5;
                        skip_tick = 60;
                    }
                    break;
                case Keyboard.KEY_RIGHT:
                    if(started && skippable && !mediaPlayer.isFinished() && skip >= 0) {
                        skip += 5;
                        skip_tick = 60;
                    }
                    break;
            }
        }

    }

}
