package hu.sovaroq.framework.service.database;

import org.hibernate.Session;

/**
 * Created by Oryk on 2017. 02. 22..
 */
public interface IHibernateSessionProvider {
    Session openSession();
}
