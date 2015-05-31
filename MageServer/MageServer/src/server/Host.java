package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Host implements Runnable{
	
	ServerSocket Server;
	ArrayList<Socket> clients;
	Thread thread;
	Thread thread2;
	Updates up;

	public Host() {
		clients=new ArrayList<Socket>();
		
			
			try {
			Server=new ServerSocket(27084);
		} catch (IOException e) {
			System.out.println(e.toString());
			System.exit(0);
		}
		
		up=new Updates(clients);
		thread=new Thread(this,"Server Accept");
		thread.start();
		thread2=new Thread(up,"Server Updates");
		thread2.start();
			
		
	}

	@Override
	public void run() {
		System.out.println("Recieveing Started!");
		while(true){
			try {
			Socket Sock=Server.accept();
			System.out.println("Client Connecting!");
			
			if(Sock!=null){
				clients.add(Sock);
				up.clients=clients;
			}
		} catch (IOException e) {
			System.out.println("ERROR: "+e.toString());
			System.exit(0);
		}
		
		}
		
	}

}
