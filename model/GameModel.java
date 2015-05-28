package model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import control.GameStateManager;
import control.button.ButtonHandler;

public class GameModel implements ActionListener{
	
	
	private Timer update;
	public static Color[] colors = {Color.MAGENTA,Color.RED,Color.GREEN,Color.YELLOW,Color.CYAN,Color.WHITE};
	private GameStateManager gsm;
	private SongHandler sh;
	
	public GameModel(GameStateManager gsm)
	{
			
		this.gsm = gsm;
		
		sh = new SongHandler();
		
		update = new Timer(1000/30, this);
		update.start();
		
		for(int i = 1; i < ButtonHandler.getButtons().size(); i++){
			ButtonHandler.getButtons().get(i).setColor(colors[i-1]);;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gsm.update();		
	}
}
