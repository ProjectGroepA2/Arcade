package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import model.GameModel;
import view.GameView;
import control.GameControl;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window()
	{
		//Create window
		super("Arcade");
		setSize(500,600);
		
		//Set window close listener
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//Set window to fullscreen
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		
		//Create Instances
		GameView view = new GameView();
		GameModel model = new GameModel(view);
		GameControl control = new GameControl(model, view);
		setContentPane(view);
		
		addKeyListener(control);
		addMouseListener(control);
		addWindowFocusListener(control);
		
		//Display
		pack();
		setVisible(true);
	}
}
