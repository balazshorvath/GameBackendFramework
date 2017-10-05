package com.noroc.entityapi;

import java.util.List;

public interface IEntity {
    ISession getSession();
    List<IEntity> getRelatives();
}
