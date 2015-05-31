package model.gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import model.objects.MenuButton;
import control.GameStateManager;
import control.button.Button;
import control.button.ButtonEvent;
import control.joystick.Joystick;
import control.joystick.JoystickEvent;



public class MenuState extends GameState {
	private ArrayList<MenuButton> buttons;
	private int selected;
	
	public MenuState(GameStateManager gsm) {	
		super(gsm);
		buttons = new ArrayList<MenuButton>();
		buttons.add(new MenuButton(330,50,1.7,"Genre", 0, Color.green));
		buttons.add(new MenuButton(330, 150, 1.7, "Most played", 10, new Color(0,0,255)));
		buttons.add(new MenuButton(330, 250, 1.7, "Best played", 20, Color.red));
		buttons.add(new MenuButton(330, 350, 1.7, "Last played", 30, Color.yellow));
	}
	@Override
	public void init() {
	}

	@Override
	public void update() {
	     for(MenuButton b:buttons){
	    	 b.update();
	     }
	}

	@Override
	public void draw(Graphics2D g2) {
	     for(MenuButton b:buttons){
	    	 b.draw(g2);
	     }	    
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {
		if(e.getJoystick().getPos() == Joystick.Position.DOWN){
			selected++;
			selected %= buttons.size();
			for(int i = 0; i < buttons.size(); i++){
				if(selected == i){
					buttons.get(i).setSelected(true);
				}else{
					buttons.get(i).setSelected(false);
				}
			}
		}else if(e.getJoystick().getPos() == Joystick.Position.UP){
			selected--;
			if(selected < 0) selected = buttons.size()-1;
			for(int i = 0; i < buttons.size(); i++){
				if(selected == i){
					buttons.get(i).setSelected(true);
				}else{
					buttons.get(i).setSelected(false);
				}
			}
		}
	}

}
