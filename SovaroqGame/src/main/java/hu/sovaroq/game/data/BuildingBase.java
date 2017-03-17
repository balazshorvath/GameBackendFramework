package hu.sovaroq.game.data;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

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
