package hu.sovaroq.framework.service;

import hu.sovaroq.framework.eventing.bus.IEventBus;
import org.apache.logging.log4j.Logger;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService<Config> {
    void start(Config config);

    void stop();

    void restart();

    Config getConfig();

    void setConfig(Config config);

    String getStatusDescription();

    Double getWorkload();

    void setBus(IEventBus bus);

    void setLog(Logger log);
}
