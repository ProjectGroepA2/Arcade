package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.GameView;
import control.GameStateManager;

public class GameModel implements ActionListener{
	
	GameView view;
	Timer update;
	
	GameStateManager gsm;
	
	public GameModel(GameView view,GameStateManager gsm)
	{
		this.view = view;	
		this.gsm = gsm;
		update = new Timer(1000/30, this);
		update.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gsm.update();		
	}
}
