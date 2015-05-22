package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.GameView;

public class GameModel implements ActionListener{
	
	GameView view;
	Timer update;
	Player player;
	
	
	public GameModel(GameView view)
	{
		this.view = view;
		player = new Player(1920/2, 1080/2);
		update = new Timer(1000/10, this);
		update.start();
	}
	
	public void update(){
		player.update();
		view.setPlayer(player);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		update();		
	}
}
