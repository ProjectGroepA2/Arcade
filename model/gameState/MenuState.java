package model.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import control.GameStateManager;

public class MenuState extends GameState {

	public MenuState(GameStateManager gsm) {
		super(gsm);
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
		g2.fillRect(10, 10, 100, 10);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q){
			gsm.next();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
