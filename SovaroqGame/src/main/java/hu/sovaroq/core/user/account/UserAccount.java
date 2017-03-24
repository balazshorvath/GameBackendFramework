package hu.sovaroq.core.user.account;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "Account")
@Data
@ToString
@EqualsAndHashCode
public class UserAccount implements IUserAccount{
	
	@Id
	@GeneratedValue(strategy = AUTO)
	Long accountId;
	
	@Column
	long experiencePoints;
	
	@Column
	long money;
	
	@Column
	byte[] profileImage;
	
	
}
