package hu.sovaroq.experiment.mongo.repositories;

import com.mongodb.DB;
import hu.sovaroq.experiment.mongo.MongoDBRepo;
import hu.sovaroq.experiment.mongo.data.ChatMessage;

/**
 * Created by Oryk on 2017. 02. 19..
 */
public class ChatMessageRepository extends MongoDBRepo<ChatMessage, String> {
    public ChatMessageRepository(DB database) {
        super(database);
    }
}
