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
	GameStateManager gsm;
	
	public GameControl(GameModel model, GameView view,GameStateManager gsm)
	{
		this.model = model;
		this.view = view;
		this.gsm = gsm;
	}

	@Override
	public void buttonPressed(ButtonEvent e) {	
//		System.out.println("Game control, button pressed: "+e.getButton().getButtonID());
		gsm.currentState.buttonPressed(e);
	}

	@Override
	public void buttonReleased(ButtonEvent e) {	
//		System.out.println("Game control, button released: "+e.getButton().getButtonID());
		gsm.currentState.buttonReleased(e);
	}

	@Override
	public void onJoystickMoved(JoystickEvent e) {
		gsm.currentState.onJoystickMoved(e);
	}
}
