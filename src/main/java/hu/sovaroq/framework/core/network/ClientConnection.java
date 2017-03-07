package hu.sovaroq.framework.core.network;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import hu.sovaroq.framework.core.eventbase.IFrameworkEvent;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.network.ISovaroqConnection;
import lombok.Data;

/**
 * 
 */
@Data
public class ClientConnection implements ISovaroqConnection {
	private GameSocket gameSocket;
	
	private InetAddress userAddress;

	@Override
	public IFrameworkEvent poll(long timeout, TimeUnit unit) {
		return null;
	}

	@Override
	public IFrameworkEvent peek() {
		return null;
	}

	@Override
	public void send(IFrameworkEvent o) throws FrameworkException {

	}

	@Override
	public void cache(IFrameworkEvent o) {

	}

	@Override
	public void flush() throws FrameworkException {

	}

	@Override
	public boolean isOpen() {
		return false;
	}
}
