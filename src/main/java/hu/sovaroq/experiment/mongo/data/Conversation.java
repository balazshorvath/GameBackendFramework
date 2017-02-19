package hu.sovaroq.experiment.mongo.data;

import lombok.Data;
import org.mongojack.DBRef;
import org.mongojack.Id;
import org.mongojack.MongoCollection;
import org.mongojack.ObjectId;

import java.util.Set;

/**
 * Created by Oryk on 2017. 02. 19..
 */
@Data
@MongoCollection(name = "Conversation")
public class Conversation {
    @Id @ObjectId
    String id = new org.bson.types.ObjectId().toString();
    Set<DBRef<MongoUser, String>> participants;
    String name;

    public DBRef<Conversation, String> makeRef(){
        return new DBRef<>(id, (Class<Conversation>) this.getClass());
    }
}
