package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.VolatileImage;

import model.gameState.PlayState;

public class InfoPanel {
	
	private static String totalHighscore = "000000";
	private int x, y;
	private VolatileImage infoPanel;
	
	public InfoPanel(int x, int y){
		this.x = x;
		this.y = y;
		updateIPanel();
		generateInfoPanel();
	}
	
	public void updateIPanel() {		
		totalHighscore  = "" + PlayState.currentScore;
		
		generateInfoPanel();
	}
	
	private void generateInfoPanel(){
		infoPanel = Images.initVolatileImage(256, 1024, Transparency.OPAQUE);
		Graphics2D g2 = infoPanel.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);	
	    g2.setColor(Color.BLACK);
	    
		g2.fillRect(x, y, 256, 1024);
		
		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 30);
		g2.setFont(scoreFont);
		g2.setColor(Color.ORANGE);
		
		g2.drawString("Score: " + totalHighscore, 25, 75);
		g2.drawRect(25, 100, 200, 30);
		g2.drawRect(25, 300, 200, 700);
		g2.setColor(Color.GREEN);
		g2.fillRect(25, 100, (int)(2 * PlayState.lifePoints), 30);
		
		g2.setColor(Color.YELLOW);		
		g2.fillRect(25, 1000 - 7 * PlayState.comboScore, 200, 0 + 7 * PlayState.comboScore);
		g2.dispose();
		infoPanel.createGraphics();
	}
	
	public void draw(Graphics2D g2){
		g2.drawImage(infoPanel, 0, 0, 256,1024,null);
	}
	
	public static String getTotalHighscore()
	{
		return totalHighscore;
	}
}
