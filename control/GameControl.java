package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.GameModel;
import view.GameView;
import control.button.ButtonEvent;
import control.button.ButtonListener;
import control.joystick.JoystickEvent;
import control.joystick.JoystickListener;

public class GameControl implements JoystickListener, ButtonListener,ActionListener {
	
	private long lastTime = System.currentTimeMillis();
	GameModel model;
	GameView view;
	GameStateManager gsm;
	Timer update;
	
	public GameControl(GameModel model, GameView view,GameStateManager gsm)
	{
		this.model = model;
		this.view = view;
		this.gsm = gsm;
		update = new Timer(1000/60, this);
		update.start();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		model.update(currentTime - lastTime);
		lastTime = currentTime;
		view.repaint();		
	}
}
