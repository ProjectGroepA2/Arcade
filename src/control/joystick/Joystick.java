package control.joystick;

public class Joystick {
	
	public enum Position {
		UP,
		LEFT,
		DOWN,
		RIGHT,
		UP_RIGHT,
		DOWN_RIGHT,
		UP_LEFT,
		DOWN_LEFT,
		CENTER
	}
	
	private Position pos;
	
	public Joystick()
	{
		pos = Position.CENTER;
	}
	
	public void setPosition(Position pos)
	{
		this.pos = pos;
	}
	public Position getPos() {
		return pos;
	}
	
}
