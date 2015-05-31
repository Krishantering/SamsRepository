package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sun.media.sound.JavaSoundAudioClip;

import handle.Loader;
import handle.MageQuest;
import handle.Objhandler;

public class Player extends Gameobject implements Mob{
	
	private static final long serialVersionUID = 1L;
	boolean jumping=false;
	int health=50;
	boolean running=false;
	
	

	public Player(int x, int y, Objhandler hand) {
		super(x, y, hand);
		
		img=Loader.Loadimg("/Player.png");
		width=30;
		height=60;
		
	}
	
	public void tick() {

		slowTimer++;
		if (slowTimer > MageQuest.slowdown) {
			slowTimer = 1;

			running = false;

			if (velx > 0)
				velx--;

			if (velx < 0)
				velx++;

			if (MageQuest.KeyW) {
				if (!jumping) {
					vely -= 20;
					jumping = true;
				}
			}

			if (MageQuest.KeyD) {
				while (velx < 10)
					velx++;
				running = true;
				MageQuest.setDirection(1);
			}

			if (MageQuest.KeyA) {
				while (velx > -10)
					velx--;
				running = true;
				MageQuest.setDirection(-1);
			}

			if (MageQuest.KeyS) {
				if (height > 20) {
					height -= 5;
					y += 5;
				}

			} else {
				if (height < 60) {
					height += 5;
					y -= 5;
				}

			}

			if (vely < MageQuest.Gravity) {
				vely += MageQuest.Gravity / 10;
			}

		}

		y += vely / MageQuest.slowdown;
		x += velx / MageQuest.slowdown;

		MageQuest.HEALTH = this.health;
	}
	
	public void render(Graphics g) {
		
		if(running){
			if(MageQuest.direction==1){
				g.drawImage(MageQuest.images.get(0).get(0).get(MageQuest.animation/10), x, y, width, height, null);
			}else{
				g.drawImage(MageQuest.images.get(0).get(1).get(MageQuest.animation/10), x, y, width, height, null);
			}
			
		}else{
			g.drawImage(img, x, y, width, height, null);
		}
		
		
		g.setColor(Color.BLACK);
		g.drawString(MageQuest.name, x-5, y-5);

		

		
	}

	@Override
	public void Collision(Rectangle r,Gameobject go) {
		if(this.getBoundsDown().intersects(r)){
			if(vely>0) {
				jumping=false;
				vely=0;
			}
			

			this.y=r.y-this.height;
	
		}else if(this.getBoundsUpp().intersects(r)){
			if(vely<0)
				vely=0;
			
			this.y=r.y+r.height;
		}
		
		
		if(this.getBoundsLeft().intersects(r)){
			if(velx<0)
				velx=0;
			
			this.x=r.x+r.width;
		}
		
		if(this.getBoundsRight().intersects(r)){
			if(velx>0)
				velx=0;
			
			x=r.x-this.width;
		}
		
		
		
		
		
		
	}
	
	
	public Rectangle getClose(){
		return new Rectangle(x-300,y-300,width+600,height+600);
	}
	
	@Override
	public void Die() {
		MageQuest.Dead=true;
	}

	@Override
	public void TakeDamage(int Damage, int Type) {
		// TODO Fix Damage Taking
		health-=Damage;
		MageQuest.Hurt.play();
		
		if(health<0)
			Die();
		
		if(Type==Mob.ElectricShock){
			MageQuest.blind=5*60;
		}
		
	}

}
