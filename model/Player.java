package model;

import image.Images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Player {

	private Point2D middlePoint;
	private int index = 2,width,height;
	private AffineTransform transform;
	private BufferedImage img;
	
	public Player(int x, int y){
		middlePoint = new Point2D.Double(x, y);	
		transform = new AffineTransform();
		img = Images.getImage(Images.ImageType.player);
		width = img.getWidth();
		height = img.getHeight();
	}
	
	public void draw(Graphics2D g2){//		
		g2.drawImage(img, transform, null);
		g2.setPaint(Color.RED);		
	}
	
	public void update(){
		transform = new AffineTransform();
		transform.rotate(Math.toRadians(index*45),middlePoint.getX(),middlePoint.getY());
		transform.translate(middlePoint.getX() - width/2, middlePoint.getY() - height*2);			
	}
}
