package hu.sovaroq.game.core.data;

import static javax.persistence.GenerationType.AUTO;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "BuildingBase")
@Data
@ToString
public class BuildingBase {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	@Column
	private Long buildingid;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@Column(length = 50, nullable = false)
	private String description;
	
	@Column(nullable = false)
	private long baseHP;
	
	@Column(nullable = false)
	private long baseAttackDamage;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "unitbase_buildingbase", joinColumns = { @JoinColumn(name = "buildingid") }, inverseJoinColumns = { @JoinColumn(name = "unitid") })
	Set<UnitBase> availableUnits;

}
