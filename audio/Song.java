package audio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Song {
	
	private String title;
	private String subtitle;
	private String author;
	private String creator;
	private double BPM;
	
	private File audio;
	private File background;
	private File banner;
	private File file;
	
	private List<SongInstance> songs;
	
	public Song()
	{
		title = "";
		subtitle = "";
		author = "";
		creator = "";
		BPM = 0.0;
		
		audio = null;
		background = null;
		banner = null;
		file = null;
		
		songs = new ArrayList<SongInstance>();
	}
	
	public void addSongInstance(SongInstance s)
	{
		songs.add(s);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public double getBPM() {
		return BPM;
	}

	public void setBPM(double bPM) {
		BPM = bPM;
	}

	public File getAudio() {
		return audio;
	}

	public void setAudio(File audio) {
		this.audio = audio;
	}

	public File getBackground() {
		return background;
	}

	public void setBackground(File background) {
		this.background = background;
	}

	public File getBanner() {
		return banner;
	}

	public void setBanner(File banner) {
		this.banner = banner;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<SongInstance> getSongs() {
		return songs;
	}
}
