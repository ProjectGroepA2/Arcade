package model.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import audio.Song;

public class MenuButton {

	private ArrayList<Color> colors;
	private int x, y;
	private int xx,yy;
	private boolean selected; 
	private Song song;
	Color color;
	
	public MenuButton(int x, int y, Color color, Song song){
		this.x = x;
		this.y = y;
		this.color = color;
		setSong(song);
		
	}
	
	public MenuButton(int x, int y, int xx, int yy, Color color, Song song){
		this.x = x;
		this.y = y;
		this.color = color;
		this.yy = yy;
		this.xx = xx;
		setSong(song);
	}

	public void draw(Graphics2D g2d){

		
		if(selected){
			g2d.setColor(color.darker().darker().darker().darker());
			g2d.fillRect(x-10, y-10,xx+20,yy+20);
			g2d.setColor(color.darker().darker());
			g2d.fillRect(x-5, y-5,xx+10,yy+10);
			g2d.setColor(color);
			g2d.fillRect(x,y,xx,yy);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x-10, y-10, xx+20, yy+20);
			g2d.drawRect(x-5, y-5, xx+10, yy+10);
			g2d.drawRect(x, y, xx, yy);
		}
		else{
			g2d.setColor(color.darker().darker());
			g2d.fillRect(x-5, y-5,890,80);
			g2d.setColor(color);
			g2d.fillRect(x,y,880,70);
		}

		//draw text
		g2d.setColor(Color.BLACK);
		Font textFont = new Font("OCR A Extended", Font.BOLD,60);
		g2d.setFont(textFont);
		if(selected)
			g2d.drawString(song.getTitle(),  x+50, y+63);
		else
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
