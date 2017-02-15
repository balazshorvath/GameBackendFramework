package hu.sovaroq.experiment.mongo.data;

import org.bson.types.ObjectId;
import org.mongojack.Id;


public class User {
	@Id
	private String id;
	private String login;

	public User(String id, String login){
		this.id = id;
		this.login = login;
	}

	public User(){
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
}