package model.objects;

import java.util.Date;

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

	public int getScore() {
		return score;
	}
	
	public long getTime(){
		return time;
	}
	public Date getDate(){
		return new Date(getTime());
	}
	
	public String getName(){
		return name;
	}
}
