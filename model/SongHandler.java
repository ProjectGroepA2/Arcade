package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.Window;
import audio.AudioPlayer;
import audio.Song;
import audio.SongInstance;
import audio.io.DirScanner;
import audio.sorting.SortALPHA;
import data.io.SQLConnector;

public class SongHandler {
	
	private List<Song> songs;
	
	private Song currentSong;
	private SongInstance currentSongInstance;
	private int currentIndex;
	SQLConnector sql;
	
	private File dir;
	
	private AudioPlayer p;

	public SongHandler(SQLConnector sql)
	{
		this.sql = sql;
		
		songs = new ArrayList<Song>();
		
		currentSong = null;
		currentSongInstance = null;
		currentIndex = 1;
		
		p = new AudioPlayer();
		
		if(Window.ON_RASP)
			dir = new File(System.getProperty( "user.home" ) + "/ColorStrike/Songs/");
		else
			dir = new File(System.getProperty( "user.home" ) + "/Documents/songs/");
		
		songs = DirScanner.scanDirectories(dir);
		
		Collections.sort(songs, new SortALPHA());
		
		sql.update(songs);
		
		updatePlayer();
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
		currentIndex = i;
		updatePlayer();
	}
	public void set(SongInstance si)
	{
		currentSongInstance = si;
	}
	
	
	private void updatePlayer()
	{
		if(currentIndex < 0)
			currentIndex = songs.size() + currentIndex;
		if(currentIndex >= songs.size())
			currentIndex = currentIndex - songs.size();
	
		currentSong = songs.get(currentIndex);
		currentSongInstance = currentSong.getSongs().get(0);
		
		p.close();
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
	public SongInstance getCurrentSongInstance()
	{
		return currentSongInstance;
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
	
	public void sort(Comparator<Song> sorter)
	{
		Collections.sort(songs, sorter);
	}
	
	
}
