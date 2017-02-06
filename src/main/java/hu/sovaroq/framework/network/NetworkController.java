package hu.sovaroq.framework.network;

import hu.sovaroq.framework.configuration.annotation.Config;
import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.Service;
import hu.sovaroq.game.core.config.DefaultFileParser;

public class NetworkController extends Service<NetworkController.NetworkConfig>{

	public NetworkController(IController parent, String serviceId) {
		super(parent, serviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(NetworkConfig config) {
		// TODO Auto-generated method stub
		
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
	
	@Config(fileParser = DefaultFileParser.class)
	public class NetworkConfig{
		
	}

}
