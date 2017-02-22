package hu.sovaroq.framework.database;

import hu.sovaroq.framework.core.logger.LogProvider;
import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.service.database.IHibernateSessionProvider;
import net.jodah.typetools.TypeResolver;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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
        // Auto-closeable
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            entity = session.get(entityType, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            log.error(e);
            throw new FrameworkException(e);
        }
        return entity;
    }

    @Override
    public List<T> findBy(String key, String value) throws FrameworkException {
        Transaction tx = null;
        List<T> entities = null;
        // Auto-closeable
        try (Session session = openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<T> query = builder.createQuery(entityType);
            Root<T> root = query.from(entityType);
            query.where(builder.equal(root.get(key), value));

            Query q = session.createQuery(query);

            entities = q.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            log.error(e);
            throw new FrameworkException(e);
        }
        return entities;
    }


    @Override
    public List<T> findAll() throws FrameworkException {
        Transaction tx = null;
        List<T> entities = null;
        // Auto-closeable
        try (Session session = openSession()) {
            tx = session.beginTransaction();

            CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(entityType);
            Query q = session.createQuery(query);

            entities = q.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            log.error(e);
            throw new FrameworkException(e);
        }
        return entities;
    }

    @Override
    public Long save(T t) throws FrameworkException {
        return null;
    }

    @Override
    public void delete(Long id) throws FrameworkException {

    }

    protected Session openSession() throws FrameworkException {
        Session session = provider.openSession();
        if(session == null){
            throw new FrameworkException("Database is not present.");
        }
        return session;
    }
}
