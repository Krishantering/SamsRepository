package handle;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import object.EvilWizard;
import object.FireBall;
import object.Gameobject;
import object.Player;
import object.ElStrike;
import object.StaticBlock;

public class Objhandler implements ActionListener {
	
	ArrayList<Gameobject> Objects;
	ArrayList<Gameobject> Remove;
	MageQuest mq;
	Player p;
	Camera Cam;
	
	public Objhandler(MageQuest mq,Camera Cam){
		
		this.mq=mq;
		this.Cam=Cam;
		
		for(int i=0;i<9;i++)
		mq.ibutt[i].addActionListener(this);
		
		
		Objects=new ArrayList<Gameobject>();
		Remove=new ArrayList<Gameobject>();
		
		int wall=10;
		this.NewObject(p=new Player(100, 0, this));
		this.NewObject(new EvilWizard(2000, 0, this, p));
		
		for(int i=0;i<100;i++){
			this.NewObject(new StaticBlock(30*i, 100, this));
			this.NewObject(new StaticBlock(30*i, 100, this));
			this.NewObject(new StaticBlock(i*30*wall, 70, null));
			this.NewObject(new StaticBlock(i*30*wall, 40, null));
			this.NewObject(new StaticBlock(i*30*wall, 10, null));
		}
		
	}
	
	public void tick(){
		
		for(int i=0;i<Objects.size();i++){
			 Objects.get(i).tick();
			 
			 for(int x=0;x<Objects.size();x++){
				 
				 if(!(Objects.get(i)==Objects.get(x))){
					 if(Objects.get(i).getBounds().intersects(Objects.get(x).getBounds())){
						 Objects.get(i).Collision(Objects.get(x).getBounds(), Objects.get(x));
					 }
					 if(Objects.get(i)instanceof Player){
							 if(Objects.get(x)instanceof FireBall || Objects.get(x)instanceof ElStrike){
							 if(((Player) Objects.get(i)).getClose().intersects(Objects.get(x).getBounds())){
								 MageQuest.slowdown=10;
							 }
							 }
						 }
				 }
				 
			 }
		}
		Cam.tick(p.getX()+(p.width/2), p.getY()+(p.height/2));
		
		if(!Remove.isEmpty()){
			for(int i=0;i<Remove.size();i++){
			Objects.remove(Remove.get(i));
			Remove.remove(i);
		}
		}
		
		
		
	}
	
	public void render(Graphics g){
		
		for(int i=0;i<Objects.size();i++){
			 Objects.get(i).render(g);
		}
		
	}
	
	public void NewObject(Gameobject ob){
		Objects.add(ob);
	}
	
	public void Remove(Gameobject ob){
		Remove.add(ob);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		
		JButton Scource=(JButton) e.getSource();
		
		if(Scource.getLabel()=="FireBall"){
			if(MageQuest.direction==1){
				this.NewObject(new FireBall(p.getX()+p.getHeight(),p.getY()+(p.getHeight()/2) , this, MageQuest.direction, 0));
			}else{
				this.NewObject(new FireBall(p.getX()-p.getWidth(),p.getY()+(p.getHeight()/2),this,MageQuest.direction, 0));
			}
			
			}else if(Scource.getLabel()=="ElectricStrike"){
				if(MageQuest.direction==1){
					this.NewObject(new ElStrike(p.getX()+p.getHeight(),p.getY()+(p.getHeight()/2) , this, MageQuest.direction, 0));
				}else{
					this.NewObject(new ElStrike(p.getX()-p.getWidth(),p.getY()+(p.getHeight()/2),this,MageQuest.direction, 0));
				}
				
				}else if(Scource.getLabel()=="Options"){
				
				mq.options();
				
			}else{
			JOptionPane.showMessageDialog(null, "Comming Soon",Scource.getLabel(), JOptionPane.PLAIN_MESSAGE);
		}
		
		
	}
	
}
