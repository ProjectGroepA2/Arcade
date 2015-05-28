package model.drawObjects;

import image.Images;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.gameState.PlayState;
import model.objects.PlayArea;

public class Player extends Person {
	
	private BufferedImage img;
	private PlayArea path ;
	public List<Bullet> bullets;
	
	public Player(int x, int y,PlayArea paths){
		super(x,y);
		this.path = paths;
		img = Images.getImage(Images.ImageType.player2);	
		width = img.getWidth();
		height = img.getHeight();
		bullets = Collections.synchronizedList(new ArrayList<Bullet>());
	}
	
	public void draw(Graphics2D g2){//		
		g2.drawImage(img, transform, null);	
		
		g2.setStroke(new BasicStroke(5));
		for(Bullet b : bullets){
			b.draw(g2);
		}
	}
	
	public void update(){
		transform = new AffineTransform();
		transform.rotate(Math.toRadians(index*45),middlePoint.getX(),middlePoint.getY());
		transform.translate(middlePoint.getX() - width/2, middlePoint.getY() - height);
		for(Bullet b : bullets){			
			b.update();
		}
		Iterator<Bullet> i = bullets.iterator();
		while(i.hasNext()){
			if(!PlayState.borderRect.intersectsLine(i.next().bullet)){
				i.remove();
			}
		}
	}
	
	public void addBullet(Color c){	
//		System.out.println(index);
		bullets.add(new Bullet(10, c, 10, index,path.paths.get(index)));		
	}
	
	public void removeBullet(Bullet bullet){
		bullets.remove(bullet);
	}
}
