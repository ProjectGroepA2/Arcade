package model.drawObjects.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import model.drawObjects.DrawObject;

public class Enemy extends DrawObject {

	
	private int length,timeToClick = 5,currentTimeToClick;	
	private double speedX,speedY,lengthOf1Side;//lengthOf1Side wordt alleen gebruikt als de lijn een schuine lijn is.
	public Line2D enemy;
	private Color c;	
	private boolean clickable = false;
	/**
	 * 
	 * @param pathID
	 * @param lengthOfEnemy
	 * @param c
	 * @param beginPoint
	 * @param endPoint
	 * @param steptsToEndPoint
	 */
	public Enemy(int pathID,int lengthOfEnemy,Color c,double steptsToEndPoint,Point2D beginPoint,Point2D endPoint){
		super();		
		this.length = lengthOfEnemy;			
//		System.out.println(this.length*this.length/2);
//		System.out.println(Math.sqrt(Math.pow(this.length,2)/2));
		lengthOf1Side = Math.sqrt(Math.pow(lengthOfEnemy,2)/2);		
		this.c = c;		
		this.index = pathID;				
		double beginX, beginY,endX = 0,endY = 0;
		beginX =  beginPoint.getX();
		beginY = beginPoint.getY();
		endX = beginX;
		endY = beginY;		
		
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
			endX += this.length;			
			break;
		case 7:
			endY -= lengthOf1Side;
			endX -= lengthOf1Side;			
			break;				
		}
			
		
		enemy = new Line2D.Double(beginX, beginY, endX, endY);	
		//bereken de lengte van de enemy tot het einde van de lijn
		double distanceX,distanceY;
		distanceX = (enemy.getX1() - endPoint.getX());
		distanceY = (enemy.getY1() - endPoint.getY());		
		//de afstand mag niet negatief zijn
		if(distanceX < 0){
			distanceX *= -1;
		}
		if(distanceY < 0){
			distanceY *= -1;
		}
//		System.out.println("Index: "+index+"\tX afstand: "+distanceX+"\tY afstand: "+distanceY);
		//snelheid 	= afstand 	/tijd		
		speedX 		= (distanceX	/steptsToEndPoint);
		speedY 		= (distanceY	/steptsToEndPoint);
//		System.out.println("Index: "+index+"\tX speed: "+speedX+"\tY speed: "+speedY);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setPaint(c);
		g2.draw(enemy);		
	}

	@Override
	public void update() {	
		if(clickable){
			currentTimeToClick++;
		}
			double x1,x2,y1,y2;
			x1 = enemy.getX1();
			x2 = enemy.getX2();
			y1 = enemy.getY1();
			y2 = enemy.getY2();		
			
			switch(index){
			case 0:
				y1 += speedY;
				y2 = y1 - length;				
				break;
			case 1:
				x1 -= speedX;
				x2 = x1 + lengthOf1Side;	
				
				y1 += speedY;
				y2 = y1 - lengthOf1Side;							
				break;
			case 2:			
				x1 -= speedX;
				x2 = x1 + length;				
				break;
			case 3:			
				x1 -= speedX;
				x2 = x1 + lengthOf1Side;
				
				y1 -= speedY;
				y2 = y1 + lengthOf1Side;
				break;		
			case 4:
				y1 -= speedY;
				y2 = y1 + length;		
				break;
			case 5:
				x1 += speedX;
				x2 = x1 - lengthOf1Side;
				
				y1 -= speedY;
				y2 = y1 + lengthOf1Side;
				break;
			case 6:
				x1 += speedX;
				x2 = x1 - length;				
				break;
			case 7:
				x1 += speedX;
				x2 = x1 - lengthOf1Side;
				
				y1 += speedY;
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
	
	public void clickable(){
		clickable = true;
	}
	
	/**
	 * deze methode kijkt hoeveel frame je nog hebt om te klikken op het moment dat je bolletje in de hitzone zit
	 * @return
	 */
	public boolean finised(){
		if(currentTimeToClick >= timeToClick){
			return true;
		}
		return false;
	}
	
	public boolean isClickable(){
		return clickable;
	}
	
	public int getTimeLeftToClick() {
		return currentTimeToClick;
	}

}
