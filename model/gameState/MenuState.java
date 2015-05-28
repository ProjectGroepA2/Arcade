package model.gameState;

import java.awt.Graphics2D;

import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class MenuState extends GameState {

	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawString("Press 0 to start the game", 1280/2, 1024/2);

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
