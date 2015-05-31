package object;

import handle.Objhandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class StaticBlock extends Gameobject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StaticBlock(int x, int y, Objhandler hand) {
		super(x, y, hand);
		
		height=30;
		width=30;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);

	}
	
	@Override
	public void tick(int slowdown){
		
	}

	@Override
	public void Collision(Rectangle r, Gameobject Go) {}
	
}
