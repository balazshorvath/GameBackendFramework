package hu.sovaroq.framework.network;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.AbstractService;

public class NetworkController extends AbstractService<NetworkController.NetworkConfig>{
	
	private boolean enabled;
	
	private Thread connectionAccepting;
	
	private int listenerPort;

	public NetworkController(IController parent, String serviceId) {
		super(parent, serviceId);
	}

	@Override
	public void start(NetworkConfig config) {
		log.debug("onCreate");
		connectionAccepting = new Thread(new Runnable(){
			@Override
			public void run() {
				SSLServerSocket serverSocket = null;
				try {
					serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(listenerPort);
					log.info("SSLServerSocket opened on port: " + listenerPort);
				} catch (IOException e1) {
					enabled = false;
					log.error("Could not open SSLServerSocket!" + e1);
				}
				
				SSLSocket socket = null;
				
				while(enabled){
					try {
						socket = (SSLSocket) serverSocket.accept();
						log.info("Connection received from " + socket.getRemoteSocketAddress().toString());
						ClientConnection connection = new ClientConnection();
						connection.start(socket);
						//bus post
						new INetworkControllerEvents.NewClientConnectionRequest.NewClientConnectionRequestBuilder().connection(connection).build();						
						
					} catch (IOException e) {						
						e.printStackTrace();
					}				
					
				}
				
			}
			
		});
	}

	@Override
	public void stop() {
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
