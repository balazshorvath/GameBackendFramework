package hu.sovaroq.framework.service.database;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Oryk on 2017. 02. 22..
 */
public interface IHibernateSessionProvider {
    EntityManager getEntityManager();
    CriteriaBuilder getCriteriaBuilder(); 
}
