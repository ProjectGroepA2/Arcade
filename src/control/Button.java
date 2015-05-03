package control;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

public class Button {
	private GpioPinDigitalInput input;
	
	public Button(int buttonId, Pin pin){
		input = GpioFactory.getInstance().provisionDigitalInputPin(pin , "Button:"+buttonId);
	}
	public boolean getState(){
		return input.getState().isLow();
	}
}
