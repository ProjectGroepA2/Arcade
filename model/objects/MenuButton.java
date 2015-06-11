package model.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import model.GameModel;
import audio.Song;

public class MenuButton {

	private ArrayList<Color> colors;
	private int x, y;
	private boolean selected; 
	private Song song;
	Color color;
	
	public MenuButton(int x, int y, Color color, Song song){
		this.x = x;
		this.y = y;
		this.color = color;
		setSong(song);
		
	}
	
	

	public void draw(Graphics2D g2d){
			g2d.setColor(color);
			g2d.fillRect(x,y,880,50);
		
		if(selected){
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x, y, 880, 50);
		}

		//draw text
		g2d.setColor(Color.BLACK);
		Font textFont = new Font("OCR A Extended", Font.BOLD,60);
		g2d.setFont(textFont);
		g2d.drawString(song.getTitle(),  x+50, y+50);
	}
	
//	public void update(){
//		if(selected && fadecounter < 5){
//			x += 8;
//			y -= 8;
//			scalefactor += 0.04;
//			fadecounter++;
//			calculateButton();
//		}else if(!selected && fadecounter >0){
//			x += 8;
//			y += 8;
//			fadecounter--;
//			scalefactor -= 0.04;
//			calculateButton();
//		}
//	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected(){
		return selected;
	}
	public void setX(int x){
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
}
