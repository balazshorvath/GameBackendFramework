--
-- Created by IntelliJ IDEA.
-- User: Oryk
-- Date: 2017. 02. 27.
-- Time: 21:42
-- To change this template use File | Settings | File Templates.
--


local context = (...)

local self = context.self

local attack, doObjective, update

-- A table for the actions to take based on the state of the entity
local action = {
    [1] = doObjective,
    [2] = attack
}

--
-- Every "tick", the file is called.
--  context:
--      dt          - delta time, now - lastCall
--      entities    - Ordered list of entities closest is the first
--
update = function(dt, entities)
    print("doObjective")
    print(self)
    doObjective(dt, entities)
    print(self)
end

 --action[self.state](dt, entities)

-- End "Main"


--
-- State behaviours
--
attack = function(dt, entities)

end

doObjective = function(dt, entities)
    -- LUA array start from 1 (as IntelliJ told me).
    -- It's interesting, since arrays are tables with
    -- integer keys.
    if entities and entities[1] then
        self.state = 2
        self.attacking = entities[1]
    else
        -- moveMe will assume, that 'self' has:
        --  - x
        --  - y
        --  - speed
        --  - path (containing the path the entity will go through it's lifetime)
        --
        -- Additional info:
        -- The function will use a global movement calculating algorithm and set the
        -- 'self' x, y to the proper value based on speed and dt.
        --
        -- The path's first element has to be the next x, y to reach.
        -- The function will also take care of reaching these points and setting
        -- the new direction.
        --
        --
        --
        helpers.moveMe(self, dt)
    end
end


