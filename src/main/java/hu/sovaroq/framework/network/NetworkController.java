package hu.sovaroq.framework.network;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.Service;

public class NetworkController extends Service<NetworkController.NetworkConfig>{
	
	private boolean enabled;
	
	private Thread connectionAccepting;
	
	private int listenerPort;

	public NetworkController(IController parent, String serviceId) {
		super(parent, serviceId);
	}

	@Override
	public void onCreate(NetworkConfig config) {
		connectionAccepting = new Thread(new Runnable(){
			@Override
			public void run() {
				SSLServerSocket serverSocket = null;
				try {
					serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(listenerPort);
				} catch (IOException e1) {
					enabled = false;
				}
				
				while(enabled){
					try {
						SSLSocket socket = (SSLSocket) serverSocket.accept();
						ClientConnection connection = new ClientConnection();
						connection.start(socket);
					} catch (IOException e) {						
						e.printStackTrace();
					}				
					
				}
				
			}
			
		});
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfig(NetworkConfig config) {
		// TODO Auto-generated method stub
		
	}
	
	public class NetworkConfig{
		
	}

	@Override
	public String getStatusDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getWorkload() {
		// TODO Auto-generated method stub
		return null;
	}

}
