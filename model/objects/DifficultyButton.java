package model.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DifficultyButton {
	
	private int x = 900;
	private int y;
	private String str;
	private Color color;
	

	public DifficultyButton(int y, String str, Color color) {
		this.y = y;
		this.str = str;
		this.color = color;
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(x, y, 300, 75);
		g2.setColor(Color.ORANGE);
		g2.drawRect(x, y, 300, 75);
		g2.drawRect(x-2, y-2, 304, 79);
		
		g2.setColor(color);
		Font textFont2 = new Font("OCR A Extended", Font.BOLD, 50);
		g2.setFont(textFont2);
		
		g2.drawString(str, x+5, y+50);
		
	}
	
}
