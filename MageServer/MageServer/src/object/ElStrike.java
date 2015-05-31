package object;

import handle.Loader;
import handle.MageQuest;
import handle.Objhandler;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ElStrike extends Gameobject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Trail> Tra;
	
	public ElStrike(int x, int y, Objhandler hand,int direction,int vely) {
		super(x, y, hand);
		this.vely=vely;
		
		MageQuest.ElStrike.play();
		
		width=60;
		height=20;
		falls=false;
		AeroDyn=true;
		
		velx=60*direction;
		img=Loader.Loadimg("/ElStrike"+direction+".png");
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
