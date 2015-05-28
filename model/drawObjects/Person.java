package model.drawObjects;

import java.awt.geom.Point2D;

public abstract class Person extends DrawObject {

	protected Point2D middlePoint;
	
	public Person(double x, double y) {
		super();
		middlePoint = new Point2D.Double(x, y);		
	}
}
