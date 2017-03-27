package hu.sovaroq.core.network.stream;

import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

public class GameInputStream extends CipherInputStream{

	public GameInputStream(InputStream is, Cipher c) {
		super(is, c);
		// TODO Auto-generated constructor stub
	}	
	
	public byte readByte(){
		return 0x00;
	}
	
	public String readString(int lenght){
		return "";
	}
	
	public byte startNextMessageRead(){
		//read MESSAGE_START and return message type byte
		return 0x00;
	}
	
	public Double readDouble(){
		return 0d;
	}
	
	public Integer readInt(){
		return 0;
	}

}
