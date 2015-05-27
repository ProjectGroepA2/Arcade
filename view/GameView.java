package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import control.GameStateManager;
import control.LedHandler;

public class GameView extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1939480784205689618L;
	
	Timer t;	
	LedHandler led;
	GameStateManager gsm;
	
	public GameView(LedHandler led,GameStateManager gsm)
	{
		this.led=led;
		this.gsm = gsm;		
		t = new Timer(1000/60, this);
		t.start();
		setPreferredSize(new Dimension(1280,1024));
	}

	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	              RenderingHints.KEY_ANTIALIASING,
	              RenderingHints.VALUE_ANTIALIAS_ON);
	     g2d.setRenderingHints(rh);
		gsm.currentState.draw(g2d);		
	}	
}
