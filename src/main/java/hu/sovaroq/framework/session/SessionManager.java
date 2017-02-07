package hu.sovaroq.framework.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import hu.sovaroq.framework.network.INetworkControllerEvents;
import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.Service;

public class SessionManager extends Service<SessionManager.SessionManagerConfig>{

	private Map<UUID, Session> currentSessions;
	
	public SessionManager(IController parent, String serviceId) {
		super(parent, serviceId);
		// TODO Auto-generated constructor stub
	}

	public class SessionManagerConfig{
		
	}
	
	public void onEvent(INetworkControllerEvents.NewClientConnectionRequest request){
		if(currentSessions.containsKey(request.getSessionID())){
			Session oldSession = currentSessions.get(request.getSessionID());
			Session newSession = Session.builder().clientConnection(request.getConnection()).player(oldSession.getPlayer()).sessionID(oldSession.getSessionID()).user(oldSession.getUser()).build();
			currentSessions.put(oldSession.getSessionID(), newSession);

		}else{
			UUID sessionUUID = UUID.randomUUID();
			Session newSession = Session.builder().clientConnection(request.getConnection()).sessionID(sessionUUID).build();
			currentSessions.put(sessionUUID, newSession);
		}
		//bus post
		INetworkControllerEvents.NewClientConnectionResponse.builder().clientSession(currentSessions.get(request.getSessionID())).build();
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
