package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.VolatileImage;

import control.button.ButtonHandler;
import main.Window;
import model.SongHandler;
import model.gameState.PlayState;

public class InfoPanel {
	
	private static String totalHighscore = "000000";
	private int x, y;
	private VolatileImage infoPanel;
	private SongHandler sh;
	private String time;
	private int highscore;
	
	public InfoPanel(int x, int y, SongHandler sh){
		this.x = x;
		this.y = y;
		this.sh = sh;
		updateIPanel();
		generateInfoPanel();
	}

	public void init(int highscore)
	{
		this.highscore = highscore;
		updateIPanel();
		generateInfoPanel();
	}
	public void updateIPanel() {		
		totalHighscore  = "" + PlayState.currentScore;
		
		long progress = (sh.getProgress() / 1000);
		String millis = (((progress) % 1000) + "").length() > 3 ? ("" +((progress) % 1000)).substring(0, 2) : "" + ((progress) % 1000);
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
	    
	    GradientPaint gp = new GradientPaint(0, 0, new Color(245,245,245), 256, 1024, Color.WHITE);
	    g2.setPaint(gp);
		
		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 30);
		g2.setFont(scoreFont);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(25, 900, (int)(2 * PlayState.lifePoints), 30);
		
		g2.setColor(Color.YELLOW);		
		g2.fillRect(25, 950, (int)(2 * PlayState.comboScore), 30);
		
		g2.setColor(Color.BLACK);
		
		g2.drawRect(25, 900, 200, 30);
		g2.drawRect(25, 950, 200, 30);
		
		g2.drawString("Score: " + totalHighscore, 25, 75);
		g2.drawString(sh.getCurrentSong().getTitle(), 25, 125);
		g2.drawString(sh.getCurrentSong().getAuthor(), 25, 175);
		g2.drawString("" + highscore, 25, 225);
		g2.drawString(time, 25, 275);
			

		if(!Window.ON_RASP){
			int width,height;
			width = g2.getFontMetrics().stringWidth("");
			height = g2.getFontMetrics().getHeight();
			
			
			Ellipse2D oval1 = new Ellipse2D.Double(15, 700, 50, 50);
			g2.setColor(ButtonHandler.getButton(1).getColor());
			g2.fill(oval1);
			
			Ellipse2D oval2 = new Ellipse2D.Double(75, 700, 50, 50);
			g2.setColor(ButtonHandler.getButton(2).getColor());
			g2.fill(oval2);
			
			Ellipse2D oval3 = new Ellipse2D.Double(135, 700, 50, 50);
			g2.setColor(ButtonHandler.getButton(3).getColor());
			g2.fill(oval3);
			
			Ellipse2D oval4 = new Ellipse2D.Double(40, 760, 50, 50);
			g2.setColor(ButtonHandler.getButton(4).getColor());
			g2.fill(oval4);
			
			Ellipse2D oval5 = new Ellipse2D.Double(100, 760, 50, 50);
			g2.setColor(ButtonHandler.getButton(5).getColor());
			g2.fill(oval5);
			
			Ellipse2D oval6 = new Ellipse2D.Double(160, 760, 50, 50);
			g2.setColor(ButtonHandler.getButton(6).getColor());
			g2.fill(oval6);
		}
				
//				g2.setColor(Color.BLACK);
//				width = g2.getFontMetrics().stringWidth(""+i);
//				g2.drawString(""+i, (int)oval.getCenterX()-width/2,(int)oval.getMaxY()+height);
		
		
		
		
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
