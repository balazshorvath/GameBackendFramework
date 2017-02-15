package hu.sovaroq.framework.service.chat;

import static javax.persistence.GenerationType.AUTO;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import hu.sovaroq.framework.data.user.User;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Conversation")
@Data
@ToString
public class Conversation {

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	int conversationid;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_conversation", joinColumns = { @JoinColumn(name = "conversationid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
	Set<User> participants;
	
	@Column(length = 255, nullable = false)
	String name;
	
}
