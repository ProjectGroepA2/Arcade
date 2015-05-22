package model.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import model.objects.InfoPanel;
import model.objects.Lines;
import control.GameStateManager;

public class PlayState extends GameState{

	public static final Rectangle2D borderRect = new Rectangle2D.Double(256, 0, 1024, 1024);
	private Lines lines;
	private InfoPanel hsb;
	public PlayState(GameStateManager gsm) {
		super(gsm);
		lines = new Lines((int) borderRect.getX(),100);
		hsb = new InfoPanel(0, 0);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g2) {	
		hsb.draw(g2);
		g2.setClip(borderRect);	
		lines.draw(g2);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
