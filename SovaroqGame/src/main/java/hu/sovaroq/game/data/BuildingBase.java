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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "unitbase_buildingbase", joinColumns = {@JoinColumn(name = "buildingId")}, inverseJoinColumns = {@JoinColumn(name = "unitId")})
    Set<UnitBase> availableUnits;
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long buildingId;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String description;
    @Column(nullable = false)
    private long baseHP;
    @Column(nullable = false)
    private long baseAttackDamage;

    public BuildingBase(Set<UnitBase> availableUnits, String name, String description, long baseHP, long baseAttackDamage) {
        this.availableUnits = availableUnits;
        this.name = name;
        this.description = description;
        this.baseHP = baseHP;
        this.baseAttackDamage = baseAttackDamage;
    }

    public BuildingBase() {
    }
}
