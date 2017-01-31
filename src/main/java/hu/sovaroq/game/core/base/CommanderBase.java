package hu.sovaroq.game.core.base;

import static javax.persistence.GenerationType.AUTO;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.Data;
import lombok.ToString;


@Entity
@Table(name = "CommanderBase")
@Data
@ToString
public class CommanderBase {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	private Long commanderid;
	
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(length = 50, nullable = false, unique = true)
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "buildingbase_commanderbase", joinColumns = { @JoinColumn(name = "commanderid") }, inverseJoinColumns = { @JoinColumn(name = "buildingid") })
	private Set<BuildingBase> availableBuildings;

}
