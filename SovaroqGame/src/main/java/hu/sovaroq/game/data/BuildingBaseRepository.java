package hu.sovaroq.game.data;

import hu.sovaroq.core.database.repository.HibernateRepository;
import hu.sovaroq.core.database.repository.Repository;
import hu.sovaroq.core.database.service.IHibernateSessionProvider;

@Repository
public class BuildingBaseRepository extends HibernateRepository<BuildingBase> {

    public BuildingBaseRepository(IHibernateSessionProvider provider) {
        super(provider);
    }

    public BuildingBaseRepository(IHibernateSessionProvider provider, Class<BuildingBase> entityType) {
        super(provider, entityType);
    }
}
