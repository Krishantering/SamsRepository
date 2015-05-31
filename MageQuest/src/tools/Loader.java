package tools;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Loader {
	
	public static BufferedImage Loadimg(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(Loader.class.getResource(path));
			
			for(int x=0;x<img.getWidth();x++){
				for(int y=0;y<img.getHeight();y++){
					final int clr = img.getRGB(x, y);
	                final int red = (clr & 0x00ff0000) >> 16;
	                final int green = (clr & 0x0000ff00) >> 8;
	                final int blue = clr & 0x000000ff;
	                
	                
	                if(red==55||green==55||blue==55){
	                	int a = 0;
	                	int col = (a << 24) | (red << 16) | (green << 8) | blue;
	                	img.setRGB(x, y, col);
	                }
					}
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Problem loading file: "+path, "Error", JOptionPane.PLAIN_MESSAGE);
		}
		
		return img;
	}
}
