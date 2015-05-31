package object;

import handle.MageQuest;
import handle.Objhandler;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import tools.Loader;

public class ElStrike extends Gameobject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int SPEED=60;
	ArrayList<Trail> Tra;
	
	public ElStrike(int x, int y, Objhandler hand,int velx,int vely) {
		super(x, y, hand);
		this.vely=vely;
		this.velx=velx;
		
		MageQuest.ElStrike.play();
		
		width=60;
		height=20;
		falls=false;
		AeroDyn=true;
		Collideable=false;
		
		img=Loader.Loadimg("/ElStrike"+velx/60+".png");
		Tra=new ArrayList<Trail>();
	}

	@Override
	public void render(Graphics g) {
		
		Tra.add(new Trail(img, 1f, x, y, width, height));
		g.drawImage(img, x, y, width, height, null);
		for(int i=0;i<Tra.size();i++){
			Tra.get(i).render(g);
			Tra.get(i).T-=0.05f;
			if(Tra.get(i).T<0.1f){
				Tra.remove(i);
			}
		}

	}

	@Override
	public void Collision(Rectangle r, Gameobject Go) {
		
		if(Go instanceof Mob){
			((Mob) Go).TakeDamage(0, Mob.ElectricShock);
		}
		hand.Remove(this);
		

	}

}
