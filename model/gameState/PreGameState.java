package model.gameState;

import data.io.SQLConnector;
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

	private double 	index2 = 3,
					index = 3;

	private int grootte = 150;

	private SQLConnector sql;

	private BasicStroke s = new BasicStroke(20);
	
	private BufferedImage screenshot;
	
	public PreGameState(GameStateManager gsm, SongHandler sh, SQLConnector sql) {
		super(gsm, sh, sql);
		this.sql = sql;
		screenshot = Images.getImage(Images.ImageType.screenshot);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float factor) {

		index2 -= factor/1000;
		index = (double) Math.round(index2*1000)/1000;
		if(index <= 0){
			grootte = 150;
			index = 3;
			index2 = 3;
			gsm.setState(control.GameStateManager.State.PLAY_STATE);
		}
		
		if(grootte > 270)
			grootte = 150;
		grootte+=2;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(screenshot,0,0,1280,1024,null);
		Font textFont = new Font("OCR A Extended", Font.BOLD,grootte);		
		g2.setFont(textFont);
		g2.setStroke(s);
		g2.setColor(Color.RED);
		String text = "" + index;
		int width = g2.getFontMetrics().stringWidth(text);		
		g2.drawString(text,  325, 400);
		if(index < 1){
			text = "GO!!!";
		}
		else if (index < 2){
			text = "SET";
		}
		else{
			text = "READY";
		}
		width =  g2.getFontMetrics().stringWidth(text);
		g2.drawString(text, (256+1024/2)-width/2, 600);
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
