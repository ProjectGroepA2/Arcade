package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javazoom.jl.player.Player;
import main.Window;
import audio.Song;
import audio.io.DirScanner;

public class SongHandler {
	
	private List<Song> songs;
	
	private Song currentSong;
	private int currentIndex;
	
	private File dir;

	public SongHandler()
	{
		songs = new ArrayList<Song>();
		
		currentSong = null;
		currentIndex = 0;
		
		if(Window.ON_RASP)
			dir = new File(System.getProperty( "user.home" ) + "/ColorStrike/Songs");
		else
			dir = new File("Songs");
		
		songs = DirScanner.scanDirectories(dir);
		
		currentSong = songs.get(currentIndex);
	}
	
	public void next()
	{
		currentIndex++;
		currentIndex%=songs.size();
		currentSong = songs.get(currentIndex);
		updatePlayer();
	}
	public void previous()
	{
		currentIndex--;
		currentIndex%=songs.size();
		currentSong = songs.get(currentIndex);
		updatePlayer();
	}
	
	private void updatePlayer()
	{
		
	}
}
