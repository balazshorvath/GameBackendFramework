--
-- Created by IntelliJ IDEA.
-- User: Oryk
-- Date: 2017. 02. 27.
-- Time: 21:42
-- To change this template use File | Settings | File Templates.
--
local context = (...)

local objective = context.objective
local self = context.self

local attack, doObjective

-- A table for the actions to take based on the state of the entity
local action = {
    [1] = doObjective,
    [2] = attack
}

--
-- Every "tick", uptade is called.
--  Params:
--      dt          - delta time, now - lastCall
--      entities    - Ordered list of entities closest is the first
--
function update(dt, entities)
    action[self.state](dt, entities)
end

--
-- State behaviours
--
attack = function(dt, entities)

end

doObjective = function(dt, entities)
    -- LUA array start from 1 (as IntelliJ told me).
    -- It's interesting, since arrays are tables with
    -- integer keys.
    if entities[0] ~= nil then
        self.state = 2
        self.attacking = entities[0]
    else
        api.
    end
end


