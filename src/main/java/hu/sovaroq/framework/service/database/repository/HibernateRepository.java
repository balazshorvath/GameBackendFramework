package hu.sovaroq.framework.service.database.repository;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.exception.database.DatabaseException;
import hu.sovaroq.framework.service.database.DatabaseService;
import net.jodah.typetools.TypeResolver;

/**
 * Created by Oryk on 2017. 02. 14..
 */
@SuppressWarnings("unchecked")
public abstract class HibernateRepository<T> implements CRUDRepository<T> {
    protected final Class<T> entityType;
    
    protected Logger log = LogProvider.createLogger(this.getClass());

    public HibernateRepository(){
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(HibernateRepository.class, getClass());
        this.entityType = (Class<T>) typeArguments[0];
    }

    @Override
    public T findById(Long id) throws FrameworkException {
        Transaction tx = null;
        T entity = null;
        // Auto-closeable
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            entity = session.get(entityType, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return entity;
    }


    @Override
    public Long save(T t) throws FrameworkException {
        return null;
    }

    @Override
    public void delete(Long id) throws FrameworkException {

    }

    protected Session openSession() throws FrameworkException {
        if(DatabaseService.getSessionFactory().isClosed())
            throw new DatabaseException("DatabaseFactory is closed.");
        return DatabaseService.getSessionFactory().openSession();
    }
}
