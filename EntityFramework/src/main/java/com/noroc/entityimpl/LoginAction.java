package com.noroc.entityimpl;

import com.noroc.entityapi.IAction;
import com.noroc.entityapi.IEntity;

public class LoginAction implements IAction {
    private IEntity causer;
    private String login;
    private String password;

    @Override
    public IEntity getCauser() {
        return causer;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
