package hu.sovaroq.framework.service.database.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import hu.sovaroq.framework.data.user.IUser;
import hu.sovaroq.framework.data.user.User;
import hu.sovaroq.framework.exception.FrameworkException;

/**
 * Created by Oryk on 2017. 02. 14..
 */
public class UserRepository extends HibernateRepository<User> {


    public IUser getByLogon(String logon) throws FrameworkException {
        User user = null;
        // Auto-closeable
        try (Session session = openSession()) {

            user = session.bySimpleNaturalId(User.class).load(logon);

        } catch (HibernateException e) {
        	log.error("Failed to get user. " + e);
            e.printStackTrace();
        }
        return user;
    }

	@Override
	public List<User> findAll() throws FrameworkException {
		// TODO Auto-generated method stub
		return null;
	}
}
