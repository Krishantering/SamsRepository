package object;

import handle.Loader;
import handle.MageQuest;
import handle.Objhandler;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EvilWizard extends Gameobject implements Mob {

	/**
	 * This is a Evil Wizard Enemy
	 */
	
	private static final long serialVersionUID = 1L;
	int health=20;
	Player p;
	int direction=1;
	boolean jumping=false;

	public EvilWizard(int x, int y, Objhandler hand,Player p) {
		super(x, y, hand);
		width=30;
		height=60;
		img=Loader.Loadimg("/EvilWizard.png");
		this.p=p;
		
	}

	@Override
	public void Die() {

		hand.Remove(this);

	}
	
	@Override
	public void tick(){
		if(x>p.x){
			direction=-1;
			while(velx>-7)
				velx--;
		}else{
			direction=1;
			while(velx<7)
				velx++;
		}
		
		
			if(new Random().nextInt(40)==0){
				
				//TODO fix aiming thingy
				
				
				if(new Random().nextBoolean()==true){
					if(direction==1){
					hand.NewObject(new FireBall(x+width, y+width/2, hand, direction, this.y-p.y/this.x-p.x));
				}else{
					hand.NewObject(new FireBall(x-width, y+width/2, hand, direction,this.y-p.y/50));
				}
				}else{
					if(direction==1){
					hand.NewObject(new ElStrike(x+width, y+width/2, hand, direction,this.y-p.y/50));
				}else{
					hand.NewObject(new ElStrike(x-width, y+width/2, hand, direction,this.y-p.y/50));
				}
				}
			
		}
		
		if(!AeroDyn){
			if(velx>0)
				velx--;
			
			if(velx<0)
				velx++;
		}
		
		if(!jumping){
			vely-=20;
			jumping=true;
		}
	
		if(vely<MageQuest.Gravity){
			vely+=MageQuest.Gravity/10;
		}
		
		
	y+=(vely/MageQuest.slowdown);
	x+=(velx/MageQuest.slowdown);
	
	
	}

	@Override
	public void TakeDamage(int Damage, int Type) {
		
		health-=Damage;
		
		if(health<1)
			Die();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, width, height, null);

	}

	@Override
	public void Collision(Rectangle r, Gameobject Go) {
		
		
		
		
		if(this.getBoundsUpp().intersects(r)){
			if(vely<0)
				vely=0;
			
			this.y=r.y+r.height;
			
		}else if(this.getBoundsDown().intersects(r)){
			if(vely>0){
				vely=0;
			}
			
			if(!(vely<0))
			jumping=false;
			
			this.y=r.y-this.height;
	
		}
		
		if(this.getBoundsLeft().intersects(r)){
			if(velx<0)
				velx=0;
			
			this.x=r.x+r.width;
		}
		
		if(this.getBoundsRight().intersects(r)){
			if(vely>0)
				vely=0;
			
			x=r.x-this.width;
		}
		
		

	}

}
