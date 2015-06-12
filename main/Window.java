package main;


import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
	public static boolean ON_RASP;
	
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 1024;
	
	public Window(boolean ON_RASP)
	{
		//Create window
		super("Arcade");
		setSize(WIDTH, HEIGHT);
		
		//Create Events
		Window.ON_RASP = ON_RASP;
		if(ON_RASP){ //Only on the raspberry pi
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] devices =  graphicsEnvironment.getScreenDevices();

			if (!devices[0].isFullScreenSupported ()){
			     throw new UnsupportedOperationException ("Fullscreen mode is unsupported.");
			}else{
				devices[0].setFullScreenWindow(this);
			}			
			//Remove cursor
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			this.setCursor(blankCursor);
		}
		
		ButtonHandler bth = new ButtonHandler();
		JoystickHandler jsh = new JoystickHandler();
		
		SQLConnector sql = new SQLConnector();
		NetworkHandler ntw = new NetworkHandler("192.168.1.6", 1113, bth, jsh);
		
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
		pack();
		setVisible(true);
	}
}
