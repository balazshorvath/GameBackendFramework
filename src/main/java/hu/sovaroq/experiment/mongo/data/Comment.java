package hu.sovaroq.experiment.mongo.data;

import org.bson.types.ObjectId;
import org.mongojack.Id;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Comment {
    private final ObjectId id;
    public String text;
    private final ObjectId owner;

	@JsonCreator
	public Comment(@Id ObjectId id, ObjectId owner) {
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