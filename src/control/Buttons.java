package control;

import java.util.ArrayList;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

public class Buttons {
	private ArrayList<GpioPinDigitalInput> buttons;
	private final GpioController rpi = GpioFactory.getInstance();
	/**
	 Pin numbering below *not used yet, just for remembering the pin numbers*
	 */
	//arcade buttons
	private final int button1 = 29;
	private final int button2 = 28;
	private final int button3 = 27;
	private final int button4 = 25;
	private final int button5 = 24;
	private final int button6 = 23;
	//joystick
	private final int joystickleft = 26;
	private final int joystickup = 22;
	private final int joystickright = 21;
	private final int joystickdown = 11;
	//start-stop button
	private final int buttonstart = 10;
	
	public Buttons(){
		buttons = new ArrayList<GpioPinDigitalInput>();
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_29 , "button1"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_28 , "button2"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_27 , "button3"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_25 , "button4"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_24 , "button5"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_23 , "button6"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_26 , "joystickleft"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_22 , "joystickup"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_21 , "joystickright"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_11 , "joystickdown"));
		buttons.add(rpi.provisionDigitalInputPin(RaspiPin.GPIO_10 , "buttonstart"));
	}
}
