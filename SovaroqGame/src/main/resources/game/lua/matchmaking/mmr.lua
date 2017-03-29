--
-- Created by IntelliJ IDEA.
-- User: balazs_horvath
-- Date: 3/29/2017
-- Time: 10:08
-- To change this template use File | Settings | File Templates.
--

getMMR = function(playerWin, playerLose, playerXP, xpMultiplier, winLoseMultiplier, totalGamesMultiplier)
    if (playerLose + playerWin) == 0 then
        return 0;
    end
    local wl = (playerWin / playerLose) * winLoseMultiplier
    local total = (playerLose + playerWin) * totalGamesMultiplier
    local xp = playerXP * xpMultiplier

    return wl + total + xp
end


-- mmr, ms, mmr, mmr
getMMRRange = function(playerMMR, timeInQueue, maxMMRRange, mmrRangeIncrement)
    local sec = timeInQueue / 1000
end
