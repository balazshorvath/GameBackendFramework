package hu.sovaroq.game.data;

import hu.sovaroq.core.database.repository.HibernateRepository;
import hu.sovaroq.core.database.repository.Repository;
import hu.sovaroq.core.database.service.IHibernateSessionProvider;

/**
 * Created by balazs_horvath on 4/3/2017.
 */
@Repository
public class CommanderBaseRepository extends HibernateRepository<CommanderBase> {
    public CommanderBaseRepository(IHibernateSessionProvider provider) {
        super(provider);
    }

    public CommanderBaseRepository(IHibernateSessionProvider provider, Class<CommanderBase> entityType) {
        super(provider, entityType);
    }
}
