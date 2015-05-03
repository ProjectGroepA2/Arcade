package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import view.Panel;

public class Window extends JFrame {
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
		
		//Create Instances
		Panel panel = new Panel();
		setContentPane(panel);
		
		//Display
		pack();
		setVisible(true);
	}
}
