package control.button;

import java.awt.Color;

import main.Window;
import control.NetworkHandler;

public class Button {

	private 	Color 			color;
	private 	int 			ledID,
								buttonID;
	public		int				pressed;

	private		NetworkHandler	ntw;
	
	public Button(int buttonID, int ledID, NetworkHandler ntw) {
		color = new Color(255,255,255);
		this.ledID = ledID;
		this.buttonID = buttonID;
		this.ntw = ntw;
		pressed = 1;
		setLed();
	}
	
	private void setLed() {
		if(Window.ON_RASP) {
			ntw.setLed(ledID, color.getGreen(), color.getRed(), color.getBlue());
			ntw.show();
		}
	}
	
	public void setColor(Color newColor) {
		color = newColor;
		setLed();
	}
	
	public Color getColor(){
		return color;
	}
	public int getButtonID()
	{
		return buttonID;
	}
	public int getLedID()
	{
		return ledID;
	}
}
