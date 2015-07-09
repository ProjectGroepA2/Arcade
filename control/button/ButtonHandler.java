package control.button;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import main.Window;
import model.GameModel;
import control.NetworkHandler;

public class ButtonHandler implements KeyListener{

	List<ButtonListener> listeners;
	static List<Button> buttons;
	NetworkHandler ntw;
	
	public ButtonHandler()
	{
		this.ntw = null;
		
		listeners = new ArrayList<ButtonListener>();
		buttons = new ArrayList<Button>();
		
	}

	public void addButtonListener(ButtonListener toAdd) {
	    listeners.add(toAdd);
	}
	
	public void buttonPress(Button b) {
		if(Window.ON_ARCADE)
		{
			Color c = b.getColor().brighter().brighter();
			ntw.setLed(b.getLedID(), c.getGreen(), c.getRed(), c.getBlue());
			ntw.show();
		}
		ButtonEvent e = new ButtonEvent(b, System.currentTimeMillis());
	    for (ButtonListener bt : listeners)
	        bt.buttonPressed(e);
	}
	 
	public void buttonRelease(Button b) {
		if(Window.ON_ARCADE)
		{
			ntw.setLed(b.getLedID(), b.getColor().getGreen(), b.getColor().getRed(), b.getColor().getBlue());
			ntw.show();
		}
		ButtonEvent e = new ButtonEvent(b, System.currentTimeMillis());
	    for (ButtonListener bt : listeners)
	        bt.buttonReleased(e);
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_0:
			buttonPress(buttons.get(0));
			break;
		case KeyEvent.VK_1:
			buttonPress(buttons.get(1));
			break;
		case KeyEvent.VK_2:
			buttonPress(buttons.get(2));
			break;
		case KeyEvent.VK_3:
			buttonPress(buttons.get(3));
			break;
		case KeyEvent.VK_4:
			buttonPress(buttons.get(4));
			break;
		case KeyEvent.VK_5:
			buttonPress(buttons.get(5));
			break;
		case KeyEvent.VK_6:
			buttonPress(buttons.get(6));
			break;
		case KeyEvent.VK_ESCAPE:
			if(!Window.ON_ARCADE)
				System.exit(0);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_0:
			buttonRelease(buttons.get(0));
			break;
		case KeyEvent.VK_1:
			buttonRelease(buttons.get(1));
			break;
		case KeyEvent.VK_2:
			buttonRelease(buttons.get(2));
			break;
		case KeyEvent.VK_3:
			buttonRelease(buttons.get(3));
			break;
		case KeyEvent.VK_4:
			buttonRelease(buttons.get(4));
			break;
		case KeyEvent.VK_5:
			buttonRelease(buttons.get(5));
			break;
		case KeyEvent.VK_6:
			buttonRelease(buttons.get(6));
			break;
		}
	}
	public void keyTyped(KeyEvent arg0) {}
	
	public static List<Button> getButtons()
	{
		return buttons;
	}
	
	public static Button getButton(int id)
	{
		for(Button b : buttons)
		{
			if(b.getButtonID() == id)
			{
				return b;
			}
		}
		return null;
	}

	public void setNetwork(NetworkHandler ntw) {
		this.ntw = ntw;
		
		buttons.add(new Button(0, -1, ntw));
		buttons.add(new Button(1, 2, ntw));
		buttons.add(new Button(2, 1, ntw));
		buttons.add(new Button(3, 0, ntw));
		buttons.add(new Button(4, -1, ntw));
		buttons.add(new Button(5, 3, ntw));
		buttons.add(new Button(6, 4, ntw));
		
		for (int i = 1; i < buttons.size(); i++) {
			buttons.get(i).setColor(GameModel.colors[i - 1]);
		}
	}
}
