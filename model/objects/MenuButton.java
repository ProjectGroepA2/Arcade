package model.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

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

		
		if(selected){
			g2d.setColor(color);
			GeneralPath path = new GeneralPath();
			path.moveTo(320, 540);
			path.lineTo(320, 590);
			path.lineTo(385, 565);
			
			path.closePath();
			g2d.fill(path);
			
			g2d.setColor(Color.BLACK);
			g2d.draw(path);
			
			g2d.setColor(color.darker().darker().darker().darker());
			g2d.fillRect(x-10, y-10,900,90);
		}
		g2d.setColor(color.darker().darker());
		g2d.fillRect(x-5, y-5,890,80);
		g2d.setColor(color);
		g2d.fillRect(x,y,880,70);
		
		if(selected){
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x-10, y-10, 900, 90);
			g2d.drawRect(x-5, y-5, 890, 80);
			g2d.drawRect(x, y, 880, 70);
			
		}

		//draw text
		g2d.setColor(Color.BLACK);
		Font textFont = new Font("OCR A Extended", Font.BOLD,60);
		g2d.setFont(textFont);
		g2d.drawString(song.getTitle(),  x+50, y+57);
	}
	
	
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
