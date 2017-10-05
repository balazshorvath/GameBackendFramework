package com.noroc.entityimpl;

import com.noroc.entityapi.ISession;
import com.noroc.entityapi.INetworkMessage;

public class LoginMessage implements INetworkMessage {
    private String login;
    private String password;

    @Override
    public ISession getSession() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

}
