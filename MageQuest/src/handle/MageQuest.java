package handle;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.Client;
import net.CouldNotConnectToHost;
import menus.MainMenu;
import menus.Option;
import object.Gameobject;
import tools.Loader;

import com.sun.media.sound.JavaSoundAudioClip;

public class MageQuest extends JPanel implements Runnable ,KeyListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Thread thread;
	Objhandler hand;
	boolean running=false;
	boolean pause=false;
	JFrame frame=null;
	JPanel p1 = this;
	JPanel p2;
	JPanel WP;
	JButton ibutt[];
	public static Boolean KeyD=false;
	public static Boolean KeyA=false;
	public static Boolean KeyW=false;
	public static Boolean KeyS=false;
	public static int Gravity = 10;
	public static int direction = -1;
	public static int blind = 0;
	public static String name;
	public static String LevelName="A LEVEL";
	public static JavaSoundAudioClip Hurt;
	public static JavaSoundAudioClip FireBall;
	public static JavaSoundAudioClip ElStrike;
	public static JavaSoundAudioClip Music_1;
	public static boolean Dead = false;
	public static boolean FULLSCREEN=false;
	public static int HEALTH=0;
	public static int WIDTH=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int HEIGHT=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int slowdown=1;
	public static int animation=1;
	Camera Cam;
	Client c;
	BufferStrategy bs;
	MainMenu mm;
	Object[] options;
	public static ArrayList<ArrayList<ArrayList<BufferedImage>>> images;
	 
	
	
	
	public MageQuest(){
		
		try {
			c=new Client("localhost", 27084);
		} catch (CouldNotConnectToHost e) {
			System.out.println(e.getMessage());
		}
		
		LoadSound();
		LoadTextures();
		
		ibutt = new JButton[9];
		
		ibutt[0]=new JButton("FireBall");
		ibutt[1]=new JButton("ElectricStrike");
		ibutt[8]=new JButton("Options");
		for(int i=2;i<8;i++){
			ibutt[i]=new JButton("Spell: "+(i+1));
		}
		thread=new Thread(this);
		
		hand = new Objhandler(this, Cam=new Camera(0, 0));
		
		if (!CreateW()) {
			JOptionPane.showMessageDialog(null, "Failed to create window");
		}
		
		mm=new MainMenu(this);
		
		frame.add(mm);
		
		while(name==null||name.length()<2)
		name=JOptionPane.showInputDialog(null, "At least 2 letters", "What is Your Name", JOptionPane.PLAIN_MESSAGE);
		
		Music_1.loop();
	}
	
	public boolean SaveGame() {
		try {

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
					new File("out/Score/"+MageQuest.name+".prog")));

			for (int i = 0; i < hand.Objects.size(); i++) {

				oos.writeObject(hand.Objects.get(i));

			}
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean LoadGame(String path) {
		
		try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("out/out.prog")));
			
				
				
					try {
						while (true) {
						if(ois.readObject() instanceof Gameobject)
						hand.Objects.add((Gameobject) ois.readObject());
						}
					} catch (EOFException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
				
		}
	
		public void LoadSound(){
			try {
				Hurt = new JavaSoundAudioClip(MageQuest.class.getResourceAsStream("/Sound/Hurt.wav"));
				FireBall = new JavaSoundAudioClip(MageQuest.class.getResourceAsStream("/Sound/FireBall.wav"));
				ElStrike = new JavaSoundAudioClip(MageQuest.class.getResourceAsStream("/Sound/Electric Strike.wav"));
				Music_1=new JavaSoundAudioClip(MageQuest.class.getResourceAsStream("/Sound/Music_1.wav"));
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Could not Load sound :(", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "There was a IOException", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void LoadTextures(){
			
			images=new ArrayList<ArrayList<ArrayList<BufferedImage>>>();
			
			images.add(new ArrayList<ArrayList<BufferedImage>>());
			
			images.get(0).add(new ArrayList<BufferedImage>());
			images.get(0).add(new ArrayList<BufferedImage>());
			
			for(int i=0;i<6;i++){
				images.get(0).get(0).add(Loader.Loadimg("/PlayerRun"+(i+1)+"1.png"));
			}
			
			for(int i=0;i<6;i++){
				images.get(0).get(1).add(Loader.Loadimg("/PlayerRun"+(i+1)+"-1.png"));
			}
			
		}
		
		
	public void run() {
		
		int fps=0,updates=0;
		long fpstimer =System.currentTimeMillis();
		double nsPerTick = 1000000000/60;
		
		
		double then =System.nanoTime();
		double unprocessed=0;
		
		while(running){
			
			while(!pause){
				
				double now =System.nanoTime();
			unprocessed += (now-then) / nsPerTick;
			then=now;
			
			boolean shouldRender=false;
			
			while(unprocessed>=1){
				updates++;
				Tick();
				unprocessed--;
				shouldRender=true;
			}
			
			if(shouldRender){
				fps++;
				p1.repaint();
			}
			
			
			//Fps Timer
			
			if(System.currentTimeMillis()-fpstimer>1000){
				System.out.println();
				System.out.println(updates+" :ticks fps: "+fps);
				System.out.println();
				updates=0;
				fps=0;
				fpstimer=System.currentTimeMillis();
			}
				
			}
		}
		
	}

	public void Tick(){
		
		if(slowdown>1)
		slowdown--;
		
		animation++;
		
		if(animation>=60)
			animation=0;
		
		p1.grabFocus();
		hand.tick();
	}

	public void paintComponent(Graphics g){
		
		Graphics2D g2d= (Graphics2D) g;
		
		
			
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, p1.getWidth(), p1.getHeight());
		
		
		g2d.translate(Cam.getX(), Cam.getY());
		hand.render(g);
		g2d.translate(-Cam.getX(), -Cam.getY());
		
		g2d.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, 0.5f));
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, p1.getWidth(), p1.getHeight());
		
			if(blind>0){
				blind--;
			g2d.setComposite(AlphaComposite.getInstance(
		            AlphaComposite.SRC_OVER, 1f*blind/(60*5)));
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
			
		g.setColor(Color.GREEN);
		g.fillRect(10, 10, WIDTH/50*HEALTH, 20);
		
		if(Dead){
			
			g2d.setComposite(AlphaComposite.getInstance(
			            AlphaComposite.SRC_OVER, 0.7f));
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.red);
			g.drawString("YOU DIED", WIDTH/2, HEIGHT/2);
			
			pause=true;
			
		}
		
	}

	public boolean CreateW(){
		WP=new JPanel();
		frame=new JFrame();
		frame.add(WP);
		
		frame.setUndecorated(FULLSCREEN);
		
		frame.setVisible(true);
		if(!FULLSCREEN){
			frame.setSize(WIDTH/4*3,HEIGHT/4*3);
		}else{
			frame.setSize(WIDTH,HEIGHT);
		}
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		WP.setLayout(new BorderLayout());
		
		p1.setLayout(new GridLayout(3,1));
		
		p2=new JPanel();
		
		p2.setLayout(new GridLayout(1,9));
		p2.setPreferredSize(new Dimension(WP.getWidth(),100));
		
		for(int i=0;i<9;i++){
			p2.add(ibutt[i]);
		}
		
		WP.add(p1,BorderLayout.CENTER);
		WP.add(p2,BorderLayout.SOUTH);
		
		p1.setVisible(false);
		p2.setVisible(false);
		
		p1.addKeyListener(this);
		
		p1.grabFocus();
		
		return true;
	}
	
	public void windowdecorated(){
		frame.setVisible(false);
		frame=new JFrame();
		frame.add(WP);
		
		frame.setUndecorated(FULLSCREEN);
		
		frame.setVisible(true);
		if(!FULLSCREEN){
			frame.setSize(WIDTH/4*3,HEIGHT/4*3);
		}else{
			frame.setSize(WIDTH,HEIGHT);
		}
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MageQuest();
	}

	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D){
			KeyD=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_A){
			KeyA=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_W){
			KeyW=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_S){
			KeyS=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_F3){
			System.out.println("KeyD: "+KeyD+" KeyA: "+KeyA+" KeyShift: "+" KeyW: "+KeyW);
		}
		
	}

	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D){
			KeyD=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_A){
			KeyA=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_W){
			KeyW=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_S){
			KeyS=false;
		}
		
	}

	public static int getDirection() {
		return direction;
	}

	public static void setDirection(int direction) {
		MageQuest.direction = direction;
	}

	public void start() {
		
		try {
			c.WriteText("Starting The Game!!!!   :) Yeah Its gonna be so fun");
			c.WriteText("By the way that was me speaking and my name is "+name);
		} catch (IOException e) {
			
		}catch(NullPointerException e){
			System.out.println("No server to write to");
		}
		p1.setVisible(true);
		p2.setVisible(true);
		running=true;
		thread.start();
		
	}
	
	public void options(){
		pause=true;
		p2.setVisible(false);
		
		options=new Object[3];
		
		JButton b=new JButton("Done");
		options[0]=b;
		b.addActionListener(this);
		
		Option o1=new Option("Spells on numpad", false);
		options[1]=o1;
		
		Option o2=new Option("Fullscreen", FULLSCREEN);
		options[2]=o2;
		
		p1.add(o1);
		p1.add(o2);
		p1.add(b);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((Option) options[1]).getbState();
		
		if(((Option) options[2]).getbState()){
			FULLSCREEN=true;
		}else{
			FULLSCREEN=false;
		}
		windowdecorated();
		
		p1.removeAll();
		p1.revalidate();
		p2.setVisible(true);
		pause=false;
	}

}
