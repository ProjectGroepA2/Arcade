package model.gameState;

import image.Images;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.SongHandler;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class PreGameState extends GameState {

	double index2 = 2.25;
	double index = 2.25;
	double timer;
	
	BufferedImage screenshot;
	
	public PreGameState(GameStateManager gsm, SongHandler sh) {
		super(gsm, sh);
		screenshot = Images.getImage(Images.ImageType.screenshot);	
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float factor) {
//		timer = timer +factor/1000;
//		index -= timer;
		index2 -= factor/1000;
		index = (double) Math.round(index2*1000)/1000;
		if(index <= 0){
			gsm.setState(control.GameStateManager.State.PLAY_STATE);
		}
		
	}

	@Override
	public void draw(Graphics2D g2) {
		Font textFont = new Font("OCR A Extended", Font.BOLD,200);
		g2.setFont(textFont);
		g2.drawString("" + index,  325, 400);
			
		if(index < 0.75)
			g2.drawString("GO!!!",325,600);
		else if (index < 1.5)
			g2.drawString("SET", 450,600);
		else
			g2.drawString("READY", 325, 600);
		
	}
	

	@Override
	public void buttonPressed(ButtonEvent e) {
		
	}

	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}

	@Override
	public void onJoystickMoved(JoystickEvent e) {
		
	}

}
