package view;

import java.util.ArrayList;
import java.util.List;

interface JoyStickListener {
	public void onJoyStickMoved();
}

class JoyStick {
	List<JoyStickListener> listeners = new ArrayList<JoyStickListener>();

	public void addJoyStickListener(JoyStickListener toAdd) {
	    listeners.add(toAdd);
	}
	
	public void onJoyStickMoved() {
	    for (JoyStickListener yst : listeners)
	        yst.onJoyStickMoved();
	}
}
