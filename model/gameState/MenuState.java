package model.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;

import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;



public class MenuState extends GameState {
    
	public Polygon triangle;
	
	int page;
    int x,y;

	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g2) {
		
	    g2.setColor(Color.BLACK);
	    g2.fillRect(0, 0, 1280, 1024);
		
	    g2.setColor(Color.ORANGE);
	    triangle = new Polygon();
	    triangle.addPoint(0, 0);
	    triangle.addPoint(0, 1024/4);
	    triangle.addPoint(1280/2, 0);	    
	    g2.fillPolygon(triangle);
	    
	    for(int i = 1; i <= 4; i++){
	    	g2.fillRect(780, 1124 - 240*i, 500, 100);
	    }
	    
		Font textFont = new Font("OCR A Extended", Font.BOLD, 60);
		g2.setFont(textFont);
		g2.setColor(Color.BLACK);
	    
	    if(page ==  0){
			g2.drawString("Main Menu", 50, 50);
	    }
	    if(page ==  1){
			g2.drawString("Most Played", 50, 50);
	    }
	    if(page ==  2){
			g2.drawString("Genre", 50, 50);
	    }
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		
		switch(e.getButton().getButtonID()){
		case 0:
			gsm.next();
			break;
		}
		
		
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

}
