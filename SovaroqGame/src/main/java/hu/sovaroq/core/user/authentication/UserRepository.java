package hu.sovaroq.core.user.authentication;


import hu.sovaroq.core.database.repository.HibernateRepository;
import hu.sovaroq.core.database.repository.Repository;
import hu.sovaroq.core.database.service.IHibernateSessionProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Oryk on 2017. 02. 14..
 */
@Repository
public class UserRepository extends HibernateRepository<User> {

    public UserRepository(IHibernateSessionProvider provider) {
        super(provider);
    }

    public UserRepository(IHibernateSessionProvider provider, Class<User> entityType) {
        super(provider, entityType);
    }

    public User findByLogon(String logon) {
        EntityManager entityManager = provider.getEntityManager();
        CriteriaBuilder criteriaBuilder = provider.getCriteriaBuilder();
        User entity = null;
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(entityType);
        Root<User> rootElement = criteriaQuery.from(entityType);
        criteriaQuery.select(rootElement);
        criteriaQuery.where(criteriaBuilder.equal(rootElement.get(User.LOGIN), logon));

        try {
            entity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException ex) {
            entity = null;
        }

        return entity;
    }
}
