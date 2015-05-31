package handle;

import object.Gameobject;
import object.Player;

public class Camera {
	
	int x,y; 
	
	public Camera(int x,int y){
		
		this.x=x;
		this.y=y;
		
	}public void tick(int x,int y) {
		
		this.x=-x + 1200/2;
		this.y=-y + 800/2;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
}
