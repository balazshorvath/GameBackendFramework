package hu.sovaroq.framework.service;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService<Config> {
    void start(Config config);
    void stop();
    void restart();

    String getServiceId();
    IController getParent();

    Config getConfig();
    void setConfig(Config config);
    
    String getStatusDescription();
    Double getWorkload();
}
