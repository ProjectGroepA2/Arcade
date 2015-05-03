package control;

import java.util.EventListener;

public interface ButtonsListener extends EventListener {
	
	/**
	 * Invoked when a button has been pressed.
	 */
	public void buttonPressed(ButtonEvent e);
		
	/**
	 * Invoked when a button has been released.
	 */
	public void buttonReleased(ButtonEvent e);
	
	/**
	 * Invoked when a button is held down.
	 */
	public void buttonHeld(ButtonEvent e); 
}
