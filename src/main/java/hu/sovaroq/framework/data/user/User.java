package hu.sovaroq.framework.data.user;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

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
	
	@NaturalId
	@Column(name = LOGIN)
	String login;
	
	@Column(length = 255, nullable = false)
	String password;
	
	@Column
	boolean locked = false;
	
	
}
