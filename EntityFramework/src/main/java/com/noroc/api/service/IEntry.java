package com.noroc.api.service;

public interface IEntry {
    State init();
    State destroy();

    State getState();

    enum State {
        CREATED,
        CREATION_FAILED,
        INITIALIZED,
        INITIALIZATION_FAILED,
        DESTROYED
    }
}
