package audio;

import java.awt.Color;

import control.button.Button;
import control.button.ButtonHandler;

public class ButtonInstance {
	
	private long time;
	private int buttonID;
	private Color color;
	private Button button;
	
	public ButtonInstance()
	{
		time = 0;
		buttonID = 0;
		
		color = null;
		
		button = null;
	}
	
	public Button getButton() {
		return button;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getButtonID() {
		return buttonID;
	}

	public void setButtonID(int buttonID) {
		if(buttonID < ButtonHandler.getButtons().size() - 1)
		{
			this.buttonID = buttonID;
			this.button = ButtonHandler.getButton(buttonID);
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
