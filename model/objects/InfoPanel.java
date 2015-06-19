package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.VolatileImage;

import model.SongHandler;
import model.drawObjects.CoinAnimation;
import model.gameState.PlayState;

public class InfoPanel {
	
	private static String totalHighscore = "000000";
	private int x, y;
	private VolatileImage infoPanel;
	private SongHandler sh;
	private String time;

	private CoinAnimation coinAnimation = new CoinAnimation(125, 300);
	private int tempComboScore;
	
	public InfoPanel(int x, int y, SongHandler sh){
		this.x = x;
		this.y = y;
		this.sh = sh;
		updateIPanel();
		generateInfoPanel();
	}
	
	public void updateIPanel() {		
		totalHighscore  = "" + PlayState.currentScore;
//		time = "";
//		long progress = (sh.getProgress() / 1000);
//		time = progress%1000 + time;
//		progress /= 1000;
//		time = progress%1000 + ":" + time;
//		progress /= 1000;
//		time = progress + ":" +  time;
		
		long progress = (sh.getProgress() / 1000);
		String millis = ((progress) % 1000) + "".length() > 3 ? ("" +((progress) % 1000)).substring(0, 2) : "" + ((progress) % 1000);
		String second = ((progress / 1000) % 60 + "").length() <= 1 ? "0" +((progress / 1000) % 60) : "" + ((progress / 1000) % 60);
		String minute = ((progress / (1000 * 60)) % 60 + "").length() <= 1 ? "0" +((progress / (1000 * 60)) % 60) : "" + ((progress / (1000 * 60)) % 60);
		time = minute + ":" + second + ":" + millis;
		generateInfoPanel();
	}
	
	private void generateInfoPanel(){
		infoPanel = Images.initVolatileImage(256, 1024, Transparency.OPAQUE);
		Graphics2D g2 = infoPanel.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);	
	    g2.setColor(Color.BLACK);
	    
	    
//	    g2.setColor(Color.GRAY);
	    GradientPaint gp = new GradientPaint(0, 0, new Color(245,245,245), 256, 1024, Color.WHITE);
	    g2.setPaint(gp);
		g2.fillRect(x, y, 256, 1024);
		
		g2.setColor(Color.BLACK);
		g2.drawRect(x, y, 255, 1023);
		
		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 30);
		g2.setFont(scoreFont);
		g2.setColor(Color.GREEN);
		g2.fillRect(25, 100, (int)(2 * PlayState.lifePoints), 30);
		
		g2.setColor(Color.ORANGE);
		g2.drawString("Score: " + totalHighscore, 25, 75);
		g2.drawRect(25, 100, 200, 30);
		g2.drawRect(25, 300, 200, 700);
		
		g2.drawString(sh.getCurrentSong().getTitle(), 25, 200);
		g2.drawString(sh.getCurrentSongInstance().getDifficulty(), 25, 230);
		g2.drawString(sh.getCurrentSong().getAuthor(), 25, 260);
		g2.drawString(time, 25, 290);

		if (!coinAnimation.areWeDoneYet()) {
			coinAnimation.draw(g2);
			// Score pas updaten wanneer coin klaar is met afspelen
			if (coinAnimation.isLastLoop())
				tempComboScore = PlayState.comboScore;
		} else
			tempComboScore = PlayState.comboScore;
		g2.drawString(sh.getCurrentSongInstance().getDifficulty(), 25, 230);
		g2.drawString(sh.getCurrentSong().getAuthor(), 25, 260);
		g2.drawString(time, 25, 290);
		
//	    GradientPaint gp = new GradientPaint(300, 0, new Color(200,200,255), 256, 1024, Color.WHITE);
//	    g2.setPaint(gp);
//		g2.fillRect(x, y, 256, 1024);
			
			
		g2.setColor(Color.YELLOW);		
		g2.fillRect(25, 1000 - 7 * tempComboScore, 200, 7 * tempComboScore);
		g2.dispose();
		infoPanel.createGraphics();
	}

	public void throwACoin() {
		coinAnimation.start();		// Teller weer op 0 zetten
	}
	
	public void draw(Graphics2D g2){
		g2.drawImage(infoPanel, 0, 0, 256,1024,null);
	}
	
	public static String getTotalHighscore()
	{
		return totalHighscore;
	}
}
