package handle;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import object.Gameobject;
import object.StaticBlock;

public class LevelCreator extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int CurrentOb=1;
	private MageQuest mq;
	public ArrayList<Gameobject> Objects;

	public LevelCreator(MageQuest mq) {
		this.mq=mq;
		Objects=new ArrayList<Gameobject>();
		this.addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		
	}
	
	public boolean SaveLevel() {
		try {

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
					new File("out/"+MageQuest.name+".prog")));

			for (int i = 0; i < Objects.size(); i++) {

				oos.writeObject(Objects.get(i));

			}
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(CurrentOb==1){
			Objects.add(new StaticBlock(e.getX(), e.getY(), null));
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
