package model.objects;

import image.Images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;

import model.drawObjects.Enemy;
import model.gameState.PlayState;

public class PlayArea {

	public List<Path> paths;
	private Polygon octagon,hitArea;	
	public Rectangle2D[] hitAreas; //de area voor elk path die de enemy moet raken
	private VolatileImage background;
	
	public PlayArea(double xToRight,double heightOfGameScreen,double widthOfGameScreen, int sizeOctagon) {
		super();
		paths = new ArrayList<Path>();		
		
		int amountOfAngles = 8;
		double middlePointX = widthOfGameScreen/2+xToRight;
		double middlePointY = heightOfGameScreen/2;
		
		octagon = new Polygon();		
		for(int i = 0; i < amountOfAngles; i++){
			octagon.addPoint((int)(middlePointX+sizeOctagon*Math.cos(i*Math.PI/(amountOfAngles/2))), 
							 (int)(middlePointY+sizeOctagon*Math.sin(i*Math.PI/(amountOfAngles/2))));			
		}
		
		hitArea = new Polygon();
		sizeOctagon += PlayState.sizeOfEnemy;		
		for(int i = 0; i < amountOfAngles; i++){
			hitArea.addPoint((int)(middlePointX+sizeOctagon*Math.cos(i*Math.PI/(amountOfAngles/2))), 
							 (int)(middlePointY+sizeOctagon*Math.sin(i*Math.PI/(amountOfAngles/2))));
		}
		
		hitAreas = new Rectangle2D[amountOfAngles];
		int newIndex;
		Point2D beginPoint,endPoint;
		for(int index = 0; index < hitAreas.length; index++){
			//in het polygon staat de cooridinaten van de top niet als eerste in de array, maar op index 6.n
			newIndex = (index+6+8)%8;	
			hitAreas[index] = new Rectangle2D.Double();
			beginPoint = new Point2D.Double(octagon.xpoints[newIndex], octagon.ypoints[newIndex]);
			endPoint = new Point2D.Double(hitArea.xpoints[newIndex], hitArea.ypoints[newIndex]);			
			hitAreas[index].setFrameFromDiagonal(beginPoint,endPoint);			
		}
		
		
		Rectangle2D cr = null;//cr --> current rectangle
		double size = (hitAreas[1].getWidth() - 10)/2;//dit is de grootte van een zijde als die van de oorspronklijke 0 was.
		
		cr = hitAreas[0];
		hitAreas[0].setFrame(cr.getX()-size/2, cr.getY()+1, size, cr.getHeight());
		
		cr = hitAreas[2];
		hitAreas[2].setFrame(cr.getX()-1, cr.getY()-size/2, cr.getWidth(), size);
		
		cr = hitAreas[4];
		hitAreas[4].setFrame(cr.getX()-size/2, cr.getY()-1, size, cr.getHeight());
		
		cr = hitAreas[6];
		hitAreas[6].setFrame(cr.getX()+1, cr.getY()-size/2, cr.getWidth(), size);
		
		widthOfGameScreen += xToRight;			
		
//		paths.add(new Path(middlePointX,0						,hitArea.xpoints[6],hitArea.ypoints[6]));//top
//		paths.add(new Path(widthOfGameScreen,0					,hitArea.xpoints[7],hitArea.ypoints[7]));//right 	-top
//		paths.add(new Path(widthOfGameScreen,middlePointY		,hitArea.xpoints[0],hitArea.ypoints[0]));//right
//		paths.add(new Path(widthOfGameScreen,heightOfGameScreen,hitArea.xpoints[1],hitArea.ypoints[1]));//right	-down
//		paths.add(new Path(middlePointX,heightOfGameScreen		,hitArea.xpoints[2],hitArea.ypoints[2]));//down
//		paths.add(new Path(xToRight,heightOfGameScreen			,hitArea.xpoints[3],hitArea.ypoints[3]));//left		-down
//		paths.add(new Path(xToRight,middlePointY				,hitArea.xpoints[4],hitArea.ypoints[4]));//left
//		paths.add(new Path(xToRight,0							,hitArea.xpoints[5],hitArea.ypoints[5]));//left	 	-top
		
		double angleX,angleY;
		angleX = (Math.cos(Math.toRadians(45)))*Enemy.distanceToOctagon;
		angleY = (Math.sin(Math.toRadians(45)))*Enemy.distanceToOctagon;
		
		paths.add(new Path(hitArea.xpoints[6],hitArea.ypoints[6]-Enemy.distanceToOctagon		,hitArea.xpoints[6],hitArea.ypoints[6]));//top
		paths.add(new Path(hitArea.xpoints[7] + angleX,hitArea.ypoints[7] - angleY				,hitArea.xpoints[7],hitArea.ypoints[7]));//right 	-top
		paths.add(new Path(hitArea.xpoints[0]+Enemy.distanceToOctagon,hitArea.ypoints[0]		,hitArea.xpoints[0],hitArea.ypoints[0]));//right
		paths.add(new Path(hitArea.xpoints[1]+angleX,hitArea.ypoints[1]+angleY					,hitArea.xpoints[1],hitArea.ypoints[1]));//right	-down
		paths.add(new Path(hitArea.xpoints[2],hitArea.ypoints[2]+Enemy.distanceToOctagon		,hitArea.xpoints[2],hitArea.ypoints[2]));//down
		paths.add(new Path(hitArea.xpoints[3] - angleX,hitArea.ypoints[3] + angleY				,hitArea.xpoints[3],hitArea.ypoints[3]));//left		-down
		paths.add(new Path(hitArea.xpoints[4] - Enemy.distanceToOctagon,hitArea.ypoints[4]		,hitArea.xpoints[4],hitArea.ypoints[4]));//left
		paths.add(new Path(hitArea.xpoints[5] - angleX,hitArea.ypoints[5] - angleY				,hitArea.xpoints[5],hitArea.ypoints[5]));//left	 	-top
		
		
		//drawing into buffer
		background = Images.initVolatileImage(1280,1024, Transparency.BITMASK);
		Graphics2D g2 = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
	    Line2D current;
		int index;
		g2.setColor(Color.BLACK);
		for(int i = 0; i < paths.size(); i++){
			current = paths.get(i);
			index = (i+14)%8;
			g2.drawLine((int)current.getX1(), (int)current.getY1(), octagon.xpoints[index],octagon.ypoints[index]);
		}
		g2.draw(octagon);
		g2.draw(hitArea);
		g2.dispose();
		background.createGraphics();
	}	
	
	public void draw(Graphics2D g2){
		g2.drawImage(background, 0, 0, 1280, 1024, null);
	}
	
	public Line2D getLine(int index){
		if(index < 0){
			index = 0;
		}
		if(index >= 8 ){
			index = 7;
		}
		return paths.get(index);
	}
}
