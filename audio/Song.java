package audio;

import image.Images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Song {

	private String title;
	private String subtitle;
	private String author;
	private double sample_start;
	private double BPM;

	private File audio;
	private File background;
	private File banner;
	private File file;
	
	private BufferedImage backgroundImage;
	private BufferedImage bannerImage;

	private List<SongInstance> songs;

	public Song() {
		title = "";
		subtitle = "";
		author = "";
		sample_start = 0;
		BPM = 0.0;

		audio = null;
		background = null;
		banner = null;
		file = null;
		
		backgroundImage = null;
		bannerImage = null;

		songs = new ArrayList<SongInstance>();
	}

	public void addSongInstance(SongInstance s) {
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

	public double getSampleStart() {
		return sample_start;
	}

	public void setSampleStart(double sample_start) {
		this.sample_start = sample_start;
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
	
	public BufferedImage getBackgroundImage()
	{
		if(backgroundImage == null)
		{
			this.backgroundImage = Images.readImage(background);
		}
		return backgroundImage;
	}

	public File getBanner() {
		return banner;
	}
	
	public BufferedImage getBannerImage()
	{
		if(bannerImage == null)
		{
			this.bannerImage = Images.readImage(banner);
		}
		return bannerImage;
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
	
	public void close()
	{
	}
}
