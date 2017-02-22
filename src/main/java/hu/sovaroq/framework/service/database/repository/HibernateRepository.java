package hu.sovaroq.framework.service.database.repository;

import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.framework.exception.database.DatabaseException;
import hu.sovaroq.framework.service.database.DatabaseService;
import net.jodah.typetools.TypeResolver;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
        if(DatabaseService.getSessionFactory().isClosed())
            throw new DatabaseException("DatabaseFactory is closed.");
        return DatabaseService.getSessionFactory().openSession();
    }
}
