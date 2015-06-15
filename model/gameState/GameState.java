package model.gameState;

import java.awt.Graphics2D;

import model.SongHandler;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;
import data.io.SQLConnector;

public abstract class GameState  {

	protected GameStateManager gsm;
	protected SongHandler sh;
	protected SQLConnector sql;

	public GameState(GameStateManager gsm, SongHandler sh, SQLConnector sql) {
		this.gsm = gsm;
		this.sh = sh;
		this.sql = sql;
	}
	
	public abstract void init();
	public abstract void update(float factor);
	public abstract void draw(Graphics2D g2);	
	public abstract void buttonPressed(ButtonEvent e);	
	public abstract void buttonReleased(ButtonEvent e);	
	public abstract void onJoystickMoved(JoystickEvent e);
}
