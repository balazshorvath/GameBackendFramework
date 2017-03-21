package hu.sovaroq.core.user.authentication;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hu.sovaroq.core.user.account.UserAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

	@Column(name = LOGIN, length = 255, nullable = false, unique = true)
	String login;
	
	@Column(length = 255, nullable = false, unique = true)
	String email;
	
	@Column(length = 255, nullable = false)
	String password;
	
	@Column
	boolean locked = false;
	
	@Column
	@OneToOne
	UserAccount account;
	
}
