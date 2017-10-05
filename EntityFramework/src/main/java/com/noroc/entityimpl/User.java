package com.noroc.entityimpl;

import com.noroc.entityapi.ISession;
import com.noroc.entityapi.IAction;
import com.noroc.entityapi.IEntity;
import com.noroc.entityapi.NetworkAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements IEntity {
    private Set<IEntity> relatives = new HashSet<>();
    private ISession session;
    private String username;

    @NetworkAction
    public List<IAction> login(LoginMessage login){
        List<IAction> actions = new ArrayList<>();
        return actions;
    }


    @Override
    public ISession getSession() {
        return session;
    }

    @Override
    public List<IEntity> getRelatives() {
        return new ArrayList<>(relatives);
    }


}
