package audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

	private Clip clip;

	public AudioPlayer() {
		clip = null;
	}

	public void setClip(Song s) {
		try {
			if(clip!=null)
				clip.close();
			clip = null;
			AudioInputStream ais = s.getAudioStream();
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (clip == null)
			return;
		clip.start();
	}
	
	public void pause()
	{
		if (clip == null)
			return;
		if (clip.isRunning())
		{
			clip.stop();
		}
	}
	
	public void play(int framePosition) {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(framePosition);
		clip.start();
	}

	public void stop() {
		if (clip == null)
			return;
		if (clip.isRunning())
		{
			clip.stop();
			clip.setFramePosition(0);
		}
	}

	public void close() {
		stop();
		if(clip != null)
		{
			clip.close();
		}
	}
	
	public long getProgress()
	{
		if(clip != null)
			return clip.getMicrosecondPosition();
		else
			return 0L;
	}

}
