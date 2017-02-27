package hu.sovaroq.framework.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.service.database.IHibernateSessionProvider;
import net.jodah.typetools.TypeResolver;

/**
 * In case you want to initialize this class on it's own (not a subclass of this),
 * use the <code>HibernateRepository(IHibernateSessionProvider provider, Class<T> entityType)<code/>
 * constructor, otherwise the type parameter will be incorrect
 *
 * Created by Oryk on 2017. 02. 14..
 */
@SuppressWarnings("unchecked")
public class HibernateRepository<T> implements CRUDRepository<T, Long> {
    protected final Class<T> entityType;
    protected final IHibernateSessionProvider provider;
    
    protected Logger log = LogProvider.createLogger(this.getClass());

    public HibernateRepository(IHibernateSessionProvider provider){
        this.provider = provider;
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(HibernateRepository.class, getClass());
        this.entityType = (Class<T>) typeArguments[0];
    }
    public HibernateRepository(IHibernateSessionProvider provider, Class<T> entityType){
        this.provider = provider;
        this.entityType = entityType;
    }

    @Override
    public T findById(Long id) throws FrameworkException {
        Transaction tx = null;
        T entity = null;

        return entity;
    }

    @Override
    public List<T> findBy(String key, String value) throws FrameworkException {
        Transaction tx = null;
        List<T> entities = null;
        // Auto-closeable
        return entities;
    }


    @Override
    public List<T> findAll() throws FrameworkException {
        EntityTransaction tx = null;
        List<T> entities = null;

        return entities;
    }

    @Override
    public Long save(T t) throws FrameworkException {
        return null;
    }

    @Override
    public void delete(Long id) throws FrameworkException {

    }

    protected EntityManager getEntityManager() throws FrameworkException {
        EntityManager em = provider.getEntityManager();
        if(em == null){
            throw new FrameworkException("Database is not present.");
        }
        return em;
    }

    public Class<T> getEntityType() {
        return entityType;
    }
}
