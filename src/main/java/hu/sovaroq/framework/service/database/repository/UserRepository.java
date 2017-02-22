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

}
