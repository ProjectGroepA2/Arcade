package model.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.Song;

public class MenuButton {

	private int x, y, rounding;
	private double scalefactor;
	private Color buttoncolor;
	
	private boolean selected; 
	private BufferedImage background;
	
	private Song song;
	
	public MenuButton(int x, int y, double scale, int rounding, Color c0, Song song){
		this.x = x;
		this.y = y;
		this.scalefactor = scale;
		this.rounding = rounding;
		this.buttoncolor = c0;
		setSong(song);
		calculateButton();
	}
	
	public void calculateButton(){
		Color c1 = buttoncolor.darker();
		Color c2 = buttoncolor.darker().darker();
		Color c3 = buttoncolor.darker().darker().darker();
		
		c1 = new Color((int)(c1.getRed()*0.8), (int)(c1.getGreen()*0.8),(int)(c1.getBlue()*0.8));
		c2 = new Color((int)(c2.getRed()*0.8), (int)(c2.getGreen()*0.8),(int)(c2.getBlue()*0.8));
		c3 = new Color((int)(c3.getRed()*0.8), (int)(c3.getGreen()*0.8),(int)(c3.getBlue()*0.8));
		
		
		GeneralPath border = arrayToGeneralpath(new int[][]{{449,15}, {114,48},{78,25+rounding},{10,43+(int)(rounding*0.5)},{15,88+(int)(rounding*0.5)},{82,73+rounding},{118,88},{449,54}});
		GeneralPath line = arrayToGeneralpath(new int[][]{{-11,55},{-40,60},{-38,78},{-358,170-rounding*3},{-358,174-rounding*3},{-37,87},{-35,102},{-5,95}});
		Paint gradient = new LinearGradientPaint(	new Point2D.Double((-100)*scalefactor,(512)*scalefactor), 
													new Point2D.Double((600)*scalefactor,(512)*scalefactor), 
													new float[]{0.0f, 1.0f}, 
													new Color[]{new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 10), buttoncolor});
		//Draw the generated button to the buffered image
		background = new BufferedImage(1280, 1024, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setRenderingHints(rh);
	    
		g2d.setColor(c2);
		g2d.fill(arrayToGeneralpath(new int[][]{{449, 1}, {449, 68},{113, 103}, {106, 35}}));
		g2d.setColor(c3);
		g2d.fill(arrayToGeneralpath(new int[][]{{113, 103}, {106, 35},{70, 12+rounding}, {77, 88+rounding}}));
		g2d.setColor(c2);
		g2d.fill(arrayToGeneralpath(new int[][]{{70, 12+rounding}, {77, 88+rounding},{4, 104+(int)(rounding*0.5)}, {-4, 32+(int)(rounding*0.5)}}));
		g2d.setColor(buttoncolor);
		g2d.fill(arrayToGeneralpath(new int[][]{{449, 15}, {449, 54},{118, 88}, {114, 48}}));
		g2d.setColor(c1);
		g2d.fill(arrayToGeneralpath(new int[][]{{118, 88}, {114, 48},{78, 25+rounding}, {82, 73+rounding}}));
		g2d.setColor(buttoncolor);
		g2d.fill(arrayToGeneralpath(new int[][]{{78, 25+rounding}, {82, 73+rounding},{15, 88+(int)(rounding*0.5)}, {10, 43+(int)(rounding*0.5)}}));
		
		if(selected){
			g2d.setStroke(new BasicStroke(4));
			g2d.setColor(Color.BLACK);
			g2d.draw(border);
		}
		g2d.setPaint(gradient);
		g2d.fill(line);
		
		//draw text
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("OCR A Extended", Font.BOLD, (int)(30*scalefactor)));
		g2d.translate((160+358)*scalefactor, (74)*scalefactor);
		g2d.rotate(-0.1);
		g2d.drawString(song.getTitle(),  0, 0);
		
		background.createGraphics();		
	}
	
	public GeneralPath arrayToGeneralpath(int block[][]){
		GeneralPath polyline =  new GeneralPath(GeneralPath.WIND_EVEN_ODD, block.length);
		polyline.moveTo ((block[0][0]+358)*scalefactor, (block[0][1])*scalefactor);
		for (int index = 1; index < block.length; index++) {
		         polyline.lineTo((block[index][0]+358)*scalefactor, (block[index][1])*scalefactor);
		};
		polyline.closePath();
		return polyline;
	}

	public void draw(Graphics2D g2d){
		g2d.drawImage(background, (int)((x-320)*scalefactor), (int) ((y)*scalefactor), 1280, 1024, null);
	}
	
	public void update(){
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		x += 100;
		y -= 40;
		scalefactor += 0.2;
		calculateButton();
	}

	public boolean isSelected(){
		return selected;
	}
	public void setX(int x){
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
}
