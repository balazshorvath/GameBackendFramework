package hu.sovaroq.framework.database;

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
}
