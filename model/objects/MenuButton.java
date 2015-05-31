package model.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MenuButton {

	private ArrayList<GeneralPath> buttonparts;
	private GeneralPath border, line;
	private ArrayList<Color> colors;
	private Paint gradient;
	private int x, y, rounding;
	private double scalefactor;
	private String text;
	
	private boolean selected; 
	private int fadecounter;
	
	public MenuButton(int x, int y, double scale, String text, int rounding, Color color){
		this.x = x;
		this.y = y;
		this.scalefactor = scale;
		this.text = text;
		this.rounding = rounding;
		calculateButton();
		
		line = arrayToGeneralpath(new int[][]{{-11,55},{-40,60},{-38,78},{-358,170-rounding*3},{-358,174-rounding*3},{-37,87},{-35,102},{-5,95}});
		gradient = new LinearGradientPaint(
							new Point2D.Double((-358+x)*scalefactor,(167+y)*scalefactor), 
							new Point2D.Double((-7+x)*scalefactor,(70+y)*scalefactor), 
							new float[]{0.0f, 1.0f}, 
							new Color[]{new Color(color.getRed(), color.getGreen(), color.getBlue(), 10), color});

		
		colors = new ArrayList<Color>();
//		colors.add(new Color(47,153,21,255));
//		colors.add(new Color(36,120,16,255));
//		colors.add(new Color(48,154,22,255));
//		colors.add(new Color(77,254,41,255));
//		colors.add(new Color(61,201,32,255));
//		colors.add(new Color(77,254,41,255));
		
		
		colors.add(color.darker().darker());
		colors.add(color.darker().darker().darker());
		colors.add(color.darker().darker());
		colors.add(color);
		colors.add(color.darker());
		colors.add(color);
	}
	
	public void calculateButton(){
		buttonparts = new ArrayList<GeneralPath>();
		buttonparts.add(arrayToGeneralpath(new int[][]{{449, 1}, {449, 68},{113, 103}, {106, 35}}));															//background right
		buttonparts.add(arrayToGeneralpath(new int[][]{{113, 103}, {106, 35},{70, 12+rounding}, {77, 88+rounding}}));										//background middle
		buttonparts.add(arrayToGeneralpath(new int[][]{{70, 12+rounding}, {77, 88+rounding},{4, 104+(int)(rounding*0.5)}, {-4, 32+(int)(rounding*0.5)}}));	//background left
		buttonparts.add(arrayToGeneralpath(new int[][]{{449, 15}, {449, 54},{118, 88}, {114, 48}}));															//foreground right
		buttonparts.add(arrayToGeneralpath(new int[][]{{118, 88}, {114, 48},{78, 25+rounding}, {82, 73+rounding}}));											//foreground middle
		buttonparts.add(arrayToGeneralpath(new int[][]{{78, 25+rounding}, {82, 73+rounding},{15, 88+(int)(rounding*0.5)}, {10, 43+(int)(rounding*0.5)}}));	//foreground left
	
		border = arrayToGeneralpath(new int[][]{{449,15}, {114,48},{78,25+rounding},{10,43+(int)(rounding*0.5)},{15,88+(int)(rounding*0.5)},{82,73+rounding},{118,88},{449,54}});
	}
	
	public GeneralPath arrayToGeneralpath(int block[][]){
		GeneralPath polyline =  new GeneralPath(GeneralPath.WIND_EVEN_ODD, block.length);
		polyline.moveTo ((block[0][0]+x)*scalefactor, (block[0][1]+y)*scalefactor);
		for (int index = 1; index < block.length; index++) {
		         polyline.lineTo((block[index][0]+x)*scalefactor, (block[index][1]+y)*scalefactor);
		};
		polyline.closePath();
		return polyline;
	}

	public void draw(Graphics2D g2d){
		for(int i = 0; i < buttonparts.size(); i++){ //fill every button part
			g2d.setColor(colors.get(i));
			g2d.fill((buttonparts.get(i)));
		}
		
		if(selected){
			g2d.setStroke(new BasicStroke(4));
			g2d.setColor(Color.BLACK);
			g2d.draw(border);
		}
		g2d.setPaint(gradient);
		g2d.fill(line);

		//draw text
		g2d.setColor(Color.BLACK);
		Font textFont = new Font("OCR A Extended", Font.BOLD, (int)(30*scalefactor));
		g2d.setFont(textFont);
		g2d.translate((x+160)*scalefactor, (y+74)*scalefactor);
		g2d.rotate(-0.1);
		g2d.drawString(text,  0, 0);
		g2d.rotate(0.1);
		g2d.translate(-(x+160)*scalefactor, -(y+74)*scalefactor);
	}
	
	public void update(){
		if(selected && fadecounter < 5){
			x -= 8;
			y -=8;
			scalefactor += 0.04;
			fadecounter++;
			calculateButton();
		}else if(!selected && fadecounter >0){
			x += 8;
			y += 8;
			fadecounter--;
			scalefactor -= 0.04;
			calculateButton();
		}
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
