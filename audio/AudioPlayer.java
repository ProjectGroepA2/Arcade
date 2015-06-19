package audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer{

	private Player clip;
	private FileInputStream fis;
	private Song s;
	private Thread thread;

	public AudioPlayer() {
		clip = null;
	}

	public void setClip(Song s) {
		try {
			if(clip!=null)
				clip.close();
			this.s = s;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			fis = new FileInputStream(s.getAudio());
			clip = new Player(fis);
		} catch (JavaLayerException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Thread thread = new Thread(() -> {
				while (clip != null){
					try {
						clip.play();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
				}
			});
		 thread.start();
	}

	public void close() {
		if(clip != null)
		{
			clip.close();
			clip = null;

			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
