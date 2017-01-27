package hu.noroc.framework.service.base.config.model;

import java.util.Objects;

/**
 * Created by Oryk on 2017. 01. 25..
 */
@Deprecated
public class ConfigParameter<T> implements IConfigParameter<T> {
    private final String name;
    private T value;

    public ConfigParameter(String name, T value) {
        Objects.requireNonNull(name, "Parameter name cannot be null!");
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public Class<T> getType() {
        if (value == null){
            return null;
        }
        return (Class<T>) value.getClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigParameter<?> that = (ConfigParameter<?>) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = value == null ? result :  31 * result + value.hashCode();
        return result;
    }
}
