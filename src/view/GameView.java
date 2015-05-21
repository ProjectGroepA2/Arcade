package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import control.LedHandler;

public class GameView extends JPanel implements ActionListener{
	
	Timer t;
	Color c;
	LedHandler led;
	public GameView(LedHandler led)
	{
		this.led=led;
		t = new Timer(1000/30, this);
		t.start();
		c = new Color(100,100,100);
	}

	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(c);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void setColor(Color c)
	{
		this.c = c;
		for(int i =6; i < 55; i++){
			led.setLed(i, c.getRed(), c.getGreen(), c.getBlue());
		}
		led.show();
	}
}
