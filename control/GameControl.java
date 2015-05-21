package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.Timer;

import model.GameModel;
import view.GameView;

public class GameControl implements MouseListener, KeyListener, WindowFocusListener, ActionListener{
	
	GameModel model;
	GameView view;
	Timer update;
	
	public GameControl(GameModel model, GameView view)
	{
		this.model = model;
		this.view = view;
		update = new Timer(1000/10, this);
		update.start();
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void windowGainedFocus(WindowEvent e) {}

	public void windowLostFocus(WindowEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.update();		
	}
}
