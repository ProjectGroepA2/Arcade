package model.objects.highscore;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HighscoreName {

	private final char[] characters = " abcdefghijklmnopqrstuvwxyz0123456789†".toUpperCase().toCharArray();
	private int spacing = 10;
	private HighscoreLetter[] letters;
	private Font font;
	private int index = 0;
	
	public HighscoreName(int x, int y,int nameLength,Font font) {
		this.font = font;
		letters = new HighscoreLetter[nameLength];
		int marge = (int) (Math.floor(nameLength/2)-1)*spacing;
		x -= (double)HighscoreLetter.charLength*(nameLength/2.0) + marge;
		
		System.out.println(x);
		for(int i = 0; i < letters.length; i++){			
				letters[i] = new HighscoreLetter(characters, x+(HighscoreLetter.charLength*i)+(spacing*i), y);		
		}
	}
	
	public void drawName(Graphics2D g2){
		g2.setFont(font);
		for(int i = 0; i < letters.length; i++){
			if(i == index){
				g2.setColor(Color.RED);
			}else{
				g2.setColor(Color.BLACK);
			}
			letters[i].draw(g2);			
		}
	}	
	
	public void left(){
		index--;
		if(index < 0){
			index = letters.length-1;
		}
	}
	
	public void right(){
		index++;
		index %= letters.length;
	}
	
	public void up(){
		letters[index].up();
	}
	
	public void down(){
		letters[index].down();
	}
	
	public String getName(){
		String name = "";
		for(int i = 0; i < letters.length; i++){
			name += letters[i].getCurrentString();
		}
		return name;
	}
}
