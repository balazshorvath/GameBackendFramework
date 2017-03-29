package hu.sovaroq.core.matchmaking;

import hu.sovaroq.core.user.authentication.User;
import lombok.Value;

/**
 * Created by balazs_horvath on 3/29/2017.
 */
public class IMatchmakingService {


    @Value
    public static class MatchmakingConfig {
        private String mmrScript;

        private Double xpMultiplier;
        private Double winLoseMultiplier;
        private Double totalGamesMultiplier;

        private Long mmrRangeIncrement;
        private Long maximumMMRRange;
        private Long initialMMRRange;
    }

    @Value
    public static class MatchFinder {
        public final User user;
        public final Long mmr;
        public final Long queuedAt;
        public Long mmrRange;
    }
}
