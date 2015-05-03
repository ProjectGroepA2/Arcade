package view;

import java.util.ArrayList;
import java.util.List;

interface ButtonListener {
	public void buttonPressed();
	public void buttonReleased();
}

class Button {
	List<ButtonListener> listeners = new ArrayList<ButtonListener>();

	public void addButtonListener(ButtonListener toAdd) {
	    listeners.add(toAdd);
	}
	
	public void buttonPress() {
	    for (ButtonListener bt : listeners)
	        bt.buttonPressed();
	}
	 
	public void buttonRelease() {
	    for (ButtonListener bt : listeners)
	        bt.buttonReleased();
	}
}
