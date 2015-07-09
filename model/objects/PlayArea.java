package model.objects;

import image.Images;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;

import model.drawObjects.Enemy;
import model.gameState.PlayState;

public class PlayArea {

	public List<Path> paths;
	private Polygon innerHitAreaBorder,outsideHitAreaBorder;	
	private GeneralPath hitArea;
	private Color hitAreaColor,pathColor = null;
	private VolatileImage background;
	private boolean hit = false;
	private int count = 0,maxCount = 100,pathID = -1;
	private Stroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
	
	private Rectangle2D backgroundPlay = new Rectangle2D.Double(256, 0, 1024, 1024);
	
	
	public PlayArea(double xToRight,double heightOfGameScreen,double widthOfGameScreen, int sizeOctagon) {
		super();
		paths = new ArrayList<Path>();		
		setHitAreaColor(Color.WHITE);
		//make the polygons
		int amountOfAngles = 8;
		double middlePointX = widthOfGameScreen/2+xToRight;
		double middlePointY = heightOfGameScreen/2;
		
		//the inner octagon
		innerHitAreaBorder = new Polygon();		
		for(int i = 0; i < amountOfAngles; i++){
			innerHitAreaBorder.addPoint((int)(middlePointX+sizeOctagon*Math.cos(i*Math.PI/(amountOfAngles/2))), 
							 (int)(middlePointY+sizeOctagon*Math.sin(i*Math.PI/(amountOfAngles/2))));			
		}
		
		//the outside octagon
		outsideHitAreaBorder = new Polygon();
		sizeOctagon += PlayState.sizeOfEnemy;		
		for(int i = 0; i < amountOfAngles; i++){
			outsideHitAreaBorder.addPoint((int)(middlePointX+sizeOctagon*Math.cos(i*Math.PI/(amountOfAngles/2))), 
							 (int)(middlePointY+sizeOctagon*Math.sin(i*Math.PI/(amountOfAngles/2))));
		}
		
		//hitArea the area where you must click the enemy
		hitArea = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		
		hitArea.moveTo(innerHitAreaBorder.xpoints[0], innerHitAreaBorder.ypoints[0]);
		for(int i = 1; i < innerHitAreaBorder.npoints;i++){
			hitArea.lineTo(innerHitAreaBorder.xpoints[i], innerHitAreaBorder.ypoints[i]);
		}
		
		hitArea.moveTo(outsideHitAreaBorder.xpoints[0], outsideHitAreaBorder.ypoints[0]);
		for(int i = 1; i < innerHitAreaBorder.npoints;i++){
			hitArea.lineTo(outsideHitAreaBorder.xpoints[i], outsideHitAreaBorder.ypoints[i]);
		}		
		
		//make the paths
		double angleX,angleY;
		angleX = (Math.cos(Math.toRadians(45)))*Enemy.distanceToOctagon;
		angleY = (Math.sin(Math.toRadians(45)))*Enemy.distanceToOctagon;
		
		paths.add(new Path(outsideHitAreaBorder.xpoints[6],outsideHitAreaBorder.ypoints[6]-Enemy.distanceToOctagon		,outsideHitAreaBorder.xpoints[6],outsideHitAreaBorder.ypoints[6]));//top
		paths.add(new Path(outsideHitAreaBorder.xpoints[7] + angleX,outsideHitAreaBorder.ypoints[7] - angleY				,outsideHitAreaBorder.xpoints[7],outsideHitAreaBorder.ypoints[7]));//right 	-top
		paths.add(new Path(outsideHitAreaBorder.xpoints[0]+Enemy.distanceToOctagon,outsideHitAreaBorder.ypoints[0]		,outsideHitAreaBorder.xpoints[0],outsideHitAreaBorder.ypoints[0]));//right
		paths.add(new Path(outsideHitAreaBorder.xpoints[1]+angleX,outsideHitAreaBorder.ypoints[1]+angleY					,outsideHitAreaBorder.xpoints[1],outsideHitAreaBorder.ypoints[1]));//right	-down
		paths.add(new Path(outsideHitAreaBorder.xpoints[2],outsideHitAreaBorder.ypoints[2]+Enemy.distanceToOctagon		,outsideHitAreaBorder.xpoints[2],outsideHitAreaBorder.ypoints[2]));//down
		paths.add(new Path(outsideHitAreaBorder.xpoints[3] - angleX,outsideHitAreaBorder.ypoints[3] + angleY				,outsideHitAreaBorder.xpoints[3],outsideHitAreaBorder.ypoints[3]));//left		-down
		paths.add(new Path(outsideHitAreaBorder.xpoints[4] - Enemy.distanceToOctagon,outsideHitAreaBorder.ypoints[4]		,outsideHitAreaBorder.xpoints[4],outsideHitAreaBorder.ypoints[4]));//left
		paths.add(new Path(outsideHitAreaBorder.xpoints[5] - angleX,outsideHitAreaBorder.ypoints[5] - angleY				,outsideHitAreaBorder.xpoints[5],outsideHitAreaBorder.ypoints[5]));//left	 	-top
		
		
		//drawing into buffer
		background = Images.initVolatileImage(1280,1024, Transparency.BITMASK);
		Graphics2D g2 = background.createGraphics();		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
//	    g2.setColor(Color.WHITE);
//	    g2.fillRect(256, 0, 1024, 1024);
	    Line2D current;	
		g2.setColor(Color.BLACK);
		g2.setStroke(stroke);
		for(int i = 0; i < paths.size(); i++){
			current = paths.get(i);			
			g2.draw(current);
		}
		g2.draw(innerHitAreaBorder);
		g2.draw(outsideHitAreaBorder);
		g2.dispose();
		background.createGraphics();
	}	
	
	public void draw(Graphics2D g2){		
		g2.drawImage(background, 0, 0, 1280, 1024, null);
		if(hit){
			g2.setColor(hitAreaColor);
			g2.fill(hitArea);
			if(count == maxCount){
				hit = false;
			}
		}		
		if(pathID >= 0 && pathColor != null){	
			g2.setColor(new Color(pathColor.getRed(), pathColor.getGreen(), pathColor.getBlue(),50));
			g2.fill(backgroundPlay);
			g2.setStroke(stroke);
			g2.setColor(pathColor);
			g2.draw(paths.get(pathID));
		}
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
	

	public void setHitAreaColor(Color color) {	
		hitAreaColor = color;	
		count = 0;
	}
	
	public void hit(){
		hit = true;
	}	
	
	public void count(){
		if(hit){
			count += 3;
			if(count > maxCount){
				count = maxCount;
			}
			hitAreaColor = new Color(hitAreaColor.getRed(),hitAreaColor.getGreen(),hitAreaColor.getBlue(),255-(255*count)/maxCount);	
		}
	}
	
	public void pathPainted(int pathID,Color pathColor){
		this.pathID = pathID;
		this.pathColor = pathColor;
	}
}
