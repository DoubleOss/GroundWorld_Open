package com.doubleos.gw.media;

import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffmpeg.*;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MCMediaPlayer {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private FFmpegResultFuture ffmpegFuture;
	private Path ffmpegPath;
	private Path videoPath;
	private int width;
	private int height;
	
	private long position;
	public long startTime;
	private boolean paused;
	private boolean finished;
	private float volume;
	
	private ByteBuffer renderBuffer;
	private ByteBuffer updateBuffer;
	
	public float audioFrameRate;
	public float videoFrameRate;
	public long duration;
	
	private AudioUpdateRunnable audioRunnable;
	private VideoUpdateRunnable videoRunnable;
	private BlockingQueue<byte[]> audioQueue;
	private BlockingQueue<byte[]> videoQueue;

	private SourceDataLine line;

	private Path ffprobePath;

	FFprobeResult m_result;
	
	public MCMediaPlayer(Path ffprobePath, Path ffmpegPath, Path videoPath) {
		this.ffmpegPath = ffmpegPath;
		this.videoPath = videoPath;

		this.ffprobePath = ffprobePath;
		
		audioFrameRate = 0;
		videoFrameRate = 0;
		duration = 0L;
		line = null;
		
		m_result = new FFprobe(ffprobePath)
				.setShowStreams(true)
				.setInput(videoPath)
				.setLogLevel(LogLevel.FATAL)
				.execute();


		
		for(com.github.kokorin.jaffree.ffprobe.Stream stream : m_result.getStreams()) {
			if(stream.getCodecType() == StreamType.AUDIO) {
				audioFrameRate = stream.getSampleRate() / 1000.0F;
				
				// pcm_s16be
				AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, stream.getSampleRate(), 16, stream.getChannels(), (16 * stream.getChannels()) / 8, stream.getSampleRate(), true);
				try {
					line = AudioSystem.getSourceDataLine(audioFormat);
					line.open(audioFormat);
				} catch(LineUnavailableException e) {
					LOGGER.error("Couldn't open the SourceDataLine.");
				}
			} else if(stream.getCodecType() == StreamType.VIDEO) {
				width = stream.getWidth();
				height = stream.getHeight();
				videoFrameRate = stream.getAvgFrameRate().floatValue();
				duration = (long) (stream.getDuration() * 1000.0F);
			}
		}

		position = 0L;
		startTime = 0L;
		paused = false;
		finished = false;
		volume = 1.0F;
		
		renderBuffer = BufferUtils.createByteBuffer(width * height * 4);
		updateBuffer = BufferUtils.createByteBuffer(width * height * 4);
		
		audioRunnable = null;
		videoRunnable = null;
		
		audioQueue = null;
		videoQueue = null;
		if(audioFrameRate != 0) {
			audioQueue = new ArrayBlockingQueue<>((int) (audioFrameRate * 3.0F));
		}
		if(videoFrameRate != 0) {
			videoQueue = new ArrayBlockingQueue<>((int) (videoFrameRate * 3.0F));
		}
	}

	public void reset()
	{
		this.ffmpegPath = ffmpegPath;
		this.videoPath = videoPath;

		audioFrameRate = 0;
		videoFrameRate = 0;
		duration = 0L;
		line = null;


		for(com.github.kokorin.jaffree.ffprobe.Stream stream : m_result.getStreams()) {
			if(stream.getCodecType() == StreamType.AUDIO) {
				audioFrameRate = stream.getSampleRate() / 1000.0F;

				// pcm_s16be
				AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, stream.getSampleRate(), 16, stream.getChannels(), (16 * stream.getChannels()) / 8, stream.getSampleRate(), true);
				try {
					line = AudioSystem.getSourceDataLine(audioFormat);
					line.open(audioFormat);
				} catch(LineUnavailableException e) {
					LOGGER.error("Couldn't open the SourceDataLine.");
				}
			} else if(stream.getCodecType() == StreamType.VIDEO) {
				width = stream.getWidth();
				height = stream.getHeight();
				videoFrameRate = stream.getAvgFrameRate().floatValue();
				duration = (long) (stream.getDuration() * 1000.0F);
			}
		}

		position = 0L;
		startTime = 0L;
		paused = false;
		finished = false;

		renderBuffer = BufferUtils.createByteBuffer(width * height * 4);
		updateBuffer = BufferUtils.createByteBuffer(width * height * 4);

		audioRunnable = null;
		videoRunnable = null;

		audioQueue = null;
		videoQueue = null;
		if(audioFrameRate != 0) {
			audioQueue = new ArrayBlockingQueue<>((int) (audioFrameRate * 3.0F));
		}
		if(videoFrameRate != 0) {
			videoQueue = new ArrayBlockingQueue<>((int) (videoFrameRate * 3.0F));
		}
	}

	public void play() {
		if(ffmpegFuture != null)
			return;
		
		ffmpegFuture = new FFmpeg(ffmpegPath)
				.addInput(UrlInput.fromPath(videoPath).addArgument("-re"))
				.addOutput(FrameOutput.withConsumerAlpha(new MCFrameConsumer()))
				.setProgressListener(progress -> {})
				.setLogLevel(LogLevel.FATAL)
				.executeAsync();
		
		//.setFilter(StreamType.VIDEO, String.format("scale=%d:%d", width, height))
		//.setOutputListener(line -> {})
		
		if(audioFrameRate != 0) {
			line.start();
			audioRunnable = new AudioUpdateRunnable(audioFrameRate);
			Thread thread = new Thread(audioRunnable);
			thread.setDaemon(true);
			thread.start();
		}
		if(videoFrameRate != 0) {
			videoRunnable = new VideoUpdateRunnable(videoFrameRate);
			Thread thread = new Thread(videoRunnable);
			thread.setDaemon(true);
			thread.start();
		}

		
		position = 0L;
		startTime = System.currentTimeMillis() + 500l;
	}
	
	public void update() {
		if(!finished && ffmpegFuture != null && ffmpegFuture.isDone() && getPosition() >= duration) {
			stop();
		}
	}
	
	public void pause() {
		if(ffmpegFuture == null || ffmpegFuture.isDone())
			return;
		
		if(audioRunnable != null) {
			audioRunnable.stop();
			audioRunnable = null;
			audioQueue.clear();
			line.stop();
			line.flush();
		}
		if(videoRunnable != null) {
			videoRunnable.stop();
			videoRunnable = null;
			videoQueue.clear();
		}
		
		ffmpegFuture.graceStop();
		ffmpegFuture = null;
		
		long t = System.currentTimeMillis() - startTime;
		if(t > 0) {
			position += t;
		}
		paused = true;
	}
	
	public void resume() {
		if(ffmpegFuture != null || !paused)
			return;
		
		ffmpegFuture = new FFmpeg(ffmpegPath)
				.addInput(UrlInput.fromPath(videoPath).setPosition(position).addArgument("-re"))
				.addOutput(FrameOutput.withConsumerAlpha(new MCFrameConsumer()))
				.setProgressListener(progress -> {})
				.setLogLevel(LogLevel.FATAL)
				.executeAsync();
		
		//.setFilter(StreamType.VIDEO, String.format("scale=%d:%d", width, height))
		//.setOutputListener(line -> {})
		
		if(audioFrameRate != 0) {
			line.start();
			audioRunnable = new AudioUpdateRunnable(audioFrameRate);
			Thread thread = new Thread(audioRunnable);
			thread.setDaemon(true);
			thread.start();
		}
		if(videoFrameRate != 0) {
			videoRunnable = new VideoUpdateRunnable(videoFrameRate);
			Thread thread = new Thread(videoRunnable);
			thread.setDaemon(true);
			thread.start();
		}
		
		startTime = System.currentTimeMillis() + 500L;
		paused = false;
	}
	
	public void stop() {
		if(ffmpegFuture == null)
			return;
		
		if(audioRunnable != null) {
			audioRunnable.stop();
			audioRunnable = null;
			audioQueue.clear();
			line.stop();
			line.flush();
		}
		if(videoRunnable != null) {
			videoRunnable.stop();
			videoRunnable = null;
			videoQueue.clear();
		}
		
		if(!ffmpegFuture.isDone()) {
			ffmpegFuture.graceStop();
		}
		ffmpegFuture = null;
		
		position = 0L;
		paused = false;
		finished = true;
	}
	
	public void release() {
		line.stop();
		line.flush();
		line.close();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ByteBuffer getRenderBuffer() {
		renderBuffer.clear();
		return renderBuffer;
	}
	
	public long getPosition() {
		if(ffmpegFuture != null) {
			long t = System.currentTimeMillis() - startTime;
			return t > 0 ? position + t : position;
		} else {
			return position;
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public void skip(long millis) {
		if(ffmpegFuture == null || ffmpegFuture.isDone())
			return;
		
		pause();
		position += millis;
		if(position < 0) {
			position = 0;
		}
		resume();
	}
	
	private class AudioUpdateRunnable implements Runnable {
		
		private boolean running;
		private long delay;
		
		public AudioUpdateRunnable(float frameRate) {
			running = true;
			delay = (long) (1000.0F / frameRate);
		}
		
		@Override
		public void run() {
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					if(!running) {
						this.cancel();
						return;
					}
					
					if(audioQueue.size() > 0) {
						try {
							byte[] samples = audioQueue.take();
							
							// 16 bit BE samples
							int length = samples.length;
							for(int i = 0; i < length; i += 2) {
								short sample = (short) (((samples[i] & 0xFF) << 8) | (samples[i + 1] & 0xFF));
								sample = (short) (sample * volume);
								
							    samples[i] = (byte) (sample >> 8);
							    samples[i + 1] = (byte) sample;
							}
							
							line.write(samples, 0, samples.length);
						} catch (InterruptedException e) { }
					}
				}
			};
			timer.scheduleAtFixedRate(task, 500L, delay);
		}
		
		public void stop() {
			running = false;
		}
	}
	
	private class VideoUpdateRunnable implements Runnable {
		
		private boolean running;
		private long delay;
		
		public VideoUpdateRunnable(float frameRate) {
			running = true;
			delay = (long) (1000.0F / frameRate);
		}
		
		@Override
		public void run() {
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					if(!running) {
						this.cancel();
						return;
					}
					
					if(videoQueue.size() > 0) {
						try {
							updateBuffer.clear();
							updateBuffer.put(videoQueue.take());
							
							ByteBuffer temp = renderBuffer;
							renderBuffer = updateBuffer;
							updateBuffer = temp;
						} catch (Exception e) { }
					}
				}
			};
			timer.scheduleAtFixedRate(task, 500L, delay);
		}
		
		public void stop() {
			running = false;
		}
	}
	
	private class MCFrameConsumer implements FrameConsumer {

		private List<Stream> streams;
		
		@Override
		public void consumeStreams(List<Stream> streams) {
			this.streams = streams;
		}

		@Override
		public void consume(Frame frame) {
			if(frame == null) {
				return;
			}
			
			Stream stream = streams.get(frame.getStreamId());
			if(stream.getType() == Stream.Type.AUDIO) {
				try {
					while(audioQueue.remainingCapacity() < 1) {
						Thread.sleep(10L);
					}
					audioQueue.add(frame.getSamples());
				} catch(Exception e) { }
			} else if(stream.getType() == Stream.Type.VIDEO) {
				try {
					while(videoQueue.remainingCapacity() < 1) {
						Thread.sleep(10L);
					}
					videoQueue.add(frame.getData());
				} catch(Exception e) { }
			}
		}
	}
}
