package com.noroc.entityapi;

import com.noroc.api.network.protocol.IDataConverter;

public interface IConnection {
    INetworkMessage readNextMessage();
    State close();

    String getIp();
    State getState();
    IDataConverter getDataConverter();

    enum State{
        CONNECTING,
        CONNECTED,
        DISCONNECTED,
        CLOSED
    }
}
