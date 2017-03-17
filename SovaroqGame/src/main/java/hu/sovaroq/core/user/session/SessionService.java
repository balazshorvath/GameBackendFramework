package hu.sovaroq.core.user.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import hu.sovaroq.framework.eventing.bus.EventListener;
import hu.sovaroq.core.user.authentication.IUser;
import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;

@Service
@EventListener
public class SessionService extends AbstractService<SessionService.SessionManagerConfig>{

	private Map<UUID, Session> currentSessions = new ConcurrentHashMap<>();
	
	private Map<IUser, UUID> userToSessionID = new ConcurrentHashMap<>();
		
	public SessionService() {
		super();
	}

	public class SessionManagerConfig{
		
	}
	
	public void onEvent(ISessionServiceEvents.CreateOrGetSession request){
		log.debug("received: " + request);
		UUID sessionID = request.getSessionID();
		IUser user = request.getUser();
		if(sessionID == null && user != null){
			if(userToSessionID.containsKey(user)){
				post(new ISessionServiceEvents.CreateOrGetSessionResponse(request.getRequestId(), currentSessions.get(userToSessionID.get(user))));
			}else{
				UUID sessionUUID = UUID.randomUUID();
				Session newSession = Session.builder().sessionID(sessionUUID).user(request.getUser()).build();
				currentSessions.put(sessionUUID, newSession);
				userToSessionID.put(request.getUser(), sessionUUID);
				post(new ISessionServiceEvents.CreateOrGetSessionResponse(request.getRequestId(), currentSessions.get(sessionUUID)));
			}
		}else if(sessionID != null){
			if(currentSessions.containsKey(sessionID)){
				post(new ISessionServiceEvents.CreateOrGetSessionResponse(request.getRequestId(), currentSessions.get(sessionID)));
			}else{
				Session newSession = Session.builder().sessionID(sessionID).build();
				currentSessions.put(sessionID, newSession);
			}
		}
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
