package model.drawObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Bullet extends DrawObject {

	private double speed,lengthOfBullet;
	
	public Line2D bullet;
	private Color c;
	
	public Bullet(double speed,Color c,double lengthOfBullet,int index,Line2D path) {
		super();
		this.speed = speed;
		this.lengthOfBullet = lengthOfBullet;
		this.c = c;
		double beginX,beginY,endX,endY;
		
		beginX = path.getX2();
		beginY = path.getY2();
		endX = beginX;
		endY = beginY;
		
		//the 8 richtingen van de octagon
		switch(index){
		case 0:	
			endY = beginY - lengthOfBullet;
			break;
		case 1:
			endX = beginX + lengthOfBullet;
			endY = beginY - lengthOfBullet;
			break;
		case 2:
			endX = beginX + lengthOfBullet;
			break;
		case 3:
			endX = beginX + lengthOfBullet;
			endY = beginY + lengthOfBullet;
			break;
		case 4:
			endY = beginY + lengthOfBullet;
			break;
		case 5:
			endX = beginX - lengthOfBullet;
			endY = beginY + lengthOfBullet;
			break;
		case 6:
			endX = beginX - lengthOfBullet;
			break;
		case 7:
			endX = beginX - lengthOfBullet;
			endY = beginY - lengthOfBullet;
			break;		
		}
		bullet = new Line2D.Double(beginX, beginY, endX, endY);
//		System.out.println("Index: "+index);
//		System.out.println("X difference: "+(bullet.getX2()-bullet.getX1()));
//		System.out.println("Y difference: "+(bullet.getY2()-bullet.getY1()));
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setPaint(c);
		g2.draw(bullet);
	}

	@Override
	public void update() {		
		double x1,x2,y1,y2,yDifference,xDifference;
		x1 = bullet.getX1();
		x2 = bullet.getX2();
		y1 = bullet.getY1();
		y2 = bullet.getY2();
		yDifference = y2 - y1;
		xDifference = x2 - x1;		
				
		if(yDifference < 0.0){			
			y1 -= speed;
			y2 = y1-lengthOfBullet;
		}else if(yDifference > 0.0){
			y1 += speed;
			y2 = y1+lengthOfBullet;
		}
		
		if(xDifference < 0.0){			
			x1 -= speed;
			x2 = x1-lengthOfBullet;
		}else if(xDifference > 0.0){
			x1 += speed;
			x2 = x1+lengthOfBullet;
		}		
		
		bullet.setLine(x1, y1, x2, y2);
	}

	public Color getColor() {
		return c;
	}

	public void setColor(Color c) {
		this.c = c;
	}	
	
	
}
