package hu.sovaroq.core.matchmaking;

import hu.sovaroq.framework.service.AbstractService;
import hu.sovaroq.framework.service.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by balazs_horvath on 3/29/2017.
 */
@Service(configurationClass = IMatchmakingService.MatchmakingConfig.class, configurationFile = "game/Matchmaking.properties")
public class MatchmakingService extends AbstractService<IMatchmakingService.MatchmakingConfig> {
    Set<IMatchmakingService.MatchmakingConfig> pool = new HashSet<>();


}
