package model.drawObjects;

import image.Images;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Person {
	
	private BufferedImage img;	
	
	public Player(int x, int y){
		super(x,y);		
		img = Images.getImage(Images.ImageType.player2);	
		width = img.getWidth();
		height = img.getHeight();		
	}
	
	public void draw(Graphics2D g2){//		
		g2.drawImage(img, transform, null);		
		
	}
	
	public void update(){
		transform = new AffineTransform();
		transform.rotate(Math.toRadians(index*45),middlePoint.getX(),middlePoint.getY());
		transform.translate(middlePoint.getX() - width/2, middlePoint.getY() - height);	
	}
}
	
