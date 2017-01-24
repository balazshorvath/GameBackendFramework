package hu.noroc.framework.service.base.config.model;

/**
 * Value can be another IServiceConfig.
 * Could be used to generate a form, or something.
 *
 * Created by Oryk on 2017. 01. 23..
 */
public interface IConfigParameter<T> {
    String getName();
    T getValue();
    void setValue(T value);
    Class<T> getType();
}
