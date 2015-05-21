package model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.GameView;
import control.button.Button;
import control.button.ButtonHandler;

public class GameModel implements ActionListener{
	
	GameView view;
	Timer t;
	
	public GameModel(GameView view)
	{
		this.view = view;
		t = new Timer(2000, this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(Button b : ButtonHandler.getButtons())
		{
			b.setColor(new Color((int)(Math.random()*254+1), (int)(Math.random()*254+1), (int)(Math.random()*254+1)));
		}
	}
}
