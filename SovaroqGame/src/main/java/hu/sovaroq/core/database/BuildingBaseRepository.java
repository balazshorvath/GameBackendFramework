package hu.sovaroq.core.database;

import hu.sovaroq.game.data.BuildingBase;

@Repository
public class BuildingBaseRepository extends HibernateRepository<BuildingBase> {

    public BuildingBaseRepository(IHibernateSessionProvider provider) {
        super(provider);
    }

    public BuildingBaseRepository(IHibernateSessionProvider provider, Class<BuildingBase> entityType) {
        super(provider, entityType);
    }
}
