package model.objects.highscore;

import java.awt.Graphics2D;

public class HighscoreLetter {
	
	public static int charLength = 46;
	private char[] letters;
	private int x,y,index = 0;
		
	public HighscoreLetter(char[] letters, int x, int y) {
		super();
		this.letters = letters;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g2){
		charLength = g2.getFontMetrics().stringWidth(letters[index]+"");
		g2.drawString(letters[index]+"", x, y);
		g2.drawLine(x, y, x+charLength, y);
	}
	
	public void up(){
		index++;
		index %= letters.length;
	}
	
	public void down(){
		index--;
		if(index < 0){
			index = letters.length-1;
		}
	}
	
	public String getCurrentString(){
		return letters[index]+"";
	}
}
