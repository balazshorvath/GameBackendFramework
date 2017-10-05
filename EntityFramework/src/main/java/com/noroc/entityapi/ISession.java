package com.noroc.entityapi;

public interface ISession {
    String getId();
    IConnection getConnection();
}
