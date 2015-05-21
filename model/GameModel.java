package model;

import view.GameView;

public class GameModel{
	
	GameView view;
	Player player;
	
	
	public GameModel(GameView view)
	{
		this.view = view;
		player = new Player(1920/2, 1080/2);
		
	}
	
	public void update(){
		player.update();
		view.setPlayer(player);
	}
}
