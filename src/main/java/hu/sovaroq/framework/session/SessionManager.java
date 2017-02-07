package hu.sovaroq.framework.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import hu.sovaroq.framework.network.INetworkControllerEvents;
import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.AbstractService;

public class SessionManager extends AbstractService<SessionManager.SessionManagerConfig>{

	private Map<UUID, Session> currentSessions;
	
	public SessionManager(IController parent, String serviceId) {
		super(parent, serviceId);
		// TODO Auto-generated constructor stub
	}

	public class SessionManagerConfig{
		
	}
	
	public void onEvent(INetworkControllerEvents.NewClientConnectionRequest request){
		if(currentSessions.containsKey(request.getSessionID())){
			//post on bus
			new INetworkControllerEvents.NewClientConnectionResponse(currentSessions.get(request.getSessionID()));
		}else{
			UUID sessionUUID = UUID.randomUUID();
			Session newSession = Session.builder().clientConnection(request.getConnection()).sessionID(sessionUUID).build();
			//post on bus
			new INetworkControllerEvents.NewClientConnectionResponse(newSession);
			currentSessions.put(sessionUUID, newSession);
		}
	}

	@Override
	public void onCreate(SessionManagerConfig config) {
		currentSessions = new HashMap<>();
		
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
	public void setConfig(SessionManagerConfig config) {
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
}
