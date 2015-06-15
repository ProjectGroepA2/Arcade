package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import model.SongHandler;
import model.objects.InfoPanel;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class GameOverState extends GameState {

    BufferedImage gameOver = Images.getImage(ImageType.gameover);
    VolatileImage background;
	Font textFont = new Font("OCR A Extended", Font.BOLD, 70);
	
	
	private String endScore = "";
	
    int frame;

	public GameOverState(GameStateManager gsm, SongHandler sh) {
		super(gsm, sh);
		createBackground();
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float factor) {

        frame++;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1280, 1024, null);
		int image_x = ((frame / 5) % 5) * 40;
		g2.drawImage(gameOver.getSubimage(image_x, 0, 40, 26),  640-122, 512, 245, 130, null);
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		g2.setColor(new Color(1,1,1, 0.3f));
		g2.fillRect(0,0,1280,1024);
		
		g2.setColor(new Color(0,1,0, 0.7f));
		g2.fillRect(0,0,100,1024);
		g2.fillRect(1180,0,100,1024);
		
		g2.setColor(new Color(1,1,0, 0.7f));
		g2.fillRect(100,0,100,1024);
		g2.fillRect(1080,0,100,1024);
		
		g2.setColor(new Color(1,0,0, 0.7f));
		g2.fillRect(200,0,100,1024);
		g2.fillRect(980,0,100,1024);
		
		g2.setColor(Color.BLACK);
		g2.setFont(textFont);
		g2.drawString("High Score", 385, 212);
		g2.drawString(InfoPanel.getTotalHighscore(), 390, 342);
		g2.dispose();
		background.createGraphics();
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		
		switch(e.getButton().getButtonID()){
		case 0:
			gsm.setState(State.MENU_STATE);
			break;
		}
		
		
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {
	}
	
	public void createBackground(){
		background = Images.initVolatileImage(1280, 1024, Transparency.OPAQUE);
		Graphics2D g2 = background.createGraphics();
	}
}
