package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import model.SongHandler;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;
import data.io.SQLConnector;

public class TitleState extends GameState {

    BufferedImage pressStart = Images.getImage(ImageType.pressstart);
    BufferedImage colorStrike = Images.getImage(ImageType.colorstrike);
    BufferedImage kast = Images.getImage(ImageType.kast);
    VolatileImage background;
	Font textFont = new Font("OCR A Extended", Font.BOLD, 15);
	Font textFont2 = new Font("OCR A Extended", Font.BOLD, 130);
	GradientPaint gp = new GradientPaint(300, 0, new Color(0, 0, 1, 0.6f), 980, 1024, new Color(0, 0, 1, 0.2f));

	
    int index = 0;
    int varx = 0;
    int frame;

    
    int indexKast = 0;
    int xKast=0;
    
	public TitleState(GameStateManager gsm, SongHandler sh, SQLConnector sql){
		super(gsm, sh, sql);
		createBackground();
	}

	@Override
	public void init() {
		sh.play();
	}

	@Override
	public void update(float factor) {

        frame++;
        indexKast++;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1280, 1024, null);
		int image_x = ((frame / 6) % 6) * 49;
		g2.drawImage(pressStart.getSubimage(image_x, 0, 49, 26),  640-122, 512, 245, 130, null);
		
		
		
		xKast = indexKast/10;
		xKast%=4;
		//g2.drawImage(kast.getSubimage(xKast*300,0,300,400), 640-122,650,300,400,null);
		g2.drawImage(kast.getSubimage(xKast*300,0,300,400), 100,300,300,400,null);
	}

	@Override
	public void buttonPressed(ButtonEvent e) {

		switch (e.getButton().getButtonID()) {
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

	public void createBackground() {
		background = Images.initVolatileImage(1280, 1024, Transparency.OPAQUE);
		Graphics2D g2 = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.setColor(new Color(1, 1, 1, 0.3f));
		g2.fillRect(0, 0, 1280, 1024);

		g2.setColor(new Color(0, 1, 0, 0.7f));
		g2.fillRect(0, 0, 100, 1024);
		g2.fillRect(1180, 0, 100, 1024);

		g2.setColor(new Color(1, 1, 0, 0.7f));
		g2.fillRect(100, 0, 100, 1024);
		g2.fillRect(1080, 0, 100, 1024);

		g2.setColor(new Color(1, 0, 0, 0.7f));
		g2.fillRect(200, 0, 100, 1024);
		g2.fillRect(980, 0, 100, 1024);

		g2.setPaint(gp);
		g2.fillRect(300, 0, 680, 1024);

		g2.setFont(textFont);
		g2.setColor(Color.WHITE);
		g2.drawString("©2015 Team Hamtaro", 550, 1012);

		g2.setColor(Color.RED);
		g2.setFont(textFont2);
		g2.drawString("Color", 385, 212);
		g2.drawString("Strike", 390, 342);
		g2.dispose();
		background.createGraphics();
	}

}
