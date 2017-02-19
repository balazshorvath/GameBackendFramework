package hu.sovaroq.experiment.mongo;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import hu.sovaroq.experiment.mongo.data.*;
import hu.sovaroq.experiment.mongo.repositories.ChatMessageRepository;
import hu.sovaroq.experiment.mongo.repositories.ConversationRepository;
import hu.sovaroq.experiment.mongo.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.mongojack.DBRef;

import com.mongodb.DB;
import com.mongodb.MongoClient;

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


        UserRepository userRepository = new UserRepository(db.database);
        ChatMessageRepository chatMessageRepository = new ChatMessageRepository(db.database);
        ConversationRepository conversationRepository = new ConversationRepository(db.database);

        Random randomGenerator = new Random();
        SecureRandom random = new SecureRandom();

        List<MongoUser> users = new ArrayList<>(10);
        List<Conversation> conversations = new ArrayList<>(5);
        List<ChatMessage> messages = new ArrayList<>();


        long startLoading = System.currentTimeMillis();

        for(int i=0; i <= 10; i++){
            MongoUser user = new MongoUser();
            user.setLogin("User" + i);
            user.setPassword("pass");
            userRepository.save(user);
            users.add(user);
        }

        for(int i = 0; i <= 5; i++){
            Conversation conversation = new Conversation();
            conversation.setName("TestConversation" + i);
            Set<DBRef<MongoUser, String>> convUsers = new HashSet<>();
            for(int j=0; j <= i % 2; j++){
                convUsers.add(users.get(randomGenerator.nextInt(users.size())).makeRef());
            }
            conversation.setParticipants(convUsers);
            conversationRepository.save(conversation);
            conversations.add(conversation);
        }

        for(int i = 0; i <= 100000; i++){
            ChatMessage message = new ChatMessage();
            message.setConversation(conversations.get(randomGenerator.nextInt(conversations.size())).makeRef());
            message.setMessage(getRandomString(random));
            message.setUser(users.get(randomGenerator.nextInt(users.size())).makeRef());
            message.setTimestamp(random.nextLong());
            chatMessageRepository.save(message);
            messages.add(message);
        }

        long stoploading = System.currentTimeMillis();

        System.out.println("Elapsed time from start until write ready is " + (stoploading - startLoading) + " ms.");
    }
    private static String getRandomString(SecureRandom random){
        return new BigInteger(130, random).toString(32);
    }
}
