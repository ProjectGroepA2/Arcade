package model.objects;

import java.awt.geom.Line2D;
import java.util.ArrayDeque;

import model.drawObjects.enemy.Enemy;

public class Path extends Line2D.Double {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4209367336660836698L;
	private ArrayDeque<Enemy> enemysInPath;
	
	public Path(double x1, double y1, double x2, double y2) {
		super(x1,y1,x2,y2);
		enemysInPath = new ArrayDeque<Enemy>();
	}

	public ArrayDeque<Enemy> getEnemysInPath() {
		return enemysInPath;
	}

	public void setEnemysInPath(ArrayDeque<Enemy> enemysInPath) {
		this.enemysInPath = enemysInPath;
	}	
}
