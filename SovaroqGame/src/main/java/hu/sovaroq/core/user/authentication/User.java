package hu.sovaroq.core.user.authentication;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "User")
@Data
@ToString
@EqualsAndHashCode
public class User implements IUser {
	public static final String USERID = "userId";
	public static final String LOGIN = "login";

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = USERID)
	Long userId;
	
	@NaturalId
	@Column(name = LOGIN)
	String login;
	
	@Column(length = 255, nullable = false)
	String password;
	
	@Column
	boolean locked = false;
	
	
}
