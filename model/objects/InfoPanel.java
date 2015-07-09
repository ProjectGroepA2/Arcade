package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;

import control.button.ButtonHandler;
import main.Window;
import model.SongHandler;
import model.drawObjects.CoinAnimation;
import model.gameState.PlayState;

public class InfoPanel {
	
	private static String totalHighscore = "000000";
	private int x, y;
	private VolatileImage infoPanel;
	private SongHandler sh;
	private String time;
	private int highscore = 0,
				tempComboScore = 0;

	private CoinAnimation coinAnimation = new CoinAnimation(27, 820);
	
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
		
		g2.setColor(Color.GREEN);
		g2.fillRect(25, 900, (int) (2 * PlayState.lifePoints), 30);
		
		g2.setColor(Color.YELLOW);		
		g2.fillRect(25, 950, (2 * PlayState.comboScore), 30);
		
		g2.setColor(Color.BLACK);
		
		g2.drawRect(25, 900, 200, 30);
		g2.drawRect(25, 950, 200, 30);
		
		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 35);
		g2.setFont(scoreFont);
		g2.drawString("Score: ", 25, 75);
		
		Font scoreFont2 = new Font("OCR A Extended", Font.BOLD, 25);
		g2.setFont(scoreFont2);
		int charsPerLine = 10;
		int linesNeeded = sh.getCurrentSong().getTitle().length() / charsPerLine + 1;  // 9 chars per regel
		g2.drawString("Title: ", 25, 225 - (linesNeeded) * 30);
		g2.drawString("Time: ", 25, 265);
		g2.drawString("Best: ", 25, 345);

		Font scoreFont3 = new Font("OCR A Extended", Font.BOLD, 35);
		g2.setFont(scoreFont3);
		
		g2.drawString("" + totalHighscore, 25, 105);

		// Aanpassen zodat hele titel zichtbaar is in infoP
		for (int count = 0; count < linesNeeded; count++)
			if (sh.getCurrentSong().getTitle().length() ==  count * charsPerLine + charsPerLine - 1)
			g2.drawString(	sh.getCurrentSong().getTitle().substring(count * charsPerLine,
							count * charsPerLine + charsPerLine - 1),
							25,
							235 - (linesNeeded - count) * 30);
			else
				g2.drawString(	sh.getCurrentSong().getTitle().substring(count * charsPerLine,
								sh.getCurrentSong().getTitle().length()),
								25,
								235 - (linesNeeded - 1 - count) * 30);

/*		g2.drawString(sh.getCurrentSong().getTitle(), 25, 215);*/
		g2.drawString(time, 25, 295);
		g2.drawString("" + highscore, 25, 375);


		if(!Window.ON_RASP){
			Ellipse2D oval1 = new Ellipse2D.Double(25, 650, 50, 50);
			g2.setColor(ButtonHandler.getButton(1).getColor());
			g2.fill(oval1);

			Ellipse2D oval2 = new Ellipse2D.Double(85, 650, 50, 50);
			g2.setColor(ButtonHandler.getButton(2).getColor());
			g2.fill(oval2);

			Ellipse2D oval3 = new Ellipse2D.Double(145, 650, 50, 50);
			g2.setColor(ButtonHandler.getButton(3).getColor());
			g2.fill(oval3);

			Ellipse2D oval4 = new Ellipse2D.Double(50, 710, 50, 50);
			g2.setColor(ButtonHandler.getButton(4).getColor());
			g2.fill(oval4);

			Ellipse2D oval5 = new Ellipse2D.Double(110, 710, 50, 50);
			g2.setColor(ButtonHandler.getButton(5).getColor());
			g2.fill(oval5);

			Ellipse2D oval6 = new Ellipse2D.Double(170, 710, 50, 50);
			g2.setColor(ButtonHandler.getButton(6).getColor());
			g2.fill(oval6);
		}

		if (!coinAnimation.areWeDoneYet()) {
			coinAnimation.draw(g2);
			// Score pas updaten wanneer coin in zijn laatste draw loop zit
			if (coinAnimation.isLastLoop())
				tempComboScore = PlayState.comboScore;
		} else
			tempComboScore = PlayState.comboScore;

		//* Score/Coin animation bar
		g2.setColor(Color.BLACK);
		g2.fillRect(25, 800, 2 * tempComboScore, 40);
		g2.drawRect(25, 800, 200, 40);

		g2.dispose();
		infoPanel.createGraphics();
	}
	
	public void draw(Graphics2D g2){
		g2.drawImage(infoPanel, 0, 0, 256, 1024,null);
	}
	
	public static String getTotalHighscore()
	{
		return totalHighscore;
	}


	public void throwACoin(Color c) {
		coinAnimation.start(c);		// Teller weer op 1 zetten
	}
}
