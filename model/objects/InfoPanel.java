package model.objects;

import image.Images;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.VolatileImage;

import model.SongHandler;
import model.drawObjects.CoinAnimation;
import model.drawObjects.PointAnimation;
import model.gameState.PlayState;

public class InfoPanel {
	
	private static String totalHighscore = "000000";
	private int x, y;
	private VolatileImage infoPanel;
	private SongHandler sh;
	private String time;

	private int tempComboMulitplier;

	private CoinAnimation coinAnimation = new CoinAnimation(125, 300);


	public InfoPanel(int x, int y, SongHandler sh){
		this.x = x;
		this.y = y;
		this.sh = sh;
		updateIPanel();
		generateInfoPanel();
	}
	
	public void updateIPanel() {		
		totalHighscore  = "" + PlayState.currentScore;
		
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

		// Score balk
		g2.setColor(Color.BLACK);
		g2.drawRect(x, y, 255, 1023);

		Font scoreFont = new Font("OCR A Extended", Font.BOLD, 30);
		g2.setFont(scoreFont);
		g2.setColor(Color.GREEN);
		g2.fillRect(25, 100, (int)(2 * PlayState.lifePoints), 30);

		// Laatste punten in-/decrease tonen.
		if (PlayState.sinceScoreChanged < 60 && PlayState.sinceScoreChanged != 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1 - (PlayState.sinceScoreChanged * 0.015))));
			g2.setColor(Color.RED);
			g2.drawString("+" + PlayState.lastScoreChange, 185, 40);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}

		g2.setColor(Color.ORANGE);
		g2.drawString("Score: " + totalHighscore, 25, 75);

		g2.setColor(Color.BLACK);
		// BALK
		g2.drawRect(25, 100, 200, 30);
		g2.drawRect(25, 300, 200, 700);

		g2.drawString(sh.getCurrentSong().getTitle(), 25, 200);
		g2.drawString(sh.getCurrentSongInstance().getDifficulty(), 25, 230);
		g2.drawString(sh.getCurrentSong().getAuthor(), 25, 260);
		g2.drawString(time, 25, 290);

		// Controlen of coin animatie nodig is
		if (!coinAnimation.areWeDoneYet()) {
			coinAnimation.draw(g2);
			// Score pas updaten wanneer coin in zijn laatste draw loop zit
			if (coinAnimation.isLastLoop())
				tempComboMulitplier = PlayState.comboMulitplier;
		} else
			tempComboMulitplier = PlayState.comboMulitplier;

		g2.drawString(sh.getCurrentSongInstance().getDifficulty(), 25, 230);
		g2.drawString(sh.getCurrentSong().getAuthor(), 25, 260);
		g2.drawString(time, 25, 290);
			
		g2.setColor(Color.YELLOW);
		g2.fillRect(25, 1000 - tempComboMulitplier * 35, 200, 35 * tempComboMulitplier);
		g2.dispose();
		infoPanel.createGraphics();
	}

	public void throwACoin() {
		coinAnimation.start();		// Teller weer op 1 zetten
	}

	public void draw(Graphics2D g2){
		g2.drawImage(infoPanel, 0, 0, 256,1024,null);
	}
	
	public static String getTotalHighscore()
	{
		return totalHighscore;
	}
}
