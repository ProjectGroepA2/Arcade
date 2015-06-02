package image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Main;

public class Images{

 public static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

	 public Images(){
	  
	 }

	 static{
		  try{
		   images.add(ImageIO.read(Main.class.getResource("/image/player.png"))); 
		   images.add(ImageIO.read(Main.class.getResource("/image/player2.png"))); 
		  
		  }catch(IOException e){
		   e.printStackTrace();
		  }
	 }

	 public static BufferedImage getImage(ImageType img)
	 {
	  return images.get(img.ordinal());
	 }

	 public enum ImageType
	 {
	  player,player2	 
	 }
}
