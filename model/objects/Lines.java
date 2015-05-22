package model.objects;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Lines {

	private List<Shape> lines;
	private Polygon octagon;
	
	
	public Lines(int xToRight, int sizeOctagon) {
		super();
		lines = new ArrayList<Shape>();
		int middlePointX = 512+xToRight;
		int middlePointY = 512;
		int amountOfAngles = 8;
		octagon = new Polygon();		
		for(int i = 0; i < amountOfAngles; i++){
			octagon.addPoint((int)(middlePointX+sizeOctagon*Math.cos(i*Math.PI/(amountOfAngles/2))), 
							 (int)(middlePointY+sizeOctagon*Math.sin(i*Math.PI/(amountOfAngles/2))));
		}
		AffineTransform t = new AffineTransform();
		for(int i = 0; i < 8; i++){
			t.rotate(Math.toRadians(45), middlePointX, 512);
			lines.add(t.createTransformedShape(new Line2D.Double(middlePointX, -213, middlePointX, 512)));
			
		}
	}	
	
	public void draw(Graphics2D g2){
		for(Shape s : lines){
			g2.draw(s);
		}
		g2.draw(octagon);
	}
}
