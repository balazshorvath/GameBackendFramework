package hu.sovaroq.framework.service.database.repository;

import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.exception.FrameworkException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Oryk on 2017. 02. 14..
 */
public class UserRepository extends HibernateRepository<User> {


    public List<User> findBy(String key, String value) throws FrameworkException {
        Transaction tx = null;
        List<User> entities = null;
        // Auto-closeable
        try (Session session = openSession()) {

            CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(entityType);
            Root<User> root = query.from(entityType);
            query.select(root);
//            query.where(session.getCriteriaBuilder().equal(root.get()))
            tx = session.beginTransaction();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return entities;
    }
}
