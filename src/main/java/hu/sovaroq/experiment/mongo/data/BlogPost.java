package hu.sovaroq.experiment.mongo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import java.util.List;


public class BlogPost {
    private final String id;
    private final List<String> comments;
    private final String owner;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public BlogPost(@Id @ObjectId String id, @JsonProperty("comments") List<String> comments, @JsonProperty("owner") String owner) {
		this.id = id;
		this.owner = owner;
		this.comments = comments;
	}
	public BlogPost(List<String> comments, String owner) {
		this.id = new org.bson.types.ObjectId().toString();
		this.owner = owner;
		this.comments = comments;
	}
	@Id @ObjectId
	public String getId() {
		return id;
	}

	public List<String> getComments() {
		return comments;
	}

	public String getOwner() {
		return owner;
	}
}
