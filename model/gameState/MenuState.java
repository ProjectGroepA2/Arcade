package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;

import model.GameModel;
import model.SongHandler;
import model.objects.DifficultyButton;
import model.objects.MenuButton;
import audio.Song;
import audio.SongInstance;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.Joystick;
import control.joystick.JoystickEvent;

public class MenuState extends GameState {
	private ArrayList<MenuButton> buttons;
	private ArrayList<DifficultyButton> buttons2;
	private int selected, oldselected;
	private List<Song> songs;

	private int animationcounter;
	private boolean subscreen, startanimation;
	private VolatileImage mainScreenBackground, subScreenBackground;
	
	int yPosDiffButton = 900;
	private int difSelect=0;
	Font textFont2 = new Font("OCR A Extended", Font.BOLD, 50);
	BufferedImage aanwijzers = Images.getImage(ImageType.aanwijzers);
	int index = 0;
	
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
		generateMainScreenBackground();		
	}
	@Override
	public void init() {
		
	}

	@Override
	public void update(float factor) {
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
	    	 
	    	buttons2.clear();
	 		int instanceNr = 0;
	 		for(int i = sh.getCurrentSong().getSongs().size(); i>0; i--){
	 			if(sh.getCurrentSong().getSongs().size() == 0)
	 				continue;
	 			SongInstance si = sh.getCurrentSong().getSongs().get(i-1);
	 			buttons2.add(new DifficultyButton(yPosDiffButton-instanceNr,si.getDifficulty(), GameModel.colors[i-1]));
	 			instanceNr += 100;
	 		}
	 		
	     }
	     index++;
	}

	@Override
	public void draw(Graphics2D g2) {
		if(!subscreen) {
			g2.drawImage(mainScreenBackground, 0, 0, 1280,1024,null);
			for(MenuButton b:buttons){
		    	 b.draw(g2);
		     }
		}
		if(subscreen) {
			g2.drawImage(subScreenBackground,0,0,1280,1024,null);
			index%=25;
			int y = (index/5)*75;
			int x = (index%5)*75;
			g2.drawImage(aanwijzers.getSubimage(x, y, 75, 75), 825,900 - difSelect*100,75,75,null);		
		}
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		if(subscreen){								//Screen for Song details
			if(e.getButton().getButtonID() == 2){
				subscreen = false;
			}
			if(e.getButton().getButtonID() == 1){
				sh.close();
				gsm.setState(control.GameStateManager.State.PLAY_STATE);
			}
		}else{										//Screen for selecting song
			if(e.getButton().getButtonID() == 1){
				subscreen = true;
				generateSubScreenBackground();
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
		
		if(subscreen){		
			if(e.getJoystick().getPos() == Joystick.Position.DOWN){
				difSelect--;
				if(difSelect < 0){
					difSelect += buttons2.size();
				}
			}else if(e.getJoystick().getPos() == Joystick.Position.UP){
				difSelect++;
				if(difSelect > buttons2.size()-1){
					difSelect = 0;
				}
			}
			sh.set(sh.getCurrentSong().getSongs().get(difSelect));	
			generateSubScreenBackground();			
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
	
	public void generateMainScreenBackground(){
		mainScreenBackground = Images.initVolatileImage(1280, 1024, Transparency.OPAQUE);
		Graphics2D g2 = mainScreenBackground.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		g2.setFont(textFont2);
		g2.setPaint(new GradientPaint(0, 0, new Color(0,0,1, 0.6f),1280,1024 ,new Color(0,0,1, 0.2f)));
		g2.fillRect(0, 0, 1280, 1024);	
		g2.setPaint(new GradientPaint(0, 0, new Color(1,1,0, 0.6f),1280,1024 ,new Color(0,0,1, 0.2f)));
		
	 
		Polygon triangle2 = new Polygon(); 
		triangle2.addPoint(0, 0);
		triangle2.addPoint(0, 1024/4+50);
		triangle2.addPoint(1280/2+50, 0);
		g2.fillPolygon(triangle2);
		
		g2.setColor(Color.ORANGE);
		Polygon triangle = new Polygon();
	    triangle.addPoint(0, 0);
	    triangle.addPoint(0, 1024/4);
	    triangle.addPoint(1280/2, 0);
		g2.fillPolygon(triangle);
		
		g2.setColor(Color.BLACK);
		g2.drawString("Main Menu", 32, 60);
		g2.dispose();
		mainScreenBackground.createGraphics();
	}
	
	public void generateSubScreenBackground(){
		subScreenBackground = Images.initVolatileImage(1280, 1024, Transparency.OPAQUE);
		Graphics2D g2 = subScreenBackground.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		g2.setFont(textFont2);
	    g2.setColor(Color.BLACK);
		GradientPaint gp3 = new GradientPaint(640, 1024/8,Color.BLUE,640,1024 ,Color.WHITE);
		g2.setPaint(gp3);
		g2.fillRect(0, 0, 1280, 1024);
		GradientPaint gp4 = new GradientPaint(0, 0,new Color(1,1,0,0.6f),1280,1024,new Color(1,1,1,0.2f));
		g2.setPaint(gp4);
		g2.fillRect(0,128,1280,25);
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
		g2.dispose();
		subScreenBackground.createGraphics();
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
