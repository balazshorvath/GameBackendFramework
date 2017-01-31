package hu.sovaroq.game.core.base;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
public class BuildingBase {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	private Long id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(length = 50, nullable = false, unique = true)
	private String description;
	
	@Column(nullable = false)
	private long baseHP;
	
	@Column(nullable = false)
	private long baseAttackDamage;

}
