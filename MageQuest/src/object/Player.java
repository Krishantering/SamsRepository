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

import tools.ImageRotator;
import tools.Loader;

import com.sun.media.sound.JavaSoundAudioClip;

import handle.MageQuest;
import handle.Objhandler;

public class Player extends Gameobject implements Mob{
	
	private static final long serialVersionUID = 1L;
	boolean jumping=false;
	int health=50;
	boolean running=false;
	public int Dreagres=20;
	boolean flipping=false;
	
	

	public Player(int x, int y, Objhandler hand) {
		super(x, y, hand);
		
		img=Loader.Loadimg("/Player.png");
		width=30;
		height=60;
		
	}
	
	public void tick(int slowdown) {

		slowTimer++;
		if (slowTimer > slowdown) {
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
				if (height > 30) {
					height -= 5;
					y += 5;
				}
				
				if(jumping&&!flipping){
					System.out.println(MageQuest.name+": I'm going to do a flip");
					flipping=true;
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
			
			if(flipping){
				Dreagres+=9;
				//TODO perfect rotation
				if(Dreagres>=360){
					Dreagres=0;
					flipping=false;
				}
				if(Dreagres!=0){
					
					if(Dreagres<90){
					width+=2;
					height-=2;
				}else if(Dreagres<180){
					width-=2;
					height+=2;
				}else if(Dreagres<270){
					width+=2;
					height-=2;
				}else if(Dreagres<360){
					width-=2;
					height+=2;
				}
					
				}else{
					width=30;
					height=60;
				}
				
			}

		}

		y += vely / slowdown;
		x += velx / slowdown;

		MageQuest.HEALTH = this.health;
	}
	
	public void render(Graphics g) {
		
		if(running){
			if(MageQuest.direction==1){
				g.drawImage(ImageRotator.RotateImg(MageQuest.images.get(0).get(0).get(MageQuest.animation/10), Dreagres*MageQuest.direction), x, y, width, height, null);
			}else{
				g.drawImage(ImageRotator.RotateImg(MageQuest.images.get(0).get(1).get(MageQuest.animation/10), Dreagres*MageQuest.direction), x, y, width, height, null);
			}
			
		}else{
			g.drawImage(ImageRotator.RotateImg(img, Dreagres), x, y, width, height, null);
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
