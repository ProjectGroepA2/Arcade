package control;

import model.GameModel;
import view.GameView;
import control.button.ButtonEvent;
import control.button.ButtonListener;
import control.joystick.JoystickEvent;
import control.joystick.JoystickListener;

public class GameControl implements JoystickListener, ButtonListener{
	
	GameModel model;
	GameView view;
	
	public GameControl(GameModel model, GameView view)
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public void buttonPressed(ButtonEvent e) {
		view.setColor(e.getButton().getColor());
	}

	@Override
	public void buttonReleased(ButtonEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoyStickMoved(JoystickEvent e) {
		// TODO Auto-generated method stub
		
	}
}
