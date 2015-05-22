package control;

import java.util.ArrayList;
import java.util.List;

import model.gameState.GameState;
import model.gameState.MenuState;
import model.gameState.PlayState;

public class GameStateManager {

	private List<GameState> gamestates;
	public GameState currentState;
	private int index = 0;
	
	public GameStateManager(){
		gamestates = new ArrayList<GameState>();
		gamestates.add(new MenuState(this));
		gamestates.add(new PlayState(this));
		currentState = gamestates.get(0);
	}

	public void next() {
		index++;
		index %= gamestates.size();
	}
	
	public void update(){
		currentState = gamestates.get(index);
		currentState.update();
	}
}
