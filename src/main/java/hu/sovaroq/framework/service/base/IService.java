package hu.sovaroq.framework.service.base;

import org.apache.logging.log4j.Logger;

import hu.sovaroq.framework.core.bus.IEventBus;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService<Config> {
    void start(Config config);
    void stop();
    void restart();

    Config getConfig();
    String getStatusDescription();
    Double getWorkload();

    void setConfig(Config config);
    void setBus(IEventBus bus);
    void setLog(Logger log);
}
