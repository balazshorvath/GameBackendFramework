package hu.sovaroq.framework.database;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.service.database.IHibernateSessionProvider;

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
    
    public User findByLogon(String logon){
    	EntityManager entityManager = provider.getEntityManager();
    	CriteriaBuilder criteriaBuilder = provider.getCriteriaBuilder();
        User entity = null;        
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(entityType);
        Root<User> rootElement = criteriaQuery.from(entityType);
        criteriaQuery.select(rootElement);
        criteriaQuery.where(criteriaBuilder.equal(rootElement.get(User.USERID), logon));

        entity = entityManager.createQuery(criteriaQuery).getSingleResult();
        
        return entity;
    }
}
