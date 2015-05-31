package object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import handle.Loader;
import handle.MageQuest;
import handle.Objhandler;

public class FireBall extends Gameobject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Trail> Tra;

	public FireBall(int x, int y, Objhandler hand,int direction,int vely) {
		super(x, y, hand);
		this.vely=vely;
		
		MageQuest.FireBall.play();
		
		width=20;
		falls=false;
		height=20;
		AeroDyn=true;
		velx=30*direction;
		img=Loader.Loadimg("/FireBall.png");
		Tra=new ArrayList<Trail>();
	}
	
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
	public void Collision(Rectangle r,Gameobject go) {
		// TODO Program Explosion
		
		if(go instanceof Mob){
			((Mob) go).TakeDamage(15, Mob.ExplosionDamage);
		}
		
		if(go instanceof ElStrike){
			vely=vely*-1;
		}else{
			hand.Remove(this);
		}
		
		
		
	}
	
}
