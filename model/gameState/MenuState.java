package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.SongHandler;
import model.objects.MenuButton;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.joystick.Joystick;
import control.joystick.JoystickEvent;

public class MenuState extends GameState {
	private ArrayList<MenuButton> buttons;
	private int selected;
	private Polygon triangle;

    int frame = 0;
    int maxFrames = 2560;
    int animationcounter;
	
	public MenuState(GameStateManager gsm, SongHandler sh) {	
		super(gsm, sh);
		buttons = new ArrayList<MenuButton>();
		buttons.add(new MenuButton(-600, 50,1.7,	"Quick play", 0, Color.green));
		buttons.add(new MenuButton(-600, 150, 1.7, "Pick song", 10, new Color(60,60,255)));
		buttons.add(new MenuButton(-600, 250, 1.7, "Best played", 20, Color.red));
		buttons.add(new MenuButton(-600, 350, 1.7, "Last played", 30, Color.yellow));
	}
	@Override
	public void init() {
	}

	@Override
	public void update() {
		buttonInAnimation(animationcounter);
	     for(MenuButton b:buttons){
	    	 b.update();
	     }
	     frame++;
	}

	@Override
	public void draw(Graphics2D g2) {
//	    g2.drawImage(Images.getImage(ImageType.background), -640 -((frame * 4) % maxFrames), 0, 5120, 1024, null); 
	    for(MenuButton b:buttons){
	    	 b.draw(g2);
	     }
	    
	    g2.setColor(Color.ORANGE);
	    triangle = new Polygon();
	    triangle.addPoint(0, 0);
	    triangle.addPoint(0, 1024/4);
	    triangle.addPoint(1280/2, 0);	    
	    g2.fillPolygon(triangle);
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		if(e.getButton().getButtonID() == 1){
			for(MenuButton b:buttons){
				if(b.isSelected()){
					gsm.setState(State.PLAY_STATE);
				}
			}
		}
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
	
	public void buttonInAnimation(int button){
		if(button >= 0 && button < buttons.size() ){
			buttons.get(button).setX(buttons.get(button).getX()+40);
			if(buttons.get(button).getX() >= 320){
				animationcounter++;
			}
			if(buttons.get(button).getX() >= -200){
				buttonInAnimation(button+1);
			}
		}
	}

}
