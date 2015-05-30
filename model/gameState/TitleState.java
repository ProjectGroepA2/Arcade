package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class TitleState extends GameState {

    BufferedImage pressStart = Images.getImage(ImageType.pressstart);
    BufferedImage colorStrike = Images.getImage(ImageType.colorstrike);
    
    int index = 0;
    int varx = 0;
    int x,y,frame = 0,maxFrames = 5;

	public TitleState(GameStateManager gsm) {
		super(gsm);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		if(frame == maxFrames-1){
			 x = (index % 6)*49;
	         y = 0;
	
	         index++;
	         index %= 6;
		}
         frame++;
         frame %= (maxFrames);
	}

	@Override
	public void draw(Graphics2D g2) {
		
	    g2.setColor(Color.BLACK);    
		g2.fillRect(0, 0, 1280, 1024);
		
		g2.setColor(Color.ORANGE);
		g2.fillRect(1280/2 -120, 1024/2 - 80, 225, 90);
		g2.drawRect(1280/2 -122, 1024/2 - 82, 228, 93);
		
		g2.translate(1280/2, 1024/2);               
	        
	    BufferedImage subImg = pressStart.getSubimage(x, y, 49, 26);
	    g2.drawImage(subImg, varx - 26*5, 0 - 20*5, 49*5, 26*5, null);
	    
	    BufferedImage subImg2 = colorStrike;
	    g2.drawImage(subImg2,  0 -27*8 , 0 -300, 54*8, 18*8, null);
	
	    varx+=0;

		Font textFont = new Font("OCR A Extended", Font.BOLD, 15);
		g2.setFont(textFont);
		g2.setColor(Color.WHITE);
		g2.drawString("Copyright 2015 by Daniel Compagner", -180, 500);
		
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		
		switch(e.getButton().getButtonID()){
		case 0:
			gsm.next();
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
