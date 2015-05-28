package model.drawObjects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public abstract class DrawObject {

	
	protected AffineTransform transform;
	protected double width,height;
	protected int index = 0;
	public DrawObject() {	
		transform = new AffineTransform();
	}	

	public int getIndex(){
		return index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	
	public abstract void draw(Graphics2D g2);
	public abstract void update();
}
