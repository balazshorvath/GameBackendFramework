package hu.sovaroq.framework.service.chat;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hu.sovaroq.framework.data.user.User;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ChatMessage")
@Data
@ToString
public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	int messageid;

	@Column
	long timestamp;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	Conversation conversation;
	
	@Column(length = 255, nullable = false)
	String message;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	User user;
	
}
