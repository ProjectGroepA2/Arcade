package main;


import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.GameModel;
import model.SongHandler;
import view.GameView;
import control.GameControl;
import control.GameStateManager;
import control.NetworkHandler;
import control.button.ButtonHandler;
import control.joystick.JoystickHandler;
import data.io.SQLConnector;

public class Window extends JFrame {	
	private static final long serialVersionUID = -9222956702898533696L;
	public static boolean ON_ARCADE;
	
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 1024;
	
	public Window(boolean ON_ARCADE)
	{
		//Create window
		super("Arcade");
		setSize(WIDTH, HEIGHT);
		
		//Create Events
		Window.ON_ARCADE = ON_ARCADE;
		if(ON_ARCADE){ //Only on the arcade machine
		
			//Remove cursor
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			this.setCursor(blankCursor);
		}
		
		ButtonHandler bth = new ButtonHandler();
		JoystickHandler jsh = new JoystickHandler();
		
 
		final SQLConnector sql = new SQLConnector();

		NetworkHandler ntw = new NetworkHandler("10.42.0.5", 1113, bth, jsh);
		
		bth.setNetwork(ntw);
		
		//Create Instances
		final SongHandler sh = new SongHandler(sql);
		GameStateManager gsm = new GameStateManager(sh, sql);
		GameView view = new GameView(gsm);
		GameModel model = new GameModel(sh, gsm, ntw);
		GameControl control = new GameControl(model, view,gsm);
		setContentPane(view);
		
		//Set window close listener
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				sh.close();
				try {
					sql.finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		//Create EventListeners
		addKeyListener(bth);
		addKeyListener(jsh);
		bth.addButtonListener(control);
		jsh.addJoystickListener(control);
		
		//Display
		setResizable(false);
		pack();
		setVisible(true);
	}
}
