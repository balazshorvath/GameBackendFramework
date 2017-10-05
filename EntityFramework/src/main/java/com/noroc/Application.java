package com.noroc;

import com.noroc.entityapi.IEntityManager;
import com.noroc.entityimpl.User;

public class Application {

    /**
     * Called from the framework.
     */
    public int initFramework(IEntityManager manager){
        manager.register(User.class);

        return 0;
    }

}
