package control.joystick;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.Window;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import control.joystick.Joystick.Position;

public class JoystickHandler implements KeyListener{

	List<JoystickListener> listeners;
	Set<Integer> keys;
	public static Joystick j = new Joystick();;
	
	public JoystickHandler()
	{
		listeners = new ArrayList<JoystickListener>();
		keys = new HashSet<Integer>();
//		if(Window.ON_RASP)
//			addGpioListeners();
	}
	
	private void addGpioListeners(){
		ArrayList<GpioPinDigitalInput> inputpins = new ArrayList<GpioPinDigitalInput>();
        final GpioController gpio = GpioFactory.getInstance();
        
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "UP")); //button 1 to 6 + start button
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "LEFT"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_11, "RIGHT"));
        inputpins.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_10, "DOWN"));

        
        for(GpioPinDigitalInput p:inputpins){
        	  p.addListener(new GpioPinListenerDigital() {
                  @Override
                  public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {
                	  if(e.getState() == PinState.HIGH){
                		  keyReleased(new KeyEvent(
                				  		new java.awt.Component(){

											/**
											 * 
											 */
											private static final long serialVersionUID = 1L;}, 
                				  		KeyEvent.KEY_RELEASED, 
                				  		System.nanoTime(), 
                				  		0, 
                				  		stringToKeyevent(e.getPin().getName()), 
                				  		KeyEvent.CHAR_UNDEFINED)
                				  );
                		  System.out.println(e.getPin().getName() + " Released");
                	  }else{
                		  keyPressed(new KeyEvent(
          				  		new java.awt.Component(){

									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;}, 
          				  		KeyEvent.KEY_PRESSED, 
          				  		System.nanoTime(), 
          				  		0, 
          				  		stringToKeyevent(e.getPin().getName()), 
          				  		KeyEvent.CHAR_UNDEFINED)
          				  );
                		  System.out.println(e.getPin().getName() + " Pressed");
                	  }
                  }                  
              });
        }
	}

	public void addJoystickListener(JoystickListener toAdd) {
		listeners.add(toAdd);
	}

	public void onJoystickMoved(Joystick j) {
		JoystickEvent e = new JoystickEvent(j, System.currentTimeMillis());
		for (JoystickListener yst : listeners)
			yst.onJoystickMoved(e);
	}
	
	private void updateJoystickPosition()
	{
		if(keys.contains(KeyEvent.VK_UP) && keys.contains(KeyEvent.VK_RIGHT))
		{
			j.setPosition(Position.UP_RIGHT);
		}
		else if(keys.contains(KeyEvent.VK_UP) && keys.contains(KeyEvent.VK_LEFT))
		{
			j.setPosition(Position.UP_LEFT);
		}
		else if(keys.contains(KeyEvent.VK_DOWN) && keys.contains(KeyEvent.VK_RIGHT))
		{
			j.setPosition(Position.DOWN_RIGHT);
		}
		else if(keys.contains(KeyEvent.VK_DOWN) && keys.contains(KeyEvent.VK_LEFT))
		{
			j.setPosition(Position.DOWN_LEFT);
		}
		
		else if(keys.contains(KeyEvent.VK_UP))
		{
			j.setPosition(Position.UP);
		}
		else if(keys.contains(KeyEvent.VK_LEFT))
		{
			j.setPosition(Position.LEFT);
		}
		else if(keys.contains(KeyEvent.VK_RIGHT))
		{
			j.setPosition(Position.RIGHT);
		}
		else if(keys.contains(KeyEvent.VK_DOWN))
		{
			j.setPosition(Position.DOWN);
		}
		
		else
		{
			j.setPosition(Position.CENTER);
		}
	}
	
	private int stringToKeyevent(String s){
		switch(s){
		case "UP":
			return KeyEvent.VK_UP;
		case "DOWN":
			return KeyEvent.VK_DOWN;
		case "LEFT":
			return KeyEvent.VK_LEFT;
		case "RIGHT":
			return KeyEvent.VK_RIGHT;
		}
		return -1;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			Set<Integer> keysCopy = new HashSet<Integer>(keys);
			keys.add(e.getKeyCode());
			updateJoystickPosition();
			
			if(!keys.equals(keysCopy))
			{
				onJoystickMoved(j);				
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			Set<Integer> keysCopy = new HashSet<Integer>(keys);
			
			keys.remove(e.getKeyCode());
			updateJoystickPosition();
			
			if(!keys.equals(keysCopy))
			{
				onJoystickMoved(j);				
			}
		}
	}

	public void keyTyped(KeyEvent e) {}
}	
