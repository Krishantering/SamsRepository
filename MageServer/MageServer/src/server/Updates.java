package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import net.ClientPack;

public class Updates implements Runnable {
	
	ArrayList<Socket> clients;
	
	public Updates(ArrayList<Socket> clients){
		this.clients=clients;
	}
	
	@Override
	public void run() {
		System.out.println("Updates Started!");
		while(true){
			
			for(int i=0;i<clients.size();i++){
				
				try {
						
						ObjectInputStream ois = new ObjectInputStream(clients.get(i).getInputStream());
						if(ois.available()>0){
							System.out.println("Reading Started");
						Object pack = null;
						try{
							pack = (ClientPack) ois.readObject();
						}catch(ClassCastException e){
							if(!(pack instanceof ClientPack)){
								System.out.println("HeeeeeelloooOOoOOOooO? You cant send me a non ClientPack pack, duh");
							}
						}
						
						if(pack!=null)
						System.out.println(((ClientPack) pack).getText());
					}
						
				} catch (EOFException e) {
					System.out.println("End of data stream");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}
				
			}
					
		}
	}
}
