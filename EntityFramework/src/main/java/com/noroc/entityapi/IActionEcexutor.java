package com.noroc.entityapi;

public interface IActionEcexutor<T extends IAction> {
    void execute(T action);
}
