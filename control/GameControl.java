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

public class GameControl implements JoystickListener, ButtonListener, ActionListener {
	
	private long lastTime = System.currentTimeMillis();
	private GameModel model;
	private GameView view;
	private GameStateManager gsm;
	private Timer update;
	
	public GameControl(final GameModel model, final GameView view,GameStateManager gsm)	{
		this.model = model;
		this.view = view;
		this.gsm = gsm;

		view.setIgnoreRepaint(true);
		update = new Timer(1000/60,this);
		update.start();
	}

	@Override
	public void buttonPressed(ButtonEvent e) {	
		gsm.buttonPressed(e);
	}

	@Override
	public void buttonReleased(ButtonEvent e) {	
		gsm.buttonReleased(e);
	}

	@Override
	public void onJoystickMoved(JoystickEvent e) {
		gsm.onJoystickMoved(e);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long time = currentTime - lastTime;
		if(time > 1000/60)
			time = 1000/60;
		model.update(time);
		lastTime = currentTime;
		view.repaint();				
	}
}
