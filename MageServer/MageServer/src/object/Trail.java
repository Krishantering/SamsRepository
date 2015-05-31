package object;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Trail {
	
	BufferedImage img;
	float T;
	int x;
	int y;
	int width;
	int height;

	public Trail(BufferedImage img,float T,int x,int y,int width,int height) {
		
		this.img=img;
		this.T=T;
		this.y=y;
		this.x=x;
		this.height=height;
		this.width=width;
		
	}
	
	public void render(Graphics g){
		
		Graphics2D g2d=(Graphics2D) g;
		
		 g2d.setComposite(AlphaComposite.getInstance(
		            AlphaComposite.SRC_OVER, T));
		
		g.drawImage(img, x, y, width, height, null);
		
	}

}
