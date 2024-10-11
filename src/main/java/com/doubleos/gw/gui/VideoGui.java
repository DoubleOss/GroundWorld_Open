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

	//영상 재생시 게임 사운드 카테고리 제어용
	private static SoundCategory[] soundCategoryArr = { SoundCategory.MASTER, SoundCategory.MUSIC,
			SoundCategory.RECORDS, SoundCategory.WEATHER, SoundCategory.BLOCKS, SoundCategory.HOSTILE,
			SoundCategory.NEUTRAL, SoundCategory.PLAYERS, SoundCategory.AMBIENT, SoundCategory.VOICE };
	
	public VideoGui(String videoName) throws Exception {
		this(videoName, 1.0F, false);
	}
	
	public VideoGui(String videoName, float volume, boolean skippable) throws Exception {
		Minecraft mc = Minecraft.getMinecraft();

		// 영상 재생시 FFmpeg 구성 파일 정상적으로 존재하는지 체크
		// 이후 재생을 위한 영상 파일 존재하는지 확인
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

	/*
	생성자가 먼저 호출 되면서 이후 InitGui 함수가 호출
	 */
	@Override
	public void initGui() {

		// initGui 호출시 그래픽 연산을 줄이기 위해 render 거리를 0으로 변경
		// 이후 영상 프레임과 게임 Fps를 맞추기 위해 최대 Fps를 영상과 똑같이 변경
		// 영상소리만 듣기 위해 모든 사운드 Level 0
		
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

		Mouse.setGrabbed(false); //마우스 포인터 끄기

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

		//Background 배경 검은색으로 변경
		drawRect(0, 0, width, height, 0xFF000000);
		//영상 프레임과 게임 프레임을 일치화
		mc.gameSettings.limitFramerate = (int)mediaPlayer.videoFrameRate;
		if(started) {
			if(texture == 0) {
				// OpenGl 텍스쳐 생성
				texture = GL11.glGenTextures();
				// 텍스쳐 Bind
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
				// 텍스쳐 화면 크기 보정
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				try {
					// 텍스쳐를 연산함 재생중인 영상의 프레임을 가져와 텍스쳐 작업
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, videoWidth, videoHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, mediaPlayer.getRenderBuffer());
				} catch(Exception e) { }
				// 텍스쳐 해제
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			} else {
				//이후 실시간으로 재생되는 영상의 텍스쳐를 업데이트
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