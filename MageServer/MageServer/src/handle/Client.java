package handle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
	
	public void WriteText(String Text){
		PrintStream p=new PrintStream(out);
		p.println(Text);
	}

}
