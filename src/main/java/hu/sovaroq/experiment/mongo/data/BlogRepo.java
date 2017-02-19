package hu.sovaroq.experiment.mongo.data;

import com.mongodb.DB;
import hu.sovaroq.experiment.mongo.MongoDBRepo;

/**
 * Created by Oryk on 2017. 02. 19..
 */
public class BlogRepo extends MongoDBRepo<BlogPost, String> {
    public BlogRepo(DB database) {
        super(database);
    }
}
