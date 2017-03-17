package hu.sovaroq.core.chat;

import hu.sovaroq.core.user.authentication.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

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
