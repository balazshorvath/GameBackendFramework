package hu.sovaroq.game.core.base;

import lombok.Data;
import lombok.ToString;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@ToString
public class Unit {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	private Long id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(length = 50, nullable = false, unique = true)
	private String description;
	
	public Unit(){}

}

