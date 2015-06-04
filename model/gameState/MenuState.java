package model.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import model.SongHandler;
import model.objects.DifficultyButton;
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
	private ArrayList<DifficultyButton> buttons2;
	private int selected, oldselected;
	private List<Song> songs;
	private Polygon triangle;

	private int animationcounter;
	private boolean subscreen, startanimation;
	
	private int z;
	
	public MenuState(GameStateManager gsm, SongHandler sh) {	
		super(gsm, sh);
		buttons = new ArrayList<MenuButton>();
		buttons2 = new ArrayList<DifficultyButton>();
		this.songs = sh.getSongs();
		startanimation = true;
		subscreen = false;
		
		buttons.add(new MenuButton(-600, 50,1.7, 0, Color.green, selectedToSong(selected-2) ));
		buttons.add(new MenuButton(-600, 150, 1.7, 10, Color.BLUE, selectedToSong(selected-1)));
		buttons.add(new MenuButton(-600, 250, 1.7, 20, Color.red, selectedToSong(selected)));
		buttons.add(new MenuButton(-600, 350, 1.7, 30, Color.yellow,selectedToSong(selected+1)));
		buttons.add(new MenuButton(-600, 450, 1.7, 30, Color.WHITE,selectedToSong(selected+2)));
		buttons.get(2).setSelected(true);
		
		buttons2.add(new DifficultyButton(600 ,"Beginner", Color.BLUE));
		buttons2.add(new DifficultyButton(700 ,"Easy", Color.GREEN));
		buttons2.add(new DifficultyButton(800 ,"Normal", Color.orange));
		buttons2.add(new DifficultyButton(900 ,"Hard", Color.RED));
		
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
		    	 z=i;
	    	 }
	    	 oldselected = selected;
	     }
	}

	@Override
	public void draw(Graphics2D g2) {
	    triangle = new Polygon();
	    triangle.addPoint(0, 0);
	    triangle.addPoint(0, 1024/4);
	    triangle.addPoint(1280/2, 0);	    
	    g2.fillPolygon(triangle);
	    
		g2.setColor(Color.BLACK);
		Font textFont2 = new Font("OCR A Extended", Font.BOLD, 50);
		g2.setFont(textFont2);
		
		if(!subscreen) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0,0,1280,1024);
	
			for(MenuButton b:buttons){
		    	 b.draw(g2);
		     }
			
			g2.setColor(Color.ORANGE);
			g2.fillPolygon(triangle);
			g2.setColor(Color.BLACK);
			g2.drawString("Main Menu", 30, 60);
		}
		
		if(subscreen) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0,0,1280,1024);
			g2.setColor(Color.ORANGE);
			g2.fillRect(0, 0, 1280, 1024/8);
			g2.setColor(Color.BLACK);
			g2.drawString(selectedToSong(selected).getTitle(), 30, 60);
			
			g2.setColor(Color.WHITE);
			g2.drawString("Overall Highscore: " + "", 30, 200);
			g2.drawString("Daily Highscore: " + "", 30, 300);
			g2.drawString("Beats per Minute: " + selectedToSong(selected).getBPM(), 30, 400);
			
			for(DifficultyButton b : buttons2){
				b.draw(g2);
			}
			
			
		}
		

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
