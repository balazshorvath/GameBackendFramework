package com.noroc.entityapi;

import java.lang.reflect.Method;

public interface IEntityManager {
    void register(Class<? extends IEntity> entityType);
    IEntityConfig getConfig(Class<? extends IEntity> entityType);

    interface IEntityConfig {
        Class<? extends IEntity> getType();
        Method getMethodForMessage(Class<? extends INetworkMessage> messageType);
    }
}
