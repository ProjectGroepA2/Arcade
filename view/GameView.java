package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

import control.GameStateManager;
import control.LedHandler;

public class GameView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1939480784205689618L;
	
		
	LedHandler led;
	GameStateManager gsm;
	Font fpsfont = 	new Font("OCR A Extended", Font.BOLD, 60);
	
	public GameView(LedHandler led,GameStateManager gsm)
	{
		this.led=led;
		this.gsm = gsm;		
		this.
		setPreferredSize(new Dimension(1280,1024));
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
		
		g2d.setColor(Color.RED);
		g2d.setFont(fpsfont);
		g2d.drawString(gsm.fps + "fps", 1000, 40);
		Toolkit.getDefaultToolkit().sync();
	}	
}
