package model.gameState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import main.Window;
import model.SongHandler;
import model.drawObjects.Enemy;
import model.drawObjects.Player;
import model.objects.InfoPanel;
import model.objects.Path;
import model.objects.PlayArea;
import audio.ButtonInstance;
import audio.ObjectInstance;
import control.GameStateManager;
import control.GameStateManager.State;
import control.button.Button;
import control.button.ButtonEvent;
import control.button.ButtonHandler;
import control.joystick.JoystickEvent;
import data.io.SQLConnector;

public class PlayState extends GameState {

	public static final Rectangle2D borderRect = new Rectangle2D.Double(256, 0, 1024, 1024);
	private PlayArea area;
	private InfoPanel infoPanel;
	private Player player;
	private Stroke stroke;
	private Stroke borderStroke;

	public static int sizeOfEnemy = 40;
	public static int currentScore = 0;
	public static int comboScore = 0;
	public static double lifePoints = 100;
	
	private int enemies_hit = 0;
	private int enemies_missed = 0;
	private int buttons_pressed = 0;
	private int joystick_moved = 0;
	
	private boolean init = false;

	private long oldProgress = 0;

	public PlayState(GameStateManager gsm, SongHandler sh, SQLConnector sql) {
		super(gsm, sh, sql);
		infoPanel = new InfoPanel(0, 0, sh);
		area = new PlayArea(256, 1024, 1024, 125);
		player = new Player(1280 - 1024 + 1024 / 2, 1024 / 2);
		stroke = new BasicStroke(sizeOfEnemy, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		borderStroke = new BasicStroke(sizeOfEnemy+2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	}

	@Override
	public void init() {
		init = true;
		lifePoints = 100;
		currentScore = 0;
		comboScore = 0;
		oldProgress = 0;
		
		enemies_hit = 0;
		enemies_missed = 0;
		buttons_pressed = 0;
		joystick_moved = 0;
		
		infoPanel.init(sql.getHighscore(sh.getCurrentSong(), sh.getCurrentSongInstance()).getScore());
		
		for(Path p : area.paths)
		{
			p.getEnemysInPath().clear();
		}
		
		ButtonHandler.getButton(1).setColor(sh.getCurrentSongInstance().getButtons().get(0).getColor());
		
		sh.play();
		init = false;
	}

	@Override
	public void update(float factor) {
		if(init)
			return;
		
		long progress = (long) ((sh.getProgress() / 1000) + (Enemy.secondsToEnd * 1000));

		for (ButtonInstance bu : sh.getCurrentSongInstance().getButtonsBetween(oldProgress, progress)) {
			Button b = ButtonHandler.getButton(bu.getButtonID());
			b.setColor(bu.getColor());
		}

		for (ObjectInstance ob : sh.getCurrentSongInstance().getObjectsBetween(oldProgress, progress)) {
			Path p = area.paths.get(ob.getDirection());
			p.addEnemy(ob.getColor(), ob.getDirection(), (int) ob.getLength());
		}
		

		if(progress > sh.getCurrentSongInstance().getEndTime() + Enemy.secondsToEnd*1000*2)
			endGame();

		oldProgress = progress;

		Enemy closedEnemy = null;
		player.update(factor);
		for (Path path : area.paths) {

			Iterator<Enemy> enemyIterator = path.getEnemysInPath().iterator();

			while (enemyIterator.hasNext()) {

				Enemy e = enemyIterator.next();
				if (e.getDistanceFromStart() > Enemy.distanceToOctagon + (sizeOfEnemy * 1.5)) {
					enemyIterator.remove();
					lifePoints -= 5;
					comboScore /= 2;
					enemies_missed++;
				}
				if(closedEnemy == null){
					closedEnemy = e;
				}
				if((Enemy.distanceToOctagon-closedEnemy.getDistanceFromStart()) > (Enemy.distanceToOctagon-e.getDistanceFromStart())){
						closedEnemy = e;
				}
				e.update(factor);
			}
		}

		lifePoints -= 0.002 * factor;
		
		infoPanel.updateIPanel();
		
		if(lifePoints <= 0)
		{
			endGame();
		}
		if(comboScore >= 100)
		{
			comboScore = 0;
			currentScore += 500;
		}

		
		area.count();
		if(closedEnemy == null){
			area.pathPainted(-1, null);
		}else{
			area.pathPainted(closedEnemy.getIndex(), closedEnemy.getColor());
		}
	}

	private void endGame() {
		if(sh.getProgress()/1000 > 5000)	
		{
			sql.addPlaydata(sh.getCurrentSong(), sh.getCurrentSongInstance(), sh.getProgress()/1000, enemies_missed, enemies_hit, buttons_pressed, joystick_moved);
			sh.getCurrentSongInstance().played();
		}
		
		if(currentScore == 0)
			gsm.setState(State.MENU_STATE);
		else
			gsm.setState(State.GAMEOVER_STATE);
	}

	@Override
	public void draw(Graphics2D g2) {
		try {
			infoPanel.draw(g2);
			g2.setClip(borderRect);
			area.draw(g2);

			for (Path p : area.paths) {
				if (p.getEnemysInPath() != null) {
					for (Enemy enemy : p.getEnemysInPath()) {
						g2.setStroke(borderStroke);
						enemy.draw(g2, true);
						g2.setStroke(stroke);
						enemy.draw(g2);
					}
				}
			}

			if (player != null)
				player.draw(g2);
		} catch (Exception e) {
		}
//		if(!Window.ON_RASP){
//			int width,height;
//			width = g2.getFontMetrics().stringWidth("");
//			height = g2.getFontMetrics().getHeight();
//			for (int i = 1; i < ButtonHandler.getButtons().size(); i++) {
//				Ellipse2D oval = new Ellipse2D.Double(880+(50*i), 0, 50, 50);
//				g2.setColor(ButtonHandler.getButton(i).getColor());
//				g2.fill(oval);
//				g2.setColor(Color.BLACK);
//				width = g2.getFontMetrics().stringWidth(""+i);
//				g2.drawString(""+i, (int)oval.getCenterX()-width/2,(int)oval.getMaxY()+height);
//			}
//		}
	}

	@Override
	public void buttonPressed(ButtonEvent e) {
		boolean notHit = true;
		Iterator<Enemy> enemysInPath = area.paths.get(player.getIndex()).getEnemysInPath().iterator();
		while (enemysInPath.hasNext()) {
			Enemy enemy = enemysInPath.next();
			if (enemy.getDistanceFromStart() > Enemy.distanceToOctagon || enemy.getDistanceFromStart() > Enemy.distanceToOctagon + sizeOfEnemy) {
				if (e.getButton().getColor().equals(enemy.getColor())) {
					currentScore += enemy.getDistanceFromStart() - Enemy.distanceToOctagon;
					comboScore += 5;
					lifePoints = Math.min(lifePoints+10, 100);
					area.setHitAreaColor(enemy.getColor());
					area.hit();
					enemies_hit++;
					enemysInPath.remove();
					notHit = false;
					break;
				}
			}
		}
		

		player.setBeat();

		if(notHit)
		{
			if(area.paths.get(player.getIndex()).getEnemysInPath().size() > 0)
			{
				lifePoints -= 1.5;
			}
		}
		
		if(e.getButton().getButtonID() == 0)
		{
			endGame();
		}
		else
			buttons_pressed++;
	}

	@Override
	public void buttonReleased(ButtonEvent e) {
	}

	@Override
	public void onJoystickMoved(JoystickEvent e) {
		switch (e.getJoystick().getPos()) {
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
		joystick_moved++;
	}

	public void addEnemy(int pathID, Color color) {
		Path path = area.paths.get(pathID);
		Enemy e = new Enemy(pathID, 1, color, path);
		path.getEnemysInPath().addLast(e);
	}
}
