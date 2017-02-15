package hu.sovaroq.experiment.mongo;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongojack.DBRef;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import hu.sovaroq.experiment.mongo.data.BlogPost;
import hu.sovaroq.experiment.mongo.data.Comment;
import hu.sovaroq.experiment.mongo.data.User;

/**
 * Created by Oryk on 12/24/2015.
 */
public class NorocDB {

    private String url;
    private String db;
    private String user;
    private String pw;

    private MongoClient client;
    private DB database;

    private static NorocDB instance;


    private NorocDB(String url, String db, String user, String pw) {
        this.url = url;
        this.db = db;
        this.user = user;
        this.pw = pw;
    }

    private NorocDB(String url, String db) {
        this.url = url;
        this.db = db;
    }

    public static NorocDB getInstance(String url, String db, String user, String pw){
        if(instance == null){
            instance = new NorocDB(url, db, user, pw);
            instance.initialize();
        }
        return instance;
    }

    public static NorocDB getInstance(String url, String db){
        if(instance == null){
            instance = new NorocDB(url, db);
            instance.initialize();
        }
        return instance;
    }
    public static NorocDB getInstance(){
        if(instance == null){
            instance = new NorocDB("localhost", "Test");
            instance.initialize();
        }
        return instance;
    }

    private void initialize(){
        client = new MongoClient(url);
        database = client.getDB(db);

    }

    public static void main(String[] args) throws IOException{
    	NorocDB db = NorocDB.getInstance();
    	MongoDBRepo<User, ObjectId> userRepo = new MongoDBRepo<>(db.database);
    	MongoDBRepo<BlogPost, ObjectId> blogRepo = new MongoDBRepo<>(db.database);
    	MongoDBRepo<Comment, ObjectId> commentRepo = new MongoDBRepo<>(db.database);

    	User user1 = new User(null, "User1");
    	User user2 = new User(null, "User2");
    	User user3 = new User(null, "User3");
    	User user4 = new User(null, "User4");
    	ObjectId uid1 = userRepo.save(user1);
    	userRepo.save(user2);
    	userRepo.save(user3);
    	userRepo.save(user4);

    	BlogPost post = new BlogPost(null, uid1);
    	post.comments = new ArrayList<>();
    	//blogRepo.save(new BlogPost(new ObjectId(), new DBRef<User, ObjectId>(user2.getId(), User.class)));
    	//blogRepo.save(new BlogPost(new ObjectId(), new DBRef<User, ObjectId>(user2.getId(), User.class)));
    	//blogRepo.save(new BlogPost(new ObjectId(), new DBRef<User, ObjectId>(user3.getId(), User.class)));

    	for(int i = 0; i < 100; i++){
    		Comment c = new Comment(null, uid1);
    		commentRepo.save(c);
    		post.comments.add(c.getId());
    	}

    	blogRepo.save(post);
    	User test1 = userRepo.findById(uid1);
    	
    	BlogPost blogtest1 = blogRepo.findBy("owner", test1.getId()).get(0);
    	
    	int counter = 0;
    	for(ObjectId ref : blogtest1.comments){
    		Comment c = commentRepo.findById(ref);
    		if (c != null){
    			counter++;
    		}
    	}
    	if(counter == 100){
    		System.out.println("Success.");
    	}else {
    		System.out.println("Failure.");
    	}
    	
    }
}
