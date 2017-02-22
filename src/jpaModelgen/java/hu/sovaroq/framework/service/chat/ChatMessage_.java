package hu.sovaroq.framework.service.chat;

import hu.sovaroq.framework.data.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChatMessage.class)
public abstract class ChatMessage_ {

	public static volatile SingularAttribute<ChatMessage, Integer> messageid;
	public static volatile SingularAttribute<ChatMessage, String> message;
	public static volatile SingularAttribute<ChatMessage, User> user;
	public static volatile SingularAttribute<ChatMessage, Conversation> conversation;
	public static volatile SingularAttribute<ChatMessage, Long> timestamp;

}

