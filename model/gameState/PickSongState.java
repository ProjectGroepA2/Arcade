package model.gameState;

import java.awt.Graphics2D;

import model.SongHandler;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class PickSongState extends GameState {
	
	public PickSongState(GameStateManager gsm, SongHandler sh) {
		super(gsm, sh);
	}

	@Override
	public void init() {
		sh.set(0);
		sh.play(true);
	}

	@Override
	public void update(float factor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(sh.getCurrentSong().getBackgroundImage(), null, 0, 0);
		g2d.drawImage(sh.getCurrentSong().getBannerImage(), null, 400, 0);
	}

	@Override
	public void buttonPressed(ButtonEvent e) {}

	@Override
	public void buttonReleased(ButtonEvent e) {}

	@Override
	public void onJoystickMoved(JoystickEvent e) {
		switch (e.getJoystick().getPos()) {
		case LEFT:
			sh.previous();
			sh.play(true);
			break;
		case RIGHT:
			sh.next();
			sh.play(true);
			break;
		default:
			break;
		}
	}

}
