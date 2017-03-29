package hu.sovaroq.core.network;

import java.util.ArrayList;
import java.util.List;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.features.Run;

public class NetworkService extends AbstractService<NetworkService.NetworkConfig> {

	List<ClientConnection> clientConnection = new ArrayList<>();

    public NetworkService() {
        super();
    }

    @Override
    @Run
    public void start(NetworkConfig config) {
        log.debug("start");
        //poll client connection for messages. 

    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setConfig(NetworkConfig config) {
        // TODO Auto-generated method stub

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
        
    }

    public class NetworkConfig {

    }

}
