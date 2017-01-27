package hu.noroc.framework.service;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService<Config> {
    void onCreate(Config config);
    void onDestroy();
    void restart();

    String getServiceId();
    IContext getParent();

    Config getConfig();
    void setConfig(Config config);
}
