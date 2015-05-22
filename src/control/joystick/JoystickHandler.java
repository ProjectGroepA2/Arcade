package control.joystick;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import control.joystick.Joystick.Position;

public class JoystickHandler implements KeyListener{

	List<JoystickListener> listeners;
	Set<Integer> keys;
	Joystick j;
	
	public JoystickHandler()
	{
		listeners = new ArrayList<JoystickListener>();
		keys = new HashSet<Integer>();
		j = new Joystick();
	}

	public void addJoystickListener(JoystickListener toAdd) {
		listeners.add(toAdd);
	}

	public void onJoystickMoved(Joystick j) {
		JoystickEvent e = new JoystickEvent(j, System.currentTimeMillis());
		for (JoystickListener yst : listeners)
			yst.onJoystickMoved(e);
	}
	
	private void updateJoystickPosition()
	{
		if(keys.contains(KeyEvent.VK_UP) && keys.contains(KeyEvent.VK_RIGHT))
		{
			j.setPosition(Position.UP_RIGHT);
		}
		else if(keys.contains(KeyEvent.VK_UP) && keys.contains(KeyEvent.VK_LEFT))
		{
			j.setPosition(Position.UP_LEFT);
		}
		else if(keys.contains(KeyEvent.VK_DOWN) && keys.contains(KeyEvent.VK_RIGHT))
		{
			j.setPosition(Position.DOWN_RIGHT);
		}
		else if(keys.contains(KeyEvent.VK_DOWN) && keys.contains(KeyEvent.VK_LEFT))
		{
			j.setPosition(Position.DOWN_LEFT);
		}
		
		else if(keys.contains(KeyEvent.VK_UP))
		{
			j.setPosition(Position.UP);
		}
		else if(keys.contains(KeyEvent.VK_LEFT))
		{
			j.setPosition(Position.LEFT);
		}
		else if(keys.contains(KeyEvent.VK_RIGHT))
		{
			j.setPosition(Position.RIGHT);
		}
		else if(keys.contains(KeyEvent.VK_DOWN))
		{
			j.setPosition(Position.DOWN);
		}
		
		else
		{
			j.setPosition(Position.CENTER);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Test " + e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			Set<Integer> keysCopy = new HashSet<Integer>(keys);
			System.out.println("YES!");
			keys.add(e.getKeyCode());
			updateJoystickPosition();
			
			if(!keys.equals(keysCopy))
			{
				onJoystickMoved(j);				
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			Set<Integer> keysCopy = new HashSet<Integer>(keys);
			
			keys.remove(e.getKeyCode());
			updateJoystickPosition();
			
			if(!keys.equals(keysCopy))
			{
				onJoystickMoved(j);				
			}
		}
	}

	public void keyTyped(KeyEvent e) {}
}	