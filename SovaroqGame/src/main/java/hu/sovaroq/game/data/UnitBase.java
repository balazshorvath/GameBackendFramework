package hu.sovaroq.game.data;

import static javax.persistence.GenerationType.AUTO;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyClass;
import javax.persistence.Table;

import hu.sovaroq.game.data.combat.AttackBase;
import hu.sovaroq.game.data.combat.DefenseType;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
public class UnitBase {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long unitid;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;
    @Column(nullable = false)
    private long baseHP;
    @Column(nullable = false)
    private double baseMovementSpeed;
    @Column
    private double baseDamageModifier;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attackbase_unitbase", joinColumns = {@JoinColumn(name = "unitid")}, inverseJoinColumns = {@JoinColumn(name = "attackid")})
    private Set<AttackBase> baseAttacks;
    
    @MapKeyClass(value = DefenseType.class)
    @ElementCollection(targetClass = Integer.class)
    private Map<DefenseType, Integer> defenseStats;
    
    @Column(length = 50, nullable = false)
    private String scriptFile;

    public UnitBase() {
    }

}

