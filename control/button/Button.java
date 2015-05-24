package control.button;

import java.awt.Color;

import main.Window;
import control.LedHandler;

public class Button {

	Color color;
	int ledID;
	int buttonID;
	LedHandler led;
	
	public Button(int buttonID, int ledID, LedHandler led)
	{
		color = new Color(255,255,255);
		this.ledID = ledID;
		this.buttonID = buttonID;
		this.led = led;
		setLed();
	}
	
	private void setLed()
	{
		if(Window.ON_RASP)
		{
			led.setLed(ledID, color.getGreen(), color.getRed(), color.getBlue());
			led.show();
		}
	}
	
	public void setColor(Color newColor)
	{
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
}
