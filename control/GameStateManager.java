package control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.SongHandler;
import model.gameState.GameState;
import model.gameState.MenuState;
import model.gameState.PlayState;
import model.gameState.TitleState;
import control.button.Button;
import control.button.ButtonHandler;

public class GameStateManager {

	private List<GameState> gamestates;
	public GameState currentState;
	private int index = 0;
	public int fps;
	
	public enum State {
		TITLE_STATE,
		MENU_STATE,
		PLAY_STATE
	}
	
	public GameStateManager(SongHandler sh){
		gamestates = new ArrayList<GameState>();
		gamestates.add(new TitleState(this, sh));
		gamestates.add(new MenuState(this, sh));
		gamestates.add(new PlayState(this, sh));
		setState(State.TITLE_STATE);
	}
	
	public void setState(State st)
	{
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
		currentState.update(factor);
		fps = (int) (60/(factor/10));
	}
	
	public void init()
	{
		for (int i = 1; i < ButtonHandler.getButtons().size(); i++) {
			Button b = ButtonHandler.getButton(i);
			b.setColor(Color.BLACK);
		}
		currentState.init();
	}
}
