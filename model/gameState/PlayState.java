
package model.gameState;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import model.GameModel;
import model.SongHandler;
import model.drawObjects.Player;
import model.drawObjects.enemy.Enemy;
import model.objects.InfoPanel;
import model.objects.Path;
import model.objects.PlayArea;
import audio.ObjectInstance;
import control.GameStateManager;
import control.button.ButtonEvent;
import control.joystick.JoystickEvent;

public class PlayState extends GameState{

	public static final Rectangle2D borderRect = new Rectangle2D.Double(256, 0, 1024, 1024);
	private PlayArea area;
	private InfoPanel infoPanel;
	private Player player;			
	private Stroke stroke;	 	
	
	public static int sizeOfEnemy = 40;
	public static int currentScore = 0; 
	public static int lifePoints = 100;
	
	private long oldProgress = 0;
	
	public PlayState(GameStateManager gsm, SongHandler sh) {
		super(gsm,sh);
		infoPanel = new InfoPanel(0, 0);			
		area = new PlayArea(256, 1024, 1024, 100);
//		for(int index = 0; index < 8; index++){						
//			addEnemy(index, GameModel.colors[index % 6]);
//		}
		
		player = new Player(1280-1024+1024/2, 1024/2);		
		stroke = new BasicStroke(sizeOfEnemy,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
	}

	@Override
	public void init() {
		sh.stop();
		sh.play();
		
		System.out.println("Diff" + sh.getCurrentSongInstance().getDifficulty());
	}

	@Override
	public void update() {		
		player.update();		
		for(Path path : area.paths){						
		Iterator<Enemy>enemyIterator = path.getEnemysInPath().iterator();
		while(enemyIterator.hasNext()){			
			Enemy e = enemyIterator.next();
			Rectangle2D hitArea = area.hitAreas[e.getIndex()];
			if(e.finised()){
				enemyIterator.remove();
			}			
			if(hitArea.intersectsLine(e.enemy) && !e.isClickable()){
				e.clickable();
			}
				e.update();				
			}
		}		
		
		infoPanel.updateIPanel();		
		
		long progress = sh.getProgress() / 1000;

		for(ObjectInstance ob : sh.getCurrentSongInstance().getBetween(oldProgress, progress))
		{
			Path p = area.paths.get(ob.getDirection());
			p.addEnemy(ob.getColor(), ob.getDirection(), (int) ob.getLength());
		}
			
		oldProgress = progress;
	}
	

	@Override
	public void draw(Graphics2D g2) {			
		try{			
			infoPanel.draw(g2);
			g2.setClip(borderRect);	
			area.draw(g2);
			
			g2.setStroke(stroke);
			for(Path p : area.paths){
				if(p.getEnemysInPath() != null){
					for(Enemy enemy : p.getEnemysInPath()){
						enemy.draw(g2);
					}
				}	
			}

			if(player != null)
				player.draw(g2);
		}catch(Exception e){};
		
	}
	
	

	@Override
	public void buttonPressed(ButtonEvent e) {			
//		int index = player.getIndex();		
		Path currentPath = area.paths.get(player.getIndex());
		if (!currentPath.getEnemysInPath().isEmpty()) {
			Enemy ce = currentPath.getEnemysInPath().getFirst();
			if (ce.isClickable()) {
				if (ce.getColor().equals(e.getButton().getColor())) {
					currentScore += ce.getTimeLeftToClick();
					currentPath.getEnemysInPath().remove(ce);
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

	public void addEnemy(int pathID,Color color){	
		Path path = area.paths.get(pathID);
		Enemy e = new Enemy(pathID,1,color,100,path.getP1(),path.getP2());		
		path.getEnemysInPath().addLast(e);			
	}		
}
