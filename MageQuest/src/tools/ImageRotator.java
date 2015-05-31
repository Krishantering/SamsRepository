package tools;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageRotator {
	
	public static BufferedImage RotateImg(BufferedImage img,int Dergres){
		
		BufferedImage Stamp=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=(Graphics2D) Stamp.getGraphics();
		
		g.rotate(Math.toRadians(Dergres),img.getWidth()/2,img.getHeight()/2);
		g.drawImage(img, 0, 0, null);
		
		img=Stamp;
		
		return img;
	}
	
}
