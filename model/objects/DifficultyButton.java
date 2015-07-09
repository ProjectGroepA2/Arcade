package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.VolatileImage;

public class DifficultyButton {
	
	private int x = 900;
	private int y;
	private String str;
	private Color color;
	private VolatileImage button;

	public DifficultyButton(int y, String str, Color color) {
		this.y = y;
		this.str = str;
		this.color = color;
		generateButton();
	}
	
	public void generateButton(){
		button = Images.initVolatileImage(305, 80, Transparency.OPAQUE);
		Graphics2D g2 = button.createGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 300, 75);
		g2.setColor(Color.ORANGE);
		
//		g2.drawRect(0, 0, 300, 75);
//		g2.drawRect(0-2, 0-2, 304, 79);
		g2.drawRect(0, 0, 304, 79);
		g2.drawRect(2, 2, 300, 75);
		g2.setColor(color);
		Font textFont2 = new Font("OCR A Extended", Font.BOLD, 50);
		g2.setFont(textFont2);
		
		g2.drawString(str, 0+5, 0+50);
		g2.dispose();
		button.createGraphics();
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(button, x, y, 305, 80, null);
	}
	
}
