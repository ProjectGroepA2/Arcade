package control;

import java.util.EventListener;

public interface JoystickListener extends EventListener {
	/**
	 * Invoked when the joystick has been moved.
	 */
	public void joystickMoved(Joystickevent e);
}
