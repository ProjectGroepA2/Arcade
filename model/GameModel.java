package model;

import java.awt.Color;

import control.GameStateManager;
import control.button.ButtonHandler;

public class GameModel{
	
	public static Color[] colors = {Color.GREEN,Color.YELLOW,Color.RED,Color.MAGENTA,Color.CYAN,Color.WHITE};
	private GameStateManager gsm;
	
	public GameModel(SongHandler sh, GameStateManager gsm)
	{
		this.gsm = gsm;
		
		for(int i = 1; i < ButtonHandler.getButtons().size(); i++){
			ButtonHandler.getButtons().get(i).setColor(colors[i-1]);;
		}
	}
	
	public void update(float factor){	
		gsm.update(factor);
	}
}
