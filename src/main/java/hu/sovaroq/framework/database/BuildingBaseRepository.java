package hu.sovaroq.framework.database;

import hu.sovaroq.framework.service.database.IHibernateSessionProvider;
import hu.sovaroq.game.core.data.BuildingBase;

@Repository
public class BuildingBaseRepository extends HibernateRepository<BuildingBase> {

    public BuildingBaseRepository(IHibernateSessionProvider provider) {
        super(provider);
    }

    public BuildingBaseRepository(IHibernateSessionProvider provider, Class<BuildingBase> entityType) {
        super(provider, entityType);
    }
}
