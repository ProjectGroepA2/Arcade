package control;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Buttontest{
	
	final GpioController rpi = GpioFactory.getInstance();
	public Buttontest(){
		  GpioPinDigitalInput button1 = rpi.provisionDigitalInputPin(	RaspiPin.GPIO_07,             
				  														"button1"); 
		  button1.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e){
				System.out.println(e.getPin());
				System.out.println(e.getSource());
				System.out.println(e.getState());
			}
		});	
		  while(true){
			  
		  }
	}

}
