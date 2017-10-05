package com.noroc.entityapi;

import java.net.Socket;

public interface ISessionFactory {
    ISession createSession();
}
