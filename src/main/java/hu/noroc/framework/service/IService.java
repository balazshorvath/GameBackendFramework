package hu.noroc.framework.service;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IService {
    void onCreate(IService parent);
    void onDestroy();

    void restart();

    String getServiceType();
    String getServiceId();
    String getStatusDescription();
    Double getWorkload();
}
