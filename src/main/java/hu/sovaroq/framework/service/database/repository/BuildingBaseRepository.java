package hu.sovaroq.framework.service.database.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hu.sovaroq.framework.exception.FrameworkException;
import hu.sovaroq.game.core.data.BuildingBase;

public class BuildingBaseRepository extends HibernateRepository<BuildingBase>{

	@Override
	public List<BuildingBase> findAll() throws FrameworkException {
        Transaction tx = null;
        List<BuildingBase> bases = new ArrayList<>();
        // Auto-closeable
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            
            CriteriaQuery<BuildingBase> query = session.getCriteriaBuilder().createQuery(BuildingBase.class);
            Root<BuildingBase> root = query.from(BuildingBase.class);
            query.select(root);
//            BuildingBase_ base = null;
//            query.where(session.getCriteriaBuilder().equal(root.get(BuildingBase_.)))
            
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return bases;
	}
	

}
