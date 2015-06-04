package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import control.joystick.JoystickEvent;
import model.SongHandler;
import control.GameStateManager;
import control.button.ButtonEvent;

public class TitleState extends GameState {

    BufferedImage pressStart = Images.getImage(ImageType.pressstart);
    BufferedImage colorStrike = Images.getImage(ImageType.colorstrike);
    BufferedImage background = Images.getImage(ImageType.background);
    
    int index = 0;
    int varx = 0;
    int frame = 0;
    int maxFrames = 2560;

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
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,1280,1024);
		
		g2.translate(640, 512);
	    
		BufferedImage subImg2 = background.getSubimage(0, 0, 5120, 1024);
	    g2.drawImage(subImg2, -640 -((frame * 4) % maxFrames), -512, 5120, 1024, null);
//		
//		g2.setColor(Color.ORANGE);
//		g2.fillRect( -25*5 -1, - 18*5 -1, 49*5 + 1, 26*5 + 1);
//		g2.drawRect( -25*5 -3, - 18*5 -3, 49*5 + 4, 26*5 + 4);

		int image_x = ((frame / 6) % 6) * 49;
	    BufferedImage subImg = pressStart.getSubimage(image_x, 0, 49, 26);
	    g2.drawImage(subImg, - 25*5, - 18*5, 49*5, 26*5, null);
	    
	    g2.drawImage(colorStrike, -27*8 , -300, 54*8, 18*8, null);

	
		Font textFont = new Font("OCR A Extended", Font.BOLD, 15);
		g2.setFont(textFont);
		g2.setColor(Color.WHITE);
		g2.drawString("�2015 Team Hamtaro", - 18*5, 500);
		
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
