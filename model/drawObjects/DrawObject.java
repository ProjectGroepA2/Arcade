package model.drawObjects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class DrawObject {

	protected Point2D middlePoint;
	protected AffineTransform transform;
	protected int index = 0,width,height;
	
	public DrawObject(int x, int y) {
		middlePoint = new Point2D.Double(x, y);
		transform = new AffineTransform();
	}
	
	public abstract void draw(Graphics2D g2);
	public abstract void update();
}
