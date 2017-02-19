package hu.sovaroq.experiment.mongo.repositories;

import com.mongodb.DB;
import hu.sovaroq.experiment.mongo.MongoDBRepo;
import hu.sovaroq.experiment.mongo.data.Conversation;

/**
 * Created by Oryk on 2017. 02. 19..
 */
public class ConversationRepository extends MongoDBRepo<Conversation, String> {
    public ConversationRepository(DB database) {
        super(database);
    }
}
