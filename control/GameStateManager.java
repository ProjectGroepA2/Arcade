package control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.gameState.EndState;
import model.gameState.GameState;
import model.gameState.HelpState;
import model.gameState.MenuState;
import model.gameState.PlayState;
import model.gameState.PreGameState;
import model.gameState.TitleState;
import control.button.Button;
import control.button.ButtonEvent;
import control.button.ButtonHandler;
import control.joystick.JoystickEvent;
import control.joystick.JoystickHandler;
import data.io.SQLConnector;

public class GameStateManager {

	private List<GameState> gamestates;
	public GameState currentState;
	private int index = 0;
	public int fps;
	private float timeOfNoAction = 0,maxTimeToHaveNoAction = 45000;
	
	public enum State {
		TITLE_STATE,
		MENU_STATE,
		HELP_STATE,
		PLAY_STATE,
		END_STATE,
		PRE_GAME_STATE
	}
	
	public GameStateManager(SongHandler sh, SQLConnector sql){
		gamestates = new ArrayList<GameState>();
		gamestates.add(new TitleState(this, sh, sql));
		gamestates.add(new MenuState(this, sh, sql));
		gamestates.add(new HelpState(this, sh, sql));
		gamestates.add(new PlayState(this, sh, sql));
		gamestates.add(new EndState(this, sh, sql));
		gamestates.add(new PreGameState(this,sh, sql));
		setState(State.TITLE_STATE);
	}
	
	public void setState(State st)	{
		if (st.ordinal() > 0 && !(currentState instanceof TitleState))
			currentState.stopAudio();

		currentState = gamestates.get(st.ordinal());
		init();
	}

	public void next() {
		index++;
		index %= gamestates.size();
		currentState = gamestates.get(index);
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
