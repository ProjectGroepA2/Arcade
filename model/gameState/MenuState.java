package model.gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import model.SongHandler;
import model.objects.MenuButton;
import audio.Song;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.Button;
import control.button.ButtonEvent;
import control.joystick.Joystick;
import control.joystick.JoystickEvent;

public class MenuState extends GameState {
	private ArrayList<MenuButton> buttons;
	private int selected, oldselected;
	private List<Song> songs;
	private Polygon triangle;

	private int animationcounter;
	private boolean subscreen, startanimation;
	
	public MenuState(GameStateManager gsm, SongHandler sh) {	
		super(gsm, sh);
		buttons = new ArrayList<MenuButton>();
		this.songs = sh.getSongs();
		startanimation = true;
		
		buttons.add(new MenuButton(-600, 50,1.7, 0, Color.green, selectedToSong(selected-2) ));
		buttons.add(new MenuButton(-600, 150, 1.7, 10, new Color(60,60,255), selectedToSong(selected-1)));
		buttons.add(new MenuButton(-600, 250, 1.7, 20, Color.red, selectedToSong(selected)));
		buttons.add(new MenuButton(-600, 350, 1.7, 30, Color.yellow,selectedToSong(selected+1)));
		buttons.add(new MenuButton(-600, 450, 1.7, 30, Color.yellow,selectedToSong(selected+2)));
		buttons.get(2).setSelected(true);
		
	}
	@Override
	public void init() {
	}

	@Override
	public void update() {
		if(startanimation){
			buttonInAnimation(animationcounter);
			if(animationcounter == 5){
				startanimation = false;
			}
		}else if(subscreen){
			nextScreen();
		}else{
			previousScreen();
		}
	     for(MenuButton b:buttons){
	    	 b.update();
	     }
	     if(selected != oldselected){
	    	 for(int i = 0; i < buttons.size(); i++){
		    	 buttons.get(i).setSong(selectedToSong(selected+(i-2)));
	    	 }
	    	 oldselected = selected;
	     }
	}

	@Override
	public void draw(Graphics2D g2) {
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
		if(subscreen){								//Screen for Song details
			if(e.getButton().getButtonID() == 2){
				subscreen = false;
			}	
		}else{										//Screen for selecting song
			if(e.getButton().getButtonID() == 1){
				subscreen = true;
			}else if(e.getButton().getButtonID() == 2){
				subscreen = false;
			}	
		}
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {
		if(subscreen){								//Screen for Song details
				
		}else{										//Screen for selecting song
			if(e.getJoystick().getPos() == Joystick.Position.DOWN){
				selected++;
			}else if(e.getJoystick().getPos() == Joystick.Position.UP){
				selected--;
			}
			sh.set(songs.indexOf(selectedToSong(selected)));
			sh.play();	
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

	public void nextScreen(){
		if(buttons.get(0).getX() > -700){
			for(MenuButton b:buttons){
				b.setX(b.getX()-100);
			}
		}
	}
	
	public void previousScreen(){
		if(buttons.get(0).getX() < 300){
			for(MenuButton b:buttons){
				b.setX(b.getX()+100);
			}
		}
	}
	
	public Song selectedToSong(int s){
		s %= songs.size();
		if(s < 0){
			return songs.get(songs.size()+s);
		}else{
			return songs.get(s);
		}
	}
}
