package model.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import model.gameState.PlayState;

public class InfoPanel {
	
	private String totalHighscore = "XXXXXX";
	private int lifePercent;
	private int upgradeScore = 0;
	private int x, y;
	
	public InfoPanel(int x, int y){
		this.x = x;
		this.y = y;
		updateIPanel();
	}
	
	public void updateIPanel() {		
		totalHighscore  = "Score: " + PlayState.currentScore;
		
		
		lifePercent =+ PlayState.lifePoints;
		
		if(0 <= PlayState.currentScore && PlayState.currentScore <=100) {			
			upgradeScore = PlayState.currentScore;
		}
		
		if(PlayState.currentScore == 100) {
		}
	}
	
	public void draw(Graphics2D g2){
		g2.setColor(Color.BLACK);
		g2.fillRect(x, y, 256, 1024);
		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 30);
		g2.setFont(scoreFont);
		g2.setColor(Color.ORANGE);
		g2.drawString(totalHighscore, 25, 75);
		g2.drawRect(25, 100, 200, 30);
		g2.drawRect(25, 300, 200, 700);
		g2.setColor(Color.GREEN);
		g2.fillRect(25, 100, 2 * lifePercent, 30);
		
		g2.setColor(Color.YELLOW);		
		g2.fillRect(25, 1000 - 7 * upgradeScore, 200, 0 + 7 * upgradeScore);
		
		g2.setColor(Color.BLACK);
	
		
		
		
	}
}
