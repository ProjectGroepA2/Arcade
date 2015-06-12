package model.objects;

import audio.SongInstance;

public class Highscore {
	public SongInstance si;
	public String name;
	public int score;
	public long time;
	
	public Highscore(SongInstance si, String name, int score, long time) {
		this.si = si;
		this.name = name;
		this.score = score;
		this.time = time;
	}
}
