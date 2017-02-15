package hu.sovaroq.experiment.mongo.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongojack.Id;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BlogPost {
    private final ObjectId id;
    public List<ObjectId> comments;
    public final ObjectId owner;

	@JsonCreator
	public BlogPost(@Id ObjectId id, ObjectId owner) {
		this.id = id;
		this.owner = owner;
	}
	@Id
	public ObjectId getId() {
		return id;
	}	

	public ObjectId getOwner(){
		return this.owner;
	}
    
}
