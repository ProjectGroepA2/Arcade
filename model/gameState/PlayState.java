package model.gameState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

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

public class PlayState extends GameState {

	public static final Rectangle2D borderRect = new Rectangle2D.Double(256, 0, 1024, 1024);
	private PlayArea area;
	private InfoPanel infoPanel;
	private Player player;
	private Stroke stroke;

	public static int sizeOfEnemy = 40;
	public static int currentScore = 0;
	public static int comboScore = 0;
	public static double lifePoints = 100;

	private long oldProgress = 0;

	public PlayState(GameStateManager gsm, SongHandler sh) {
		super(gsm, sh);
		infoPanel = new InfoPanel(0, 0);
		area = new PlayArea(256, 1024, 1024, 125);
		player = new Player(1280 - 1024 + 1024 / 2, 1024 / 2);
		stroke = new BasicStroke(sizeOfEnemy, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	}

	@Override
	public void init() {
		
		lifePoints = 100;
		currentScore = 0;
		comboScore = 0;
		oldProgress = 0 ;
		
		for(Path p : area.paths)
		{
			p.getEnemysInPath().clear();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sh.play();

		for (int i = 1; i < ButtonHandler.getButtons().size(); i++) {
			Button b = ButtonHandler.getButton(i);
			b.setColor(Color.BLACK);
		}
		
		ButtonHandler.getButton(1).setColor(sh.getCurrentSongInstance().getButtons().get(0).getColor());

	}

	@Override
	public void update(float factor) {

		long progress = (long) ((sh.getProgress() / 1000) + (Enemy.secondsToEnd * 1000));

		for (ButtonInstance bu : sh.getCurrentSongInstance().getButtonsBetween(oldProgress, progress)) {
			Button b = ButtonHandler.getButton(bu.getButtonID());
			b.setColor(bu.getColor());
		}

		for (ObjectInstance ob : sh.getCurrentSongInstance().getObjectsBetween(oldProgress, progress)) {
			Path p = area.paths.get(ob.getDirection());
			p.addEnemy(ob.getColor(), ob.getDirection(), (int) ob.getLength());
		}

		oldProgress = progress;

		player.update(factor);
		for (Path path : area.paths) {

			Iterator<Enemy> enemyIterator = path.getEnemysInPath().iterator();

			while (enemyIterator.hasNext()) {

				Enemy e = enemyIterator.next();

				if (e.getDistanceFromStart() > Enemy.distanceToOctagon + (sizeOfEnemy * 1.5)) {
					enemyIterator.remove();
					lifePoints -= 5;
					comboScore /= 2;
				}

				e.update(factor);
			}
		}

		lifePoints -= 0.002 * factor;
		
		if(lifePoints <= 0)
			gsm.setState(State.MENU_STATE);
		if(comboScore >= 100)
		{
			comboScore = 0;
			currentScore += 500;
		}

		infoPanel.updateIPanel();
	}

	@Override
	public void draw(Graphics2D g2) {
		try {
			infoPanel.draw(g2);
			g2.setClip(borderRect);
			area.draw(g2);

			g2.setStroke(stroke);
			for (Path p : area.paths) {
				if (p.getEnemysInPath() != null) {
					for (Enemy enemy : p.getEnemysInPath()) {
						enemy.draw(g2);
					}
				}
			}

			if (player != null)
				player.draw(g2);
		} catch (Exception e) {
		}
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
			gsm.setState(State.MENU_STATE);
		}
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
	}

	public void addEnemy(int pathID, Color color) {
		Path path = area.paths.get(pathID);
		Enemy e = new Enemy(pathID, 1, color, path);
		path.getEnemysInPath().addLast(e);
	}
}
