package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	
	Socket Sock;
	OutputStream out;
	InputStream in;
	
	public Client(String Ip,int port) throws CouldNotConnectToHost {
		try {
			//System.out.println("Connecting...");
			Sock=new Socket(Ip,port);
			out=Sock.getOutputStream();
			in=Sock.getInputStream();
			//System.out.println("Complete");
		} catch (IOException e) {
			throw new CouldNotConnectToHost(Ip, port);
		}
		
	}
	
	public void WriteText(String Text) throws IOException {
		ObjectOutputStream ois = new ObjectOutputStream(Sock.getOutputStream());
		ois.writeObject(new ClientPack(Text).ToBytes());
		ois.close();
	}

}
