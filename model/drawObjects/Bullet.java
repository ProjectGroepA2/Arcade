package model.drawObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Bullet extends DrawObject {

	private double speed,lengthOfBullet;
	private Line2D bullet;
	private Color c;
	
	public Bullet(double speed,Color c,double lengthOfBullet,int index,Line2D path) {
		super();
		this.speed = speed;
		this.lengthOfBullet = lengthOfBullet;
		this.c = c;
		
		double beginX = path.getX2();
		double beginY = path.getY2();
		double endX = beginX;
		double endY = beginY;
		
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
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setPaint(c);
		g2.draw(bullet);
	}

	@Override
	public void update() {		
		double x1 = bullet.getX1();
		double x2 = bullet.getX2();
		double y1 = bullet.getY1();
		double y2 = bullet.getY2();
		double yDifference = y2 - y1;
		double xDifference = x2 - x1;		
				
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

	public Line2D getBullet() {
		return bullet;
	}

	public void setBullet(Line2D bullet) {
		this.bullet = bullet;
	}		
}
