package hu.sovaroq.experiment.mongo.data;

import lombok.Data;
import org.mongojack.DBRef;
import org.mongojack.Id;
import org.mongojack.MongoCollection;
import org.mongojack.ObjectId;

/**
 * Created by Oryk on 2017. 02. 19..
 */
@Data
@MongoCollection(name = "ChatMessage")
public class ChatMessage {
    @Id @ObjectId
    String id = new org.bson.types.ObjectId().toString();
    long timestamp;
    DBRef<Conversation, String> conversation;
    String message;
    DBRef<MongoUser, String> user;

    public DBRef<ChatMessage, String> makeRef(){
        return new DBRef<>(id, (Class<ChatMessage>) this.getClass());
    }
}
