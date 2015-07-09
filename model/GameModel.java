package model;

import java.awt.Color;

import main.Window;
import control.GameStateManager;
import control.NetworkHandler;
import control.SongHandler;

public class GameModel {

	public static Color[] colors = { Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA, Color.CYAN, Color.BLUE };
	private static String[] ledStripModes = {"full", "half", "quarter", "random"};
	private GameStateManager gsm;
	private NetworkHandler ntw;
	private int count = 0;

	public GameModel(SongHandler sh, GameStateManager gsm, NetworkHandler ntw) {
		this.gsm = gsm;
		this.ntw = ntw;
	}

	public void update(float factor) {
		gsm.update(factor);
		if (Window.ON_ARCADE)
			updateLedStrip(factor);
	}
	
	private void updateLedStrip(float factor)
	{
		
		count += factor;
		if(count > 400)
		{
			String mode = ledStripModes[(int) (Math.random() * (ledStripModes.length - 1) + 1)];
			
			switch(mode)
			{
			default:
			case "full":
				
				Color c = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				
				for (int i = 6; i < 46; i++) { 
					ntw.setLed(i, c.getRed(), c.getGreen(), c.getBlue());
				}
				
				ntw.show();
				
				break;
				
			case "half":
				
				Color c1 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				Color c2 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				while(c1==c2)	
					c2 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				
				for (int i = 6; i < 26; i++) {
					ntw.setLed(i, c1.getRed(), c1.getGreen(), c1.getBlue());
				}
				for (int i = 27; i < 46; i++) {
					ntw.setLed(i, c2.getRed(), c2.getGreen(), c2.getBlue());
				}
				
				ntw.show();
				
				break;
				
			case "quarter":

				Color c3 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				Color c4 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				Color c5 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				Color c6 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				
				while(c3==c4)	
					c4 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				while(c4==c5)	
					c5 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				while(c5==c6)	
					c6 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
				
				for (int i = 6; i < 16; i++) {
					ntw.setLed(i, c3.getRed(), c3.getGreen(), c3.getBlue());
				}
				for (int i = 17; i < 26; i++) {
					ntw.setLed(i, c4.getRed(), c4.getGreen(), c4.getBlue());
				}
				for (int i = 27; i < 36; i++) {
					ntw.setLed(i, c5.getRed(), c5.getGreen(), c5.getBlue());
				}
				for (int i = 37; i <46; i++) {
					ntw.setLed(i, c6.getRed(), c6.getGreen(), c6.getBlue());
				}
				
				ntw.show();
				
				break;
				
			case "random":
				
				Color c7;
				
				for (int i = 6; i < 46; i++) {
					c7 = colors[(int) (Math.random() * (colors.length - 1) + 1)];
					ntw.setLed(i, c7.getRed(), c7.getGreen(), c7.getBlue());
				}
				
				ntw.show();
				
				break;
			}
			
			count = 0;
		}
	}
}
