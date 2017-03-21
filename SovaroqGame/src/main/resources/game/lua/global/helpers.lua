--
-- Created by IntelliJ IDEA.
-- User: Oryk
-- Date: 2017. 02. 27.
-- Time: 21:58
-- To change this template use File | Settings | File Templates.
--


helpers = {
    -- testFunction
    testFunction = function()
        print("helpers.testFunction")
    end,
    -- moveMe will assume, that 'entity' has:
    --  - x
    --  - y
    --  - speed
    --  - path (containing the path the entity will go through it's lifetime)
    --
    -- Additional info:
    -- The function will use a global movement calculating algorithm and set the
    -- 'entity' x, y to the proper value based on speed and dt.
    --
    -- The path's first element has to be the next x, y to reach.
    -- The function will also take care of reaching these points and setting
    -- the new direction.
    --
    -- return
    --
    moveMe = function(entity, dt)
        if entity.path[1] == nil then
            return nil
        end
        local time = helpers.calcTime(entity.x, entity.y, entity.path[1].x, entity.path[1].y, entity.speed)
        if time <= 1 then
            entity.x, entity.y = entity.path[1].x, entity.path[1].y

            table.remove(entity.path, 1)
            return entity.path[1]
        end

        return nil
    end,

    --
    -- Algorithm from Noroc project
    --
    calcTime = function(xFrom, yFrom, xTo, yTo, speed)
        return math.sqrt((xTo - xFrom)*(xTo - xFrom) + (yTo - yFrom)*(yTo - yFrom)) / (10.0 * speed)
    end
}
