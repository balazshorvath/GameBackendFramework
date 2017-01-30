package hu.sovaroq.framework.service;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService<Config> {
    void onCreate(Config config);
    void onDestroy();
    void restart();

    String getServiceId();
    IController getParent();

    Config getConfig();
    void setConfig(Config config);
}
