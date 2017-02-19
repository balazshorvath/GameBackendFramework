package hu.sovaroq.experiment.mongo.repositories;

import com.mongodb.DB;
import hu.sovaroq.experiment.mongo.MongoDBRepo;
import hu.sovaroq.experiment.mongo.data.MongoUser;

/**
 * Created by Oryk on 2017. 02. 19..
 */
public class UserRepository extends MongoDBRepo<MongoUser, String> {
    public UserRepository(DB database) {
        super(database);
    }
}
