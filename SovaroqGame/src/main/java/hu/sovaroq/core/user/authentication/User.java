package hu.sovaroq.core.user.authentication;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hu.sovaroq.core.user.account.UserAccount;
import lombok.AllArgsConstructor;
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

    public User() {
    }

    public User(String login, UserRole role, String email, String password, boolean locked, UserAccount account) {
        this.login = login;
        this.role = role;
        this.email = email;
        this.password = password;
        this.locked = locked;
        this.account = account;
    }

    @Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = USERID)
	Long userId;

	@Column(name = LOGIN, length = 255, nullable = false, unique = true)
	String login;
	
	@Column
	UserRole role;
	
	@Column(length = 255, nullable = false, unique = true)
	String email;
	
	@Column(length = 255, nullable = false)
	String password;
	
	@Column
	boolean locked = false;
	
	@OneToOne
	UserAccount account;	
}
