package model.gameState;

import java.awt.Graphics2D;

import model.SongHandler;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class MenuState extends GameState {

	public MenuState(GameStateManager gsm, SongHandler sh) {
		super(gsm, sh);
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
		g2.drawString("Press 1 to pick a song", 1280/2, 1024/2 + 40);

	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		
		switch(e.getButton().getButtonID()){
		case 0:
			gsm.setState(State.PLAY_STATE);
			break;
		case 1:
			gsm.setState(State.PICKSONG_STATE);
			break;
		}
		
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {}

}
