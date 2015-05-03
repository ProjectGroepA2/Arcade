package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener, ButtonListener, JoyStickListener{
	
	Timer t;
	
	public Panel()
	{
		//addButtonListener(this);
		
		t = new Timer(1000/30, this);
		t.start();
	}

	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	}

	@Override
	public void buttonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoyStickMoved() {
		// TODO Auto-generated method stub
		
	}
}
