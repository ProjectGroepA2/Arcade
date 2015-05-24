package control.joystick;

public class JoystickEvent {
	private Joystick joystick;
	private Long now;
	
	public JoystickEvent(Joystick joystick, Long now){
		this.joystick = joystick;
		this.now = now;
	}

	public Joystick getJoystick() {
		return joystick;
	}

	public Long getNow() {
		return now;
	}	
}
