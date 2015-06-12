package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import model.SongHandler;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

import javax.swing.*;

public class TitleState extends GameState implements ActionListener {

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

	private Timer timer;

	int showInLoops = 1;
    
    int indexKast = 0;
    int xKast=0;
    
	public TitleState(GameStateManager gsm, SongHandler sh){
		super(gsm, sh);
		// Logica zit in ActionListener (dus actionPerformed())
		(timer = new Timer(1000/3, this)).start();
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
		// Scherm beelt wat kleurrijke rectangles op het scherm.
		if (showInLoops > 0) {
			g2.drawImage(background, 0, 0, 1280, 1024, null);
			createBackground();
		}
		if (showInLoops > 18) {
			// Code die de arcadekast en 'Press Start' toont.
			int image_x = ((frame / 6) % 6) * 49;
			g2.drawImage(pressStart.getSubimage(image_x, 0, 49, 26), 640 - 122, 512, 245, 130, null);

			xKast = indexKast / 10;
			xKast %= 4;
			g2.drawImage(kast.getSubimage(xKast * 300, 0, 300, 400), 100, 300, 300, 400, null);
		}
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

		if (showInLoops > 0) {
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHints(rh);
		}
		if (showInLoops > 2) {
			g2.setColor(new Color(1, 1, 1, 0.3f));
			g2.fillRect(0, 0, 1280, 1024);
		}
		if (showInLoops > 4) {
			g2.setColor(new Color(0, 1, 0, 0.7f));
			g2.fillRect(0, 0, 100, 1024);
			g2.fillRect(1180, 0, 100, 1024);
		}
		if (showInLoops > 6) {
			g2.setColor(new Color(1, 1, 0, 0.7f));
			g2.fillRect(100, 0, 100, 1024);
			g2.fillRect(1080, 0, 100, 1024);
		}
		if (showInLoops > 8) {
			g2.setColor(new Color(1, 0, 0, 0.7f));
			g2.fillRect(200, 0, 100, 1024);
			g2.fillRect(980, 0, 100, 1024);
		}
		if (showInLoops > 12) {
			g2.setPaint(gp);
			g2.fillRect(300, 0, 680, 1024);
		}
		if (showInLoops > 14) {
			g2.setFont(textFont);
			g2.setColor(Color.WHITE);
			g2.drawString("©2015 Team Hamtaro", 550, 1012);
		}
		if (showInLoops > 16) {
			g2.setColor(Color.RED);
			g2.setFont(textFont2);
			g2.drawString("Color", 385, 212);
			g2.drawString("Strike", 390, 342);
			g2.dispose();
		}
		if (showInLoops > 18) {
			timer.stop();
		}

		background.createGraphics();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		createBackground();
		showInLoops++;
	}

}
