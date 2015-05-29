package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Window;
import audio.AudioPlayer;
import audio.Song;
import audio.io.DirScanner;

public class SongHandler {
	
	private List<Song> songs;
	
	private Song currentSong;
	private int currentIndex;
	
	private File dir;
	
	private AudioPlayer p;

	public SongHandler()
	{
		songs = new ArrayList<Song>();
		
		currentSong = null;
		currentIndex = -1;
		
		p = new AudioPlayer();
		
		if(Window.ON_RASP)
			dir = new File(System.getProperty( "user.home" ) + "/ColorStrike/Songs");
		else
			dir = new File("Songs");
		
		songs = DirScanner.scanDirectories(dir);
	}
	
	public void next()
	{
		currentIndex++;
		updatePlayer();
	}
	public void previous()
	{
		currentIndex--;
		updatePlayer();
	}
	public void set(int i)
	{
		if(currentIndex != i)
		{
			currentIndex = i;
			updatePlayer();
		}
	}
	
	
	private void updatePlayer()
	{
		if(currentIndex < 0)
			currentIndex = songs.size() + currentIndex;
		
		currentIndex%=songs.size();
		currentSong = songs.get(currentIndex);
		
		p.stop();
		p.setClip(currentSong);
	}
	
	public long getProgress()
	{
		return p.getProgress();
	}
	
	public List<Song> getSongs()
	{
		return songs;
	}
	public Song getCurrentSong()
	{
		return currentSong;
	}
	
	public void close()
	{
		p.close();
		for(Song s : songs)
		{
			s.close();
		}
	}
	
	public void play()
	{
		p.play();
	}
	
	public void play(boolean b)
	{
		if(b)
		{
			p.play((int)currentSong.getSampleStart()*10000);
		}
	}
	
	public void pause()
	{
		p.pause();
	}
	
	public void stop()
	{
		p.stop();
	}
}
