package model;

import java.awt.Color;

import main.Window;
import control.GameStateManager;
import control.LedHandler;
import control.button.ButtonHandler;

public class GameModel {

	public static Color[] colors = { Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA, Color.CYAN, Color.ORANGE };
	private GameStateManager gsm;
	private LedHandler led;
	private int count = 0;

	public GameModel(SongHandler sh, GameStateManager gsm, LedHandler led) {
		this.gsm = gsm;
		this.led = led;

		for (int i = 1; i < ButtonHandler.getButtons().size(); i++) {
			ButtonHandler.getButtons().get(i).setColor(colors[i - 1]);
			;
		}
	}

	public void update(float factor) {
		gsm.update(factor);

		Color c = colors[(int) (Math.random() * (colors.length - 1) + 1)];

		if (Window.ON_RASP) {
			count++;
			if(count>15)
			{
				for (int i = 7; i < 54; i++) {
					led.setLed(i, c.getRed(), c.getGreen(), c.getBlue());
				}
				led.show();
				count = 0;
			}
		}
	}
}
