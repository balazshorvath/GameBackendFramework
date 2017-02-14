package hu.sovaroq.framework.core.network;

import java.net.InetAddress;

import javax.net.ssl.SSLSocket;

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
