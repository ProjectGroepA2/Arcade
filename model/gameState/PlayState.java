
package model.gameState;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.GameModel;
import model.SongHandler;
import model.drawObjects.Player;
import model.drawObjects.enemy.Enemy;
import model.objects.InfoPanel;
import model.objects.PlayArea;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class PlayState extends GameState{

	public static final Rectangle2D borderRect = new Rectangle2D.Double(256, 0, 1024, 1024);
	private PlayArea area;
	private InfoPanel infoPanel;
	private Player player;
	private List<Enemy> enemys;		
	private Stroke stroke;
	private Iterator<Enemy> enemyIterator;	
	
	public static int sizeOfEnemy = 40;
	public static int currentScore = 0; 
	public static int lifePoints = 100;
	
	public PlayState(GameStateManager gsm, SongHandler sh) {
		super(gsm,sh);
		infoPanel = new InfoPanel(0, 0);
		enemys = new ArrayList<Enemy>();		
		area = new PlayArea(256, 1024, 1024, 100);
		for(int index = 0; index < 2; index++){
			Line2D path = area.paths.get(index);			
			addEnemy(index, GameModel.colors[index % 6], path.getP1(),path.getP2());
		}
		
		player = new Player(1280-1024+1024/2, 1024/2);		
		stroke = new BasicStroke(sizeOfEnemy,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
	}

	@Override
	public void init() {
		
		
	}

	@Override
	public void update() {		
		player.update();		
		enemyIterator = enemys.iterator();
		while(enemyIterator.hasNext()){			
			Enemy e = enemyIterator.next();
			Rectangle2D hitArea = area.hitAreas[e.getIndex()];
			if(e.finised()){
				enemyIterator.remove();
			}			
			if(hitArea.intersectsLine(e.enemy) && !e.isClickable()){
				e.clickable();
			}
			if(e.isClickable()){
				
			}
//			System.out.println(e.enemy.getP1().distance(hitArea.getCenterX(), hitArea.getCenterY()));
			e.update();				
		}
			infoPanel.updateIPanel();
	}
	

	@Override
	public void draw(Graphics2D g2) {			
		try{			
			infoPanel.draw(g2);
			g2.setClip(borderRect);	
			area.draw(g2);
			
			g2.setStroke(stroke);
			if(enemys != null){
				for(Enemy enemy : enemys){
					enemy.draw(g2);
				}
			}			

			if(player != null)
				player.draw(g2);
		}catch(Exception e){};
		
	}
	
	

	@Override
	public void buttonPressed(ButtonEvent e) {			
		enemyIterator = enemys.iterator();
		while(enemyIterator.hasNext()){			
			Enemy enemy = enemyIterator.next();
			if(enemy.isClickable()){
				if(enemy.getIndex() == player.getIndex()){
					if(enemy.getColor().equals(e.getButton().getColor())){
						enemyIterator.remove();
						currentScore += enemy.getTimeLeftToClick();
					}
				}
			}
		}	
	}

	@Override
	public void buttonReleased(ButtonEvent e) {		
	}

	@Override
	public void onJoystickMoved(JoystickEvent e) {	
		switch(e.getJoystick().getPos()){
		case CENTER:
			break;
		case DOWN:
			player.setIndex(4);
			break;
		case DOWN_LEFT:
			player.setIndex(5);
			break;
		case DOWN_RIGHT:
			player.setIndex(3);
			break;
		case LEFT:
			player.setIndex(6);
			break;
		case RIGHT:
			player.setIndex(2);
			break;
		case UP:
			player.setIndex(0);
			break;
		case UP_LEFT:
			player.setIndex(7);
			break;
		case UP_RIGHT:
			player.setIndex(1);
			break;
		default:			
			break;
		}
	}

	public void addEnemy(int pathID,Color color,Point2D beginPoint,Point2D endPoint){		
		enemys.add(new Enemy(pathID,1,color,100,beginPoint, endPoint));	
	}		
}
