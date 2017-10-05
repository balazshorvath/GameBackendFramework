package com.noroc.api.network;

public interface IServer extends Runnable {
    RunnableState init();
    RunnableState stop();

    RunnableState getState();
    int getPort();
    String getIp();
}
