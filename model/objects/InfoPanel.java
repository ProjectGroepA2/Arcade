package model.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import model.gameState.PlayState;

public class InfoPanel {
	
	public String totalHighscore = "XXXXXX";
	private String totalLifePoints = "";
	private int x, y;
	
	public InfoPanel(int x, int y){
		this.x = x;
		this.y = y;
		updateIPanel();
	}
	
	public void updateIPanel() {		
		totalHighscore  = "Score: " + PlayState.currentScore;
		totalLifePoints = "Hp:    " + PlayState.lifePoints;
	}
	
	public void draw(Graphics2D g2){
		g2.fillRect(x, y, 256, 1024);
		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 30);
		g2.setFont(scoreFont);
		g2.setColor(Color.ORANGE);
		g2.drawString(totalHighscore, 25, 150);
		g2.drawString(totalLifePoints, 25, 190);
		g2.setColor(Color.BLACK);
		
		
	}
}
