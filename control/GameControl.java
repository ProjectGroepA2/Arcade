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
	GameModel model;
	GameView view;
	GameStateManager gsm;
	Timer update;
	
	public GameControl(final GameModel model, final GameView view,GameStateManager gsm)
	{
		this.model = model;
		this.view = view;
		this.gsm = gsm;
		view.setIgnoreRepaint(true);
		update = new Timer(0,this);
		update.start();
//		Timer update = new Timer();
//		update.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				long currentTime = System.currentTimeMillis();
//				model.update(currentTime - lastTime);
//				lastTime = currentTime;
//				view.repaint();			
//				System.out.println("Test");
//			}
//		}, 0,1000/120);
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
		long time = currentTime - lastTime;
		if(time > 1000/60)
			time = 1000/60;
		model.update(time);
		lastTime = currentTime;
		view.repaint();				
	}
}
