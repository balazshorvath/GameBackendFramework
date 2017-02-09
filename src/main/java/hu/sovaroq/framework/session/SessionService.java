package hu.sovaroq.framework.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import hu.sovaroq.framework.bus.IEventBus;
import hu.sovaroq.framework.network.INetworkServiceEvents;
import hu.sovaroq.framework.service.IController;
import hu.sovaroq.framework.service.AbstractService;

public class SessionService extends AbstractService<SessionService.SessionManagerConfig>{

	private Map<UUID, Session> currentSessions;
	
	public SessionService(String serviceId, IEventBus bus) {
		super(serviceId, bus);
	}

	public class SessionManagerConfig{
		
	}
	
	public void onEvent(INetworkServiceEvents.NewClientConnectionRequest request){
		if(currentSessions.containsKey(request.getSessionID())){
			Session oldSession = currentSessions.get(request.getSessionID());
			Session newSession = Session.builder().clientConnection(request.getConnection()).player(oldSession.getPlayer()).sessionID(oldSession.getSessionID()).user(oldSession.getUser()).build();
			currentSessions.put(oldSession.getSessionID(), newSession);

		}else{
			UUID sessionUUID = UUID.randomUUID();
			Session newSession = Session.builder().clientConnection(request.getConnection()).sessionID(sessionUUID).build();
			currentSessions.put(sessionUUID, newSession);
		}
		post(new INetworkServiceEvents.NewClientConnectionResponse(currentSessions.get(request.getSessionID())));
	}

	@Override
	public void start(SessionManagerConfig config) {
		currentSessions = new HashMap<>();
		
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
