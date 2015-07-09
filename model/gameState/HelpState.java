package model.gameState;

import image.Images;
import image.Images.ImageType;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import model.GameModel;
import model.SongHandler;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.ButtonEvent;
import control.button.ButtonHandler;
import control.joystick.JoystickEvent;
import data.io.SQLConnector;

public class HelpState extends GameState {

    BufferedImage help = Images.getImage(ImageType.help);
    VolatileImage background;
	Font textFontSmall = new Font("OCR A Extended", Font.BOLD, 15);

	
    int index = 0;
    int frame;

    
	public HelpState(GameStateManager gsm, SongHandler sh, SQLConnector sql){
		super(gsm, sh, sql);
		createBackground();
	}

	@Override
	public void init() {
		ButtonHandler.getButton(2).setColor(GameModel.colors[2]);
		sh.play();
	}

	@Override
	public void update(float factor) {

        frame++;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1280, 1024, null);

	}

	@Override
	public void buttonPressed(ButtonEvent e) {

		if(e.getButton().getButtonID() == 2) {
			gsm.setState(State.MENU_STATE);
		}

	}

	@Override
	public void buttonReleased(ButtonEvent e) {

	}

	@Override
	public void onJoystickMoved(JoystickEvent e) {
	}

	public void createBackground() {
		background = Images.initVolatileImage(1280, 1024, Transparency.OPAQUE);
		Graphics2D g2 = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		
		g2.drawImage(help, 0,0,1280,1024,null);
		
		g2.setFont(textFontSmall);
		
		//back
		g2.setColor(GameModel.colors[2]);
		g2.fillOval(80, 965, 30, 30);
		g2.drawString("Back", 115, 985);

		g2.dispose();
		background.createGraphics();
	}

}
