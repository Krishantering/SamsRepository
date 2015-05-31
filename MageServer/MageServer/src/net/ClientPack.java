package net;

public class ClientPack {
	
	public String Text;

	public ClientPack(String Text) {
		this.Text=Text;
	}
	
	public byte[] ToBytes(){
		return Text.getBytes();
	}
	public String getText(){
		return Text;
	}

}
