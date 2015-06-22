package control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.SongHandler;
import model.gameState.*;
import control.button.Button;
import control.button.ButtonEvent;
import control.button.ButtonHandler;
import control.joystick.JoystickEvent;
import control.joystick.JoystickHandler;
import data.io.SQLConnector;

public class GameStateManager {

	private List<GameState> gamestates;

	public 	GameState 	currentState;
	private SongHandler sh;

	private int 	index 					= 0;
	public 	int 	fps;
	private float 	timeOfNoAction 			= 0,
					maxTimeToHaveNoAction 	= 45000;

	public enum State {
		TITLE_STATE,
		MENU_STATE,
		HELP_STATE,
		PLAY_STATE,
		GAMEOVER_STATE,
		PRE_GAME_STATE
	}
	
	public GameStateManager(SongHandler sh, SQLConnector sql){
		this.sh = sh;
		gamestates = new ArrayList<>();
		gamestates.add(new TitleState(this, sh, sql));
		gamestates.add(new MenuState(this, sh, sql));
		gamestates.add(new HelpState(this, sh, sql));
		gamestates.add(new PlayState(this, sh, sql));
		gamestates.add(new GameOverState(this, sh, sql));
		gamestates.add(new PreGameState(this, sh, sql));
		setState(State.TITLE_STATE);
	}

	public void setState(State st)
	{
		if (currentState != null)
			currentState.stopAudio();

		currentState = gamestates.get(st.ordinal());
		init();
	}
	
	public void update(float factor){
		timeOfNoAction += factor;	
		if(timeOfNoAction > maxTimeToHaveNoAction){
			setState(State.TITLE_STATE);
			timeOfNoAction = 0;
		}
		currentState.update(factor);
		fps = (int) (60/(factor/10));
	}
	
	public void init()
	{
		JoystickHandler.REPEAT = false;
		for (int i = 1; i < ButtonHandler.getButtons().size(); i++) {
			Button b = ButtonHandler.getButton(i);
			b.setColor(Color.BLACK);
		}
		currentState.init();
	}

	public void buttonPressed(ButtonEvent e) {
		timeOfNoAction = 0;
		currentState.buttonPressed(e);
	}

	public void buttonReleased(ButtonEvent e) {
		timeOfNoAction = 0;
		currentState.buttonReleased(e);
	}

	public void onJoystickMoved(JoystickEvent e) {
		timeOfNoAction = 0;
		currentState.onJoystickMoved(e);		
	}
}
