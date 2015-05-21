package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Player;

public class GameView extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1939480784205689618L;
	Timer t;
	Player player;
	
	public GameView()
	{
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
		
		if(player != null)
		player.draw(g2d);
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
}
