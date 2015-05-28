package model.objects;

import java.awt.Graphics2D;

public class InfoPanel {

	private int x, y;
	
	public InfoPanel(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g2){
		g2.fillRect(x, y, 256, 1024);
	}
}
