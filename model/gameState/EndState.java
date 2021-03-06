package model.gameState;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

import model.GameModel;
import model.objects.InfoPanel;
import model.objects.highscore.HighscoreName;

import com.google.zxing.WriterException;

import control.GameStateManager;
import control.SongHandler;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.button.ButtonHandler;
import control.joystick.JoystickEvent;
import control.joystick.JoystickHandler;
import data.io.SQLConnector;
import data.io.WebcamUploader;

public class EndState extends GameState {

    BufferedImage end = null;
    VolatileImage background;
	Font textFont = new Font("OCR A Extended", Font.BOLD, 70);
	HighscoreName hsn;
	int image_x = 0;	
	private boolean uploaded;
	private BufferedImage QRimage;
	
	boolean timeOut = false;
	float timeOutCounter = 0;
	
    int frame;

	public EndState(GameStateManager gsm, SongHandler sh, SQLConnector sql) {
		super(gsm, sh, sql);
		hsn = new HighscoreName(640,717,5,textFont);
	}

	@Override
	public void init() {
		createBackground();
		
		ButtonHandler.getButton(1).setColor(GameModel.colors[0]);
		ButtonHandler.getButton(2).setColor(GameModel.colors[2]);
		
		JoystickHandler.REPEAT = true;
		uploaded = false;
		timeOut = true;
		timeOutCounter = 0;

		if(PlayState.won()){
			end = Images.getImage(Images.ImageType.youwon);
		}else{
			end = Images.getImage(Images.ImageType.gameover);
		}
	}

	@Override
	public void update(float factor) {
		if(timeOut)
		{
			timeOutCounter += factor;
			
			if(timeOutCounter > 800)
			{
				timeOut = false;
			}
		}
		
		
		image_x = ((frame / 5) % 5) * 40;
        frame++;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1280, 1024, null);
		
		if(end != null){
			g2.drawImage(end.getSubimage(image_x, 0, 40, 26),  640-122, 400, 245, 130, null);
		}
			
		if(!uploaded)
			hsn.drawName(g2);
		else if(QRimage != null){
			g2.drawImage(QRimage, (1280/2)-(QRimage.getWidth()/2), 600,null);
			//1280 is de breedte van de panel
		}
			
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		if(!timeOut)
		{
			switch(e.getButton().getButtonID()){
			case 0:
				gsm.setState(State.TITLE_STATE);
				break;
			case 1:
				if(hsn.getName().trim().length() >= 3)
				{
					if(!uploaded){
						int id = sql.addHighscore(sh.getCurrentSong(), sh.getCurrentSongInstance(), hsn.getName(), PlayState.currentScore);
						try {
							WebcamUploader.takePictureAndUpload(id);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						 		
						try {
							QRimage = WebcamUploader.createQRImage("http://portfolio.jancokock.me/colorstrike/message.php?id="+id, 400);
						} catch (WriterException | IOException e1) {
							e1.printStackTrace();
						}
						uploaded = true;
	
					}else{
						gsm.setState(State.MENU_STATE);
					}
				}
				break;			
			case 2:
				gsm.setState(State.MENU_STATE);
				break;
			}
		}
	}
	
	@Override
	public void buttonReleased(ButtonEvent e) {}
	
	@Override
	public void onJoystickMoved(JoystickEvent e) {
		if(!timeOut)
		{
			switch(e.getJoystick().getPos()){		
			case DOWN:
				hsn.down();
				break;			
			case LEFT:
				hsn.left();
				break;
			case RIGHT:
				hsn.right();
				break;
			case UP:
				hsn.up();
				break;		
			default:
				break;	
			}
		}
	}
	
	public void createBackground(){
		background = Images.initVolatileImage(1280, 1024, Transparency.OPAQUE);
		Graphics2D g2 = background.createGraphics();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		g2.setColor(new Color(1,1,1, 0.3f));
		g2.fillRect(0,0,1280,1024);
		
		g2.setColor(new Color(0,1,0, 0.7f));
		g2.fillRect(0,0,100,1024);
		g2.fillRect(1180,0,100,1024);
		
		g2.setColor(new Color(1,1,0, 0.7f));
		g2.fillRect(100,0,100,1024);
		g2.fillRect(1080,0,100,1024);
		
		g2.setColor(new Color(1,0,0, 0.7f));
		g2.fillRect(200,0,100,1024);
		g2.fillRect(980,0,100,1024);
		
		g2.setColor(Color.BLACK);
		g2.setFont(textFont);
		
		g2.drawString("High Score", 385, 212);
		g2.drawString(InfoPanel.getTotalHighscore() + "", 390, 342);
		g2.dispose();
	}
}
