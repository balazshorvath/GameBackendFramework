package com.noroc.entityapi;

import java.util.List;

public interface IEntityCloud {
    List<IEntity> getEntities(String sessionId);
    void newEntity(IEntity entity, String sessionId);
}
