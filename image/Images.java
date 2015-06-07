package image;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Main;

public class Images {

	public static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

	public Images() {

	}

	static {
		try {
			images.add(toCompatibleImage(ImageIO.read(Main.class.getResource("/image/player.png"))));
			images.add(toCompatibleImage(ImageIO.read(Main.class.getResource("/image/player2.png"))));
			images.add(toCompatibleImage(ImageIO.read(Main.class.getResource("/image/pressstart.png")))); 
			images.add(toCompatibleImage(ImageIO.read(Main.class.getResource("/image/colorstrike.png")))); 
		   	images.add(toCompatibleImage(ImageIO.read(Main.class.getResource("/image/background.png"))));
		   	images.add(toCompatibleImage(ImageIO.read(Main.class.getResource("/image/aanwijzers4sho.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getImage(ImageType img) {
		return images.get(img.ordinal());
	}

	public enum ImageType {
		player,player2,pressstart,colorstrike,background,aanwijzers
	}

	public static BufferedImage readImage(File f) {
		BufferedImage bf = null;
		try {
			bf = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bf;
	}
	
	public static BufferedImage toCompatibleImage(BufferedImage image)
	{
		// obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment.
			getLocalGraphicsEnvironment().getDefaultScreenDevice().
			getDefaultConfiguration();

		/*
		 * if image is already compatible and optimized for current system 
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;

		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image; 
	}
}
