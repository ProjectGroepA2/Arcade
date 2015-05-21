package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import model.GameModel;
import view.GameView;
import control.GameControl;
import control.LedHandler;
import control.button.ButtonHandler;
import control.joystick.JoystickHandler;

public class Window extends JFrame {
	
	public static boolean ON_RASP = false;
	
	public Window()
	{
		//Create window
		super("Arcade");
		setSize(1280, 1024);
		
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
		
		//Create Events
		LedHandler led = null;
		
		if(Window.ON_RASP) //TODO REMOVE
		{
			led = new LedHandler();
		}
		
		ButtonHandler bth = new ButtonHandler(led);
		JoystickHandler jsh = new JoystickHandler();
		
		//Create Instances
		GameView view = new GameView(led);
		GameModel model = new GameModel(view);
		GameControl control = new GameControl(model, view);
		setContentPane(view);
		
		//Create EventListeners
		addKeyListener(bth);
		addKeyListener(jsh);
		bth.addButtonListener(control);
		jsh.addJoystickListener(control);
		
		//Display
		pack();
		setVisible(true);
	}
}
