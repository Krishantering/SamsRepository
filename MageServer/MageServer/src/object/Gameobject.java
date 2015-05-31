package object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import handle.MageQuest;
import handle.Objhandler;

public abstract class Gameobject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int vely=0;
	int velx=0;
	int y=0;
	int x=0;
	public int width;
	public int height;
	BufferedImage img;
	Objhandler hand;
	int slowTimer=1;
	boolean falls=true;
	boolean AeroDyn=false;
	
	public Gameobject(int x,int y,Objhandler hand){
		
		this.y=y;
		this.x=x;
		this.hand=hand;
		
	}
	
	public void tick(){
		
		slowTimer++;
		if(slowTimer>MageQuest.slowdown){
			slowTimer=1;
			
			if(!AeroDyn){
				if(velx>0)
					velx--;
				
				if(velx<0)
					velx++;
			}
		
		if(vely<MageQuest.Gravity&&falls){
			vely+=MageQuest.Gravity/10;
		}
			
		}
			
			
		
		y+=(vely/MageQuest.slowdown);
		x+=(velx/MageQuest.slowdown);
	}
	
	public abstract void render(Graphics g);
	
	public abstract void Collision(Rectangle r,Gameobject Go);
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	
	
	public Rectangle getBoundsUpp(){
		return new Rectangle(x,y,width,1);
	}
	//TODO Fix Super Jump Bug
	public Rectangle getBoundsDown(){
		return new Rectangle(x,y+(height-1),width,1);
	}
	
	public Rectangle getBoundsLeft(){
		return new Rectangle(x,y,1,height);
	}
	
	public Rectangle getBoundsRight(){
		return new Rectangle(x+(width-1),y,1,height);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
