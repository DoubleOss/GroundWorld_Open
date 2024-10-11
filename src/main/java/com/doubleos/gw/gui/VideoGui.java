package com.doubleos.gw.gui;

import com.doubleos.gw.Render;
import com.doubleos.gw.media.MCMediaPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@SideOnly(Side.CLIENT)
public class VideoGui extends GuiScreen {

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

	
	private float[] soundLevelArr;
	private static SoundCategory[] soundCategoryArr = { SoundCategory.MASTER, SoundCategory.MUSIC,
			SoundCategory.RECORDS, SoundCategory.WEATHER, SoundCategory.BLOCKS, SoundCategory.HOSTILE,
			SoundCategory.NEUTRAL, SoundCategory.PLAYERS, SoundCategory.AMBIENT, SoundCategory.VOICE };
	
	public VideoGui(String videoName) throws Exception {
		this(videoName, 1.0F, false);
	}
	
	public VideoGui(String videoName, float volume, boolean skippable) throws Exception {
		Minecraft mc = Minecraft.getMinecraft();
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
	
	@Override
	public void initGui() {

		volume_tick = 0;


		renderDistance = mc.gameSettings.renderDistanceChunks;
		renderFps = mc.gameSettings.limitFramerate;
		
		soundLevelArr = new float[10];
		for(int i = 0; i < 10; i ++) {
			soundLevelArr[i] = mc.gameSettings.getSoundLevel(soundCategoryArr[i]);
			mc.gameSettings.setSoundLevel(soundCategoryArr[i], 0);
		}
		
		mediaPlayer = new MCMediaPlayer(ffprobePath, ffmpegPath, videoPath);
		mediaPlayer.setVolume(volume / 100.0F);

		videoWidth = mediaPlayer.getWidth();
		videoHeight = mediaPlayer.getHeight();

		Mouse.setGrabbed(false);

	}

	@Override
	public void updateScreen() {
		if(!started)
		{
			mc.gameSettings.renderDistanceChunks = 0;
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
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{

		drawRect(0, 0, width, height, 0xFF000000);
		mc.gameSettings.limitFramerate = (int)mediaPlayer.videoFrameRate;
		if(started) {
			if(texture == 0) {
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
				GlStateManager.translate(0, 0, 0);
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
				mc.player.closeScreen();
			}

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
	
	@Override
	public void onGuiClosed() {
		mediaPlayer.stop();
		mediaPlayer.release();
		GL11.glDeleteTextures(texture);
		
		mc.gameSettings.renderDistanceChunks = renderDistance;
		mc.gameSettings.limitFramerate = renderFps;
		
		for(int i = 0; i < 10; i ++) {
			mc.gameSettings.setSoundLevel(soundCategoryArr[i], soundLevelArr[i]);
		}
	}

	// 0 = LMB, 1 = RMB, 2 = MMB
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
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
				mediaPlayer.setVolume(volume / 100.0F);
				volume_tick = 120;
			}

		}
	}

	@Override
	protected void keyTyped(char c, int key) {
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

}