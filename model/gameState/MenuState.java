package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
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
	private Polygon triangle,triangle2;

	private int animationcounter;
	private boolean subscreen, startanimation;
	
	int yPosDiffButton = 900;
	private int difSelect=0;
	
	BufferedImage aanwijzers = Images.getImage(ImageType.aanwijzers);
	int index = 0;
	
	
	
	int kleurButton1 = 0;
	int kleurButton2 = 1;
	int kleurButton3 = 2;
	int kleurButton4 = 3;
	int kleurButton5 = 4;
	
	
	
	public MenuState(GameStateManager gsm, SongHandler sh) {	
		super(gsm, sh);
		buttons = new ArrayList<MenuButton>();
		buttons2 = new ArrayList<DifficultyButton>();
		this.songs = sh.getSongs();
		startanimation = true;
		subscreen = false;
		
		
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
		buttons.clear();		
		buttons.add(new MenuButton(400,250,GameModel.colors[kleurButton1],selectedToSong(selected-2)));
		buttons.add(new MenuButton(400,390,GameModel.colors[kleurButton2],selectedToSong(selected-1)));
		buttons.add(new MenuButton(400,530,GameModel.colors[kleurButton3],selectedToSong(selected)));
		buttons.add(new MenuButton(400,670,GameModel.colors[kleurButton4],selectedToSong(selected+1)));
		buttons.add(new MenuButton(400,810,GameModel.colors[kleurButton5],selectedToSong(selected+2)));
		buttons.get(2).setSelected(true);
		
		
		g2.setColor(Color.BLACK);
		Font textFont2 = new Font("OCR A Extended", Font.BOLD, 50);
		g2.setFont(textFont2);
		
		
		if(!subscreen) {
			GradientPaint gp = new GradientPaint(0, 0, new Color(0,0,1, 0.6f),1280,1024 ,new Color(0,0,1, 0.2f));
			g2.setPaint(gp);
			g2.fillRect(0, 0, 1280, 1024);
			
			
			
			triangle2 = new Polygon();
			triangle2.addPoint(0, 0);
			triangle2.addPoint(0, 1024/4+50);
			triangle2.addPoint(1280/2+50, 0);
			
			
			
		    triangle = new Polygon();
		    triangle.addPoint(0, 0);
		    triangle.addPoint(0, 1024/4);
		    triangle.addPoint(1280/2, 0);	    
		    
		    
			for(MenuButton b:buttons){
		    	 b.draw(g2);
		     }
			
			
			GradientPaint gp2 = new GradientPaint(0, 0, new Color(1,1,0, 0.6f),1280,1024 ,new Color(0,0,1, 0.2f));
			g2.setPaint(gp2);
			g2.fillPolygon(triangle2);
			
			g2.setColor(Color.ORANGE);
			g2.fillPolygon(triangle);
			
			g2.setColor(Color.BLACK);
			g2.drawString("Main Menu", 30, 60);
		}
		
		if(subscreen) {
			//g2.setColor(Color.BLACK);
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
			
			
			int y = (index/5)*75;
			int x = (index%5)*75;
			index%=25;
			BufferedImage subImg = aanwijzers.getSubimage(x, y, 75, 75);
			g2.drawImage(subImg, 825,900 - difSelect*100,75,75,null);
			
		}
		

	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		if(subscreen){								//Screen for Song details
			if(e.getButton().getButtonID() == 2){
				subscreen = false;
			}
			if(e.getButton().getButtonID() == 1){
				gsm.next();
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
		
		if(subscreen){		
			if(e.getJoystick().getPos() == Joystick.Position.DOWN){
				difSelect--;
				if(difSelect < 0){
					difSelect += buttons2.size();
				}
//				System.out.println(difSelect);
			}else if(e.getJoystick().getPos() == Joystick.Position.UP){
				difSelect++;
				if(difSelect > buttons2.size()-1){
					difSelect = 0;
				}

				
//				System.out.println(difSelect);
			}
			sh.set(sh.getCurrentSong().getSongs().get(difSelect));	
			
			
			
			
		}else{										//Screen for selecting song
			if(e.getJoystick().getPos() == Joystick.Position.DOWN){
				selected++;
				
				kleurButton1++;
				kleurButton1 %= 6;
				
				kleurButton2++;
				kleurButton2 %= 6;
				
				kleurButton3++;
				kleurButton3 %= 6;
				
				kleurButton4++;
				kleurButton4 %= 6;
				
				kleurButton5++;
				kleurButton5 %= 6;
				
				

			}else if(e.getJoystick().getPos() == Joystick.Position.UP){
				selected--;
				kleurButton1--;
				if(kleurButton1 < 0){
					kleurButton1 = 5;
				}
				
				kleurButton2--;
				if(kleurButton2 < 0){
					kleurButton2 = 5;
				}
				
				kleurButton3--;
				if(kleurButton3 < 0){
					kleurButton3 = 5;
				}
					
				kleurButton4--;
				if(kleurButton4 < 0){
					kleurButton4 = 5;
				}
				
				kleurButton5--;
				if(kleurButton5 < 0){
					kleurButton5 = 5;
				}
				

				
			}
			sh.set(songs.indexOf(selectedToSong(selected)));
			sh.play(true);	
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
