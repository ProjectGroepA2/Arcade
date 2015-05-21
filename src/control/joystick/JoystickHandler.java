package control.joystick;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import control.LedHandler;

public class JoystickHandler implements KeyListener{
	
	List<JoystickListener> listeners;
	LedHandler led;
	
	public JoystickHandler(LedHandler led)
	{
		this.led = led;
		listeners = new ArrayList<JoystickListener>();
	}

	public void addJoyStickListener(JoystickListener toAdd) {
	    listeners.add(toAdd);
	}
	
	public void onJoyStickMoved() {
		JoystickEvent e = new JoystickEvent(new Joystick(), 1000L); //TODO edit
	    for (JoystickListener yst : listeners)
	        yst.onJoyStickMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}