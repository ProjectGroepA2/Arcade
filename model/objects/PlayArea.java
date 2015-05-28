package model.objects;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class PlayArea {

	public List<Line2D> paths;
	public Polygon octagon;
	
	
	public PlayArea(int xToRight,int heightOfGameScreen,int widthOfGameScreen, int sizeOctagon) {
		super();
		paths = new ArrayList<Line2D>();
		int middlePointX = widthOfGameScreen/2+xToRight;
		int middlePointY = heightOfGameScreen/2;
		int amountOfAngles = 8;
		
		octagon = new Polygon();		
		for(int i = 0; i < amountOfAngles; i++){
			octagon.addPoint((int)(middlePointX+sizeOctagon*Math.cos(i*Math.PI/(amountOfAngles/2))), 
							 (int)(middlePointY+sizeOctagon*Math.sin(i*Math.PI/(amountOfAngles/2))));
		}
		
		widthOfGameScreen += xToRight;	
		
		
		paths.add(new Line2D.Double(middlePointX,0						,octagon.xpoints[6],octagon.ypoints[6]));//top
		paths.add(new Line2D.Double(widthOfGameScreen,0					,octagon.xpoints[7],octagon.ypoints[7]));//right 	-top
		paths.add(new Line2D.Double(widthOfGameScreen,middlePointY		,octagon.xpoints[0],octagon.ypoints[0]));//right
		paths.add(new Line2D.Double(widthOfGameScreen,heightOfGameScreen,octagon.xpoints[1],octagon.ypoints[1]));//right	-down
		paths.add(new Line2D.Double(middlePointX,heightOfGameScreen		,octagon.xpoints[2],octagon.ypoints[2]));//down
		paths.add(new Line2D.Double(xToRight,heightOfGameScreen			,octagon.xpoints[3],octagon.ypoints[3]));//left		-down
		paths.add(new Line2D.Double(xToRight,middlePointY				,octagon.xpoints[4],octagon.ypoints[4]));//left
		paths.add(new Line2D.Double(xToRight,0							,octagon.xpoints[5],octagon.ypoints[5]));//left	 	-top		
	}	
	
	public void draw(Graphics2D g2){
		for(Shape s : paths){
			g2.draw(s);
		}
		g2.draw(octagon);
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
