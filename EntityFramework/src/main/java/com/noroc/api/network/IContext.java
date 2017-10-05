package com.noroc.api.network;

import com.noroc.entityapi.ISession;

public interface IContext extends Runnable {
    RunnableState init();
    RunnableState stop();
    void newSession(ISession session);


    RunnableState getState();
}
