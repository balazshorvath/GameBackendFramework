package hu.sovaroq.framework.network;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.extension.Run;
import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.service.AbstractService;

public class NetworkService extends AbstractService<NetworkService.NetworkConfig> {

	public NetworkService() {
		super();
	}

	private boolean enabled;

	private int listenerPort;

	@Override
	@Run
	public void start(NetworkConfig config) {
		log.debug("start");
		SSLServerSocket serverSocket = null;
		try {
			serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(listenerPort);
			log.info("SSLServerSocket opened on port: " + listenerPort);
		} catch (IOException e1) {
			enabled = false;
			log.error("Could not open SSLServerSocket!" + e1);
		}

		SSLSocket socket = null;

		while (enabled) {
			try {
				socket = (SSLSocket) serverSocket.accept();
				log.info("Connection received from " + socket.getRemoteSocketAddress().toString());
				ClientConnection connection = new ClientConnection();
				connection.start(socket);
				post(new INetworkServiceEvents.NewClientConnectionRequest(null, connection));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConfig(NetworkConfig config) {
		// TODO Auto-generated method stub

	}

	public class NetworkConfig {

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

	@Override
	public void stop() {
		enabled = false;
	}

}
