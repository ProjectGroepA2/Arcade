package audio;

import java.awt.Color;

public class ObjectInstance {
	
	private long time;
	private int direction;
	
	private Color color;
	
	private boolean hold;
	private long length;
	
	public ObjectInstance()
	{
		time = 0;
		direction = 0;
		
		color = null;
		
		hold = false;
		length = 0;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}
	
	
	
	
}
