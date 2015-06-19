package model.drawObjects;

import image.Images;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Player extends DrawObject {
	
	private BufferedImage img,pulse;	
	private AffineTransform transform;
	private int lastindex;
	private Point2D middlePoint;
	
	private int subImgX,subImgY, indexCursor =0;
	
	private int subImgXBeat, indexBeat = 0;
	private boolean beat = false;
	
	public Player(int x, int y){
		img = Images.getImage(Images.ImageType.cursor);	
		pulse = Images.getImage(Images.ImageType.pulse);
		width = 100;//img.getWidth();
		height = 100;//img.getHeight();	
		lastindex = -10;
		middlePoint = new Point2D.Double(x,y);
	}
	
	public void draw(Graphics2D g2){//		
		subImgX 	= 	(indexCursor % 8) *	100;
		subImgY 	= 	(indexCursor / 8) *	100;
		subImgXBeat = 	(indexBeat % 9  ) *	100;

		BufferedImage subImg2 = pulse.getSubimage(subImgXBeat,0,100,100);
		g2.drawImage(subImg2, transform, null);	

		BufferedImage subImg = img.getSubimage(subImgX,subImgY,100,100);
		g2.drawImage(subImg, transform, null);
	}
	
	public void update(float update){
        if(beat){
            indexBeat++;
            if(indexBeat >= 9) {
            	indexBeat = 0;
            	beat = false;
            }
          }

        indexCursor++;
		indexCursor%=32;
        
		if(lastindex != index){
			transform = new AffineTransform();
			transform.rotate(Math.toRadians(index*45),middlePoint.getX(),middlePoint.getY());
			transform.translate(middlePoint.getX() - width/2, middlePoint.getY() - height);
			lastindex = index;
		}
	}
	
	public void setBeat() {
		beat = true;
		indexBeat = 0;
	}
}
	
