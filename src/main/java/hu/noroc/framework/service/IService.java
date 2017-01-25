package hu.noroc.framework.service;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService {
    void onCreate(IContext parent);
    void onDestroy();

    void restart();
    String getServiceId();
}
