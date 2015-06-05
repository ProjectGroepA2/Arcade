package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import control.joystick.JoystickEvent;
import model.SongHandler;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;

public class TitleState extends GameState {

    BufferedImage pressStart = Images.getImage(ImageType.pressstart);
    BufferedImage colorStrike = Images.getImage(ImageType.colorstrike);
    BufferedImage background = Images.getImage(ImageType.background);
    
    int index = 0;
    int varx = 0;
    int frame;

	public TitleState(GameStateManager gsm, SongHandler sh){
		super(gsm, sh);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float factor) {

        frame++;
	}

	@Override
	public void draw(Graphics2D g2) {
		
		
		g2.setColor(new Color(1,1,1, 0.3f));
		g2.fillRect(0,0,1280,1024);
		
		g2.setColor(new Color(0,1,0, 0.7f));
		g2.fillRect(0,0,100,1024);
		
		g2.setColor(new Color(1,1,0, 0.7f));
		g2.fillRect(100,0,100,1024);
		
		g2.setColor(new Color(1,0,0, 0.7f));
		g2.fillRect(200,0,100,1024);
		
		g2.setColor(new Color(0,1,0, 0.7f));
		g2.fillRect(1180,0,100,1024);
		
		g2.setColor(new Color(1,1,0, 0.7f));
		g2.fillRect(1080,0,100,1024);
		
		g2.setColor(new Color(1,0,0, 0.7f));
		g2.fillRect(980,0,100,1024);
		
		GradientPaint gp = new GradientPaint(300, 0, new Color(0,0,1, 0.6f),980,1024 ,new Color(0,0,1, 0.2f));
		g2.setPaint(gp);
		g2.fillRect(300, 0, 680, 1024);
		
		g2.translate(640, 512);
		
		int image_x = ((frame / 6) % 6) * 49;
	    BufferedImage subImg = pressStart.getSubimage(image_x, 0, 49, 26);
	    g2.drawImage(subImg, - 25*5, 200, 49*5, 26*5, null);
	
		Font textFont = new Font("OCR A Extended", Font.BOLD, 15);
		g2.setFont(textFont);
		g2.setColor(Color.WHITE);
		g2.drawString("�2015 Team Hamtaro", - 18*5, 500);
		
		g2.setColor(Color.RED);
		Font textFont2 = new Font("OCR A Extended", Font.BOLD, 130);
		g2.setFont(textFont2);
		g2.drawString("Color", -215, -300);
		g2.drawString("Strike", -250, -170);
		
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		
		switch(e.getButton().getButtonID()){
		case 0:
			//gsm.next();
			gsm.setState(State.MENU_STATE);
			break;
		}
		
		
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

}
