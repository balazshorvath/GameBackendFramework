package hu.sovaroq.core.chat;

import hu.sovaroq.core.user.authentication.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

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
