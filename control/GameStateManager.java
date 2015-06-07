package control;

import java.util.ArrayList;
import java.util.List;

import model.SongHandler;
import model.gameState.GameState;
import model.gameState.MenuState;
import model.gameState.PlayState;
import model.gameState.TitleState;

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
		currentState = gamestates.get(0);
	}
	
	public void setState(State st)
	{
		currentState = gamestates.get(st.ordinal());
		currentState.init();
	}

	public void next() {
		index++;
		index %= gamestates.size();
		currentState = gamestates.get(index);
		currentState.init();
	}
	
	public void update(float factor){		
		currentState.update(factor);
		fps = (int) (60/(factor/10));
	}
}
