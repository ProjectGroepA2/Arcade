package audio;

import control.button.Button;
import control.button.ButtonHandler;

public class ObjectInstance {
	
	private long time;
	private int direction;
	
	private int buttonID;
	private Button button;
	
	private boolean hold;
	private long length;
	
	public ObjectInstance()
	{
		time = 0;
		direction = 0;
		
		buttonID = 0;
		button = null;
		
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

	public int getButtonID() {
		return buttonID;
	}

	public void setButtonID(int buttonID) {
		if(buttonID < ButtonHandler.getButtons().size() - 1)
		{
			this.buttonID = buttonID;
			this.button = ButtonHandler.getButton(buttonID);
		}
	}

	public Button getButton() {
		return button;
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
