package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import model.gameState.PlayState;

public class InfoPanel {
	
	private String totalHighscore = "XXXXXX";
	private int lifePercent;
	private int upgradeScore = 0;
	private int x, y;
	private BufferedImage infoPanel;
	
	public InfoPanel(int x, int y){
		this.x = x;
		this.y = y;
		generateInfoPanel();
		updateIPanel();
	}
	
	public void updateIPanel() {		
		totalHighscore  = "Score: " + PlayState.currentScore;
		
		
		lifePercent =+ PlayState.lifePoints;
		
		if(0 <= PlayState.currentScore && PlayState.currentScore <=100) {			
			if(upgradeScore != PlayState.currentScore){
				upgradeScore = PlayState.currentScore;
				generateInfoPanel();
			}
		}
	}
	
	private void generateInfoPanel(){
		infoPanel = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = infoPanel.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);	
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
		infoPanel.createGraphics();
		infoPanel = Images.toCompatibleImage(infoPanel);
	}
	
	public void draw(Graphics2D g2){
		g2.drawImage(infoPanel, 0, 0, 1280,1024,null);
	}
}
