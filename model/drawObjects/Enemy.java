package model.drawObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import model.objects.Path;

public class Enemy extends DrawObject {

	public static final double distanceToOctagon = 1000,secondsToEnd = 5.0;	
	private int length;
	private double lengthOf1Side,distanceFromStart;//lengthOf1Side wordt alleen gebruikt als de lijn een schuine lijn is.	
	public Line2D enemy;
	private Color c;	
	private Path path;
	
	public Enemy(int pathID,int lengthOfEnemy,Color c,Path path){
		super();		
		this.length = lengthOfEnemy;			
//		System.out.println(this.length*this.length/2);
//		System.out.println(Math.sqrt(Math.pow(this.length,2)/2));
		lengthOf1Side = Math.sqrt(Math.pow(lengthOfEnemy,2)/2);		
		this.c = c;		
		this.index = pathID;
		this.path = path;
		double beginX, beginY,endX = 0,endY = 0;	
		
		beginX = path.getX1();
		beginY = path.getY1();					
		
		//the 8 richtingen van de octagon
		switch(index){
		case 0:				
			endY -= this.length;			
			break;
		case 1:			
			endY -= lengthOf1Side;
			endX += lengthOf1Side;			
			break;
		case 2:				
			endX -= this.length;			
			break;
		case 3:				
			endY += lengthOf1Side;
			endX += lengthOf1Side;			
			break;		
		case 4:			
			endY += this.length;			
			break;
		case 5:			
			endY += lengthOf1Side;
			endX -= lengthOf1Side;			
			break;
		case 6:			
			endX -= this.length;			
			break;
		case 7:						
			endY -= lengthOf1Side;
			endX -= lengthOf1Side;			
			break;				
		}
		
		endX += beginX;
		endY += beginY;
		
		enemy = new Line2D.Double(beginX, beginY, endX, endY);	
//		System.out.println("Enemy added on path: "+pathID);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setPaint(c);
		g2.draw(enemy);		
	}

	@Override
	public void update(float factor) {	
		
		distanceFromStart += (distanceToOctagon/(secondsToEnd*1000))*factor;		
		
		double x1,x2,y1,y2;
		x1 = enemy.getX1();
		x2 = enemy.getX2();
		y1 = enemy.getY1();
		y2 = enemy.getY2();		
		
		double angleX,angleY;
		angleX = Math.cos(Math.toRadians(45))*distanceFromStart;
		angleY = Math.sin(Math.toRadians(45))*distanceFromStart;
		
//		System.out.println(Math.toRadians(45)*distanceFromStart);
//		System.out.println(angleX+" - "+angleY);
		
		switch(index){
		case 0:
			y1 = path.getY1() +  distanceFromStart;
			y2 = y1 - length;				
			break;
		case 1:
//			x1 -= speedX;
			x1 = path.getX1() - angleX;
			x2 = x1 + lengthOf1Side;	
			
			y1 = path.getY1() + angleY;
			y2 = y1 - lengthOf1Side;							
			break;
		case 2:			
			x1 = path.getX1()-distanceFromStart;
			x2 = x1 + length;				
			break;
		case 3:			
			x1 = path.getX1() - angleX;
			x2 = x1 + lengthOf1Side;
			
			y1 = path.getY1() - angleY;
			y2 = y1 + lengthOf1Side;
			break;		
		case 4:
			y1 = path.getY1() - distanceFromStart;
			y2 = y1 + length;		
			break;
		case 5:
			x1 = path.getX1() + angleX;
			x2 = x1 - lengthOf1Side;
			
			y1 = path.getY1() - angleY;
			y2 = y1 + lengthOf1Side;
			break;
		case 6:
			x1 = path.getX1() + distanceFromStart;
			x2 = x1 - length;				
			break;
		case 7:
			x1 = path.getX1() + angleX;
			x2 = x1 - lengthOf1Side;
			
			y1 = path.getY1() + angleY;
			y2 = y1 - lengthOf1Side;
			break;					
		}		
		enemy.setLine(x1, y1, x2, y2);				
	}
	

	public Color getColor() {
		return c;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public Line2D getEnemy() {
		return enemy;
	}

	public void setBullet(Line2D bullet) {
		this.enemy = bullet;
	}

	public double getDistanceFromStart() {
		return distanceFromStart;
	}	
}
