package hu.sovaroq.core.database;

import hu.sovaroq.framework.exception.FrameworkException;

import java.util.List;

/**
 * Created by Oryk on 2017. 02. 14..
 */
public interface CRUDRepository<T, ID> {
    T findById(ID id) throws FrameworkException;
    List<T> findBy(String key, String value) throws FrameworkException;
    List<T> findAll() throws FrameworkException;

    void save(T t) throws FrameworkException;
    void delete(ID id) throws FrameworkException;
}
