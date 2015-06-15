package model.gameState;

import image.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.awt.image.WritableRenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.WriterException;

import model.GameModel;
import model.SongHandler;
import model.objects.InfoPanel;
import model.objects.highscore.HighscoreName;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.button.ButtonHandler;
import control.joystick.JoystickEvent;
import control.joystick.JoystickHandler;
import data.io.SQLConnector;
import data.io.WebcamUploader;

public class GameOverState extends GameState {

    BufferedImage gameOver = Images.getImage(Images.ImageType.gameover);
    VolatileImage background;
	Font textFont = new Font("OCR A Extended", Font.BOLD, 70);
	HighscoreName hsn;
	int image_x = 0;	
	private boolean uploaded;
	private BufferedImage QRimage;
	
    int frame;

	public GameOverState(GameStateManager gsm, SongHandler sh, SQLConnector sql) {
		super(gsm, sh, sql);
		hsn = new HighscoreName(640,717,5,textFont);
	}

	@Override
	public void init() {
		createBackground();
		ButtonHandler.getButton(1).setColor(GameModel.colors[0]);
		JoystickHandler.REPEAT = true;
		uploaded = false;
	}

	@Override
	public void update(float factor) {
		image_x = ((frame / 5) % 5) * 40;
        frame++;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1280, 1024, null);		
		g2.drawImage(gameOver.getSubimage(image_x, 0, 40, 26),  640-122, 400, 245, 130, null);
		if(!uploaded)
			hsn.drawName(g2);
		else if(QRimage != null){
			g2.drawImage(QRimage, 400, 600, 400, 400, null);
		}
			
	}
	
	@Override
	public void buttonPressed(ButtonEvent e) {
		//System.out.println("Name: "+hsn.getName());
		switch(e.getButton().getButtonID()){
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

					System.out.println(id);
				}else{
					gsm.setState(State.MENU_STATE);
				}
			}
			break;			
		case 2:
			gsm.setState(State.MENU_STATE);
		}
		
		
	}
	@Override
	public void buttonReleased(ButtonEvent e) {
		
	}
	@Override
	public void onJoystickMoved(JoystickEvent e) {
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
