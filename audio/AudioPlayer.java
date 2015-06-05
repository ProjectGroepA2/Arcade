package audio;

import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer{

	private Player clip;
	private FileInputStream fis;

	public AudioPlayer() {
		clip = null;
	}

	public void setClip(Song s) {
		try {
			if(clip != null){
				clip.close();
			}
			fis = new FileInputStream(s.getAudio());
			clip = new Player(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (clip == null)
			return;
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(clip != null){
					try {
						clip.play();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
				}
			}}).start();
		
	}

	
	public void play(final int framePosition) {
		if (clip == null)
			return;	
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(clip != null){
					try {
						clip.play(framePosition);
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
				}
			}}).start();

	}


	public void close() {
		if(clip != null)
		{
			clip.close();
			clip = null;
			fis = null;
		}
	}
	
	public long getProgress()
	{
		if(clip != null)
			return clip.getPosition()*1000;
		else
			return 0L;
	}

}
