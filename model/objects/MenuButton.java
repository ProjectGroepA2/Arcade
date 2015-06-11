package model.objects;

import image.Images;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.VolatileImage;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import audio.Song;

public class MenuButton {

	private int x, y, rounding;
	private double scalefactor;
	private Color buttoncolor;
	
	private boolean selected; 
	private VolatileImage background;

	private ArrayList<Color> colors;
	private int x, y;
	private boolean selected; 
	private Song song;
	Color color;
	
	public MenuButton(int x, int y, Color color, Song song){
		this.x = x;
		this.y = y;
		this.scalefactor = scale;
		this.rounding = rounding;
		this.buttoncolor = c0;
		setSong(song);
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
		background = Images.initVolatileImage(1280, 500, Transparency.TRANSLUCENT);
		Graphics2D g2d = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setRenderingHints(rh);
	    g2d.setComposite(AlphaComposite.DstOut);
	    g2d.fillRect(0, 0, background.getWidth(), background.getHeight());
	    g2d.setComposite(AlphaComposite.SrcOver);
	    
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
		this.color = color;
		setSong(song);
		
	}
	
	

	public void draw(Graphics2D g2d){

		
		if(selected){
			g2d.setColor(color);
			GeneralPath path = new GeneralPath();
			path.moveTo(320, 540);
			path.lineTo(320, 590);
			path.lineTo(385, 565);
			
			path.closePath();
			g2d.fill(path);
			
			g2d.setColor(Color.BLACK);
			g2d.draw(path);
			
			g2d.setColor(color.darker().darker().darker().darker());
			g2d.fillRect(x-10, y-10,900,90);
		}
		g2d.setColor(color.darker().darker());
		g2d.fillRect(x-5, y-5,890,80);
		g2d.setColor(color);
		g2d.fillRect(x,y,880,70);
>>>>>>> 7dd4066a8ab297001a5e0e35d877aff3f29525ac
		
		if(selected){
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x-10, y-10, 900, 90);
			g2d.drawRect(x-5, y-5, 890, 80);
			g2d.drawRect(x, y, 880, 70);
			
		}

		g2d.setPaint(gradient);
		g2d.fill(line);
		
		//draw text
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("OCR A Extended", Font.BOLD, (int)(30*scalefactor)));
		g2d.translate((160+358)*scalefactor, (74)*scalefactor);
		g2d.rotate(-0.1);
		g2d.drawString(song.getTitle(),  0, 0);
		g2d.dispose();
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
		g2d.drawImage(background, (int)((x-320)*scalefactor), (int) ((y)*scalefactor), 1280, 500, null);
	}
	
	public void update(){
	}

		//draw text
		g2d.setColor(Color.BLACK);
		Font textFont = new Font("OCR A Extended", Font.BOLD,60);
		g2d.setFont(textFont);
		g2d.drawString(song.getTitle(),  x+50, y+57);
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
		calculateButton();
	}
}
