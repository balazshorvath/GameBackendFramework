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
@MongoCollection(name = "MongoUser")
public class MongoUser {
    @Id @ObjectId
    String id = new org.bson.types.ObjectId().toString();
    String login;
    String password;
    boolean locked = false;

    public DBRef<MongoUser, String> makeRef(){
        return new DBRef<>(id, (Class<MongoUser>) this.getClass());
    }
}
