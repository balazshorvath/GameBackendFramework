package hu.sovaroq.framework.network;

import java.net.InetAddress;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import lombok.Data;

/**
 * 
 */
@Data
public class ClientConnection {
	
	private SSLSocket keyExchangeSocket;
	
	private GameSocket gameSocket;
	
	private InetAddress userAddress;
	
	public void start(SSLSocket socket){
		keyExchangeSocket =  socket;
		
	}
	
}
