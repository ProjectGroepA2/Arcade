package model.drawObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Enemy extends Person{

	private Color 				color;
	private Ellipse2D.Double 	circle;
	private Point2D				beginPoint;
	private Dimension			size;
	private double xSpeed,ySpeed;
	
	/**
	 * 
	 * @param path, the path the player is walking on.
	 * @param c, the color of the enemy
	 * @param size, the size of the enemy
	 * @param stepsToFinishTheMiddle, the speed to finish the middle octagon
	 */
	public Enemy(Line2D path,Color c,int size,double stepsToFinishTheMiddle) {
		
		super(path.getX1()-size/2,path.getY1()-size/2);	
		beginPoint = new Point2D.Double(middlePoint.getX(),middlePoint.getY());
		this.size = new Dimension(size,size);
		color = c;
		circle = new Ellipse2D.Double(middlePoint.getX(),middlePoint.getY(),this.size.width,this.size.height);
		index = 0;
		xSpeed = (path.getX2() - circle.getCenterX())/stepsToFinishTheMiddle;
		ySpeed = (path.getY2() - circle.getCenterY())/stepsToFinishTheMiddle;		
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setPaint(color);
		g2.fill(circle);
	}
	
	private void moveTowardsPlayer()
	{
		
		double newX, newY;			
		newX = index*xSpeed+beginPoint.getX();
		newY = index*ySpeed+beginPoint.getY();
		index++;
		middlePoint.setLocation(newX, newY);		
		circle.setFrame(middlePoint, size);		
	}

	@Override
	public void update() {		
		moveTowardsPlayer();		
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Ellipse2D.Double getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D.Double circle) {
		this.circle = circle;
	}
	
	/**
	 * Deze methode kijkt of de enemy is geraakt door een bullet
	 * @param bullet, een kogel om te kijken of hij deze enemy heeft geraakt
	 * @return, true = raakt, false = mis
	 */
	public boolean bulletHitMe(Bullet bullet){
		if(circle.getBounds2D().intersectsLine(bullet.getBullet())){
			return true;
		}
		return false;
	}
	
	/**
	 * Hier kijk je of de bullet de juiste kleur heeft, om de enemy te vermoorden
	 * @param bullet, de kogel om te kije of hij de zelfde kleur heeft als deze enemy
	 * @return,true = zelfde kleur, false = andere kleur
	 */
	public boolean ColorHitMe(Bullet bullet){
		if(this.color.equals(bullet.getColor())){
			return true;
		}
		return false;
	}
	
}
