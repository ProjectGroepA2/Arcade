package control;

import java.util.ArrayList;
import java.util.List;

import model.SongHandler;
import model.gameState.GameState;
import model.gameState.MenuState;
import model.gameState.TitleState;
import model.gameState.PickSongState;
import model.gameState.PlayState;

public class GameStateManager {

	private List<GameState> gamestates;
	public GameState currentState;
	private int index = 0;
	
	public enum State {
		TITLE_STATE,
		MENU_STATE,
		PLAY_STATE,
		PICKSONG_STATE
	}
	
	public GameStateManager(SongHandler sh){
		gamestates = new ArrayList<GameState>();
		gamestates.add(new TitleState(this, sh));
		gamestates.add(new MenuState(this, sh));
		gamestates.add(new PlayState(this, sh));
		gamestates.add(new PickSongState(this, sh));
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
	
	public void update(){		
		currentState.update();
	}
}
