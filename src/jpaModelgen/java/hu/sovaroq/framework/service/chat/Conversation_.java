package hu.sovaroq.framework.service.chat;

import hu.sovaroq.framework.data.user.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Conversation.class)
public abstract class Conversation_ {

	public static volatile SingularAttribute<Conversation, Integer> conversationid;
	public static volatile SingularAttribute<Conversation, String> name;
	public static volatile SetAttribute<Conversation, User> participants;

}

