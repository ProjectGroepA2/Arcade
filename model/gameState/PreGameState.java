package model.gameState;

import image.Images;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.SongHandler;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class PreGameState extends GameState {

	double index2 = 3;
	double index = 3;
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
		g2.drawImage(screenshot,0,0,1280,1024,null);
		Font textFont = new Font("OCR A Extended", Font.BOLD,200);
		BasicStroke s = new BasicStroke(20);
		g2.setFont(textFont);
		g2.setStroke(s);
		g2.setColor(Color.BLACK);
		g2.drawString("" + index,  325, 400);
		if(index < 1)
			g2.drawString("GO!!!",325,600);
		else if (index < 2)
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
